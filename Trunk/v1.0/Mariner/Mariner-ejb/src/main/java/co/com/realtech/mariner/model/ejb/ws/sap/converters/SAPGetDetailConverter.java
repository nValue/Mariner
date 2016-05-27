package co.com.realtech.mariner.model.ejb.ws.sap.converters;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacionItem;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDDETACTO;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDDETACTOT;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method.ZPSCDTTVURLIST;
import java.io.Serializable;
import java.util.ArrayList;
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
            
            detalle.setDescladoc(SAPEquivalenceUtils.findClasesDocumentos(responseHeader.value.getVDESCLADOC()));
            detalle.setDescoridoc(SAPEquivalenceUtils.findOrigenDocumentos(responseHeader.value.getVDESORIDOC()));
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
    /**
     * Convertor de Holder listado de liquidaciones a estructura list detalle liquidacion.
     * @param datos
     * @return
     * @throws Exception 
     */
    public static List<DetalleLiquidacion> convertHoldersListas(ZPSCDTTVURLIST datos) throws Exception {
        List<DetalleLiquidacion> detalles = new ArrayList<>();
        try {
            // Loop extratendo datos de listado de liquidaciones.
            datos.getItem().stream().map((co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_list_method.ZPSCDPRNCAB detLoop) -> {
                DetalleLiquidacion detalle = new DetalleLiquidacion();
                detalle.setLiqNumero(detLoop.getVNUMERO());
                detalle.setOtoNum(detLoop.getVOTONUM());
                detalle.setBenNum(detLoop.getVBENNUM());
                detalle.setOtoNombre(detLoop.getVOTONOMBRE());
                detalle.setBenNombre(detLoop.getVBENNOMBRE());
                detalle.setOtoCc(detLoop.getVOTOCC());
                detalle.setBenCc(detLoop.getVBENCC());
                detalle.setOtoNit(detLoop.getVOTONIT());
                detalle.setBenNit(detLoop.getVBENNIT());
                detalle.setMunicipio(detLoop.getVMUNICIPIO());
                detalle.setClavePer(detLoop.getVCLAVEPER());
                detalle.setMunNombre(detLoop.getVMUNNOMBRE());
                detalle.setFechaLiquidacion(detLoop.getVFECLIQ());
                detalle.setUname(detLoop.getUNAME());
                detalle.setFechaLdp(detLoop.getVFECLDP());
                detalle.setTexto(detLoop.getVTEXTO());
                detalle.setClase(detLoop.getVCLASE());
                detalle.setDocumento(detLoop.getVDOCUMENTO());
                detalle.setTotal(detLoop.getVTOTAL());
                detalle.setDescladoc(SAPEquivalenceUtils.findClasesDocumentos(detLoop.getVDESCLADOC()));
                detalle.setDescoridoc(SAPEquivalenceUtils.findOrigenDocumentos(detLoop.getVDESORIDOC()));
                detalle.setFechaDoc(detLoop.getVFECDOC());
                detalle.setMatricula(detLoop.getVMATINM());
                detalle.setTintMora(detLoop.getVTINTMORA());
                detalle.setNotaria(detLoop.getVNOTARIA());
                detalle.setDescInt(detLoop.getVDSCTOINT());
                detalle.setDescImp(detLoop.getVDSCTOIMP());
                detalle.setTotalDesc(detLoop.getVTOTDSCTO());
                detalle.setFechaLimite(detLoop.getFECHALIMITE());
                detalle.setNorodrad(detLoop.getNRORAD());
                return detalle;
            }).forEach((detalle) -> {
                detalles.add(detalle);
            });
        } catch (Exception e) {
            throw e;
        }
        return detalles;
    }

}
