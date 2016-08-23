/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.reportes;

import co.com.realtech.mariner.model.entity.MarRolesReportes;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author fabianagudelo
 */
@Local
public interface RolesReportesDAOBeanLocal {
    
    /**
     * Obtiene todos los reportes asociados a un usuario específico
     * @param usuario
     * @return
     * @throws MarinerPersistanceException 
     */
    public List<MarRolesReportes> obtenerReportesPorUsuarioYTipo(MarUsuarios usuario, String tipo) throws MarinerPersistanceException;
    
}
