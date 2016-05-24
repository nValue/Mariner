package co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de conversion y transmicion de datos de liquidacion
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class DetalleLiquidacion implements Serializable {

    private String documentoOtorgante;
    private String numeroOtorgante;
    private String documentoBenificiario;
    private String numeroBenificiario;
    private String municipio;
    private String fechaLiquidacion;
    private String usuarioSAP;
    private String observaciones;
    private String claseLiquidacion;
    private BigDecimal valorTotal;
    private String fechaDocumento;
    private String matricula;
    private BigDecimal interesesMora;
    private BigDecimal ajuste;
    private String notaria;
    private BigDecimal descuentoIntereses;
    private BigDecimal descuento;
    private BigDecimal totalDescuento;
    private String fechaLimitePagoBoleta;
    private List<DetalleLiquidacionItem> itemsDetalle = new ArrayList<>();

    public DetalleLiquidacion() {
    }

    public String getDocumentoOtorgante() {
        return documentoOtorgante;
    }

    public void setDocumentoOtorgante(String documentoOtorgante) {
        this.documentoOtorgante = documentoOtorgante;
    }

    public String getNumeroOtorgante() {
        return numeroOtorgante;
    }

    public void setNumeroOtorgante(String numeroOtorgante) {
        this.numeroOtorgante = numeroOtorgante;
    }

    public String getDocumentoBenificiario() {
        return documentoBenificiario;
    }

    public void setDocumentoBenificiario(String documentoBenificiario) {
        this.documentoBenificiario = documentoBenificiario;
    }

    public String getNumeroBenificiario() {
        return numeroBenificiario;
    }

    public void setNumeroBenificiario(String numeroBenificiario) {
        this.numeroBenificiario = numeroBenificiario;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public String getUsuarioSAP() {
        return usuarioSAP;
    }

    public void setUsuarioSAP(String usuarioSAP) {
        this.usuarioSAP = usuarioSAP;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getClaseLiquidacion() {
        return claseLiquidacion;
    }

    public void setClaseLiquidacion(String claseLiquidacion) {
        this.claseLiquidacion = claseLiquidacion;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public BigDecimal getInteresesMora() {
        return interesesMora;
    }

    public void setInteresesMora(BigDecimal interesesMora) {
        this.interesesMora = interesesMora;
    }

    public BigDecimal getAjuste() {
        return ajuste;
    }

    public void setAjuste(BigDecimal ajuste) {
        this.ajuste = ajuste;
    }

    public String getNotaria() {
        return notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }

    public BigDecimal getDescuentoIntereses() {
        return descuentoIntereses;
    }

    public void setDescuentoIntereses(BigDecimal descuentoIntereses) {
        this.descuentoIntereses = descuentoIntereses;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public String getFechaLimitePagoBoleta() {
        return fechaLimitePagoBoleta;
    }

    public void setFechaLimitePagoBoleta(String fechaLimitePagoBoleta) {
        this.fechaLimitePagoBoleta = fechaLimitePagoBoleta;
    }

    public List<DetalleLiquidacionItem> getItemsDetalle() {
        return itemsDetalle;
    }

    public void setItemsDetalle(List<DetalleLiquidacionItem> itemsDetalle) {
        this.itemsDetalle = itemsDetalle;
    }

}
