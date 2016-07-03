package co.com.realtech.mariner.model.ejb.dao.entity_based.reportes;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Bean encargado de las consultas que se realizan para extraer reportes y demás
 * @author fabianagudelo
 */
@Stateless
public class ReportesDAOBean extends GenericDAOBean implements ReportesDAOBeanLocal {

    
    @Override
    public List<MarReportes> obtenerReportesDeCodigosTipo(String codigos) throws MarinerPersistanceException{
        List<MarReportes> reportes = null;
        try {
            String sql = "SELECT re.* FROM mar_reportes re INNER JOIN mar_reportes_tipos rt ON re.rti_id = rt.rti_id \n" +
                            "WHERE re.rep_estado = 'A' AND rt.rti_codigo IN (%CODIGOS%) ORDER BY re.rep_nombre ";
            sql = sql.replace("%CODIGOS%", codigos);
            System.out.println("sql = " + sql);
            Query q = getEntityManager().createNativeQuery(sql,MarReportes.class);
            reportes = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return reportes;
    }
    
}
