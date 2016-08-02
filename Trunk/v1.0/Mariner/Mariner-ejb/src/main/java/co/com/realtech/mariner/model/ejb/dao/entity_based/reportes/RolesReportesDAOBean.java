package co.com.realtech.mariner.model.ejb.dao.entity_based.reportes;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRolesReportes;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author fabianagudelo
 */
@Stateless
public class RolesReportesDAOBean extends GenericDAOBean implements RolesReportesDAOBeanLocal {

    @Override
    public List<MarRolesReportes> obtenerReportesPorUsuario(MarUsuarios usuario) throws MarinerPersistanceException {
        List<MarRolesReportes> reportes = null;
        try {
            String query = "SELECT DISTINCT rr.* \n"
                    + "FROM mar_roles_reportes rr \n"
                    + "INNER JOIN mar_roles ro ON rr.rol_id = ro.rol_id\n"
                    + "INNER JOIN mar_roles_usuarios ur ON ro.rol_id = ur.rol_id\n"
                    + "INNER JOIN mar_reportes r ON rr.rep_id = r.rep_id\n"
                    + "INNER JOIN mar_reportes_tipos rt ON r.rti_id = rt.rti_id\n"
                    + "WHERE rt.rti_codigo = 'EST_01'\n"
                    + "AND ur.usu_id = :usrId ";
            query = query.replace(":usrId", usuario.getUsuId().toString());
            Query q = getEntityManager().createNativeQuery(query,MarRolesReportes.class);
            reportes = (List<MarRolesReportes>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return reportes;
    }

}
