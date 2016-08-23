package co.com.realtech.mariner.controller.jsf.managed_bean.index;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarNotificaciones;
import co.com.realtech.mariner.model.entity.MarReportes;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    private Date fechaSel;
    private MarReportes reporteGeneral;
    private String parametros;
    
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
            fechaSel = new Date();
            obtenerReportePrincipal();
            colocarParametrosGrafico();
        } catch (Exception e) {
            logger.error("Error inicializando NotificacionesManagedBean, causado por " + e);
        }
    }
    
    /**
     * Obtiene el reporte principal que se debe mostrar en la pantalla de inicio.
     */
    public void obtenerReportePrincipal(){
        try {
            reporteGeneral = (MarReportes)genericDAOBean.findByColumn(MarReportes.class, "repCodigo", "GEN_01");
        } catch (Exception e) {
            logger.error("No se puede cargar el reporte general, causado por: " + e.getMessage());
        }
    }
    
    /**
     * Coloca el parámetro de la notaría para el gráfico si es que la persona lo tiene asociado, sino trae todo.
     */
    public void colocarParametrosGrafico(){
        if(usuarioSesion.getNotId() == null){
            parametros = "n.not_id";
        }else{
            parametros = usuarioSesion.getNotId().getNotId().toString();
        }
        parametros = parametros + "-";
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
    
    /**
     * Retorna la fecha Formateada
     *
     * @return
     */
    public String getFechaSelFormateada() {
        String salida;
        SimpleDateFormat ds = new SimpleDateFormat("dd/MM/yyyy");
        salida = ds.format(fechaSel);
        return salida;
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

    public Date getFechaSel() {
        return fechaSel;
    }

    public void setFechaSel(Date fechaSel) {
        this.fechaSel = fechaSel;
    }

    public MarReportes getReporteGeneral() {
        return reporteGeneral;
    }

    public void setReporteGeneral(MarReportes reporteGeneral) {
        this.reporteGeneral = reporteGeneral;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }
    
    
    
    
    
}
