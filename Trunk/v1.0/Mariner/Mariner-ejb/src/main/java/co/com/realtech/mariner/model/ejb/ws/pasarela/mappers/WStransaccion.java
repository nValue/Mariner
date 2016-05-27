
package co.com.realtech.mariner.model.ejb.ws.pasarela.mappers;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.0
 * 
 */
@WebService(name = "WStransaccion", targetNamespace = "http://ws/")
public interface WStransaccion {


    /**
     * 
     * @param cus
     * @param codEmpresa
     * @return
     *     returns co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.Transaccion
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTransaccion", targetNamespace = "http://ws/", className = "co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.GetTransaccion")
    @ResponseWrapper(localName = "getTransaccionResponse", targetNamespace = "http://ws/", className = "co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.GetTransaccionResponse")
    public Transaccion getTransaccion(
        @WebParam(name = "CUS", targetNamespace = "")
        String cus,
        @WebParam(name = "CodEmpresa", targetNamespace = "")
        String codEmpresa);

}