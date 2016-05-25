package co.com.realtech.mariner.util.string;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;

/**
 * Clase de conversion de Strings para logica de negocio.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class BusinessStringUtils implements Serializable {
    /**
     * Conversion de numero de liquidacion con ceros a la izquierda
     * @param original
     * @return 
     */
    public static String convertNumeroLiquidacion(String original) {
        String resultado;
        try {
            if (original.length() >= 16) {
                resultado = original;
            } else {
                resultado = StringUtils.leftPad(original, 16, "0");
            }
        } catch (Exception e) {
            resultado = original;
        }
        return resultado;
    }
    
}
