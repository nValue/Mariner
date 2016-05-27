package co.com.realtech.mariner.model.ejb.ws.sap;

import co.com.realtech.mariner.model.ejb.ws.sap.converters.SAPGetDetailConverter;
import co.com.realtech.mariner.model.ejb.ws.sap.implementations.SAPWSGetDetailsImplementation;
import co.com.realtech.mariner.model.ejb.ws.sap.implementations.SAPWSListLiquidacionesImplementation;
import co.com.realtech.mariner.model.ejb.ws.sap.implementations.SAPWSVURPaymentImplementation;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDDETACTOT;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method.ZPSCDTTVURLIST;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.payment.DetallePago;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.xml.ws.Holder;

/**
 * EJB encargado del consumo de servicios de la capa de SAP.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8.91
 */
@Stateless
public class WSSAPConsumerBean implements WSSAPConsumerBeanLocal {

    /**
     * Consulta y mapeo de datos liquidacion SAP.
     *
     * @param liquidacion
     * @return
     * @throws Exception
     */
    @Override
    public DetalleLiquidacion getDetail(String liquidacion) throws Exception {
        Holder<ZPSCDPRNCAB> responseHeader = new Holder<>();
        Holder<ZPSCDDETACTOT> responseDetail = new Holder<>();
        SAPWSGetDetailsImplementation wsSapIm = SAPWSGetDetailsImplementation.create();
        wsSapIm.getDetail(liquidacion, responseHeader, responseDetail);
        return SAPGetDetailConverter.convertHolders(responseHeader, responseDetail);
    }

    /**
     * Proceso para aplicar proceso de pago en SAP.
     *
     * @param codigoLiquidacion
     * @param fechaRecaudo
     * @param horaRecaudo
     * @param valor
     * @return
     * @throws Exception
     */
    @Override
    public DetallePago aplicarPagoSAP(String codigoLiquidacion, String fechaRecaudo, String horaRecaudo, BigDecimal valor) throws Exception {
        DetallePago detallePago = new DetallePago();
        try {
            detallePago = new DetallePago();
            String codigoBanco = ConstantesUtils.cargarConstante("VUR-CODIGO-BANCO-SAP");
            Holder<String> eMESSAGE = new Holder<>();
            Holder<Integer> eRETURN = new Holder<>();
            SAPWSVURPaymentImplementation wsPayment = SAPWSVURPaymentImplementation.create();
            wsPayment.vurPayment(codigoBanco, fechaRecaudo, horaRecaudo, codigoLiquidacion, valor.toString(), eMESSAGE, eRETURN);

            // Obtencion y mapeo de resultados.
            detallePago.setNumeroLiquidacion(codigoLiquidacion);
            detallePago.setFechaRecaudo(fechaRecaudo);
            detallePago.setHoraRecaudo(horaRecaudo);
            detallePago.setNumeroCuenta(codigoBanco);
            detallePago.setValor(valor);
            detallePago.setEstadoSalida(eRETURN.value.toString());
            detallePago.setMensajeSalida("SAP Message: "+eMESSAGE.value);
        } catch (Exception e) {
            throw e;
        }
        return detallePago;
    }

    /**
     * Obtener listado de liquidaciones pendientes.
     *
     * @param fecha
     * @return
     * @throws Exception
     */
    @Override
    public List<DetalleLiquidacion> getListLiquidaciones(String fecha) throws Exception {
        try {
            SAPWSListLiquidacionesImplementation impWS = SAPWSListLiquidacionesImplementation.create();
            ZPSCDTTVURLIST datos = impWS.zpscdfmVURGETLIST(fecha);
            return SAPGetDetailConverter.convertHoldersListas(datos);
        } catch (Exception e) {
            throw e;
        }
    }
}
