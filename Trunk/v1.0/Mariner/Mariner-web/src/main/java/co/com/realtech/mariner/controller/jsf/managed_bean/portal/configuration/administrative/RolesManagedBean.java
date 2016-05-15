package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.administrative;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.modulo.ModulosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarRoles;
import co.com.realtech.mariner.model.entity.MarRolesModulos;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF administracion de roles
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK 1.0
 */
@ManagedBean
@ViewScoped
public class RolesManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "ModulosDAOBean")
    protected ModulosDAOBeanLocal modulosDAOBean;

    private MarRoles rol;
    private List<MarRoles> roles;
    private MarModulos modulo;
    private List<MarModulos> modulos;
    private MarRolesModulos rolModulo;
    private List<MarRolesModulos> rolesModulos;

    @Override
    public void init() {
        try {
            setRoles((List<MarRoles>) genericDAOBean.loadAllForEntity(MarRoles.class, "rolNombre asc"));
            setModulos(modulosDAOBean.loadAccesibleModules());
            if (!getRoles().isEmpty()) {
                setRol(getRoles().get(0));
                filtrarRolesModulos();
            }
        } catch (Exception e) {
            logger.error("Error inicializando RolesManagedBean, causado por " + e);
        }
    }

    /**
     * Filtrado de roles modulos.
     */
    public void filtrarRolesModulos() {
        try {
            setRolesModulos((List<MarRolesModulos>) genericDAOBean.findAllByColumn(MarRolesModulos.class, "rolId", getRol(), true, "modId.modNombre asc"));
        } catch (Exception e) {
            logger.error("Error filtrando modulos vinculados al rol, causado por " + e);
        }
    }

    /**
     * Creacion de nuevo rol
     */
    public void agregarRol() {
        setRol(new MarRoles());
    }

    /**
     * Borrado de Rol.
     */
    public void eliminarRol() {
        try {
            genericDAOBean.delete(getRol(), getRol().getRolId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Rol eliminada correctamente en la base de datos", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo eliminar el Rol seleccionada, por favor intente nuevamente", true, false);
            logger.error("Error borrando rol, causado por " + e);
        }
    }

    /**
     * Borrado de vinculo del rol con el modulo
     */
    public void eliminarRolModulo() {
        try {
            genericDAOBean.delete(getRolModulo(), getRolModulo().getRmoId());
            filtrarRolesModulos();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Vinculo de Modulo con Rol eliminado correctamente", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo eliminar el vinculo seleccionado, por favor intente nuevamente", true, false);
            logger.error("Error borrando rol, causado por " + e);
        }
    }

    /**
     * Guardado informacion del rol
     */
    public void guardarRol() {
        try {
            auditSessionUtils.setAuditReflectedValues(getRol());

            if (getRol().getRolId() == null) {
                genericDAOBean.save(getRol());
            } else {
                genericDAOBean.merge(getRol());
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Rol almacenada correctamente en la base de datos", true, false);
            PrimeFacesContext.execute("PF('dialogRol').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se ha podido almacenar el Rol, por favor intente nuevamente", true, false);
            logger.error("Error guardando rol, causado por " + e);
        }
    }

    /**
     * Vincular Modulo al Rol.
     */
    public void vincularModulo() {
        try {
            boolean validacion = true;

            for (MarRolesModulos rolM : getRolesModulos()) {
                if (rolM.getModId().equals(getModulo())) {
                    validacion = false;
                    break;
                }
            }

            if (validacion) {
                MarRolesModulos rolMo = new MarRolesModulos();
                rolMo.setModId(getModulo());
                rolMo.setRolId(getRol());
                auditSessionUtils.setAuditReflectedValues(rolMo);
                genericDAOBean.save(rolMo);
                filtrarRolesModulos();
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Modulo vinculado correctamente con el Rol seleccionado", true, false);
                PrimeFacesContext.execute("PF('dialogModulos').hide();");
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Pulsate, "Notificacion", "Lo sentimos pero el Modulo seleccionado ya se encuentra vinculado al Rol.", true, false);
            }
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se ha podido vincular el Rol con el Modulo, por favor intente nuevamente", true, false);
            logger.error("Error vinculando rol con modulo, causado por " + e);
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

    public MarModulos getModulo() {
        return modulo;
    }

    public void setModulo(MarModulos modulo) {
        this.modulo = modulo;
    }

    public List<MarModulos> getModulos() {
        return modulos;
    }

    public void setModulos(List<MarModulos> modulos) {
        this.modulos = modulos;
    }

    public MarRolesModulos getRolModulo() {
        return rolModulo;
    }

    public void setRolModulo(MarRolesModulos rolModulo) {
        this.rolModulo = rolModulo;
    }

    public List<MarRolesModulos> getRolesModulos() {
        return rolesModulos;
    }

    public void setRolesModulos(List<MarRolesModulos> rolesModulos) {
        this.rolesModulos = rolesModulos;
    }

}
