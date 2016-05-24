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
    public List<MarRadicacionesFasesEstados> obtenerRadicFaseEstDeRadyFase(MarRadicaciones radicacion, String faseEstado) throws MarinerPersistanceException{
        List<MarRadicacionesFasesEstados> radicacionesFasesEstados = null;
        try {
            Query q = getEntityManager().createQuery("FROM MarRadicacionesFasesEstados rfe WHERE rfe.radId = :radId AND rfe.fesId.fesCodigo = :faseEstado ORDER BY rfe.rfeId");
            q.setParameter("radId", radicacion);
            q.setParameter("faseEstado", faseEstado);
            radicacionesFasesEstados = (List<MarRadicacionesFasesEstados>)q.getResultList();
        } catch (NoResultException e){
        } catch (Exception e) {
            throw e;
        }
        return radicacionesFasesEstados;
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
            String sql = "WITH ultimasFases AS (\n"
                    + "  SELECT DISTINCT rfe.rad_id, MAX(rfe.rfe_id) OVER(PARTITION BY rfe.rad_id) AS rfe_id\n"
                    + "  FROM mar_radicaciones r \n"
                    + "  INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id \n"
                    + "  INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                    + "  WHERE rfe.usu_id = :usuId\n"
                    + "    :WHEREFASE\n"
                    + "    AND TRUNC(rfe.rfe_fecha_inicio) BETWEEN TO_DATE(':fechaIn','dd-MM-yyyy') AND TO_DATE(':fechaFin','dd-MM-yyyy')\n"
                    + ") SELECT rfe.* FROM mar_radicaciones_fases_estados rfe\n"
                    + "INNER JOIN ultimasFases uf ON rfe.rfe_id = uf.rfe_id\n"
                    + "ORDER BY rfe.rfe_fecha_inicio DESC";
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sql = sql.replace(":fechaIn", sdf.format(fechaIn));
            sql = sql.replace(":fechaFin", sdf.format(fechaFin));
            sql = sql.replace((":usuId"), usuario.getUsuId().toString());
            if(faseEstado == null){
                sql = sql.replace(":WHEREFASE", "");
            }else{
                sql = sql.replace(":WHEREFASE", "AND fe.fes_codigo = '"+ faseEstado +"'");
            }
            System.out.println("sql = " + sql);
            Query q = getEntityManager().createNativeQuery(sql, MarRadicacionesFasesEstados.class);
            radicacionesLibres = (List<MarRadicacionesFasesEstados>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesLibres;
    }
    
    /**
     * Obtiene la última fase en la que se encuentra una radicación.
     * @param radicacion
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public MarRadicacionesFasesEstados obtenerUltimaFaseDeRadicacion(MarRadicaciones radicacion) throws MarinerPersistanceException{
        MarRadicacionesFasesEstados radFaseEstado = null;
        try {
            String sql = "SELECT * FROM (\n"
                    + "  SELECT * FROM mar_radicaciones_fases_estados \n"
                    + "  WHERE rad_id = :radId\n"
                    + "  ORDER BY rfe_id DESC\n"
                    + ") WHERE ROWNUM < 2";
            sql = sql.replace(":radId", radicacion.getRadId().toString());
            Query q = getEntityManager().createNativeQuery(sql, MarRadicacionesFasesEstados.class);
            q.setMaxResults(1);
            radFaseEstado = (MarRadicacionesFasesEstados)q.getSingleResult();
        } catch (Exception e) {
            throw e;
        }
        return radFaseEstado;
    }

    /**
     * Obtiene las radicaciones cuya ultima fase se encuentre en las ingresadas y siempre y cuando esté (A)ctiva
     * @param fases
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicacionesFasesEstados> obtenerPendientesConCodigos(String fases) throws MarinerPersistanceException {
        List<MarRadicacionesFasesEstados> rads = null;
        try {
            String sql = "WITH ultEstado AS \n"
                    + "(\n"
                    + "SELECT DISTINCT r.rad_id, MAX(rfe.rfe_id) OVER(PARTITION BY r.rad_id) AS rfe_id\n"
                    + "FROM mar_radicaciones r \n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id\n"
                    + "WHERE r.rad_estado = 'A'\n"
                    + ") \n"
                    + "SELECT rfe.* FROM mar_radicaciones_fases_estados rfe \n"
                    + "INNER JOIN ultEstado u ON rfe.rfe_id = u.rfe_id\n"
                    + "INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                    + "WHERE fe.fes_codigo IN (%ESTADOS%)";
            sql = sql.replace("%ESTADOS%", fases);
            Query q = getEntityManager().createNativeQuery(sql,MarRadicacionesFasesEstados.class);
            rads = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return rads;
    }

}
