package co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Objeto de conversion y transmicion de datos de liquidacion (Item Detalle)
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class DetalleLiquidacionItem implements Serializable{
    
    private String tipoRenta;
    private String numeroLiquidacion;
    private String consecutivo;
    private String codigoActo;
    private BigDecimal cantidadLiquidaciones;
    private BigDecimal cantidad;
    private BigDecimal baseGrabable;
    private BigDecimal baseGrabableLiquidacion;
    private BigDecimal valorImpuesto;
    private BigDecimal valorImpuestoAutoliquidado;
    private BigDecimal valorExedente;
    private BigDecimal valorExedenteAutoliquidado;
    private BigDecimal valorNetoMonedaDocumento;
    private String tarifaAutoliquidada;
    private BigDecimal valorEstampillaProdesarrollo;
    private BigDecimal valorEstampillaProcultura;
    private BigDecimal valorEstampillaProseguridad;
    private BigDecimal valorEstampillaProuceva;
    
    public DetalleLiquidacionItem(){}

    public String getTipoRenta() {
        return tipoRenta;
    }

    public void setTipoRenta(String tipoRenta) {
        this.tipoRenta = tipoRenta;
    }

    public String getNumeroLiquidacion() {
        return numeroLiquidacion;
    }

    public void setNumeroLiquidacion(String numeroLiquidacion) {
        this.numeroLiquidacion = numeroLiquidacion;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getCodigoActo() {
        return codigoActo;
    }

    public void setCodigoActo(String codigoActo) {
        this.codigoActo = codigoActo;
    }

    public BigDecimal getCantidadLiquidaciones() {
        return cantidadLiquidaciones;
    }

    public void setCantidadLiquidaciones(BigDecimal cantidadLiquidaciones) {
        this.cantidadLiquidaciones = cantidadLiquidaciones;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getBaseGrabable() {
        return baseGrabable;
    }

    public void setBaseGrabable(BigDecimal baseGrabable) {
        this.baseGrabable = baseGrabable;
    }

    public BigDecimal getBaseGrabableLiquidacion() {
        return baseGrabableLiquidacion;
    }

    public void setBaseGrabableLiquidacion(BigDecimal baseGrabableLiquidacion) {
        this.baseGrabableLiquidacion = baseGrabableLiquidacion;
    }

    public BigDecimal getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(BigDecimal valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }

    public BigDecimal getValorImpuestoAutoliquidado() {
        return valorImpuestoAutoliquidado;
    }

    public void setValorImpuestoAutoliquidado(BigDecimal valorImpuestoAutoliquidado) {
        this.valorImpuestoAutoliquidado = valorImpuestoAutoliquidado;
    }

    public BigDecimal getValorExedente() {
        return valorExedente;
    }

    public void setValorExedente(BigDecimal valorExedente) {
        this.valorExedente = valorExedente;
    }

    public BigDecimal getValorExedenteAutoliquidado() {
        return valorExedenteAutoliquidado;
    }

    public void setValorExedenteAutoliquidado(BigDecimal valorExedenteAutoliquidado) {
        this.valorExedenteAutoliquidado = valorExedenteAutoliquidado;
    }

    public BigDecimal getValorNetoMonedaDocumento() {
        return valorNetoMonedaDocumento;
    }

    public void setValorNetoMonedaDocumento(BigDecimal valorNetoMonedaDocumento) {
        this.valorNetoMonedaDocumento = valorNetoMonedaDocumento;
    }

    public String getTarifaAutoliquidada() {
        return tarifaAutoliquidada;
    }

    public void setTarifaAutoliquidada(String tarifaAutoliquidada) {
        this.tarifaAutoliquidada = tarifaAutoliquidada;
    }

    public BigDecimal getValorEstampillaProdesarrollo() {
        return valorEstampillaProdesarrollo;
    }

    public void setValorEstampillaProdesarrollo(BigDecimal valorEstampillaProdesarrollo) {
        this.valorEstampillaProdesarrollo = valorEstampillaProdesarrollo;
    }

    public BigDecimal getValorEstampillaProcultura() {
        return valorEstampillaProcultura;
    }

    public void setValorEstampillaProcultura(BigDecimal valorEstampillaProcultura) {
        this.valorEstampillaProcultura = valorEstampillaProcultura;
    }

    public BigDecimal getValorEstampillaProseguridad() {
        return valorEstampillaProseguridad;
    }

    public void setValorEstampillaProseguridad(BigDecimal valorEstampillaProseguridad) {
        this.valorEstampillaProseguridad = valorEstampillaProseguridad;
    }

    public BigDecimal getValorEstampillaProuceva() {
        return valorEstampillaProuceva;
    }

    public void setValorEstampillaProuceva(BigDecimal valorEstampillaProuceva) {
        this.valorEstampillaProuceva = valorEstampillaProuceva;
    }
    
}
