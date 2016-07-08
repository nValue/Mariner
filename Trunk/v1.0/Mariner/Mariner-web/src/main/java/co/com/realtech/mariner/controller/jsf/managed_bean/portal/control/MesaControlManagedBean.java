package co.com.realtech.mariner.controller.jsf.managed_bean.portal.control;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.control.MesaControlBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarFasesEstados;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

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
    private MarRadicacionesFasesEstados radicacion;
    //private List<MarRadicaciones> radicaciones;
    //private List<MarRadicaciones> radicacionesFiltrado;
    private List<MarRadicacionesFasesEstados> radicaciones;
    private List<MarRadicacionesFasesEstados> radicacionesFiltrado;
    
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
            radicaciones = radicFasesEstadosDAOBean.obtenerRadicFasEstXFiltroValorFechas(nombre, valorFiltro, fechaInicial, fechaFinal);
            //setRadicaciones(mesaControlBeanLocal.filtrarRadicacionesMesaControl(nombre, getValorFiltro(), tipoDato, getFechaInicial(), getFechaFinal()));

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
            setTransaccion(getRadicacion().getRadId().getMarTransacciones());
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
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>)genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacion.getRadId(), true, "rfeId");
        } catch (Exception e) {
            String msj = "No se pueden traer las fases-estados de la radicación, causado por: " + e.getMessage();
             PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", msj, true, false);
             logger.error(msj,e);
        }
    }
    
    
    /**
     * Devuelve una radicación al estado de validación, solo es válido si el proceso ya pasó alguna vez por ahí.
     */
    public void devolverRadicacion(){
        try {
            MarRadicacionesFasesEstados radFaseEstado = null;
            boolean permiteDev = false;
            for (MarRadicacionesFasesEstados faseEstado : radicacionesFasesEstados) {
                if(faseEstado.getFesId().getFesCodigo().equals("R-P")){
                    if(!(radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1).getFesId().getFesCodigo().equals("R-P"))){
                        permiteDev = true;
                        radFaseEstado = faseEstado;
                    }
                }
            }
            String observaciones = "Proceso devuelto por mesa de control; Usuario: " + usuarioSesion.getUsuLogin();
            if (permiteDev) {
                //Se crea la fase proceso de aprobación nuevamente con la observacion.
                BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacion.getRadId(),
                        "R-P", "A", radFaseEstado.getUsuId().getUsuId(), observaciones, null);
                Integer salida = BDsalida.intValue();
                if (salida == -999) {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado rechazo para la radicación, por favor verifique que la información este correcta e intente de nuevo", true, false);
                    return;
                }
                //Notifica a la persona a través de Push el rechazo correspondiente
                EventBus eventBus = EventBusFactory.getDefault().eventBus();
                eventBus.publish("/estados/"+radFaseEstado.getUsuId().getUsuId(), new FacesMessage("Radicacion rechazada", "La radicacion " + radicacion.getRadId().getRadNumero() + " que ya habia sido validada, se ha retornado a su mesa de validación por el usuario: " + usuarioSesion.getUsuLogin()));

                obtenerFasesEstados();
                PrimeFacesPopup.lanzarDialog(Effects.Clip, "Proceso realizado", "La radicación ahora se encuentra en la mesa de aprobación del usuario " + radFaseEstado.getUsuId().getPerId().getPerNombres() + " " + radFaseEstado.getUsuId().getPerId().getPerApellidos(), true, false);
            }else{
                PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "Esta radicación todavía no puede ser devuelta puesto que no ha llegado al estado apropiado para ello", true, false);
            }
        } catch (Exception e) {
            String msj = "No se pueden obtener las fases estados, causado por " + e;
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

    public MarRadicacionesFasesEstados getRadicacion() {
        return radicacion;
    }

    public void setRadicacion(MarRadicacionesFasesEstados radicacion) {
        this.radicacion = radicacion;
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

    public MarTransacciones getTransaccion() {
        return transaccion;
    }

    public List<MarRadicacionesFasesEstados> getRadicaciones() {
        return radicaciones;
    }

    public void setRadicaciones(List<MarRadicacionesFasesEstados> radicaciones) {
        this.radicaciones = radicaciones;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFiltrado() {
        return radicacionesFiltrado;
    }

    public void setRadicacionesFiltrado(List<MarRadicacionesFasesEstados> radicacionesFiltrado) {
        this.radicacionesFiltrado = radicacionesFiltrado;
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
