package co.com.realtech.mariner.model.sdo.logs;

import java.io.Serializable;
import java.util.Date;

/**
 * SDO para servicio web log de la consulta.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
public class EntidadLog implements Serializable {

    private String codigo;
    private String tipo;
    private Date fecha;
    private String mensaje;
    private String mensajeTecnico;

    public EntidadLog() {
    }

    public EntidadLog(String c, String t, String m, String mt) {
        this.fecha = new Date();
        this.codigo = c;
        this.tipo = t;
        this.mensaje = m;
        this.mensajeTecnico = mt;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
