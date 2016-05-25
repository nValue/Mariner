
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
 *         &lt;element name="E_LIST" type="{urn:sap-com:document:sap:rfc:functions}ZPSCDTT_VUR_LIST"/>
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
    "elist"
})
@XmlRootElement(name = "ZPSCDFM_VUR_GETLISTResponse")
public class ZPSCDFMVURGETLISTResponse {

    @XmlElement(name = "E_LIST", required = true)
    protected ZPSCDTTVURLIST elist;

    /**
     * Obtiene el valor de la propiedad elist.
     * 
     * @return
     *     possible object is
     *     {@link ZPSCDTTVURLIST }
     *     
     */
    public ZPSCDTTVURLIST getELIST() {
        return elist;
    }

    /**
     * Define el valor de la propiedad elist.
     * 
     * @param value
     *     allowed object is
     *     {@link ZPSCDTTVURLIST }
     *     
     */
    public void setELIST(ZPSCDTTVURLIST value) {
        this.elist = value;
    }

}
