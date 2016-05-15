package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reference_tables;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarNumeraciones;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF para administracion informacion numeracion de la plataforma.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class NumeracionManagedBean extends GenericManagedBean implements Serializable {

    private MarNumeraciones numeracion;
    private List<MarNumeraciones> numeraciones;

    public NumeracionManagedBean() {
    }

    @Override
    public void init() {
        try {
            setNumeraciones((List<MarNumeraciones>) genericDAOBean.loadAllForEntity(MarNumeraciones.class, "numCodigo"));
        } catch (Exception e) {
            logger.error("Error inicializando NumeracionManagedBean causado por " + e, e);
        }
    }

    /**
     * Agregar nueva numeracion.
     */
    public void agregarNumeracion() {
        setNumeracion(new MarNumeraciones());
    }

    /**
     * Eliminar numeracion seleccionada
     */
    public void eliminarNumeracion() {
        try {
            genericDAOBean.delete(getNumeracion(), getNumeracion().getNumId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Numeracion eliminada correctamente..", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se puede eliminar la Numeracion seleccionado, por favor intente nuevamente", true, false);
            logger.error("Error eliminando numeracion, causado por: " + e, e);
        }
    }

    /**
     * Guardar configuracion de numeracion.
     */
    public void guardarNumeracion() {
        try {
            auditSessionUtils.setAuditReflectedValues(getNumeracion());
            if (getNumeracion().getNumId() == null) {
                genericDAOBean.save(getNumeracion());
            } else {
                genericDAOBean.merge(getNumeracion());
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Numeracion almacenada correctamente.", true, false);
            PrimeFacesContext.execute("PF('dialogNumeracion').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se ha podido guardar la configuracion de Numeracion, por favor intente nuevamente", true, false);
            logger.error("Error guardando numeracion, causado por: " + e, e);
        }
    }

    public MarNumeraciones getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(MarNumeraciones numeracion) {
        this.numeracion = numeracion;
    }

    public List<MarNumeraciones> getNumeraciones() {
        return numeraciones;
    }

    public void setNumeraciones(List<MarNumeraciones> numeraciones) {
        this.numeraciones = numeraciones;
    }

}
