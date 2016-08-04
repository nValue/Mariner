package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.cambio_estado.DetalleCambioEstado;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesActosSap;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarRechazosCausales;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.model.logic.estados.SAPEstadosLogicOperations;
import co.com.realtech.mariner.util.cdf.CDFFileDispatcher;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.files.PDFUtils;
import co.com.realtech.mariner.util.jsf.file.FileDownloader;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class RevisionManagedBean extends GenericManagedBean{

    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;
    
    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;
    
    private List<MarRadicaciones> radicacionesPendientes;
    private MarRadicaciones radicacionPendienteSel;
    
    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;
    private MarRadicacionesFasesEstados radicacionFaseEstadoSel;
    private List<MarRadicacionesFasesEstados> radicacionesFasesEstadosFiltros;
    
    private List<MarRadicacionesFasesEstados> radicacionesHistorial;
    private MarRadicacionesFasesEstados radicacionHistorialSel;
    
    private List<MarRadicacionesActosSap> radicacionesActos;
    
    private List<MarRechazosCausales> rechazos;
    private MarRechazosCausales rechazoSel;
    
    private Date fechaFiltroInic;
    private Date fechaFiltroFin;
    
    private String observaciones;
    
    @Override
    public void init() {
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        obtenerRadicacionesPendientes();
        obtenerRechazos();
    }
    
    /**
     * Obtiene las radicaciones asignadas al usuario.
     */
    public void obtenerRadicacionesPendientes(){
        try {
            radicacionesPendientes = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'R-P','R-A'", usuarioSesion);
            if(!radicacionesPendientes.isEmpty()){
                radicacionPendienteSel = radicacionesPendientes.get(0);
                obtenerFasesEstadosDeRadicacion();
            }else{
                radicacionPendienteSel = null;
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones, causado por : " + e, e);
        }    
    }
    
    /**
     * Obtiene los rechazos disponibles en la plataforma.
     */
    public void obtenerRechazos(){
        try {
            rechazos = (List<MarRechazosCausales>)genericDAOBean.loadAllForEntity(MarRechazosCausales.class, "rcaId");
            if(!rechazos.isEmpty()){
                rechazoSel = rechazos.get(0);
            }
        } catch (Exception e) {
            String msj = "No se pueden obtener los motivos del rechazo, causado por : " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazos inválidos", msj, true, false);
            logger.error(msj, e);
        }
    }
    
    /**
     * Trae todas las fases-estados en los que hay pasado una radicación.
     */
    public void obtenerFasesEstadosDeRadicacion(){
        try {
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>)genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacionPendienteSel, true, "rfeId");
            if(!radicacionesFasesEstados.isEmpty()){
                radicacionFaseEstadoSel = radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1);
                //Si el proceso ya está como Revisión-Aprobado, coloca las observaciones
                if(radicacionFaseEstadoSel.getFesId().getFesCodigo().equals("R-A")){
                    observaciones = radicacionFaseEstadoSel.getRfeObservaciones();
                }else{
                    observaciones = "";
                }
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las fases procesos, causado por : " + e, e);
        }
    }
    
    /**
     * Obtiene las radicaciones que ya evaluó el usuario.
     */
    public void obtenerRadicacionesProcesadas(){
        try {
            radicacionesHistorial = radicFasesEstadosDAOBean.obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(usuarioSesion, null,fechaFiltroInic, fechaFiltroFin);
            if(!radicacionesHistorial.isEmpty()){
                radicacionFaseEstadoSel = radicacionesHistorial.get(0);
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones procesadas, causado por : " + e, e);
        }    
    }
    
    /**
     * Asigna una nueva radicacion al usuario en sesión.
     */
    public void asignarNuevaRadicacion() {
        try {
            if(usuarioSesion.getUsuEsAprobador()== null || !usuarioSesion.getUsuEsAprobador().equals("S")){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "No es aprobador", "No se le pueden asignar radicaciones para validar ya que usted no posee el permiso para ello", true, false);
                return;
            }
            //Carga la constante del número máximo de radicaciones que puede tener un validador.
            Integer maxRads = Integer.parseInt(ConstantesUtils.cargarConstante("MAX-VALID-USER"));
            if(radicacionesPendientes != null && (radicacionesPendientes.size() >= maxRads)){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Máximo encontrado", "El usuario tiene el máximo de radicaciones permitidas para el proceso ( " + maxRads + " )", true, false);
                return;
            }
            MarRadicacionesFasesEstados radicacionFaseUltimoEstado;
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_asignar_liquidacion", usuarioSesion.getUsuId().intValue(), "G-A", "");
            Integer salida = BDsalida.intValue();
            switch (salida) {
                case -999:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "No hay datos", "No hay radicaciones disponibles en este momento, por favor intente mas tarde", true, false);
                    return;
                case -1000:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Fase generación necesaria", "No se encuentra una Fase-Estado de código 'R-P' por favor configúrela", true, false);
                    return;
                default:
                    radicacionFaseUltimoEstado = (MarRadicacionesFasesEstados)genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId", BigDecimal.valueOf(salida));
                    if(radicacionFaseUltimoEstado == null){
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valor no encontrado", "No se encontró una radicación-fase-estado para este usuario.", true, false);
                        return;
                    }
                    break;
            }
            MarRadicaciones radObtenida = radicacionFaseUltimoEstado.getRadId();
            obtenerRadicacionesPendientes();
            radicacionPendienteSel = radObtenida;
            obtenerFasesEstadosDeRadicacion();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación encontrada", "Se ha asignado la radicación <b> -" + radObtenida.getRadNumero() + "- </b> a sus pendientes, puede verificarla en la lista desplegable de radicaciones", true, false);
        } catch (Exception e) {
            logger.error("Error asignando una radicación disponible, causado por: " + e, e);
        }
    }
    
    /**
     * Se pasa el proceso a aceptado por parte de los revisores.
     */
    public void validarRadicacion(){
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionPendienteSel);
            if(!rfe.getRfeId().equals(radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1).getRfeId())){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación ya procesada", "Lo sentimos pero esta radicación ya ha sido procesada por la siguiente parte del proceso y no puede ser editada.", true, false);
                return;
            }
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionPendienteSel.getRadId(), 
                    "R-A", "A", usuarioSesion.getUsuId(),observaciones,null);
            Integer salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                return;
            }
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Revisión correcta", "Revisión realizada correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede guardar la aprobación, causado por " + e, e);
        }
    }
    
    /**
     * Desvincula una liquidación de SAP asociada a una radicación.
     */
    public void desvincularLiquidacion(){
        try {
            genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_desvincular_liquidacion", radicacionPendienteSel.getRadId());
            
            //Se obtiene el usuario que hizo el proceso para enviarle de vuelta el proceso, el que la sube a SAP debe ser.
            List<MarRadicacionesFasesEstados> rfes = radicFasesEstadosDAOBean.obtenerRadicFaseEstDeRadyFase(radicacionPendienteSel, "G-P");
            MarUsuarios usuarioAsignado = rfes.get(rfes.size()-1).getUsuId();
            
            //Se crea la fase proceso de rechazo (R-R) con las observaciones colocadas.
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionPendienteSel.getRadId(), 
                    "R-R", "R", usuarioAsignado,observaciones,null);
            
            Integer salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado rechazo de revisión para la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                return;
            }
            radicacionPendienteSel = null;
            obtenerRadicacionesPendientes();
            
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Desvinculación completada", "Desvinculación realizada correctamente, se ha devuelto el proceso al liquidador.", true, false);
        } catch (Exception e) {
            String msj = "Error desvinculando la radicación, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Desvinculación incompleta", msj, true, false);
            logger.error(msj , e);
        }
    }
    
    /**
     * Rechaza la radicación a un estado anterior o la anula según sea el caso.
     */
    public void rechazarRadicacion(){
        try {
            SAPEstadosLogicOperations slo = SAPEstadosLogicOperations.create();
            DetalleCambioEstado det = slo.cambiarEstadoLiquidacion(radicacionPendienteSel.getRadLiquidacion(), "A");
            if(!det.getEstadoRespuesta().equals("0")){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se pudo anular la liquidación en SAP, causado por : " + det.getMensaje(), true, false);
                return;
            }
            
            //Se obtiene el usuario que hizo el proceso para enviarle de vuelta el proceso, el que la sube a SAP debe ser.
            List<MarRadicacionesFasesEstados> rfes = radicFasesEstadosDAOBean.obtenerRadicFaseEstDeRadyFase(radicacionPendienteSel, "G-P");
            MarUsuarios usuarioAsignado = rfes.get(rfes.size()-1).getUsuId();
            
            //Se crea la fase proceso de rechazo (R-R) con las observaciones colocadas.
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionPendienteSel.getRadId(), 
                    "R-R", "R", usuarioAsignado,observaciones,null);
            
            Integer salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado rechazo de revisión para la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                return;
            }
            
            MarRadicacionesFasesEstados rfeNuevo = (MarRadicacionesFasesEstados)genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId.rfeId", BDsalida);
            rfeNuevo.setRcaId(rechazoSel);
            genericDAOBean.merge(rfeNuevo);
            
            //Notifica a la persona a través de Push el rechazo correspondiente
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/estados/"+usuarioAsignado.getUsuId(), new FacesMessage("Liquidación rechazada", "La radicacion " + radicacionPendienteSel.getRadNumero() + " ha sido rechazada. Motivo: " + observaciones));
            
            radicacionPendienteSel = null;
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo realizado", "Proceso rechazado correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede rechazar la radicación, debido a " + e, e);
        }        
    }
    
    /**
     * Selecciona una radicación del historial y muestra su información en pantalla.
     */
    public void seleccionarRadicacionHistorial(){
        radicacionPendienteSel = radicacionFaseEstadoSel.getRadId();
        radicacionHistorialSel = radicacionFaseEstadoSel;
        obtenerFasesEstadosDeRadicacion();
    }
    
    /**
     * Obtiene un historial de todas las radicaciones atendidas por el usuario actual.
     */
    public void obtenerHistorialRadicaciones(){
        try {
            PrimeFacesContext.execute("PF('tablaHist').clearFilters()");
            radicacionesHistorial = radicFasesEstadosDAOBean.obtenerRadicFasEstXUsuFasEstYFechasFase(usuarioSesion, "'R-A','R-R'",fechaFiltroInic, fechaFiltroFin);
            if(!radicacionesHistorial.isEmpty()){
                radicacionHistorialSel = radicacionesHistorial.get(0);
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones procesadas, causado por : " + e, e);
        }    
    }
    
    /**
     * Obtiene los actos asociadas a una radicación.
     */
    public void obtenerActosDeRadicacion(){
        try {
            //Si es nulo es porque se está consultando desde el historial y debe traer los campos
            if(radicacionPendienteSel == null){
                radicacionPendienteSel = radicacionHistorialSel.getRadId();
            }
            System.out.println("radicacionPendienteSel = " + radicacionPendienteSel);
            radicacionesActos = (List<MarRadicacionesActosSap>)genericDAOBean.findAllByColumn(MarRadicacionesActosSap.class, "rdeId", radicacionPendienteSel.getMarRadicacionesDetallesSap(), true, "rdsId");
        } catch (Exception e) {
            String msj = "No se pueden obtener los actos de la radicación, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Actos no realizado", msj, true, false);
            logger.error(msj, e);
        }
    }
    
    
    /**
     * Ingresa la transacción para pago en bancos si no está creada.
     *
     */
    public boolean validarTransaccion() {
        try {
            // verificar que la transaccion no tenga una en curso.
            MarTransacciones transVerificacion = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "radId", radicacionPendienteSel);
            if (transVerificacion == null) {
                transVerificacion = new MarTransacciones();
                transVerificacion.setTraTipoPago("RECIBO");
                transVerificacion.setRadId(radicacionPendienteSel);
                transVerificacion.setTraReferencia(radicacionPendienteSel.getRadLiquidacion());
                transVerificacion.setTraValor(radicacionPendienteSel.getRadValorLiq());
                transVerificacion.setTraCuantia(radicacionPendienteSel.getRadCuantia());
                transVerificacion.setTraFechaInicio(new Date());
                transVerificacion.setTraEstado("T");
                transVerificacion.setUsuId(usuarioSesion);
                transVerificacion.setTraApellidos("Gobernación");
                transVerificacion.setTraNombres("Robot");
                transVerificacion.setTraTelefono("7777777");
                transVerificacion.setTraCorreo("robotgobernacion@valledelcauca.gov.co");
                transVerificacion.setTraDocumento("99999999");
                transVerificacion.setTdcId(usuarioSesion.getPerId().getTdcId());
                auditSessionUtils.setAuditReflectedValues(transVerificacion);
                genericDAOBean.save(transVerificacion);
                System.out.println("Transaccion " + transVerificacion.getTraId() + " guardada");
            }
            return true;
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error seleccionando el medio de pago.", true, false);
            logger.error("Error realizando validación en impresión, causado por " + e);
        }
        return false;
    }
    
    /**
     * Coloca el estado S a la columna de radicacion-impresion.
     */
    public void validarEntrega(){
        try {
            MarTransacciones transVerificacion = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "radId", radicacionPendienteSel);
            if(transVerificacion == null){
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Impresión requerida", "Antes de validar la entrega debe descargar el recibo de pago.", true, false);
                return;
            }
            radicacionPendienteSel.setRadEsImpresion("S");
            auditSessionUtils.setAuditReflectedValues(radicacionPendienteSel);
            genericDAOBean.merge(radicacionPendienteSel);
            
            radicacionPendienteSel = null;
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Proceso completado", "Se ha marcado el proceso con el estado de impresión de recibo de pago satisfactoriamente", true, false);
            PrimeFacesContext.execute("PF('dialogImpresion').hide()");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error validando la entrega", true, false);
            logger.error("Error validando entrega de impresión, causado por " + e);
        }
    }
    
    /**
     * Descargar recibo de pago.
     */
    public void descargarRecibo() {
        try {
            if (validarTransaccion()) {
                //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                //String contextPath = servletContext.getContextPath();
                //String urlRecibo = contextPath + "/static/fileDispatcher/" + radicacionPendienteSel.getArcIdReciboPago().getArcId() + "-" +  radicacionPendienteSel.getArcIdReciboPago().getArcHash() + "." + radicacionPendienteSel.getArcIdReciboPago().getArcExtension();
                //RequestContext.getCurrentInstance().execute("window.open('" + urlRecibo + "');");
                
                CDFFileDispatcher dispatcher = CDFFileDispatcher.create();
                dispatcher.findFile(radicacionPendienteSel.getArcIdReciboPago().getArcId());
                File archivo = PDFUtils.agregarTexto(dispatcher.getFileContent(), "Turno: " + radicacionPendienteSel.getRadTurno() + " - " + radicacionPendienteSel.getRadNumero());
                if(archivo.exists()){
                    byte[] bytes = IOUtils.toByteArray(new FileInputStream(archivo));
                    new FileDownloader().descargarArchivoFacesContext(FacesContext.getCurrentInstance(), bytes, archivo.getName() + ".pdf");
                }else{
                    PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se encuentra el archivo con la impresión de turno", true, false);
                }
            }
        } catch (Exception e) {
        }
    }
    
    
    
    /**
     * Obtiene los datos de la impresión.
     */
    public void obtenerImpresion() {
        //Si es nulo es porque se está consultando desde el historial y debe traer los campos
        if (radicacionPendienteSel == null) {
            radicacionPendienteSel = radicacionHistorialSel.getRadId();
        }
    }

    public List<MarRadicaciones> getRadicacionesPendientes() {
        return radicacionesPendientes;
    }

    public void setRadicacionesPendientes(List<MarRadicaciones> radicacionesPendientes) {
        this.radicacionesPendientes = radicacionesPendientes;
    }

    public MarRadicaciones getRadicacionPendienteSel() {
        return radicacionPendienteSel;
    }

    public void setRadicacionPendienteSel(MarRadicaciones radicacionPendienteSel) {
        this.radicacionPendienteSel = radicacionPendienteSel;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstados() {
        return radicacionesFasesEstados;
    }

    public void setRadicacionesFasesEstados(List<MarRadicacionesFasesEstados> radicacionesFasesEstados) {
        this.radicacionesFasesEstados = radicacionesFasesEstados;
    }

    public MarRadicacionesFasesEstados getRadicacionFaseEstadoSel() {
        return radicacionFaseEstadoSel;
    }

    public void setRadicacionFaseEstadoSel(MarRadicacionesFasesEstados radicacionFaseEstadoSel) {
        this.radicacionFaseEstadoSel = radicacionFaseEstadoSel;
    }

    public Date getFechaFiltroInic() {
        return fechaFiltroInic;
    }

    public void setFechaFiltroInic(Date fechaFiltroInic) {
        this.fechaFiltroInic = fechaFiltroInic;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesHistorial() {
        return radicacionesHistorial;
    }

    public void setRadicacionesHistorial(List<MarRadicacionesFasesEstados> radicacionesHistorial) {
        this.radicacionesHistorial = radicacionesHistorial;
    }

    public List<MarRechazosCausales> getRechazos() {
        return rechazos;
    }

    public void setRechazos(List<MarRechazosCausales> rechazos) {
        this.rechazos = rechazos;
    }

    public MarRechazosCausales getRechazoSel() {
        return rechazoSel;
    }

    public void setRechazoSel(MarRechazosCausales rechazoSel) {
        this.rechazoSel = rechazoSel;
    }

    public List<MarRadicacionesActosSap> getRadicacionesActos() {
        return radicacionesActos;
    }

    public void setRadicacionesActos(List<MarRadicacionesActosSap> radicacionesActos) {
        this.radicacionesActos = radicacionesActos;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstadosFiltros() {
        return radicacionesFasesEstadosFiltros;
    }

    public void setRadicacionesFasesEstadosFiltros(List<MarRadicacionesFasesEstados> radicacionesFasesEstadosFiltros) {
        this.radicacionesFasesEstadosFiltros = radicacionesFasesEstadosFiltros;
    }

    
    
    
    
    
    
}
