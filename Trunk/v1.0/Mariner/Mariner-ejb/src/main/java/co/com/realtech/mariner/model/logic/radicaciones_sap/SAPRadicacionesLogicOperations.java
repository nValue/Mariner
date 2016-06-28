package co.com.realtech.mariner.model.logic.radicaciones_sap;

import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBean;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesActosSap;
import co.com.realtech.mariner.model.entity.MarRadicacionesDetallesSap;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.model.sdo.estandar.EntidadLiquidacionResultado;
import co.com.realtech.mariner.model.sdo.logs.EntidadLog;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import co.com.realtech.mariner.util.marshall.liquidacion.LiquidacionMarshaller;
import co.com.realtech.mariner.util.marshall.liquidacion.LiquidacionMarshallersBuilder;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;

/**
 * Utilidades de logica de negocio para Vinculo de SAP y Radicacioens VUR
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPRadicacionesLogicOperations implements Serializable {

    protected GenericDAOBeanLocal genericDAOBean;
    protected RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;
    protected WSSAPConsumerBeanLocal wSSAPConsumerBean;

    public static SAPRadicacionesLogicOperations create() {
        return new SAPRadicacionesLogicOperations();
    }

    private SAPRadicacionesLogicOperations() {
        try {
            InitialContext ic = new InitialContext();
            genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
            wSSAPConsumerBean = (WSSAPConsumerBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(WSSAPConsumerBean.class, Boolean.TRUE));
            radicFasesEstadosDAOBean = (RadicFasesEstadosDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(RadicFasesEstadosDAOBean.class, Boolean.TRUE));
        } catch (Exception e) {
            System.out.println("Error inicializando SAPRadicacionesLogicOperations, causado por " + e);
        }
    }
    /**
     * Consultar detalle de la liquidacion en SAP por codigo de liquidacion.
     * @param codigoLiquidacion
     * @return
     * @throws Exception 
     */
    public DetalleLiquidacion consultarLiquidacionSAP(String codigoLiquidacion) throws Exception {
        DetalleLiquidacion detalle = new DetalleLiquidacion();
        try {
            // Buscar la radicacion por liquidacion en SAP
            codigoLiquidacion = BusinessStringUtils.convertNumeroLiquidacion(codigoLiquidacion);
            detalle = wSSAPConsumerBean.getDetail(codigoLiquidacion);
        } catch (Exception e) {
            throw e;
        }
        return detalle;
    }

    /**
     * Vinculo de Informacion de SAP por codigo de la liquidacion con Radiacion.
     *
     * @param radicacion
     * @param codigoLiquidacion
     * @return
     */
    public EntidadLiquidacionResultado vincularRadicacionSAP(MarRadicaciones radicacion, String codigoLiquidacion, boolean cambiaEstado) {
        EntidadLiquidacionResultado salida = new EntidadLiquidacionResultado();
        try {
            if (radicacion != null) {
                // Verificamos que la radicacion este en estado Pendiente SAP G-S
                MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacion);

                if (estado.getFesId().getFesCodigo().equals("G-S")) {
                    MarRadicacionesDetallesSap detalleSAP = (MarRadicacionesDetallesSap) genericDAOBean.findByColumn(MarRadicacionesDetallesSap.class, "radId", radicacion);
                    if (detalleSAP == null) {
                        detalleSAP = new MarRadicacionesDetallesSap();
                        detalleSAP.setRadId(radicacion);
                    }
                    // Buscar la radicacion por liquidacion en SAP
                    codigoLiquidacion = BusinessStringUtils.convertNumeroLiquidacion(codigoLiquidacion);
                    DetalleLiquidacion detalleWS = wSSAPConsumerBean.getDetail(codigoLiquidacion);

                    if (detalleWS.getLiqNumero() != null && !detalleWS.getLiqNumero().equals("")) {
                        LiquidacionMarshaller marshall = LiquidacionMarshallersBuilder.createLiquidacionMarshaller();
                        marshall.fillLiquidacionValues(detalleSAP, radicacion, detalleWS);
                        detalleSAP = marshall.getSap();
                        List<MarRadicacionesActosSap> actosSAP = marshall.getActos();
                        // Guardamos la radicacion.
                        genericDAOBean.merge(radicacion);

                        // Guardamos informacion de la liquidacion.
                        List<MarRadicacionesActosSap> actosSAPBD = new ArrayList<>();
                        if (detalleSAP.getRdeId() == null) {
                            genericDAOBean.save(detalleSAP);
                        } else {
                            genericDAOBean.merge(detalleSAP);
                            actosSAPBD = (List<MarRadicacionesActosSap>) genericDAOBean.findAllByColumn(MarRadicacionesActosSap.class, "rdeId", detalleSAP, true, "rdsId desc");
                        }
                        // Borramos los actos en BD si ya los tiene
                        if (!actosSAPBD.isEmpty()) {
                            for (MarRadicacionesActosSap actoBD : actosSAPBD) {
                                genericDAOBean.delete(actoBD, actoBD.getRdsId());
                            }
                        }
                        // Guardamos los actos de la liquidacion
                        for (MarRadicacionesActosSap actoSap : actosSAP) {
                            actoSap.setRdeId(detalleSAP);
                            actoSap.setAudFecha(new Date());
                            genericDAOBean.save(actoSap);
                        }
                        if (cambiaEstado) {
                            // Guardamos la nueva fase estado del proceso.
                            MarUsuarios usuarioSistema = ((MarUsuarios) genericDAOBean.findByColumn(MarUsuarios.class, "usuLogin", "MARINER"));
                            BigDecimal regEstado = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacion, "G-A", "A", usuarioSistema, "", null);
                            if (regEstado.intValue() != -999) {
                                salida.setEstado("OK");
                                salida.setCodigoConfirmacion(regEstado.longValue());
                                salida.setLog(new EntidadLog("1000", "OK", "Proceso de liquidacion recibida en la plataforma", "El proceso de liquidacion No." + codigoLiquidacion + " ha sido recibido por la plataforma."));
                            } else {
                                salida.setEstado("ERROR");
                                salida.setLog(new EntidadLog("1005", "ERROR", "Error registrando estado Fase en sistema", "Se obtuvieron los datos de SAP, pero no fue posible vincular el Estado Fase G-A"));
                            }
                        } else {
                            salida.setEstado("OK");
                            salida.setLog(new EntidadLog("1000", "OK", "Proceso de liquidacion recibida en la plataforma", "El proceso de liquidacion No." + codigoLiquidacion + " ha sido recibido por la plataforma."));
                        }
                    } else {
                        salida.setEstado("ERROR");
                        salida.setLog(new EntidadLog("1004", "ERROR", "Liquidacion no encontrada en SAP", "Error, la liquidacion " + codigoLiquidacion + " no ha sido retornadada por el sistema SAP"));
                    }
                } else {
                    salida.setEstado("ERROR");
                    salida.setLog(new EntidadLog("1006", "ERROR", "Error validacion en Fase G-S", "La radicacion seleccionada no se encuentra en estado G-S para pasar a G-A"));
                }
            } else {
                salida.setEstado("ERROR");
                salida.setLog(new EntidadLog("1002", "ERROR", "No se ha encontrado la Radicacion en el sistema", "El objeto de radicacion no ha sido enviado al objeto de operacion logica, el codigo de radicacion no existe en la plataforma"));
            }
            return salida;
        } catch (Exception e) {
            salida.setEstado("ERROR");
            salida.setLog(new EntidadLog("1001", "ERROR", "Error interno procesando peticion", "Error interno causado por " + e));
        }
        return salida;
    }
}
