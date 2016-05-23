
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZPSCD_PRNCAB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZPSCD_PRNCAB">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V_NUMERO" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="V_OTO_NOMBRE" type="{urn:sap-com:document:sap:rfc:functions}char80"/>
 *         &lt;element name="V_BEN_NOMBRE" type="{urn:sap-com:document:sap:rfc:functions}char80"/>
 *         &lt;element name="V_OTO_CC" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="V_BEN_CC" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="V_OTO_NIT" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="V_BEN_NIT" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="V_OTO_NUM" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="V_BEN_NUM" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="V_MUNICIPIO" type="{urn:sap-com:document:sap:rfc:functions}char3"/>
 *         &lt;element name="V_CLAVE_PER" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="V_MUN_NOMBRE" type="{urn:sap-com:document:sap:rfc:functions}char20"/>
 *         &lt;element name="V_FEC_LIQ" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="UNAME" type="{urn:sap-com:document:sap:rfc:functions}char12"/>
 *         &lt;element name="V_FEC_LDP" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="V_TEXTO" type="{urn:sap-com:document:sap:rfc:functions}char132"/>
 *         &lt;element name="V_CLASE" type="{urn:sap-com:document:sap:rfc:functions}numeric2"/>
 *         &lt;element name="V_NUM_LIQ" type="{urn:sap-com:document:sap:rfc:functions}char16"/>
 *         &lt;element name="V_DOCUMENTO" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="V_TOTAL" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="V_DESCLADOC" type="{urn:sap-com:document:sap:rfc:functions}char11"/>
 *         &lt;element name="V_DESORIDOC" type="{urn:sap-com:document:sap:rfc:functions}char11"/>
 *         &lt;element name="V_FEC_DOC" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="V_MATINM" type="{urn:sap-com:document:sap:rfc:functions}char16"/>
 *         &lt;element name="V_TINTMORA" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="V_AJUSTE" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="CODTAR" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="V_NOTARIA" type="{urn:sap-com:document:sap:rfc:functions}char16"/>
 *         &lt;element name="V_DSCTO_INT" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="V_DSCTO_IMP" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="V_TOT_DSCTO" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="FECHALIMITE" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPSCD_PRNCAB", propOrder = {
    "vnumero",
    "votonombre",
    "vbennombre",
    "votocc",
    "vbencc",
    "votonit",
    "vbennit",
    "votonum",
    "vbennum",
    "vmunicipio",
    "vclaveper",
    "vmunnombre",
    "vfecliq",
    "uname",
    "vfecldp",
    "vtexto",
    "vclase",
    "vnumliq",
    "vdocumento",
    "vtotal",
    "vdescladoc",
    "vdesoridoc",
    "vfecdoc",
    "vmatinm",
    "vtintmora",
    "vajuste",
    "codtar",
    "vnotaria",
    "vdsctoint",
    "vdsctoimp",
    "vtotdscto",
    "fechalimite"
})
public class ZPSCDPRNCAB {

    @XmlElement(name = "V_NUMERO", required = true)
    protected String vnumero;
    @XmlElement(name = "V_OTO_NOMBRE", required = true)
    protected String votonombre;
    @XmlElement(name = "V_BEN_NOMBRE", required = true)
    protected String vbennombre;
    @XmlElement(name = "V_OTO_CC", required = true)
    protected String votocc;
    @XmlElement(name = "V_BEN_CC", required = true)
    protected String vbencc;
    @XmlElement(name = "V_OTO_NIT", required = true)
    protected String votonit;
    @XmlElement(name = "V_BEN_NIT", required = true)
    protected String vbennit;
    @XmlElement(name = "V_OTO_NUM", required = true)
    protected String votonum;
    @XmlElement(name = "V_BEN_NUM", required = true)
    protected String vbennum;
    @XmlElement(name = "V_MUNICIPIO", required = true)
    protected String vmunicipio;
    @XmlElement(name = "V_CLAVE_PER", required = true)
    protected String vclaveper;
    @XmlElement(name = "V_MUN_NOMBRE", required = true)
    protected String vmunnombre;
    @XmlElement(name = "V_FEC_LIQ", required = true)
    protected String vfecliq;
    @XmlElement(name = "UNAME", required = true)
    protected String uname;
    @XmlElement(name = "V_FEC_LDP", required = true)
    protected String vfecldp;
    @XmlElement(name = "V_TEXTO", required = true)
    protected String vtexto;
    @XmlElement(name = "V_CLASE", required = true)
    protected String vclase;
    @XmlElement(name = "V_NUM_LIQ", required = true)
    protected String vnumliq;
    @XmlElement(name = "V_DOCUMENTO", required = true)
    protected String vdocumento;
    @XmlElement(name = "V_TOTAL", required = true)
    protected BigDecimal vtotal;
    @XmlElement(name = "V_DESCLADOC", required = true)
    protected String vdescladoc;
    @XmlElement(name = "V_DESORIDOC", required = true)
    protected String vdesoridoc;
    @XmlElement(name = "V_FEC_DOC", required = true)
    protected String vfecdoc;
    @XmlElement(name = "V_MATINM", required = true)
    protected String vmatinm;
    @XmlElement(name = "V_TINTMORA", required = true)
    protected BigDecimal vtintmora;
    @XmlElement(name = "V_AJUSTE", required = true)
    protected BigDecimal vajuste;
    @XmlElement(name = "CODTAR", required = true)
    protected String codtar;
    @XmlElement(name = "V_NOTARIA", required = true)
    protected String vnotaria;
    @XmlElement(name = "V_DSCTO_INT", required = true)
    protected BigDecimal vdsctoint;
    @XmlElement(name = "V_DSCTO_IMP", required = true)
    protected BigDecimal vdsctoimp;
    @XmlElement(name = "V_TOT_DSCTO", required = true)
    protected BigDecimal vtotdscto;
    @XmlElement(name = "FECHALIMITE", required = true)
    protected String fechalimite;

    /**
     * Gets the value of the vnumero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVNUMERO() {
        return vnumero;
    }

    /**
     * Sets the value of the vnumero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVNUMERO(String value) {
        this.vnumero = value;
    }

    /**
     * Gets the value of the votonombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVOTONOMBRE() {
        return votonombre;
    }

    /**
     * Sets the value of the votonombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVOTONOMBRE(String value) {
        this.votonombre = value;
    }

    /**
     * Gets the value of the vbennombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBENNOMBRE() {
        return vbennombre;
    }

    /**
     * Sets the value of the vbennombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBENNOMBRE(String value) {
        this.vbennombre = value;
    }

    /**
     * Gets the value of the votocc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVOTOCC() {
        return votocc;
    }

    /**
     * Sets the value of the votocc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVOTOCC(String value) {
        this.votocc = value;
    }

    /**
     * Gets the value of the vbencc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBENCC() {
        return vbencc;
    }

    /**
     * Sets the value of the vbencc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBENCC(String value) {
        this.vbencc = value;
    }

    /**
     * Gets the value of the votonit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVOTONIT() {
        return votonit;
    }

    /**
     * Sets the value of the votonit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVOTONIT(String value) {
        this.votonit = value;
    }

    /**
     * Gets the value of the vbennit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBENNIT() {
        return vbennit;
    }

    /**
     * Sets the value of the vbennit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBENNIT(String value) {
        this.vbennit = value;
    }

    /**
     * Gets the value of the votonum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVOTONUM() {
        return votonum;
    }

    /**
     * Sets the value of the votonum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVOTONUM(String value) {
        this.votonum = value;
    }

    /**
     * Gets the value of the vbennum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBENNUM() {
        return vbennum;
    }

    /**
     * Sets the value of the vbennum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBENNUM(String value) {
        this.vbennum = value;
    }

    /**
     * Gets the value of the vmunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMUNICIPIO() {
        return vmunicipio;
    }

    /**
     * Sets the value of the vmunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMUNICIPIO(String value) {
        this.vmunicipio = value;
    }

    /**
     * Gets the value of the vclaveper property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVCLAVEPER() {
        return vclaveper;
    }

    /**
     * Sets the value of the vclaveper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVCLAVEPER(String value) {
        this.vclaveper = value;
    }

    /**
     * Gets the value of the vmunnombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMUNNOMBRE() {
        return vmunnombre;
    }

    /**
     * Sets the value of the vmunnombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMUNNOMBRE(String value) {
        this.vmunnombre = value;
    }

    /**
     * Gets the value of the vfecliq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVFECLIQ() {
        return vfecliq;
    }

    /**
     * Sets the value of the vfecliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVFECLIQ(String value) {
        this.vfecliq = value;
    }

    /**
     * Gets the value of the uname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNAME() {
        return uname;
    }

    /**
     * Sets the value of the uname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNAME(String value) {
        this.uname = value;
    }

    /**
     * Gets the value of the vfecldp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVFECLDP() {
        return vfecldp;
    }

    /**
     * Sets the value of the vfecldp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVFECLDP(String value) {
        this.vfecldp = value;
    }

    /**
     * Gets the value of the vtexto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVTEXTO() {
        return vtexto;
    }

    /**
     * Sets the value of the vtexto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVTEXTO(String value) {
        this.vtexto = value;
    }

    /**
     * Gets the value of the vclase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVCLASE() {
        return vclase;
    }

    /**
     * Sets the value of the vclase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVCLASE(String value) {
        this.vclase = value;
    }

    /**
     * Gets the value of the vnumliq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVNUMLIQ() {
        return vnumliq;
    }

    /**
     * Sets the value of the vnumliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVNUMLIQ(String value) {
        this.vnumliq = value;
    }

    /**
     * Gets the value of the vdocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVDOCUMENTO() {
        return vdocumento;
    }

    /**
     * Sets the value of the vdocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVDOCUMENTO(String value) {
        this.vdocumento = value;
    }

    /**
     * Gets the value of the vtotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVTOTAL() {
        return vtotal;
    }

    /**
     * Sets the value of the vtotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVTOTAL(BigDecimal value) {
        this.vtotal = value;
    }

    /**
     * Gets the value of the vdescladoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVDESCLADOC() {
        return vdescladoc;
    }

    /**
     * Sets the value of the vdescladoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVDESCLADOC(String value) {
        this.vdescladoc = value;
    }

    /**
     * Gets the value of the vdesoridoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVDESORIDOC() {
        return vdesoridoc;
    }

    /**
     * Sets the value of the vdesoridoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVDESORIDOC(String value) {
        this.vdesoridoc = value;
    }

    /**
     * Gets the value of the vfecdoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVFECDOC() {
        return vfecdoc;
    }

    /**
     * Sets the value of the vfecdoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVFECDOC(String value) {
        this.vfecdoc = value;
    }

    /**
     * Gets the value of the vmatinm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMATINM() {
        return vmatinm;
    }

    /**
     * Sets the value of the vmatinm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMATINM(String value) {
        this.vmatinm = value;
    }

    /**
     * Gets the value of the vtintmora property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVTINTMORA() {
        return vtintmora;
    }

    /**
     * Sets the value of the vtintmora property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVTINTMORA(BigDecimal value) {
        this.vtintmora = value;
    }

    /**
     * Gets the value of the vajuste property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVAJUSTE() {
        return vajuste;
    }

    /**
     * Sets the value of the vajuste property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVAJUSTE(BigDecimal value) {
        this.vajuste = value;
    }

    /**
     * Gets the value of the codtar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODTAR() {
        return codtar;
    }

    /**
     * Sets the value of the codtar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODTAR(String value) {
        this.codtar = value;
    }

    /**
     * Gets the value of the vnotaria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVNOTARIA() {
        return vnotaria;
    }

    /**
     * Sets the value of the vnotaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVNOTARIA(String value) {
        this.vnotaria = value;
    }

    /**
     * Gets the value of the vdsctoint property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVDSCTOINT() {
        return vdsctoint;
    }

    /**
     * Sets the value of the vdsctoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVDSCTOINT(BigDecimal value) {
        this.vdsctoint = value;
    }

    /**
     * Gets the value of the vdsctoimp property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVDSCTOIMP() {
        return vdsctoimp;
    }

    /**
     * Sets the value of the vdsctoimp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVDSCTOIMP(BigDecimal value) {
        this.vdsctoimp = value;
    }

    /**
     * Gets the value of the vtotdscto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVTOTDSCTO() {
        return vtotdscto;
    }

    /**
     * Sets the value of the vtotdscto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVTOTDSCTO(BigDecimal value) {
        this.vtotdscto = value;
    }

    /**
     * Gets the value of the fechalimite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHALIMITE() {
        return fechalimite;
    }

    /**
     * Sets the value of the fechalimite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHALIMITE(String value) {
        this.fechalimite = value;
    }

}
