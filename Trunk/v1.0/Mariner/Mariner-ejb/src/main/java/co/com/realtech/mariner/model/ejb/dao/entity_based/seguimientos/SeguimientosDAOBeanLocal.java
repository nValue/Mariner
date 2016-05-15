package co.com.realtech.mariner.model.ejb.dao.entity_based.seguimientos;

import co.com.realtech.mariner.model.entity.NvaSeguimiento;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface EJB SeguimientosDAOBean
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@Local
public interface SeguimientosDAOBeanLocal {
    
    public List<NvaSeguimiento> filtradoSeguimientos(Date fechaInicial,Date fechaFinal,String texto) throws Exception;
    
}
