package co.com.realtech.mariner.model.ejb.ws.sap;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method.DetalleLiquidacion;
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

    public DetalleLiquidacion getDetail(String liquidacion) throws Exception;
    
    public void vurPayment(String iCUENTABCO,String iFECHARECAUDO,String iHORARECAUDO,String iNROLIQ,String iVALOR,Holder<String> eMESSAGE,Holder<Integer> eRETURN) throws Exception;
    
}
