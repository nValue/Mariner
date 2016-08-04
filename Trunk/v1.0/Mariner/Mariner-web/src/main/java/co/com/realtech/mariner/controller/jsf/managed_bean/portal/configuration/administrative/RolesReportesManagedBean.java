package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.administrative;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.reportes.ReportesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.model.entity.MarRoles;
import co.com.realtech.mariner.model.entity.MarRolesReportes;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Configuracion de Reportes asociados a los diferentes roles de la plataforma.
 *
 * @author Fabián Agudelo
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class RolesReportesManagedBean extends GenericManagedBean implements Serializable {
    
    @EJB(beanName = "ReportesDAOBean")
    private ReportesDAOBeanLocal reportesDAOBean;

    private MarRoles rol;
    private List<MarRoles> roles;
    private MarRolesReportes rolReporte;
    private List<MarRolesReportes> rolesReportes;
    private MarReportes reporte;
    private List<MarReportes> reportes;

    @Override
    public void init() {
        try {
            setRoles((List<MarRoles>) genericDAOBean.loadAllForEntity(MarRoles.class, "rolNombre"));
            if (!getRoles().isEmpty()) {
                setRol(getRoles().get(0));
                seleccionarRol();
            }
        } catch (Exception e) {
            logger.error("Error inicializando RolesReportesManagedBean, causado por " + e, e);
        }
    }
    
    /**
     * Obtiene la lista de reportes para ser asinados a un rol.
     */
    public void obtenerReportesDisponibles(){
        try {
            reportes = reportesDAOBean.obtenerReportesDeCodigosTipo("'ESPECIFICOS','GRAFICOS'");
            if(!reportes.isEmpty()){
                reporte = reportes.get(0);
            }
        } catch (Exception e) {
            String msj = "No se pueden obtener los reportes disponibles, causado por : " + e.getMessage();
            logger.error(msj,e);
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", msj,true,false);
        }
        
    }

    /**
     * Evento de seleccion sobre lista de roles.
     */
    public void seleccionarRol() {
        try {
            rolesReportes = (List<MarRolesReportes>) genericDAOBean.findAllByColumn(MarRolesReportes.class, "rolId", rol, true, "repId.repNombre");
            if (!getRolesReportes().isEmpty()) {
                setRolReporte(getRolesReportes().get(0));
            }
            /*
            reportes = reportesDAOBean.obtenerReportesDeCodigosTipo("'ESP_01','EST_01'");
            if (!getReportes().isEmpty()) {
                setReporte(getReportes().get(0));
            }
            setRolesReportes((List<MarRolesReportes>) genericDAOBean.findAllByColumn(MarRolesReportes.class, "rolId", getRol(), true, "repId.repNombre"));
            if (!getRolesReportes().isEmpty()) {
                setRolReporte(getRolesReportes().get(0));
            }*/
        } catch (Exception e) {
            logger.error("Error seleccionando rol, causado por " + e, e);
        }
    }
    /**
     * Vinculo entre reporte y rol seleccionado.
     */
    public void asociarReporteRol(){
        try {
            boolean validacion=false;
            
            for(MarRolesReportes crr:getRolesReportes()){
                if(crr.getRepId().equals(getReporte())){
                    validacion=true;
                }
            }
            if(validacion){
                PrimeFacesPopup.lanzarDialog(Effects.Drop, "Notificacion", "Lo sentimos pero el reporte <b>"+getReporte().getRepNombre()+"</b> ya se encuentra vinculado a rol <b>"+getRol().getRolNombre()+"</b>",true,false);
            }
            else{
                MarRolesReportes nRlRepore = new MarRolesReportes();
                nRlRepore.setRolId(getRol());
                nRlRepore.setRepId(getReporte());
                auditSessionUtils.setAuditReflectedValues(nRlRepore);
                genericDAOBean.save(nRlRepore);
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Reporte <b>"+getReporte().getRepNombre()+"</b> vinculado correctamente al rol <b>"+getRol().getRolNombre()+"</b>",true,false);
                PrimeFacesContext.execute("PF('dialogRolesReportes').hide();");
                seleccionarRol();
            }            
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error de asociacion.", "Lo sentimos pero en este momento no es posible realizar la configuracion.",true,false);
            logger.error("Error asociando rol y reporte, causado por " + e, e);
        }
    }
    /**
     * Borrado de vinculo entre Rol y Reporte.
     */
    public void eliminarRolReporte(){
        try {
            genericDAOBean.delete(getRolReporte(), getRolReporte().getRreId());
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion.", "Asociacion de Rol / Reporte eliminada correctamente.",true,false);
            seleccionarRol();
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error de Borrado.", "Lo sentimos pero en este momento no es posible borrar la configuracion de Rol / Reporte.",true,false);
            logger.error("Error eliminando rol / reporte, causado por " + e, e);
        }
    }

    public MarRoles getRol() {
        return rol;
    }

    public void setRol(MarRoles rol) {
        this.rol = rol;
    }

    public List<MarRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<MarRoles> roles) {
        this.roles = roles;
    }

    public MarRolesReportes getRolReporte() {
        return rolReporte;
    }

    public void setRolReporte(MarRolesReportes rolReporte) {
        this.rolReporte = rolReporte;
    }

    public List<MarRolesReportes> getRolesReportes() {
        return rolesReportes;
    }

    public void setRolesReportes(List<MarRolesReportes> rolesReportes) {
        this.rolesReportes = rolesReportes;
    }

    public MarReportes getReporte() {
        return reporte;
    }

    public void setReporte(MarReportes reporte) {
        this.reporte = reporte;
    }

    public List<MarReportes> getReportes() {
        return reportes;
    }

    public void setReportes(List<MarReportes> reportes) {
        this.reportes = reportes;
    }
    
    

}
