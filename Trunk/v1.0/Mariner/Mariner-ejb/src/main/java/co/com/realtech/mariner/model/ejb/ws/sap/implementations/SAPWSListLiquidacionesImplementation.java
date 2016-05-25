package co.com.realtech.mariner.model.ejb.ws.sap.implementations;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZwsvurGetdetail;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method.ZPSCDTTVURLIST;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method.ZsnGetlist;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method.ZwsvurGetlist;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

/**
 * Implementacion metodo vur get list (liquidaciones)
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPWSListLiquidacionesImplementation implements Serializable {

    public static SAPWSListLiquidacionesImplementation create() {
        return new SAPWSListLiquidacionesImplementation();
    }

    private SAPWSListLiquidacionesImplementation() {
    }

    /**
     * Cargue de definicion servicio web getDetail de la liquidacion.
     *
     * @param wsdlUrl
     * @return
     * @throws Exception
     */
    private ZwsvurGetlist loadServiceDefinition() throws Exception {
        // Constante con URL del servicio web
        String urlWS = ConstantesUtils.cargarConstante("WS-SAP-GETLIST");
        final String userWS = ConstantesUtils.cargarConstante("WS-SAP-USER");
        final String claveWS = ConstantesUtils.cargarConstante("WS-SAP-PASSWORD");

        try {
            if (urlWS == null || urlWS.equals("")) {
                throw new Exception("Error generando Definicion de servicio No se ha encontrado URL (WS-SAP-GETLIST) en sistema de constantes");
            } else {
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userWS, claveWS.toCharArray());
                    }
                });

                QName qname = new QName("urn:sap-com:document:sap:rfc:functions", "zsn_getlist");
                ZsnGetlist serviceD = new ZsnGetlist(null, qname);
                ZwsvurGetlist port = serviceD.getZbnGetlist();
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
                return port;
            }
        } catch (Exception e) {
            throw new Exception("Error generando Definicion de servicio zsn_getlist web en URL " + urlWS + " causado por " + e);
        }
    }

    /**
     * Ejecucion de metodo zpscdfmVURGETLIST en servicio web.
     *
     * @param fecha
     * @return
     * @throws Exception
     */
    public ZPSCDTTVURLIST zpscdfmVURGETLIST(String fecha) throws Exception {
        try {
            ZwsvurGetlist portService = loadServiceDefinition();
            return portService.zpscdfmVURGETLIST(fecha);
        } catch (Exception e) {
            System.out.println("Error en WS - obteniendo listado de liquidaciones, causado por: " + e);
            throw e;
        }
    }

}
