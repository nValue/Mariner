package co.com.realtech.mariner.model.ejb.ws.pasarela;

import co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.Transaccion;
import co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.WStransaccion;
import co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.WStransaccionService;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

/**
 * EJB encargado del consumo de servicios web y opciones de pasarela de pagos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8.91
 */
@Stateless
public class PSEWSConsumerBean implements PSEWSConsumerBeanLocal {

    /**
     * Cargue de definicion servicio web transacciones Pasarela
     *
     * @param wsdlUrl
     * @return
     * @throws Exception
     */
    private WStransaccion loadServiceDefinition() throws Exception {
        // Constante con URL del servicio web
        String urlWS = ConstantesUtils.cargarConstante("WS-URL-PASARELA-ABCPAGOS");

        try {
            if (urlWS == null || urlWS.equals("")) {
                throw new Exception("Error generando Definicion de servicio WStransaccionService No se ha encontrado URL (WS-URL-PASARELA-ABCPAGOS) en sistema de constantes");
            } else {
                QName qname = new QName("http://ws/", "WStransaccionService");
                WStransaccionService serviceD = new WStransaccionService(null, qname);
                WStransaccion port = serviceD.getWStransaccionPort();
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
                return port;
            }
        } catch (Exception e) {
            throw new Exception("Error generando Definicion de servicio zwsvur_getdetail web en URL " + urlWS + " causado por " + e);
        }
    }

    /**
     * Consulta la informacion de la transaccion en la pasarela de pagos.
     *
     * @param cus
     * @param codEmpresa
     * @return
     * @throws java.lang.Exception
     */
    @Override
    public Transaccion consultarTransaccion(String cus, String codEmpresa) throws Exception {
        Transaccion transaccion = null;
        try {
            WStransaccion wsTrasaccion = loadServiceDefinition();
            transaccion = wsTrasaccion.getTransaccion(cus, codEmpresa);
        } catch (Exception e) {
            System.out.println("Error en WS pasarela de pagos - consultarTransaccion, causado por: " + e);
            throw e;
        }
        return transaccion;
    }
}
