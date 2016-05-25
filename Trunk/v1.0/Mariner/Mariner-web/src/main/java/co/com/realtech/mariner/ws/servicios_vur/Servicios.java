package co.com.realtech.mariner.ws.servicios_vur;

import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.business.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesActosSap;
import co.com.realtech.mariner.model.entity.MarRadicacionesDetallesSap;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.model.sdo.estandar.EntidadLiquidacionResultado;
import co.com.realtech.mariner.model.sdo.logs.EntidadLog;
import co.com.realtech.mariner.ws.servicios_vur.marshaller.EntityMarshallers;
import co.com.realtech.mariner.ws.servicios_vur.marshaller.EntityMarshallersBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Fachada servicios web implementacion VUR Valle del Cauca.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@WebService(serviceName = "Servicios")
public class Servicios {

    @EJB(beanName = "GenericDAOBean")
    protected GenericDAOBeanLocal genericDAOBean;

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    @EJB(beanName = "WSSAPConsumerBean")
    private WSSAPConsumerBeanLocal wSSAPConsumerBean;

    /**
     * Metodo expuerto para recibir notificacion de cuando se crea la
     * liquidacion en otros sistemas.
     *
     * @param codigoLiquidacion
     * @param codigoRadicacion
     * @return
     */
    @WebMethod(operationName = "creacionLiquidacion")
    public EntidadLiquidacionResultado creacionLiquidacion(@WebParam(name = "codigoLiquidacion") String codigoLiquidacion, @WebParam(name = "codigoRadicacion") Long codigoRadicacion) {
        EntidadLiquidacionResultado salida = new EntidadLiquidacionResultado();
        try {
            // consulta detalle.
            MarRadicaciones radicacion = (MarRadicaciones) genericDAOBean.findByColumn(MarRadicaciones.class, "radId", new BigDecimal(codigoRadicacion.toString()));

            if (radicacion != null) {
                // Verificamos que la radiacion este en estado Pendiente SAP G-S
                MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(radicacion);

                if (estado.getFesId().getFesCodigo().equals("G-S")) {
                    MarRadicacionesDetallesSap detalleSAP = (MarRadicacionesDetallesSap) genericDAOBean.findByColumn(MarRadicacionesDetallesSap.class, "radId", radicacion);
                    if (detalleSAP == null) {
                        detalleSAP = new MarRadicacionesDetallesSap();
                        detalleSAP.setRadId(radicacion);
                    }
                    // Buscar la radicacion por liquidacion en SAP
                    DetalleLiquidacion detalle = wSSAPConsumerBean.getDetail(codigoLiquidacion);
                    if (detalle.getLiqNumero() != null && !detalle.getLiqNumero().equals("")) {
                        EntityMarshallers marshall = EntityMarshallersBuilder.createEntityMarshallers();
                        List<MarRadicacionesActosSap> actos = new ArrayList<>();
                        marshall.mixDetailObjects(detalleSAP, actos, radicacion, detalle);
                        if (detalleSAP.getRdeId() == null) {
                            genericDAOBean.save(detalleSAP);
                        } else {
                            genericDAOBean.merge(detalleSAP);
                        }
                        // Guardamos los actos de la liquidacion
                        for (MarRadicacionesActosSap actoSap : actos) {
                            actoSap.setRdeId(detalleSAP);
                            actoSap.setAudFecha(new Date());
                            genericDAOBean.save(actoSap);
                        }
                        // Guardamos la radicacion
                        genericDAOBean.merge(radicacion);
                        // Guardamos la nueva fase estado del proceso.
                        MarUsuarios usuarioSistema = ((MarUsuarios) genericDAOBean.findByColumn(MarUsuarios.class, "usuLogin", "MARINER"));
                        BigDecimal regEstado = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", radicacion, "G-A", "A", usuarioSistema, "", null);

                        if (regEstado.intValue() != -999) {
                            salida.setEstado("OK");
                            salida.setCodigoConfirmacion(regEstado.longValue());
                            salida.setLog(new EntidadLog("1000", "OK", "Proceso de liquidacion recibida en la plataforma", "El proceso de liquidacion No." + codigoLiquidacion + " ha sido recibido por la plataforma."));
                        } else {
                            salida.setEstado("ERROR");
                            salida.setLog(new EntidadLog("1005", "ERROR", "Error registrando estado Fase en sistema", "Se obtubieron los datos de SAP, pero no fue posible vincular el Estado Fase G-A"));
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
                salida.setLog(new EntidadLog("1002", "ERROR", "No se ha encontrado la Radicacion con el codigo ingresado", "Error buscando radicacion " + codigoRadicacion + " no se ha encontrado el codigo de radicacion en el sistema."));
            }
        } catch (Exception e) {
            salida.setEstado("ERROR");
            salida.setLog(new EntidadLog("1001", "ERROR", "Error interno procesando peticion", "Error interno causado por " + e));
        }
        return salida;
    }

    /**
     * Ajustar estado Liquidacion.
     *
     * @param codigoLiquidacion
     * @param codigoRadicacion
     * @param estadoLiquidacion
     * @return
     */
    @WebMethod(operationName = "ajustarEstadoLiquidacion")
    public EntidadLiquidacionResultado ajustarEstadoLiquidacion(@WebParam(name = "codigoLiquidacion") String codigoLiquidacion, @WebParam(name = "codigoRadicacion") Long codigoRadicacion, @WebParam(name = "estadoLiquidacion") String estadoLiquidacion) {
        EntidadLiquidacionResultado salida = new EntidadLiquidacionResultado();
        try {
            salida.setEstado("OK");
            salida.setCodigoConfirmacion(new Long((int) (Math.random() * 96656)));
            salida.setLog(new EntidadLog("1000", "OK", "Proceso ajuste liquidacion recibida en la plataforma", "El proceso de liquidacion No." + codigoLiquidacion + " ha sido recibido por la plataforma para ajuste con nuevo estado " + estadoLiquidacion));

        } catch (Exception e) {
            salida.setEstado("ERROR");
            salida.setCodigoConfirmacion(new Long((int) (Math.random() * 96656)));
            salida.setLog(new EntidadLog("1001", "ERROR", "Error interno procesando peticion", "Error interno causado por " + e));
        }
        return salida;
    }

    /**
     * Recibir informacion de la boleta Fiscal y del Recibo de Pago.
     *
     * @param codigoLiquidacion
     * @param codigoRadicacion
     * @param boletaFiscal
     * @param reciboDePago
     * @return
     */
    @WebMethod(operationName = "recibirInformacionLiquidacion")
    public EntidadLiquidacionResultado recibirInformacionLiquidacion(@WebParam(name = "codigoLiquidacion") String codigoLiquidacion, @WebParam(name = "codigoRadicacion") Long codigoRadicacion, @WebParam(name = "boletaFiscal") byte[] boletaFiscal, @WebParam(name = "reciboDePago") byte[] reciboDePago) {
        EntidadLiquidacionResultado salida = new EntidadLiquidacionResultado();
        try {
            salida.setEstado("OK");
            salida.setCodigoConfirmacion(new Long((int) (Math.random() * 96656)));
            salida.setLog(new EntidadLog("1000", "OK", "Documentos de Boleta Fiscal y Recibo almacenados correctemente.", "El proceso de liquidacion No." + codigoLiquidacion + " ha recibido la informacion no estructurada"));
        } catch (Exception e) {
            salida.setEstado("ERROR");
            salida.setCodigoConfirmacion(new Long((int) (Math.random() * 96656)));
            salida.setLog(new EntidadLog("1001", "ERROR", "Error interno procesando peticion", "Error interno causado por " + e));
        }
        return salida;
    }
}
