
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
 *         &lt;element name="I_NROLIQ" type="{urn:sap-com:document:sap:rfc:functions}char16"/>
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
    "inroliq"
})
@XmlRootElement(name = "ZPSCDFM_VUR_GETDETAIL")
public class ZPSCDFMVURGETDETAIL {

    @XmlElement(name = "I_NROLIQ", required = true)
    protected String inroliq;

    /**
     * Gets the value of the inroliq property.
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
     * Sets the value of the inroliq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINROLIQ(String value) {
        this.inroliq = value;
    }

}
