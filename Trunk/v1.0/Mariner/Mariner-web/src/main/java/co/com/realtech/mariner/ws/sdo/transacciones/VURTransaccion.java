package co.com.realtech.mariner.ws.sdo.transacciones;

import java.io.Serializable;

/**
 * SDO de transacciones para pasarela de pagos y WS Pasarela.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.9
 */
public class VURTransaccion implements Serializable {

    private long codigoTransaccion;
    private String cus;
    private String nombres;
    private String apellidos;
    private String correo;
    private String estado;
    private String telefono;
    private long valorTransaccion;
    private String descripcionTransaccion;
    private String tipoDocumento;
    private String numeroDocumento;
    private String referencia;
    private String codigoServicioACH;
    private VURTransaccionLogSDO log;

    public VURTransaccion() {
    }

    public long getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(long codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public String getCus() {
        return cus;
    }

    public void setCus(String cus) {
        this.cus = cus;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public long getValorTransaccion() {
        return valorTransaccion;
    }

    public void setValorTransaccion(long valorTransaccion) {
        this.valorTransaccion = valorTransaccion;
    }

    public VURTransaccionLogSDO getLog() {
        return log;
    }

    public void setLog(VURTransaccionLogSDO log) {
        this.log = log;
    }

    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodigoServicioACH() {
        return codigoServicioACH;
    }

    public void setCodigoServicioACH(String codigoServicioACH) {
        this.codigoServicioACH = codigoServicioACH;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
