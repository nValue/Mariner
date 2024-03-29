package co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean encargado de las consultas que se le realizan a la entidad MarUsuarios
 *
 * @author fabianagudelo
 * @version 1.0
 * @since JDK1.9.91
 */
@Stateless
public class UsuariosDAOBean extends GenericDAOBean implements UsuariosDAOBeanLocal {

    /**
     * Retorna los usuarios asociados filtrado por cadena de caracteres.
     *
     * @param filterValue
     * @param usuarioSession
     * @return
     * @throws Exception
     */
    @Override
    public List<MarUsuarios> loadUserFiltered(String filterValue, MarUsuarios usuarioSession) throws Exception {
        List<MarUsuarios> usuarios = new ArrayList<>();
        try {
            String queryText = "SELECT * FROM mar_usuarios U\n"
                    + "INNER JOIN mar_personas P ON U.PER_ID=P.PER_ID\n"
                    + "WHERE (UPPER(U.USU_LOGIN) LIKE UPPER('%!PARAM%')\n"
                    + "	OR UPPER(p.PER_DOCUMENTO) LIKE UPPER('%!PARAM%')\n"
                    + "	OR UPPER(P.PER_NOMBRES) LIKE UPPER('%!PARAM%')\n"
                    + "	OR UPPER(P.PER_APELLIDOS) LIKE UPPER('%!PARAM%')\n"
                    + "	OR UPPER(P.PER_EMAIL) LIKE UPPER('%!PARAM%')\n"
                    + "	OR UPPER(P.PER_NOMBRES||' '||P.PER_APELLIDOS) LIKE UPPER('!PARAM%'))";
            queryText = queryText.replaceAll("!PARAM", filterValue.trim());
            Query q = getEntityManager().createNativeQuery(queryText, MarUsuarios.class);
            usuarios = (List<MarUsuarios>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return usuarios;
    }

    /**
     * Retorna true, si el usuario se puede crear en la base de datos.
     *
     * @param usuario
     * @param persona
     * @return
     * @throws Exception
     */
    @Override
    public boolean validacionCreacionUsuario(MarUsuarios usuario, MarPersonas persona) throws Exception {
        boolean validacion = false;
        try {
            Query q = getEntityManager().createQuery("from MarUsuarios as u where upper(u.usuLogin)=:usuLogin");
            System.out.println("USUARIO:" + usuario.getUsuLogin());
            q.setParameter("usuLogin", usuario.getUsuLogin().toUpperCase().trim());
            List<MarUsuarios> usuarios = (List<MarUsuarios>) q.getResultList();

            if (usuarios.isEmpty()) {
                validacion = true;
            }

            q = getEntityManager().createQuery("from MarUsuarios as u where (u.perId.tdcId=:tdcId and u.perId.perDocumento=:perDocumento) or upper(u.perId.perEmail)=upper(:perEmail)");
            q.setParameter("tdcId", persona.getTdcId());
            q.setParameter("perDocumento", persona.getPerDocumento());
            q.setParameter("perEmail", persona.getPerEmail());
            usuarios = (List<MarUsuarios>) q.getResultList();

            if (validacion) {
                validacion = usuarios.isEmpty();
            }
        } catch (Exception e) {
            return false;
        }
        return validacion;
    }
    
    
    @Override
    public List<MarUsuarios> obtenerAsociadosAModulo(String labor) throws MarinerPersistanceException {
        List<MarUsuarios> usuarios = null;
        try {
            String sql = "SELECT * FROM mar_usuarios WHERE ";
            switch (labor) {
            case "LIQ":
                sql = sql + " usu_es_liquidador = 'S'\n";
                break;
            case "APR":
                sql = sql + " usu_es_aprobador = 'S'\n";
                break;
            case "NOT":
                sql = sql + " usu_es_notario = 'S'\n";
                break;
            default:
                return usuarios;
        }
            sql = sql + " ORDER BY mor_id, usu_login";
            Query q = getEntityManager().createNativeQuery(sql,MarUsuarios.class);
            usuarios = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return usuarios;
    }
    
    /**
     * Obtiene la cantidad de radicaciones que tiene pendiente un usuario, para una fase
     * @param usuario
     * @param fase
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public int obtenerCantPendientesDeUsuario(MarUsuarios usuario, String fase) throws MarinerPersistanceException{
        int cant = 0;
        try {
            String sql = "WITH maxs AS \n"
                    + "(\n"
                    + "  SELECT r.rad_id, MAX(rfe.rfe_id) AS rfe_id\n"
                    + "  FROM mar_radicaciones r \n"
                    + "  INNER JOIN mar_radicaciones_fases_estados rfe ON r.rad_id = rfe.rad_id\n"
                    + "  WHERE r.rad_estado = 'A'\n"
                    + "  GROUP BY r.rad_id\n"
                    + ") SELECT NVL(MAX(COUNT(*)),0) AS total\n"
                    + "FROM maxs m\n"
                    + "INNER JOIN mar_radicaciones_fases_estados rfe ON m.rfe_id = rfe.rfe_id\n"
                    + "INNER JOIN mar_fases_estados fe ON rfe.fes_id = fe.fes_id\n"
                    + "WHERE fe.fes_codigo IN (%FASES%)\n"
                    + "  AND rfe.usu_id = %USUARIO%\n"
                    + "GROUP BY rfe.usu_id";
            sql = sql.replace("%USUARIO%", usuario.getUsuId().toString());
            sql = sql.replace("%FASES%", fase);
            System.out.println("sql = " + sql);
            Query q = getEntityManager().createNativeQuery(sql);
            q.setMaxResults(1);
            cant = ((BigDecimal)q.getSingleResult()).intValue();
        } catch (Exception e) {
            throw e;
        }
        return cant;
    }
    
    /**
     * Obtiene el primer usuario que tenga asociado ese correo.
     * @param email
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public MarUsuarios obtenerUsuarioPorCorreo(String email) throws MarinerPersistanceException{
        MarUsuarios usuario = null;
        try {
            String sql = "SELECT u.* \n" +
                        "FROM mar_usuarios u\n" +
                        "INNER JOIN mar_personas p ON u.per_id = p.per_id\n" +
                        "WHERE p.per_email = '" + email + "'";
            Query q = getEntityManager().createNativeQuery(sql,MarUsuarios.class);
            q.setMaxResults(1);
            List<MarUsuarios> usuarios = q.getResultList();
            if(!usuarios.isEmpty()){
                usuario = usuarios.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }

}
