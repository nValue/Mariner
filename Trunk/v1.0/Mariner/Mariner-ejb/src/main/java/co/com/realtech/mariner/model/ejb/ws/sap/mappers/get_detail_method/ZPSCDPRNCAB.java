
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.*;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ZPSCD_PRNCAB complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad vnumero.
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
     * Define el valor de la propiedad vnumero.
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
     * Obtiene el valor de la propiedad votonombre.
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
     * Define el valor de la propiedad votonombre.
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
     * Obtiene el valor de la propiedad vbennombre.
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
     * Define el valor de la propiedad vbennombre.
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
     * Obtiene el valor de la propiedad votocc.
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
     * Define el valor de la propiedad votocc.
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
     * Obtiene el valor de la propiedad vbencc.
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
     * Define el valor de la propiedad vbencc.
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
     * Obtiene el valor de la propiedad votonit.
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
     * Define el valor de la propiedad votonit.
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
     * Obtiene el valor de la propiedad vbennit.
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
     * Define el valor de la propiedad vbennit.
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
     * Obtiene el valor de la propiedad votonum.
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
     * Define el valor de la propiedad votonum.
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
     * Obtiene el valor de la propiedad vbennum.
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
     * Define el valor de la propiedad vbennum.
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
     * Obtiene el valor de la propiedad vmunicipio.
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
     * Define el valor de la propiedad vmunicipio.
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
     * Obtiene el valor de la propiedad vclaveper.
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
     * Define el valor de la propiedad vclaveper.
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
     * Obtiene el valor de la propiedad vmunnombre.
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
     * Define el valor de la propiedad vmunnombre.
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
     * Obtiene el valor de la propiedad vfecliq.
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
     * Define el valor de la propiedad vfecliq.
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
     * Obtiene el valor de la propiedad uname.
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
     * Define el valor de la propiedad uname.
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
     * Obtiene el valor de la propiedad vfecldp.
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
     * Define el valor de la propiedad vfecldp.
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
     * Obtiene el valor de la propiedad vtexto.
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
     * Define el valor de la propiedad vtexto.
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
     * Obtiene el valor de la propiedad vclase.
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
     * Define el valor de la propiedad vclase.
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
     * Obtiene el valor de la propiedad vnumliq.
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
     * Define el valor de la propiedad vnumliq.
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
     * Obtiene el valor de la propiedad vdocumento.
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
     * Define el valor de la propiedad vdocumento.
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
     * Obtiene el valor de la propiedad vtotal.
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
     * Define el valor de la propiedad vtotal.
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
     * Obtiene el valor de la propiedad vdescladoc.
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
     * Define el valor de la propiedad vdescladoc.
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
     * Obtiene el valor de la propiedad vdesoridoc.
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
     * Define el valor de la propiedad vdesoridoc.
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
     * Obtiene el valor de la propiedad vfecdoc.
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
     * Define el valor de la propiedad vfecdoc.
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
     * Obtiene el valor de la propiedad vmatinm.
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
     * Define el valor de la propiedad vmatinm.
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
     * Obtiene el valor de la propiedad vtintmora.
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
     * Define el valor de la propiedad vtintmora.
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
     * Obtiene el valor de la propiedad vajuste.
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
     * Define el valor de la propiedad vajuste.
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
     * Obtiene el valor de la propiedad codtar.
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
     * Define el valor de la propiedad codtar.
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
     * Obtiene el valor de la propiedad vnotaria.
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
     * Define el valor de la propiedad vnotaria.
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
     * Obtiene el valor de la propiedad vdsctoint.
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
     * Define el valor de la propiedad vdsctoint.
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
     * Obtiene el valor de la propiedad vdsctoimp.
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
     * Define el valor de la propiedad vdsctoimp.
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
     * Obtiene el valor de la propiedad vtotdscto.
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
     * Define el valor de la propiedad vtotdscto.
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
     * Obtiene el valor de la propiedad fechalimite.
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
     * Define el valor de la propiedad fechalimite.
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
