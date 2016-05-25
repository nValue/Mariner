
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method;

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
 *         &lt;element name="I_DATE" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
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
    "idate"
})
@XmlRootElement(name = "ZPSCDFM_VUR_GETLIST")
public class ZPSCDFMVURGETLIST {

    @XmlElement(name = "I_DATE", required = true)
    protected String idate;

    /**
     * Obtiene el valor de la propiedad idate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDATE() {
        return idate;
    }

    /**
     * Define el valor de la propiedad idate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDATE(String value) {
        this.idate = value;
    }

}
