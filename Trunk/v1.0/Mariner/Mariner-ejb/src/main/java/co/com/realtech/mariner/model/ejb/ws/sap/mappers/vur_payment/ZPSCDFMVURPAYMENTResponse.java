
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
 *         &lt;element name="E_MESSAGE" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="E_RETURN" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "emessage",
    "ereturn"
})
@XmlRootElement(name = "ZPSCDFM_VUR_PAYMENTResponse")
public class ZPSCDFMVURPAYMENTResponse {

    @XmlElement(name = "E_MESSAGE", required = true)
    protected String emessage;
    @XmlElement(name = "E_RETURN")
    protected int ereturn;

    /**
     * Obtiene el valor de la propiedad emessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMESSAGE() {
        return emessage;
    }

    /**
     * Define el valor de la propiedad emessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMESSAGE(String value) {
        this.emessage = value;
    }

    /**
     * Obtiene el valor de la propiedad ereturn.
     * 
     */
    public int getERETURN() {
        return ereturn;
    }

    /**
     * Define el valor de la propiedad ereturn.
     * 
     */
    public void setERETURN(int value) {
        this.ereturn = value;
    }

}
