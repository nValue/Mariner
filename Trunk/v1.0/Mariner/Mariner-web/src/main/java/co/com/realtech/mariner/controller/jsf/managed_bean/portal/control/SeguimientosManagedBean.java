package co.com.realtech.mariner.controller.jsf.managed_bean.portal.control;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.seguimientos.SeguimientosDAOBeanLocal;
import co.com.realtech.mariner.model.entity.NvaSeguimiento;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado del manejo de seguimientos en la plataforma.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class SeguimientosManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "SeguimientosDAOBean")
    protected SeguimientosDAOBeanLocal seguimientosDAOBean;

    private String clave;
    private Date fechaInicial;
    private Date fechaFinal;
    private NvaSeguimiento seguimiento;
    private List<NvaSeguimiento> seguimientos;
    private List<NvaSeguimiento> seguimientosFiltrado;

    public SeguimientosManagedBean() {
    }

    @Override
    public void init() {
        Calendar cal = Calendar.getInstance();
        setFechaFinal(cal.getTime());        
        cal.add(Calendar.DATE, -1);
        setFechaInicial(cal.getTime());
    }

    /**
     * Filtrar seguimiento seguin fechas y filtro de texto.
     */
    public void filtrarSeguimiento() {
        try {
            setSeguimientos(seguimientosDAOBean.filtradoSeguimientos(getFechaInicial(), getFechaFinal(), getClave()));
            if (getSeguimientos().isEmpty()) {
                PrimeFacesPopup.lanzarDialog(Effects.Fold, "Notificacion", "No se han encontrado seguimientos vinculados la filtro ingresado.", true, false);
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Filtro de seguimientos realizado correctamente.", true, false);
            }
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ocurrio un error intentando obtener el seguimiento, por favor intente nuevamente.", true, false);
            logger.error("Error realizando filtrado de seguimiento, causado por " + e);
        }
    }

    /**
     * Limpiado de filtro.
     */
    public void limpiarFiltro() {
        setClave("");
        setSeguimientos(new ArrayList<>());
        setSeguimientosFiltrado(new ArrayList<>());
        PrimeFacesContext.execute("PF('wvSeguimiento').clearFilters();");
    }

    public NvaSeguimiento getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(NvaSeguimiento seguimiento) {
        this.seguimiento = seguimiento;
    }

    public List<NvaSeguimiento> getSeguimientos() {
        return seguimientos;
    }

    public void setSeguimientos(List<NvaSeguimiento> seguimientos) {
        this.seguimientos = seguimientos;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<NvaSeguimiento> getSeguimientosFiltrado() {
        return seguimientosFiltrado;
    }

    public void setSeguimientosFiltrado(List<NvaSeguimiento> seguimientosFiltrado) {
        this.seguimientosFiltrado = seguimientosFiltrado;
    }

}
