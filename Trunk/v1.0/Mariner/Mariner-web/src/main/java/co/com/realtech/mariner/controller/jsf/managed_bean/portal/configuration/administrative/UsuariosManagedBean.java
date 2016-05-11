/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.administrative;

import co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios.UsuariosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarRoles;
import co.com.realtech.mariner.model.entity.MarRolesUsuarios;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.crypto.CryptoUtils;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import co.com.realtech.mariner.util.session.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;


/**
 * ManagedBean encargado de la administración de los usuarios, incluyendo contraseñas y demás.
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class UsuariosManagedBean implements Serializable {

    @EJB(beanName = "GenericDAOBean")
    private GenericDAOBeanLocal genericDAOBean;
    
    @EJB(beanName = "UsuariosDAOBean")
    private UsuariosDAOBeanLocal usuariosDAOBean;
    
    private List<MarUsuarios> usuarios;
    private MarUsuarios usuarioSel;
    private MarUsuarios usuarioSesion;
    private MarUsuarios usuarioSeleccion;
    private List<MarTiposDocumentos> tiposDocumentos;
    private List<MarRolesUsuarios> rolesUsuarios;
    private MarRolesUsuarios rolUsuarioSel;
    private MarPersonas personaSel;
    
    private String claveNueva;
    
    private List<MarRoles> roles;
    private MarRoles rolSel;
    
    private Logger logger;
    
    private AuditSessionUtils auditSessionUtils;
    
    @PostConstruct
    public void init(){
        usuarioSesion = (MarUsuarios) SessionUtils.obtenerValorGeneric("marineruser");
        logger = Logger.getLogger(UsuariosManagedBean.class);
        obtenerTiposDocumentos();
        obtenerRoles();
        claveNueva = "";
        auditSessionUtils = AuditSessionUtils.create();
    }
    
    public UsuariosManagedBean() {
    }
    
    public void obtenerRoles(){
        try {
            roles = (List<MarRoles>)genericDAOBean.loadAllForEntity(MarRoles.class, "rolNombre");
            if(!roles.isEmpty()){
                rolSel = roles.get(0);
            }
        } catch (Exception e) {
            logger.error("Error obteniendo los roles disponibles, causado por :" + e, e);
        }
    }
    
    /**
     * Obtiene los tipos de documentos parametrizados en la aplicación.
     */
    public void obtenerTiposDocumentos(){
        try {
            tiposDocumentos = (List<MarTiposDocumentos>)genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre");
        } catch (Exception e) {
            logger.error("Error obteniendo los tipos de documentos, causado por " + e , e);
        }
    }
    
    /**
     * Obtiene los roles asociados a un usuario.
     */
    public void obtenerRolesDeUsuario(){
        try {
            rolesUsuarios = (List<MarRolesUsuarios>)genericDAOBean.findAllByColumn(MarRolesUsuarios.class, "usuId.usuId", usuarioSel.getUsuId(), true, "rolId.rolNombre");
            if(!rolesUsuarios.isEmpty()){
                rolUsuarioSel = rolesUsuarios.get(0);
            }
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
        } catch (Exception e) {
            logger.error("Error cargando informacion del usuario / devolcuiones, error causado por " + e, e);
        }
    }
    
    /**
     * Elimina un rol asociado a un usuario.
     */
    public void eliminarRolDeUsuario(){
        try {
            genericDAOBean.delete(rolUsuarioSel, rolUsuarioSel.getRusId());
            obtenerRolesDeUsuario();
        } catch (Exception e) {
            logger.error("Error eliminando el rol al usuario, causado por " +e,e);
        }
    }
    
    /**
     * Asigna el rol al usuario seleccionado.
     */
    public void asignarRol(){
        try {
            Predicate<MarRolesUsuarios> rolExistente = r->r.getRolId().equals(rolSel);
            boolean existeRol = rolesUsuarios.stream().anyMatch(rolExistente);
            if(existeRol){
                PrimeFacesPopup.lanzarDialog(Effects.Fold, "Información exisitente", "El rol ya se encuentra asignado a este usuario", true,false);
            }else{
                MarRolesUsuarios nuevoRolUsuario = new MarRolesUsuarios();
                nuevoRolUsuario.setRolId(rolSel);
                nuevoRolUsuario.setUsuId(usuarioSel);
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
    public void guardarUsuario(){
        try {
            auditSessionUtils.setAuditReflectedValues(personaSel);
            genericDAOBean.merge(personaSel);
            auditSessionUtils.setAuditReflectedValues(usuarioSel);
            genericDAOBean.merge(usuarioSel);
            PrimeFacesPopup.lanzarDialog(Effects.Fold, "Información guardada", "Usuario guardado correctamente", true, false);
        } catch (Exception e) {
            logger.error("Error guardando datos para el usuario, causado por " + e, e);
        }
    }
    
    /**
     * Cambia la clave actual del usuario siempre y cuando no sea null.
     */
    public void cambiarClave(){
        try {
            if(!claveNueva.equals("")){
                usuarioSel.setUsuPassword(CryptoUtils.encrypt(claveNueva));
                auditSessionUtils.setAuditReflectedValues(usuarioSel);
                genericDAOBean.merge(usuarioSel);
                PrimeFacesPopup.lanzarDialog(Effects.Fold, "Información guardada", "Clave cambiada correctamente", true, false);
            }else{
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
    public void limpiarUsuario(){
        usuarioSel = null;
        personaSel = null;
        usuarioSeleccion = null;
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
    
    
    
}
