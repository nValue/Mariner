package co.com.realtech.mariner.model.ejb.dao.procedure_based;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import javax.ejb.Stateless;

/**
 * EJB encargado de funcionalidades basadas en PLSQL
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@Stateless
public class ProcedureBasedBean extends GenericDAOBean implements ProcedureBasedBeanLocal {

    /**
     * Retorna el valor de la constante ingresada.
     *
     * @param llave
     * @return
     */
    @Override
    public String cargarConstante(String llave) {
        String valor;
        try {
            valor=callGenericFunction("FN_CONSULTAR_CONSTANTE", llave).toString();
        } catch (Exception e) {
            valor = "";
        }
        return valor;
    }

}
