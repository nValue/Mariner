package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.administrative;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios.UsuariosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarNotarias;
import co.com.realtech.mariner.model.entity.MarOficinasRegistros;
import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarRoles;
import co.com.realtech.mariner.model.entity.MarRolesUsuarios;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.crypto.CryptoUtils;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de la administración de los usuarios, incluyendo
 * contraseñas y demás.
 *
 * @author fabianagudelo
 * @version 1.0
 * @since JDK1.8.91
 *
 */
@ManagedBean
@ViewScoped
public class UsuariosManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "UsuariosDAOBean")
    private UsuariosDAOBeanLocal usuariosDAOBean;

    private List<MarUsuarios> usuarios;
    private MarUsuarios usuarioSel;
    private MarUsuarios usuarioSeleccion;
    private List<MarTiposDocumentos> tiposDocumentos;
    private List<MarRolesUsuarios> rolesUsuarios;
    private MarRolesUsuarios rolUsuarioSel;
    private MarPersonas personaSel;

    private String claveNueva;
    private String claveNuevaRep;

    private List<MarRoles> roles;
    private MarRoles rolSel;

    private List<MarNotarias> notarias;
    private List<MarOficinasRegistros> oficinas;
    
    private Date fechaInicioRol;
    private Date fechaFinRol;

    @Override
    public void init() {
        usuarioSel = null;
        personaSel = null;
        usuarioSeleccion=null;
        obtenerTiposDocumentos();
        obtenerRoles();
        obtenerOficinas();
        //obtenerNotarias();
        claveNueva = "";
        Calendar cal = Calendar.getInstance();
        fechaInicioRol = cal.getTime();
        cal.add(Calendar.YEAR, 2);
        fechaFinRol = cal.getTime();
    }

    /**
     * Obtener oficinas de registro desplegables.
     */
    public void obtenerOficinas() {
        try {
            setOficinas((List<MarOficinasRegistros>) genericDAOBean.findAllByColumn(MarOficinasRegistros.class, "morEstado", "A", true, "morNombre asc"));
            getOficinas().add(0, null);
        } catch (Exception e) {
            logger.error("Error cargando listas desplegables, causado por :" + e, e);
        }
    }
    
    /**
     * Obtiene las notarías de acuerdo a la oficina registral seleccionada.
     */
    public void obtenerNotarias(){
        try {
            System.out.println("Obteniendo notarias...");
            setNotarias((List<MarNotarias>) genericDAOBean.findAllByColumn(MarNotarias.class, "morId", usuarioSel.getMorId(), true, "notNombre asc"));
            getNotarias().add(0, null);
        } catch (Exception e) {
            logger.error("Error obteniendo las notarías, causado por :" + e, e);
        }
    }

    public void obtenerRoles() {
        try {
            roles = (List<MarRoles>) genericDAOBean.loadAllForEntity(MarRoles.class, "rolNombre");
        } catch (Exception e) {
            logger.error("Error obteniendo los roles disponibles, causado por :" + e, e);
        }
    }

    /**
     * Obtiene los tipos de documentos parametrizados en la aplicación.
     */
    public void obtenerTiposDocumentos() {
        try {
            tiposDocumentos = (List<MarTiposDocumentos>) genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre");
        } catch (Exception e) {
            logger.error("Error obteniendo los tipos de documentos, causado por " + e, e);
        }
    }

    /**
     * Obtiene los roles asociados a un usuario.
     */
    public void obtenerRolesDeUsuario() {
        try {
            rolesUsuarios = (List<MarRolesUsuarios>) genericDAOBean.findAllByColumn(MarRolesUsuarios.class, "usuId.usuId", usuarioSel.getUsuId(), true, "rolId.rolNombre");
        } catch (Exception e) {
            logger.error("Error obteniendo los roles del usuario, causado por " + e, e);
        }
    }

    /**
     * Filtrado de usuarios por texto ingresado.
     *
     * @param query
     * @return
     */
    public List<MarUsuarios> completeUsers(String query) {
        List<MarUsuarios> usuFiltered = new ArrayList<>();
        try {
            usuFiltered = usuariosDAOBean.loadUserFiltered(query, usuarioSesion);
        } catch (Exception e) {
            logger.error("Error filtrando usuarios, error causado por " + e, e);
        }
        return usuFiltered;
    }

    /**
     * Seleccionar Usuario.
     */
    public void seleccionarUsuario() {
        try {
            usuarioSel = usuarioSeleccion;
            personaSel = usuarioSel.getPerId();
            obtenerRolesDeUsuario();
            obtenerNotarias();
        } catch (Exception e) {
            logger.error("Error cargando informacion del usuario / devolcuiones, error causado por " + e, e);
        }
    }

    /**
     * Elimina un rol asociado a un usuario.
     */
    public void eliminarRolDeUsuario() {
        try {
            genericDAOBean.delete(rolUsuarioSel, rolUsuarioSel.getRusId());
            obtenerRolesDeUsuario();
        } catch (Exception e) {
            logger.error("Error eliminando el rol al usuario, causado por " + e, e);
        }
    }
    
    
    /**
     * Asigna el rol al usuario seleccionado.
     */
    public void asignarRol() {
        try {
            //System.out.println("Nemths ROL:"+rolSel.getRolNombre());
            Predicate<MarRolesUsuarios> rolExistente = r -> r.getRolId().equals(rolSel);
            boolean existeRol = rolesUsuarios.stream().anyMatch(rolExistente);
            if (existeRol) {
                PrimeFacesPopup.lanzarDialog(Effects.Fold, "Información exisitente", "El rol ya se encuentra asignado a este usuario", true, false);
            } else {
                MarRolesUsuarios nuevoRolUsuario = new MarRolesUsuarios();
                nuevoRolUsuario.setRolId(rolSel);
                nuevoRolUsuario.setUsuId(usuarioSel);
                nuevoRolUsuario.setRusFechaInicio(fechaInicioRol);
                nuevoRolUsuario.setRusFechaFin(fechaFinRol);
                auditSessionUtils.setAuditReflectedValues(nuevoRolUsuario);
                genericDAOBean.save(nuevoRolUsuario);
                PrimeFacesContext.execute("PF('modalRoles').hide()");
                obtenerRolesDeUsuario();
            }
        } catch (Exception e) {
            logger.error("Error asignando rol al usuario, causado por " + e, e);
        }
    }

    /**
     * Guarda todos los cambios realizados a un usuario.
     */
    public void guardarUsuario() {
        try {
            auditSessionUtils.setAuditReflectedValues(personaSel);
            auditSessionUtils.setAuditReflectedValues(usuarioSel);

            if (personaSel.getPerId() == null) {
                // Validar que no exista otro usuario con la misma configuracion
                String login = personaSel.getTdcId().getTdcSigla() + personaSel.getPerDocumento();
                if (usuarioSel.getUsuLogin() == null || usuarioSel.getUsuLogin().equals("")) {
                    usuarioSel.setUsuLogin(login);
                }

                // Validamos si el usuario existe en la BD
                boolean validacion = usuariosDAOBean.validacionCreacionUsuario(usuarioSel, personaSel);

                if (validacion) {
                    // Asignar nueva clave de usuario
                    if (usuarioSel.getUsuPassword() == null || usuarioSel.getUsuPassword().equals("")) {
                        usuarioSel.setUsuPassword(CryptoUtils.encrypt(claveNueva));
                    }
                    genericDAOBean.save(personaSel);
                    usuarioSel.setPerId(personaSel);
                    genericDAOBean.save(usuarioSel);
                    usuarioSeleccion = usuarioSel;
                    PrimeFacesPopup.lanzarDialog(Effects.Fold, "Notificacion", "Usuario almacenado correctamente en base de datos", true, false);
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error de Validacion", "Lo sentimos, pero la informacion ingresada ya se encuentra vinculada a otro usuario en la plataforma.", true, false);
                }
            } else {
                if (usuarioSel.getUsuEstado().equals("A")) {
                    usuarioSel.setUsuIntentosFail(Short.parseShort("0"));
                }
                genericDAOBean.merge(personaSel);
                genericDAOBean.merge(usuarioSel);
                usuarioSeleccion = usuarioSel;
                PrimeFacesPopup.lanzarDialog(Effects.Fold, "Notificacion", "Usuario actualizado correctamente en base de datos", true, false);
            }
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Notificacion", "Ha ocurrido un erro al crear el usuario, por favor intente nuevamente.", true, false);
            logger.error("Error guardando datos para el usuario, causado por " + e, e);
        }
    }

    /**
     * Borrar usuario seleccionado.
     */
    public void eliminarUsuario() {
        try {
            genericDAOBean.delete(personaSel, personaSel.getPerId());
            try {
                genericDAOBean.delete(usuarioSel, usuarioSel.getUsuId());
            } catch (Exception e) {
                logger.info("Omitiendo borrado de usuario, causado por " + e);
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Usuario eliminado correctamente de la base de datos.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Notificacion", "Lo sentimos pero no se ha podido eliminar el usuario, por favor intente nuevamente.", true, false);
            logger.error("Error eliminando usuario del usuario, causado por " + e, e);
        }
    }
    
    /**
     * Limpia las claves para ser ingresadas de nuevo.
     */
    public void limpiarClaves(){
        claveNueva = "";
        claveNuevaRep = "";
    }

    /**
     * Cambia la clave actual del usuario siempre y cuando no sea null.
     */
    public void cambiarClave() {
        try {
            if (!claveNueva.equals("")) {
                usuarioSel.setUsuPassword(CryptoUtils.encrypt(claveNueva));
                auditSessionUtils.setAuditReflectedValues(usuarioSel);
                usuarioSel.setUsuCambioClave("S");
                genericDAOBean.merge(usuarioSel);
                PrimeFacesPopup.lanzarDialog(Effects.Fold, "Información guardada", "Clave cambiada correctamente", true, false);
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Fold, "Información no guardada", "La clave registrada no es válida", true, false);
            }
            PrimeFacesContext.execute("PF('modalClave').hide()");
        } catch (Exception e) {
            logger.error("Error cambiando la clave, causado por " + e, e);
        }
    }

    /**
     * Limpia todos los campos del usuario.
     */
    public void limpiarUsuario() {
        usuarioSel = null;
        personaSel = null;
        usuarioSeleccion = null;
    }

    /**
     * Creacion de nuevo usuario.
     */
    public void crearUsuario() {
        usuarioSeleccion = null;
        rolesUsuarios = new ArrayList<>();
        usuarioSel = new MarUsuarios();
        personaSel = new MarPersonas();
        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Por favor ingrese la informacion del nuevo usuario", true, false);
        usuarioSel.setUsuIntentosFail(Short.parseShort("0"));
        Calendar cal = Calendar.getInstance();
        usuarioSel.setUsuFechaInicio(cal.getTime());
        cal.add(Calendar.YEAR, 2);
        usuarioSel.setUsuFechaFin(cal.getTime());
    }
    
    public List<MarUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<MarUsuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public MarUsuarios getUsuarioSel() {
        return usuarioSel;
    }

    public void setUsuarioSel(MarUsuarios usuarioSel) {
        this.usuarioSel = usuarioSel;
    }

    public List<MarTiposDocumentos> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<MarTiposDocumentos> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public MarUsuarios getUsuarioSeleccion() {
        return usuarioSeleccion;
    }

    public void setUsuarioSeleccion(MarUsuarios usuarioSeleccion) {
        this.usuarioSeleccion = usuarioSeleccion;
    }

    public List<MarRolesUsuarios> getRolesUsuarios() {
        return rolesUsuarios;
    }

    public void setRolesUsuarios(List<MarRolesUsuarios> rolesUsuarios) {
        this.rolesUsuarios = rolesUsuarios;
    }

    public MarRolesUsuarios getRolUsuarioSel() {
        return rolUsuarioSel;
    }

    public void setRolUsuarioSel(MarRolesUsuarios rolUsuarioSel) {
        this.rolUsuarioSel = rolUsuarioSel;
    }

    public List<MarRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<MarRoles> roles) {
        this.roles = roles;
    }

    public MarRoles getRolSel() {
        return rolSel;
    }

    public void setRolSel(MarRoles rolSel) {
        this.rolSel = rolSel;
    }

    public MarPersonas getPersonaSel() {
        return personaSel;
    }

    public void setPersonaSel(MarPersonas personaSel) {
        this.personaSel = personaSel;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public List<MarNotarias> getNotarias() {
        return notarias;
    }

    public void setNotarias(List<MarNotarias> notarias) {
        this.notarias = notarias;
    }

    public List<MarOficinasRegistros> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<MarOficinasRegistros> oficinas) {
        this.oficinas = oficinas;
    }

    public Date getFechaInicioRol() {
        return fechaInicioRol;
    }

    public void setFechaInicioRol(Date fechaInicioRol) {
        this.fechaInicioRol = fechaInicioRol;
    }

    public Date getFechaFinRol() {
        return fechaFinRol;
    }

    public void setFechaFinRol(Date fechaFinRol) {
        this.fechaFinRol = fechaFinRol;
    }

    public String getClaveNuevaRep() {
        return claveNuevaRep;
    }

    public void setClaveNuevaRep(String claveNuevaRep) {
        this.claveNuevaRep = claveNuevaRep;
    }
    
    

}
