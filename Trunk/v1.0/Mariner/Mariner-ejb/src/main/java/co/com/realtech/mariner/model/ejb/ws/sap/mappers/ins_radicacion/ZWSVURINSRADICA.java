
package co.com.realtech.mariner.model.ejb.ws.sap.mappers.ins_radicacion;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.0
 * 
 */
@WebService(name = "ZWSVUR_INSRADICA", targetNamespace = "urn:sap-com:document:sap:rfc:functions")
public interface ZWSVURINSRADICA {


    /**
     * 
     * @param iNRORAD
     * @param eRETURN
     * @param iUNAME
     * @param eMESSAGE
     */
    @WebMethod(operationName = "ZPSCDFM_VUR_INSRADICA")
    @RequestWrapper(localName = "ZPSCDFM_VUR_INSRADICA", targetNamespace = "urn:sap-com:document:sap:rfc:functions", className = "co.com.realtech.mariner.model.ejb.ws.sap.mappers.ins_radicacion.ZPSCDFMVURINSRADICA")
    @ResponseWrapper(localName = "ZPSCDFM_VUR_INSRADICAResponse", targetNamespace = "urn:sap-com:document:sap:rfc:functions", className = "co.com.realtech.mariner.model.ejb.ws.sap.mappers.ins_radicacion.ZPSCDFMVURINSRADICAResponse")
    public void zpscdfmVURINSRADICA(
        @WebParam(name = "I_NRORAD", targetNamespace = "")
        String iNRORAD,
        @WebParam(name = "I_UNAME", targetNamespace = "")
        String iUNAME,
        @WebParam(name = "E_MESSAGE", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> eMESSAGE,
        @WebParam(name = "E_RETURN", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<Integer> eRETURN);

}
