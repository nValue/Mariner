package co.com.realtech.mariner.model.ejb.ws.sap.implementations;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDTTVURDETAIL;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZwsvurGetdetail;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZwsvurGetdetail_Service;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import java.io.Serializable;
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
     * Cargue de definicion servicio web CTLServicios.
     *
     * @param wsdlUrl
     * @return
     * @throws Exception
     */
    private ZwsvurGetdetail loadServiceDefinition() throws Exception {
        // Constante con URL del servicio web
        String urlWS = ConstantesUtils.cargarConstante("WS-SAP-GET-DETAIL");
        try {
            if (urlWS == null || urlWS.equals("")) {
                throw new Exception("Error generando Definicion de servicio zwsvur_getdetail No se ha encontrado URL (WS-SAP-GET-DETAIL) en sistema de constantes");
            } else {
                QName qname = new QName("urn:sap-com:document:sap:rfc:functions", "zwsvur_getdetail");
                ZwsvurGetdetail_Service serviceD = new ZwsvurGetdetail_Service(null, qname);
                ZwsvurGetdetail port = serviceD.getZwsvurGetdetail();
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
                bindingProvider.getRequestContext().put("com.sun.xml.ws.request.timeout", 60000);
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
    public void getDetail(String liquidacion,Holder<ZPSCDPRNCAB> responseHeader,Holder<ZPSCDTTVURDETAIL> responseDetail) throws Exception {
        try {
            ZwsvurGetdetail portService = loadServiceDefinition();
            portService.zpscdfmVURGETDETAIL(liquidacion, responseDetail, responseHeader);
        } catch (Exception e) {
            System.out.println("Error en WS - consultarTarifaConvenio, causado por: " + e);
            throw e;
        }
    }
}
