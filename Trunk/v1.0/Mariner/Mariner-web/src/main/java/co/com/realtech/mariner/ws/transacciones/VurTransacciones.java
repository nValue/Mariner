package co.com.realtech.mariner.ws.transacciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionConfirmacion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionLogSDO;
import java.math.BigDecimal;
import java.util.Date;
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
                    transaccion.setLog(new VURTransaccionLogSDO("OK", "Transaccion con codigo " + codigoTransaccion + " encontrada en la plataforma VUR Valle del Cauca"));
                    transaccion.setApellidos(transaccionBD.getUsuId().getPerId().getPerApellidos());
                    transaccion.setNombres(transaccionBD.getUsuId().getPerId().getPerNombres());
                    transaccion.setTipoDocumento(transaccionBD.getUsuId().getPerId().getTdcId().getTdcSigla());
                    transaccion.setNumeroDocumento(transaccionBD.getUsuId().getPerId().getPerDocumento());
                    transaccion.setCodigoTransaccion(codigoTransaccion);
                    transaccion.setCus(transaccionBD.getTraCus());
                    transaccion.setDescripcionTransaccion("Pago de Derechos de Registro Valle del Cauca");
                    transaccion.setReferencia(transaccionBD.getTraReferencia());
                    transaccion.setTelefono(transaccionBD.getUsuId().getPerId().getPerTelefono());
                    long valorTransaccion = transaccionBD.getTraValor().longValue();
                    transaccion.setValorTransaccion(valorTransaccion);
                    transaccion.setCodigoServicioACH(ConstantesUtils.cargarConstante("WS-PASARELA-ACH"));
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

    /**
     * Retorna la informacion de la transaccion por referencia de pago.
     *
     * @param referencia
     * @return
     */
    @WebMethod(operationName = "consultarTransaccionPorReferencia")
    public VURTransaccion consultarTransaccionPorReferencia(@WebParam(name = "referencia") String referencia) {
        VURTransaccion transaccion = new VURTransaccion();
        try {
            MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "traReferencia", referencia);

            if (transaccionBD != null) {
                if (transaccionBD.getTraEstado().equalsIgnoreCase("T")) {
                    transaccion.setEstado("OK");
                    transaccion.setLog(new VURTransaccionLogSDO("OK", "Transaccion con Referencia " + referencia + " encontrada en la plataforma VUR Valle del Cauca"));
                    transaccion.setApellidos(transaccionBD.getUsuId().getPerId().getPerApellidos());
                    transaccion.setNombres(transaccionBD.getUsuId().getPerId().getPerNombres());
                    transaccion.setTipoDocumento(transaccionBD.getUsuId().getPerId().getTdcId().getTdcSigla());
                    transaccion.setNumeroDocumento(transaccionBD.getUsuId().getPerId().getPerDocumento());
                    transaccion.setCodigoTransaccion(transaccionBD.getTraId().longValue());
                    transaccion.setCus(transaccionBD.getTraCus());
                    transaccion.setDescripcionTransaccion("Pago de Derechos de Registro Valle del Cauca");
                    transaccion.setReferencia(transaccionBD.getTraReferencia());
                    transaccion.setTelefono(transaccionBD.getUsuId().getPerId().getPerTelefono());
                    long valorTransaccion = transaccionBD.getTraValor().longValue();
                    transaccion.setValorTransaccion(valorTransaccion);
                    transaccion.setCodigoServicioACH(ConstantesUtils.cargarConstante("WS-PASARELA-ACH"));
                } else {
                    transaccion.setEstado("ERROR");
                    transaccion.setLog(new VURTransaccionLogSDO("ERROR", "La transaccion con referencia " + referencia + " no se encuentra en un estado pendiente por pago"));
                }
            } else {
                transaccion.setEstado("ERROR");
                transaccion.setLog(new VURTransaccionLogSDO("ERROR", "No se ha encontrado ninguna transaccion con referencia: " + referencia));
            }
        } catch (Exception e) {
            transaccion.setEstado("ERROR");
            transaccion.setLog(new VURTransaccionLogSDO("ERROR", "Se ha produccido un error interno al obtener la informacion de la transaccion por referencia " + referencia, "Error interno " + e.getMessage()));
        }
        return transaccion;
    }

    /**
     * Metodo de confirmacion de transaccion proveniente de la pasarela de
     * pagos.
     *
     * @param codigoTransaccion
     * @param claveConfirmacion
     * @return
     */
    @WebMethod(operationName = "confirmarTransaccion")
    public VURTransaccionConfirmacion confirmarTransaccion(@WebParam(name = "codigoTransaccion") Long codigoTransaccion, @WebParam(name = "claveConfirmacion") String claveConfirmacion) {
        VURTransaccionConfirmacion confirmacion = new VURTransaccionConfirmacion();
        try {
            String claveConfConstante = ConstantesUtils.cargarConstante("WS-PASARELA-CODIGO-CONFIRMACION");

            if (claveConfConstante.equals(claveConfirmacion)) {
                MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "traId", new BigDecimal(codigoTransaccion));

                if (transaccionBD != null) {
                    if (transaccionBD.getTraEstado().equals("T")) {
                        transaccionBD.setTraFechaFinalizacion(new Date());
                        transaccionBD.setTraEstado("A");
                        genericDAOBean.merge(transaccionBD);
                        confirmacion.setEstado("OK");
                        confirmacion.setLog(new VURTransaccionLogSDO("OK", "Transaccion confirmada correctamente en la plataforma", "Transaccion confirmada correctamente en la plataforma"));
                    } else {
                        confirmacion.setEstado("ERROR");
                        confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "La transaccion con codigo " + codigoTransaccion + " no se encuentra en estado por confirmar en la plataforma"));
                    }
                } else {
                    confirmacion.setEstado("ERROR");
                    confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "No se ha encontrado ninguna transaccion con codigo de transaccion: " + codigoTransaccion));
                }
            } else {
                confirmacion.setEstado("ERROR");
                confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Clave de confirmacion incorrecta", "La Clave de confirmacion ingresada es incorrecta, contacte al administrador de la plataforma."));
            }
        } catch (Exception e) {
            confirmacion.setEstado("ERROR");
            confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Se ha produccido un error interno confirmando la transaccion", "Error intentando confirmar la transaccion por codigo, error interno " + e));
        }
        return confirmacion;
    }
}
