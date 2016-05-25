package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarRechazosCausales;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
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
 * ManagedBean encargado de asignar las radicaciones a los liquidadores y enviar
 * la info a SAP
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class GeneracionManagedBean extends GenericManagedBean {

    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    @EJB(beanName = "WSSAPConsumerBean")
    private WSSAPConsumerBeanLocal wSSAPConsumerBean;

    private List<MarRadicaciones> radicacionesUsuario;
    private MarRadicaciones radicacionUsuarioSel;
    private MarRadicacionesFasesEstados radFaseEstadoActual;

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstProcesadas;
    private MarRadicacionesFasesEstados radicacionFaseEstProcesadaSel;

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;

    private List<MarRechazosCausales> rechazos;
    private MarRechazosCausales rechazoSel;
    
    private String observaciones;
    private Date fechaFiltroInic;
    private Date fechaFiltroFin;

    @Override
    public void init() {
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        obtenerRadicacionesPendientes();
        obtenerRechazos();
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
     * Obtiene las radicaciones asignadas al usuario.
     */
    public void obtenerRadicacionesPendientes() {
        try {
            System.out.println("ObteniendoPdtes");
            radicacionesUsuario = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'G-P', 'G-S', 'R-R'", usuarioSesion);
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
    public void obtenerRadicacionesProcesadas() {
        try {
            radicacionesFasesEstProcesadas = radicFasesEstadosDAOBean.obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(usuarioSesion, null, fechaFiltroInic, fechaFiltroFin);
            if (!radicacionesFasesEstProcesadas.isEmpty()) {
                radicacionFaseEstProcesadaSel = radicacionesFasesEstProcesadas.get(0);
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones procesadas, causado por : " + e, e);
        }
    }

    /**
     * Trae todas las fases-estados en los que hay pasado una radicación.
     */
    public void obtenerFasesEstadosDeRadicacion() {
        try {
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>) genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacionUsuarioSel, true, "rfeId");
            if (!radicacionesFasesEstados.isEmpty()) {
                radFaseEstadoActual = radicacionesFasesEstados.get(radicacionesFasesEstados.size() - 1);
                if (radFaseEstadoActual.getFesId().getFesCodigo().equals("G-P")) {
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

            Integer maxRads = Integer.parseInt(ConstantesUtils.cargarConstante("MAX-LIQUID-USER"));
            if (radicacionesUsuario != null && (radicacionesUsuario.size() > maxRads)) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Máximo encontrado", "El usuario tiene el máximo de radicaciones permitidas para el proceso ( " + maxRads + " )", true, false);
                return;
            }
            radFaseEstadoActual = null;
            BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_asignar_liquidacion", usuarioSesion.getUsuId().intValue(), "I-P", "");

            Integer salida = BDsalida.intValue();
            System.out.println("salida = " + salida);
            if (salida != null) {
                switch (salida) {
                    case -999:
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "No hay datos", "No hay radicaciones disponibles en este momento, por favor intente mas tarde", true, false);
                        return;
                    case -1000:
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Fase generación necesaria", "No se encuentra una Fase-Estado de código 'G-P' por favor configúrela", true, false);
                        return;
                    default:
                        radFaseEstadoActual = (MarRadicacionesFasesEstados) genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId", BigDecimal.valueOf(salida));
                        if (radFaseEstadoActual == null) {
                            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valor no encontrado", "No se encontró una radicación-fase-estado para este usuario.", true, false);
                            return;
                        }
                        break;
                }
            }
            MarRadicaciones radObtenida = radFaseEstadoActual.getRadId();
            obtenerRadicacionesPendientes();
            radicacionUsuarioSel = radObtenida;
            obtenerFasesEstadosDeRadicacion();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación encontrada", "Se ha asignado la radicación <b> -" + radObtenida.getRadNumero() + "- </b> a sus pendientes, puede verificarla en la lista desplegable de radicaciones", true, false);
        } catch (Exception e) {
            logger.error("Error asignando una radicación disponible, causado por: " + e, e);
        }
    }

    /**
     * Se pasa el proceso a pendiente por subir en el ERP SAP.
     */
    public void validarRadicacion() {
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionUsuarioSel);
            if (!rfe.getRfeId().equals(radicacionesFasesEstados.get(radicacionesFasesEstados.size() - 1).getRfeId())) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación ya procesada", "Lo sentimos pero esta radicación ya ha sido procesada por la siguiente parte del proceso y no puede ser editada.", true, false);
                return;
            }
            BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionUsuarioSel.getRadId(),
                    "G-S", "A", usuarioSesion.getUsuId(), observaciones, null);
            Integer salida = BDsalida.intValue();
            if (salida == -999) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
            MarRadicaciones radicacionGuardada = radicacionUsuarioSel;
            obtenerRadicacionesPendientes();
            radicacionUsuarioSel = radicacionGuardada;
            obtenerFasesEstadosDeRadicacion();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación correcta", "La radicación número: " + radicacionUsuarioSel.getRadNumero() + " ahora se encuentra en estado de pendiente para cargue en SAP.", true, false);
        } catch (Exception e) {
            logger.error("No se puede guardar la aprobación, causado por " + e, e);
        }
    }

    /**
     * Rechaza la radicación a un estado anterior o la anula según sea el caso.
     */
    public void rechazarRadicacion() {
        try {
            //Se obtiene el usuario que hizo el proceso para enviarle de vuelta el proceso.
            List<MarRadicacionesFasesEstados> rfes = radicFasesEstadosDAOBean.obtenerRadicFaseEstDeRadyFase(radicacionUsuarioSel, "I-P");
            MarUsuarios usuarioAsignado = rfes.get(rfes.size() - 1).getUsuId();

            //Se crea la fase proceso de rechazo (I-R) con las observaciones colocadas.
            BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionUsuarioSel.getRadId(),
                    "I-R", "R", usuarioAsignado, observaciones, null);
            Integer salida = BDsalida.intValue();
            if (salida == -999) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado rechazo para la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
            MarRadicacionesFasesEstados rfeNuevo = (MarRadicacionesFasesEstados)genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId.rfeId", BDsalida);
            rfeNuevo.setRcaId(rechazoSel);
            genericDAOBean.merge(rfeNuevo);
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo realizado", "Proceso rechazado correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede rechazar la radicación, debido a " + e, e);
        }
    }

    /**
     * Obtiene la radicación que se ingresó en SAP con todos sus datos.
     */
    public void obtenerSAP() {
        try {
            
            MarRadicacionesFasesEstados rfEst = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionUsuarioSel);
            if(rfEst.getFesId().getFesCodigo().equals("G-A")){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso validado", "La radicación actual ya ha sido cargada y validada correctamente desde el sistema SAP, puede continuar con otros procesos.", true, false);
            }else if(rfEst.getFesId().getFesCodigo().equals("G-S")){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso en espera", "El proceso aún se encuentra en espera del cargue de información por parte de la plataforma SAP.", true, false);
            }
            
            /*
            //Valida que haya una respuesta por parte del WS en SAP
            if (true) {
                //Si no es vació hay que buscar otra validación para confirmar que los datos son correctos.
                BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionUsuarioSel.getRadId(),
                        "G-A", "A", usuarioSesion.getUsuId(), observaciones, null);
                Integer salida = BDsalida.intValue();
                if (salida == -999) {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado rechazo para la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                    return;
                } else {
                    obtenerRadicacionesPendientes();
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Información Obtenida", "La información fue cargada correctamente a la radicación por parte del sistema SAP", true, false);
                }
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Información no extraida", "No hay datos de retorno por parte de SAP, verifique la creación correcta del proceso en dicha plataforma e intente de nuevo", true, false);
            }*/
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Información no extraida", "No se puede obtener la información de SAP, causado por " + e, true, false);
            logger.error("No se puede obtener la información de SAP, causado por " + e, e);
        }
    }

    /**
     * Pregunta si la radicacion actual es un rechazo (Sirve para colocar el (R) en la lista de los pendientes)
     * @param radSel
     * @return 
     */
    public String esRechazo(MarRadicaciones radSel){
        String valor = radSel.getRadNumero();
        try {
            MarRadicacionesFasesEstados ultimo = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radSel);
            if(ultimo.getFesId().getFesCodigo().equals("R-R")){
                valor = "(R) " + valor;
            }
        } catch (Exception e) {
        }
        return valor;
    }
    
    /**
     * Muestra el detalle de una radicación vieja en pantalla.
     */
    public void verDetalleRadSel() {
        radicacionUsuarioSel = radicacionFaseEstProcesadaSel.getRadId();
        obtenerFasesEstadosDeRadicacion();
        PrimeFacesContext.execute("PF('dialogHistorial').hide()");
    }

    /**
     * Valida si el último estado todavía hace parte de los estados de
     * generación para editar información
     *
     * @return
     */
    public boolean validarUltimoEstado() {
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionUsuarioSel);
            if (rfe.getFesId().getFasId().getFasCodigo().equals("GEN")) {
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
    
    

}
