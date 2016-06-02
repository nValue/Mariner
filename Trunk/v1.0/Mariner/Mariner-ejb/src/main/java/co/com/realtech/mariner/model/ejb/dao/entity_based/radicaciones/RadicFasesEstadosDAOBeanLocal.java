package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Bean de sessión destinado a todas las consultas que se hagan a las fases y los estados
 * de las radicaciones.
 * @author fabianagudelo
 */
@Local
public interface RadicFasesEstadosDAOBeanLocal {
    
    public List<MarRadicacionesFasesEstados> obtenerRadicFaseEstDeRadyFase(MarRadicaciones radicacion, String faseEstado) throws MarinerPersistanceException;

    
    /**
     * Obtiene las radicaciones-fases-estados asociadas a un usuario, una fase-estado última y un rango de fechas(Opcionales)
     * @param usuario
     * @param faseEstado
     * @param fechaIn
     * @param fechaFin
     * @return
     * @throws MarinerPersistanceException 
     */
    public List<MarRadicacionesFasesEstados> obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(MarUsuarios usuario, String faseEstado, Date fechaIn, Date fechaFin) throws MarinerPersistanceException;
    
    public MarRadicacionesFasesEstados obtenerUltimaFaseDeRadicacion(MarRadicaciones radicacion) throws MarinerPersistanceException;
    
    public List<MarRadicacionesFasesEstados> obtenerPendientesConCodigos(String fases) throws MarinerPersistanceException ;
    
    /**
     * Obtiene los minutos desde que un proceso se encuentra en un estado hasta el tiempo actual
     * @param radFaseEst
     * @return
     * @throws MarinerPersistanceException 
     */
    public int obtenerMinutosActualesRadFase(MarRadicacionesFasesEstados radFaseEst) throws MarinerPersistanceException;
    
    /**
     * Obtiene las radicaciones-fases-estados cuya ultima fase sea la ingresada y para un usuario específico si se envía
     * @param fase El código de la fase, si quiere varias fases, escríbalas con coma y entre comillas simples ej: 'I-P','G-P'
     * @param usuario
     * @return
     * @throws MarinerPersistanceException 
     */
    public List<MarRadicacionesFasesEstados> obtenerPorUltimaFaseUsuario(String fase, MarUsuarios usuario) throws MarinerPersistanceException;
    
    /**
     * Obtiene las radicaciones-fases-estados cuya ultima fase sea la ingresada, por fechas y si ya está impreso o no
     * @param fase
     * @param fechaInicial
     * @param fechaFinal
     * @param fueImpreso
     * @return 
     */
    public List<MarRadicacionesFasesEstados> obtenerUltimaFaseFechaImpreso(String fase, Date fechaInicial, Date fechaFinal, boolean fueImpreso);
    
    
}
