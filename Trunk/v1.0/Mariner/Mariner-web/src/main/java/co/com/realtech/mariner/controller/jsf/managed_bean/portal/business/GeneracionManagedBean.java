package co.com.realtech.mariner.controller.jsf.managed_bean.portal.business;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarFasesEstados;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de asignar las radicaciones a los liquidadores y enviar la info a SAP
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class GeneracionManagedBean extends GenericManagedBean{
    
    private List<MarRadicaciones> radicacionesUsuario;
    private MarRadicaciones radicacionUsuarioSel;
    private MarRadicacionesFasesEstados radFaseEstadoActual;
    
    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;
    
    private List<MarRadicacionesFasesEstados> radicacionesFasesEstados;
    
    private String observaciones;
    
    @Override
    public void init() {
        obtenerRadicaciones();
    }
    
    /**
     * Obtiene las radicaciones asignadas al usuario.
     */
    public void obtenerRadicaciones(){
        try {
            radicacionesUsuario = radicacionesDAOBean.obtenerRadicacionesPorUsuarioYFase(usuarioSesion, "GEN");
            if(!radicacionesUsuario.isEmpty()){
                radicacionUsuarioSel = radicacionesUsuario.get(0);
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
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>)genericDAOBean.findAllByColumn(MarRadicacionesFasesEstados.class, "radId", radicacionUsuarioSel, true, "rfeId");
            if(!radicacionesFasesEstados.isEmpty()){
                radFaseEstadoActual = radicacionesFasesEstados.get(radicacionesFasesEstados.size()-1);
                if(radFaseEstadoActual.getFesId().getFesCodigo().equals("G-P")){
                    observaciones = radFaseEstadoActual.getRfeObservaciones();
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
            radFaseEstadoActual = null;
            Integer salida = (Integer)genericDAOBean.callGenericFunction("fn_asignar_liquidacion", usuarioSesion.getUsuId(), "I-P", "");
            System.out.println("salida = " + salida);
            if(salida != null) switch (salida) {
                case -999:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "No hay datos", "No hay radicaciones disponibles en este momento, por favor intente mas tarde", true, false);
                    return;
                case -1000:
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Fase generación necesaria", "No se encuentra una Fase-Estado de código 'G-P' por favor configúrela", true, false);
                    return;
                default:
                    radFaseEstadoActual = (MarRadicacionesFasesEstados)genericDAOBean.findByColumn(MarRadicacionesFasesEstados.class, "rfeId", BigDecimal.valueOf(salida));
                    if(radFaseEstadoActual == null){
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valor no encontrado", "No se encontró una radicación-fase-estado para este usuario.", true, false);
                        return;
                    }
                    break;
            }
            obtenerRadicaciones();
            radicacionUsuarioSel = radFaseEstadoActual.getRadId();
            obtenerFasesEstadosDeRadicacion();
        } catch (Exception e) {
            logger.error("Error asignando una radicación disponible, causado por: " + e, e);
        }
    }
    
    /**
     * Se pasa el proceso a pendiente por subir en el ERP SAP.
     */
    public void validarPendienteSAP(){
        try {
            MarRadicacionesFasesEstados radFaseEstado = new MarRadicacionesFasesEstados();
            MarFasesEstados fasEstSAP = (MarFasesEstados)genericDAOBean.findByColumn(MarFasesEstados.class, "fesCodigo", "G-S");
            radFaseEstado.setFesId(fasEstSAP);
            radFaseEstado.setRadId(radicacionUsuarioSel);
            radFaseEstado.setRfeEstado("A");
            radFaseEstado.setRfeEstadoAprobacion("A");
            radFaseEstado.setRfeFechaInicio(new Date());
            radFaseEstado.setUsuId(usuarioSesion);
            radFaseEstado.setRfeObservaciones(observaciones);
            auditSessionUtils.setAuditReflectedValues(radFaseEstado);
            genericDAOBean.save(radFaseEstado);
            obtenerRadicaciones();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Validación correcta", "Validación realizada correctamente", true, false);
        } catch (Exception e) {
            logger.error("No se puede guardar la aprobación, causado por " + e, e);
        }
    }
    
    /**
     * Rechaza la radicación a un estado anterior o la anula según sea el caso.
     */
    public void rechazarRadicacion(){
        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Rechazo", "Opps!, rechazo en desarrollo...", true, false);
    }
    

    public List<MarRadicaciones> getRadicacionesUsuario() {
        return radicacionesUsuario;
    }

    public void setRadicacionesUsuario(List<MarRadicaciones> radicacionesUsuario) {
        this.radicacionesUsuario = radicacionesUsuario;
    }

    public MarRadicaciones getRadicacionUsuarioSel() {
        return radicacionUsuarioSel;
    }

    public void setRadicacionUsuarioSel(MarRadicaciones radicacionUsuarioSel) {
        this.radicacionUsuarioSel = radicacionUsuarioSel;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesFasesEstados() {
        return radicacionesFasesEstados;
    }

    public void setRadicacionesFasesEstados(List<MarRadicacionesFasesEstados> radicacionesFasesEstados) {
        this.radicacionesFasesEstados = radicacionesFasesEstados;
    }

    public MarRadicacionesFasesEstados getRadFaseEstadoActual() {
        return radFaseEstadoActual;
    }

    public void setRadFaseEstadoActual(MarRadicacionesFasesEstados radFaseEstadoActual) {
        this.radFaseEstadoActual = radFaseEstadoActual;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    
}
