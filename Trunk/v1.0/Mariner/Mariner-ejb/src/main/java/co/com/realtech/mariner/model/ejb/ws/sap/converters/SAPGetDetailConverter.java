package co.com.realtech.mariner.model.ejb.ws.sap.converters;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method.DetalleLiquidacionItem;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDDETACTO;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDDETACTOT;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import java.io.Serializable;
import java.math.BigDecimal;
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
    public static DetalleLiquidacion convertHolders(Holder<ZPSCDPRNCAB> responseHeader, Holder<ZPSCDDETACTOT> responseDetail) throws Exception {
        DetalleLiquidacion detalle = new DetalleLiquidacion();
        try {
            // header
            detalle.setLiqNumero(responseHeader.value.getVNUMERO());
            detalle.setOtoNum(responseHeader.value.getVOTONUM());
            detalle.setBenNum(responseHeader.value.getVBENNUM());
            detalle.setOtoNombre(responseHeader.value.getVOTONOMBRE());
            detalle.setBenNombre(responseHeader.value.getVBENNOMBRE());
            detalle.setOtoCc(responseHeader.value.getVOTOCC());
            detalle.setBenCc(responseHeader.value.getVBENCC());
            detalle.setOtoNit(responseHeader.value.getVOTONIT());
            detalle.setBenNit(responseHeader.value.getVBENNIT());
            detalle.setMunicipio(responseHeader.value.getVMUNICIPIO());
            detalle.setClavePer(responseHeader.value.getVCLAVEPER());
            detalle.setMunNombre(responseHeader.value.getVMUNNOMBRE());
            detalle.setFechaLiquidacion(responseHeader.value.getVFECLIQ());
            detalle.setUname(responseHeader.value.getUNAME());
            detalle.setFechaLdp(responseHeader.value.getVFECLDP());
            detalle.setTexto(responseHeader.value.getVTEXTO());
            detalle.setClase(responseHeader.value.getVCLASE());
            detalle.setDocumento(responseHeader.value.getVDOCUMENTO());
            detalle.setTotal(responseHeader.value.getVTOTAL());
            detalle.setDescladoc(responseHeader.value.getVDESCLADOC());
            detalle.setDescoridoc(responseHeader.value.getVDESORIDOC());
            detalle.setFechaDoc(responseHeader.value.getVFECDOC());
            detalle.setMatricula(responseHeader.value.getVMATINM());
            detalle.setTintMora(responseHeader.value.getVTINTMORA());
            detalle.setNotaria(responseHeader.value.getVNOTARIA());
            detalle.setDescInt(responseHeader.value.getVDSCTOINT());
            detalle.setDescImp(responseHeader.value.getVDSCTOIMP());
            detalle.setTotalDesc(responseHeader.value.getVTOTDSCTO());
            detalle.setFechaLimite(responseHeader.value.getFECHALIMITE());
            detalle.setNorodrad(responseHeader.value.getNRORAD());

            // Details
            for (ZPSCDDETACTO loopDetalle : responseDetail.value.getItem()) {
                DetalleLiquidacionItem item = new DetalleLiquidacionItem();
                item.setCodigoActo(loopDetalle.getCODACTO());
                item.setNombreASR(loopDetalle.getNOMBREASR());
                item.setTipoPer(loopDetalle.getTIPPER());
                item.setBaseIpp(loopDetalle.getBASEIIPP());
                item.setBaseEst(loopDetalle.getBASEEST());
                item.setTarifaIpp(loopDetalle.getTARIFAIIPP());
                item.setTarifaCio(loopDetalle.getTARIFACIO());
                item.setImpuestoIpp(loopDetalle.getIMPTOIIPP());
                item.setImpuestoCio(loopDetalle.getIMPTOCIO());
                item.setBaseEst(loopDetalle.getBASEEST());
                item.setBaseEst1(loopDetalle.getBASEEST1());
                item.setBaseEst2(loopDetalle.getBASEEST2());
                item.setValorSinCuantia(loopDetalle.getVRSINCUANTIA());
                item.setValorServInfo(loopDetalle.getVVALSERINF());
                item.setValorIntereses(loopDetalle.getVRINTERESES());
                item.setFechaDocumento(loopDetalle.getVFECDOC());
                item.setFechaVencimiento(loopDetalle.getVFECVEN());
                item.setFechaPago(loopDetalle.getVFECVEN());
                item.setBaseEst3(loopDetalle.getBASEEST3());
                item.setValorEstadoProd(loopDetalle.getVRESTPROD());
                item.setBaseEst4(loopDetalle.getBASEEST4());
                item.setBaseEst5(loopDetalle.getBASEEST5());
                detalle.getItemsDetalle().add(item);
            }
        } catch (Exception e) {
            throw e;
        }
        return detalle;
    }

}
