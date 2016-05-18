package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean de sessión encargado de las transacciones realizadas a las radicaciones, fases y estados
 * @author fabianagudelo
 */
@Stateless(name = "RadicacionesDAOBean")
public class RadicacionesDAOBean extends GenericDAOBean implements RadicacionesDAOBeanLocal {
    
    /**
     * Obtiene las radicaciones asociadas a cualquier dato conocido como número, cus, codigo_acto
     * o número de escritura, si viene el usuario también se tiene en cuenta, sino no.
     * @param filtro
     * @param usuarioActual
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadicacionesPorFiltro(String filtro, MarUsuarios usuarioActual) throws MarinerPersistanceException{
        List<MarRadicaciones> radicacionesFiltradas = new ArrayList<>();
        try {
            String sql = "SELECT r.*\n" +
                        "FROM mar_radicaciones r\n" +
                        "INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id\n" +
                        "INNER JOIN mar_escrituras e ON r.esc_id = e.esc_id\n" +
                        "WHERE 1 = 1 AND (r.rad_numero LIKE('%=VALOR=%')\n" +
                        "OR r.rad_cus LIKE ('%=VALOR=%')\n" +
                        "OR r.rad_codigo_acto LIKE ('%=VALOR=%')\n" +
                        "OR e.esc_numero LIKE ('%=VALOR=%') ) \n";
            if(usuarioActual != null){
                sql = sql + " AND rfe.usu_id = " + usuarioActual.getUsuId();
            }
            sql = sql.replaceAll("=VALOR=", filtro);
            Query q = getEntityManager().createNativeQuery(sql,MarRadicaciones.class);
            radicacionesFiltradas = (List<MarRadicaciones>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesFiltradas;
    }
    
    /**
     * Obtiene las radicaciones cuya ultima fase sea la ingresada y para un usuario específico si se envía
     * @param fase
     * @param usuario
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadicacionesPorUltimaFase(String fase, MarUsuarios usuario) throws MarinerPersistanceException{
        List<MarRadicaciones> radicacionesLibres = new ArrayList<>();
        try {
            String sql = "WITH maximos AS ( \n"
                    + "   SELECT MAX(rfes.rfe_id) AS rfe_id, rfes.rad_id\n"
                    + "    FROM mar_radicaciones_fases_estados rfes\n"
                    + "    INNER JOIN mar_fases_estados fe ON rfes.fes_id = fe.fes_id\n"
                    + "    %WHERE%\n"
                    + "    GROUP BY rfes.rad_id\n"
                    + ")\n"
                    + "SELECT r.* FROM mar_radicaciones r \n"
                    + "INNER JOIN maximos m ON r.rad_id = m.rad_id\n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfes ON m.rfe_id = rfes.rfe_id\n"
                    + "INNER JOIN mar_fases_estados fes ON rfes.fes_id = fes.fes_id\n"
                    + "AND fes.fes_codigo = :fase\n"
                    + "ORDER BY r.rad_fecha";
            if(usuario == null){
                sql = sql.replace("%WHERE%", "");
            }else{
                sql = sql.replace("%WHERE%", "WHERE rfes.usu_id = " + usuario.getUsuId());
            }
            Query q = getEntityManager().createNativeQuery(sql,MarRadicaciones.class);
            q.setParameter("fase", fase);
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
            Query q = getEntityManager().createQuery("SELECT DISTINCT r FROM MarRadicaciones r INNER JOIN r.marRadicacionesFasesEstadosList rfe WHERE rfe.usuId = :usuId AND rfe.fesId.fasId.fasCodigo = :fase");
            q.setParameter("usuId", usuario);
            q.setParameter("fase", fase);
            radicacionesLibres = (List<MarRadicaciones>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesLibres;
    }
    
    
    /**
     * Obtiene las radicaciones para el usuario con la Fase-Estado
     * @param usuario
     * @param faseEstado
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadicacionesPorUsuarioYFaseEstado(MarUsuarios usuario, String faseEstado) throws MarinerPersistanceException{
        List<MarRadicaciones> radicacionesLibres = new ArrayList<>();
        try {
            Query q = getEntityManager().createQuery("SELECT DISTINCT r FROM MarRadicaciones r INNER JOIN r.marRadicacionesFasesEstadosList rfe WHERE rfe.usuId = :usuId AND rfe.fesId.fesCodigo = :faseEstado");
            q.setParameter("usuId", usuario);
            q.setParameter("faseEstado", faseEstado);
            radicacionesLibres = (List<MarRadicaciones>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicacionesLibres;
    }
    
    
    
}
