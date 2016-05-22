package co.com.realtech.mariner.model.ejb.ws.sap;

import co.com.realtech.mariner.model.ejb.ws.sap.implementations.SAPWSGetDetailsImplementation;
import co.com.realtech.mariner.model.ejb.ws.sap.implementations.SAPWSVURPaymentImplementation;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDTTVURDETAIL;
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
     * Cargar detalle de la liquidacion.
     *
     * @param liquidacion
     * @param responseHeader
     * @param responseDetail
     * @throws Exception
     */
    @Override
    public void getDetail(String liquidacion, Holder<ZPSCDPRNCAB> responseHeader, Holder<ZPSCDTTVURDETAIL> responseDetail) throws Exception {
        SAPWSGetDetailsImplementation wsSapIm = SAPWSGetDetailsImplementation.create();
        wsSapIm.getDetail(liquidacion, responseHeader, responseDetail);
    }

    /**
     * Metodo de invocacion Pagos SAP para VUR valle del cauca
     *
     * @param iCUENTABCO
     * @param iFECHARECAUDO
     * @param iHORARECAUDO
     * @param iNROLIQ
     * @param iVALOR
     * @param eMESSAGE
     * @param eRETURN
     * @throws Exception
     */
    @Override
    public void vurPayment(String iCUENTABCO, String iFECHARECAUDO, String iHORARECAUDO, String iNROLIQ, String iVALOR, Holder<String> eMESSAGE, Holder<Integer> eRETURN) throws Exception {
        SAPWSVURPaymentImplementation wsPayment = SAPWSVURPaymentImplementation.create();
        wsPayment.vurPayment(iCUENTABCO, iFECHARECAUDO, iHORARECAUDO, iNROLIQ, iVALOR, eMESSAGE, eRETURN);
    }
}
