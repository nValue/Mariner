package co.com.realtech.mariner.util.sap_files;

import co.com.realtech.mariner.model.ejb.dao.procedure_based.ProcedureBasedBean;
import co.com.realtech.mariner.model.ejb.dao.procedure_based.ProcedureBasedBeanLocal;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.math.BigDecimal;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Utilidad de gestion de archivos SAP, vinculacion de archivos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPFilesUtils {
    
    static ProcedureBasedBeanLocal procedureBasedBean;
    final static Logger logger = Logger.getLogger(SAPFilesUtils.class);
    
    static {
        try {
            InitialContext ic = new InitialContext();
            procedureBasedBean = (ProcedureBasedBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(ProcedureBasedBean.class, Boolean.TRUE));
        } catch (Exception e) {
            logger.error("Error inicializando SAPFilesUtils, causado por: " + e, e);
        }
    }

    /**
     * Vincular archivos de SAP con la Radicacion.
     *
     * @param radId
     * @return
     */
    public static String vincularArchivosSAP(BigDecimal radId) {
        return procedureBasedBean.vincularArchivoSAP(radId);
    }
    
}
