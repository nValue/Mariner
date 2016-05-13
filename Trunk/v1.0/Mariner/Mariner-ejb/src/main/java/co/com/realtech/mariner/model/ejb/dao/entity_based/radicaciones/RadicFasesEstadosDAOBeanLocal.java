/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import javax.ejb.Local;

/**
 *
 * @author fabianagudelo
 */
@Local
public interface RadicFasesEstadosDAOBeanLocal {
    
    public MarRadicacionesFasesEstados obtenerRadicFaseEstDeRadyFase(MarRadicaciones radicacion, String faseEstado) throws MarinerPersistanceException;
    
}
