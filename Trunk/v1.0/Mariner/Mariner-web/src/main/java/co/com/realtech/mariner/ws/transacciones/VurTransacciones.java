package co.com.realtech.mariner.ws.transacciones;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Servicio web encargado de exponer las transacciones y funciones para la pasarela de pagos.
 * 
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.9.91
 */
@WebService(serviceName = "VurTransacciones")
public class VurTransacciones {

    /**
     * This is a sample web service operation
     * @param txt
     * @return 
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
