package co.com.realtech.mariner.util.string;

/**
 * Utilidades de manejo de cadenas de Texto.
 * 
 * @author  Andres Rivera
 * @version 1.0
 * @since   JDK1.7
 */
public class TextUtils {
    /**
     * Genera una cadena de texto con la cantidad de Tabulados necesarios
     * @param amount
     * @return 
     */
    public static String generateTabs(Integer amount){
        String tabs="";
        for(int t=0;t<=amount;t++){
            tabs+="   ";
        }
        return tabs;
    }
    
}