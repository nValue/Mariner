package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.administrative;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarCiudades;
import co.com.realtech.mariner.model.entity.MarPaises;
import java.io.Serializable;
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
public class OficinasRegistroManagedBean extends GenericManagedBean implements Serializable{

    private List<MarCiudades> ciudades;
    
    @Override
    public void init() {
        try {
            // Ciudades
            MarPaises paisCol = (MarPaises) genericDAOBean.findByColumn(MarPaises.class, "paiSigla", "CO");
            setCiudades((List<MarCiudades>) genericDAOBean.findAllByColumn(MarCiudades.class, "depId.paiId", paisCol, true, "ciuNombre asc"));
        } catch (Exception e) {
        }
    }

    public List<MarCiudades> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<MarCiudades> ciudades) {
        this.ciudades = ciudades;
    }
    
}
