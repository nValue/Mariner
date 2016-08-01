package co.com.realtech.mariner.ws.transacciones.core;

import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.payment.DetallePago;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.model.logic.pagos.SAPPagosLogicOperations;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionConfirmacion;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionLogSDO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Clase avanzada encargada de opciones de confirmacion de transacciones.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class TransactionCore implements Serializable {

    final static Logger logger = Logger.getLogger(TransactionCore.class);
    private GenericDAOBeanLocal genericDAOBean;
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    public static TransactionCore create() {
        return new TransactionCore();
    }

    private TransactionCore() {
        try {
            InitialContext ic = new InitialContext();
            genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
            radicFasesEstadosDAOBean = (RadicFasesEstadosDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(RadicFasesEstadosDAOBean.class, Boolean.TRUE));
        } catch (Exception e) {
            logger.error("Error inicializando TransactionCore, causado por " + e, e);
        }
    }

    /**
     * Confirmacin de pago en la logica de negocio VUR.
     *
     * @param claveConfirmacion
     * @param fechaPago
     * @param valorPagado
     * @param referenciaCodigoBarras
     * @return
     */
    public VURTransaccionConfirmacion confirmarTransaccion(String claveConfirmacion, String fechaPago, Long valorPagado, String referenciaCodigoBarras) {
        VURTransaccionConfirmacion confirmacion = new VURTransaccionConfirmacion();
        try {
            MarUsuarios usuarioMariner = (MarUsuarios) genericDAOBean.findByColumn(MarUsuarios.class, "usuLogin", "MARINER");
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
                                BigDecimal salida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacion, "P-A", "A", usuarioMariner, "", null);

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
                                        // Nuevo parametro para enviar la fecha real del ingreso del dinero.
                                        String fechaValor=fechaRecaudo;
                                        
                                        DetallePago detallePagoSap = pagos.aplicarPagoSAP(transaccionBD.getRadId().getRadLiquidacion(), fechaRecaudo,fechaValor, horaRecaudo, new BigDecimal(valorPagado.toString()));
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

}
