package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reference_tables;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarNumeraciones;
import co.com.realtech.mariner.model.entity.MarRechazosCausales;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF para administracion informacion causales de rechazo.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class CausalesRechazoManagedBean extends GenericManagedBean implements Serializable {

    private MarRechazosCausales causal;
    private List<MarRechazosCausales> causales;

    public CausalesRechazoManagedBean() {
    }

    @Override
    public void init() {
        try {
            setCausales((List<MarRechazosCausales>) genericDAOBean.loadAllForEntity(MarRechazosCausales.class, "rcaNombres"));
        } catch (Exception e) {
            logger.error("Error inicializando CausalesRechazoManagedBean causado por " + e, e);
        }
    }

    /**
     * Agregar nueva causal.
     */
    public void agregarCausal() {
        setCausal(new MarRechazosCausales());
    }

    /**
     * Eliminar numeracion seleccionada
     */
    public void eliminarCausal() {
        try {
            genericDAOBean.delete(getCausal(), getCausal().getRcaId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Causal de rechazo eliminada correctamente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se puede eliminar la Causal de Rechazo seleccionada, por favor intente nuevamente", true, false);
            logger.error("Error eliminando causal de rechazo, causado por: " + e, e);
        }
    }

    /**
     * Guardar configuracion de causal de rechazo..
     */
    public void guardarCausalRechazo() {
        try {
            auditSessionUtils.setAuditReflectedValues(getCausal());
            if (getCausal().getRcaId() == null) {
                genericDAOBean.save(getCausal());
            } else {
                genericDAOBean.merge(getCausal());
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Causal de Rechazo almacenada correctamente.", true, false);
            PrimeFacesContext.execute("PF('dialogCausales').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se ha podido guardar la causal de rechazo, por favor intente nuevamente", true, false);
            logger.error("Error guardando causal de rechazo, causado por: " + e, e);
        }
    }

    public MarRechazosCausales getCausal() {
        return causal;
    }

    public void setCausal(MarRechazosCausales causal) {
        this.causal = causal;
    }

    public List<MarRechazosCausales> getCausales() {
        return causales;
    }

    public void setCausales(List<MarRechazosCausales> causales) {
        this.causales = causales;
    }

}
