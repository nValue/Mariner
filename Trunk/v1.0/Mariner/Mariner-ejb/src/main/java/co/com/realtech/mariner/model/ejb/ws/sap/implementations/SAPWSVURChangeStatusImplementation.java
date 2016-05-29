package co.com.realtech.mariner.model.ejb.ws.sap.implementations;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.cambio_estado.DetalleCambioEstado;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_payment.ZsnPayment;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_payment.ZwsvurPayment;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_updstatus.ZWSVURUPDSTATUS;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.vur_updstatus.ZsnUpdstatus;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
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
public class SAPWSVURChangeStatusImplementation implements Serializable {

    public static SAPWSVURChangeStatusImplementation create() {
        return new SAPWSVURChangeStatusImplementation();
    }

    private SAPWSVURChangeStatusImplementation() {
    }

    /**
     * Cargue de definicion servicio web ZWSVURUPDSTATUS de la liquidacion.
     *
     * @param wsdlUrl
     * @return
     * @throws Exception
     */
    private ZWSVURUPDSTATUS loadServiceDefinition() throws Exception {
        // Constante con URL del servicio web
        String urlWS = ConstantesUtils.cargarConstante("WS-SAP-CAMBIO-ESTADO");
        String userWS = ConstantesUtils.cargarConstante("WS-SAP-USER");
        String claveWS = ConstantesUtils.cargarConstante("WS-SAP-PASSWORD");

        try {
            if (urlWS == null || urlWS.equals("")) {
                throw new Exception("Error generando Definicion de servicio zsn_updstatus No se ha encontrado URL (WS-SAP-CAMBIO-ESTADO) en sistema de constantes");
            } else {
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userWS, claveWS.toCharArray());
                    }
                });

                QName qname = new QName("urn:sap-com:document:sap:rfc:functions", "zsn_updstatus");
                ZsnUpdstatus serviceD = new ZsnUpdstatus(null, qname);
                ZWSVURUPDSTATUS port = serviceD.getZbnUpdstatus();
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlWS);
                bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, userWS);
                bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, claveWS);
                bindingProvider.getRequestContext().put("com.sun.xml.ws.request.timeout", 60000);
                return port;
            }
        } catch (Exception e) {
            throw new Exception("Error generando Definicion de servicio zbn_updstatus web en URL " + urlWS + " causado por " + e);
        }
    }

    /**
     * Ejecicion metodo para cambios de estadod de la liquidacion.
     *
     * @param liquidacion
     * @param estado
     * @param eMESSAGE
     * @param eRETURN
     * @throws Exception
     */
    public void cambiarEstadoLiquidacion(String liquidacion, String estado, Holder<String> eMESSAGE, Holder<Integer> eRETURN) throws Exception {
        ZWSVURUPDSTATUS portService = loadServiceDefinition();
        portService.zpscdfmVURUPDSTATUS(estado, liquidacion, eMESSAGE, eRETURN);
    }

}
