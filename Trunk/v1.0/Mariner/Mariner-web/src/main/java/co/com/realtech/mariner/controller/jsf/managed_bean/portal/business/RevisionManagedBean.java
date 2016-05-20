/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped

public class RevisionManagedBean extends GenericManagedBean{

    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;
    
    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;
    
    private List<MarRadicaciones> radicacionesPendientes;
    private MarRadicaciones radicacionPendienteSel;
    
    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;
    private MarRadicacionesFasesEstados radicacionFaseEstadoSel;
    
    private List<MarRadicacionesFasesEstados> radicacionesHistorial;
    private MarRadicacionesFasesEstados radicacionHistorialSel;
    
    private Date fechaFiltroInic;
    private Date fechaFiltroFin;
    
    private String observaciones;
    
    @Override
    public void init() {
        fechaFiltroInic = new Date();
        fechaFiltroFin = new Date();
    }
    
    /**
     * Obtiene las radicaciones asignadas al usuario.
     */
    public void obtenerRadicacionesPendientes(){
        try {
            radicacionesPendientes = radicacionesDAOBean.obtenerRadicacionesPorUltimaFase("'G-A'", usuarioSesion);
            if(!radicacionesPendientes.isEmpty()){
                radicacionPendienteSel = radicacionesPendientes.get(0);
                obtenerFasesEstadosDeRadicacion();
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones, causado por : " + e, e);
        }    
    }
    
    /**
     * Trae todas las fases-estados en los que hay pasado una radicación.
     */
    public void obtenerFasesEstadosDeRadicacion(){
        try {
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>)genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacionPendienteSel, true, "rfeId");
            if(!radicacionesFasesEstados.isEmpty()){
                radicacionFaseEstadoSel = radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1);
                //Si el proceso ya está como Revisión-Aprobado, coloca las observaciones
                if(radicacionFaseEstadoSel.getFesId().getFesCodigo().equals("R-A")){
                    observaciones = radicacionFaseEstadoSel.getRfeObservaciones();
                }else{
                    observaciones = "";
                }
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las fases procesos, causado por : " + e, e);
        }
    }
    
    /**
     * Asigna una nueva radicacion al usuario en sesión.
     */
    public void asignarNuevaRadicacion() {
        try {
            //Carga la constante del número máximo de radicaciones que puede tener un validador.
            Integer maxRads = Integer.parseInt(ConstantesUtils.cargarConstante("MAX-VALID-USER"));
            if(radicacionesPendientes.size() > maxRads){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Máximo encontrado", "El usuario tiene el máximo de radicaciones permitidas para el proceso ( " + maxRads + " )", true, false);
                return;
            }
            MarRadicacionesFasesEstados radicacionFaseUltimoEstado;
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_asignar_liquidacion", usuarioSesion.getUsuId().intValue(), "R-P", "");
            Integer salida = BDsalida.intValue();
            switch (salida) {
                case -999:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "No hay datos", "No hay radicaciones disponibles en este momento, por favor intente mas tarde", true, false);
                    return;
                case -1000:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Fase generación necesaria", "No se encuentra una Fase-Estado de código 'R-P' por favor configúrela", true, false);
                    return;
                default:
                    radicacionFaseUltimoEstado = (MarRadicacionesFasesEstados)genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId", BigDecimal.valueOf(salida));
                    if(radicacionFaseUltimoEstado == null){
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valor no encontrado", "No se encontró una radicación-fase-estado para este usuario.", true, false);
                        return;
                    }
                    break;
            }
            MarRadicaciones radObtenida = radicacionFaseUltimoEstado.getRadId();
            System.out.println("radicacionFaseUltimoEstado.getRadId() = " + radicacionFaseUltimoEstado.getRadId());
            obtenerRadicacionesPendientes();
            radicacionPendienteSel = radObtenida;
            System.out.println("radicacionUsuarioSel = " + radicacionPendienteSel);
            obtenerFasesEstadosDeRadicacion();
            System.out.println("radicacionUsuarioSel = " + radicacionPendienteSel);
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación encontrada", "Se ha asignado una nueva radicación a sus pendientes, puede verificarla en la lista desplegable de radicaciones", true, false);
        } catch (Exception e) {
            logger.error("Error asignando una radicación disponible, causado por: " + e, e);
        }
    }
    
    /**
     * Se pasa el proceso a aceptado por parte de los revisores.
     */
    public void validarRadicacion(){
        try {
            MarRadicacionesFasesEstados rfe = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacionPendienteSel);
            if(!rfe.getRfeId().equals(radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1).getRfeId())){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Radicación ya procesada", "Lo sentimos pero esta radicación ya ha sido procesada por la siguiente parte del proceso y no puede ser editada.", true, false);
                return;
            }
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionPendienteSel.getRadId(), 
                    "R-A", "A", usuarioSesion.getUsuId(),observaciones,null);
            Integer salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación incorrecta", "No se puede crear el siguiente estado de la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                return;
            }
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Revisión correcta", "Revisión realizada correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede guardar la aprobación, causado por " + e, e);
        }
    }
    
    /**
     * Rechaza la radicación a un estado anterior o la anula según sea el caso.
     */
    public void rechazarRadicacion(){
        try {
            //Se obtiene el usuario que hizo el proceso para enviarle de vuelta el proceso.
            List<MarRadicacionesFasesEstados> rfes = radicFasesEstadosDAOBean.obtenerRadicFaseEstDeRadyFase(radicacionPendienteSel, "G-S");
            MarUsuarios usuarioAsignado = rfes.get(rfes.size()-1).getUsuId();
            
            //Se crea la fase proceso de rechazo (I-R) con las observaciones colocadas.
            BigDecimal BDsalida = (BigDecimal)genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacionPendienteSel.getRadId(), 
                    "R-R", "R", usuarioAsignado,observaciones,null);
            
            Integer salida = BDsalida.intValue();
            if(salida == -999){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo incorrecto", "No se puede crear el estado rechazo de revisión para la radicación, por favor verifique que la información este correcta e intente de nuevo.", true, false);
                return;
            }
            obtenerRadicacionesPendientes();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo realizado", "Proceso rechazado correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede rechazar la radicación, debido a " + e, e);
        }        
    }
    
    /**
     * Obtiene un historial de todas las radicaciones atendidas por el usuario actual.
     */
    public void obtenerHistorialRadicaciones(){
        try {
            radicacionesHistorial = radicFasesEstadosDAOBean.obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(usuarioSesion, null,fechaFiltroInic, fechaFiltroFin);
            if(!radicacionesHistorial.isEmpty()){
                radicacionHistorialSel = radicacionesHistorial.get(0);
            }
        } catch (Exception e) {
            logger.error("Error obteniendo las radicaciones procesadas, causado por : " + e, e);
        }    
    }

    public List<MarRadicaciones> getRadicacionesPendientes() {
        return radicacionesPendientes;
    }

    public void setRadicacionesPendientes(List<MarRadicaciones> radicacionesPendientes) {
        this.radicacionesPendientes = radicacionesPendientes;
    }

    public MarRadicaciones getRadicacionPendienteSel() {
        return radicacionPendienteSel;
    }

    public void setRadicacionPendienteSel(MarRadicaciones radicacionPendienteSel) {
        this.radicacionPendienteSel = radicacionPendienteSel;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstados() {
        return radicacionesFasesEstados;
    }

    public void setRadicacionesFasesEstados(List<MarRadicacionesFasesEstados> radicacionesFasesEstados) {
        this.radicacionesFasesEstados = radicacionesFasesEstados;
    }

    public MarRadicacionesFasesEstados getRadicacionFaseEstadoSel() {
        return radicacionFaseEstadoSel;
    }

    public void setRadicacionFaseEstadoSel(MarRadicacionesFasesEstados radicacionFaseEstadoSel) {
        this.radicacionFaseEstadoSel = radicacionFaseEstadoSel;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
    
    
    
}
