package co.com.realtech.mariner.util.marshall.liquidacion;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacionItem;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesActosSap;
import co.com.realtech.mariner.model.entity.MarRadicacionesDetallesSap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de asignacion de propiedades extraidas de SAP a la Entidad de
 * base de datos
 *
 * @author Andres Rivera
 * @version 1.0
 * @since
 */
public class LiquidacionMarshaller implements Serializable {

    private MarRadicacionesDetallesSap sap;
    private List<MarRadicacionesActosSap> actos;

    protected LiquidacionMarshaller() {
    }

    /**
     * Asignacion de propiedades uno a uno entre Entidad de base de datos y
     * Objecto SDO servicios web.
     *
     * @param sapInput
     * @param radicacion
     * @param detalle
     */
    public void fillLiquidacionValues(MarRadicacionesDetallesSap sapInput, MarRadicaciones radicacion, DetalleLiquidacion detalle) {
        sap = sapInput;
        sap.setRdeLiqNumero(detalle.getLiqNumero());
        sap.setRdeOtoNum(detalle.getOtoNum());
        sap.setRdeBenNum(detalle.getBenNum());
        sap.setRdeOtoNombre(detalle.getOtoNombre());
        sap.setRdeBenNombre(detalle.getBenNombre());
        sap.setRdeOtoCc(detalle.getOtoCc());
        sap.setRdeBenCc(detalle.getBenCc());
        sap.setRdeOtoNit(detalle.getOtoNit());
        sap.setRdeBenNit(detalle.getBenNit());
        sap.setRdeMunicipio(detalle.getMunicipio());
        sap.setRdeClavePer(detalle.getClavePer());
        sap.setRdeMunNombre(detalle.getMunNombre());
        sap.setRdeFechaLiquidacion(detalle.getFechaLiquidacion());
        sap.setRdeUname(detalle.getUname());
        sap.setRdeFechaLdp(detalle.getFechaLdp());
        sap.setRdeTexto(detalle.getTexto());
        sap.setRdeClase(detalle.getClase());
        sap.setRdeDocumento(detalle.getDocumento());
        sap.setRdeTotal(detalle.getTotal());
        sap.setRdeDescladoc(detalle.getDescladoc());
        sap.setRdeDescoridoc(detalle.getDescoridoc());
        sap.setRdeFechaDoc(detalle.getFechaDoc());
        sap.setRdeMatricula(detalle.getMatricula());
        sap.setRdeTintMora(detalle.getTintMora());
        sap.setRdeNotaria(detalle.getNotaria());
        sap.setRdeDescInt(detalle.getDescInt());
        sap.setRdeDescImp(detalle.getDescImp());
        sap.setRdeTotalDesc(detalle.getTotalDesc());
        sap.setRdeFechaLimite(detalle.getFechaLimite());
        sap.setRdeNorodrad(detalle.getNorodrad());

        // Valores del objeto radicacion
        radicacion.setRadValorLiq(detalle.getTotal());
        radicacion.setRadLiquidacion(detalle.getLiqNumero());

        // Maping de los Actos
        actos = new ArrayList<>();
        for (DetalleLiquidacionItem det : detalle.getItemsDetalle()) {
            MarRadicacionesActosSap detSap = new MarRadicacionesActosSap();
            detSap.setRdsCodigoActo(det.getCodigoActo());
            detSap.setRdsNomnbreAsr(det.getNombreASR());
            detSap.setRdsTipoPer(det.getTipoPer());
            detSap.setRdsBaseIpp(det.getBaseIpp());
            detSap.setRdsBaseEst(det.getBaseEst());
            detSap.setRdsTarifaIpp(det.getTarifaIpp());
            detSap.setRdsTarifaCio(det.getTarifaCio());
            detSap.setRdsImpIpp(det.getImpuestoIpp());
            detSap.setRdsImpCio(det.getImpuestoCio());
            detSap.setRdsBaseEst(det.getBaseEst());
            detSap.setRdsBaseEst1(det.getBaseEst1());
            detSap.setRdsBaseEst2(det.getBaseEst2());
            detSap.setRdsValorSinCuantia(det.getValorSinCuantia());
            detSap.setRdsValServInfo(det.getValorServInfo());
            detSap.setRdsValorIntereses(det.getValorIntereses());
            detSap.setRdsFechaDocumento(det.getFechaDocumento());
            detSap.setRdsFechaVencimiento(det.getFechaVencimiento());
            detSap.setRdsFechaPago(det.getFechaPago());
            detSap.setRdsBaseEst3(det.getBaseEst3());
            detSap.setRdsVrEstadoProd(det.getValorEstadoProd());
            detSap.setRdsBaseEst4(det.getBaseEst4());
            detSap.setRdsBaseEst5(det.getBaseEst5());
            actos.add(detSap);
        }
    }

    public MarRadicacionesDetallesSap getSap() {
        return sap;
    }

    public void setSap(MarRadicacionesDetallesSap sap) {
        this.sap = sap;
    }

    public List<MarRadicacionesActosSap> getActos() {
        return actos;
    }

    public void setActos(List<MarRadicacionesActosSap> actos) {
        this.actos = actos;
    }

}
