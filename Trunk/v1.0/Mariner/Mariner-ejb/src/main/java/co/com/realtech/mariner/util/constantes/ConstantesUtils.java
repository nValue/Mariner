package co.com.realtech.mariner.util.constantes;

import co.com.realtech.mariner.model.ejb.dao.procedure_based.ProcedureBasedBean;
import co.com.realtech.mariner.model.ejb.dao.procedure_based.ProcedureBasedBeanLocal;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Utilidad de gestion de constantes
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class ConstantesUtils {

    static ProcedureBasedBeanLocal procedureBasedBean;
    final static Logger logger = Logger.getLogger(ConstantesUtils.class);

    static {
        try {
            InitialContext ic = new InitialContext();
            procedureBasedBean = (ProcedureBasedBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(ProcedureBasedBean.class, Boolean.TRUE));
        } catch (Exception e) {
            logger.error("Error inicializando ConstantesUtils, causado por: " + e, e);
        }
    }

    /**
     * Cargar constante de base de datos.
     *
     * @param llave
     * @return
     */
    public static String cargarConstante(String llave) {
        return procedureBasedBean.cargarConstante(llave);
    }

}
