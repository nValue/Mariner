
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method;

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
@WebServiceClient(name = "zsn_getlist", targetNamespace = "urn:sap-com:document:sap:rfc:functions", wsdlLocation = "http://lavoragine.elvalle.com:8000/sap/bc/srt/wsdl/bndg_8DA5415773913812E1000000C0A8C847/wsdl11/allinone/standard/document?sap-client=710")
public class ZsnGetlist
    extends Service
{

    private final static URL ZSNGETLIST_WSDL_LOCATION;
    private final static WebServiceException ZSNGETLIST_EXCEPTION;
    private final static QName ZSNGETLIST_QNAME = new QName("urn:sap-com:document:sap:rfc:functions", "zsn_getlist");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://lavoragine.elvalle.com:8000/sap/bc/srt/wsdl/bndg_8DA5415773913812E1000000C0A8C847/wsdl11/allinone/standard/document?sap-client=710");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ZSNGETLIST_WSDL_LOCATION = url;
        ZSNGETLIST_EXCEPTION = e;
    }

    public ZsnGetlist() {
        super(__getWsdlLocation(), ZSNGETLIST_QNAME);
    }

    public ZsnGetlist(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * 
     * @return
     *     returns ZwsvurGetlist
     */
    @WebEndpoint(name = "zbn_getlist")
    public ZwsvurGetlist getZbnGetlist() {
        return super.getPort(new QName("urn:sap-com:document:sap:rfc:functions", "zbn_getlist"), ZwsvurGetlist.class);
    }

    private static URL __getWsdlLocation() {
        if (ZSNGETLIST_EXCEPTION!= null) {
            throw ZSNGETLIST_EXCEPTION;
        }
        return ZSNGETLIST_WSDL_LOCATION;
    }

}
