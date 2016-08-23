package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reference_tables;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarPrioridades;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF para administracion de las prioridades
 *
 * @author Fabián Agudelo
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class PrioridadesManagedBean extends GenericManagedBean implements Serializable {

    private MarPrioridades prioridadSel;
    private List<MarPrioridades> prioridades;

    public PrioridadesManagedBean() {
    }

    @Override
    public void init() {
        try {
            prioridades = ((List<MarPrioridades>) genericDAOBean.loadAllForEntity(MarPrioridades.class, "priOrden"));
        } catch (Exception e) {
            logger.error("Error inicializando PrioridadesManagedBean causado por " + e, e);
        }
    }

    /**
     * Agregar nueva causal.
     */
    public void agregarPrioridad() {
        prioridadSel = new MarPrioridades();
    }

    /**
     * Eliminar numeracion seleccionada
     */
    public void eliminarPrioridad() {
        try {
            genericDAOBean.delete(prioridadSel, prioridadSel.getPriId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Causal de rechazo eliminada correctamente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se puede eliminar la prioridad seleccionada, por favor intente nuevamente", true, false);
            logger.error("Error eliminando prioridad, causado por: " + e, e);
        }
    }

    /**
     * Guardar configuracion de causal de rechazo..
     */
    public void guardarPrioridad() {
        try {
            auditSessionUtils.setAuditReflectedValues(prioridadSel);
            if (prioridadSel.getPriId() == null) {
                genericDAOBean.save(prioridadSel);
            } else {
                genericDAOBean.merge(prioridadSel);
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de prioridad almacenada correctamente.", true, false);
            PrimeFacesContext.execute("PF('dialogPrioridades').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se ha podido guardar la prioridad, por favor intente nuevamente", true, false);
            logger.error("Error guardando prioridad, causado por: " + e, e);
        }
    }

    public MarPrioridades getPrioridadSel() {
        return prioridadSel;
    }

    public void setPrioridadSel(MarPrioridades prioridadSel) {
        this.prioridadSel = prioridadSel;
    }

    public List<MarPrioridades> getPrioridades() {
        return prioridades;
    }

    public void setPrioridades(List<MarPrioridades> prioridades) {
        this.prioridades = prioridades;
    }

    

}
