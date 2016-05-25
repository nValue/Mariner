package co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.payment;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * SDO Resultado de pago al invocar WS SAP
 * 
 * @author Andres Rivera
 * @version 1.0
 * @since JDK,18
 */
public class DetallePago implements Serializable{
    
    private String numeroCuenta;
    private String fechaRecaudo;
    private String horaRecaudo;
    private String numeroLiquidacion;
    private BigDecimal valor;
    private String estadoSalida="OK";
    private String mensajeSalida;
    
    public DetallePago(){}

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getFechaRecaudo() {
        return fechaRecaudo;
    }

    public void setFechaRecaudo(String fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }

    public String getHoraRecaudo() {
        return horaRecaudo;
    }

    public void setHoraRecaudo(String horaRecaudo) {
        this.horaRecaudo = horaRecaudo;
    }

    public String getNumeroLiquidacion() {
        return numeroLiquidacion;
    }

    public void setNumeroLiquidacion(String numeroLiquidacion) {
        this.numeroLiquidacion = numeroLiquidacion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEstadoSalida() {
        return estadoSalida;
    }

    public void setEstadoSalida(String estadoSalida) {
        this.estadoSalida = estadoSalida;
    }

    public String getMensajeSalida() {
        return mensajeSalida;
    }

    public void setMensajeSalida(String mensajeSalida) {
        this.mensajeSalida = mensajeSalida;
    }
    
}
