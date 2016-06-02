package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.math.BigDecimal;
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
    
        @Override
    public List<MarRadicacionesFasesEstados> obtenerRadicFasesEstadosPorUsuarioFaseEstadoYFechas(MarUsuarios usuario, String faseEstado, Date fechaIn, Date fechaFin) throws MarinerPersistanceException {
        List<MarRadicacionesFasesEstados> radicacionesLibres = new ArrayList<>();
        try {
            String sql = "WITH procesosInvolucrados AS\n"
                    + "(\n"
                    + "  SELECT DISTINCT r.rad_id FROM mar_radicaciones r\n"
                    + "  INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id\n"
                    + "  WHERE rfe.usu_id = :usuId\n"
                    + "), ultimasFases AS (\n"
                    + "  SELECT DISTINCT rfe.rad_id, MAX(rfe.rfe_id) OVER(PARTITION BY rfe.rad_id) AS rfe_id\n"
                    + "  FROM procesosInvolucrados r \n"
                    + "  INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id\n"
                    + "    \n"
                    + ") SELECT rfe.* \n"
                    + "FROM mar_radicaciones_fases_estados rfe\n"
                    + "INNER JOIN ultimasFases uf ON rfe.rfe_id = uf.rfe_id\n"
                    + "INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                    + "INNER JOIN mar_radicaciones r ON rfe.rad_id = r.rad_id\n"
                    + "AND TRUNC(rfe.rfe_fecha_inicio) BETWEEN TO_DATE(':fechaIn','dd-MM-yyyy') AND TO_DATE(':fechaFin','dd-MM-yyyy')\n"
                    + ":WHEREFASE\n"
                    + "ORDER BY rfe.rfe_fecha_inicio DESC";
            if (faseEstado != null && !faseEstado.contains("'")) {
                faseEstado = "'" + faseEstado + "'";
            }
            sql = sql.replace((":usuId"), usuario.getUsuId().toString());
            if(fechaIn == null){
                sql = sql.replace("AND TRUNC(rfe.rfe_fecha_inicio) BETWEEN TO_DATE(':fechaIn','dd-MM-yyyy') AND TO_DATE(':fechaFin','dd-MM-yyyy')", "");
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sql = sql.replace(":fechaIn", sdf.format(fechaIn));
                sql = sql.replace(":fechaFin", sdf.format(fechaFin));
            }
            if (faseEstado == null) {
                sql = sql.replace(":WHEREFASE", "");
            } else {
                sql = sql.replace(":WHEREFASE", "AND fe.fes_codigo IN (" + faseEstado + ")");
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
                    + "WHERE fe.fes_codigo IN (%ESTADOS%)\n"
                    + "ORDER BY rfe.rfe_id ASC";
            sql = sql.replace("%ESTADOS%", fases);
            Query q = getEntityManager().createNativeQuery(sql,MarRadicacionesFasesEstados.class);
            rads = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return rads;
    }
    
    
    @Override
    public int obtenerMinutosActualesRadFase(MarRadicacionesFasesEstados radFaseEst) throws MarinerPersistanceException{
        int mins = 0;
        try {
            String sql = "SELECT ROUND((SYSDATE - rfe_fecha_inicio) * 24 * 60,0) AS diferencia\n"
                        + "FROM mar_radicaciones_fases_estados\n"
                        + "WHERE rfe_id = %RADFASEEST%";
            sql = sql.replace("%RADFASEEST%", radFaseEst.getRfeId().toString());
            Query q = getEntityManager().createNativeQuery(sql);
            q.setMaxResults(1);
            mins = ((BigDecimal)q.getSingleResult()).intValue();
        } catch (Exception e) {
            throw e;
        }
        return mins;
    }

    @Override
    public List<MarRadicacionesFasesEstados> obtenerPorUltimaFaseUsuario(String fase, MarUsuarios usuario) throws MarinerPersistanceException {
        List<MarRadicacionesFasesEstados> radicacionesLibres = new ArrayList<>();
        try {
            String sql = "WITH maximos AS (\n"
                    + "  SELECT MAX(rfes.rfe_id) AS rfe_id, rfes.rad_id\n"
                    + "  FROM mar_radicaciones_fases_estados rfes\n"
                    + "  INNER JOIN mar_fases_estados fe ON rfes.fes_id = fe.fes_id\n"
                    + "  INNER JOIN mar_radicaciones r ON rfes.rad_id = r.rad_id\n"
                    + "  WHERE 1 = 1\n"
                    + "    AND r.rad_estado = 'A'\n"
                    + "    GROUP BY rfes.rad_id\n"
                    + ")\n"
                    + "SELECT rfes.* FROM maximos m \n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfes ON m.rfe_id = rfes.rfe_id\n"
                    + "INNER JOIN mar_radicaciones r ON rfes.rad_id = r.rad_id\n"
                    + "INNER JOIN mar_fases_estados fes ON rfes.fes_id = fes.fes_id\n"
                    + "WHERE 2 = 2\n"
                    + "  AND fes.fes_codigo IN (:fase)\n"
                    + "ORDER BY r.rad_fecha";
            if (usuario != null){
                sql = sql.replace("2 = 2", "rfes.usu_id = " + usuario.getUsuId());
            }
            sql = sql.replace(":fase", fase);
            Query q = getEntityManager().createNativeQuery(sql,MarRadicacionesFasesEstados.class);
            radicacionesLibres = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesLibres;
    }
    

}
