package co.com.realtech.mariner.controller.jsf.managed_bean.portal.utilidades.cargue_asobancaria;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarCarguesAsobancaria;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.files.extractor.BusinessFileExtractor;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionConfirmacion;
import co.com.realtech.mariner.ws.transacciones.core.TransactionCore;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;

/**
 * Controlador JSF cargue de archivo asobancaria.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class CargueAsobancariaManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "GenericDAOBean")
    private GenericDAOBeanLocal genericDAOBeanLocal;

    private Integer bloque;
    private MarCarguesAsobancaria cargue;
    private List<MarCarguesAsobancaria> cargues;

    @Override
    public void init() {
        try {
            setBloque(null);
            setCargues(new ArrayList<>());
        } catch (Exception e) {
            logger.error("Error inicializando CargueAsobancariaManagedBean, causado por " + e);
        }
    }

    /**
     * Buscar bloque por indice seleccionado.
     */
    public void buscarBloque() {
        try {
            setCargues((List<MarCarguesAsobancaria>) genericDAOBean.findAllByColumn(MarCarguesAsobancaria.class, "casBloque", getBloque(), true, "casId asc"));
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Informacion de bloque cargada correctamente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Error", "Lo sentimos pero en este momento no es posible realizar el cargue de los datos, por favor intente nuevamente.", true, false);
            logger.error("No se puede cargar la informacion del bloque seleccionando " + e);
        }
    }

    /**
     * Evento necesario para el cargue de archivos
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        try {
            String omitirLineas = ConstantesUtils.cargarConstante("VUR-ASOBANCA-OMITIR-LINEAS");
            String segmentoReferencia = ConstantesUtils.cargarConstante("VUR-ASOBANCA-BLOQUE-LIQ");
            String segmentoValor = ConstantesUtils.cargarConstante("VUR-ASOBANCA-BLOQUE-VALOR");
            Integer bloqueActual = new Integer(genericDAOBean.callGenericFunction("FL_RETORNAR_BLOQUE_ASOBANCA").toString());
            List<MarCarguesAsobancaria> datosExt = BusinessFileExtractor.extraerDatosArchivoBancario(usuarioSesion, event.getFile().getInputstream(), omitirLineas, segmentoReferencia, segmentoValor, bloqueActual);
            // persistimos los datos en la base de datos
            for (MarCarguesAsobancaria dat : datosExt) {
                auditSessionUtils.setAuditReflectedValues(dat);
                genericDAOBean.save(dat);
            }
            PrimeFacesContext.execute("PF('fileUploadDialog').hide();");
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Archivo cargado correctamente con identificador de bloque <b>" + bloqueActual + "</b>", true, false);
            setBloque(bloqueActual);
            buscarBloque();
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Archivo no es válido", e.getLocalizedMessage(), true, false);
            logger.error("No se puede cargar el archivo, causado por: " + e.getLocalizedMessage(), e);
        }
    }

    /**
     * Borrado de todo el bloque seleccionado.
     */
    public void borrarBloque() {
        try {
            for (MarCarguesAsobancaria cag : getCargues()) {
                genericDAOBean.delete(cag, cag.getCasId());
            }
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Bloque eliminado correctamente de la base de datos", true, false);
            init();
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no se puede eliminar la informacion del bloque", true, false);
            logger.error("Error borrando bloque de cargue asobancaria ", e);
        }
    }

    /**
     * Aplicar todos los pagos del bloque al sistema
     */
    public void aplicarPagosVUR() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // por cada referencia aplicamos el pago
            VURTransaccionConfirmacion confirmacion;
            TransactionCore core = TransactionCore.create();
            String claveConfConstante = ConstantesUtils.cargarConstante("WS-PASARELA-CODIGO-CONFIRMACION");

            for (MarCarguesAsobancaria dcart : getCargues()) {
                String codigoLiquidacion = BusinessStringUtils.convertNumeroLiquidacion(dcart.getCasReferencia());
                MarRadicaciones radicacion = (MarRadicaciones) genericDAOBean.findByColumn(MarRadicaciones.class, "radLiquidacion", codigoLiquidacion);
                MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "radId", radicacion);

                if (transaccionBD != null) {
                    confirmacion = core.confirmarTransaccion(claveConfConstante, sdf.format(new Date()), transaccionBD.getTraValor().longValue(), codigoLiquidacion,null);

                    dcart.setCasLog(confirmacion.getLog().getMensaje());
                    if (confirmacion.getEstado().equals("OK")) {
                        dcart.setCasEstado("A");
                    }
                }
                else{
                    dcart.setCasLog("Transaccion con referencia "+codigoLiquidacion+" no se encuentra registrada en el sistema VUR.");
                }
                genericDAOBean.merge(dcart);
            }
            buscarBloque();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Por favor revise el log de todos los pagos, el cargue ha finalizado correctemente.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no se puede aplicar los pagos, verifique la informacion e intente nuevamente.", true, false);
            logger.error("Error aplicando pagos del bloque ", e);
        }
    }

    public Integer getBloque() {
        return bloque;
    }

    public void setBloque(Integer bloque) {
        this.bloque = bloque;
    }

    public MarCarguesAsobancaria getCargue() {
        return cargue;
    }

    public void setCargue(MarCarguesAsobancaria cargue) {
        this.cargue = cargue;
    }

    public List<MarCarguesAsobancaria> getCargues() {
        return cargues;
    }

    public void setCargues(List<MarCarguesAsobancaria> cargues) {
        this.cargues = cargues;
    }

}
