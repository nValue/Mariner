
package co.com.realtech.mariner.model.ejb.ws.pasarela.mappers;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.com.realtech.mariner.model.ejb.ws.pasarela.mappers package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTransaccion_QNAME = new QName("http://ws/", "getTransaccion");
    private final static QName _GetTransaccionResponse_QNAME = new QName("http://ws/", "getTransaccionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.com.realtech.mariner.model.ejb.ws.pasarela.mappers
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTransaccion }
     * 
     */
    public GetTransaccion createGetTransaccion() {
        return new GetTransaccion();
    }

    /**
     * Create an instance of {@link GetTransaccionResponse }
     * 
     */
    public GetTransaccionResponse createGetTransaccionResponse() {
        return new GetTransaccionResponse();
    }

    /**
     * Create an instance of {@link Transaccion }
     * 
     */
    public Transaccion createTransaccion() {
        return new Transaccion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransaccion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "getTransaccion")
    public JAXBElement<GetTransaccion> createGetTransaccion(GetTransaccion value) {
        return new JAXBElement<GetTransaccion>(_GetTransaccion_QNAME, GetTransaccion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransaccionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "getTransaccionResponse")
    public JAXBElement<GetTransaccionResponse> createGetTransaccionResponse(GetTransaccionResponse value) {
        return new JAXBElement<GetTransaccionResponse>(_GetTransaccionResponse_QNAME, GetTransaccionResponse.class, null, value);
    }

}
