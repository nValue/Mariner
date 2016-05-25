
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ZPSCD_DETACTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ZPSCD_DETACTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INDICE" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="COD_ACTO" type="{urn:sap-com:document:sap:rfc:functions}numeric4"/>
 *         &lt;element name="NOMBRE_ASR" type="{urn:sap-com:document:sap:rfc:functions}char100"/>
 *         &lt;element name="TIP_PER" type="{urn:sap-com:document:sap:rfc:functions}char3"/>
 *         &lt;element name="BASE_IIPP" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_CIO" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_EST" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="TARIFA_IIPP" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="TARIFA_CIO" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="BASE_EST1" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="IMPTO_IIPP" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="IMPTO_CIO" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="BASE_EST2" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="VR_SIN_CUANTIA" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="V_VAL_SERINF" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="VR_INTERESES" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="V_FEC_DOC" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="V_FECVEN" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="V_FECPAGO" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="MARK" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="MARK_PR" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="BASE_EST3" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
 *         &lt;element name="VR_EST_PROD" type="{urn:sap-com:document:sap:rfc:functions}curr15.2"/>
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
@XmlType(name = "ZPSCD_DETACTO", propOrder = {
    "indice",
    "codacto",
    "nombreasr",
    "tipper",
    "baseiipp",
    "basecio",
    "baseest",
    "tarifaiipp",
    "tarifacio",
    "baseest1",
    "imptoiipp",
    "imptocio",
    "baseest2",
    "vrsincuantia",
    "vvalserinf",
    "vrintereses",
    "vfecdoc",
    "vfecven",
    "vfecpago",
    "mark",
    "markpr",
    "baseest3",
    "vrestprod",
    "baseest4",
    "baseest5"
})
public class ZPSCDDETACTO {

    @XmlElement(name = "INDICE", required = true)
    protected String indice;
    @XmlElement(name = "COD_ACTO", required = true)
    protected String codacto;
    @XmlElement(name = "NOMBRE_ASR", required = true)
    protected String nombreasr;
    @XmlElement(name = "TIP_PER", required = true)
    protected String tipper;
    @XmlElement(name = "BASE_IIPP", required = true)
    protected BigDecimal baseiipp;
    @XmlElement(name = "BASE_CIO", required = true)
    protected BigDecimal basecio;
    @XmlElement(name = "BASE_EST", required = true)
    protected BigDecimal baseest;
    @XmlElement(name = "TARIFA_IIPP", required = true)
    protected String tarifaiipp;
    @XmlElement(name = "TARIFA_CIO", required = true)
    protected String tarifacio;
    @XmlElement(name = "BASE_EST1", required = true)
    protected BigDecimal baseest1;
    @XmlElement(name = "IMPTO_IIPP", required = true)
    protected BigDecimal imptoiipp;
    @XmlElement(name = "IMPTO_CIO", required = true)
    protected BigDecimal imptocio;
    @XmlElement(name = "BASE_EST2", required = true)
    protected BigDecimal baseest2;
    @XmlElement(name = "VR_SIN_CUANTIA", required = true)
    protected BigDecimal vrsincuantia;
    @XmlElement(name = "V_VAL_SERINF", required = true)
    protected BigDecimal vvalserinf;
    @XmlElement(name = "VR_INTERESES", required = true)
    protected BigDecimal vrintereses;
    @XmlElement(name = "V_FEC_DOC", required = true)
    protected String vfecdoc;
    @XmlElement(name = "V_FECVEN", required = true)
    protected String vfecven;
    @XmlElement(name = "V_FECPAGO", required = true)
    protected String vfecpago;
    @XmlElement(name = "MARK", required = true)
    protected String mark;
    @XmlElement(name = "MARK_PR", required = true)
    protected String markpr;
    @XmlElement(name = "BASE_EST3", required = true)
    protected BigDecimal baseest3;
    @XmlElement(name = "VR_EST_PROD", required = true)
    protected BigDecimal vrestprod;
    @XmlElement(name = "BASE_EST4", required = true)
    protected BigDecimal baseest4;
    @XmlElement(name = "BASE_EST5", required = true)
    protected BigDecimal baseest5;

    /**
     * Obtiene el valor de la propiedad indice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDICE() {
        return indice;
    }

    /**
     * Define el valor de la propiedad indice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDICE(String value) {
        this.indice = value;
    }

    /**
     * Obtiene el valor de la propiedad codacto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODACTO() {
        return codacto;
    }

    /**
     * Define el valor de la propiedad codacto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODACTO(String value) {
        this.codacto = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreasr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREASR() {
        return nombreasr;
    }

    /**
     * Define el valor de la propiedad nombreasr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREASR(String value) {
        this.nombreasr = value;
    }

    /**
     * Obtiene el valor de la propiedad tipper.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPPER() {
        return tipper;
    }

    /**
     * Define el valor de la propiedad tipper.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPPER(String value) {
        this.tipper = value;
    }

    /**
     * Obtiene el valor de la propiedad baseiipp.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEIIPP() {
        return baseiipp;
    }

    /**
     * Define el valor de la propiedad baseiipp.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEIIPP(BigDecimal value) {
        this.baseiipp = value;
    }

    /**
     * Obtiene el valor de la propiedad basecio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASECIO() {
        return basecio;
    }

    /**
     * Define el valor de la propiedad basecio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASECIO(BigDecimal value) {
        this.basecio = value;
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
     * Obtiene el valor de la propiedad tarifaiipp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTARIFAIIPP() {
        return tarifaiipp;
    }

    /**
     * Define el valor de la propiedad tarifaiipp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTARIFAIIPP(String value) {
        this.tarifaiipp = value;
    }

    /**
     * Obtiene el valor de la propiedad tarifacio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTARIFACIO() {
        return tarifacio;
    }

    /**
     * Define el valor de la propiedad tarifacio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTARIFACIO(String value) {
        this.tarifacio = value;
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
     * Obtiene el valor de la propiedad imptoiipp.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPTOIIPP() {
        return imptoiipp;
    }

    /**
     * Define el valor de la propiedad imptoiipp.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPTOIIPP(BigDecimal value) {
        this.imptoiipp = value;
    }

    /**
     * Obtiene el valor de la propiedad imptocio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPTOCIO() {
        return imptocio;
    }

    /**
     * Define el valor de la propiedad imptocio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPTOCIO(BigDecimal value) {
        this.imptocio = value;
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
     * Obtiene el valor de la propiedad vrsincuantia.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVRSINCUANTIA() {
        return vrsincuantia;
    }

    /**
     * Define el valor de la propiedad vrsincuantia.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVRSINCUANTIA(BigDecimal value) {
        this.vrsincuantia = value;
    }

    /**
     * Obtiene el valor de la propiedad vvalserinf.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVVALSERINF() {
        return vvalserinf;
    }

    /**
     * Define el valor de la propiedad vvalserinf.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVVALSERINF(BigDecimal value) {
        this.vvalserinf = value;
    }

    /**
     * Obtiene el valor de la propiedad vrintereses.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVRINTERESES() {
        return vrintereses;
    }

    /**
     * Define el valor de la propiedad vrintereses.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVRINTERESES(BigDecimal value) {
        this.vrintereses = value;
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
     * Obtiene el valor de la propiedad vfecven.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVFECVEN() {
        return vfecven;
    }

    /**
     * Define el valor de la propiedad vfecven.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVFECVEN(String value) {
        this.vfecven = value;
    }

    /**
     * Obtiene el valor de la propiedad vfecpago.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVFECPAGO() {
        return vfecpago;
    }

    /**
     * Define el valor de la propiedad vfecpago.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVFECPAGO(String value) {
        this.vfecpago = value;
    }

    /**
     * Obtiene el valor de la propiedad mark.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARK() {
        return mark;
    }

    /**
     * Define el valor de la propiedad mark.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARK(String value) {
        this.mark = value;
    }

    /**
     * Obtiene el valor de la propiedad markpr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMARKPR() {
        return markpr;
    }

    /**
     * Define el valor de la propiedad markpr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMARKPR(String value) {
        this.markpr = value;
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
     * Obtiene el valor de la propiedad vrestprod.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVRESTPROD() {
        return vrestprod;
    }

    /**
     * Define el valor de la propiedad vrestprod.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVRESTPROD(BigDecimal value) {
        this.vrestprod = value;
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
