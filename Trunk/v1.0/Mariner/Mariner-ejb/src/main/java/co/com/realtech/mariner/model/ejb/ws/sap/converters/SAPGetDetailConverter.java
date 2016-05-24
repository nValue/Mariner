package co.com.realtech.mariner.model.ejb.ws.sap.converters;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method.DetalleLiquidacionItem;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDLIQDETALLE;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDTTVURDETAIL;
import java.io.Serializable;
import java.util.List;
import javax.xml.ws.Holder;

/**
 * Utilidad de conversion de Holders SAP a objeto DetalleLiquidacion
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPGetDetailConverter implements Serializable {

    /**
     * Conversion de objeto.
     *
     * @param responseHeader
     * @param responseDetail
     * @return
     * @throws Exception
     */
    public static DetalleLiquidacion convertHolders(Holder<ZPSCDPRNCAB> responseHeader, Holder<ZPSCDTTVURDETAIL> responseDetail) throws Exception {
        DetalleLiquidacion detalle = new DetalleLiquidacion();
        try {
            for(ZPSCDLIQDETALLE loopDetalle:responseDetail.value.getItem()){
                DetalleLiquidacionItem item=new DetalleLiquidacionItem();
                item.setTipoRenta(loopDetalle.getRENTA());
                item.setNumeroLiquidacion(loopDetalle.getNROLIQ());
                item.setConsecutivo(loopDetalle.getNROCONS());
                item.setCodigoActo(loopDetalle.getCODIGO());
                item.setCantidadLiquidaciones(loopDetalle.getCANTIDADLIQ());
                item.setCantidad(loopDetalle.getCANTIDAD());
                item.setBaseGrabable(loopDetalle.getBASEGRAV());
                item.setBaseGrabableLiquidacion(loopDetalle.getBASEGRAVLIQ());
                
                detalle.getItemsDetalle().add(item);
            }
            
        } catch (Exception e) {
            throw e;
        }
        return detalle;
    }

}
