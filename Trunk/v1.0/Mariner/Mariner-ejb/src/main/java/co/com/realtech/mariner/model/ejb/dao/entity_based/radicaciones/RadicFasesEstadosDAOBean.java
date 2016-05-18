package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    
    
    /**
     * Obtiene las radicaciones-fases-estados asociadas a un usuario, una fase-estado y un rango de fechas
     * @param usuario
     * @param faseEstado
     * @param fechaIn
     * @param fechaFin
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicacionesFasesEstados> obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(MarUsuarios usuario, String faseEstado, Date fechaIn, Date fechaFin) throws MarinerPersistanceException{
        List<MarRadicacionesFasesEstados> radicacionesLibres = new ArrayList<>();
        if(fechaIn == null || fechaFin == null){
            return radicacionesLibres;
        }
        try {
            String sql = "SELECT DISTINCT rfe.* \n" +
                        "FROM mar_radicaciones r \n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id \n"
                    + "INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                    + "WHERE rfe.usu_id = :usuId \n"
                    + "AND fe.fes_codigo = ':faseEstado'\n"
                    + "AND TRUNC(rfe.rfe_fecha_inicio) BETWEEN TO_DATE(':fechaIn','dd-MM-yyyy') AND TO_DATE(':fechaFin','dd-MM-yyyy')";
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sql = sql.replace(":fechaIn", sdf.format(fechaIn));
            sql = sql.replace(":fechaFin", sdf.format(fechaFin));
            sql = sql.replace((":usuId"), usuario.getUsuId().toString());
            sql = sql.replace(":faseEstado", faseEstado);
            System.out.println("sql = " + sql);
            Query q = getEntityManager().createNativeQuery(sql, MarRadicacionesFasesEstados.class);
            radicacionesLibres = (List<MarRadicacionesFasesEstados>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesLibres;
    }

}
