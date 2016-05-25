package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.notarias.NotariasDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.model.entity.MarEscrituras;
import co.com.realtech.mariner.model.entity.MarFasesEstados;
import co.com.realtech.mariner.model.entity.MarNotarias;
import co.com.realtech.mariner.model.entity.MarPuntosMontajes;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.util.cdf.CDFFileUploader;
import co.com.realtech.mariner.util.consecutives.NumeracionesManager;
import co.com.realtech.mariner.util.io.file.FileUtilidades;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstProcesadas;
    private MarRadicacionesFasesEstados radicacionFaseEstProcesadaSel;

    private MarFasesEstados faseEstadoPendiente;
    private String observacionesProceso;

    private Date fechaFiltroInic;
    private Date fechaFiltroFin;

    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;

    @Override
    public void init() {
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        obtenerNotarias();
        obtenerTiposDocumentos();
        obtenerFasesEstados();
        obtenerRadicacionesPendientes();
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
            radicacionesFasesEstProcesadas = radicFasesEstadosDAOBean.obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(usuarioSesion, null, fechaFiltroInic, fechaFiltroFin);
            if (!radicacionesFasesEstProcesadas.isEmpty()) {
                radicacionFaseEstProcesadaSel = radicacionesFasesEstProcesadas.get(0);
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
     * Pregunta si la radicacion actual es un rechazo (Sirve para colocar el (R) en la lista de los pendientes)
     * @param radSel
     * @return 
     */
    public String esRechazo(MarRadicaciones radSel){
        String valor = radSel.getRadNumero();
        try {
            MarRadicacionesFasesEstados ultimo = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radSel);
            if(ultimo.getFesId().getFesCodigo().equals("I-R")){
                valor = "(R) " + valor;
            }
        } catch (Exception e) {
        }
        return valor;
    }

    /**
     * Crea una nueva radicación vacía para el llenado de la información
     * necesaria.
     */
    public void solicitarRadicacion() {
        try {
            radicaciones = null;
            MarEscrituras escrituraNueva = new MarEscrituras();
            escrituraNueva.setEscFecha(new Date());
            radicacionSel = new MarRadicaciones();
            radicacionSel.setEscId(escrituraNueva);
            radicacionSel.setNotId(notarias.get(0));
            //radicacionSel.setTdcIdReceptor(tiposDocumentosRecibe.get(0));
            //radicacionSel.setTdcIdOtorgante(tiposDocumentosOtorga.get(0));
            radicacionSel.setRadEstado("A");
            observacionesProceso = "";
            radicacionesFasesEstados = null;
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
            logger.error("Error al subir el archivo al repositorio, causado por " + e, e);
        }
    }

    /**
     * Guarda la radicación actual.
     */
    public void guardarRadicacion() {
        try {
            MarEscrituras escrituraActual = radicacionSel.getEscId();
            if (escrituraActual.getArcId() == null) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Documento faltante", "Necesita adjuntar la escritura para guardar el proceso", true, false);
                return;
            }

            //Valida que la radicación actual no haya sido cambiada de fase para guardar los cambios.
            if (radicacionSel.getRadId() != null) {
                MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionSel);
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

            //Luego se guarda la radicación usando la numeración correspondiente
            auditSessionUtils.setAuditReflectedValues(radicacionSel);
            if (radicacionSel.getRadId() == null) {
                NumeracionesManager nm = new NumeracionesManager();
                String siguienteNum = nm.loadNextConsecutiveRad("RAD01", radicacionSel);
                radicacionSel.setRadNumero(siguienteNum);
                radicacionSel.setRadFecha(new Date());
                genericDAOBean.save(radicacionSel);
            } else {
                genericDAOBean.merge(radicacionSel);
            }

            //Si la fase corresponde a un rechazo anterior o es nueva, crea el estado I-P
            BigDecimal dbSalida;
            Integer salida = -999;
            //Si las fases-estados está vacia, es una nueva radicación y se crea el estado I-P
            if (radicacionesFasesEstados == null || radicacionesFasesEstados.isEmpty()) {
                dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionSel.getRadId(),
                        "I-P", "A", usuarioSesion.getUsuId(), observacionesProceso, null);
                salida = dbSalida.intValue();
            } else {
                MarRadicacionesFasesEstados rfes = radicacionesFasesEstados.get(radicacionesFasesEstados.size() - 1);
                //Si la última fase-estado es de rechazo, vuelve a crear el estado I-P
                if (rfes.getFesId().getFesCodigo().equals("I-R")) {
                    dbSalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionSel.getRadId(),
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
            MarRadicaciones radicacionGuardada = radicacionSel;
            obtenerRadicacionesPendientes();
            radicacionSel = radicacionGuardada;
            obtenerFasesEstadosDeRadicacion();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso realizado", "Se ha creado una nueva radicación con número: " + radicacionGuardada.getRadNumero(), true, false);
        } catch (Exception e) {
            logger.error("Error guardando la radicación, causado por " + e, e);
        }
    }

    /**
     * Cancela la radicación actual.
     */
    public void cancelarRadicacion() {
        obtenerRadicacionesPendientes();
        radicacionSel = null;
    }

    public CargueSolicitudesManagedBean() {
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

}
