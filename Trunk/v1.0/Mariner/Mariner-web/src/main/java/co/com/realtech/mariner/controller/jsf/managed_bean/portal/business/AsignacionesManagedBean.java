package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios.UsuariosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * ManagedBean encargado de la pantalla de asignaciones para los notarios, liquidadores y validadores
 * dependiente el parámetro de entrada.
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class AsignacionesManagedBean extends GenericManagedBean{
    
    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;
    
    @EJB(beanName = "UsuariosDAOBean")
    private UsuariosDAOBeanLocal usuariosDAOBean;

    private List<MarRadicacionesFasesEstados> radFasesEstadosDisponibles;
    private List<MarRadicacionesFasesEstados> radFaseEstadosDisponiblesSelec;
    
    private List<MarRadicacionesFasesEstados> radFasesEstadosUsuario;
    private MarRadicacionesFasesEstados radFaseEstadoUsuario;
    
    private List<MarRadicacionesFasesEstados> radFasesEstadosUsuarioFiltro;
    
    private List<MarUsuarios> usuarios;
    private MarUsuarios usuarioSel;
    
    private List<MarUsuarios> usuariosReasigs;
    private MarUsuarios usuarioReasigSel;
    
    private String rol;
    private String codigosPendientes;
    private String usuariosPendientes;
    private boolean permiteAsignar;
    private String estadoAsignacion;

    @Override
    public void init() {
        Map mapaParametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Object especialidadObject = null;
        especialidadObject = mapaParametros.get("role");
        if (especialidadObject == null) {
            PrimeFacesPopup.lanzarDialog(Effects.Shake, "Parámetro inválido", "El parámetro de inicio con el rol no es correcto, verifique la configuración", true, false);
            return;
        } else {
            rol = especialidadObject.toString();
        }
        estadoAsignacion = "";
        permiteAsignar = true;
        codigosPendientes = "";
        usuariosPendientes = "";
        if (rol.equals("NOT")) {
            permiteAsignar = false;
            codigosPendientes = "'I-R'";
            usuariosPendientes = "'I-R'";
        } else if (rol.equals("LIQ")) {
            codigosPendientes = "'I-P'";
            usuariosPendientes = "'G-P','R-R'";
            estadoAsignacion = "G-P";
        } else if (rol.equals("APR")) {
            codigosPendientes = "'G-A'";
            usuariosPendientes = "'R-P'";
            estadoAsignacion = "R-P";
        }
        obtenerRadicacionesDisponibles();
        obtenerUsuariosConModulo();
    }

    /**
     * Obtiene las radicaciones que no se han asignado a un usuario dependiendo del rol Notario o Liquidador.
     */
    public void obtenerRadicacionesDisponibles(){
        try {
            radFasesEstadosDisponibles = radicFasesEstadosDAOBean.obtenerPendientesConCodigos(codigosPendientes);
            /*
            if(!radFasesEstadosDisponibles.isEmpty()){
                radFaseEstadoDisponible = radFasesEstadosDisponibles.get(0);
            }*/
        } catch (Exception e) {
            String sql = "No se pueden obtener las radicaciones disponibles, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicaciones no disponibles", sql, true, false);
            logger.error(sql, e);
        }
    }
    
    /**
     * Obtiene los usuarios que tienen el acceso al módulo de liquidación, validación o notaría.
     */
    public void obtenerUsuariosConModulo(){
        try {
            usuarios = usuariosDAOBean.obtenerAsociadosAModulo(rol);
            usuariosReasigs = usuarios;
            if(!usuarios.isEmpty()){
                usuarioSel = usuarios.get(0);
                usuarioReasigSel = usuarios.get(0);
            }
        } catch (Exception e) {
            String sql = "No se pueden obtener los usuarios disponibles, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Error en usuarios", sql, true, false);
            logger.error(sql, e);
        }
    }
    
    /**
     * Obtiene los pendientes que tiene el usuario.
     */
    public void obtenerPendientesUsuario(){
        try {
            radFasesEstadosUsuario = radicFasesEstadosDAOBean.obtenerPorUltimaFaseUsuario(usuariosPendientes, usuarioSel);
            if(!radFasesEstadosUsuario.isEmpty()){
                radFaseEstadoUsuario = radFasesEstadosUsuario.get(0);
            }
        } catch (Exception e) {
            String msj = "No se pueden obtener las radicaciones pendientes para un usuario, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Error en usuarios", msj, true, false);
            logger.error(msj, e);
        }
        
    }
    
    /**
     * Trae el número de radicaciones que actualmente tiene pendientes un usuario
     * @param usu
     * @return 
     */
    public int traerPendientesDeUsuario(MarUsuarios usu){
        int num = 0;
        try {
            num = usuariosDAOBean.obtenerCantPendientesDeUsuario(usu, usuariosPendientes);
        } catch (Exception e) {
            String msj = "No se pueden obtener la cantidad de radicaciones pendientes para un usuario, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Error en usuarios", msj, true, false);
            logger.error(msj, e);
        }
        return num;
    }
    
    /**
     * Asigna una radicación disponible a el usuario seleccionado.
     */
    public void asignarRadicacion(){
        try {
            for (MarRadicacionesFasesEstados faseEstadoDispSel : radFaseEstadosDisponiblesSelec) {
                //Primero valida que el proceso no haya sido asignado a un usuario.
                MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(faseEstadoDispSel.getRadId());
                if (rfe.getRfeId().equals(faseEstadoDispSel.getRfeId())) {
                    BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", faseEstadoDispSel.getRadId().getRadId(),
                            estadoAsignacion, "A", usuarioSel.getUsuId(), "", null);
                    Integer salida = BDsalida.intValue();
                    if (salida == -999) {
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                        return;
                    }
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación asignada", "La radicación número <b>" + faseEstadoDispSel.getRadId().getRadNumero() + "</b>, se ha asignado al usuario <b>" + usuarioSel.getUsuLogin() + "</b>", true, false);
                    obtenerRadicacionesDisponibles();
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación ya procesada", "Esta radicación disponible acaba de ser procesada por un usuario y no puede ser asignada", true, false);
                }
            }
        } catch (Exception e) {
            String msj = "No se pueden asignar la radicacioes, debido a : " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Asignaciones no son posibles", msj, true, false);
            logger.error(msj,e);            
        }
    }
    
    /**
     * Cambia la radicación de un usuario a otro seleccionado.
     */
    public void reAsignarRadicacion(){
        try {
            //Primero valida que el proceso no haya sido asignado a un usuario.
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radFaseEstadoUsuario.getRadId());
            if(rfe.getRfeId().equals(radFaseEstadoUsuario.getRfeId())){
                //Ahora valida que el estado actual del proceso no lo haya obtenido otro usuario mientras el asignador cambiaba la radicación a otro usuario.
                MarUsuarios usuNuevo = rfe.getUsuId();
                if(!radFaseEstadoUsuario.getUsuId().getUsuId().equals(usuNuevo.getUsuId())){
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación cargada externamente", "Mientras usted realizaba la operación de asignación, esta radicación fue colocada al usuario " + usuNuevo.getUsuLogin(), true, false);
                    return;
                }
                //Si la radicación no ha cambiado estados mientras se procesaba si se puede cambiar el usuario.
                radFaseEstadoUsuario.setUsuId(usuarioReasigSel);
                auditSessionUtils.setAuditReflectedValues(radFaseEstadoUsuario);
                genericDAOBean.merge(radFaseEstadoUsuario);
                PrimeFacesContext.execute("PF('dialogRadicUsuario').hide()");
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación asignada", "La radicación número <b>" + radFaseEstadoUsuario.getRadId().getRadNumero()+"</b>, se ha reasignado al usuario <b>" + usuarioReasigSel.getUsuLogin() + "</b>" , true, false);
                obtenerRadicacionesDisponibles();
                obtenerUsuariosConModulo();
            }else{
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación ya procesada", "Esta radicación disponible acaba de ser procesada por un usuario y no puede ser asignada", true, false);
            }
        } catch (Exception e) {
            String msj = "No se puede asignar la radicación, debido a : " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Asignación no es posible", msj, true, false);
            logger.error(msj,e);            
        }
    }
    
    /**
     * Obtiene el tiempo en minutos desde que la radicación ha estado en el estado enviado.
     */
    public int obtenerTiempoMins(MarRadicacionesFasesEstados est){
        int minutos = 0;
        try {
            minutos = radicFasesEstadosDAOBean.obtenerMinutosActualesRadFase(est);
        } catch (Exception e) {
            String msj = "No se puede obtener el tiempo de la radicación, debido a : " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Tiempo no es posible", msj, true, false);
            logger.error(msj,e);   
        }
        return minutos;
    }

    public List<MarRadicacionesFasesEstados> getRadFasesEstadosDisponibles() {
        return radFasesEstadosDisponibles;
    }

    public void setRadFasesEstadosDisponibles(List<MarRadicacionesFasesEstados> radFasesEstadosDisponibles) {
        this.radFasesEstadosDisponibles = radFasesEstadosDisponibles;
    }

    public List<MarRadicacionesFasesEstados> getRadFaseEstadosDisponiblesSelec() {
        return radFaseEstadosDisponiblesSelec;
    }

    public void setRadFaseEstadosDisponiblesSelec(List<MarRadicacionesFasesEstados> radFaseEstadosDisponiblesSelec) {
        this.radFaseEstadosDisponiblesSelec = radFaseEstadosDisponiblesSelec;
    }

    public List<MarRadicacionesFasesEstados> getRadFasesEstadosUsuario() {
        return radFasesEstadosUsuario;
    }

    public void setRadFasesEstadosUsuario(List<MarRadicacionesFasesEstados> radFasesEstadosUsuario) {
        this.radFasesEstadosUsuario = radFasesEstadosUsuario;
    }

    public MarRadicacionesFasesEstados getRadFaseEstadoUsuario() {
        return radFaseEstadoUsuario;
    }

    public void setRadFaseEstadoUsuario(MarRadicacionesFasesEstados radFaseEstadoUsuario) {
        this.radFaseEstadoUsuario = radFaseEstadoUsuario;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<MarUsuarios> getUsuariosReasigs() {
        return usuariosReasigs;
    }

    public void setUsuariosReasigs(List<MarUsuarios> usuariosReasigs) {
        this.usuariosReasigs = usuariosReasigs;
    }

    public MarUsuarios getUsuarioReasigSel() {
        return usuarioReasigSel;
    }

    public void setUsuarioReasigSel(MarUsuarios usuarioReasigSel) {
        this.usuarioReasigSel = usuarioReasigSel;
    }

    public boolean isPermiteAsignar() {
        return permiteAsignar;
    }

    public void setPermiteAsignar(boolean permiteAsignar) {
        this.permiteAsignar = permiteAsignar;
    }

    public List<MarRadicacionesFasesEstados> getRadFasesEstadosUsuarioFiltro() {
        return radFasesEstadosUsuarioFiltro;
    }

    public void setRadFasesEstadosUsuarioFiltro(List<MarRadicacionesFasesEstados> radFasesEstadosUsuarioFiltro) {
        this.radFasesEstadosUsuarioFiltro = radFasesEstadosUsuarioFiltro;
    }
    
    
    
}
