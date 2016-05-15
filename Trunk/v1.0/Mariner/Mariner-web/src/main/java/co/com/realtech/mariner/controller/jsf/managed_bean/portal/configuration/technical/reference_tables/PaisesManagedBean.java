package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reference_tables;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarDepartamentos;
import co.com.realtech.mariner.model.entity.MarPaises;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF para administracion informacion de paises y departamento.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class PaisesManagedBean extends GenericManagedBean implements Serializable {

    private MarPaises pais;
    private List<MarPaises> paises;
    private MarDepartamentos departamento;
    private List<MarDepartamentos> departamentos;

    public PaisesManagedBean() {
    }

    @Override
    public void init() {
        try {
            setPaises((List<MarPaises>) genericDAOBean.loadAllForEntity(MarPaises.class, "paiNombre asc"));
            if (!getPaises().isEmpty()) {
                setPais(getPaises().get(0));
                filtrarPaises();
            }
        } catch (Exception e) {
            logger.error("Error inicializando PaisesManagedBean causado por " + e, e);
        }
    }

    /**
     * Filtrado de lista de paises.
     */
    public void filtrarPaises() {
        try {
            setDepartamentos((List<MarDepartamentos>) genericDAOBean.findAllByColumn(MarDepartamentos.class, "paiId", getPais(), true, "depNombre asc"));
        } catch (Exception e) {
            logger.error("Error filtrando departamentos de pais seleccionado, causado por " + e, e);
        }
    }

    /**
     * Agregar nuevo pais.
     */
    public void crearPais() {
        setPais(new MarPaises());
    }

    /**
     * Crear nuevo departamento.
     */
    public void crearDepartamento() {
        setDepartamento(new MarDepartamentos());
        getDepartamento().setPaiId(getPais());
    }

    /**
     * Borrado de pais seleccionado.
     */
    public void eliminarPais() {
        try {
            genericDAOBean.delete(getPais(), getPais().getPaiId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de pais eliminada correctamente", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se puede eliminar el pais seleccionado, por favor intente nuevamente", true, false);
            logger.error("Error eliminando pais, causado por: " + e, e);
        }
    }

    /**
     * Borrado de departamento seleccionado.
     */
    public void eliminarDepartamento() {
        try {
            genericDAOBean.delete(getDepartamento(), getDepartamento().getDepId());
            filtrarPaises();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Departamento eliminado correctamente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se puede eliminar el Departamento seleccionado, por favor intente nuevamente", true, false);
            logger.error("Error eliminando departamento, causado por: " + e, e);
        }
    }

    /**
     * Guardar configuracion de pais.
     */
    public void guardarPais() {
        try {
            auditSessionUtils.setAuditReflectedValues(getPais());
            if (getPais().getPaiId() == null) {
                genericDAOBean.save(getPais());
            } else {
                genericDAOBean.merge(getPais());
            }
            setPaises((List<MarPaises>) genericDAOBean.loadAllForEntity(MarPaises.class, "paiNombre asc"));
            filtrarPaises();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Pais almacenada correctamente.", true, false);
            PrimeFacesContext.execute("PF('dialogPais').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se ha podido guardar la configuracion del pais, por favor intente nuevamente", true, false);
            logger.error("Error guardando pais, causado por: " + e, e);
        }
    }

    /**
     * Guardar configuracion de departamento.
     */
    public void guardarDepartamento() {
        try {
            auditSessionUtils.setAuditReflectedValues(getDepartamento());
            if (getDepartamento().getDepId() == null) {
                genericDAOBean.save(getDepartamento());
            } else {
                genericDAOBean.merge(getDepartamento());
            }
            filtrarPaises();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Departamento almacenada correctamente.", true, false);
            PrimeFacesContext.execute("PF('dialogDepartamento').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se ha podido guardar la configuracion del Departamento, por favor intente nuevamente", true, false);
            logger.error("Error guardando Departamento, causado por: " + e, e);
        }
    }

    public MarPaises getPais() {
        return pais;
    }

    public void setPais(MarPaises pais) {
        this.pais = pais;
    }

    public List<MarPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<MarPaises> paises) {
        this.paises = paises;
    }

    public MarDepartamentos getDepartamento() {
        return departamento;
    }

    public void setDepartamento(MarDepartamentos departamento) {
        this.departamento = departamento;
    }

    public List<MarDepartamentos> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<MarDepartamentos> departamentos) {
        this.departamentos = departamentos;
    }

}
