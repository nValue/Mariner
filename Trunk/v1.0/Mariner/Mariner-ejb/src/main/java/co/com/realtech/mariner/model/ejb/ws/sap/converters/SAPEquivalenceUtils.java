package co.com.realtech.mariner.model.ejb.ws.sap.converters;

/**
 * Utilidades de equivalencia de SAP.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class SAPEquivalenceUtils {

    /**
     * Mapeador de clases documentos.
     *
     * @param value
     * @return
     */
    public static String findClasesDocumentos(String value) {
        String salida;
        if (value.equalsIgnoreCase("1")) {
            salida = "Acta";
        } else if (value.equalsIgnoreCase("2")) {
            salida = "Acto";
        } else if (value.equalsIgnoreCase("3")) {
            salida = "Auto Interlocutorio";
        } else if (value.equalsIgnoreCase("4")) {
            salida = "Escritura";
        } else if (value.equalsIgnoreCase("5")) {
            salida = "Resolución";
        } else if (value.equalsIgnoreCase("6")) {
            salida = "Sentencia";
        } else if (value.equalsIgnoreCase("7")) {
            salida = "Otros";
        } else {
            salida = "Otros";
        }
        return salida;
    }

    /**
     * Encontrar origen documento
     *
     * @param value
     * @return
     */
    public static String findOrigenDocumentos(String value) {
        String salida;
        if (value.equalsIgnoreCase("C")) {
            salida = "Consulado";
        } else if (value.equalsIgnoreCase("D")) {
            salida = "Dian";
        } else if (value.equalsIgnoreCase("J")) {
            salida = "Juzgado";
        } else if (value.equalsIgnoreCase("N")) {
            salida = "Notaria";
        } else if (value.equalsIgnoreCase("O")) {
            salida = "Otros";
        } else if (value.equalsIgnoreCase("S")) {
            salida = "Superfinanciera";
        } else {
            salida = "Otros";
        }
        return salida;
    }

}
