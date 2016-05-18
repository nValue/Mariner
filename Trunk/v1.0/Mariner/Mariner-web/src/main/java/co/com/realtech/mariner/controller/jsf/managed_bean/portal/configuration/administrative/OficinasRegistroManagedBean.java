package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.administrative;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarCiudades;
import co.com.realtech.mariner.model.entity.MarOficinasRegistros;
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
 * Controlador JSF administracion oficinas de registro.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class OficinasRegistroManagedBean extends GenericManagedBean implements Serializable {

    private List<MarCiudades> ciudades;
    private MarOficinasRegistros oficina;
    private List<MarOficinasRegistros> oficinas;
    private List<MarOficinasRegistros> oficinasFiltrado;

    @Override
    public void init() {
        try {
            // Ciudades
            MarPaises paisCol = (MarPaises) genericDAOBean.findByColumn(MarPaises.class, "paiSigla", "CO");
            setCiudades((List<MarCiudades>) genericDAOBean.findAllByColumn(MarCiudades.class, "depId.paiId", paisCol, true, "ciuNombre asc"));
            setOficinas((List<MarOficinasRegistros>) genericDAOBean.loadAllForEntity(MarOficinasRegistros.class, "morNombre asc"));
        } catch (Exception e) {
            logger.error("Error inicializando OficinasRegistroManagedBean, causado por:" + e);
        }
    }

    /**
     * Creacion de nuevo rol
     */
    public void agregarOficina() {
        setOficina(new MarOficinasRegistros());
    }

    /**
     * Borrado de Notaria seleccionada..
     */
    public void eliminarOficina() {
        try {
            genericDAOBean.delete(getOficina(), getOficina().getMorId());
            init();
            PrimeFacesContext.execute("PF('oficinasWV').clearFilters();");
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Oficina de Registro eliminada correctamente", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo eliminar la Oficina de Registro, por favor intente nuevamente", true, false);
            logger.error("Error borrando Notaria, causado por " + e);
        }
    }

    /**
     * Guardado de informacion de la notaria.
     */
    public void guardarOficina() {
        try {
            auditSessionUtils.setAuditReflectedValues(getOficina());

            if (getOficina().getMorId() == null) {
                genericDAOBean.save(getOficina());
            } else {
                genericDAOBean.merge(getOficina());
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Oficina de Registro guardada correctamente", true, false);
            PrimeFacesContext.execute("PF('dialogOficina').hide();");
            PrimeFacesContext.execute("PF('oficinasWV').clearFilters();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo guardar la Oficina de Registro, por favor intente nuevamente", true, false);
            logger.error("Error guardando Notaria, causado por " + e);
        }
    }
    /**
     * Metodo de autocomplete para lista de Ciudades
     * @param query
     * @return 
     */
    public List<MarCiudades> autoCompleteCiudad(String query) {
        List<MarCiudades> allCities = getCiudades();
        List<MarCiudades> filteredCities = new ArrayList<>();
         
        for (MarCiudades ciudad : allCities) {
            if(ciudad.getCiuNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredCities.add(ciudad);
            }
        }         
        return filteredCities;
    }

    public List<MarCiudades> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<MarCiudades> ciudades) {
        this.ciudades = ciudades;
    }

    public MarOficinasRegistros getOficina() {
        return oficina;
    }

    public void setOficina(MarOficinasRegistros oficina) {
        this.oficina = oficina;
    }

    public List<MarOficinasRegistros> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<MarOficinasRegistros> oficinas) {
        this.oficinas = oficinas;
    }

    public List<MarOficinasRegistros> getOficinasFiltrado() {
        return oficinasFiltrado;
    }

    public void setOficinasFiltrado(List<MarOficinasRegistros> oficinasFiltrado) {
        this.oficinasFiltrado = oficinasFiltrado;
    }

}
