package co.com.realtech.mariner.model.ejb.ws.pasarela;

import co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.Transaccion;
import javax.ejb.Local;

/**
 * (interface)
 * EJB encargado del consumo de servicios web y opciones de pasarela de pagos.
 * 
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8.91
 */
@Local
public interface PSEWSConsumerBeanLocal {
    
    public Transaccion consultarTransaccion(String cus, String codEmpresa) throws Exception;
    
}
