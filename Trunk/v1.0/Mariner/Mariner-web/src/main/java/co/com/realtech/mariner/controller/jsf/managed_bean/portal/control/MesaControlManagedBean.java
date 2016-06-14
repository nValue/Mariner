package co.com.realtech.mariner.controller.jsf.managed_bean.portal.control;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.control.MesaControlBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean controlador de mesa de control.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class MesaControlManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "MesaControlBean")
    private MesaControlBeanLocal mesaControlBeanLocal;

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    private String filtro;
    private String valorFiltro;
    private Date fechaInicial;
    private Date fechaFinal;
    private MarRadicaciones radicacion;
    private List<MarRadicaciones> radicaciones;
    private List<MarRadicaciones> radicacionesFiltrado;
    
    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;
    private MarTransacciones transaccion;

    @Override
    public void init() {
        try {
            Calendar cal = Calendar.getInstance();
            setFechaFinal(cal.getTime());
            cal.add(Calendar.DATE, 0);
            setFechaInicial(cal.getTime());
        } catch (Exception e) {
            logger.error("Error inicializando MesaControlManagedBean, cuasado por " + e);
        }
    }

    /**
     * Limpiado de filtros interface.
     */
    public void limpiarFiltros() {
        setValorFiltro("");
        setRadicaciones(new ArrayList<>());
    }

    /**
     * Realizar filtrado registros mesa de control.
     */
    public void filtrarMesaControl() {
        try {
            String nombre = getFiltro().split(",")[0];
            String tipoDato = getFiltro().split(",")[1];

            if (nombre.equals("RAD-LIQUIDACION")) {
                setValorFiltro(BusinessStringUtils.convertNumeroLiquidacion(getValorFiltro()));
            }

            setRadicaciones(mesaControlBeanLocal.filtrarRadicacionesMesaControl(nombre, getValorFiltro(), tipoDato, getFechaInicial(), getFechaFinal()));

            if (getRadicaciones().isEmpty()) {
                PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "No se han encontrado radicaciones vinculadas al filtro seleccionado.", true, false);
            } else {
                setRadicacion(getRadicaciones().get(0));
            }
        } catch (Exception e) {
            logger.error("Error realizando filtrado MesaControlManagedBean, cuasado por " + e);
        }
    }

    /**
     * Cargar detalle de la radicacion.
     */
    public void cargarDetalleRadicacion() {
        try {
            // Cargar detalle de la transaccion
            setTransaccion(getRadicacion().getMarTransacciones());
            // Cargar estados de la radicacion.
            obtenerFasesEstados();
        } catch (Exception e) {
            logger.error("Error cargando detalle de la radicacion, cuasado por " + e);
        }
    }
    
    /**
     * Trae todos los estados asociados a la radicación actual.
     */
    public void obtenerFasesEstados(){
        try {
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>)genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacion, true, "rfeId");
        } catch (Exception e) {
            String msj = "No se pueden traer las fases-estados de la radicación, causado por: " + e.getMessage();
             PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", msj, true, false);
             logger.error(msj,e);
        }
    }

    /**
     * Carga la descripcion del ultimo estado de la radicacion.
     *
     * @param radId
     * @return
     */
    /*
    public String cargarDescripcionUltimoEstado(MarRadicaciones radId) {
        String salida;
        try {
            MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radId);
            salida = estado.getFesId().getFesNombre();
        } catch (Exception e) {
            salida = "Error..";
        }
        return salida;
    }*/

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public MarRadicaciones getRadicacion() {
        return radicacion;
    }

    public void setRadicacion(MarRadicaciones radicacion) {
        this.radicacion = radicacion;
    }

    public List<MarRadicaciones> getRadicaciones() {
        return radicaciones;
    }

    public void setRadicaciones(List<MarRadicaciones> radicaciones) {
        this.radicaciones = radicaciones;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getValorFiltro() {
        return valorFiltro;
    }

    public void setValorFiltro(String valorFiltro) {
        this.valorFiltro = valorFiltro;
    }

    public List<MarRadicaciones> getRadicacionesFiltrado() {
        return radicacionesFiltrado;
    }

    public void setRadicacionesFiltrado(List<MarRadicaciones> radicacionesFiltrado) {
        this.radicacionesFiltrado = radicacionesFiltrado;
    }

    public MarTransacciones getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(MarTransacciones transaccion) {
        this.transaccion = transaccion;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstados() {
        return radicacionesFasesEstados;
    }

    public void setRadicacionesFasesEstados(List<MarRadicacionesFasesEstados> radicacionesFasesEstados) {
        this.radicacionesFasesEstados = radicacionesFasesEstados;
    }

    
    
}
