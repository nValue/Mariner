package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reportes;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarReportesTipos;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class ReportesTiposManagedBean extends GenericManagedBean{

    private List<MarReportesTipos> reportesTipos;
    private MarReportesTipos reporteTipoSel;
    
    @ManagedProperty(value="#{motorReportesManagedBean}")
    private MotorReportesManagedBean motorReportesManagedBean;
    
    @Override
    public void init() {
        obtenerReportesTipos();
    }
    
    /**
     * Obtiene los tipos de reporte parametrizados en el sistema.
     */
    public void obtenerReportesTipos(){
        try {
            reportesTipos = (List<MarReportesTipos>)genericDAOBean.loadAllForEntity(MarReportesTipos.class, "rtiId");
            if(!reportesTipos.isEmpty()){
                reporteTipoSel = reportesTipos.get(0);
            }
        } catch (Exception e) {
            logger.error("No se pueden obtener los tipos de reportes, causado por: " + e, e);
        }
    }
    
    /**
     * Crea un nuevo tipo de reporte.
     */
    public void nuevoTipoReporte(){
        reporteTipoSel = new MarReportesTipos();
    }
    
    /**
     * Guarda el tipo de reporte actual.
     */
    public void guardarTipoReporte(){
        try {
            auditSessionUtils.setAuditReflectedValues(reporteTipoSel);
            if (reporteTipoSel.getRtiId() == null) {
                genericDAOBean.save(reporteTipoSel);
            }else{
                genericDAOBean.merge(reporteTipoSel);
            }
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Tipo de reporte almacenado correctamente", true, false);
            PrimeFacesContext.execute("PF('tipoReporteDialog').hide();");
            obtenerReportesTipos();
            motorReportesManagedBean.obtenerReportesTipos();
        } catch (Exception e) {
            logger.error("No se puede almacenar este tipo de reporte, causado por " + e, e);
        }
    }
    
    /**
     * Elimina el tipo de reporte seleccionado.
     */
    public void eliminarTipoReporte(){
        try {
            genericDAOBean.delete(reporteTipoSel, reporteTipoSel.getRtiId());
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Tipo de documento eliminado correctamente", true, false);
            obtenerReportesTipos();
            motorReportesManagedBean.obtenerReportesTipos();
        } catch (Exception e) {
            logger.error("No se puede eliminar este tipo de reporte, causado por " + e, e);
        }
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

    public MotorReportesManagedBean getMotorReportesManagedBean() {
        return motorReportesManagedBean;
    }

    public void setMotorReportesManagedBean(MotorReportesManagedBean motorReportesManagedBean) {
        this.motorReportesManagedBean = motorReportesManagedBean;
    }
    
    
    
}
