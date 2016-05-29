package co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de conversion y transmision de datos de liquidacion
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class DetalleLiquidacion implements Serializable {

    private String liqNumero;
    private String otoNombre;
    private String benNombre;
    private String otoCc;
    private String benCc;
    private String otoNit;
    private String benNit;
    private String otoNum;
    private String benNum;
    private String municipio;
    private String clavePer;
    private String munNombre;
    private String fechaLiquidacion;
    private String uname;
    private String fechaLdp;
    private String texto;
    private String clase;
    private String documento;
    private BigDecimal total;
    private String descladoc;
    private String descoridoc;
    private String fechaDoc;
    private String matricula;
    private BigDecimal tintMora;
    private String codTarifa;
    private String notaria;
    private BigDecimal descInt;
    private BigDecimal descImp;
    private BigDecimal totalDesc;
    private String fechaLimite;
    private String norodrad;

    private String interlocutor;
    private String cvPeriodo;
    private String fechaVencimiento;
    private String expedidoEn;
    private String liquidacionReferencia;
    private String telefono;
    private BigDecimal valorImpuestoIPP;
    private BigDecimal valorImpuestoCamara;
    private BigDecimal valorDescuentoIntereses;
    private BigDecimal servicioInfo;
    private BigDecimal valorImpuestoSinCuantia;
    private BigDecimal valorImpuestoEstampilla;
    private BigDecimal valorDescuentoImpuesto;
    private String expando;

    private List<DetalleLiquidacionItem> itemsDetalle = new ArrayList<>();

    public DetalleLiquidacion() {
    }

    public String getLiqNumero() {
        return liqNumero;
    }

    public void setLiqNumero(String liqNumero) {
        this.liqNumero = liqNumero;
    }

    public String getOtoNombre() {
        return otoNombre;
    }

    public void setOtoNombre(String otoNombre) {
        this.otoNombre = otoNombre;
    }

    public String getBenNombre() {
        return benNombre;
    }

    public void setBenNombre(String benNombre) {
        this.benNombre = benNombre;
    }

    public String getOtoCc() {
        return otoCc;
    }

    public void setOtoCc(String otoCc) {
        this.otoCc = otoCc;
    }

    public String getBenCc() {
        return benCc;
    }

    public void setBenCc(String benCc) {
        this.benCc = benCc;
    }

    public String getOtoNit() {
        return otoNit;
    }

    public void setOtoNit(String otoNit) {
        this.otoNit = otoNit;
    }

    public String getBenNit() {
        return benNit;
    }

    public void setBenNit(String benNit) {
        this.benNit = benNit;
    }

    public String getOtoNum() {
        return otoNum;
    }

    public void setOtoNum(String otoNum) {
        this.otoNum = otoNum;
    }

    public String getBenNum() {
        return benNum;
    }

    public void setBenNum(String benNum) {
        this.benNum = benNum;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getClavePer() {
        return clavePer;
    }

    public void setClavePer(String clavePer) {
        this.clavePer = clavePer;
    }

    public String getMunNombre() {
        return munNombre;
    }

    public void setMunNombre(String munNombre) {
        this.munNombre = munNombre;
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getFechaLdp() {
        return fechaLdp;
    }

    public void setFechaLdp(String fechaLdp) {
        this.fechaLdp = fechaLdp;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDescladoc() {
        return descladoc;
    }

    public void setDescladoc(String descladoc) {
        this.descladoc = descladoc;
    }

    public String getDescoridoc() {
        return descoridoc;
    }

    public void setDescoridoc(String descoridoc) {
        this.descoridoc = descoridoc;
    }

    public String getFechaDoc() {
        return fechaDoc;
    }

    public void setFechaDoc(String fechaDoc) {
        this.fechaDoc = fechaDoc;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public BigDecimal getTintMora() {
        return tintMora;
    }

    public void setTintMora(BigDecimal tintMora) {
        this.tintMora = tintMora;
    }

    public String getCodTarifa() {
        return codTarifa;
    }

    public void setCodTarifa(String codTarifa) {
        this.codTarifa = codTarifa;
    }

    public String getNotaria() {
        return notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }

    public BigDecimal getDescInt() {
        return descInt;
    }

    public void setDescInt(BigDecimal descInt) {
        this.descInt = descInt;
    }

    public BigDecimal getDescImp() {
        return descImp;
    }

    public void setDescImp(BigDecimal descImp) {
        this.descImp = descImp;
    }

    public BigDecimal getTotalDesc() {
        return totalDesc;
    }

    public void setTotalDesc(BigDecimal totalDesc) {
        this.totalDesc = totalDesc;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getNorodrad() {
        return norodrad;
    }

    public void setNorodrad(String norodrad) {
        this.norodrad = norodrad;
    }

    public List<DetalleLiquidacionItem> getItemsDetalle() {
        return itemsDetalle;
    }

    public void setItemsDetalle(List<DetalleLiquidacionItem> itemsDetalle) {
        this.itemsDetalle = itemsDetalle;
    }

    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public String getCvPeriodo() {
        return cvPeriodo;
    }

    public void setCvPeriodo(String cvPeriodo) {
        this.cvPeriodo = cvPeriodo;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getExpedidoEn() {
        return expedidoEn;
    }

    public void setExpedidoEn(String expedidoEn) {
        this.expedidoEn = expedidoEn;
    }

    public String getLiquidacionReferencia() {
        return liquidacionReferencia;
    }

    public void setLiquidacionReferencia(String liquidacionReferencia) {
        this.liquidacionReferencia = liquidacionReferencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getValorImpuestoIPP() {
        return valorImpuestoIPP;
    }

    public void setValorImpuestoIPP(BigDecimal valorImpuestoIPP) {
        this.valorImpuestoIPP = valorImpuestoIPP;
    }

    public BigDecimal getValorImpuestoCamara() {
        return valorImpuestoCamara;
    }

    public void setValorImpuestoCamara(BigDecimal valorImpuestoCamara) {
        this.valorImpuestoCamara = valorImpuestoCamara;
    }

    public BigDecimal getValorDescuentoIntereses() {
        return valorDescuentoIntereses;
    }

    public void setValorDescuentoIntereses(BigDecimal valorDescuentoIntereses) {
        this.valorDescuentoIntereses = valorDescuentoIntereses;
    }

    public BigDecimal getServicioInfo() {
        return servicioInfo;
    }

    public void setServicioInfo(BigDecimal servicioInfo) {
        this.servicioInfo = servicioInfo;
    }

    public BigDecimal getValorImpuestoSinCuantia() {
        return valorImpuestoSinCuantia;
    }

    public void setValorImpuestoSinCuantia(BigDecimal valorImpuestoSinCuantia) {
        this.valorImpuestoSinCuantia = valorImpuestoSinCuantia;
    }

    public BigDecimal getValorImpuestoEstampilla() {
        return valorImpuestoEstampilla;
    }

    public void setValorImpuestoEstampilla(BigDecimal valorImpuestoEstampilla) {
        this.valorImpuestoEstampilla = valorImpuestoEstampilla;
    }

    public BigDecimal getValorDescuentoImpuesto() {
        return valorDescuentoImpuesto;
    }

    public void setValorDescuentoImpuesto(BigDecimal valorDescuentoImpuesto) {
        this.valorDescuentoImpuesto = valorDescuentoImpuesto;
    }

    public String getExpando() {
        return expando;
    }

    public void setExpando(String expando) {
        this.expando = expando;
    }

}
