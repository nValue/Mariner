package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reportes;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.reportes.ReportesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.model.entity.MarReportesParametros;
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
public class ReportesParametrosManagedBean extends GenericManagedBean{
    
    @EJB(beanName = "ReportesDAOBean")
    private ReportesDAOBeanLocal reportesDAOBeanLocal;

    private List<MarReportes> reportes;
    private MarReportes reporteSel;
    private List<MarReportesParametros> reportesParametros;
    private MarReportesParametros reporteParametrosSel;
    
    @Override
    public void init(){
        obtenerReportes();
    }
    
    /**
     * Obtiene todos los reportes de la aplicación.
     */
    public void obtenerReportes(){
        try {
            reportes = reportesDAOBeanLocal.obtenerReportesDeCodigosTipo("'ESPECIFICOS'");
            if(!reportes.isEmpty()){
                reporteSel = reportes.get(0);
                obtenerParametrosReporte();
            }
        } catch (Exception e) {
            logger.error("No se pueden obtener todos los reportes, debido a : " + e, e);
        }   
    }
    
    /**
     * De acuerdo a un reporte seleccionado, obtiene toda la configuración del reporte, si la hay.
     */
    public void obtenerParametrosReporte(){
        try {
            reportesParametros = (List<MarReportesParametros>)genericDAOBean.findAllByColumn(MarReportesParametros.class, "repId", reporteSel);
            if(!reportesParametros.isEmpty()){
                reporteParametrosSel = reportesParametros.get(0);
            }else{
                reporteParametrosSel = new MarReportesParametros();
                reporteParametrosSel.setRepId(reporteSel);
            }
        } catch (Exception e) {
            logger.error("No se extraer la configuración del reporte, debido a : " + e, e);
        }
    }
    
    /**
     * Elimina un parámetro seleccionado.
     */
    public void eliminarParametro(){
        try {
            genericDAOBean.delete(reporteParametrosSel, reporteParametrosSel.getRpaId());
            obtenerParametrosReporte();
        } catch (Exception e) {
            logger.error("Error al eliminar el parámetro, debido a : " + e, e);
        }
    }
    
    /**
     * Crea un nuevo parámetro.
     */
    public void nuevoParametro(){
        reporteParametrosSel = new MarReportesParametros();
        reporteParametrosSel.setRepId(reporteSel);
        reporteParametrosSel.setRpaRequerido("S");
        System.out.println("reporteParametrosSel.getRppRequerido() = " + reporteParametrosSel.getRpaRequerido());
    }
    
    /**
     * Guarda el parámetro actual.
     */
    public void guardarParametro(){
        String[] palabrasProhibidas = {"DELETE", "DROP", "UPDATE", "INSERT"};
        String sqlSeguridad = reporteParametrosSel.getRpaQueryLista();
        for (String palabrasProhibida : palabrasProhibidas) {
            if (sqlSeguridad.toUpperCase().contains(palabrasProhibida)) {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Permiso denegado", "No puede guardar la consulta debido a que tiene palabras restrigidas por motivos de seguridad", true, false);
                return;
            }
        }
        auditSessionUtils.setAuditReflectedValues(reporteParametrosSel);
        try {
            if (reporteParametrosSel.getRpaId() == null) {
                genericDAOBean.save(reporteParametrosSel);
            } else {
                genericDAOBean.merge(reporteParametrosSel);
            }
        obtenerParametrosReporte();
        PrimeFacesContext.execute("PF('widgetParametros').hide();");
        } catch (Exception e) {
            logger.error("No se puede eliminar este parámetro, debido a : " + e , e);
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

    public List<MarReportesParametros> getReportesParametros() {
        return reportesParametros;
    }

    public void setReportesParametros(List<MarReportesParametros> reportesParametros) {
        this.reportesParametros = reportesParametros;
    }

    public MarReportesParametros getReporteParametrosSel() {
        return reporteParametrosSel;
    }

    public void setReporteParametrosSel(MarReportesParametros reporteParametrosSel) {
        this.reporteParametrosSel = reporteParametrosSel;
    }
    
    
    
    
}
