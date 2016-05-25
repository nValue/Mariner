package co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Objeto de conversion y transmicion de datos de liquidacion (Item Detalle)
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class DetalleLiquidacionItem implements Serializable {

    private String codigoActo;
    private String nombreASR;
    private String tipoPer;
    private BigDecimal baseIpp;
    private BigDecimal baseCio;
    private BigDecimal baseEst;
    private String tarifaIpp;
    private String tarifaCio;
    private String tarifaEst;
    private BigDecimal impuestoIpp;
    private BigDecimal impuestoCio;
    private BigDecimal baseEst1;
    private BigDecimal baseEst2;
    private BigDecimal valorSinCuantia;
    private BigDecimal valorServInfo;
    private BigDecimal ValorIntereses;
    private String fechaDocumento;
    private String fechaVencimiento;
    private String fechaPago;
    private BigDecimal baseEst3;
    private BigDecimal valorEstadoProd;
    private BigDecimal baseEst4;
    private BigDecimal baseEst5;

    public DetalleLiquidacionItem() {
    }

    public String getCodigoActo() {
        return codigoActo;
    }

    public void setCodigoActo(String codigoActo) {
        this.codigoActo = codigoActo;
    }

    public String getNombreASR() {
        return nombreASR;
    }

    public void setNombreASR(String nombreASR) {
        this.nombreASR = nombreASR;
    }

    public String getTipoPer() {
        return tipoPer;
    }

    public void setTipoPer(String tipoPer) {
        this.tipoPer = tipoPer;
    }

    public BigDecimal getBaseIpp() {
        return baseIpp;
    }

    public void setBaseIpp(BigDecimal baseIpp) {
        this.baseIpp = baseIpp;
    }

    public BigDecimal getBaseCio() {
        return baseCio;
    }

    public void setBaseCio(BigDecimal baseCio) {
        this.baseCio = baseCio;
    }

    public BigDecimal getBaseEst() {
        return baseEst;
    }

    public void setBaseEst(BigDecimal baseEst) {
        this.baseEst = baseEst;
    }

    public String getTarifaIpp() {
        return tarifaIpp;
    }

    public void setTarifaIpp(String tarifaIpp) {
        this.tarifaIpp = tarifaIpp;
    }

    public String getTarifaCio() {
        return tarifaCio;
    }

    public void setTarifaCio(String tarifaCio) {
        this.tarifaCio = tarifaCio;
    }

    public String getTarifaEst() {
        return tarifaEst;
    }

    public void setTarifaEst(String tarifaEst) {
        this.tarifaEst = tarifaEst;
    }

    public BigDecimal getImpuestoIpp() {
        return impuestoIpp;
    }

    public void setImpuestoIpp(BigDecimal impuestoIpp) {
        this.impuestoIpp = impuestoIpp;
    }

    public BigDecimal getImpuestoCio() {
        return impuestoCio;
    }

    public void setImpuestoCio(BigDecimal impuestoCio) {
        this.impuestoCio = impuestoCio;
    }

    public BigDecimal getBaseEst2() {
        return baseEst2;
    }

    public void setBaseEst2(BigDecimal baseEst2) {
        this.baseEst2 = baseEst2;
    }

    public BigDecimal getValorSinCuantia() {
        return valorSinCuantia;
    }

    public void setValorSinCuantia(BigDecimal valorSinCuantia) {
        this.valorSinCuantia = valorSinCuantia;
    }

    public BigDecimal getValorServInfo() {
        return valorServInfo;
    }

    public void setValorServInfo(BigDecimal valorServInfo) {
        this.valorServInfo = valorServInfo;
    }

    public BigDecimal getValorIntereses() {
        return ValorIntereses;
    }

    public void setValorIntereses(BigDecimal ValorIntereses) {
        this.ValorIntereses = ValorIntereses;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getBaseEst3() {
        return baseEst3;
    }

    public void setBaseEst3(BigDecimal baseEst3) {
        this.baseEst3 = baseEst3;
    }

    public BigDecimal getValorEstadoProd() {
        return valorEstadoProd;
    }

    public void setValorEstadoProd(BigDecimal valorEstadoProd) {
        this.valorEstadoProd = valorEstadoProd;
    }

    public BigDecimal getBaseEst4() {
        return baseEst4;
    }

    public void setBaseEst4(BigDecimal baseEst4) {
        this.baseEst4 = baseEst4;
    }

    public BigDecimal getBaseEst5() {
        return baseEst5;
    }

    public void setBaseEst5(BigDecimal baseEst5) {
        this.baseEst5 = baseEst5;
    }

    public BigDecimal getBaseEst1() {
        return baseEst1;
    }

    public void setBaseEst1(BigDecimal baseEst1) {
        this.baseEst1 = baseEst1;
    }

}
