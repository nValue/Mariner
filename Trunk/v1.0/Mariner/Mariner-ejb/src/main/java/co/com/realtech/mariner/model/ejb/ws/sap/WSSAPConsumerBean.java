package co.com.realtech.mariner.model.ejb.ws.sap;

import co.com.realtech.mariner.model.ejb.ws.sap.converters.SAPGetDetailConverter;
import co.com.realtech.mariner.model.ejb.ws.sap.implementations.SAPWSGetDetailsImplementation;
import co.com.realtech.mariner.model.ejb.ws.sap.implementations.SAPWSVURPaymentImplementation;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDDETACTOT;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
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
