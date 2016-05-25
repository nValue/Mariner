
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_updstatus;

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
 *         &lt;element name="I_ESTADO" type="{urn:sap-com:document:sap:rfc:functions}char2"/>
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
    "iestado",
    "inroliq"
})
@XmlRootElement(name = "ZPSCDFM_VUR_UPDSTATUS")
public class ZPSCDFMVURUPDSTATUS {

    @XmlElement(name = "I_ESTADO", required = true)
    protected String iestado;
    @XmlElement(name = "I_NROLIQ", required = true)
    protected String inroliq;

    /**
     * Obtiene el valor de la propiedad iestado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIESTADO() {
        return iestado;
    }

    /**
     * Define el valor de la propiedad iestado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIESTADO(String value) {
        this.iestado = value;
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

}
