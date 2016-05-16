/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author fabianagudelo
 */
@Local
public interface RadicFasesEstadosDAOBeanLocal {
    
    public MarRadicacionesFasesEstados obtenerRadicFaseEstDeRadyFase(MarRadicaciones radicacion, String faseEstado) throws MarinerPersistanceException;
    
    public List<MarRadicacionesFasesEstados> obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(MarUsuarios usuario, String faseEstado, Date fechaIn, Date fechaFin) throws MarinerPersistanceException;
    
}
