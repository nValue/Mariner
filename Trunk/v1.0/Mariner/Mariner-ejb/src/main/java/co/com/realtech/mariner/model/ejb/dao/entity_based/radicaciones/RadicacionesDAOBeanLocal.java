package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesAgrupamientos;
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
    
    
    /**
     * Obtiene las radicaciones cuya ultima fase sea la ingresada y para un usuario específico si se envía
     * @param fase El código de la fase, si quiere varias fases, escríbalas con coma y entre comillas simples ej: 'I-P','G-P'
     * @param usuario
     * @return
     * @throws MarinerPersistanceException 
     */
    public List<MarRadicaciones> obtenerRadicacionesPorUltimaFase(String fase, MarUsuarios usuario) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadicacionesPorUsuarioYFase(MarUsuarios usuario, String fase) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadicacionesPorUsuarioYFaseEstado(MarUsuarios usuario, String faseEstado) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadicacionesFinalizacionPorFechasYParametro(String tipo, String campoBusqueda, Date fechaInicial, Date fechaFinal, MarUsuarios usuario) throws MarinerPersistanceException;
    
    public List<MarRadicaciones> obtenerRadsAtendidasNotYFaseFinal(MarUsuarios usuario, String fasesAtendidas, String fasesFinales) throws MarinerPersistanceException;
    
    
    /**
     * Verifica que un turno no haya sido asignado el día de hoy.
     * @param turno
     * @return
     * @throws MarinerPersistanceException 
     */
    public boolean esTurnoValido(String turno) throws MarinerPersistanceException;
    
    /**
     * Obtiene las radicaciones activas por agrupamiento
     * @param radAgrup
     * @return
     * @throws MarinerPersistanceException 
     */
    public List<MarRadicaciones> obtenerRadicacionesActivasPorAgrupacion(MarRadicacionesAgrupamientos radAgrup) throws MarinerPersistanceException;
    
}