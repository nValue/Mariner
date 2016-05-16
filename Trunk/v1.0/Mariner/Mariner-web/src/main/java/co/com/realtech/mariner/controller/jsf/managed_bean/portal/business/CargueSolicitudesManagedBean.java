package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.model.entity.MarEscrituras;
import co.com.realtech.mariner.model.entity.MarFasesEstados;
import co.com.realtech.mariner.model.entity.MarNotarias;
import co.com.realtech.mariner.model.entity.MarPuntosMontajes;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.cdf.CDFFileUploader;
import co.com.realtech.mariner.util.consecutives.NumeracionesManager;
import co.com.realtech.mariner.util.io.file.FileUtilidades;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import co.com.realtech.mariner.util.session.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

/**
 * ManagedBean encargado de la pantalla de cargue de solicitudes.
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class CargueSolicitudesManagedBean implements Serializable{

    @EJB(beanName = "GenericDAOBean")
    private GenericDAOBeanLocal genericDAOBean;
    
    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;
    
    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;
    
    private List<MarRadicaciones> radicaciones;
    private MarRadicaciones radicacionSel;
    private MarRadicaciones radicacionSeleccion;
    private MarUsuarios usuarioActual;
    private List<MarNotarias> notarias;
    
    private List<MarTiposDocumentos> tiposDocumentosOtorga;
    private List<MarTiposDocumentos> tiposDocumentosRecibe;
    
    private MarFasesEstados faseEstadoPendiente;
    private String observacionesProceso;
    
    private AuditSessionUtils auditSessionUtils;
    private Logger logger;
    
    @PostConstruct
    public void init(){
        auditSessionUtils = AuditSessionUtils.create();
        usuarioActual = (MarUsuarios) SessionUtils.obtenerValorGeneric("marineruser");
        logger = Logger.getLogger(CargueSolicitudesManagedBean.class);
        obtenerNotarias();
        obtenerTiposDocumentos();
        obtenerFasesEstados();
    }
    
    /**
     * Obtiene las fases estados para ser asignada a la radicación.
     */
    public void obtenerFasesEstados(){
        try {
            faseEstadoPendiente = (MarFasesEstados)genericDAOBean.findByColumn(MarFasesEstados.class, "fesCodigo", "I-P");    
            if(faseEstadoPendiente == null){
                throw new Exception("No hay parametrizada una fase-estado con el código I-P");
            }
        } catch (Exception e) {
            logger.error("No se pueden obtener las fases estados, debido a " + e, e);
        }
    }
    
    /**
     * Obtiene los tipos de documento para los involucrados.
     */
    public void obtenerTiposDocumentos(){
        try {
            tiposDocumentosOtorga = (List<MarTiposDocumentos>)genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre");
            tiposDocumentosRecibe = (List<MarTiposDocumentos>)genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre");
        } catch (Exception e) {
            logger.error("Error obteniendo los tipos de documento, causado por " +e, e);
        }
    }
    
    /**
     * Método de autocomplete para buscar las radicaciones.
     * @param filtro
     * @return 
     */
    public List<MarRadicaciones> completeRadicaciones(String filtro){
        List<MarRadicaciones> radFiltradas = new ArrayList<>();
        System.out.println("filtro = " + filtro);
        try {
            radFiltradas = radicacionesDAOBean.obtenerRadicacionesPorFiltro(filtro);
            System.out.println("radFiltradas = " + radFiltradas);
        } catch (Exception e) {
            logger.error("No se puede realizar el filtro de radicaciones, debido a " + e, e);
        }
        return radFiltradas;
    }
    
    /**
     * Coloca la radicación en la variable radicacionSel para ser actualizada.
     */
    public void seleccionarRadicacion(){
        try {
            radicacionSel = radicacionSeleccion;
        } catch (Exception e) {
            logger.error("Error seleccionando la radicación, causado por " + e, e);
        }
    }
    
    /**
     * Obtiene todas las notarias parametrizadas para seleccionar la adecuada.
     */
    public void obtenerNotarias(){
        try {
            notarias = (List<MarNotarias>)genericDAOBean.findAllByColumn(MarNotarias.class, "notEstado", "A", true, "notNombre");
        } catch (Exception e) {
            logger.error("Error al obtener las notarias, causado por " + e, e);
        }
        
    }
    
    /**
     * Crea una nueva radicación vacía para el llenado de la información necesaria.
     */
    public void solicitarRadicacion(){
        try {
            MarEscrituras escrituraNueva = new MarEscrituras();
            escrituraNueva.setEscFecha(new Date());
            radicacionSel = new MarRadicaciones();
            radicacionSel.setEscId(escrituraNueva);
            radicacionSel.setNotId(notarias.get(0));
            radicacionSel.setTdcIdReceptor(tiposDocumentosRecibe.get(0));
            radicacionSel.setTdcIdOtorgante(tiposDocumentosOtorga.get(0));
            radicacionSel.setRadEstado("Y");
            observacionesProceso = "";
        } catch (Exception e) {
            logger.error("Error creando nueva Radicación, causado por " + e, e);
        }
    }
    
    
    /**
     * Evento necesario para el cargue de archivos
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
            puntoMontaje = (MarPuntosMontajes)genericDAOBean.findByColumn(MarPuntosMontajes.class, "pmoNombre", "Principal");
            if(puntoMontaje == null){
                String msj = "No hay un punto de montaje de nombre 'Principal', por favor configúrelo";
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Upload.!", msj, true, false);
                throw new Exception(msj);
            }
            archivo = fileUploader.saveFile(puntoMontaje, fileName, bytes, size, mimeType, usuarioActual.getUsuLogin(), visibleCDN);
            radicacionSel.getEscId().setArcId(archivo);
            PrimeFacesContext.execute("PF('fileUploadDialog').hide();");
        } catch (Exception e) {
            logger.error("Error al subir el archivo al repositorio, causado por " + e, e);
        }
    }
    
    /**
     * Guarda la radicación actual.
     */
    public void guardarRadicacion(){
        try {
            MarEscrituras escrituraActual = radicacionSel.getEscId();
            if(escrituraActual.getArcId() == null){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Documento faltante", "Necesita adjuntar la escritura para guardar el proceso", true, false);
                return;
            }
            //Se guarda la escritura primero.
            auditSessionUtils.setAuditReflectedValues(escrituraActual);
            if(escrituraActual.getEscId() == null){
                genericDAOBean.save(escrituraActual);
            }else{
                genericDAOBean.merge(escrituraActual);
            }
            
            //Luego se guarda la radicación usando la numeración correspondiente
            auditSessionUtils.setAuditReflectedValues(radicacionSel);
            if(radicacionSel.getRadId() == null){
                NumeracionesManager nm = new NumeracionesManager();
                String siguienteNum = nm.loadNextConsecutive("RAD01");
                radicacionSel.setRadNumero(siguienteNum);
                radicacionSel.setRadFecha(new Date());
                genericDAOBean.save(radicacionSel);
            }else{
                genericDAOBean.merge(radicacionSel);
            }
            
            //Por último se crea la fase-estado si no existe, sino se actualiza
            MarRadicacionesFasesEstados rfes = radicFasesEstadosDAOBean.obtenerRadicFaseEstDeRadyFase(radicacionSel, "I-P");
            if(rfes == null){
                rfes = new MarRadicacionesFasesEstados();
            }
            rfes.setFesId(faseEstadoPendiente);
            rfes.setRadId(radicacionSel);
            rfes.setRfeEstado("A");
            rfes.setUsuId(usuarioActual);
            rfes.setRfeEstadoAprobacion("A");
            rfes.setRfeFechaInicio(new Date());
            rfes.setRfeObservaciones(observacionesProceso);
            auditSessionUtils.setAuditReflectedValues(rfes);
            if(rfes.getRfeId() == null){
                genericDAOBean.save(rfes);
            }else{
                genericDAOBean.merge(rfes);
            }
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso realizado", "Radicación guardada exitosamente", true, false);
        } catch (Exception e) {
            logger.error("Error guardando la radicación, causado por " + e, e);
        }
    }
    
    /**
     * Cancela la radicación actual.
     */
    public void cancelarRadicacion(){
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
    
    
    
}
