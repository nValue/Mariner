package co.com.realtech.mariner.util.consecutives;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarNumeraciones;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.naming.InitialContext;

/**
 * Clase encargada de brindar utilidades en el manejo de secuencias para las
 * radicaciones y demás
 *
 * @author Fabián Agudelo
 * @version 1.0
 * @since JDK1.7
 */
public class NumeracionesManager {

    private GenericDAOBeanLocal genericDAOBean;

    public NumeracionesManager() throws Exception {
        try {
            InitialContext ic = new InitialContext();
            genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, true));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Retorna el siguiente consecutivo asociado al tipo de código
     *
     * @param codigoNumeracion
     * @return
     * @throws Exception
     */
    public synchronized String loadNextConsecutive(String codigoNumeracion) throws Exception {
        String consecutive = "";
        try {
            MarNumeraciones numeracion = (MarNumeraciones) genericDAOBean.findByColumn(MarNumeraciones.class, "numCodigo", codigoNumeracion);
            if (numeracion == null) {
                throw new Exception("No hay una numeración asociada al código: " + codigoNumeracion);
            }
            numeracion.setNumNumero(numeracion.getNumNumero().add(BigInteger.ONE));
            if (numeracion.getNumPrefijo() != null) {
                consecutive = numeracion.getNumPrefijo();
            }
            consecutive = consecutive + numeracion.getNumNumero();
            if (numeracion.getNumSufijo() != null) {
                consecutive = consecutive + numeracion.getNumSufijo();
            }
            genericDAOBean.merge(numeracion);
        } catch (Exception e) {
            throw e;
        }
        return consecutive;
    }

    /**
     * Retorna el consecutivo para las radicaciones por ciudad.
     *
     * @param codigoNumeracion
     * @param radicacion
     * @return
     * @throws Exception
     */
    public synchronized String loadNextConsecutiveRad(String codigoNumeracion, MarRadicaciones radicacion) throws Exception {
        String consecutive = "";
        try {
            MarNumeraciones numeracion = (MarNumeraciones) genericDAOBean.findByColumn(MarNumeraciones.class, "numCodigo", codigoNumeracion);
            if (numeracion == null) {
                throw new Exception("No hay una numeración asociada al código: " + codigoNumeracion);
            }

            if (numeracion.getNumUsaNumCiudad() != null) {
                if (numeracion.getNumUsaNumCiudad().equals("S")) {
                    String codigoMunicipio = radicacion.getNotId().getMorId().getCiuId().getCiuCodigoNumeracion();
                    if (codigoMunicipio != null && !codigoMunicipio.equals("")) {
                        consecutive = codigoMunicipio;
                    }
                }
            }

            numeracion.setNumNumero(numeracion.getNumNumero().add(BigInteger.ONE));
            if (numeracion.getNumPrefijo() != null) {
                consecutive = consecutive + numeracion.getNumPrefijo();
            }
            consecutive = consecutive + numeracion.getNumNumero();
            if (numeracion.getNumSufijo() != null) {
                consecutive = consecutive + numeracion.getNumSufijo();
            }
            genericDAOBean.merge(numeracion);
        } catch (Exception e) {
            throw e;
        }
        return consecutive;
    }
}
