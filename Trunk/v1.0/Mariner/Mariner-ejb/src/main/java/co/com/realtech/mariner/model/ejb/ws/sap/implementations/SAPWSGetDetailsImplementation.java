package co.com.realtech.mariner.model.ejb.ws.sap.implementations;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDDETACTOT;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZwsvurGetdetail;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZsnGetdetail;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

/**
 * Implementacion metodo getDetail de SAP
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPWSGetDetailsImplementation implements Serializable {

    public static SAPWSGetDetailsImplementation create() {
        return new SAPWSGetDetailsImplementation();
    }

    private SAPWSGetDetailsImplementation() {
    }

    /**
     * Cargue de definicion servicio web getDetail de la liquidacion.
     *
     * @param wsdlUrl
     * @return
     * @throws Exception
     */
    private ZwsvurGetdetail loadServiceDefinition() throws Exception {
        // Constante con URL del servicio web
        String urlWS = ConstantesUtils.cargarConstante("WS-SAP-GET-DETAIL");
        final String userWS = ConstantesUtils.cargarConstante("WS-SAP-USER");
        final String claveWS = ConstantesUtils.cargarConstante("WS-SAP-PASSWORD");

        try {
            if (urlWS == null || urlWS.equals("")) {
                throw new Exception("Error generando Definicion de servicio ZWSVUR_GETDETAIL No se ha encontrado URL (WS-SAP-GET-DETAIL) en sistema de constantes");
            } else {
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userWS, claveWS.toCharArray());
                    }
                });

                QName qname = new QName("urn:sap-com:document:sap:rfc:functions", "zsn_getdetail");
                ZsnGetdetail serviceD = new ZsnGetdetail(null, qname);
                ZwsvurGetdetail port = serviceD.getZbnGetdetail();
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
                return port;
            }
        } catch (Exception e) {
            throw new Exception("Error generando Definicion de servicio zwsvur_getdetail web en URL " + urlWS + " causado por " + e);
        }
    }

    /**
     * Retorna el detalle de la ejecucion al metodo zpscdfmVURGETDETAIL de SAP.
     *
     * @param liquidacion
     * @param responseHeader
     * @param responseDetail
     * @throws Exception
     */
    public void getDetail(String liquidacion, Holder<ZPSCDPRNCAB> responseHeader, Holder<ZPSCDDETACTOT> responseDetail) throws Exception {
        try {
            ZwsvurGetdetail portService = loadServiceDefinition();
            portService.zpscdfmVURGETDETAIL(liquidacion, responseDetail, responseHeader);
        } catch (Exception e) {
            System.out.println("Error en WS - obteniendo detalle liquidacion, causado por: " + e);
            throw e;
        }
    }
}
