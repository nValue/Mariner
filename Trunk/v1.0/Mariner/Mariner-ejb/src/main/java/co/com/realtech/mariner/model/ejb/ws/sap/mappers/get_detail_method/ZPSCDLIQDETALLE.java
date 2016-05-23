
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZPSCD_LIQDETALLE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZPSCD_LIQDETALLE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MANDT" type="{urn:sap-com:document:sap:rfc:functions}clnt3"/>
 *         &lt;element name="RENTA" type="{urn:sap-com:document:sap:rfc:functions}char2"/>
 *         &lt;element name="NROLIQ" type="{urn:sap-com:document:sap:rfc:functions}char16"/>
 *         &lt;element name="NROCONS" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="CODIGO" type="{urn:sap-com:document:sap:rfc:functions}numeric4"/>
 *         &lt;element name="NROTORNAG" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="NRORELIQ" type="{urn:sap-com:document:sap:rfc:functions}char16"/>
 *         &lt;element name="CODCLASE" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="CODMARCA" type="{urn:sap-com:document:sap:rfc:functions}char12"/>
 *         &lt;element name="CANTIDAD_LIQ" type="{urn:sap-com:document:sap:rfc:functions}quantum11.2"/>
 *         &lt;element name="CANTIDAD" type="{urn:sap-com:document:sap:rfc:functions}quantum11.2"/>
 *         &lt;element name="BASEGRAV_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASEGRAV" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="VRIMPUESTO" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="CONS_VRAUT" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="VRPARTICIP" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="CONS_VRPARTAUT" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="VREXCEDENT" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="CONS_VREXCED" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="CONS_VRDIF" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="CONS_VRDIFEXCED" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="REG_TIPOPERSO" type="{urn:sap-com:document:sap:rfc:functions}char3"/>
 *         &lt;element name="REG_CANTIDAD" type="{urn:sap-com:document:sap:rfc:functions}quantum13.3"/>
 *         &lt;element name="REG_BASEGRAV" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="REG_TARAUT" type="{urn:sap-com:document:sap:rfc:functions}char7"/>
 *         &lt;element name="REG_ESTAMPAUT" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="REG_INTAUT" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="DEG_TARAUT" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="DECFONCUE" type="{urn:sap-com:document:sap:rfc:functions}char12"/>
 *         &lt;element name="UNIDAD_LIQ" type="{urn:sap-com:document:sap:rfc:functions}char20"/>
 *         &lt;element name="UNIDAD" type="{urn:sap-com:document:sap:rfc:functions}char20"/>
 *         &lt;element name="PREVTADET_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="PREVTADET" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="PREVTANET_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="PREVTANET" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="VREMPYENV_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="VREMPYENV" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="TARIFA_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="TARIFA" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="TARIFAPART_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="TARIFAPART" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="NRODECFC" type="{urn:sap-com:document:sap:rfc:functions}char12"/>
 *         &lt;element name="NROREGFC" type="{urn:sap-com:document:sap:rfc:functions}char2"/>
 *         &lt;element name="NRODECOM" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="VRADUYARA_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="VRADUYARA" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="IMPPROMVIG_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="IMPPROMVIG" type="{urn:sap-com:document:sap:rfc:functions}curr13.2"/>
 *         &lt;element name="ESTAMPI_UDA_LIQ" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="ESTAMPI_UDA" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="TARIFAPORC_LIQ" type="{urn:sap-com:document:sap:rfc:functions}decimal6.3"/>
 *         &lt;element name="TARIFAPORC" type="{urn:sap-com:document:sap:rfc:functions}decimal6.3"/>
 *         &lt;element name="BASE_EST" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_EST1" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_EST2" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_EST3" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_EST4" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_EST5" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPSCD_LIQDETALLE", propOrder = {
    "mandt",
    "renta",
    "nroliq",
    "nrocons",
    "codigo",
    "nrotornag",
    "nroreliq",
    "codclase",
    "codmarca",
    "cantidadliq",
    "cantidad",
    "basegravliq",
    "basegrav",
    "vrimpuesto",
    "consvraut",
    "vrparticip",
    "consvrpartaut",
    "vrexcedent",
    "consvrexced",
    "consvrdif",
    "consvrdifexced",
    "regtipoperso",
    "regcantidad",
    "regbasegrav",
    "regtaraut",
    "regestampaut",
    "regintaut",
    "degtaraut",
    "decfoncue",
    "unidadliq",
    "unidad",
    "prevtadetliq",
    "prevtadet",
    "prevtanetliq",
    "prevtanet",
    "vrempyenvliq",
    "vrempyenv",
    "tarifaliq",
    "tarifa",
    "tarifapartliq",
    "tarifapart",
    "nrodecfc",
    "nroregfc",
    "nrodecom",
    "vraduyaraliq",
    "vraduyara",
    "imppromvigliq",
    "imppromvig",
    "estampiudaliq",
    "estampiuda",
    "tarifaporcliq",
    "tarifaporc",
    "baseest",
    "baseest1",
    "baseest2",
    "baseest3",
    "baseest4",
    "baseest5"
})
public class ZPSCDLIQDETALLE {

    @XmlElement(name = "MANDT", required = true)
    protected String mandt;
    @XmlElement(name = "RENTA", required = true)
    protected String renta;
    @XmlElement(name = "NROLIQ", required = true)
    protected String nroliq;
    @XmlElement(name = "NROCONS", required = true)
    protected String nrocons;
    @XmlElement(name = "CODIGO", required = true)
    protected String codigo;
    @XmlElement(name = "NROTORNAG", required = true)
    protected String nrotornag;
    @XmlElement(name = "NRORELIQ", required = true)
    protected String nroreliq;
    @XmlElement(name = "CODCLASE", required = true)
    protected String codclase;
    @XmlElement(name = "CODMARCA", required = true)
    protected String codmarca;
    @XmlElement(name = "CANTIDAD_LIQ", required = true)
    protected BigDecimal cantidadliq;
    @XmlElement(name = "CANTIDAD", required = true)
    protected BigDecimal cantidad;
    @XmlElement(name = "BASEGRAV_LIQ", required = true)
    protected BigDecimal basegravliq;
    @XmlElement(name = "BASEGRAV", required = true)
    protected BigDecimal basegrav;
    @XmlElement(name = "VRIMPUESTO", required = true)
    protected BigDecimal vrimpuesto;
    @XmlElement(name = "CONS_VRAUT", required = true)
    protected BigDecimal consvraut;
    @XmlElement(name = "VRPARTICIP", required = true)
    protected BigDecimal vrparticip;
    @XmlElement(name = "CONS_VRPARTAUT", required = true)
    protected BigDecimal consvrpartaut;
    @XmlElement(name = "VREXCEDENT", required = true)
    protected BigDecimal vrexcedent;
    @XmlElement(name = "CONS_VREXCED", required = true)
    protected BigDecimal consvrexced;
    @XmlElement(name = "CONS_VRDIF", required = true)
    protected BigDecimal consvrdif;
    @XmlElement(name = "CONS_VRDIFEXCED", required = true)
    protected BigDecimal consvrdifexced;
    @XmlElement(name = "REG_TIPOPERSO", required = true)
    protected String regtipoperso;
    @XmlElement(name = "REG_CANTIDAD", required = true)
    protected BigDecimal regcantidad;
    @XmlElement(name = "REG_BASEGRAV", required = true)
    protected BigDecimal regbasegrav;
    @XmlElement(name = "REG_TARAUT", required = true)
    protected String regtaraut;
    @XmlElement(name = "REG_ESTAMPAUT", required = true)
    protected BigDecimal regestampaut;
    @XmlElement(name = "REG_INTAUT", required = true)
    protected BigDecimal regintaut;
    @XmlElement(name = "DEG_TARAUT", required = true)
    protected BigDecimal degtaraut;
    @XmlElement(name = "DECFONCUE", required = true)
    protected String decfoncue;
    @XmlElement(name = "UNIDAD_LIQ", required = true)
    protected String unidadliq;
    @XmlElement(name = "UNIDAD", required = true)
    protected String unidad;
    @XmlElement(name = "PREVTADET_LIQ", required = true)
    protected BigDecimal prevtadetliq;
    @XmlElement(name = "PREVTADET", required = true)
    protected BigDecimal prevtadet;
    @XmlElement(name = "PREVTANET_LIQ", required = true)
    protected BigDecimal prevtanetliq;
    @XmlElement(name = "PREVTANET", required = true)
    protected BigDecimal prevtanet;
    @XmlElement(name = "VREMPYENV_LIQ", required = true)
    protected BigDecimal vrempyenvliq;
    @XmlElement(name = "VREMPYENV", required = true)
    protected BigDecimal vrempyenv;
    @XmlElement(name = "TARIFA_LIQ", required = true)
    protected BigDecimal tarifaliq;
    @XmlElement(name = "TARIFA", required = true)
    protected BigDecimal tarifa;
    @XmlElement(name = "TARIFAPART_LIQ", required = true)
    protected BigDecimal tarifapartliq;
    @XmlElement(name = "TARIFAPART", required = true)
    protected BigDecimal tarifapart;
    @XmlElement(name = "NRODECFC", required = true)
    protected String nrodecfc;
    @XmlElement(name = "NROREGFC", required = true)
    protected String nroregfc;
    @XmlElement(name = "NRODECOM", required = true)
    protected String nrodecom;
    @XmlElement(name = "VRADUYARA_LIQ", required = true)
    protected BigDecimal vraduyaraliq;
    @XmlElement(name = "VRADUYARA", required = true)
    protected BigDecimal vraduyara;
    @XmlElement(name = "IMPPROMVIG_LIQ", required = true)
    protected BigDecimal imppromvigliq;
    @XmlElement(name = "IMPPROMVIG", required = true)
    protected BigDecimal imppromvig;
    @XmlElement(name = "ESTAMPI_UDA_LIQ", required = true)
    protected BigDecimal estampiudaliq;
    @XmlElement(name = "ESTAMPI_UDA", required = true)
    protected BigDecimal estampiuda;
    @XmlElement(name = "TARIFAPORC_LIQ", required = true)
    protected BigDecimal tarifaporcliq;
    @XmlElement(name = "TARIFAPORC", required = true)
    protected BigDecimal tarifaporc;
    @XmlElement(name = "BASE_EST", required = true)
    protected BigDecimal baseest;
    @XmlElement(name = "BASE_EST1", required = true)
    protected BigDecimal baseest1;
    @XmlElement(name = "BASE_EST2", required = true)
    protected BigDecimal baseest2;
    @XmlElement(name = "BASE_EST3", required = true)
    protected BigDecimal baseest3;
    @XmlElement(name = "BASE_EST4", required = true)
    protected BigDecimal baseest4;
    @XmlElement(name = "BASE_EST5", required = true)
    protected BigDecimal baseest5;

    /**
     * Gets the value of the mandt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMANDT() {
        return mandt;
    }

    /**
     * Sets the value of the mandt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMANDT(String value) {
        this.mandt = value;
    }

    /**
     * Gets the value of the renta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRENTA() {
        return renta;
    }

    /**
     * Sets the value of the renta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRENTA(String value) {
        this.renta = value;
    }

    /**
     * Gets the value of the nroliq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNROLIQ() {
        return nroliq;
    }

    /**
     * Sets the value of the nroliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNROLIQ(String value) {
        this.nroliq = value;
    }

    /**
     * Gets the value of the nrocons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNROCONS() {
        return nrocons;
    }

    /**
     * Sets the value of the nrocons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNROCONS(String value) {
        this.nrocons = value;
    }

    /**
     * Gets the value of the codigo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGO() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGO(String value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the nrotornag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNROTORNAG() {
        return nrotornag;
    }

    /**
     * Sets the value of the nrotornag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNROTORNAG(String value) {
        this.nrotornag = value;
    }

    /**
     * Gets the value of the nroreliq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNRORELIQ() {
        return nroreliq;
    }

    /**
     * Sets the value of the nroreliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNRORELIQ(String value) {
        this.nroreliq = value;
    }

    /**
     * Gets the value of the codclase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODCLASE() {
        return codclase;
    }

    /**
     * Sets the value of the codclase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODCLASE(String value) {
        this.codclase = value;
    }

    /**
     * Gets the value of the codmarca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODMARCA() {
        return codmarca;
    }

    /**
     * Sets the value of the codmarca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODMARCA(String value) {
        this.codmarca = value;
    }

    /**
     * Gets the value of the cantidadliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCANTIDADLIQ() {
        return cantidadliq;
    }

    /**
     * Sets the value of the cantidadliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCANTIDADLIQ(BigDecimal value) {
        this.cantidadliq = value;
    }

    /**
     * Gets the value of the cantidad property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCANTIDAD() {
        return cantidad;
    }

    /**
     * Sets the value of the cantidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCANTIDAD(BigDecimal value) {
        this.cantidad = value;
    }

    /**
     * Gets the value of the basegravliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEGRAVLIQ() {
        return basegravliq;
    }

    /**
     * Sets the value of the basegravliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEGRAVLIQ(BigDecimal value) {
        this.basegravliq = value;
    }

    /**
     * Gets the value of the basegrav property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEGRAV() {
        return basegrav;
    }

    /**
     * Sets the value of the basegrav property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEGRAV(BigDecimal value) {
        this.basegrav = value;
    }

    /**
     * Gets the value of the vrimpuesto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVRIMPUESTO() {
        return vrimpuesto;
    }

    /**
     * Sets the value of the vrimpuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVRIMPUESTO(BigDecimal value) {
        this.vrimpuesto = value;
    }

    /**
     * Gets the value of the consvraut property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCONSVRAUT() {
        return consvraut;
    }

    /**
     * Sets the value of the consvraut property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCONSVRAUT(BigDecimal value) {
        this.consvraut = value;
    }

    /**
     * Gets the value of the vrparticip property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVRPARTICIP() {
        return vrparticip;
    }

    /**
     * Sets the value of the vrparticip property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVRPARTICIP(BigDecimal value) {
        this.vrparticip = value;
    }

    /**
     * Gets the value of the consvrpartaut property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCONSVRPARTAUT() {
        return consvrpartaut;
    }

    /**
     * Sets the value of the consvrpartaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCONSVRPARTAUT(BigDecimal value) {
        this.consvrpartaut = value;
    }

    /**
     * Gets the value of the vrexcedent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVREXCEDENT() {
        return vrexcedent;
    }

    /**
     * Sets the value of the vrexcedent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVREXCEDENT(BigDecimal value) {
        this.vrexcedent = value;
    }

    /**
     * Gets the value of the consvrexced property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCONSVREXCED() {
        return consvrexced;
    }

    /**
     * Sets the value of the consvrexced property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCONSVREXCED(BigDecimal value) {
        this.consvrexced = value;
    }

    /**
     * Gets the value of the consvrdif property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCONSVRDIF() {
        return consvrdif;
    }

    /**
     * Sets the value of the consvrdif property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCONSVRDIF(BigDecimal value) {
        this.consvrdif = value;
    }

    /**
     * Gets the value of the consvrdifexced property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCONSVRDIFEXCED() {
        return consvrdifexced;
    }

    /**
     * Sets the value of the consvrdifexced property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCONSVRDIFEXCED(BigDecimal value) {
        this.consvrdifexced = value;
    }

    /**
     * Gets the value of the regtipoperso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGTIPOPERSO() {
        return regtipoperso;
    }

    /**
     * Sets the value of the regtipoperso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGTIPOPERSO(String value) {
        this.regtipoperso = value;
    }

    /**
     * Gets the value of the regcantidad property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getREGCANTIDAD() {
        return regcantidad;
    }

    /**
     * Sets the value of the regcantidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setREGCANTIDAD(BigDecimal value) {
        this.regcantidad = value;
    }

    /**
     * Gets the value of the regbasegrav property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getREGBASEGRAV() {
        return regbasegrav;
    }

    /**
     * Sets the value of the regbasegrav property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setREGBASEGRAV(BigDecimal value) {
        this.regbasegrav = value;
    }

    /**
     * Gets the value of the regtaraut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGTARAUT() {
        return regtaraut;
    }

    /**
     * Sets the value of the regtaraut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGTARAUT(String value) {
        this.regtaraut = value;
    }

    /**
     * Gets the value of the regestampaut property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getREGESTAMPAUT() {
        return regestampaut;
    }

    /**
     * Sets the value of the regestampaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setREGESTAMPAUT(BigDecimal value) {
        this.regestampaut = value;
    }

    /**
     * Gets the value of the regintaut property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getREGINTAUT() {
        return regintaut;
    }

    /**
     * Sets the value of the regintaut property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setREGINTAUT(BigDecimal value) {
        this.regintaut = value;
    }

    /**
     * Gets the value of the degtaraut property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDEGTARAUT() {
        return degtaraut;
    }

    /**
     * Sets the value of the degtaraut property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDEGTARAUT(BigDecimal value) {
        this.degtaraut = value;
    }

    /**
     * Gets the value of the decfoncue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDECFONCUE() {
        return decfoncue;
    }

    /**
     * Sets the value of the decfoncue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDECFONCUE(String value) {
        this.decfoncue = value;
    }

    /**
     * Gets the value of the unidadliq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNIDADLIQ() {
        return unidadliq;
    }

    /**
     * Sets the value of the unidadliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNIDADLIQ(String value) {
        this.unidadliq = value;
    }

    /**
     * Gets the value of the unidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNIDAD() {
        return unidad;
    }

    /**
     * Sets the value of the unidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNIDAD(String value) {
        this.unidad = value;
    }

    /**
     * Gets the value of the prevtadetliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPREVTADETLIQ() {
        return prevtadetliq;
    }

    /**
     * Sets the value of the prevtadetliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPREVTADETLIQ(BigDecimal value) {
        this.prevtadetliq = value;
    }

    /**
     * Gets the value of the prevtadet property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPREVTADET() {
        return prevtadet;
    }

    /**
     * Sets the value of the prevtadet property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPREVTADET(BigDecimal value) {
        this.prevtadet = value;
    }

    /**
     * Gets the value of the prevtanetliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPREVTANETLIQ() {
        return prevtanetliq;
    }

    /**
     * Sets the value of the prevtanetliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPREVTANETLIQ(BigDecimal value) {
        this.prevtanetliq = value;
    }

    /**
     * Gets the value of the prevtanet property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPREVTANET() {
        return prevtanet;
    }

    /**
     * Sets the value of the prevtanet property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPREVTANET(BigDecimal value) {
        this.prevtanet = value;
    }

    /**
     * Gets the value of the vrempyenvliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVREMPYENVLIQ() {
        return vrempyenvliq;
    }

    /**
     * Sets the value of the vrempyenvliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVREMPYENVLIQ(BigDecimal value) {
        this.vrempyenvliq = value;
    }

    /**
     * Gets the value of the vrempyenv property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVREMPYENV() {
        return vrempyenv;
    }

    /**
     * Sets the value of the vrempyenv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVREMPYENV(BigDecimal value) {
        this.vrempyenv = value;
    }

    /**
     * Gets the value of the tarifaliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTARIFALIQ() {
        return tarifaliq;
    }

    /**
     * Sets the value of the tarifaliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTARIFALIQ(BigDecimal value) {
        this.tarifaliq = value;
    }

    /**
     * Gets the value of the tarifa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTARIFA() {
        return tarifa;
    }

    /**
     * Sets the value of the tarifa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTARIFA(BigDecimal value) {
        this.tarifa = value;
    }

    /**
     * Gets the value of the tarifapartliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTARIFAPARTLIQ() {
        return tarifapartliq;
    }

    /**
     * Sets the value of the tarifapartliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTARIFAPARTLIQ(BigDecimal value) {
        this.tarifapartliq = value;
    }

    /**
     * Gets the value of the tarifapart property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTARIFAPART() {
        return tarifapart;
    }

    /**
     * Sets the value of the tarifapart property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTARIFAPART(BigDecimal value) {
        this.tarifapart = value;
    }

    /**
     * Gets the value of the nrodecfc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNRODECFC() {
        return nrodecfc;
    }

    /**
     * Sets the value of the nrodecfc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNRODECFC(String value) {
        this.nrodecfc = value;
    }

    /**
     * Gets the value of the nroregfc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNROREGFC() {
        return nroregfc;
    }

    /**
     * Sets the value of the nroregfc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNROREGFC(String value) {
        this.nroregfc = value;
    }

    /**
     * Gets the value of the nrodecom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNRODECOM() {
        return nrodecom;
    }

    /**
     * Sets the value of the nrodecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNRODECOM(String value) {
        this.nrodecom = value;
    }

    /**
     * Gets the value of the vraduyaraliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVRADUYARALIQ() {
        return vraduyaraliq;
    }

    /**
     * Sets the value of the vraduyaraliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVRADUYARALIQ(BigDecimal value) {
        this.vraduyaraliq = value;
    }

    /**
     * Gets the value of the vraduyara property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVRADUYARA() {
        return vraduyara;
    }

    /**
     * Sets the value of the vraduyara property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVRADUYARA(BigDecimal value) {
        this.vraduyara = value;
    }

    /**
     * Gets the value of the imppromvigliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPPROMVIGLIQ() {
        return imppromvigliq;
    }

    /**
     * Sets the value of the imppromvigliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPPROMVIGLIQ(BigDecimal value) {
        this.imppromvigliq = value;
    }

    /**
     * Gets the value of the imppromvig property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPPROMVIG() {
        return imppromvig;
    }

    /**
     * Sets the value of the imppromvig property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPPROMVIG(BigDecimal value) {
        this.imppromvig = value;
    }

    /**
     * Gets the value of the estampiudaliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getESTAMPIUDALIQ() {
        return estampiudaliq;
    }

    /**
     * Sets the value of the estampiudaliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setESTAMPIUDALIQ(BigDecimal value) {
        this.estampiudaliq = value;
    }

    /**
     * Gets the value of the estampiuda property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getESTAMPIUDA() {
        return estampiuda;
    }

    /**
     * Sets the value of the estampiuda property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setESTAMPIUDA(BigDecimal value) {
        this.estampiuda = value;
    }

    /**
     * Gets the value of the tarifaporcliq property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTARIFAPORCLIQ() {
        return tarifaporcliq;
    }

    /**
     * Sets the value of the tarifaporcliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTARIFAPORCLIQ(BigDecimal value) {
        this.tarifaporcliq = value;
    }

    /**
     * Gets the value of the tarifaporc property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTARIFAPORC() {
        return tarifaporc;
    }

    /**
     * Sets the value of the tarifaporc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTARIFAPORC(BigDecimal value) {
        this.tarifaporc = value;
    }

    /**
     * Gets the value of the baseest property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEEST() {
        return baseest;
    }

    /**
     * Sets the value of the baseest property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEEST(BigDecimal value) {
        this.baseest = value;
    }

    /**
     * Gets the value of the baseest1 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEEST1() {
        return baseest1;
    }

    /**
     * Sets the value of the baseest1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEEST1(BigDecimal value) {
        this.baseest1 = value;
    }

    /**
     * Gets the value of the baseest2 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEEST2() {
        return baseest2;
    }

    /**
     * Sets the value of the baseest2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEEST2(BigDecimal value) {
        this.baseest2 = value;
    }

    /**
     * Gets the value of the baseest3 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEEST3() {
        return baseest3;
    }

    /**
     * Sets the value of the baseest3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEEST3(BigDecimal value) {
        this.baseest3 = value;
    }

    /**
     * Gets the value of the baseest4 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEEST4() {
        return baseest4;
    }

    /**
     * Sets the value of the baseest4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEEST4(BigDecimal value) {
        this.baseest4 = value;
    }

    /**
     * Gets the value of the baseest5 property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEEST5() {
        return baseest5;
    }

    /**
     * Sets the value of the baseest5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEEST5(BigDecimal value) {
        this.baseest5 = value;
    }

}
