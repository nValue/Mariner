package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean de sessión encargado de las transacciones realizadas a las radicaciones
 * @author fabianagudelo
 */
@Stateless(name = "RadicacionesDAOBean")
public class RadicacionesDAOBean extends GenericDAOBean implements RadicacionesDAOBeanLocal {
    
    /**
     * Obtiene las radicaciones asociadas a cualquier dato conocido como número, cus, codigo_acto
     * o número de escritura.
     * @param filtro
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadicacionesPorFiltro(String filtro) throws MarinerPersistanceException{
        List<MarRadicaciones> radicacionesFiltradas = new ArrayList<>();
        try {
            String sql = "SELECT r.*\n" +
                        "FROM mar_radicaciones r\n" +
                        "INNER JOIN mar_escrituras e ON r.esc_id = e.esc_id\n" +
                        "WHERE r.rad_numero LIKE('%=VALOR=%')\n" +
                        "OR r.rad_cus LIKE ('%=VALOR=%')\n" +
                        "OR r.rad_codigo_acto LIKE ('%=VALOR=%')\n" +
                        "OR e.esc_numero LIKE ('%=VALOR=%')";
            sql = sql.replaceAll("=VALOR=", filtro);
            Query q = getEntityManager().createNativeQuery(sql,MarRadicaciones.class);
            radicacionesFiltradas = (List<MarRadicaciones>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesFiltradas;
    }
    
    /**
     * Obtiene las radicaciones cuya ultima fase sea la ingresada
     * @param fase
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadicacionesPorUltimaFase(String fase) throws MarinerPersistanceException{
        List<MarRadicaciones> radicacionesLibres = new ArrayList<>();
        try {
            String sql = "WITH maximos AS (\n" +
                        "	SELECT MAX(rfes.rfe_id), MAX(fe.fes_codigo) AS codigo, rfes.rad_id \n" +
                        "	FROM mar_radicaciones_fases_estados rfes\n" +
                        "	INNER JOIN mar_fases_estados fe ON rfes.fes_id = fe.fes_id\n" +
                        "	GROUP BY rfes.rad_id\n" +
                        ") SELECT r.* FROM mar_radicaciones r \n" +
                        "INNER JOIN maximos m ON r.rad_id = m.rad_id\n" +
                        "AND m.codigo = :fase\n" +
                        "ORDER BY r.rad_fecha DESC";
            Query q = getEntityManager().createNativeQuery(sql);
            q.setParameter("fesCodigo", fase);
            radicacionesLibres = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesLibres;
    }
    
    /**
     * Obtiene las radicaciones para un usuario y una fase específica
     * @param usuario
     * @param fase
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadicacionesPorUsuarioYFase(MarUsuarios usuario, String fase) throws MarinerPersistanceException{
        List<MarRadicaciones> radicacionesLibres = new ArrayList<>();
        try {
            Query q = getEntityManager().createQuery("FROM MarRadicaciones r WHERE r.marRadicacionesFasesEstadosList.usuId = :usuId AND r.marRadicacionesFasesEstadosList.fesId.fasId.fasCodigo = :fase");
            q.setParameter("usuId", usuario);
            q.setParameter("fase", fase);
            radicacionesLibres = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesLibres;
    }
    
    
    
}
