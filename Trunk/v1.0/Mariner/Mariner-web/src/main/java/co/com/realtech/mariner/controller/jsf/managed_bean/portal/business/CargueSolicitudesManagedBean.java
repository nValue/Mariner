package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.notarias.NotariasDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.model.entity.MarEscrituras;
import co.com.realtech.mariner.model.entity.MarFasesEstados;
import co.com.realtech.mariner.model.entity.MarNotarias;
import co.com.realtech.mariner.model.entity.MarPrioridades;
import co.com.realtech.mariner.model.entity.MarPuntosMontajes;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesAgrupamientos;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.util.cdf.CDFFileUploader;
import co.com.realtech.mariner.util.consecutives.NumeracionesManager;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.io.file.FileUtilidades;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;

/**
 * ManagedBean encargado de la pantalla de cargue de solicitudes.
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class CargueSolicitudesManagedBean extends GenericManagedBean {

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;

    @EJB(beanName = "NotariasDAOBean")
    private NotariasDAOBeanLocal notariasDAOBean;

    private List<MarRadicaciones> radicaciones;
    private MarRadicaciones radicacionSel;
    private MarRadicaciones radicacionSeleccion;
    private List<MarNotarias> notarias;

    private List<MarTiposDocumentos> tiposDocumentosOtorga;
    private List<MarTiposDocumentos> tiposDocumentosRecibe;

    private List<String> turnosActuales;

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstProcesadas;
    private MarRadicacionesFasesEstados radicacionFaseEstProcesadaSel;

    private MarFasesEstados faseEstadoPendiente;
    private String observacionesProceso;

    private Date fechaFiltroInic;
    private Date fechaFiltroFin;

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;

    private List<MarRadicaciones> radicacionesEscrituras;
    private MarRadicaciones radicacionEscrituraSel;

    private MarRadicacionesAgrupamientos radicacionAgrupamiento;
    
    private MarPrioridades prioridadGobernacion;
    private MarPrioridades prioridadVencimiento;
    private MarPrioridades prioridadDiscapacidad;
    private MarPrioridades prioridadNotaria;
    
    private boolean esPrioridad;

    @Override
    public void init() {
        esPrioridad = false;
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        obtenerNotarias();
        obtenerTiposDocumentos();
        obtenerFasesEstados();
        obtenerRadicacionesPendientes();
        turnosActuales = new ArrayList<>();
        radicacionesEscrituras = new ArrayList<>();
        obtenerPrioridades();
    }
    
    /**
     * Obtiene las prioridades del sistema para el motor de asignaciones.
     */
    public void obtenerPrioridades(){
        try {
            prioridadGobernacion = (MarPrioridades)genericDAOBean.findByColumn(MarPrioridades.class, "priCodigo", "GOB");
            prioridadVencimiento = (MarPrioridades)genericDAOBean.findByColumn(MarPrioridades.class, "priCodigo", "VEN");
            prioridadDiscapacidad = (MarPrioridades)genericDAOBean.findByColumn(MarPrioridades.class, "priCodigo", "DIS");
            prioridadNotaria = (MarPrioridades)genericDAOBean.findByColumn(MarPrioridades.class, "priCodigo", "NOT");
        } catch (Exception e) {
            String msj = "No se pueden obtener las prioridades del sistema para la asignación automática, sin embargo el sistema puede seguir funcionando";
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Prioridades requeridas", msj, true, false);
            logger.error(msj,e);
        }
    }

    /**
     * Obtiene las radicaciones asignadas al usuario.
     */
    public void obtenerRadicacionesPendientes() {
        try {
            radicaciones = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'I-P','I-R'", usuarioSesion);
            if (!radicaciones.isEmpty()) {
                radicacionSel = radicaciones.get(0);
                obtenerFasesEstadosDeRadicacion();
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones, causado por : " + e, e);
        }
    }

    /**
     * Obtiene las fases estados para ser asignada a la radicación.
     */
    public void obtenerFasesEstados() {
        try {
            faseEstadoPendiente = (MarFasesEstados) genericDAOBean.findByColumn(MarFasesEstados.class, "fesCodigo", "I-P");
            if (faseEstadoPendiente == null) {
                throw new Exception("No hay parametrizada una fase-estado con el código I-P");
            }
        } catch (Exception e) {
            logger.error("No se pueden obtener las fases estados, debido a " + e, e);
        }
    }

    /**
     * Obtiene los tipos de documento para los involucrados.
     */
    public void obtenerTiposDocumentos() {
        try {
            tiposDocumentosOtorga = (List<MarTiposDocumentos>) genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre");
            tiposDocumentosRecibe = (List<MarTiposDocumentos>) genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre");
        } catch (Exception e) {
            logger.error("Error obteniendo los tipos de documento, causado por " + e, e);
        }
    }

    /**
     * Trae todas las fases-estados en los que hay pasado una radicación.
     */
    public void obtenerFasesEstadosDeRadicacion() {
        try {
            //Valida si la radicación actual tiene prioridad.
            if (radicacionSel.getPriId() != null && radicacionSel.getPriId().getPriCodigo().equals("DIS")) {
                esPrioridad = true;
            }else{
                esPrioridad = false;
            }
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>) genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacionSel, true, "rfeId");
            if (!radicacionesFasesEstados.isEmpty()) {
                radicacionFaseEstProcesadaSel = radicacionesFasesEstados.get(radicacionesFasesEstados.size() - 1);
                //Si la radicación fue rechazada no trae las observaciones, asi el notario las puede colocar
                if (radicacionFaseEstProcesadaSel.getFesId().getFesCodigo().equals("I-R")) {
                    observacionesProceso = "";
                } else {
                    observacionesProceso = radicacionFaseEstProcesadaSel.getRfeObservaciones();
                }
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las fases procesos, causado por : " + e, e);
        }
    }

    /**
     * Método de autocomplete para buscar las radicaciones.
     *
     * @param filtro
     * @return
     */
    public List<MarRadicaciones> completeRadicaciones(String filtro) {
        List<MarRadicaciones> radFiltradas = new ArrayList<>();
        try {
            radFiltradas = radicacionesDAOBean.obtenerRadicacionesPorFiltro(filtro, usuarioSesion);
        } catch (Exception e) {
            logger.error("No se puede realizar el filtro de radicaciones, debido a " + e, e);
        }
        return radFiltradas;
    }

    /**
     * Obtiene las radicaciones que ya evaluó el usuario.
     */
    public void obtenerRadicacionesProcesadas() {
        try {
            radicacionesFasesEstProcesadas = radicFasesEstadosDAOBean.obtenerRadicFasEstXUsuFasEstYFechasFase(usuarioSesion, "I-P", fechaFiltroInic, fechaFiltroFin);
            if (!radicacionesFasesEstProcesadas.isEmpty()) {
                radicacionFaseEstProcesadaSel = radicacionesFasesEstProcesadas.get(radicacionesFasesEstProcesadas.size() - 1);
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones procesadas, causado por : " + e, e);
        }
    }

    /**
     * Muestra el detalle de una radicación vieja en pantalla.
     */
    public void verDetalleRadSel() {
        radicacionSel = radicacionFaseEstProcesadaSel.getRadId();
        obtenerFasesEstadosDeRadicacion();
        PrimeFacesContext.execute("PF('dialogHistorial').hide()");
    }

    /**
     * Coloca la radicación en la variable radicacionSel para ser actualizada.
     */
    public void seleccionarRadicacion() {
        try {
            obtenerFasesEstadosDeRadicacion();
        } catch (Exception e) {
            logger.error("Error seleccionando la radicación, causado por " + e, e);
        }
    }

    /**
     * Obtiene todas las notarias parametrizadas para seleccionar la adecuada.
     */
    public void obtenerNotarias() {
        try {
            notarias = notariasDAOBean.obtenerNotariasDeUsuario(usuarioSesion);
            if (notarias.isEmpty()) {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Notarias no encontradas", "No se ha encontrado una notaría asociada a su usuario, puede configurarla en el panel de usuarios, de lo contrario no podrá generar radicaciones", true, false);
            }
        } catch (Exception e) {
            logger.error("Error al obtener las notarias, causado por " + e, e);
        }
    }

    /**
     * Realiza la validación del complete de los turnos.
     */
    public List<String> completeTurnos(String str) {
        List<String> turnos = new ArrayList<>();
        Predicate<String> predi = (String x) -> x.startsWith(str);
        turnos = turnosActuales.stream().filter(predi).collect(Collectors.toList());
        return turnos;
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
            if (ultimo.getFesId().getFesCodigo().equals("I-R")) {
                valor = "(R) " + valor;
            }
        } catch (Exception e) {
        }
        return valor;
    }
    
    /**
     * Retorna S si la radiación pertenece a la gobernación
     * @return 
     */
    public String esGobernacion() {
        String esGobernacion;
        if (usuarioSesion.getNotId().getNotEsGobernacion().equals("S")) {
            esGobernacion = "S";
        } else {
            esGobernacion = "N";
        }
        return esGobernacion;
    }
    
    
    /**
     * Retorna si la notaría a la que está asociado el usuario debe tener prioridad.
     * @return 
     */
    public boolean esPrioridadNotaria(){
        return usuarioSesion.getNotId().getNotEsPrioridad().equals("S");
    }

    /**
     * Crea una nueva radicación vacía para el llenado de la información
     * necesaria.
     */
    public void solicitarRadicacion() {
        try {
            esPrioridad = false;
            String permiteCrear = ConstantesUtils.cargarConstante("VUR-CREAR-RADIC");
            if (permiteCrear.equals("N")) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación deshabilitada", "La plataforma no se encuentra disponible para crear más radicaciones, si desea más información puede comunicarse con la Gobernación para validar este proceso.", true, false);
                return;
            }
            if(usuarioSesion.getNotId() == null){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notaría necesaria", "El usuario actual debe tener una notaría asociada antes de crear alguna radicación", true, false);
                return;
            }
            String gobernacion = esGobernacion();
            String maximo;
            if(gobernacion.equals("S")){
                maximo = ConstantesUtils.cargarConstante("VUR-MAX-RAD-GOB");
            }else{
                maximo = ConstantesUtils.cargarConstante("VUR-MAX-RAD-NOT");
            }
            Integer max = Integer.parseInt(maximo);
            BigDecimal decCantidad = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_cantidad_radicaciones_hoy", gobernacion);
            if(decCantidad.intValue() > max){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación deshabilitada", "Lo sentimos. La plataforma no se encuentra disponible para crear más radicaciones.", true, false);
                return;
            }
            
            radicacionAgrupamiento = new MarRadicacionesAgrupamientos();
            radicacionesEscrituras = new ArrayList<>();
            radicaciones = null;
            MarEscrituras escrituraNueva = new MarEscrituras();
            escrituraNueva.setEscFecha(new Date());
            radicacionSel = new MarRadicaciones();
            radicacionSel.setEscId(escrituraNueva);
            radicacionSel.setNotId(usuarioSesion.getNotId());
            radicacionSel.setRadEstado("A");
            observacionesProceso = "";
            radicacionesFasesEstados = null;
            radicacionFaseEstProcesadaSel = null;
            if (gobernacion.equals("S")) {
                radicacionSel.setPriId(prioridadGobernacion);
            }else if(esPrioridadNotaria()){
                radicacionSel.setPriId(prioridadNotaria);
            }
            if (usuarioSesion.getNotId().getNotEsGobernacion().equals("S")) {
                BigDecimal dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_obtener_turno", usuarioSesion.getNotId());
                Integer inicio = dbSalida.intValue();
                Integer fin = usuarioSesion.getNotId().getNotTurnos().intValue();
                turnosActuales = new ArrayList<>();
                for (int i = inicio + 1; i < fin; i++) {
                    turnosActuales.add(String.valueOf(i));
                }
                radicacionSel.setRadTurno(String.valueOf(inicio + 1));
            } else {
                //PrimeFacesPopup.lanzarDialog(Effects.Slide, "Recomendación", "La notaría a la que usted se encuentra asociado no tiene turnos habilitados, le recomendamos configurarlos antes de continuar, si usted no maneja turnos haga caso omiso a este mensaje", true, false);
            }
        } catch (Exception e) {
            logger.error("Error creando nueva Radicación, causado por " + e, e);
        }
    }

    /**
     * Evento necesario para el cargue de archivos
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        try {
            CDFFileUploader fileUploader = CDFFileUploader.create(true);
            MarArchivos archivo;
            String fileName = event.getFile().getFileName();
            byte[] bytes = IOUtils.toByteArray(event.getFile().getInputstream());
            Long size = event.getFile().getSize();
            String mimeType = event.getFile().getContentType();
            if (FileUtilidades.invalidFile(mimeType)) {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Upload.!", "Este tipo de archivo no es permitido por la aplicación", true, false);
                return;
            }
            boolean visibleCDN = false;
            MarPuntosMontajes puntoMontaje;
            puntoMontaje = (MarPuntosMontajes) genericDAOBean.findByColumn(MarPuntosMontajes.class, "pmoNombre", "Principal");
            if (puntoMontaje == null) {
                String msj = "No hay un punto de montaje de nombre 'Principal', por favor configúrelo";
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Upload.!", msj, true, false);
                throw new Exception(msj);
            }
            archivo = fileUploader.saveFile(puntoMontaje, fileName, bytes, size, mimeType, usuarioSesion.getUsuLogin(), visibleCDN);
            radicacionSel.getEscId().setArcId(archivo);
            PrimeFacesContext.execute("PF('fileUploadDialog').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Archivo no es válido", e.getLocalizedMessage(), true, false);
            logger.error("No se puede cargar el archivo, causado por: " + e.getLocalizedMessage(), e);
        }
    }

    /**
     * Guarda la radicación actual.
     */
    public void guardarRadicaciones() {

        MarEscrituras escrituraActual = radicacionSel.getEscId();
        if (escrituraActual.getArcId() == null) {
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Documento faltante", "Necesita adjuntar la escritura para guardar el proceso", true, false);
            return;
        }
        /* Validación de turno en pendiente mientras se valida el autocomplete de turnos
            if(radicacionSel.getRadTurno() == null){
            } else if(!radicacionesDAOBean.esTurnoValido(radicacionSel.getRadTurno())){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Turno inválido", "El turno (" + radicacionSel.getRadTurno() + ") ya se encuentra asignado para el día de hoy, por favor intente con otro", true, false);
                return;
            }*/

        guardarRadicacion(radicacionSel);
        for (MarRadicaciones radicacionEscritura : radicacionesEscrituras) {
            guardarRadicacion(radicacionEscritura);
        }

        MarRadicaciones radicacionGuardada = radicacionSel;
        obtenerRadicacionesPendientes();
        radicacionSel = radicacionGuardada;
        obtenerFasesEstadosDeRadicacion();
        String msj = "Se ha creado una radicación con número " + radicacionGuardada.getRadNumero();
        for (MarRadicaciones radicacionEscritura : radicacionesEscrituras) {
            msj = msj + ", " + radicacionEscritura.getRadNumero();
        }
        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso realizado", msj , true, false);
    }

    /**
     * Guarda la radicación que se pase
     *
     * @param radicacion
     */
    public void guardarRadicacion(MarRadicaciones radicacion) {
        try {
            MarEscrituras escrituraActual = radicacion.getEscId();

            //Valida que la radicación actual no haya sido cambiada de fase para guardar los cambios.
            if (radicacion.getRadId() != null) {
                MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacion);
                if (!rfe.getRfeId().equals(radicacionesFasesEstados.get(radicacionesFasesEstados.size() - 1).getRfeId())) {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación ya procesada", "Lo sentimos pero esta radicación ya ha sido procesada por la siguiente parte del proceso y no puede ser editada.", true, false);
                    return;
                }
            }
            //Se guarda la escritura primero.
            auditSessionUtils.setAuditReflectedValues(escrituraActual);
            if (escrituraActual.getEscId() == null) {
                genericDAOBean.save(escrituraActual);
            } else {
                genericDAOBean.merge(escrituraActual);
            }
            
            if (!radicacionesEscrituras.isEmpty()) {
                //Si hay un agrupamiento se guarda después
                if (radicacionAgrupamiento.getRaaId() == null) {
                    radicacionAgrupamiento.setAudFecha(new Date());
                    radicacionAgrupamiento.setAudUsuario(usuarioSesion.getUsuLogin());
                    genericDAOBean.save(radicacionAgrupamiento);
                } else {
                    auditSessionUtils.setAuditReflectedValues(radicacionAgrupamiento);
                    genericDAOBean.merge(radicacionAgrupamiento);
                }
            }

            //Luego se guarda la radicación usando la numeración correspondiente
            auditSessionUtils.setAuditReflectedValues(radicacion);
            if (radicacion.getRadId() == null) {
                NumeracionesManager nm = new NumeracionesManager();
                String siguienteNum = nm.loadNextConsecutiveRad("RAD01", radicacion);
                radicacion.setRadNumero(siguienteNum);
                radicacion.setRadFecha(new Date());
                if (!radicacionesEscrituras.isEmpty()) {
                    radicacion.setRaaId(radicacionAgrupamiento);
                }
                genericDAOBean.save(radicacion);
            } else {
                genericDAOBean.merge(radicacion);
            }

            //Si la fase corresponde a un rechazo anterior o es nueva, crea el estado I-P
            BigDecimal dbSalida;
            Integer salida = -999;
            //Si las fases-estados está vacia, es una nueva radicación y se crea el estado I-P
            if (radicacionesFasesEstados == null || radicacionesFasesEstados.isEmpty()) {
                dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacion.getRadId(),
                        "I-P", "A", usuarioSesion.getUsuId(), observacionesProceso, null);
                salida = dbSalida.intValue();
            } else {
                MarRadicacionesFasesEstados rfes = radicacionesFasesEstados.get(radicacionesFasesEstados.size() - 1);
                //Si la última fase-estado es de rechazo, vuelve a crear el estado I-P
                if (rfes.getFesId().getFesCodigo().equals("I-R")) {
                    dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacion.getRadId(),
                            "I-P", "A", usuarioSesion.getUsuId(), observacionesProceso, null);
                    salida = dbSalida.intValue();
                    //Si la última fase-estado es I-P solo actualiza la información
                } else if (rfes.getFesId().getFesCodigo().equals("I-P")) {
                    genericDAOBean.merge(rfes);
                }
            }
            if (salida == -999) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                return;
            }
        } catch (Exception e) {
            String msj = "Error guardando la radicacion, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso incompleto", msj, true, false);
            logger.error(msj, e);
        }
    }

    /**
     * Cancela la radicación actual.
     */
    public void cancelarRadicacion() {
        obtenerRadicacionesPendientes();
        radicacionSel = null;
    }
    
    /**
     * Coloca la radicación en estado (R) que significa que está rechazada.
     */
    public void anularRadicacion(){
        try {
            radicacionSel.setRadEstado("R");
            auditSessionUtils.setAuditReflectedValues(radicacionSel);
            genericDAOBean.merge(radicacionSel);
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso realizado", "La radicación ahora se encuentra en estado Anulada", true, false);
            obtenerRadicacionesPendientes();
        } catch (Exception e) {
            String msj = "No se puede anular la radicación, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso incompleto", msj, true, false);
            logger.error(msj,e);
        }
        
    }

    /**
     * Obtiene todas las radicaciones agrupadas a esta principal.
     */
    public void obtenerRadicacionesAgrupamientos() {
        if (radicacionSel.getRaaId() == null) {
            radicacionAgrupamiento = new MarRadicacionesAgrupamientos();
            radicacionAgrupamiento.setRaaAgrupaEscs("S");
            radicacionAgrupamiento.setRaaAgrupaLiqs("N");
        } else {
            radicacionAgrupamiento = radicacionSel.getRaaId();
            try {
                //Trae todas las radicaciones agrupadas y quita la que tiene actualmente en pantalla.
                List<MarRadicaciones> rads = (List<MarRadicaciones>) genericDAOBean.findAllByColumn(MarRadicaciones.class, "raaId", radicacionAgrupamiento, true, "radId");
                Predicate<MarRadicaciones> pred = x -> !x.getRadId().equals(radicacionSel.getRadId());
                radicacionesEscrituras = rads.stream().filter(pred).collect(Collectors.toList());
                if (!radicacionesEscrituras.isEmpty()) {
                    radicacionEscrituraSel = radicacionesEscrituras.get(0);
                }
            } catch (Exception e) {
                String msj = "No se pueden obtener las otras escrituras asociadas al proceso, causado por : " + e;
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Escrituras no encontradas", msj, true, false);
                logger.error(msj, e);
            }
        }
    }

    /**
     * Crea una nueva radicación asociada con la actual.
     */
    public void agregarNuevaRadicacionEscritura() {
        radicacionEscrituraSel = new MarRadicaciones();
        MarEscrituras escrituraNueva = new MarEscrituras();
        escrituraNueva.setEscFecha(new Date());
        radicacionEscrituraSel = new MarRadicaciones();
        radicacionEscrituraSel.setEscId(escrituraNueva);
        radicacionEscrituraSel.setNotId(usuarioSesion.getNotId());
        radicacionEscrituraSel.setRadEstado("A");
        radicacionEscrituraSel.setRadTurno(radicacionSel.getRadTurno());
        radicacionEscrituraSel.setRaaId(radicacionAgrupamiento);
    }

    /**
     * Adjunta un archivo pdf para la escritura adicional
     *
     * @param event
     */
    public void adjuntarEscrituraAdicional(FileUploadEvent event) {
        try {
            CDFFileUploader fileUploader = CDFFileUploader.create(true);
            MarArchivos archivo;
            String fileName = event.getFile().getFileName();
            byte[] bytes = IOUtils.toByteArray(event.getFile().getInputstream());
            Long size = event.getFile().getSize();
            String mimeType = event.getFile().getContentType();
            if (FileUtilidades.invalidFile(mimeType)) {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Upload.!", "Este tipo de archivo no es permitido por la aplicación", true, false);
                return;
            }
            boolean visibleCDN = false;
            MarPuntosMontajes puntoMontaje;
            puntoMontaje = (MarPuntosMontajes) genericDAOBean.findByColumn(MarPuntosMontajes.class, "pmoNombre", "Principal");
            if (puntoMontaje == null) {
                String msj = "No hay un punto de montaje de nombre 'Principal', por favor configúrelo";
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Upload.!", msj, true, false);
                throw new Exception(msj);
            }
            archivo = fileUploader.saveFile(puntoMontaje, fileName, bytes, size, mimeType, usuarioSesion.getUsuLogin(), visibleCDN);
            radicacionEscrituraSel.getEscId().setArcId(archivo);
            PrimeFacesContext.execute("PF('fileUploadDialogMore').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Archivo no es válido", e.getLocalizedMessage(), true, false);
            logger.error("No se puede cargar el archivo, causado por: " + e.getLocalizedMessage(), e);
        }
    }

    /**
     * Agrega la escritura al agrupamiento, si ya existe, la modifica.
     */
    public void guardarEscrituraAdicional() {
        if (radicacionEscrituraSel.getEscId().getArcId() == null) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Escritura faltante", "Debe cargar la escritura antes de guardar.", true, false);
            return;
        }
        boolean existe = false;
        int i = 0;
        for (MarRadicaciones radicacionEscritura : radicacionesEscrituras) {
            if (radicacionEscritura.getEscId().getArcId().getArcId().equals(radicacionEscrituraSel.getEscId().getArcId().getArcId())) {
                existe = true;
                radicacionesEscrituras.set(i, radicacionEscrituraSel);
            }
            i++;
        }
        if (!existe) {
            radicacionesEscrituras.add(radicacionEscrituraSel);
        }
        PrimeFacesContext.execute("PF('dialogNuevaEscritura').hide()");
    }
    
    /**
     * Ejecuta el modal de confirmación para colocar la radicación como prioridad.
     */
    public void confirmarPrioridad(){
        if(esPrioridad){
            esPrioridad = false;
            PrimeFacesContext.execute("PF('dialogConfTurno').show()");
        }else{
            if (esGobernacion().equals("S")) {
                radicacionSel.setPriId(prioridadGobernacion);
            }else{
                radicacionSel.setPriId(null);
            }
        }
    }
    
    /**
     * Coloca la prioridad para la radicación.
     */
    public void colocarPrioridad(){
        esPrioridad = true;
        radicacionSel.setPriId(prioridadDiscapacidad);
    }

    public List<MarRadicaciones> getRadicaciones() {
        return radicaciones;
    }

    public void setRadicaciones(List<MarRadicaciones> radicaciones) {
        this.radicaciones = radicaciones;
    }

    public MarRadicaciones getRadicacionSel() {
        return radicacionSel;
    }

    public void setRadicacionSel(MarRadicaciones radicacionSel) {
        this.radicacionSel = radicacionSel;
    }

    public MarRadicaciones getRadicacionSeleccion() {
        return radicacionSeleccion;
    }

    public void setRadicacionSeleccion(MarRadicaciones radicacionSeleccion) {
        this.radicacionSeleccion = radicacionSeleccion;
    }

    public List<MarNotarias> getNotarias() {
        return notarias;
    }

    public void setNotarias(List<MarNotarias> notarias) {
        this.notarias = notarias;
    }

    public List<MarTiposDocumentos> getTiposDocumentosOtorga() {
        return tiposDocumentosOtorga;
    }

    public void setTiposDocumentosOtorga(List<MarTiposDocumentos> tiposDocumentosOtorga) {
        this.tiposDocumentosOtorga = tiposDocumentosOtorga;
    }

    public List<MarTiposDocumentos> getTiposDocumentosRecibe() {
        return tiposDocumentosRecibe;
    }

    public void setTiposDocumentosRecibe(List<MarTiposDocumentos> tiposDocumentosRecibe) {
        this.tiposDocumentosRecibe = tiposDocumentosRecibe;
    }

    public String getObservacionesProceso() {
        return observacionesProceso;
    }

    public void setObservacionesProceso(String observacionesProceso) {
        this.observacionesProceso = observacionesProceso;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstados() {
        return radicacionesFasesEstados;
    }

    public void setRadicacionesFasesEstados(List<MarRadicacionesFasesEstados> radicacionesFasesEstados) {
        this.radicacionesFasesEstados = radicacionesFasesEstados;
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

    public List<String> getTurnosActuales() {
        return turnosActuales;
    }

    public void setTurnosActuales(List<String> turnosActuales) {
        this.turnosActuales = turnosActuales;
    }

    public List<MarRadicaciones> getRadicacionesEscrituras() {
        return radicacionesEscrituras;
    }

    public void setRadicacionesEscrituras(List<MarRadicaciones> radicacionesEscrituras) {
        this.radicacionesEscrituras = radicacionesEscrituras;
    }

    public MarRadicaciones getRadicacionEscrituraSel() {
        return radicacionEscrituraSel;
    }

    public void setRadicacionEscrituraSel(MarRadicaciones radicacionEscrituraSel) {
        this.radicacionEscrituraSel = radicacionEscrituraSel;
    }

    public boolean isEsPrioridad() {
        return esPrioridad;
    }

    public void setEsPrioridad(boolean esPrioridad) {
        this.esPrioridad = esPrioridad;
    }

    
}
