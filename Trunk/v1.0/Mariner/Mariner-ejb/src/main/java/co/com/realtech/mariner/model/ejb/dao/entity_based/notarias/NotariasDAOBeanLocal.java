/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.notarias;

import co.com.realtech.mariner.model.entity.MarNotarias;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.List;
import javax.ejb.Local;

/**
 * Bean de sesión encargado de obtener las notarías.
 * @author fabianagudelo
 */
@Local
public interface NotariasDAOBeanLocal {
    
    public List<MarNotarias> obtenerNotariasDeUsuario(MarUsuarios usuarioActual) throws MarinerPersistanceException;
    
}
