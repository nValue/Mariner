package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios.UsuariosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de la pantalla de asignaciones a los liquidadores
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
    private MarRadicacionesFasesEstados radFaseEstadoDisponible;
    
    private List<MarRadicacionesFasesEstados> radFasesEstadosUsuario;
    private MarRadicacionesFasesEstados radFaseEstadoUsuario;
    
    private List<MarUsuarios> usuarios;
    private MarUsuarios usuarioSel;
    
    @Override
    public void init() {
        obtenerRadicacionesDisponibles();
        obtenerUsuariosConModulo();
    }
    
    /**
     * Obtiene las radicaciones que no se han asignado a un usuario.
     */
    public void obtenerRadicacionesDisponibles(){
        try {
            radFasesEstadosDisponibles = radicFasesEstadosDAOBean.obtenerPendientesConCodigos("'I-P'");
        } catch (Exception e) {
            String sql = "No se pueden obtener las radicaciones disponibles, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicaciones no disponibles", sql, true, false);
            logger.error(sql, e);
        }
    }
    
    /**
     * Obtiene los usuarios que tienen el acceso al módulo de liquidación o validación.
     */
    public void obtenerUsuariosConModulo(){
        try {
            usuarios = usuariosDAOBean.obtenerAsociadosAModulo("10");
            if(!usuarios.isEmpty()){
                usuarioSel = usuarios.get(0);
            }
        } catch (Exception e) {
            String sql = "No se pueden obtener los usuarios disponibles, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Error en usuarios", sql, true, false);
            logger.error(sql, e);
        }
    }
    
    /**
     * Trae el número de radicaciones que actualmente tiene pendientes un usuario
     * @return 
     */
    public int traerPendientesDeUsuario(){
        int num = 0;
        try {
            
        } catch (Exception e) {
            String sql = "No se pueden obtener las radicaciones pendientes para un usuario, causado por: " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Error en usuarios", sql, true, false);
            logger.error(sql, e);
        }
        return num;
    }
}
