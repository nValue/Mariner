
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="I_CUENTA_BCO" type="{urn:sap-com:document:sap:rfc:functions}char17"/>
 *         &lt;element name="I_FECHA_RECAUDO" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="I_FECHA_VALOR" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="I_HORA_RECAUDO" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="I_NROLIQ" type="{urn:sap-com:document:sap:rfc:functions}char16"/>
 *         &lt;element name="I_VALOR" type="{urn:sap-com:document:sap:rfc:functions}char14"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "icuentabco",
    "ifecharecaudo",
    "ifechavalor",
    "ihorarecaudo",
    "inroliq",
    "ivalor"
})
@XmlRootElement(name = "ZPSCDFM_VUR_PAYMENT")
public class ZPSCDFMVURPAYMENT {

    @XmlElement(name = "I_CUENTA_BCO", required = true)
    protected String icuentabco;
    @XmlElement(name = "I_FECHA_RECAUDO", required = true)
    protected String ifecharecaudo;
    @XmlElement(name = "I_FECHA_VALOR", required = true)
    protected String ifechavalor;
    @XmlElement(name = "I_HORA_RECAUDO", required = true)
    protected String ihorarecaudo;
    @XmlElement(name = "I_NROLIQ", required = true)
    protected String inroliq;
    @XmlElement(name = "I_VALOR", required = true)
    protected String ivalor;

    /**
     * Obtiene el valor de la propiedad icuentabco.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICUENTABCO() {
        return icuentabco;
    }

    /**
     * Define el valor de la propiedad icuentabco.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICUENTABCO(String value) {
        this.icuentabco = value;
    }

    /**
     * Obtiene el valor de la propiedad ifecharecaudo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIFECHARECAUDO() {
        return ifecharecaudo;
    }

    /**
     * Define el valor de la propiedad ifecharecaudo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIFECHARECAUDO(String value) {
        this.ifecharecaudo = value;
    }

    /**
     * Obtiene el valor de la propiedad ifechavalor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIFECHAVALOR() {
        return ifechavalor;
    }

    /**
     * Define el valor de la propiedad ifechavalor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIFECHAVALOR(String value) {
        this.ifechavalor = value;
    }

    /**
     * Obtiene el valor de la propiedad ihorarecaudo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIHORARECAUDO() {
        return ihorarecaudo;
    }

    /**
     * Define el valor de la propiedad ihorarecaudo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIHORARECAUDO(String value) {
        this.ihorarecaudo = value;
    }

    /**
     * Obtiene el valor de la propiedad inroliq.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINROLIQ() {
        return inroliq;
    }

    /**
     * Define el valor de la propiedad inroliq.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINROLIQ(String value) {
        this.inroliq = value;
    }

    /**
     * Obtiene el valor de la propiedad ivalor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIVALOR() {
        return ivalor;
    }

    /**
     * Define el valor de la propiedad ivalor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIVALOR(String value) {
        this.ivalor = value;
    }

}
