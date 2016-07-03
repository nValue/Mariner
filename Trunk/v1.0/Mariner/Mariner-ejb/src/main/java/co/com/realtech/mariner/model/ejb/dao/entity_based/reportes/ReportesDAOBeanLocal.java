/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.reportes;

import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author fabianagudelo
 */
@Local
public interface ReportesDAOBeanLocal {
    
    /**
     * Obtiene los reportes asociados a un grupo de códigos separados por comas y entre comillas simples
     * @param codigos Se envían los códigos para ser tratados dentro de un IN Ej. 'ESP_01','ESL_01'
     * @return
     * @throws MarinerPersistanceException 
     */
    public List<MarReportes> obtenerReportesDeCodigosTipo(String codigos) throws MarinerPersistanceException;
    
}
