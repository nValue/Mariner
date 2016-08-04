package co.com.realtech.mariner.controller.jsf.managed_bean.portal.control;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.util.cdf.CDFFileDispatcher;
import co.com.realtech.mariner.util.files.PDFUtils;
import co.com.realtech.mariner.util.jsf.file.FileDownloader;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;

/**
 * ManagedBean encargado de la impresión de los recibos de caja.
 *
 * @author Fabián Agudelo
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class ImpresionManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    private String tipoBusqueda;
    private String codigoBusqueda;
    private List<MarRadicacionesFasesEstados> radicacionesEstados;
    private MarRadicacionesFasesEstados radicacionEstadoSel;
    private Date fechaInicial;
    private Date fechaFinal;
    private String campoBusqueda;

    @Override
    public void init() {
        fechaInicial = new Date();
        fechaFinal = new Date();
        tipoBusqueda = "P";
        seleccionarTipo();
    }
    
    /**
     * Selecciona el tipo de filtro para la impresión de los pagos.
     */
    public void seleccionarTipo(){
        radicacionEstadoSel = null;
        radicacionesEstados = radicFasesEstadosDAOBean.obtenerUltimaFaseFechaImpreso("'R-A'", fechaInicial, fechaFinal, tipoBusqueda.equals("I"));
        if(!radicacionesEstados.isEmpty()){
            radicacionEstadoSel = radicacionesEstados.get(0);
        }
    }
    
    /**
     * Busca la radicación, siempre y cuando haya quedado en estado pendiente de pago o más.
     */
    public void buscarRadicacion(){
        try {
            radicacionEstadoSel = radicFasesEstadosDAOBean.obtenerRadicConRecibo(campoBusqueda.toUpperCase());
            if(radicacionEstadoSel == null){
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Radicación no encontrada", "Esta radicación no existe, o no ha sido aprobada por un validador", true, false);
            }
        } catch (Exception e) {
            String msj = "No se puede buscar la radicación, causado por " + e.getMessage();
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", msj, true, false);
            logger.error(msj,e);
        }
    }
    
    /**
     * Descargar recibo de pago.
     */
    public void descargarRecibo() {
        try {
            if (validarTransaccion()) {
                /*
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String contextPath = servletContext.getContextPath();
                String urlRecibo = contextPath + "/static/fileDispatcher/" + radicacionEstadoSel.getRadId().getArcIdReciboPago().getArcId() + "-" + radicacionEstadoSel.getRadId().getArcIdReciboPago().getArcHash() + "." + radicacionEstadoSel.getRadId().getArcIdReciboPago().getArcExtension();
                RequestContext.getCurrentInstance().execute("window.open('" + urlRecibo + "');");
                */
                CDFFileDispatcher dispatcher = CDFFileDispatcher.create();
                dispatcher.findFile(radicacionEstadoSel.getRadId().getArcIdReciboPago().getArcId());
                String label = "";
                if(radicacionEstadoSel.getRadId().getRadTurno() == null || radicacionEstadoSel.getRadId().getRadTurno().isEmpty()){
                }else{
                    label = "Turno: " + radicacionEstadoSel.getRadId().getRadTurno() + " - " ;
                }
                label = label + radicacionEstadoSel.getRadId().getRadNumero();
                File archivo = PDFUtils.agregarTexto(dispatcher.getFileContent(), label);
                if(archivo.exists()){
                    byte[] bytes = IOUtils.toByteArray(new FileInputStream(archivo));
                    new FileDownloader().descargarArchivoFacesContext(FacesContext.getCurrentInstance(), bytes, archivo.getName() + ".pdf");
                }else{
                    PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "No se encuentra el archivo con la impresión de turno", true, false);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Ingresa la transacción para pago en bancos si no está creada.
     *
     */
    public boolean validarTransaccion() {
        System.out.println("Validando transaccion");
        try {
            // verificar que la transaccion no tenga una en curso.
            MarTransacciones transVerificacion = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "radId", radicacionEstadoSel.getRadId());
            if (transVerificacion == null) {
                transVerificacion = new MarTransacciones();
                transVerificacion.setTraTipoPago("RECIBO");
                transVerificacion.setRadId(radicacionEstadoSel.getRadId());
                transVerificacion.setTraReferencia(radicacionEstadoSel.getRadId().getRadLiquidacion());
                transVerificacion.setTraValor(radicacionEstadoSel.getRadId().getRadValorLiq());
                transVerificacion.setTraCuantia(radicacionEstadoSel.getRadId().getRadCuantia());
                transVerificacion.setTraFechaInicio(new Date());
                transVerificacion.setTraEstado("T");
                transVerificacion.setUsuId(usuarioSesion);
                transVerificacion.setTraApellidos("Gobernación");
                transVerificacion.setTraNombres("Robot");
                transVerificacion.setTraTelefono("7777777");
                transVerificacion.setTraCorreo("robotgobernacion@valledelcauca.gov.co");
                transVerificacion.setTraDocumento("99999999");
                transVerificacion.setTdcId(usuarioSesion.getPerId().getTdcId());
                auditSessionUtils.setAuditReflectedValues(transVerificacion);
                genericDAOBean.save(transVerificacion);
                System.out.println("Transaccion " + transVerificacion.getTraId() + " guardada");
            }
            return true;
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error seleccionando el medio de pago.", true, false);
            logger.error("Error realizando seleccion de medio de pago radicacion, causado por " + e);
        }
        return false;
    }
    
    /**
     * Coloca el estado S a la columna de radicacion-impresion.
     */
    public void validarEntrega(){
        try {
            System.out.println("Validando entrega...");
            MarTransacciones transVerificacion = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "radId", radicacionEstadoSel.getRadId());
            if(transVerificacion == null){
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Impresión requerida", "Antes de validar la entrega debe descargar el recibo de pago.", true, false);
                return;
            }
            radicacionEstadoSel.getRadId().setRadEsImpresion("S");
            auditSessionUtils.setAuditReflectedValues(radicacionEstadoSel.getRadId());
            genericDAOBean.merge(radicacionEstadoSel.getRadId());
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Proceso completado", "Se ha marcado el proceso con el estado de impresión de recibo de pago satisfactoriamente", true, false);
            seleccionarTipo();
            System.out.println("Entrega validada...");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error validando la entrega", true, false);
            logger.error("Error validando entrega de impresión, causado por " + e);
        }
    }


    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

    public List<MarRadicacionesFasesEstados> getRadicacionesEstados() {
        return radicacionesEstados;
    }

    public void setRadicacionesEstados(List<MarRadicacionesFasesEstados> radicacionesEstados) {
        this.radicacionesEstados = radicacionesEstados;
    }

    public MarRadicacionesFasesEstados getRadicacionEstadoSel() {
        return radicacionEstadoSel;
    }

    public void setRadicacionEstadoSel(MarRadicacionesFasesEstados radicacionEstadoSel) {
        this.radicacionEstadoSel = radicacionEstadoSel;
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

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }
    
    

}
