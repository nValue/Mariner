package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reference_tables;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador JSF para administracion parametros tipos de documentos
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class TiposDocumentoManagedBean extends GenericManagedBean implements Serializable {
    
    private MarTiposDocumentos tipoDocumento;
    private List<MarTiposDocumentos> tiposDocumentos;
    
    public TiposDocumentoManagedBean() {
    }
    
    @Override
    public void init() {
        try {
            setTiposDocumentos((List<MarTiposDocumentos>) genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre"));
        } catch (Exception e) {
            logger.error("Error inicializando TiposDocumentoManagedBean causado por " + e, e);
        }
    }

    /**
     * Agregar nuevo tipo de documento
     */
    public void agregarTipoDocumento() {
        setTipoDocumento(new MarTiposDocumentos());
    }

    /**
     * Eliminar tipo de documento seleccionado.
     */
    public void eliminarTipoDocumento() {
        try {
            genericDAOBean.delete(getTipoDocumento(), getTipoDocumento().getTdcId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Tipo de documento eliminado correctamente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se puede eliminar el tipo de documento seleccionado, por favor intente nuevamente", true, false);
            logger.error("Error eliminando tipo de documento, causado por: " + e, e);
        }
    }

    /**
     * Guardar tipo de documento.
     */
    public void guardarTipoDocumento() {
        try {
            auditSessionUtils.setAuditReflectedValues(getTipoDocumento());
            if (getTipoDocumento().getTdcId() == null) {
                genericDAOBean.save(getTipoDocumento());
            } else {
                genericDAOBean.merge(getTipoDocumento());
            }
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Tipo de documento guardado correctamente.", true, false);
            PrimeFacesContext.execute("PF('dialogTipoDocumento').hide();");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "No se ha podido guardar el tipo de documento, por favor intente nuevamente", true, false);
            logger.error("Error guardando tipo de documento, causado por: " + e, e);
        }
    }
    
    public MarTiposDocumentos getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(MarTiposDocumentos tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public List<MarTiposDocumentos> getTiposDocumentos() {
        return tiposDocumentos;
    }
    
    public void setTiposDocumentos(List<MarTiposDocumentos> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }
    
}
