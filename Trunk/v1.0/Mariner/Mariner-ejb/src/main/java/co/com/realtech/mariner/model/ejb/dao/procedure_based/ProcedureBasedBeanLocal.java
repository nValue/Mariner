package co.com.realtech.mariner.model.ejb.dao.procedure_based;

import java.math.BigDecimal;
import javax.ejb.Local;

/**
 * (interface)
 * EJB encargado de funcionalidades basadas en PLSQL
 * 
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@Local
public interface ProcedureBasedBeanLocal {
    
    public String cargarConstante(String llave);
    
    public void vincularArchivoSAP(BigDecimal radId) throws Exception;
    
}
