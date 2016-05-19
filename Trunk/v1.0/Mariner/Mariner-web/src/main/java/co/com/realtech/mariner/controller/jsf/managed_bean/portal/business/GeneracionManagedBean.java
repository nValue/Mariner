package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de asignar las radicaciones a los liquidadores y enviar la info a SAP
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class GeneracionManagedBean extends GenericManagedBean{
    
    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;
    
    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;
    
    private List<MarRadicaciones> radicacionesUsuario;
    private MarRadicaciones radicacionUsuarioSel;
    private MarRadicacionesFasesEstados radFaseEstadoActual;
    
    private List<MarRadicacionesFasesEstados> radicacionesFasesEstProcesadas;
    private MarRadicacionesFasesEstados radicacionFaseEstProcesadaSel;
    
    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;
    
    private String observaciones;
    private Date fechaFiltroInic;
    private Date fechaFiltroFin;

    @Override
    public void init() {
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        obtenerRadicacionesPendientes();
    }
    
    /**
     * Obtiene las radicaciones asignadas al usuario.
     */
    public void obtenerRadicacionesPendientes(){
        try {
            radicacionesUsuario = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'G-P','G-S'", usuarioSesion);
            if(!radicacionesUsuario.isEmpty()){
                radicacionUsuarioSel = radicacionesUsuario.get(0);
                obtenerFasesEstadosDeRadicacion();
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones, causado por : " + e, e);
        }    
    }
    
    /**
     * Obtiene las radicaciones que ya evaluó el usuario.
     */
    public void obtenerRadicacionesProcesadas(){
        try {
            radicacionesFasesEstProcesadas = radicFasesEstadosDAOBean.obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(usuarioSesion, null,fechaFiltroInic, fechaFiltroFin);
            if(!radicacionesFasesEstProcesadas.isEmpty()){
                radicacionFaseEstProcesadaSel = radicacionesFasesEstProcesadas.get(0);
            }
            System.out.println("radicacionesFasesEstProcesadas = " + radicacionesFasesEstProcesadas);
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones procesadas, causado por : " + e, e);
        }    
    }
    
    /**
     * Trae todas las fases-estados en los que hay pasado una radicación.
     */
    public void obtenerFasesEstadosDeRadicacion(){
        try {
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>)genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacionUsuarioSel, true, "rfeId");
            if(!radicacionesFasesEstados.isEmpty()){
                radFaseEstadoActual = radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1);
                if(radFaseEstadoActual.getFesId().getFesCodigo().equals("G-P")){
                    observaciones = radFaseEstadoActual.getRfeObservaciones();
                }
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las fases procesos, causado por : " + e, e);
        }
    }
    
    /**
     * Asigna una nueva radicacion al usuario en sesión.
     */
    public void asignarNuevaRadicacion() {
        try {
            radFaseEstadoActual = null;
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("fn_asignar_liquidacion", usuarioSesion.getUsuId().intValue(), "I-P", "");
            Integer salida = BDsalida.intValue();
            System.out.println("salida = " + salida);
            if(salida != null) switch (salida) {
                case -999:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "No hay datos", "No hay radicaciones disponibles en este momento, por favor intente mas tarde", true, false);
                    return;
                case -1000:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Fase generación necesaria", "No se encuentra una Fase-Estado de código 'G-P' por favor configúrela", true, false);
                    return;
                default:
                    radFaseEstadoActual = (MarRadicacionesFasesEstados)genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId", BigDecimal.valueOf(salida));
                    if(radFaseEstadoActual == null){
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valor no encontrado", "No se encontró una radicación-fase-estado para este usuario.", true, false);
                        return;
                    }
                    break;
            }
            obtenerRadicacionesPendientes();
            radicacionUsuarioSel = radFaseEstadoActual.getRadId();
            obtenerFasesEstadosDeRadicacion();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación encontrada", "Se ha asignado una nueva radicación a sus pendientes, puede verificarla en la lista desplegable de radicaciones", true, false);
        } catch (Exception e) {
            logger.error("Error asignando una radicación disponible, causado por: " + e, e);
        }
    }
    
    /**
     * Se pasa el proceso a pendiente por subir en el ERP SAP.
     */
    public void validarRadicacion(){
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionUsuarioSel);
            if(!rfe.getRfeId().equals(radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1).getRfeId())){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación ya procesada", "Lo sentimos pero esta radicación ya ha sido procesada por la siguiente parte del proceso y no puede ser editada.", true, false);
                return;
            }
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("fn_ingresar_fase_estado", radicacionUsuarioSel.getRadId(), 
                    "G-S", "A", usuarioSesion.getUsuId(),observaciones,null);
            Integer salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
            /**
            MarRadicacionesFasesEstados radFaseEstado = new MarRadicacionesFasesEstados();
            MarFasesEstados fasEstSAP = (MarFasesEstados)genericDAOBean.findByColumn(MarFasesEstados.class, "fesCodigo", "G-S");
            radFaseEstado.setFesId(fasEstSAP);
            radFaseEstado.setRadId(radicacionUsuarioSel);
            radFaseEstado.setRfeEstado("A");
            radFaseEstado.setRfeEstadoAprobacion("A");
            radFaseEstado.setRfeFechaInicio(new Date());
            radFaseEstado.setUsuId(usuarioSesion);
            radFaseEstado.setRfeObservaciones(observaciones);
            auditSessionUtils.setAuditReflectedValues(radFaseEstado);
            genericDAOBean.save(radFaseEstado);
            **/
            
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación correcta", "Validación realizada correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede guardar la aprobación, causado por " + e, e);
        }
    }
    
    /**
     * Rechaza la radicación a un estado anterior o la anula según sea el caso.
     */
    public void rechazarRadicacion(){
        try {
            //Se crea la fase proceso de rechazo (G-R) con las observaciones colocadas.
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("fn_ingresar_fase_estado", radicacionUsuarioSel.getRadId(), 
                    "G-R", "R", usuarioSesion.getUsuId(),observaciones,null);
            Integer salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado rechazo para la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
            /**
            //Después se crea la fase proceso de ingreso pendiente (I-P) para ser atendida de nuevo por los notarios.
            BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("fn_ingresar_fase_estado", radicacionUsuarioSel.getRadId(), 
                    "I-P", "A", usuarioSesion.getUsuId(),"",null);
            salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado ingreso pendiente para la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }**/
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo realizado", "Proceso rechazado correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede rechazar la radicación, debido a " + e, e);
        }
    }
    
    /**
     * Obtiene la radicación que se ingresó en SAP con todos sus datos.
     */
    public void obtenerSAP(){
        
    }
    
    /**
     * Muestra el detalle de una radicación vieja en pantalla.
     */
    public void verDetalleRadSel(){
        radicacionUsuarioSel = radicacionFaseEstProcesadaSel.getRadId();
        obtenerFasesEstadosDeRadicacion();
        PrimeFacesContext.execute("PF('dialogHistorial').hide()");
    }
    
    /**
     * Valida si el último estado todavía hace parte de los estados de generación para editar información
     * @return 
     */
    public boolean validarUltimoEstado(){
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionUsuarioSel);
            if(rfe.getFesId().getFasId().getFasCodigo().equals("GEN")){
                return true;
            }
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Validación no completada", "No se puede validar el último estado de la radicación.", true, false);
            logger.error("No se puede validar el último estado, causado por " + e, e);
        }
        return false;
    }
    

    public List<MarRadicaciones> getRadicacionesUsuario() {
        return radicacionesUsuario;
    }

    public void setRadicacionesUsuario(List<MarRadicaciones> radicacionesUsuario) {
        this.radicacionesUsuario = radicacionesUsuario;
    }

    public MarRadicaciones getRadicacionUsuarioSel() {
        return radicacionUsuarioSel;
    }

    public void setRadicacionUsuarioSel(MarRadicaciones radicacionUsuarioSel) {
        this.radicacionUsuarioSel = radicacionUsuarioSel;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstados() {
        return radicacionesFasesEstados;
    }

    public void setRadicacionesFasesEstados(List<MarRadicacionesFasesEstados> radicacionesFasesEstados) {
        this.radicacionesFasesEstados = radicacionesFasesEstados;
    }

    public MarRadicacionesFasesEstados getRadFaseEstadoActual() {
        return radFaseEstadoActual;
    }

    public void setRadFaseEstadoActual(MarRadicacionesFasesEstados radFaseEstadoActual) {
        this.radFaseEstadoActual = radFaseEstadoActual;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstProcesadas() {
        return radicacionesFasesEstProcesadas;
    }

    public void setRadicacionesFasesEstProcesadas(List<MarRadicacionesFasesEstados> radicacionesFasesEstProcesadas) {
        this.radicacionesFasesEstProcesadas = radicacionesFasesEstProcesadas;
    }

    public MarRadicacionesFasesEstados getRadicacionFaseEstProcesadaSel() {
        return radicacionFaseEstProcesadaSel;
    }

    public void setRadicacionFaseEstProcesadaSel(MarRadicacionesFasesEstados radicacionFaseEstProcesadaSel) {
        this.radicacionFaseEstProcesadaSel = radicacionFaseEstProcesadaSel;
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

    
    
    
}
