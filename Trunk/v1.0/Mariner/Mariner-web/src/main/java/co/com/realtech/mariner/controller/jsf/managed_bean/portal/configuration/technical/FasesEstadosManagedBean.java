package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarFases;
import co.com.realtech.mariner.model.entity.MarFasesEstados;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de la administracion de Faes y Estados de la
 * aplicacion.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class FasesEstadosManagedBean extends GenericManagedBean implements Serializable {

    private MarFases fase;
    private List<MarFases> fases;
    private MarFasesEstados estado;
    private List<MarFasesEstados> estados;

    public FasesEstadosManagedBean() {
    }

    @Override
    public void init() {
        try {
            setFases((List<MarFases>) genericDAOBean.loadAllForEntity(MarFases.class, "fasOrden asc"));
            if (!getFases().isEmpty()) {
                setFase(getFases().get(0));
                filtrarFasesEstados();
            }
        } catch (Exception e) {
            logger.error("Error inicializando FasesEstadosManagedBean, causado por " + e);
        }
    }

    /**
     * Filtrado de estados vinculados a la Fase.
     */
    public void filtrarFasesEstados() {
        try {
            setEstados((List<MarFasesEstados>) genericDAOBean.findAllByColumn(MarFasesEstados.class, "fasId", getFase(), true, "fesOrden asc"));
        } catch (Exception e) {
            logger.error("Error realizando filtrado de estados vinculados a la fase, causado por " + e);
        }
    }

    /**
     * Creacion de nueva fase.
     */
    public void crearFase() {
        setFase(new MarFases());
    }

    /**
     * Crercion de nuevo estado Fase
     */
    public void crearEstadoFase() {
        setEstado(new MarFasesEstados());
        getEstado().setFasId(getFase());
    }

    /**
     * Borrado de fase seleccionada.
     */
    public void eliminarFase() {
        try {
            genericDAOBean.delete(getFase(), getFase().getFasId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Fase eliminada correctamente en la base de datos", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo eliminar la Fase seleccionada, por favor intente nuevamente", true, false);
            logger.error("Error borrando Fase, causado por " + e);
        }
    }

    /**
     * Borrado de estado Fase.
     */
    public void eliminarEstadoFase() {
        try {
            genericDAOBean.delete(getEstado(), getEstado().getFesId());
            filtrarFasesEstados();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Estado Fase eliminada correctamente en la base de datos", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo eliminar el Estado Fase seleccionado, por favor intente nuevamente", true, false);
            logger.error("Error borrando Estado - Fase, causado por " + e);
        }
    }

    /**
     * Guardado de Fase.
     */
    public void guardarFase() {
        try {
            auditSessionUtils.setAuditReflectedValues(getFase());

            if (getFase().getFasId() == null) {
                genericDAOBean.save(getFase());
            } else {
                genericDAOBean.merge(getFase());
            }
            setFases((List<MarFases>) genericDAOBean.loadAllForEntity(MarFases.class, "fasOrden asc"));
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Fase almacenada correctamente en la base de datos", true, false);
            PrimeFacesContext.execute("PF('dialogFase').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo guardar la Fase, por favor intente nuevamente", true, false);
            logger.error("Error guardando Fase, causado por " + e);
        }
    }

    public void guardarEstadoFase() {
        try {
            auditSessionUtils.setAuditReflectedValues(getEstado());

            if (getEstado().getFesId() == null) {
                genericDAOBean.save(getEstado());
            } else {
                genericDAOBean.merge(getEstado());
            }
            filtrarFasesEstados();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Estado Fase almacenada correctamente en la base de datos", true, false);
            PrimeFacesContext.execute("PF('dialogEstado').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo guardar el Estado de la  Fase, por favor intente nuevamente", true, false);
            logger.error("Error guardando Estado Fase, causado por " + e);
        }
    }

    public MarFases getFase() {
        return fase;
    }

    public void setFase(MarFases fase) {
        this.fase = fase;
    }

    public List<MarFases> getFases() {
        return fases;
    }

    public void setFases(List<MarFases> fases) {
        this.fases = fases;
    }

    public MarFasesEstados getEstado() {
        return estado;
    }

    public void setEstado(MarFasesEstados estado) {
        this.estado = estado;
    }

    public List<MarFasesEstados> getEstados() {
        return estados;
    }

    public void setEstados(List<MarFasesEstados> estados) {
        this.estados = estados;
    }

}
