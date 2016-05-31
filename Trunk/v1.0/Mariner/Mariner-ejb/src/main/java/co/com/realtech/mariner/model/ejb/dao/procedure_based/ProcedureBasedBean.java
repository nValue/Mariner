package co.com.realtech.mariner.model.ejb.dao.procedure_based;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.Query;

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
            valor = callGenericFunction("PKG_VUR_NEGOCIO.FN_CONSULTAR_CONSTANTE", llave).toString();
        } catch (Exception e) {
            valor = "";
        }
        return valor;
    }

    /**
     * Vincular archivos SAP, llamado a funcion.
     *
     * @param radId
     * @throws java.lang.Exception
     */
    @Override
    public void vincularArchivoSAP(BigDecimal radId) throws Exception {
        try {
            callGenericProcedure("PKG_VUR_NEGOCIO.PL_VINCULAR_ARCHIVOS_RAD", radId);
        } catch (Exception e) {
            throw e;
        }
    }
}
