package co.com.realtech.mariner.model.sdo.estandar;

import co.com.realtech.mariner.model.sdo.logs.EntidadLog;
import java.io.Serializable;

/**
 * SDO transporte de informacion cuando se crea la transaccion.
 * 
 * @author  Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
public class EntidadLiquidacionResultado implements Serializable{
    
    private String estado;
    private Long codigoConfirmacion;
    private EntidadLog log;
    
    public EntidadLiquidacionResultado(){};

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EntidadLog getLog() {
        return log;
    }

    public void setLog(EntidadLog log) {
        this.log = log;
    }

    public Long getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    public void setCodigoConfirmacion(Long codigoConfirmacion) {
        this.codigoConfirmacion = codigoConfirmacion;
    }
    
}
