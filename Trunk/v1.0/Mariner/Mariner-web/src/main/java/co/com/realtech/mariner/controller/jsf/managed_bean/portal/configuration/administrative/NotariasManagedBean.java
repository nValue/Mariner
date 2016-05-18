package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.administrative;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarCiudades;
import co.com.realtech.mariner.model.entity.MarNotarias;
import co.com.realtech.mariner.model.entity.MarOficinasRegistro;
import co.com.realtech.mariner.model.entity.MarPaises;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF administracion de informacion de Notarias.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK 1.0
 */
@ManagedBean
@ViewScoped
public class NotariasManagedBean extends GenericManagedBean implements Serializable {

    private MarNotarias notaria;
    private List<MarNotarias> notarias;
    private List<MarOficinasRegistro> oficinasRegistro;
    private List<MarNotarias> notariasFiltrado;

    @Override
    public void init() {
        try {
            
            setNotarias((List<MarNotarias>) genericDAOBean.loadAllForEntity(MarNotarias.class, "notNombre asc"));
        } catch (Exception e) {
            logger.error("Error inicializando NotariasManagedBean, causado por " + e);
        }
    }
    /**
     * Actualizacion de oficinas de registro.
     */
    public void actualizarOficinasRegistro(){
        try {
            setOficinasRegistro((List<MarOficinasRegistro>) genericDAOBean.findAllByColumn(MarOficinasRegistro.class, "morEstado", "A", true, "morNombre asc"));
        } catch (Exception e) {
            logger.error("Error actualizando lista de oficinas de registro, causado por " + e);
        }
    }

    /**
     * Creacion de nuevo rol
     */
    public void agregarNotaria() {
        setNotaria(new MarNotarias());
    }

    /**
     * Borrado de Notaria seleccionada..
     */
    public void eliminarNotaria() {
        try {
            genericDAOBean.delete(getNotaria(), getNotaria().getNotId());
            init();
            PrimeFacesContext.execute("PF('notariasWV').clearFilters();");
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Notaria eliminada correctamente", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo eliminar la Notaria, por favor intente nuevamente", true, false);
            logger.error("Error borrando Notaria, causado por " + e);
        }
    }

    /**
     * Guardado de informacion de la notaria.
     */
    public void guardarNotaria() {
        try {
            auditSessionUtils.setAuditReflectedValues(getNotaria());

            if (getNotaria().getNotId() == null) {
                genericDAOBean.save(getNotaria());
            } else {
                genericDAOBean.merge(getNotaria());
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Configuracion de Notaria guardada correctamente", true, false);
            PrimeFacesContext.execute("PF('dialogNotaria').hide();");
            PrimeFacesContext.execute("PF('notariasWV').clearFilters();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo guardar la Notaria, por favor intente nuevamente", true, false);
            logger.error("Error guardando Notaria, causado por " + e);
        }
    }

    public MarNotarias getNotaria() {
        return notaria;
    }

    public void setNotaria(MarNotarias notaria) {
        this.notaria = notaria;
    }

    public List<MarNotarias> getNotarias() {
        return notarias;
    }

    public void setNotarias(List<MarNotarias> notarias) {
        this.notarias = notarias;
    }

    public List<MarNotarias> getNotariasFiltrado() {
        return notariasFiltrado;
    }

    public void setNotariasFiltrado(List<MarNotarias> notariasFiltrado) {
        this.notariasFiltrado = notariasFiltrado;
    }

    public List<MarOficinasRegistro> getOficinasRegistro() {
        return oficinasRegistro;
    }

    public void setOficinasRegistro(List<MarOficinasRegistro> oficinasRegistro) {
        this.oficinasRegistro = oficinasRegistro;
    }

}
