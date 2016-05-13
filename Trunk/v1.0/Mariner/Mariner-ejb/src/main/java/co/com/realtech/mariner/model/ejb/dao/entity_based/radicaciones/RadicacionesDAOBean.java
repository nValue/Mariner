package co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean de sessión encargado de las transacciones realizadas a las radicaciones
 * @author fabianagudelo
 */
@Stateless
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
    
    
}
