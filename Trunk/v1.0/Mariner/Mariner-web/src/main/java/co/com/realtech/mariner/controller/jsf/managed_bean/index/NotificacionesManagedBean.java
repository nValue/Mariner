package co.com.realtech.mariner.controller.jsf.managed_bean.index;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarNotificaciones;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de las notificaciones de la plataforma.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.9
 */
@ManagedBean
@ViewScoped
public class NotificacionesManagedBean extends GenericManagedBean implements Serializable {
    
    private int cantidadPendientes = 0;
    private MarNotificaciones notificacion;
    private List<MarNotificaciones> notificaciones;
    
    @Override
    public void init() {
        refrescarNotificacionesUsuario();
    }

    /**
     * Refrescar notificaciones del usuario seleccionado.
     */
    public void refrescarNotificacionesUsuario() {
        try {
            setCantidadPendientes(0);
            setNotificaciones((List<MarNotificaciones>) genericDAOBean.findAllByColumn(MarNotificaciones.class, "usuId", usuarioSesion, true, "ntfId desc", 30));
            getNotificaciones().stream().filter((not) -> (not.getNtfEstado().equals("P"))).forEach((_item) -> {
                setCantidadPendientes(getCantidadPendientes() + 1);
            });
        } catch (Exception e) {
            logger.error("Error inicializando NotificacionesManagedBean, causado por " + e);
        }
    }

    /**
     * Actualizacion de estado de lectura de la notificacion en pantalla.
     *
     * @param notificacion
     */
    public void actualizarEstadoLecturaNotificacion(MarNotificaciones notificacion) {
        try {
            genericDAOBean.callGenericProcedure("PKG_VUR_NOTIFICACIONES.PL_ACTUALIZAR_NOTIFICACION", notificacion.getNtfId().longValue());
            refrescarNotificacionesUsuario();
        } catch (Exception e) {
            logger.error("Error actualizando estado de lectrua a notificacion, causado por " + e);
        }
    }
    
    public MarNotificaciones getNotificacion() {
        return notificacion;
    }
    
    public void setNotificacion(MarNotificaciones notificacion) {
        this.notificacion = notificacion;
    }
    
    public List<MarNotificaciones> getNotificaciones() {
        return notificaciones;
    }
    
    public void setNotificaciones(List<MarNotificaciones> notificaciones) {
        this.notificaciones = notificaciones;
    }
    
    public int getCantidadPendientes() {
        return cantidadPendientes;
    }
    
    public void setCantidadPendientes(int cantidadPendientes) {
        this.cantidadPendientes = cantidadPendientes;
    }
    
}
