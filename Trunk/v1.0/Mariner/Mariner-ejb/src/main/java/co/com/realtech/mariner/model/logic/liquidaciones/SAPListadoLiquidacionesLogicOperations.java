package co.com.realtech.mariner.model.logic.liquidaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBean;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.sdo.get_detail_method.DetalleLiquidacion;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;

/**
 * Operaciones logicas adicionales al obtener el listado de liquidaciones
 * pendientes
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPListadoLiquidacionesLogicOperations {

    protected GenericDAOBeanLocal genericDAOBean;
    protected WSSAPConsumerBeanLocal wSSAPConsumerBean;

    public static SAPListadoLiquidacionesLogicOperations create() {
        return new SAPListadoLiquidacionesLogicOperations();
    }

    private SAPListadoLiquidacionesLogicOperations() {
        try {
            InitialContext ic = new InitialContext();
            genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
            wSSAPConsumerBean = (WSSAPConsumerBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(WSSAPConsumerBean.class, Boolean.TRUE));
        } catch (Exception e) {
            System.out.println("Error inicializando SAOListadoLiquidacionesLogicOperations, causado por " + e);
        }
    }

    /**
     * Obtener listado de liquidaciones para el dia Actual
     *
     * @return
     * @throws Exception
     */
    public List<DetalleLiquidacion> obtenerLiquidacionesSAP() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaConsulta = sdf.format(new Date());
        return obtenerLiquidacionesSAP(fechaConsulta);
    }

    /**
     * Obtener listado de liquidaciones para el dia ingresado.
     *
     * @param fecha
     * @return
     * @throws Exception
     */
    public List<DetalleLiquidacion> obtenerLiquidacionesSAP(String fecha) throws Exception {
        return wSSAPConsumerBean.getListLiquidaciones(fecha);
    }
    /**
     * Obtener listado de liquidaciones por usuario de SAP para el dia actual.
     * @param usuario
     * @return
     * @throws Exception 
     */
    public List<DetalleLiquidacion> obtenerLiquidacionesSAPByUsuario(String usuario) throws Exception {
        return filtrarLiquidacionesUsuario(obtenerLiquidacionesSAP(), usuario);
    }
    /**
     * Obtener listado de liquidaciones para usuario actual y fecha ingresada,
     * @param fecha
     * @param usuario
     * @return
     * @throws Exception 
     */
    public List<DetalleLiquidacion> obtenerLiquidacionesSAPByUsuario(String fecha, String usuario) throws Exception {
        return filtrarLiquidacionesUsuario(obtenerLiquidacionesSAP(fecha), usuario);
    }

    /**
     * Filtrado de liquidaciones por usuario.
     *
     * @param liquidaciones
     * @param usuario
     * @return
     */
    private List<DetalleLiquidacion> filtrarLiquidacionesUsuario(List<DetalleLiquidacion> liquidaciones, String usuario) {
        List<DetalleLiquidacion> filtrado = new ArrayList<>();
        liquidaciones.stream().filter((liq) -> (liq.getUname().toUpperCase().equals(usuario.toUpperCase()))).forEach((liq) -> {
            filtrado.add(liq);
        });
        return filtrado;
    }

}
