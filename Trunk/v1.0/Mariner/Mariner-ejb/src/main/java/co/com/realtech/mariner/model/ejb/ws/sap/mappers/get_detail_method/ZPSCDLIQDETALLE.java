
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ZPSCD_LIQDETALLE complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad mandt.
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
     * Define el valor de la propiedad mandt.
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
     * Obtiene el valor de la propiedad renta.
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
     * Define el valor de la propiedad renta.
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
     * Obtiene el valor de la propiedad nroliq.
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
     * Define el valor de la propiedad nroliq.
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
     * Obtiene el valor de la propiedad nrocons.
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
     * Define el valor de la propiedad nrocons.
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
     * Obtiene el valor de la propiedad codigo.
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
     * Define el valor de la propiedad codigo.
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
     * Obtiene el valor de la propiedad nrotornag.
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
     * Define el valor de la propiedad nrotornag.
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
     * Obtiene el valor de la propiedad nroreliq.
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
     * Define el valor de la propiedad nroreliq.
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
     * Obtiene el valor de la propiedad codclase.
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
     * Define el valor de la propiedad codclase.
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
     * Obtiene el valor de la propiedad codmarca.
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
     * Define el valor de la propiedad codmarca.
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
     * Obtiene el valor de la propiedad cantidadliq.
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
     * Define el valor de la propiedad cantidadliq.
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
     * Obtiene el valor de la propiedad cantidad.
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
     * Define el valor de la propiedad cantidad.
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
     * Obtiene el valor de la propiedad basegravliq.
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
     * Define el valor de la propiedad basegravliq.
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
     * Obtiene el valor de la propiedad basegrav.
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
     * Define el valor de la propiedad basegrav.
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
     * Obtiene el valor de la propiedad vrimpuesto.
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
     * Define el valor de la propiedad vrimpuesto.
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
     * Obtiene el valor de la propiedad consvraut.
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
     * Define el valor de la propiedad consvraut.
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
     * Obtiene el valor de la propiedad vrparticip.
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
     * Define el valor de la propiedad vrparticip.
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
     * Obtiene el valor de la propiedad consvrpartaut.
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
     * Define el valor de la propiedad consvrpartaut.
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
     * Obtiene el valor de la propiedad vrexcedent.
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
     * Define el valor de la propiedad vrexcedent.
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
     * Obtiene el valor de la propiedad consvrexced.
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
     * Define el valor de la propiedad consvrexced.
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
     * Obtiene el valor de la propiedad consvrdif.
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
     * Define el valor de la propiedad consvrdif.
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
     * Obtiene el valor de la propiedad consvrdifexced.
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
     * Define el valor de la propiedad consvrdifexced.
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
     * Obtiene el valor de la propiedad regtipoperso.
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
     * Define el valor de la propiedad regtipoperso.
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
     * Obtiene el valor de la propiedad regcantidad.
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
     * Define el valor de la propiedad regcantidad.
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
     * Obtiene el valor de la propiedad regbasegrav.
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
     * Define el valor de la propiedad regbasegrav.
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
     * Obtiene el valor de la propiedad regtaraut.
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
     * Define el valor de la propiedad regtaraut.
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
     * Obtiene el valor de la propiedad regestampaut.
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
     * Define el valor de la propiedad regestampaut.
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
     * Obtiene el valor de la propiedad regintaut.
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
     * Define el valor de la propiedad regintaut.
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
     * Obtiene el valor de la propiedad degtaraut.
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
     * Define el valor de la propiedad degtaraut.
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
     * Obtiene el valor de la propiedad decfoncue.
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
     * Define el valor de la propiedad decfoncue.
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
     * Obtiene el valor de la propiedad unidadliq.
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
     * Define el valor de la propiedad unidadliq.
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
     * Obtiene el valor de la propiedad unidad.
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
     * Define el valor de la propiedad unidad.
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
     * Obtiene el valor de la propiedad prevtadetliq.
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
     * Define el valor de la propiedad prevtadetliq.
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
     * Obtiene el valor de la propiedad prevtadet.
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
     * Define el valor de la propiedad prevtadet.
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
     * Obtiene el valor de la propiedad prevtanetliq.
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
     * Define el valor de la propiedad prevtanetliq.
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
     * Obtiene el valor de la propiedad prevtanet.
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
     * Define el valor de la propiedad prevtanet.
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
     * Obtiene el valor de la propiedad vrempyenvliq.
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
     * Define el valor de la propiedad vrempyenvliq.
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
     * Obtiene el valor de la propiedad vrempyenv.
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
     * Define el valor de la propiedad vrempyenv.
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
     * Obtiene el valor de la propiedad tarifaliq.
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
     * Define el valor de la propiedad tarifaliq.
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
     * Obtiene el valor de la propiedad tarifa.
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
     * Define el valor de la propiedad tarifa.
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
     * Obtiene el valor de la propiedad tarifapartliq.
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
     * Define el valor de la propiedad tarifapartliq.
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
     * Obtiene el valor de la propiedad tarifapart.
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
     * Define el valor de la propiedad tarifapart.
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
     * Obtiene el valor de la propiedad nrodecfc.
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
     * Define el valor de la propiedad nrodecfc.
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
     * Obtiene el valor de la propiedad nroregfc.
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
     * Define el valor de la propiedad nroregfc.
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
     * Obtiene el valor de la propiedad nrodecom.
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
     * Define el valor de la propiedad nrodecom.
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
     * Obtiene el valor de la propiedad vraduyaraliq.
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
     * Define el valor de la propiedad vraduyaraliq.
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
     * Obtiene el valor de la propiedad vraduyara.
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
     * Define el valor de la propiedad vraduyara.
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
     * Obtiene el valor de la propiedad imppromvigliq.
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
     * Define el valor de la propiedad imppromvigliq.
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
     * Obtiene el valor de la propiedad imppromvig.
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
     * Define el valor de la propiedad imppromvig.
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
     * Obtiene el valor de la propiedad estampiudaliq.
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
     * Define el valor de la propiedad estampiudaliq.
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
     * Obtiene el valor de la propiedad estampiuda.
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
     * Define el valor de la propiedad estampiuda.
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
     * Obtiene el valor de la propiedad tarifaporcliq.
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
     * Define el valor de la propiedad tarifaporcliq.
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
     * Obtiene el valor de la propiedad tarifaporc.
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
     * Define el valor de la propiedad tarifaporc.
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
     * Obtiene el valor de la propiedad baseest.
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
     * Define el valor de la propiedad baseest.
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
     * Obtiene el valor de la propiedad baseest1.
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
     * Define el valor de la propiedad baseest1.
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
     * Obtiene el valor de la propiedad baseest2.
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
     * Define el valor de la propiedad baseest2.
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
     * Obtiene el valor de la propiedad baseest3.
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
     * Define el valor de la propiedad baseest3.
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
     * Obtiene el valor de la propiedad baseest4.
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
     * Define el valor de la propiedad baseest4.
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
     * Obtiene el valor de la propiedad baseest5.
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
     * Define el valor de la propiedad baseest5.
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
