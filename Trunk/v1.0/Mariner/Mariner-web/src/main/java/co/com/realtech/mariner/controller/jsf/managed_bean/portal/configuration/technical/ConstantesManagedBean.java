package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarConstantes;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 * Constrolador JSF administracion de constantes del sistema
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class ConstantesManagedBean implements Serializable {
    
    final static Logger logger = Logger.getLogger(ConstantesManagedBean.class);
    
    @EJB(beanName = "GenericDAOBean")
    private GenericDAOBeanLocal genericDAOBean;
    
    private MarConstantes constante;
    private List<MarConstantes> constantes;
    private List<MarConstantes> constantesFiltro;
    
    private AuditSessionUtils auditSessionUtils;
    
    public ConstantesManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            auditSessionUtils=AuditSessionUtils.create();
            setConstantes((List<MarConstantes>) genericDAOBean.loadAllForEntity(MarConstantes.class, "conSigla"));
        } catch (Exception e) {
            logger.error("Error inicializando ConstantesManagedBean, causado por " + e);
        }
    }

    /**
     * Agregar nueva constante.
     */
    public void agregarConstante() {
        setConstante(new MarConstantes());
    }

    /**
     * Borrado de constante seleccionada.
     */
    public void eliminarConstante() {
        try {
            genericDAOBean.delete(getConstante(), getConstante().getConId());
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Constante eliminada correctamente en la base de datos", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se pudo eliminar la Constante seleccionada, por favor intente nuevamente", true, false);
            logger.error("Error borrando constante, causado por " + e);
        }
    }

    /**
     * Guardar constante ingresada / modificada en el panel de edicion.
     */
    public void guardarConstante() {
        try {
            auditSessionUtils.setAuditReflectedValues(getConstante());
            if (getConstante().getConId() == null) {
                genericDAOBean.save(getConstante());
            } else {
                genericDAOBean.merge(getConstante());
            }            
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Informacion de constante almacenada correctamente en la base de datos", true, false);
            PrimeFacesContext.execute("PF('dialogConstante').hide();");
        } catch (Exception e) {
            e.printStackTrace();
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero en este momento no es posible almacenar la informacion, por favor intente nuevamente", true, false);
            logger.error("Error guardando constante, causado por " + e);
        }
    }
    
    public MarConstantes getConstante() {
        return constante;
    }
    
    public void setConstante(MarConstantes constante) {
        this.constante = constante;
    }
    
    public List<MarConstantes> getConstantes() {
        return constantes;
    }
    
    public void setConstantes(List<MarConstantes> constantes) {
        this.constantes = constantes;
    }
    
    public List<MarConstantes> getConstantesFiltro() {
        return constantesFiltro;
    }
    
    public void setConstantesFiltro(List<MarConstantes> constantesFiltro) {
        this.constantesFiltro = constantesFiltro;
    }
}
