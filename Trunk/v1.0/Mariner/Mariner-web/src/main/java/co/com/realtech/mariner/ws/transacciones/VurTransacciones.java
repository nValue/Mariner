package co.com.realtech.mariner.ws.transacciones;

import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.pasarela.PSEWSConsumerBeanLocal;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.date.DateUtils;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionConfirmacion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionLogSDO;
import co.com.realtech.mariner.ws.transacciones.core.TransactionCore;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    @EJB(beanName = "PSEWSConsumerBean")
    protected PSEWSConsumerBeanLocal pseWSConsumerBean;

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
                    // verificar la fecha limite de pago es hoy y son mas de las 4 PM Cambio solicitado por el banco
                    if (DateUtils.permitePagoHorarioAdicional(transaccionBD.getRadId().getMarRadicacionesDetallesSap().getRdeFechaLdp())) {
                        transaccion.setEstado("OK");
                        transaccion.setLog(new VURTransaccionLogSDO("OK", "Transaccion con codigo " + codigoTransaccion + " encontrada en la plataforma VUR Valle del Cauca"));
                        transaccion.setApellidos(transaccionBD.getTraApellidos());
                        transaccion.setNombres(transaccionBD.getTraNombres());
                        transaccion.setTipoDocumento(transaccionBD.getTdcId().getTdcSigla());
                        transaccion.setNumeroDocumento(transaccionBD.getTraDocumento());
                        transaccion.setTelefono(transaccionBD.getTraTelefono());
                        transaccion.setCodigoTransaccion(codigoTransaccion);
                        transaccion.setCus(transaccionBD.getTraCus());
                        transaccion.setDescripcionTransaccion("Pago de Impuestos de Registro Valle del Cauca");
                        transaccion.setReferencia(transaccionBD.getTraReferencia());
                        transaccion.setTipoMedioPago(transaccionBD.getTraTipoPago());
                        transaccion.setFechaTransaccion(transaccionBD.getTraFechaInicio());
                        transaccion.setFechaVencimiento(extrarDateLimitFromTransaction(transaccionBD));
                        long valorTransaccion;
                        try {
                            valorTransaccion = transaccionBD.getTraValor().longValue();
                        } catch (Exception e) {
                            valorTransaccion = 0;
                        }
                        transaccion.setValorTransaccion(valorTransaccion);
                        transaccion.setCodigoServicioACH(ConstantesUtils.cargarConstante("WS-PASARELA-ACH"));
                    } else {
                        transaccion.setEstado("ERROR");
                        transaccion.setLog(new VURTransaccionLogSDO("ERROR", "La transaccion se vencio hoy y solo podia ser pagada antes de horario adicional."));
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
            e.printStackTrace();
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
            String codigoLiquidacion = BusinessStringUtils.convertNumeroLiquidacion(referencia);
            MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "traReferencia", codigoLiquidacion);

            if (transaccionBD != null) {
                if (transaccionBD.getTraEstado().equalsIgnoreCase("T")) {
                    // verificar la fecha limite de pago es hoy y son mas de las 4 PM Cambio solicitado por el banco
                    if (DateUtils.permitePagoHorarioAdicional(transaccionBD.getRadId().getMarRadicacionesDetallesSap().getRdeFechaLdp())) {
                        transaccion.setEstado("OK");
                        transaccion.setLog(new VURTransaccionLogSDO("OK", "Transaccion con Referencia " + codigoLiquidacion + " encontrada en la plataforma VUR Valle del Cauca"));
                        transaccion.setApellidos(transaccionBD.getTraApellidos());
                        transaccion.setNombres(transaccionBD.getTraNombres());
                        transaccion.setTipoDocumento(transaccionBD.getTdcId().getTdcSigla());
                        transaccion.setNumeroDocumento(transaccionBD.getTraDocumento());
                        transaccion.setTelefono(transaccionBD.getTraTelefono());
                        transaccion.setCus(transaccionBD.getTraCus());
                        transaccion.setDescripcionTransaccion("Pago de Impuestos de Registro Valle del Cauca");
                        transaccion.setReferencia(transaccionBD.getTraReferencia());
                        transaccion.setTipoMedioPago(transaccionBD.getTraTipoPago());
                        transaccion.setFechaTransaccion(transaccionBD.getTraFechaInicio());
                        transaccion.setFechaVencimiento(extrarDateLimitFromTransaction(transaccionBD));

                        long valorTransaccion;
                        try {
                            valorTransaccion = transaccionBD.getTraValor().longValue();
                        } catch (Exception e) {
                            valorTransaccion = 0;
                        }
                        transaccion.setValorTransaccion(valorTransaccion);
                        transaccion.setCodigoServicioACH(ConstantesUtils.cargarConstante("WS-PASARELA-ACH"));
                    } else {
                        transaccion.setEstado("ERROR");
                        transaccion.setLog(new VURTransaccionLogSDO("ERROR", "La transaccion con referencia " + codigoLiquidacion + " se vencio hoy y solo podia ser pagada antes de horario adicional."));
                    }
                } else {
                    transaccion.setEstado("ERROR");
                    transaccion.setLog(new VURTransaccionLogSDO("ERROR", "La transaccion con referencia " + codigoLiquidacion + " no se encuentra en un estado pendiente por pago"));
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
     * @param claveConfirmacion
     * @param fechaPago
     * @param valorPagado
     * @param referenciaCodigoBarras
     * @return
     */
    @WebMethod(operationName = "confirmarTransaccion")
    public VURTransaccionConfirmacion confirmarTransaccion(@WebParam(name = "claveConfirmacion") String claveConfirmacion, @WebParam(name = "fechaPago") String fechaPago, @WebParam(name = "valorPagado") Long valorPagado, @WebParam(name = "referenciaCodigoBarras") String referenciaCodigoBarras) {
        VURTransaccionConfirmacion confirmacion;
        TransactionCore core = TransactionCore.create();
        confirmacion = core.confirmarTransaccion(claveConfirmacion, fechaPago, valorPagado, referenciaCodigoBarras);
        return confirmacion;
    }

    /**
     * Extract fecha limite de pago de la transaccion.
     *
     * @param transaccion
     * @return
     */
    private static Date extrarDateLimitFromTransaction(MarTransacciones transaccion) {
        Date fecha;
        try {
            String fechaString = transaccion.getRadId().getMarRadicacionesDetallesSap().getRdeFechaLdp();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fecha = sdf.parse(fechaString);
        } catch (Exception e) {
            fecha = null;
        }
        return fecha;
    }

}
