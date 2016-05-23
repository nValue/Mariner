package co.com.realtech.mariner.controller.jsf.managed_bean.external;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.session.SessionUtils;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de la logica de pagos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class PaymentManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    private String autenticado;
    private String tipoBusqueda;
    private String codigoBusqueda;
    private MarRadicaciones radicacion;
    private List<MarRadicaciones> radicaciones;

    @Override
    public void init() {
        try {
            setAutenticado(SessionUtils.obtenerValor("auth"));

            if (getAutenticado().equals("S")) {
                setRadicaciones(radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'R-A'", usuarioSesion));
                if (!getRadicaciones().isEmpty()) {
                    setRadicacion(getRadicaciones().get(0));
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Fold, "Notificacion", "No se han encontrado radicaciones pendientes por pago", true, false);
                }
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Por favor ingrese el numero de la liquidacion de la cual desea realizar el pago.", true, false);
            }
        } catch (Exception e) {
            logger.error("Error inicializando PaymentManagedBean, causado por " + e);
        }
    }

    /**
     * Buscar radicacion segun filtro.
     */
    public void buscarRadicacion() {
        try {
            if (getTipoBusqueda().equals("L")) {
                List<MarRadicaciones> radiaciones = (List<MarRadicaciones>) genericDAOBean.findAllByColumn(MarRadicaciones.class, "radLiquidacion", getCodigoBusqueda(), true, "radId desc");
                if (!radiaciones.isEmpty()) {
                    setRadicacion(radiaciones.get(0));
                }
            } else {
                List<MarRadicaciones> radiaciones = (List<MarRadicaciones>) genericDAOBean.findAllByColumn(MarRadicaciones.class, "radNumero", getCodigoBusqueda(), true, "radId desc");
                if (!radiaciones.isEmpty()) {
                    setRadicacion(radiaciones.get(0));
                }
            }

            if (getRadicacion() != null) {
                MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(getRadicacion());
                if (!estado.getFesId().getFesCodigo().equals("R-A")) {
                    setRadicacion(null);
                    PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "Lo sentimos pero la radicacion seleccionada, no se encuentra en proceso de pago.", true, false);
                }
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "No se han encontrado radicaciones vinculadas al filtro seleccionado.", true, false);
            }            
        } catch (Exception e) {
            e.printStackTrace();
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error buscando el registro, por favor intente nuevamente.", true, false);
            logger.error("Error buscando radicacion, causado por " + e);
        }
    }

    /**
     * Realizar pago de radicacion seleccionada.
     *
     * @param tipoPago
     */
    public void seleccionarMedioPago(String tipoPago) {
        try {
            System.out.println("Seleccionado medio de pago " + tipoPago);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error seleccionando el medio de pago.", true, false);
            logger.error("Error realizando seleccion de medio de pago radicacion, causado por " + e);
        }
    }

    public String getAutenticado() {
        return autenticado;
    }

    public void setAutenticado(String autenticado) {
        this.autenticado = autenticado;
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

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

}
