package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.generic.ClaveValor;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de la validación y autorización de la pantalla de 
 * finalización de proceso por parte de las ORIPs
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class FinalizacionManagedBean extends GenericManagedBean{
    
    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;
    
    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;
    
    private List<MarRadicaciones> radicaciones;
    private MarRadicaciones radicacionSel;
    
    private List<ClaveValor> filtrosBusqueda;
    private ClaveValor filtroBusquedaSel;
    
    private List<ClaveValor> estadosProcesos;
    private ClaveValor estadoProcesoSel;
    
    private String campoBusqueda;
    
    private Date fechaFiltroInic;
    private Date fechaFiltroFin;
    
    @Override
    public void init() {
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        obtenerFiltrosBusqueda();
        obtenerRadicacionesPendientes();
        estadosProcesos.add(new ClaveValor("P", "Pendientes"));
        estadosProcesos.add(new ClaveValor("A", "Aprobados"));
        estadosProcesos.add(new ClaveValor("R", "Rechazados"));
    }
    
    /**
     * Crea la lista con los filtros de búsqueda para el SelectOneMenu.
     */
    public void obtenerFiltrosBusqueda(){
        filtrosBusqueda = new ArrayList<>();
        filtrosBusqueda.add(new ClaveValor("EP", "Estado proceso"));
        filtrosBusqueda.add(new ClaveValor("NR", "Número Radicación"));
        filtrosBusqueda.add(new ClaveValor("LI", "Liquidación"));
        filtrosBusqueda.add(new ClaveValor("CC", "Cédula"));
    }
    
    /**
     * Obtiene las radicaciones que no han tenido un concepto de aprobación por parte de las ORIPs.
     */
    public void obtenerRadicacionesPendientes(){
        try {
            radicaciones = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("P-A", null);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valor no encontrado", "No se pueden traer las radicaciones pendientes: " + e.getMessage(), true, false);
            logger.error("No se pueden traer las radicaciones pendientes, causado por: " + e, e);
        }
    }
    
    /**
     * Dada una radicación, retorna el último estado vigente.
     * @param radicAValidar
     * @return 
     */
    public String obtenerUltimoEstado(MarRadicaciones radicAValidar){
        try {
            return radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicAValidar).getFesId().getFesCodigo();
        } catch (Exception e) {
            String msj = "Ocurrió un error al obtener el último estado de una radicación, causado por : ";
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Último estado no encontrado", msj + e.getMessage(), true, false);
            logger.error(msj + e, e);
        }
        return "N/A";
    }
    
    /**
     * Busca la radicacion con los filtros elegidos y por un rango de fechas.
     */
    public void buscarRadicaciones(){
        
    }

    public List<MarRadicaciones> getRadicaciones() {
        return radicaciones;
    }

    public void setRadicaciones(List<MarRadicaciones> radicaciones) {
        this.radicaciones = radicaciones;
    }

    public MarRadicaciones getRadicacionSel() {
        return radicacionSel;
    }

    public void setRadicacionSel(MarRadicaciones radicacionSel) {
        this.radicacionSel = radicacionSel;
    }

    public List<ClaveValor> getEstadosProcesos() {
        return estadosProcesos;
    }

    public void setEstadosProcesos(List<ClaveValor> estadosProcesos) {
        this.estadosProcesos = estadosProcesos;
    }

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    public Date getFechaFiltroInic() {
        return fechaFiltroInic;
    }

    public void setFechaFiltroInic(Date fechaFiltroInic) {
        this.fechaFiltroInic = fechaFiltroInic;
    }

    public Date getFechaFiltroFin() {
        return fechaFiltroFin;
    }

    public void setFechaFiltroFin(Date fechaFiltroFin) {
        this.fechaFiltroFin = fechaFiltroFin;
    }

    public List<ClaveValor> getFiltrosBusqueda() {
        return filtrosBusqueda;
    }

    public void setFiltrosBusqueda(List<ClaveValor> filtrosBusqueda) {
        this.filtrosBusqueda = filtrosBusqueda;
    }

    public ClaveValor getFiltroBusquedaSel() {
        return filtroBusquedaSel;
    }

    public void setFiltroBusquedaSel(ClaveValor filtroBusquedaSel) {
        this.filtroBusquedaSel = filtroBusquedaSel;
    }

    public ClaveValor getEstadoProcesoSel() {
        return estadoProcesoSel;
    }

    public void setEstadoProcesoSel(ClaveValor estadoProcesoSel) {
        this.estadoProcesoSel = estadoProcesoSel;
    }
    
    
    
    

}
