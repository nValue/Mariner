package co.com.realtech.mariner.model.ejb.dao.entity_based.modulo;

import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface EJB ModulosDAOBeanLocal
 * 
 * @author fabianagudelo
 * @version 1.0
 * @since JDK1.7
 */
@Local
public interface ModulosDAOBeanLocal {
    
    public List<MarModulos> obtenerModulosDeUsuario(MarUsuarios usuario) throws Exception;
    
    public List<MarModulos> loadModulesByParent(MarModulos modulo) throws Exception;
    
    public List<MarModulos> loadAccesibleModules() throws Exception;
    
}
