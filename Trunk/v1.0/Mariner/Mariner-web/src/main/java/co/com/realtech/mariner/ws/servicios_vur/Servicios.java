package co.com.realtech.mariner.ws.servicios_vur;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.logic.radicaciones_sap.SAPRadicacionesLogicOperations;
import co.com.realtech.mariner.model.sdo.estandar.EntidadLiquidacionResultado;
import co.com.realtech.mariner.model.sdo.logs.EntidadLog;
import java.math.BigDecimal;
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
            SAPRadicacionesLogicOperations sapLogic = SAPRadicacionesLogicOperations.create();
            salida = sapLogic.vincularRadicacionSAP(radicacion, codigoLiquidacion);
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
