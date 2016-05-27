
package co.com.realtech.mariner.model.ejb.ws.pasarela.mappers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para getTransaccion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="getTransaccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CUS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTransaccion", propOrder = {
    "cus",
    "codEmpresa"
})
public class GetTransaccion {

    @XmlElement(name = "CUS")
    protected String cus;
    @XmlElement(name = "CodEmpresa")
    protected String codEmpresa;

    /**
     * Obtiene el valor de la propiedad cus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUS() {
        return cus;
    }

    /**
     * Define el valor de la propiedad cus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUS(String value) {
        this.cus = value;
    }

    /**
     * Obtiene el valor de la propiedad codEmpresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEmpresa() {
        return codEmpresa;
    }

    /**
     * Define el valor de la propiedad codEmpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEmpresa(String value) {
        this.codEmpresa = value;
    }

}
