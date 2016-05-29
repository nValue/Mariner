package co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.cambio_estado;

import java.io.Serializable;

/**
 * SDO Informacion de cambio estado en SAP
 * 
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class DetalleCambioEstado implements Serializable{
    
    private String liquidacion;
    private String estadoDestino;
    private String estadoRespuesta;
    private String mensaje;
    
    public DetalleCambioEstado() {        
    }

    public String getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(String liquidacion) {
        this.liquidacion = liquidacion;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getEstadoRespuesta() {
        return estadoRespuesta;
    }

    public void setEstadoRespuesta(String estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
