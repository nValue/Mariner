package co.com.realtech.mariner.model.ejb.dao.entity_based.notarias;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarNotarias;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean de sesión encargado de obtener las notarías
 * @author fabianagudelo
 */
@Stateless(name = "NotariasDAOBean")
public class NotariasDAOBean extends GenericDAOBean implements NotariasDAOBeanLocal {

    /**
     * Obtiene las notarías a las que tiene permiso un usuario.
     * @param usuarioActual
     * @return
     * @throws MarinerPersistanceException 
     */
    @Override
    public List<MarNotarias> obtenerNotariasDeUsuario(MarUsuarios usuarioActual) throws MarinerPersistanceException{
        List<MarNotarias> notarias = new ArrayList<>();
        try {
            String sql = "SELECT n.* FROM mar_usuarios u \n" +
                        " LEFT JOIN mar_notarias n ON u.not_id = n.not_id\n" +
                        " WHERE u.usu_id = " + usuarioActual.getUsuId() +
                        " ORDER BY n.not_nombre";
            Query q = getEntityManager().createNativeQuery(sql,MarNotarias.class);
            notarias = (List<MarNotarias>)q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return notarias;
    }
    
}
