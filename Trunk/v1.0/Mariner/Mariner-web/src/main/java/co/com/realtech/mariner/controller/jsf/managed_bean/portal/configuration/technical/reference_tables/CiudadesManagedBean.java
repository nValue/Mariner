package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reference_tables;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarCiudades;
import co.com.realtech.mariner.model.entity.MarDepartamentos;
import co.com.realtech.mariner.model.entity.MarPaises;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF para administracion informacion de ciudades
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class CiudadesManagedBean extends GenericManagedBean implements Serializable {
    
    private MarPaises pais;
    private List<MarPaises> paises;
    private MarCiudades ciudad;
    private List<MarCiudades> ciudades;
    private MarDepartamentos departamento;
    private List<MarDepartamentos> departamentos;
    
    public CiudadesManagedBean() {
    }
    
    @Override
    public void init() {
        try {
            setPaises((List<MarPaises>) genericDAOBean.loadAllForEntity(MarPaises.class, "paiNombre asc"));
            if (!getPaises().isEmpty()) {
                setPais(getPaises().get(0));
                filtrarPaises();
            } else {
                setDepartamento(null);
                setCiudades(new ArrayList<>());
            }
        } catch (Exception e) {
            logger.error("Error inicializando CiudadesManagedBean causado por " + e, e);
        }
    }

    /**
     * Filtrado de lista de paises.
     */
    public void filtrarPaises() {
        try {
            setDepartamentos((List<MarDepartamentos>) genericDAOBean.findAllByColumn(MarDepartamentos.class, "paiId", getPais(), true, "depNombre asc"));
            if (!getDepartamentos().isEmpty()) {
                setDepartamento(getDepartamentos().get(0));
                filtrarDepartamento();
            } else {
                setDepartamento(null);
                setCiudades(new ArrayList<>());
            }
        } catch (Exception e) {
            logger.error("Error filtrando lista de departamentos por pais, causado por " + e, e);
        }
    }

    /**
     * Filtrado de lista de ciudades por departamento.
     */
    public void filtrarDepartamento() {
        try {
            setCiudades((List<MarCiudades>) genericDAOBean.findAllByColumn(MarCiudades.class, "depId", getDepartamento(), true, "ciuNombre asc"));
        } catch (Exception e) {
            logger.error("Error filtrando ciudades por departamento, causado por " + e, e);
        }
    }

    /**
     * Crear nueva ciudad
     */
    public void crearCiudad() {
        setCiudad(new MarCiudades());
        getCiudad().setDepId(getDepartamento());
    }

    /**
     * Borrado de ciudad seleccionada.
     */
    public void borrarCiudad() {
        try {
            genericDAOBean.delete(getCiudad(), getCiudad().getCiuId());
            filtrarDepartamento();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Ciudad eliminada correctamente", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se puede eliminar la ciudad seleccionada, por favor intente nuevamente", true, false);
            logger.error("Error eliminando ciudad, causado por: " + e, e);
        }
    }

    /**
     * Guardado de informacion ciudad.
     */
    public void guardarCiudad() {
        try {
            auditSessionUtils.setAuditReflectedValues(getCiudad());
            if (getCiudad().getCiuId() == null) {
                genericDAOBean.save(getCiudad());
            } else {
                genericDAOBean.merge(getCiudad());
            }
            filtrarDepartamento();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Ciudad almacenada correctamente", true, false);
            PrimeFacesContext.execute("PF('dialogCiudad').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se ha podido guardar la ciudad, por favor intente nuevamente", true, false);
            logger.error("Error guardando ciudad, causado por: " + e, e);
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
    
    public MarCiudades getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(MarCiudades ciudad) {
        this.ciudad = ciudad;
    }
    
    public List<MarCiudades> getCiudades() {
        return ciudades;
    }
    
    public void setCiudades(List<MarCiudades> ciudades) {
        this.ciudades = ciudades;
    }
    
}
