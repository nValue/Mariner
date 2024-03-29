package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesAgrupamientos;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarRechazosCausales;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.model.logic.liquidaciones.SAPListadoLiquidacionesLogicOperations;
import co.com.realtech.mariner.model.logic.radicaciones_sap.SAPRadicacionesLogicOperations;
import co.com.realtech.mariner.model.sdo.estandar.EntidadLiquidacionResultado;
import co.com.realtech.mariner.util.consecutives.NumeracionesManager;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.sap_files.SAPFilesUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

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

    private List<MarRadicaciones> radicacionesUsuario;
    private MarRadicaciones radicacionUsuarioSel;
    private MarRadicacionesFasesEstados radFaseEstadoActual;

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstProcesadas;
    private MarRadicacionesFasesEstados radicacionFaseEstProcesadaSel;
    private List<MarRadicacionesFasesEstados> radicacionesFasEstProcFiltros;

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;

    private String codigoLiquidacion;
    private DetalleLiquidacion detalleLiquidacion;
    private List<DetalleLiquidacion> detallesLiquidaciones;

    private List<MarRechazosCausales> rechazos;
    private MarRechazosCausales rechazoSel;

    private String observaciones;
    private Date fechaFiltroInic;
    private Date fechaFiltroFin;

    private String numeroLiquidacion;

    private SAPRadicacionesLogicOperations sapRadicacionesLogicOperations;
    private SAPListadoLiquidacionesLogicOperations sapLLO;
    private Date fechaLiquidaciones;
    
    private List<MarRadicaciones> radicacionesAdicionales;
    private MarRadicaciones radicacionAdicionalSel;
    
    private MarRadicacionesAgrupamientos radicacionAgrupamiento;
    
    private int minutosRojo;
    private int minutosAmarillo;

    @Override
    public void init() {
        minutosRojo = 9999999;
        minutosAmarillo = 9999999;
        
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        fechaLiquidaciones = new Date();
        numeroLiquidacion = "";
        obtenerRadicacionesPendientes();
        obtenerRechazos();
        radicacionesAdicionales = new ArrayList<>();
        obtenerLimiteMinutos();
    }

    /**
     * Obtiene los rechazos disponibles en la plataforma.
     */
    public void obtenerRechazos() {
        try {
            rechazos = (List<MarRechazosCausales>) genericDAOBean.loadAllForEntity(MarRechazosCausales.class, "rcaId");
            if (!rechazos.isEmpty()) {
                rechazoSel = rechazos.get(0);
            }
        } catch (Exception e) {
            String msj = "No se pueden obtener los motivos del rechazo, causado por : " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazos inválidos", msj, true, false);
            logger.error(msj, e);
        }
    }
    
    /**
     * Obtiene los minutos límite para colocar los objetos de la lista en colores.
     */
    public void obtenerLimiteMinutos(){
        try {
            minutosRojo = Integer.parseInt(ConstantesUtils.cargarConstante("VUR-MINUTOS-ROJO"));
            minutosAmarillo = Integer.parseInt(ConstantesUtils.cargarConstante("VUR-MINUTOS-AMARILLO"));
        } catch (Exception e) {
            logger.error("No se pueden traer los minutos de las constantes, causado por : " + e.getMessage());
        }
    }

    /**
     * Obtiene las radicaciones asignadas al usuario.
     */
    public void obtenerRadicacionesPendientes() {
        try {
            radicacionesUsuario = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'G-P', 'R-R', 'G-S'", usuarioSesion);
            if (!radicacionesUsuario.isEmpty()) {
                radicacionUsuarioSel = radicacionesUsuario.get(0);
                obtenerFasesEstadosDeRadicacion();
                obtenerRadicacionesAgrupamientos();
            }else{
                //Si la persona no tiene algo pendiente le trae automáticamente una radicación usando el motor de asignaciones.
                asignarNuevaRadicacion();
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
            PrimeFacesContext.execute("PF('tablaHistorial').clearFilters()");
            radicacionesFasesEstProcesadas = radicFasesEstadosDAOBean.obtenerRadicFasEstXUsuFasEstYFechasFase(usuarioSesion, "G-S", fechaFiltroInic, fechaFiltroFin);
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
            if(usuarioSesion.getUsuEsLiquidador() == null || !usuarioSesion.getUsuEsLiquidador().equals("S")){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "No es liquidador", "No se le pueden asignar radicaciones ya que usted no posee el permiso para ello", true, false);
                return;
            }
            Integer maxRads = Integer.parseInt(ConstantesUtils.cargarConstante("MAX-LIQUID-USER"));
            if (radicacionesUsuario != null && (radicacionesUsuario.size() >= maxRads)) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Máximo encontrado", "El usuario tiene el máximo de radicaciones permitidas para el proceso ( " + maxRads + " )", true, false);
                return;
            }
            MarRadicacionesFasesEstados rfe = radFaseEstadoActual;
            radFaseEstadoActual = null;
            BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_asignar_liquidacion", usuarioSesion.getUsuId().intValue(), "I-P", "");
            Integer salida = BDsalida.intValue();
            System.out.println("salida = " + salida);
            if (salida != null) {
                switch (salida) {
                    case -999:
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "No hay datos", "No hay radicaciones disponibles en este momento, por favor intente mas tarde", true, false);
                        radFaseEstadoActual = rfe;
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
        } catch (NumberFormatException | MarinerPersistanceException e) {
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
            MarRadicacionesFasesEstados rfeNuevo = (MarRadicacionesFasesEstados) genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId.rfeId", BDsalida);
            rfeNuevo.setRcaId(rechazoSel);
            genericDAOBean.merge(rfeNuevo);
            
            //Notifica a la persona a través de Push el rechazo correspondiente
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/estados/"+usuarioAsignado.getUsuId(), new FacesMessage("Radicacion rechazada", "La radicacion " + radicacionUsuarioSel.getRadNumero() + " ha sido rechazada. Motivo: " + observaciones));
            
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
            if (rfEst.getFesId().getFesCodigo().equals("G-A")) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso validado", "La radicación actual ya ha sido cargada y validada correctamente desde el sistema SAP, puede continuar con otros procesos.", true, false);
            } else if (rfEst.getFesId().getFesCodigo().equals("G-S")) {
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
     * Pregunta si la radicacion actual es un rechazo (Sirve para colocar el (R)
     * en la lista de los pendientes)
     *
     * @param radSel
     * @return
     */
    public String esRechazo(MarRadicaciones radSel) {
        String valor = radSel.getRadNumero();
        try {
            MarRadicacionesFasesEstados ultimo = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radSel);
            if (ultimo.getFesId().getFesCodigo().equals("R-R")) {
                valor = "(R) - " + valor;
            }else if(ultimo.getRadId().getPriId() != null && ultimo.getRadId().getPriId().getPriCodigo().equals("DIS")){
                valor = "(I) - " + valor;
            }else if(ultimo.getRadId().getPriId() != null && ultimo.getRadId().getPriId().getPriCodigo().equals("NOT")){
                valor = "(U) - " + valor;
            }
        } catch (Exception e) {
        }
        return valor;
    }
    
    /**
     * Obtiene el tiempo en minutos desde que la radicación ha estado en el estado enviado.
     */
    public String obtenerTiempoMins(MarRadicaciones radic){
        int minutos = 0;
        try {
            MarRadicacionesFasesEstados ultFase = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radic);
            minutos = radicFasesEstadosDAOBean.obtenerMinutosActualesRadFase(ultFase);
        } catch (Exception e) {
            String msj = "No se puede obtener el tiempo de la radicación, debido a : " + e;
            logger.error(msj,e);   
        }
        String color = "";
        if(minutos > minutosRojo){
            color = "red-background";
        }else if(minutos > minutosAmarillo){
            color = "yellow-background";
        }
        return color;
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

    /**
     * Limpia toda la información de búsqueda en SAP.
     */
    public void limpiarInfoBusqSAP() {
        numeroLiquidacion = "";
        codigoLiquidacion = "";
        detalleLiquidacion = null;
        if (usuarioSesion.getUsuAliasSap() == null || usuarioSesion.getUsuAliasSap().isEmpty()) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Usuario SAP no válido", "El usuario actual no tiene configurado un usuario de SAP para obtener los datos pendientes de liquidaciones, por favor configúrelo.", true, false);
            return;
        }

        sapLLO = SAPListadoLiquidacionesLogicOperations.create();
        if (obtenerLiqPendientes()) {
            PrimeFacesContext.execute("PF('dialogLiquidacion').show()");
        }
    }

    /**
     * Obtiene las liquidaciones pendientes para el usuario y el día
     * seleccionado.
     *
     * @return
     */
    public boolean obtenerLiqPendientes() {
        try {
            codigoLiquidacion = "";
            detalleLiquidacion = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            detallesLiquidaciones = sapLLO.obtenerLiquidacionesSAPByUsuario(sdf.format(fechaLiquidaciones), usuarioSesion.getUsuAliasSap());
            return true;
        } catch (Exception e) {
            String msj = "No se pueden obtener las liquidaciones pendientes para el usuario, por favor verifique la conexion con SAP";
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Liquidaciones error", msj, true, false);
            logger.error(msj, e);
            return false;
        }
    }

    /**
     * Llama al servicio web de SAP para extraer todos los datos.
     */
    public void obtenerInformacionSAP() {
        if (getCodigoLiquidacion().isEmpty()) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Liquidación requerida", "Debe seleccionar un número de liquidación para poder validarla en SAP", true, false);
        } else {
            try {
                // Verificamos que esa liquidacion tenga Archivo y Boleta Fiscal en VUR
                String contieneArchivos = genericDAOBean.callGenericFunction("PKG_VUR_NEGOCIO.FL_VERIFICAR_ARCHIVOS_LIQUI", getCodigoLiquidacion()).toString();

                if (contieneArchivos.equals("S")) {
                    sapRadicacionesLogicOperations = SAPRadicacionesLogicOperations.create();
                    setDetalleLiquidacion(sapRadicacionesLogicOperations.consultarLiquidacionSAP(getCodigoLiquidacion()));
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Explode, "Archivos Requeridos", "Lo sentimos pero la liquidacion <b>" + getCodigoLiquidacion() + "</b> no tiene Boleta Fiscal y/o Recibo, por favor presione el Boton Generar PDF's en SAP.", true, false);
                }
            } catch (Exception e) {
                String msj = "No se pueden extraer los datos de SAP, causado por: " + e;
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error en SAP", msj, true, false);
                logger.error(msj, e);
            }
        }
    }

    /**
     * Dada la información de detalle liquidación, la vincula al proceso actual y cambia el estado de todas las adicionales.
     */
    public void vincularInformacionSAP() {
        try {
            EntidadLiquidacionResultado salida = sapRadicacionesLogicOperations.vincularRadicacionSAP(radicacionUsuarioSel, getCodigoLiquidacion(),true);
            if (salida.getEstado().equals("OK")) {
                SAPFilesUtils.vincularArchivosSAP(radicacionUsuarioSel.getRadId());
                radicacionUsuarioSel = null;
                //Si hay radicaciones adicionales, les cambia el estado a todas.
                for (MarRadicaciones radicacionAdicional : radicacionesAdicionales) {
                    BigDecimal dbSalida;
                    Integer salidaInt = -999;
                    
                    //Excepción colocada en caso de que algo falle ya que es un ajuste que se hace en producción
                    try {
                        MarRadicacionesFasesEstados rfeUlt = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionAdicional);
                        if (rfeUlt.getFesId().getFesCodigo().equals("G-S")) {
                            dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionAdicional.getRadId(),
                                    "G-A", "A", usuarioSesion.getUsuId(), observaciones, null);
                            salidaInt = dbSalida.intValue();
                            if (salidaInt == -999) {
                                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                                return;
                            }
                        }else{
                            logger.warn("No se puede cambiar la radicación " + radicacionAdicional.getRadNumero() + " al estado 'Liquidada y vinculada' porque su último estado no es 'En proceso de liquidación en SAP', la radicación principal es: " + radicacionUsuarioSel.getRadNumero());
                        }
                    } catch (Exception e) {
                        logger.error("Error al validar el último estado de una radicación agrupada, causado por : " + e.getMessage(), e);
                        dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionAdicional.getRadId(),
                                "G-A", "A", usuarioSesion.getUsuId(), observaciones, null);
                        salidaInt = dbSalida.intValue();
                        if (salidaInt == -999) {
                            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                            return;
                        }
                    }

                    //Ajuste comentariado para limpiar las radicaciones adicionales.
                    //radicacionesAdicionales = new ArrayList<>();
                    //radicacionAdicionalSel = null;
                }
                obtenerRadicacionesPendientes();
                //Ajuste realizado para limpiar las radicaciones adicionales.
                radicacionesAdicionales = new ArrayList<>();
                radicacionAdicionalSel = null;
                //Ajuste realizado para limpiar las radicaciones adicionales.
                
                PrimeFacesContext.execute("PF('dialogLiquidacion').hide();");
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso finalizado", "Vinculación realizada correctamente y cambiada a validación por aprobador.", true, false);

            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error vinculacion", salida.getLog().getMensaje(), true, false);
            }
        } catch (Exception e) {
            String msj = "Error interno realizando vinculacion de Radicacion, codigo de error:" + e;
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", msj, true, false);
            logger.error(msj,e);
        }
    }
    
    /**
     * Crea una nueva radicación y la lleva hasta el estado de liquidación.
     */
    public void crearNuevaRadicacion(){
        try {
            //Primero guarda la radicacion agrupamiento si no se ha hecho
            if(radicacionAgrupamiento.getRaaId() == null){
                radicacionAgrupamiento.setAudFecha(new Date());
                radicacionAgrupamiento.setAudUsuario(usuarioSesion.getUsuLogin());
                genericDAOBean.save(radicacionAgrupamiento);
            }
            //Primero crea la nueva radicación y la guarda
            radicacionAdicionalSel = new MarRadicaciones();
            radicacionAdicionalSel.setEscId(radicacionUsuarioSel.getEscId());
            radicacionAdicionalSel.setNotId(radicacionUsuarioSel.getNotId());
            radicacionAdicionalSel.setPriId(radicacionUsuarioSel.getPriId());
            radicacionAdicionalSel.setRaaId(radicacionAgrupamiento);
            radicacionAdicionalSel.setRadEstado(radicacionUsuarioSel.getRadEstado());
            radicacionAdicionalSel.setRadFecha(new Date());
            radicacionAdicionalSel.setRadTurno(radicacionUsuarioSel.getRadTurno());
            NumeracionesManager nm = new NumeracionesManager();
            String siguienteNum = nm.loadNextConsecutiveRad("RAD01", radicacionUsuarioSel);
            radicacionAdicionalSel.setRadNumero(siguienteNum);
            radicacionAdicionalSel.setAudFecha(new Date());
            radicacionAdicionalSel.setAudUsuario(usuarioSesion.getUsuLogin());
            genericDAOBean.save(radicacionAdicionalSel);
            
            //Obtiene el usuario que creó la radicación para colocarselo a la que se va a crear
            MarUsuarios usuarioCrea = new MarUsuarios();
            for (MarRadicacionesFasesEstados radicacionFaseEstado : radicacionesFasesEstados) {
                if(radicacionFaseEstado.getFesId().getFesCodigo().equals("I-P")){
                    usuarioCrea = radicacionFaseEstado.getUsuId();
                }
            }
            
            //Ahora se crea el estado de ingreso
            BigDecimal dbSalida;
            Integer salida = -999;
            dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionAdicionalSel.getRadId(),
                    "I-P", "A", usuarioCrea.getUsuId(), "Fase creada automáticamente - Múltiples liquidaciones", null);
            salida = dbSalida.intValue();
            if (salida == -999) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
            
            //Luego se crea el estado de asignado a liquidador
            dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionAdicionalSel.getRadId(),
                    "G-P", "A", usuarioSesion.getUsuId(), "Fase creada automáticamente - Múltiples liquidaciones", null);
            salida = dbSalida.intValue();
            if (salida == -999) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
            
            //Luego crea el estado de proceso en liquidación SAP
            dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionAdicionalSel.getRadId(),
                    "G-S", "A", usuarioSesion.getUsuId(), "Fase creada automáticamente - Múltiples liquidaciones", null);
            salida = dbSalida.intValue();
            if (salida == -999) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
            
            //Le coloca el agrupamiento a la actual también
            radicacionUsuarioSel.setRaaId(radicacionAgrupamiento);
            genericDAOBean.merge(radicacionUsuarioSel);
            
            //Al final vincula esta radicación con la liquidación actual.
            vincularNuevaLiquidacion();
            
            //Se agrega a la lista de radicacionesAdicionales
            radicacionesAdicionales.add(radicacionAdicionalSel);
        } catch (Exception e) {
            String msj = "Ocurrió un error al crear una nueva radicación, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", msj, true, false);
            logger.error(msj,e);
        }
    }
    
    /**
     * Vincula la radicación recién creada a la liquidación seleccionada pero sin cambiar el estado.
     */
    public void vincularNuevaLiquidacion(){
        try {
            EntidadLiquidacionResultado salida = sapRadicacionesLogicOperations.vincularRadicacionSAP(radicacionAdicionalSel, getCodigoLiquidacion(),false);
            if (salida.getEstado().equals("OK")) {
                SAPFilesUtils.vincularArchivosSAP(radicacionAdicionalSel.getRadId());
                PrimeFacesContext.execute("PF('dialogLiquidacion').hide();");
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso finalizado", "Se ha creado una nueva radicación con el número " + radicacionAdicionalSel.getRadNumero() + ", y se ha vinculado la liquidación de SAP con código: " + codigoLiquidacion, true, false);
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error vinculación", salida.getLog().getMensaje(), true, false);
            }
        } catch (Exception e) {
            String msj = "No se puede vincular esta liquidación a la radicación recién creada, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", msj, true, false);
            logger.error(msj, e);
        }
    }
    
    /**
     * Obtiene todas las radicaciones agrupadas a esta principal.
     */
    public void obtenerRadicacionesAgrupamientos() {
        if (radicacionUsuarioSel.getRaaId() == null) {
            radicacionAgrupamiento = new MarRadicacionesAgrupamientos();
            radicacionAgrupamiento.setRaaAgrupaEscs("N");
            radicacionAgrupamiento.setRaaAgrupaLiqs("S");
        } else {
            radicacionAgrupamiento = radicacionUsuarioSel.getRaaId();
            try {
                //Trae todas las radicaciones agrupadas y quita la que tiene actualmente en pantalla.
                List<MarRadicaciones> rads = radicacionesDAOBean.obtenerRadicacionesActivasPorAgrupacion(radicacionAgrupamiento);
                Predicate<MarRadicaciones> pred = x -> !x.getRadId().equals(radicacionUsuarioSel.getRadId());
                radicacionesAdicionales = rads.stream().filter(pred).collect(Collectors.toList());
                if (!radicacionesAdicionales.isEmpty()) {
                    radicacionAdicionalSel = radicacionesAdicionales.get(0);
                }
            } catch (Exception e) {
                String msj = "No se pueden obtener las otras liquidaciones asociadas al proceso, causado por : " + e;
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicaciones adicionales no encontradas", msj, true, false);
                logger.error(msj, e);
            }
        }
    }
    
    /**
     * Desvincula una liquidación de SAP asociada a una radicación.
     */
    public void anularRadicacion(){
        try {
            radicacionAdicionalSel.setRadEstado("R");
            auditSessionUtils.setAuditReflectedValues(radicacionAdicionalSel);
            genericDAOBean.merge(radicacionAdicionalSel);
            radicacionAdicionalSel = null;
            obtenerRadicacionesAgrupamientos();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Eliminación realizada", "La radicación se ha eliminado y su liquidación esta libre para ser asignada", true, false);
        } catch (Exception e) {
            String msj = "Error desvinculando la radicación, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Desvinculación incompleta", msj, true, false);
            logger.error(msj , e);
        }
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

    public String getNumeroLiquidacion() {
        return numeroLiquidacion;
    }

    public void setNumeroLiquidacion(String numeroLiquidacion) {
        this.numeroLiquidacion = numeroLiquidacion;
    }

    public List<DetalleLiquidacion> getDetallesLiquidaciones() {
        return detallesLiquidaciones;
    }

    public void setDetallesLiquidaciones(List<DetalleLiquidacion> detallesLiquidaciones) {
        this.detallesLiquidaciones = detallesLiquidaciones;
    }

    public Date getFechaLiquidaciones() {
        return fechaLiquidaciones;
    }

    public void setFechaLiquidaciones(Date fechaLiquidaciones) {
        this.fechaLiquidaciones = fechaLiquidaciones;
    }

    public String getCodigoLiquidacion() {
        return codigoLiquidacion;
    }

    public void setCodigoLiquidacion(String codigoLiquidacion) {
        this.codigoLiquidacion = codigoLiquidacion;
    }

    public DetalleLiquidacion getDetalleLiquidacion() {
        return detalleLiquidacion;
    }

    public void setDetalleLiquidacion(DetalleLiquidacion detalleLiquidacion) {
        this.detalleLiquidacion = detalleLiquidacion;
    }

    public List<MarRadicaciones> getRadicacionesAdicionales() {
        return radicacionesAdicionales;
    }

    public void setRadicacionesAdicionales(List<MarRadicaciones> radicacionesAdicionales) {
        this.radicacionesAdicionales = radicacionesAdicionales;
    }

    public MarRadicaciones getRadicacionAdicionalSel() {
        return radicacionAdicionalSel;
    }

    public void setRadicacionAdicionalSel(MarRadicaciones radicacionAdicionalSel) {
        this.radicacionAdicionalSel = radicacionAdicionalSel;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasEstProcFiltros() {
        return radicacionesFasEstProcFiltros;
    }

    public void setRadicacionesFasEstProcFiltros(List<MarRadicacionesFasesEstados> radicacionesFasEstProcFiltros) {
        this.radicacionesFasEstProcFiltros = radicacionesFasEstProcFiltros;
    }
    
    
    

}
