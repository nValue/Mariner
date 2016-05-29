package co.com.realtech.mariner.ws.transacciones;

import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.pasarela.PSEWSConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.payment.DetallePago;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.model.logic.pagos.SAPPagosLogicOperations;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionConfirmacion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionLogSDO;
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

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

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
        VURTransaccionConfirmacion confirmacion = new VURTransaccionConfirmacion();
        try {
            String claveConfConstante = ConstantesUtils.cargarConstante("WS-PASARELA-CODIGO-CONFIRMACION");

            if (claveConfConstante.equals(claveConfirmacion)) {
                String codigoLiquidacion = BusinessStringUtils.convertNumeroLiquidacion(referenciaCodigoBarras);
                MarRadicaciones radicacion = (MarRadicaciones) genericDAOBean.findByColumn(MarRadicaciones.class, "radLiquidacion", codigoLiquidacion);
                MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "radId", radicacion);

                if (transaccionBD != null) {
                    if (transaccionBD.getTraEstado().equals("T")) {
                        // Verificamos que la radicacion siga en estado pendiente de pago.
                        MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacion);

                        if (estado.getFesId().getFesCodigo().equals("R-A")) {
                            // verificamos la fecha de la transaccion sea dd/MM/yyyy HH:mm:ss
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            try {
                                // Guardamos la nueva fase estado del a transaccion.
                                BigDecimal salida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacion, "P-A", "A", transaccionBD.getUsuId(), "", null);

                                if (salida.intValue() != -999) {
                                    try {
                                        transaccionBD.setTraFechaFinalizacion(sdf.parse(fechaPago));
                                    } catch (Exception e) {
                                        transaccionBD.setTraFechaFinalizacion(new Date());
                                    }

                                    // Confirmamos la transaccion en SAP - Si no se guarda el error interno.
                                    try {
                                        SAPPagosLogicOperations pagos = SAPPagosLogicOperations.create();
                                        sdf = new SimpleDateFormat("yyyyMMdd");
                                        String fechaRecaudo = sdf.format(transaccionBD.getTraFechaFinalizacion());
                                        sdf = new SimpleDateFormat("HHmmss");
                                        String horaRecaudo = sdf.format(transaccionBD.getTraFechaFinalizacion());
                                        DetallePago detallePagoSap = pagos.aplicarPagoSAP(transaccionBD.getRadId().getRadLiquidacion(), fechaRecaudo, horaRecaudo, new BigDecimal(valorPagado.toString()));
                                        transaccionBD.setTraPagoSapEstado(detallePagoSap.getEstadoSalida());
                                        transaccionBD.setTraPagoSapMensaje(detallePagoSap.getMensajeSalida());
                                    } catch (Exception e) {
                                        transaccionBD.setTraPagoSapEstado("-1");
                                        transaccionBD.setTraPagoSapMensaje("Error interno intentando confirmar transaccion en SAP, causado por " + e);
                                    }

                                    transaccionBD.setTraReferenciaRecibo(referenciaCodigoBarras);
                                    transaccionBD.setTraEstado("A");
                                    transaccionBD.setTraValorPagadoPse(valorPagado.toString());

                                    genericDAOBean.merge(transaccionBD);
                                    confirmacion.setEstado("OK");
                                    confirmacion.setLog(new VURTransaccionLogSDO("OK", "Transaccion confirmada correctamente en la plataforma", "Transaccion confirmada correctamente en la plataforma"));
                                } else {
                                    confirmacion.setEstado("ERROR");
                                    confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Transaccion no confirmada en la plataforma", "Ocurrio un error al confirmar la transaccion en la plataforma, no se ha podido agregar la fase estado."));
                                }
                            } catch (MarinerPersistanceException e) {
                                confirmacion.setEstado("ERROR");
                                confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Transaccion no confirmada en la plataforma", "Se ha producido un error al intentar confirmar la transaccion en la base de datos."));
                            }
                        } else {
                            confirmacion.setEstado("ERROR");
                            confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Transaccion no confirmada en la plataforma, No se encuentra activa para confirmacion", "Ocurrio un error al confirmar la transaccion en la plataforma, la transaccion seleccionada no se encuentra en la Fase-Estado pendiente para pago"));
                        }
                    } else {
                        confirmacion.setEstado("ERROR");
                        confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "La transaccion con referencia " + referenciaCodigoBarras + " no se encuentra en estado por confirmar en la plataforma"));
                    }
                } else {
                    confirmacion.setEstado("ERROR");
                    confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "No se ha encontrado ninguna transaccion con referencia: " + referenciaCodigoBarras));
                }
            } else {
                confirmacion.setEstado("ERROR");
                confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Clave de confirmacion incorrecta", "La Clave de confirmacion ingresada es incorrecta, contacte al administrador de la plataforma."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            confirmacion.setEstado("ERROR");
            confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Se ha produccido un error interno confirmando la transaccion", "Error intentando confirmar la transaccion por codigo, error interno " + e));
        }
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
            String fechaString = transaccion.getRadId().getMarRadicacionesDetallesSap().getRdeFechaLimite();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fecha = sdf.parse(fechaString);
        } catch (Exception e) {
            fecha = null;
        }
        return fecha;
    }
}
