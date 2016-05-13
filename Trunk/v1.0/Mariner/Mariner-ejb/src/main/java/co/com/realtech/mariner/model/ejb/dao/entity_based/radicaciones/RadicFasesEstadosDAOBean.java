package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Bean de sessión destinado a todas las consultas que se hagan a las fases y los estados
 * de las radicaciones.
 * @author fabianagudelo
 */
@Stateless
public class RadicFasesEstadosDAOBean extends GenericDAOBean implements RadicFasesEstadosDAOBeanLocal {
    
    /**
     * Dada una radicación y un estado de fase, retorna si hay una radicacion-fase-estado.
     * @param radicacion
     * @param faseEstado
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public MarRadicacionesFasesEstados obtenerRadicFaseEstDeRadyFase(MarRadicaciones radicacion, String faseEstado) throws MarinerPersistanceException{
        MarRadicacionesFasesEstados radicacionFaseEstado = null;
        try {
            Query q = getEntityManager().createQuery("FROM MarRadicacionesFasesEstados rfe WHERE rfe.radId = :radId AND rfe.fesId.fesCodigo = :faseEstado");
            q.setParameter("radId", radicacion);
            q.setParameter("faseEstado", faseEstado);
            q.setMaxResults(1);
            radicacionFaseEstado = (MarRadicacionesFasesEstados)q.getSingleResult();
        } catch (NoResultException e){
        } catch (Exception e) {
            throw e;
        }
        return radicacionFaseEstado;
    }

}
