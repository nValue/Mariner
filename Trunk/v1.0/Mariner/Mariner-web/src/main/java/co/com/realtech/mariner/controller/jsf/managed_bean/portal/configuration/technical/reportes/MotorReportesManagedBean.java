package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reportes;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.model.entity.MarPuntosMontajes;
import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.model.entity.MarReportesTipos;
import co.com.realtech.mariner.model.entity.generic.ClaveValor;
import co.com.realtech.mariner.util.cdf.CDFFileUploader;
import co.com.realtech.mariner.util.files.FileUtils;
import co.com.realtech.mariner.util.io.file.ZipUtils;
import co.com.realtech.mariner.util.jasper.JasperReportsGenerator;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class MotorReportesManagedBean extends GenericManagedBean {
    
    
    private List<MarReportes> reportes;
    private MarReportes reporteSel;
    
    private List<MarReportesTipos> reportesTipos;
    private MarReportesTipos reporteTipoSel;
    
    private MarPuntosMontajes puntoMontaje;
    
    private List<ClaveValor> parametros;
    private ClaveValor parametroSel;
    

    @Override
    public void init() {
        parametros = new ArrayList<>();
        obtenerReportesTipos();
        obtenerPuntoMontaje();
    }
    
    /**
     * Obtiene todos los tipos de reportes creados.
     */
    public void obtenerReportesTipos(){
        try {
            reportesTipos = (List<MarReportesTipos>)genericDAOBean.loadAllForEntity(MarReportesTipos.class, "rtiNombre");
            if(!reportesTipos.isEmpty()){
                reporteTipoSel = reportesTipos.get(0);
                obtenerReportes();
            }
        } catch (Exception e) {
            logger.error("Error trayendo los tipos de reportes, causado por " + e, e);
        }
    }
    
    /**
     * Obtiene el punto de montaje por defecto para los reportes.
     */
    public void obtenerPuntoMontaje(){
        try {
            puntoMontaje = (MarPuntosMontajes)genericDAOBean.findByColumn(MarPuntosMontajes.class, "pmoNombre", "Reportes");
        } catch (Exception e) {
            logger.error("Error al traer el punto de montaje, causado por " + e, e);
        }
    }
    
    /**
     * Obtiene todos los reportes con sus históricos para un tipo de reporte en particular.
     */
    public void obtenerReportes(){
        try {
            reportes = (List<MarReportes>)genericDAOBean.findAllByColumn(MarReportes.class, "rtiId", reporteTipoSel);
            if(!reportes.isEmpty()){
                reporteSel = reportes.get(0);
            }
        } catch (Exception e) {
            logger.error("Error trayendo reportes, causado por " + e, e);
        }
    }
    
    /**
     * Crea un nuevo reporte para el motor principal.
     */
    public void crearNuevoReporte(){
        reporteSel = new MarReportes();
        reporteSel.setRtiId(reporteTipoSel);
    }
    
    /**
     * Elimina un reporte seleccionado con su configuración incluida.
     */
    public void eliminarReporte(){
        try {
            if(reporteSel.getArcIdReporte() != null){
                genericDAOBean.delete(reporteSel.getArcIdReporte(), reporteSel.getArcIdReporte().getArcId());
            }
            genericDAOBean.delete(reporteSel, reporteSel.getRepId());
            obtenerReportes();
        } catch (Exception e) {
            logger.error("Error eliminando el reporte, causado por " + e, e);
        }
    }
    
    /**
     * Almacena un nueva nueva configuración de reporte.
     */
    public void guardarReporte(){
        try {
            String[] palabrasProhibidas = {"DELETE","DROP","UPDATE","INSERT"};
            String sqlSeguridad = reporteSel.getRepConsulta();
            for (String palabrasProhibida : palabrasProhibidas) {
                if(sqlSeguridad.toUpperCase().contains(palabrasProhibida)){
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Permiso denegado", "No puede guardar la consulta debido a que tiene palabras restrigidas por motivos de seguridad", true, false);
                    return;
                }
            }
            if(reporteSel.getRepConsulta().contains(""))
            auditSessionUtils.setAuditReflectedValues(reporteSel);
            if(reporteSel.getRepEstado().equals("A")){
                //reportesDAOBean.disableAllReportsFromType(reporteTipoSel);
                if(reporteSel.getArcIdReporte() != null){
                    descomprimirArchivo(reporteSel.getArcIdReporte());
                }
            }
            if(reporteSel.getRepId() == null){
                genericDAOBean.save(reporteSel);
            }else{
                genericDAOBean.merge(reporteSel);
            }
            obtenerReportes();
            PrimeFacesContext.execute("PF('reporteDialog').hide();");
        } catch (Exception e) {
            logger.error("Error al guardar la nueva configuración de reporte, causado por " + e, e);
        }
    }
    
    /**
     * Descomprime un archivo en la carpeta actual donde se encuentra ubicado
     * @param archivo 
     */
    public void descomprimirArchivo(MarArchivos archivo){
        String strPath = puntoMontaje.getPmoPath() + archivo.getArcPathInterno();
        String strArch = strPath + archivo.getArcHash() + "." + archivo.getArcExtension();
        try {
            ZipUtils.unzip(strPath, strArch);
        } catch (Exception e) {
            logger.error("Error descomprimiendo archivo, debido a : " + e ,e );
        }
    }
    
    /**
     * Cargue del reporte como ZIP
     * @param event 
     */
    public void handleFileUploadReport(FileUploadEvent event) {
        try {
            CDFFileUploader fileUploader = CDFFileUploader.create(true);
            MarArchivos archivo;
            String fileName = event.getFile().getFileName();
            byte[] bytes = IOUtils.toByteArray(event.getFile().getInputstream());
            Long size = event.getFile().getSize();
            String mimeType = event.getFile().getContentType();
            if(FileUtils.invalidFile(mimeType)){
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Upload.!", "Este tipo de archivo no es permitido por la aplicación", true, false);
                return;
            }
            boolean visibleCDN = false;
            archivo = fileUploader.saveFile(puntoMontaje, fileName, bytes, size, mimeType, usuarioSesion.getUsuLogin(), visibleCDN);
            reporteSel.setArcIdReporte(archivo);
            genericDAOBean.merge(reporteSel);
            PrimeFacesContext.execute("PF('fileUploadDialog').hide();");
        } catch (Exception e) {
            logger.error("Error al subir el reporte causado por " + e, e);
        }
    }
    
    /**
     * Elimina el parámetro actual para la ejecución del reporte.
     */
    public void eliminarParametro(){
        for (ClaveValor parametro : parametros) {
            if(parametro.getClave().equals(parametroSel.getClave())){
                parametros.remove(parametro);
            }
        }
    }
    
    /**
     * Agrega el parámetro actual para la ejecución del reporte.
     */
    public void agregarParametro(){
        for (ClaveValor parametro : parametros) {
            if(parametro.getClave().equals(parametroSel.getClave())){
                return;
            }
        }
        parametros.add(parametroSel);
    }
    
    /**
     * Ejecutar Reporte Jasper.
     */
    public void ejecutarReporte() {
        try {
            JasperReportsGenerator gr = JasperReportsGenerator.create();
            HashMap<String, Object> params = new HashMap<>();
            for (ClaveValor mo : parametros) {
                if(mo.getClave().contains("_ID")){
                    params.put(mo.getClave(),new BigDecimal(mo.getValor().toString()));
                }
                else{
                    params.put(mo.getClave(), mo.getValor());
                }
            }
            gr.executeReport(FacesContext.getCurrentInstance(), params, reporteSel,null);
            PrimeFacesContext.execute("PF('ejecucionReporteDialog').hide();");
        } catch (Exception e) {
            logger.error("No se puede ejecutar el reporte, causado por " + e, e);
        }
    }

    public List<MarReportes> getReportes() {
        return reportes;
    }

    public void setReportes(List<MarReportes> reportes) {
        this.reportes = reportes;
    }

    public MarReportes getReporteSel() {
        return reporteSel;
    }

    public void setReporteSel(MarReportes reporteSel) {
        this.reporteSel = reporteSel;
    }

    public List<MarReportesTipos> getReportesTipos() {
        return reportesTipos;
    }

    public void setReportesTipos(List<MarReportesTipos> reportesTipos) {
        this.reportesTipos = reportesTipos;
    }

    public MarReportesTipos getReporteTipoSel() {
        return reporteTipoSel;
    }

    public void setReporteTipoSel(MarReportesTipos reporteTipoSel) {
        this.reporteTipoSel = reporteTipoSel;
    }

    public List<ClaveValor> getParametros() {
        return parametros;
    }

    public void setParametros(List<ClaveValor> parametros) {
        this.parametros = parametros;
    }

    public ClaveValor getParametroSel() {
        return parametroSel;
    }

    public void setParametroSel(ClaveValor parametroSel) {
        this.parametroSel = parametroSel;
    }
    
    
    
}
