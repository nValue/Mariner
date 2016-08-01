package co.com.realtech.mariner.model.logic.pagos;

import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBean;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.payment.DetallePago;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.naming.InitialContext;

/**
 * Operaciones logicas proceso de pago
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPPagosLogicOperations implements Serializable {

    protected WSSAPConsumerBeanLocal wSSAPConsumerBean;

    public static SAPPagosLogicOperations create() {
        return new SAPPagosLogicOperations();
    }

    private SAPPagosLogicOperations() {
        try {
            InitialContext ic = new InitialContext();
            wSSAPConsumerBean = (WSSAPConsumerBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(WSSAPConsumerBean.class, Boolean.TRUE));
        } catch (Exception e) {
            System.out.println("Error inicializando SAPPagosLogicOperations, causado por " + e);
        }
    }

    /**
     * Aplicar pago de liquidacion en sistema SAP.
     *
     * @param codigoLiquidacion
     * @param fechaRecaudo
     * @param horaRecaudo
     * @param valor
     * @return
     * @throws Exception
     */
    public DetallePago aplicarPagoSAP(String codigoLiquidacion, String fechaRecaudo, String fechaValor, String horaRecaudo, BigDecimal valor) throws Exception {
        return wSSAPConsumerBean.aplicarPagoSAP(codigoLiquidacion, fechaRecaudo, fechaValor, horaRecaudo, valor);
    }

}
