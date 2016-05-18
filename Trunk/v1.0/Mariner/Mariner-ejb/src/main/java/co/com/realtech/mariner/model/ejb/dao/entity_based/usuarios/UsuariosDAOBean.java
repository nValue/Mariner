package co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarUsuarios;
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
    public boolean validacionCreacionusuario(MarUsuarios usuario, MarPersonas persona) throws Exception {
        try {
            Query q = getEntityManager().createQuery("from MarUsuarios as u where upper(u.usuLogin)=upper(:usuLogin)");
            q.setParameter("usuLogin", usuario.getUsuLogin());
            List<MarUsuarios> usuarios = (List<MarUsuarios>) q.getResultList();

            if (usuarios.isEmpty()) {
                return true;
            } else {
                q = getEntityManager().createQuery("from MarUsuarios as u where (u.perId.tdcId=:tdcId and u.perId.perDocumento=:perDocumento) or upper(u.perId.perEmail)=upper(:perEmail)");
                q.setParameter("tdcId", persona.getTdcId());
                q.setParameter("perDocumento", persona.getPerDocumento());
                q.setParameter("perEmail", persona.getPerEmail());
                usuarios = (List<MarUsuarios>) q.getResultList();
                return usuarios.isEmpty();
            }
        } catch (Exception e) {
            return false;
        }
    }

}
