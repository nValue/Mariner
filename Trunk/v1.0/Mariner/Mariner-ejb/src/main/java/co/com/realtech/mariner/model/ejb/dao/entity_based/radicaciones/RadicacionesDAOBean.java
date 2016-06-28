package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesAgrupamientos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import co.com.realtech.mariner.util.string.BusinessStringUtils;
import java.math.BigDecimal;
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
    
    @Override
    public List<MarRadicaciones> obtenerRadicacionesPorUltimaFase(String fase, MarUsuarios usuario) throws MarinerPersistanceException{
        List<MarRadicaciones> radicacionesLibres = new ArrayList<>();
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
                    + "SELECT r.* FROM maximos m \n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfes ON m.rfe_id = rfes.rfe_id\n"
                    + "INNER JOIN mar_radicaciones r ON rfes.rad_id = r.rad_id\n"
                    + "INNER JOIN mar_fases_estados fes ON rfes.fes_id = fes.fes_id\n"
                    + "INNER JOIN mar_notarias n ON r.not_id = n.not_id\n"
                    + "WHERE 2 = 2\n"
                    + "  AND (fes.fes_codigo IN (:fase) AND 3 = 3)\n"
                    + "ORDER BY fes.fes_orden, r.rad_id";
            if (usuario != null){
                sql = sql.replace("2 = 2", "rfes.usu_id = " + usuario.getUsuId());
            }
            sql = sql.replace(":fase", fase);
            //Validación necesaria para agregar los pendientes por imprimir de la gobernación.
            if(fase.contains("R-A")){
                sql = sql.replace("R-A", "NADA");
                sql = sql.replace("AND 3 = 3", "OR (fes.fes_codigo = 'R-A' AND n.not_es_gobernacion = 'S' AND NVL(r.rad_es_impresion,'N') != 'S')");
            }
            System.out.println("sql = " + sql);
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
                        + "  AND 4 = 4";
            if (tipo.equals("ES")) {
                String valor = "";
                sql = sql.replace("4 = 4", " TRUNC(rfe.rfe_fecha_inicio) BETWEEN TO_DATE('FECHA1','dd/MM/yyyy') AND TO_DATE('FECHA2','dd/MM/yyyy')");
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
                sql = sql.replace("1 = 1", " r.rad_liquidacion = '" + BusinessStringUtils.convertNumeroLiquidacion(campoBusqueda) + "'");
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
    
    
    @Override
    public boolean esTurnoValido(String turno) throws MarinerPersistanceException{
        int cant = 0;
        try {
            String sql = "SELECT COUNT(*) FROM mar_radicaciones \n"
                    + "WHERE 1 = 1\n"
                    + "AND rad_turno = '%TURNO%'\n"
                    + "AND TRUNC(rad_fecha) = TRUNC(SYSDATE)";
            sql = sql.replace("%TURNO%", turno);
            Query q = getEntityManager().createNativeQuery(sql);
            q.setMaxResults(1);
            cant = ((BigDecimal)q.getSingleResult()).intValue();
        } catch (Exception e) {
            throw e;
        }
        return (cant == 0);
    }
    
    @Override
    public List<MarRadicaciones> obtenerRadicacionesActivasPorAgrupacion(MarRadicacionesAgrupamientos radAgrup) throws MarinerPersistanceException{
        List<MarRadicaciones> radicaciones = null;
        try {
            Query q = getEntityManager().createQuery("FROM MarRadicaciones r WHERE r.raaId = :raaId AND r.radEstado = 'A'");
            q.setParameter("raaId", radAgrup);
            radicaciones = (List<MarRadicaciones>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicaciones;
    }
    
}
