/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Bean encargado de las transacciones realizadas a las radicaciones
 * @author fabianagudelo
 */
@Local
public interface RadicacionesDAOBeanLocal {
    
    public List<MarRadicaciones> obtenerRadicacionesPorFiltro(String filtro, MarUsuarios usuarioActual) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadicacionesPorUltimaFase(String fase, MarUsuarios usuario) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadicacionesPorUsuarioYFase(MarUsuarios usuario, String fase) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadicacionesPorUsuarioYFaseEstado(MarUsuarios usuario, String faseEstado) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadicacionesFinalizacionPorFechasYParametro(String tipo, String campoBusqueda, Date fechaInicial, Date fechaFinal) throws MarinerPersistanceException;
    
}