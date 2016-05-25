package co.com.realtech.mariner.ws.transacciones;

import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionConfirmacion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionLogSDO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
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
                    transaccion.setDescripcionTransaccion("Pago de Derechos de Registro Valle del Cauca");
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
            MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "traReferencia", referencia);

            if (transaccionBD != null) {
                if (transaccionBD.getTraEstado().equalsIgnoreCase("T")) {
                    transaccion.setEstado("OK");
                    transaccion.setLog(new VURTransaccionLogSDO("OK", "Transaccion con Referencia " + referencia + " encontrada en la plataforma VUR Valle del Cauca"));
                    transaccion.setApellidos(transaccionBD.getTraApellidos());
                    transaccion.setNombres(transaccionBD.getTraNombres());
                    transaccion.setTipoDocumento(transaccionBD.getTdcId().getTdcSigla());
                    transaccion.setNumeroDocumento(transaccionBD.getTraDocumento());
                    transaccion.setTelefono(transaccionBD.getTraTelefono());
                    transaccion.setCus(transaccionBD.getTraCus());
                    transaccion.setDescripcionTransaccion("Pago de Derechos de Registro Valle del Cauca");
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
     * @param fechaPago
     * @param valorPagado
     * @return
     */
    @WebMethod(operationName = "confirmarTransaccion")
    public VURTransaccionConfirmacion confirmarTransaccion(@WebParam(name = "codigoTransaccion") Long codigoTransaccion, @WebParam(name = "claveConfirmacion") String claveConfirmacion, @WebParam(name = "fechaPago") String fechaPago, @WebParam(name = "valorPagado") Long valorPagado) {
        VURTransaccionConfirmacion confirmacion = new VURTransaccionConfirmacion();
        try {
            String claveConfConstante = ConstantesUtils.cargarConstante("WS-PASARELA-CODIGO-CONFIRMACION");

            if (claveConfConstante.equals(claveConfirmacion)) {
                MarTransacciones transaccionBD = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "traId", new BigDecimal(codigoTransaccion));

                if (transaccionBD != null) {
                    if (transaccionBD.getTraEstado().equals("T")) {
                        // 
                        // Verificamos que la radicacion siga en estado pendiente de pago.
                        MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(transaccionBD.getRadId());

                        if (estado.getFesId().getFesCodigo().equals("R-A")) {
                            // verificamos la fecha de la transaccion sea dd/MM/yyyy hh:mm:ss
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

                            try {
                                // Guardamos la nueva fase estado del a transaccion.
                                BigDecimal salida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", transaccionBD.getRadId(), "P-A", "A", transaccionBD.getUsuId(), "", null);

                                if (salida.intValue() != -999) {
                                    transaccionBD.setTraFechaFinalizacion(sdf.parse(fechaPago));
                                    transaccionBD.setTraValorPagado(new BigInteger(valorPagado.toString()));
                                    transaccionBD.setTraFechaFinalizacion(new Date());
                                    transaccionBD.setTraEstado("A");
                                    genericDAOBean.merge(transaccionBD);
                                    confirmacion.setEstado("OK");
                                    confirmacion.setLog(new VURTransaccionLogSDO("OK", "Transaccion confirmada correctamente en la plataforma", "Transaccion confirmada correctamente en la plataforma"));
                                } else {
                                    confirmacion.setEstado("ERROR");
                                    confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Transaccion no confirmada en la plataforma", "Ocurrio un error al confirmar la transaccion en la plataforma, no se ha podido agregar la fase estado."));
                                }
                            } catch (MarinerPersistanceException | ParseException e) {
                                confirmacion.setEstado("ERROR");
                                confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Transaccion no confirmada en la plataforma, error fecha", "El parametro fecha de la transaccion debe estar en formato DD/MM/YYYY HH24:MI:SS"));
                            }

                        } else {
                            confirmacion.setEstado("ERROR");
                            confirmacion.setLog(new VURTransaccionLogSDO("ERROR", "Transaccion no confirmada en la plataforma, No se encuentra activa para confirmacion", "Ocurrio un error al confirmar la transaccion en la plataforma, la transaccion seleccionada no se encuentra en la Fase-Estado pendiente para pago"));
                        }
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

    /**
     * Extract fecha limite de pago de la transaccion.
     *
     * @param transaccion
     * @return
     */
    public static Date extrarDateLimitFromTransaction(MarTransacciones transaccion) {
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
