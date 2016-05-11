package co.com.realtech.mariner.model.ejb.dao.entity_based.usuarios;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean encargado de las consultas que se le realizan a la entidad MarUsuarios
 * @author fabianagudelo
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
            String queryText = "SELECT * FROM mar_usuarios U\n" +
                                "INNER JOIN mar_personas P ON U.PER_ID=P.PER_ID\n" +
                                "WHERE (UPPER(U.USU_LOGIN) LIKE UPPER('%!PARAM%')\n" +
                                "	OR UPPER(p.PER_DOCUMENTO) LIKE UPPER('%!PARAM%')\n" +
                                "	OR UPPER(P.PER_NOMBRES) LIKE UPPER('%!PARAM%')\n" +
                                "	OR UPPER(P.PER_APELLIDOS) LIKE UPPER('%!PARAM%')\n" +
                                "	OR UPPER(P.PER_EMAIL) LIKE UPPER('%!PARAM%')\n" +
                                "	OR UPPER(P.PER_NOMBRES||' '||P.PER_APELLIDOS) LIKE UPPER('!PARAM%'))";
            queryText = queryText.replaceAll("!PARAM", filterValue.trim());
            Query q = getEntityManager().createNativeQuery(queryText, MarUsuarios.class);
            usuarios = (List<MarUsuarios>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return usuarios;
    }
    
}
