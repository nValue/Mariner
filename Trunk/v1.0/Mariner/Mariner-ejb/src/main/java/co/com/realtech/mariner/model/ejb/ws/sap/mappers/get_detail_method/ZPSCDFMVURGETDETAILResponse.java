
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="E_DETAIL" type="{urn:sap-com:document:sap:rfc:functions}ZPSCDTT_VUR_DETAIL"/>
 *         &lt;element name="E_HEADER" type="{urn:sap-com:document:sap:rfc:functions}ZPSCD_PRNCAB"/>
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
    "edetail",
    "eheader"
})
@XmlRootElement(name = "ZPSCDFM_VUR_GETDETAILResponse")
public class ZPSCDFMVURGETDETAILResponse {

    @XmlElement(name = "E_DETAIL", required = true)
    protected ZPSCDTTVURDETAIL edetail;
    @XmlElement(name = "E_HEADER", required = true)
    protected ZPSCDPRNCAB eheader;

    /**
     * Gets the value of the edetail property.
     * 
     * @return
     *     possible object is
     *     {@link ZPSCDTTVURDETAIL }
     *     
     */
    public ZPSCDTTVURDETAIL getEDETAIL() {
        return edetail;
    }

    /**
     * Sets the value of the edetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZPSCDTTVURDETAIL }
     *     
     */
    public void setEDETAIL(ZPSCDTTVURDETAIL value) {
        this.edetail = value;
    }

    /**
     * Gets the value of the eheader property.
     * 
     * @return
     *     possible object is
     *     {@link ZPSCDPRNCAB }
     *     
     */
    public ZPSCDPRNCAB getEHEADER() {
        return eheader;
    }

    /**
     * Sets the value of the eheader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZPSCDPRNCAB }
     *     
     */
    public void setEHEADER(ZPSCDPRNCAB value) {
        this.eheader = value;
    }

}
