package co.com.realtech.mariner.ws.transacciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionLogSDO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Servicio web encargado de exponer las transacciones y funciones para la
 * pasarela de pagos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.9.91
 */
@WebService(serviceName = "VurTransacciones")
public class VurTransacciones {

    @EJB(beanName = "GenericDAOBean")
    protected GenericDAOBeanLocal genericDAOBean;

    /**
     * COnsulta transaccion para derechos de registro en la plataforma.
     *
     * @param codigoTransaccion
     * @return
     */
    @WebMethod(operationName = "consultarTransaccion")
    public VURTransaccion consultarTransaccion(@WebParam(name = "codigoTransaccion") Long codigoTransaccion) {
        VURTransaccion transaccion = new VURTransaccion();
        try {
            MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "traId", new BigDecimal(codigoTransaccion));

            if (transaccionBD != null) {
                if (transaccionBD.getTraEstado().equalsIgnoreCase("T")) {
                    transaccion.setEstado("OK");
                    transaccion.setLog(new VURTransaccionLogSDO("OK", "Transaccion con codigo " + codigoTransaccion + " encontrada en la plataforma CTLApplication"));
                    transaccion.setApellidos(transaccionBD.getUsuId().getPerId().getPerApellidos());
                    transaccion.setNombres(transaccionBD.getUsuId().getPerId().getPerNombres());
                    transaccion.setTipoDocumento(transaccionBD.getUsuId().getPerId().getTdcId().getTdcSigla());
                    transaccion.setNumeroDocumento(transaccionBD.getUsuId().getPerId().getPerDocumento());
                    transaccion.setCodigoTransaccion(codigoTransaccion);
                    transaccion.setCus(transaccionBD.getTraCus());
                    transaccion.setDescripcionTransaccion("Pago de Derechos de Registro Valle del Cauca");
                    transaccion.setReferencia(codigoTransaccion.toString());
                    transaccion.setTelefono(transaccionBD.getUsuId().getPerId().getPerTelefono());
                    long valorTransaccion = transaccionBD.getTraValor().longValue();
                    transaccion.setValorTransaccion(valorTransaccion);

                    // Detectamos el codigo de servicio ACH para el primer certificado.
                    try {
                        transaccion.setCodigoServicioACH(genericDAOBean.callGenericFunction("PKG_VUR_NEGOCIO.FL_CARGAR_CODIGO_ACH", codigoTransaccion).toString());
                    } catch (Exception e) {
                        transaccion.setCodigoServicioACH("");
                    }
                } else {
                    transaccion.setEstado("ERROR");
                    transaccion.setLog(new VURTransaccionLogSDO("ERROR", "La transaccion con codigo " + codigoTransaccion + " no se encuentra en un estado pendiente por pago"));
                }
            } else {
                transaccion.setEstado("ERROR");
                transaccion.setLog(new VURTransaccionLogSDO("ERROR", "No se ha encontrado ninguna transaccion con codigo: " + codigoTransaccion));
            }
        } catch (Exception e) {
            transaccion.setEstado("ERROR");
            transaccion.setLog(new VURTransaccionLogSDO("ERROR", "Se ha produccido un error interno al obtener la informacion de la transaccion " + codigoTransaccion, "Error interno " + e.getMessage()));
        }
        return transaccion;
    }
}
