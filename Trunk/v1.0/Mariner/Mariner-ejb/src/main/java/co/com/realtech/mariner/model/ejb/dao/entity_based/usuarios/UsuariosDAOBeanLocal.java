package co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios;

import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.List;
import javax.ejb.Local;

/**
 * (interface)
 * @author fabianagudelo
 * @version 1.0
 * @since JDK1.9.91
 */
@Local
public interface UsuariosDAOBeanLocal {
    
    public List<MarUsuarios> loadUserFiltered(String filterValue, MarUsuarios usuarioSession) throws Exception;
    
    public boolean validacionCreacionUsuario(MarUsuarios usuario, MarPersonas persona) throws Exception;
    
    public List<MarUsuarios> obtenerAsociadosAModulo(String idModulo) throws MarinerPersistanceException;
    
    public int obtenerCantPendientesDeUsuario(MarUsuarios usuario, String fase) throws MarinerPersistanceException;
    
}
