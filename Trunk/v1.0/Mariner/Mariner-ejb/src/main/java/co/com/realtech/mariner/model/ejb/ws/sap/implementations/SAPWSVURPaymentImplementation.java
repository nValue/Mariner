package co.com.realtech.mariner.model.ejb.ws.sap.implementations;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZwsvurGetdetail;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZwsvurGetdetail_Service;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_payment.ZwsvurPayment;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_payment.ZwsvurPayment_Service;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import java.io.Serializable;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

/**
 * Implementacion broker para servicios de pagos VUR SAP
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPWSVURPaymentImplementation implements Serializable {

    public static SAPWSVURPaymentImplementation create() {
        return new SAPWSVURPaymentImplementation();
    }

    private SAPWSVURPaymentImplementation() {
    }

    /**
     * Cargue de definicion servicio web getDetail de la liquidacion.
     *
     * @param wsdlUrl
     * @return
     * @throws Exception
     */
    private ZwsvurPayment loadServiceDefinition() throws Exception {
        // Constante con URL del servicio web
        String urlWS = ConstantesUtils.cargarConstante("WS-SAP-GET-DETAIL");
        String userWS = ConstantesUtils.cargarConstante("WS-SAP-USER");
        String claveWS = ConstantesUtils.cargarConstante("WS-SAP-PASSWORD");

        try {
            if (urlWS == null || urlWS.equals("")) {
                throw new Exception("Error generando Definicion de servicio zwsvur_payment No se ha encontrado URL (WS-SAP-GET-DETAIL) en sistema de constantes");
            } else {
                QName qname = new QName("urn:sap-com:document:sap:rfc:functions", "zwsvur_payment");
                ZwsvurPayment_Service serviceD = new ZwsvurPayment_Service(null, qname);
                ZwsvurPayment port = serviceD.getZwsvurPayment();
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
                bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, userWS);
                bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, claveWS);

                bindingProvider.getRequestContext().put("com.sun.xml.ws.request.timeout", 60000);
                port.zpscdfmVURPAYMENT(claveWS, claveWS, claveWS, claveWS, userWS, null, null);
                return port;
            }
        } catch (Exception e) {
            throw new Exception("Error generando Definicion de servicio zwsvur_getdetail web en URL " + urlWS + " causado por " + e);
        }
    }
    /**
     * Metodo de invocacion Pagos SAP para VUR valle del cauca
     * @param iCUENTABCO
     * @param iFECHARECAUDO
     * @param iHORARECAUDO
     * @param iNROLIQ
     * @param iVALOR
     * @param eMESSAGE
     * @param eRETURN
     * @throws Exception 
     */
    public void vurPayment(String iCUENTABCO,String iFECHARECAUDO,String iHORARECAUDO,String iNROLIQ,String iVALOR,Holder<String> eMESSAGE,Holder<Integer> eRETURN) throws Exception{
        ZwsvurPayment portService=loadServiceDefinition();
        portService.zpscdfmVURPAYMENT(iCUENTABCO, iFECHARECAUDO, iHORARECAUDO, iNROLIQ, iVALOR, eMESSAGE, eRETURN);
    }

}
