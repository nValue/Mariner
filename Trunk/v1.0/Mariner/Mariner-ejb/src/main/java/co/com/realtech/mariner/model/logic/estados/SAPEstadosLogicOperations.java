package co.com.realtech.mariner.model.logic.estados;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBean;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.cambio_estado.DetalleCambioEstado;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import javax.naming.InitialContext;

/**
 * Utilidades manejo SAP para cambios de estados.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPEstadosLogicOperations {

    protected WSSAPConsumerBeanLocal wSSAPConsumerBean;

    public static SAPEstadosLogicOperations create() {
        return new SAPEstadosLogicOperations();
    }

    private SAPEstadosLogicOperations() {
        try {
            InitialContext ic = new InitialContext();
            wSSAPConsumerBean = (WSSAPConsumerBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(WSSAPConsumerBean.class, Boolean.TRUE));
        } catch (Exception e) {
            System.out.println("Error inicializando SAPEstadosLogicOperations, causado por " + e);
        }
    }

    /**
     * Aplicar nuevo estado a la liqidacion.
     *
     * @param liquidacion
     * @param estado
     * @return
     * @throws java.lang.Exception
     */
    public DetalleCambioEstado cambiarEstadoLiquidacion(String liquidacion, String estado) throws Exception {
        return wSSAPConsumerBean.actualizarEstadoLiquidacion(liquidacion, estado);
    }

}
