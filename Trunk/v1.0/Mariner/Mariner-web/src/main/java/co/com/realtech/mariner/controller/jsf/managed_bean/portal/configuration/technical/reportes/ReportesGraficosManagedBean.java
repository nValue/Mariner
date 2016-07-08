package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reportes;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.reportes.ReportesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.model.entity.MarReportesGraficos;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class ReportesGraficosManagedBean extends GenericManagedBean{
    
    @EJB(beanName = "ReportesDAOBean")
    private ReportesDAOBeanLocal reportesDAOBeanLocal;

    private List<MarReportes> reportes;
    private MarReportes reporteSel;
    
    private List<MarReportesGraficos> reportesGraficos;
    private MarReportesGraficos reporteGraficoSel;
    
    @Override
    public void init() {
        obtenerReportesGraficos();
        obtenerReportes();
    }
    
    /**
     * Obtiene los gráficos de reporte parametrizados en el sistema.
     */
    public void obtenerReportesGraficos(){
        try {
            reportesGraficos = (List<MarReportesGraficos>)genericDAOBean.loadAllForEntity(MarReportesGraficos.class, "rgrId");
            if(!reportesGraficos.isEmpty()){
                reporteGraficoSel = reportesGraficos.get(0);
            }
        } catch (Exception e) {
            logger.error("No se pueden obtener los gráficos de reportes, causado por: " + e, e);
        }
    }
    
    /**
     * Obtiene los reportes disponibles para asignar.
     */
    public void obtenerReportes(){
        try {
            reportes = reportesDAOBeanLocal.obtenerReportesDeCodigosTipo("'GRAFICOS'");
            if(!reportes.isEmpty()){
                reporteSel = reportes.get(0);
            }
        } catch (Exception e) {
            logger.error("No se pueden obtener los reportes disponibles, causado por: " + e, e);
        }
    }
    
    /**
     * Crea un nuevo gráfico de reporte.
     */
    public void nuevoGraficoReporte(){
        reporteGraficoSel = new MarReportesGraficos();
        obtenerReportes();
    }
    
    /**
     * Guarda el gráfico de reporte actual.
     */
    public void guardarGraficoReporte(){
        try {
            reporteGraficoSel.setRepId(reporteSel);
            auditSessionUtils.setAuditReflectedValues(reporteGraficoSel);
            if (reporteGraficoSel.getRgrId() == null) {
                genericDAOBean.save(reporteGraficoSel);
            }else{
                genericDAOBean.merge(reporteGraficoSel);
            }
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Gráfico de reporte almacenado correctamente", true, false);
            PrimeFacesContext.execute("PF('graficoReporteDialog').hide();");
            obtenerReportesGraficos();
        } catch (Exception e) {
            logger.error("No se puede almacenar este tipo de reporte, causado por " + e, e);
        }
    }
    
    /**
     * Elimina el tipo de reporte seleccionado.
     */
    public void eliminarGraficoReporte(){
        try {
            genericDAOBean.delete(reporteGraficoSel, reporteGraficoSel.getRgrId());
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Gráfico de reporte eliminado correctamente", true, false);
            obtenerReportesGraficos();
        } catch (Exception e) {
            logger.error("No se puede eliminar este tipo de reporte, causado por " + e, e);
        }
    }

    public List<MarReportesGraficos> getReportesGraficos() {
        return reportesGraficos;
    }

    public void setReportesGraficos(List<MarReportesGraficos> reportesGraficos) {
        this.reportesGraficos = reportesGraficos;
    }

    public MarReportesGraficos getReporteGraficoSel() {
        return reporteGraficoSel;
    }

    public void setReporteGraficoSel(MarReportesGraficos reporteGraficoSel) {
        this.reporteGraficoSel = reporteGraficoSel;
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
    
    
    
}
