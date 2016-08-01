package co.com.realtech.mariner.model.ejb.ws.sap;

import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.cambio_estado.DetalleCambioEstado;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.payment.DetallePago;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

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

    public DetallePago aplicarPagoSAP(String codigoLiquidacion, String fechaRecaudo, String fechaValor, String horaRecaudo, BigDecimal valor) throws Exception;

    public List<DetalleLiquidacion> getListLiquidaciones(String fecha) throws Exception;

    public DetalleCambioEstado actualizarEstadoLiquidacion(String liquidacion, String estado) throws Exception;

}
