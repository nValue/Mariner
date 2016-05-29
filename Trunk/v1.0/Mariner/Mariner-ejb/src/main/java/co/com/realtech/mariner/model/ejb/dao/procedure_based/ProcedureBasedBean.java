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
     * @param radId
     * @return 
     */
    @Override
    public String vincularArchivoSAP(BigDecimal radId) {
        String salida;
        try {
            salida = callGenericFunction("PKG_VUR_NEGOCIO.FN_VINCULAR_ARCHIVOS_RAD", radId).toString();
        } catch (Exception e) {
            salida = "ERROR: No se pudo vincular el archivo SAP, error interno " + e;
        }
        return salida;
    }

}
