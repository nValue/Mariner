package co.com.realtech.mariner.controller.jsf.managed_bean.portal.utilidades.ws;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.cambio_estado.DetalleCambioEstado;
import co.com.realtech.mariner.model.logic.estados.SAPEstadosLogicOperations;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean encargado de pruebas WS SAP Cambio de estado.
 *
 * @author Andres Rivera
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class WSSOAPCambioEstadoManagedBean extends GenericManagedBean implements Serializable {

    private String estado;
    private String liquidacion;
    private DetalleCambioEstado cambio;

    @Override
    public void init() {
        try {

        } catch (Exception e) {
            logger.error("Error inicializando WSSOAPCambioEstadoManagedBean, causado por " + e);
        }
    }

    /**
     * Cambio de estado de la liquidacion en SAP.
     */
    public void ejecutarCambioEstado() {
        try {
            SAPEstadosLogicOperations sap = SAPEstadosLogicOperations.create();
            setLiquidacion(BusinessStringUtils.convertNumeroLiquidacion(getLiquidacion()));
            setCambio(sap.cambiarEstadoLiquidacion(getLiquidacion(), getEstado()));
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "Se ha ejecutado el metodo con estado " + getCambio().getEstadoRespuesta() + ":-:" + getCambio().getMensaje(), true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Clip, "Error", "Error ejecutando metodo de cambio de estado, causado por " + e, true, false);
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(String liquidacion) {
        this.liquidacion = liquidacion;
    }

    public DetalleCambioEstado getCambio() {
        return cambio;
    }

    public void setCambio(DetalleCambioEstado cambio) {
        this.cambio = cambio;
    }

}
