package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                        "INNER JOIN mar_transacciones t ON r.rad_id = t.rad_id\n" +
                        "WHERE 1 = 1 AND (r.rad_numero LIKE('%=VALOR=%')\n" +
                        "OR t.tra_cus LIKE ('%=VALOR=%')\n" +
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
     * @param fase El código de la fase, si quiere varias fases, escríbalas con coma y entre comillas simples ej: 'I-P','G-P' 
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
                    + "AND fes.fes_codigo IN (:fase)\n"
                    + "ORDER BY r.rad_fecha";
            if(usuario == null){
                sql = sql.replace("%WHERE%", "");
            }else{
                sql = sql.replace("%WHERE%", "WHERE rfes.usu_id = " + usuario.getUsuId());
            }
            sql = sql.replace(":fase", fase);
            Query q = getEntityManager().createNativeQuery(sql,MarRadicaciones.class);
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
    
    
    /**
     * Obtiene las radicaciones que hayan llegado al estado de finalización por fechas y por tipo de búsqueda,
     * el usuario es opcional
     * @param tipo
     * @param campoBusqueda
     * @param fechaInicial
     * @param fechaFinal
     * @param usuario
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadicacionesFinalizacionPorFechasYParametro(String tipo, String campoBusqueda, Date fechaInicial, Date fechaFinal, MarUsuarios usuario) throws MarinerPersistanceException {
        List<MarRadicaciones> radicaciones = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String sql = "WITH maxs AS (\n"
                        + "SELECT DISTINCT r.*, MAX(rfe.rfe_id) OVER (PARTITION BY r.rad_id) AS maximo \n"
                        + "FROM mar_radicaciones r\n"
                        + "INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id \n"
                        + "WHERE 3 = 3\n"
                        + "ORDER BY r.rad_id)\n"
                        + "SELECT * FROM mar_radicaciones_fases_estados rfe \n"
                        + "INNER JOIN maxs r ON r.maximo = rfe.rfe_id\n"
                        + "INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                        + "WHERE 1 = 1 \n"
                        + "  AND fe.fes_codigo IN ('P-A','F-A','F-R')\n"
                        + "  AND TRUNC(rfe.rfe_fecha_inicio) BETWEEN TO_DATE('FECHA1','dd/MM/yyyy') AND TO_DATE('FECHA2','dd/MM/yyyy')";
            if (tipo.equals("ES")) {
                String valor = "";
                sql = sql.replace("AND TRUNC(rfe.rfe_fecha_inicio) BETWEEN TO_DATE('FECHA1','dd/MM/yyyy') AND TO_DATE('FECHA2','dd/MM/yyyy')", "");
                switch (campoBusqueda) {
                    case "P":
                        valor = "'P-A'";
                        break;
                    case "A":
                        valor = "'F-A'";
                        break;
                    case "R":
                        valor = "'F-R'";
                        break;
                    default:
                        break;
                }
                sql = sql.replace("1 = 1", " fe.fes_codigo = " + valor);
            }else if(tipo.equals("RA")){
                sql = sql.replace("1 = 1", " r.rad_numero = '" + campoBusqueda + "'");
            }else if(tipo.equals("LI")){
                sql = sql.replace("1 = 1", " r.rad_liquidacion = '" + campoBusqueda + "'");
            }else if(tipo.equals("OT")){
                sql = sql.replace("1 = 1", " r.rad_doc_otorgante = '" + campoBusqueda + "'");
            }else if(tipo.equals("RE")){
                sql = sql.replace("1 = 1", " r.rad_doc_receptor = '" + campoBusqueda + "'");
            }
            sql = sql.replace("FECHA1", sdf.format(fechaInicial));
            sql = sql.replace("FECHA2", sdf.format(fechaFinal));
            if(usuario != null){
                sql = sql.replace("3 = 3", "rfe.usu_id = " + usuario.getUsuId());
            }
            System.out.println("sql = " + sql);
            Query q = getEntityManager().createNativeQuery(sql, MarRadicaciones.class);
            radicaciones = (List<MarRadicaciones>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicaciones;
    }
    
    
    /**
     * Obtiene las radicaciones que atendió un usuario en una o mas fases y cuyo estado o estados finales sean los ingresados.
     * @param usuario
     * @param fasesAtendidas
     * @param fasesFinales
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarRadicaciones> obtenerRadsAtendidasYFaseFinal(MarUsuarios usuario, String fasesAtendidas, String fasesFinales) throws MarinerPersistanceException{
        List<MarRadicaciones> radicaciones = new ArrayList<>();
        try {
            String sql = "WITH delUsuario AS \n"
                    + "(\n"
                    + "  SELECT DISTINCT r.*\n"
                    + "  FROM mar_radicaciones r \n"
                    + "  INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id\n"
                    + "  INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                    + "  WHERE rfe.usu_id = %USUARIO%\n"
                    + "    AND fe.fes_codigo IN (%FASESATENDIDAS%)\n"
                    + "), maximos AS \n"
                    + "( \n"
                    + "SELECT DISTINCT r.*, MAX(rfe.rfe_id) OVER(PARTITION BY r.rad_id) AS rfe_id\n"
                    + "FROM delUsuario r \n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id\n"
                    + ") SELECT * FROM maximos m\n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfe ON m.rfe_id = rfe.rfe_id\n"
                    + "INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                    + "WHERE fe.fes_codigo IN (%FASESULTIMAS%)";
            sql = sql.replace("%USUARIO%", usuario.getUsuId().toString());
            sql = sql.replace("%FASESATENDIDAS%", fasesAtendidas);
            sql = sql.replace("%FASESULTIMAS%", fasesFinales);
            Query q = getEntityManager().createNativeQuery(sql, MarRadicaciones.class);
            radicaciones = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicaciones;
    }
    
    
}
