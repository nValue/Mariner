
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.ins_radicacion;

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
 *         &lt;element name="I_NRORAD" type="{urn:sap-com:document:sap:rfc:functions}numeric20"/>
 *         &lt;element name="I_UNAME" type="{urn:sap-com:document:sap:rfc:functions}char12"/>
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
    "inrorad",
    "iuname"
})
@XmlRootElement(name = "ZPSCDFM_VUR_INSRADICA")
public class ZPSCDFMVURINSRADICA {

    @XmlElement(name = "I_NRORAD", required = true)
    protected String inrorad;
    @XmlElement(name = "I_UNAME", required = true)
    protected String iuname;

    /**
     * Obtiene el valor de la propiedad inrorad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINRORAD() {
        return inrorad;
    }

    /**
     * Define el valor de la propiedad inrorad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINRORAD(String value) {
        this.inrorad = value;
    }

    /**
     * Obtiene el valor de la propiedad iuname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUNAME() {
        return iuname;
    }

    /**
     * Define el valor de la propiedad iuname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUNAME(String value) {
        this.iuname = value;
    }

}
