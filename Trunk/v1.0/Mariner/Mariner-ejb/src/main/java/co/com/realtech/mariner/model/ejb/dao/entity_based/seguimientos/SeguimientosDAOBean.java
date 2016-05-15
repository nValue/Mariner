package co.com.realtech.mariner.model.ejb.dao.entity_based.seguimientos;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.NvaSeguimiento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * EJB encargado de consultar a la entidad de seguimientos del sistema.
 *
 * @author Andres Rivera
 * @version 1.0
 */
@Stateless
public class SeguimientosDAOBean extends GenericDAOBean implements SeguimientosDAOBeanLocal {

    /**
     * Retorna los seguimientos vinculados a la consulta.
     *
     * @param fechaInicial
     * @param fechaFinal
     * @param texto
     * @return
     * @throws Exception
     */
    @Override
    public List<NvaSeguimiento> filtradoSeguimientos(Date fechaInicial, Date fechaFinal, String texto) throws Exception {
        List<NvaSeguimiento> filtros = new ArrayList<>();
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            Query q = getEntityManager().createQuery("from NvaSeguimiento as n where date_trunc('day',n.audFecha) between to_date(:fechaInicial,'DD/MM/YYYY') and to_date(:fechaFinal,'DD/MM/YYYY') and UPPER(n.segClave) LIKE :filtrado order by n.segId desc");
            q.setParameter("fechaInicial", sdf.format(fechaInicial));
            q.setParameter("fechaFinal", sdf.format(fechaFinal));
            q.setParameter("filtrado", "%" + texto.toUpperCase() + "%");
            filtros = (List<NvaSeguimiento>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return filtros;
    }

}
