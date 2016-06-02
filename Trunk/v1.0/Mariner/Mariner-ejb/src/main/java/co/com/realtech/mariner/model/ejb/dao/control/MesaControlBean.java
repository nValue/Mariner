package co.com.realtech.mariner.model.ejb.dao.control;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * EJB encargado de toda la funcionalidad de persistencia de la mesa de control.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@Stateless
public class MesaControlBean extends GenericDAOBean implements MesaControlBeanLocal {

    /**
     * Filtrado de radicaciones para mesa de control.
     *
     * @param tipoFiltro
     * @param valor
     * @param tipoDato
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    @Override
    public List<MarRadicaciones> filtrarRadicacionesMesaControl(String tipoFiltro, String valor, String tipoDato, Date fechaInicial, Date fechaFinal) {
        List<MarRadicaciones> radicaciones = new ArrayList<>();
        try {
            String query = "";

            if (tipoFiltro.equals("TODOS")) {
                query = "from MarRadicaciones as m where ";
            } else {
                query = "from MarRadicaciones as m where m.";
                if (tipoFiltro.equalsIgnoreCase("RAD-NUMERO")) {
                    query += "radNumero=:valor";
                } else if (tipoFiltro.equalsIgnoreCase("RAD-LIQUIDACION")) {
                    query += "radLiquidacion=:valor";
                } else if (tipoFiltro.equalsIgnoreCase("RAD-TURNO")) {
                    query += "radTurno=:valor";
                } else {
                    query += "escId.escNumero=:valor";
                }
            }

            query += (tipoFiltro.equals("TODOS") ? "" : " and ") + " trunc(m.radFecha) between trunc(:inicio) and trunc(:fin)";
            query += " order by m.radId desc";

            Query q = getEntityManager().createQuery(query);

            if (!tipoFiltro.equals("TODOS")) {
                if (tipoDato.equalsIgnoreCase("TEXT")) {
                    q.setParameter("valor", valor);
                } else {
                    q.setParameter("valor", new BigDecimal(valor));
                }
            }
            q.setParameter("inicio", fechaInicial);
            q.setParameter("fin", fechaFinal);
            radicaciones = (List<MarRadicaciones>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return radicaciones;
    }

}
