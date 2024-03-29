
package co.com.realtech.mariner.model.ejb.ws.pasarela.mappers;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "WStransaccionService", targetNamespace = "http://ws/", wsdlLocation = "http://190.144.140.19:8080/wstransaccionv2/WStransaccion?wsdl")
public class WStransaccionService
    extends Service
{

    private final static URL WSTRANSACCIONSERVICE_WSDL_LOCATION;
    private final static WebServiceException WSTRANSACCIONSERVICE_EXCEPTION;
    private final static QName WSTRANSACCIONSERVICE_QNAME = new QName("http://ws/", "WStransaccionService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://190.144.140.19:8080/wstransaccionv2/WStransaccion?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSTRANSACCIONSERVICE_WSDL_LOCATION = url;
        WSTRANSACCIONSERVICE_EXCEPTION = e;
    }

    public WStransaccionService() {
        super(__getWsdlLocation(), WSTRANSACCIONSERVICE_QNAME);
    }

    public WStransaccionService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * 
     * @return
     *     returns WStransaccion
     */
    @WebEndpoint(name = "WStransaccionPort")
    public WStransaccion getWStransaccionPort() {
        return super.getPort(new QName("http://ws/", "WStransaccionPort"), WStransaccion.class);
    }

    private static URL __getWsdlLocation() {
        if (WSTRANSACCIONSERVICE_EXCEPTION!= null) {
            throw WSTRANSACCIONSERVICE_EXCEPTION;
        }
        return WSTRANSACCIONSERVICE_WSDL_LOCATION;
    }

}
