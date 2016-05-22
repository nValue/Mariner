package co.com.realtech.mariner.model.ejb.ws.sap;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDTTVURDETAIL;
import javax.ejb.Local;
import javax.xml.ws.Holder;

/**
 * (interface) EJB encargado del consumo de servicios de la capa de SAP.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8.91
 */
@Local
public interface WSSAPConsumerBeanLocal {

    public void getDetail(String liquidacion, Holder<ZPSCDPRNCAB> responseHeader, Holder<ZPSCDTTVURDETAIL> responseDetail) throws Exception;
    
    public void vurPayment(String iCUENTABCO,String iFECHARECAUDO,String iHORARECAUDO,String iNROLIQ,String iVALOR,Holder<String> eMESSAGE,Holder<Integer> eRETURN) throws Exception;
    
}
