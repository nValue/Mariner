package co.com.realtech.mariner.util.string;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilidades de Validacion de SQLInjection.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 *
 */
public class SQLInjectionUtils implements Serializable {

    /**
     * Validacion de Texto contra SQLInjection.
     *
     * @param textToValidate
     * @return
     */
    public static boolean containsSQLInjection(String textToValidate) {
        boolean validacion;
        try {
            String regex = "^(SELECT|UPDATE|INSERT|DROP|COMMIT|TRUNCATE|MERGE|DELETE)[\\s\\S]+?\\s*?$";            
            Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
            Matcher matcher = p.matcher(textToValidate);            
            validacion = matcher.matches();
        } catch (Exception e) {
            return true;
        }
        return validacion;
    }

}
