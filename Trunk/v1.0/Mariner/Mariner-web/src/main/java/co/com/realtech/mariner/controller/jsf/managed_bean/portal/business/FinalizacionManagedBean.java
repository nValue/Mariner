package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.generic.ClaveValor;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
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
    private List<MarRadicaciones> radicacionesFiltro;
    private MarRadicaciones radicacionSel;
    
    private MarRadicacionesFasesEstados radFaseEstado;
    
    private List<MarRadicacionesFasesEstados> radFasesEstados;
    
    private List<ClaveValor> filtrosBusqueda;
    private ClaveValor filtroBusquedaSel;
    
    private List<ClaveValor> estadosProcesos;
    private ClaveValor estadoProcesoSel;
    
    private String campoBusqueda;
    private String observaciones;
    
    private Date fechaFiltroInic;
    private Date fechaFiltroFin;
    
    @Override
    public void init() {
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
        estadosProcesos = new ArrayList<>();
        estadosProcesos.add(new ClaveValor("P", "Pendientes"));
        estadosProcesos.add(new ClaveValor("A", "Aprobados"));
        estadosProcesos.add(new ClaveValor("R", "Rechazados"));
        
        //Crea la lista con los filtros de búsqueda para el SelectOneMenu.
        filtrosBusqueda = new ArrayList<>();
        filtrosBusqueda.add(new ClaveValor("ES", "Estado"));
        filtrosBusqueda.add(new ClaveValor("RA", "Radicación"));
        filtrosBusqueda.add(new ClaveValor("LI", "Liquidación"));
        //filtrosBusqueda.add(new ClaveValor("OT", "CC Otorgante"));
        //filtrosBusqueda.add(new ClaveValor("RE", "CC Receptor"));
        limpiarVariables();
        
    }
    
    /**
     * Deja las variables cargadas por defecto y realiza la búsqueda.
     */
    public void limpiarVariables(){
        observaciones = "";
        estadoProcesoSel = estadosProcesos.get(0);
        filtroBusquedaSel = filtrosBusqueda.get(0);
        buscarRadicaciones();
    }
    
    /**
     * Limpia los datos de selección de los filtros de búsqueda.
     */
    public void seleccionarFiltroBusq(){
        campoBusqueda = "";
        buscarRadicaciones();
    }
    
    /**
     * Obtiene las radicaciones que no han tenido un concepto de aprobación por parte de las ORIPs.
     */
    public void obtenerRadicacionesPendientes(){
        try {
            radicaciones = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'P-A'", null);
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
            return radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicAValidar).getFesId().getFesNombre();
        } catch (Exception e) {
            String msj = "Ocurrió un error al obtener el último estado de una radicación, causado por : ";
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Último estado no encontrado", msj + e.getMessage(), true, false);
            logger.error(msj + e, e);
        }
        return "N/A";
    }
    
    /**
     * Funcion necesaria para los Converters, obtiene el objeto entero dado una clave
     * @param clave
     * @return 
     */
    public ClaveValor obtenerClaveValor(String clave){
        for (ClaveValor cv : estadosProcesos) {
            if(cv.getClave().equals(clave)){
                return cv;
            }
        }
        for (ClaveValor cv : filtrosBusqueda) {
            if(cv.getClave().equals(clave)){
                return cv;
            }
        }
        return null;
    }
    
    /**
     * Busca la radicacion con los filtros elegidos y por un rango de fechas.
     */
    public void buscarRadicaciones(){
        try {
            if(filtroBusquedaSel.getClave().equals("ES")){
                campoBusqueda = estadoProcesoSel.getClave();
            }
            radicaciones = radicacionesDAOBean.obtenerRadicacionesFinalizacionPorFechasYParametro(filtroBusquedaSel.getClave(), campoBusqueda, fechaFiltroInic, fechaFiltroFin, null);    
            if(!radicaciones.isEmpty()){
                radicacionSel = radicaciones.get(0);
            }
        } catch (Exception e) {
            String mensaje = "No se pueden filtrar las radicaciones con los parámetros seleccionados, debido a: " + e.getMessage();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Filtros erróneos", mensaje, true, false);
            logger.error(mensaje);
        }
    }
    
    /**
     * Obtiene la última fase de una radicación para el modal del detalle.
     */
    public void obtenerUltimaFase(){
        try {
            radFaseEstado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionSel);
            radFasesEstados = (List<MarRadicacionesFasesEstados>)genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacionSel, true, "rfeId");
        } catch (Exception e) {
            String mensaje = "No se puede obtener la última fase de la radicación actual, causado por: " + e.getMessage();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Fase inválida", mensaje, true, false);
            logger.error(mensaje, e);
        }
        
    }
    
    /**
     * Finaliza todo el proceso de la radiación con el último estado.
     */
    public void finalizarRadicacion() {
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionSel);
            if (rfe.getFesId().getFesCodigo().equals("P-A")) {
                BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionSel.getRadId(),
                        "F-A", "A", usuarioSesion.getUsuId(), observaciones, null);
                Integer salida = BDsalida.intValue();
                if (salida == -999) {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                    return;
                }
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Finalización correcta", "Radicación finalizada correctamente", true, false);
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso ya validado", "El proceso ya fue validado por algún otro funcionario mientras estaba siendo consultado", true, false);
            }
            radicacionSel.setRadEstado("F");
            auditSessionUtils.setAuditReflectedValues(radicacionSel);
            genericDAOBean.merge(radicacionSel);
            buscarRadicaciones();
        } catch (Exception e) {
            String mensaje = "No se puede guardar la aprobación, causado por " + e.getMessage();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Finalización pendiente", mensaje, true, false);
            logger.error(mensaje, e);
        }
    }
    
    /**
     * Deja el proceso en estado todo el proceso de la radiación con el último estado.
     */
    public void rechazarRadicacion() {
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionSel);
            if (rfe.getFesId().getFesCodigo().equals("P-A")) {
                BigDecimal BDsalida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionSel.getRadId(),
                        "F-R", "R", usuarioSesion.getUsuId(), observaciones, null);
                Integer salida = BDsalida.intValue();
                if (salida == -999) {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                    return;
                }
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo correcto", "Radicación guardada con estado de rechazo, aunque el proceso en SAP es válido y debe verificarse", true, false);
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Proceso ya validado", "El proceso ya fue validado por algún otro funcionario mientras estaba siendo consultado", true, false);
            }
            radicacionSel.setRadEstado("R");
            auditSessionUtils.setAuditReflectedValues(radicacionSel);
            genericDAOBean.merge(radicacionSel);
            buscarRadicaciones();
        } catch (Exception e) {
            String mensaje = "No se puede guardar el rechazo, causado por " + e.getMessage();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Finalización pendiente", mensaje, true, false);
            logger.error(mensaje, e);
        }
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

    public MarRadicacionesFasesEstados getRadFaseEstado() {
        return radFaseEstado;
    }

    public void setRadFaseEstado(MarRadicacionesFasesEstados radFaseEstado) {
        this.radFaseEstado = radFaseEstado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<MarRadicacionesFasesEstados> getRadFasesEstados() {
        return radFasesEstados;
    }

    public void setRadFasesEstados(List<MarRadicacionesFasesEstados> radFasesEstados) {
        this.radFasesEstados = radFasesEstados;
    }

    public List<MarRadicaciones> getRadicacionesFiltro() {
        return radicacionesFiltro;
    }

    public void setRadicacionesFiltro(List<MarRadicaciones> radicacionesFiltro) {
        this.radicacionesFiltro = radicacionesFiltro;
    }
    
    
    
    

}
