/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios;

import co.com.realtech.mariner.model.entity.MarUsuarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author fabianagudelo
 */
@Local
public interface UsuariosDAOBeanLocal {
    
    public List<MarUsuarios> loadUserFiltered(String filterValue, MarUsuarios usuarioSession) throws Exception;
    
}
