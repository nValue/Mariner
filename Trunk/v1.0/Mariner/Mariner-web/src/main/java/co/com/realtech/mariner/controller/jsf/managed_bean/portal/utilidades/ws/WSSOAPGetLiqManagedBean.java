package co.com.realtech.mariner.controller.jsf.managed_bean.portal.utilidades.ws;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.logic.liquidaciones.SAPListadoLiquidacionesLogicOperations;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * ManagedBean utilidades prueba servicio web pruebas lista liquidaciones
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class WSSOAPGetLiqManagedBean extends GenericManagedBean implements Serializable {

    private String usuario;
    private String tipo;
    private List<DetalleLiquidacion> liquidaciones;

    @Override
    public void init() {

    }

    /**
     * Buscar liquidaciones mediante WS.
     */
    public void buscarLiquidaciones() {
        try {
            SAPListadoLiquidacionesLogicOperations sap;
            sap = SAPListadoLiquidacionesLogicOperations.create();

            if (getTipo().equals("TODAS")) {
                setLiquidaciones(sap.obtenerLiquidacionesSAP());
            } else {
                setLiquidaciones(sap.obtenerLiquidacionesSAPByUsuario(getUsuario()));
            }
        } catch (Exception e) {
            logger.error("Error buscando liquidaciones, causado por " + e);
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<DetalleLiquidacion> getLiquidaciones() {
        return liquidaciones;
    }

    public void setLiquidaciones(List<DetalleLiquidacion> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }

}
