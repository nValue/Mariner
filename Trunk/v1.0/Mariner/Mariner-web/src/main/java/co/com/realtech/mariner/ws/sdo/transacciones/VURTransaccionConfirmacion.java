package co.com.realtech.mariner.ws.sdo.transacciones;

import java.io.Serializable;

/**
 * SDO de transacciones confirmacion de transaccion.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.9
 */
public class VURTransaccionConfirmacion implements Serializable {

    private String estado;
    private VURTransaccionLogSDO log;

    public VURTransaccionConfirmacion() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public VURTransaccionLogSDO getLog() {
        return log;
    }

    public void setLog(VURTransaccionLogSDO log) {
        this.log = log;
    }

   

}
