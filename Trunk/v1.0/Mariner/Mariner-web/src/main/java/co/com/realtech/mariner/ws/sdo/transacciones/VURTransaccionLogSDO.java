package co.com.realtech.mariner.ws.sdo.transacciones;

/**
 * SDO de log de transacciones.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class VURTransaccionLogSDO {

    private String estado;
    private String mensaje;
    private String mensajeTecnico;

    public VURTransaccionLogSDO() {
    }

    public VURTransaccionLogSDO(String e, String l) {
        this.estado = e;
        this.mensaje = l;
    }

    public VURTransaccionLogSDO(String e, String l, String t) {
        this.estado = e;
        this.mensaje = l;
        this.mensajeTecnico = t;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeTecnico() {
        return mensajeTecnico;
    }

    public void setMensajeTecnico(String mensajeTecnico) {
        this.mensajeTecnico = mensajeTecnico;
    }
}
