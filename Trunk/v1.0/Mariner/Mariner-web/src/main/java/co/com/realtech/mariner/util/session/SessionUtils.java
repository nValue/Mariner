package co.com.realtech.mariner.util.session;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Session context utilities.
 *
 * @author Andres Rivera
 * @since JDK1.7
 * @version 1.0
 */
public class SessionUtils {

    /**
     * Asignar valor String a propiedad en session
     *
     * @param llave
     * @param valor
     */
    public static void asignarValor(String llave, String valor) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(llave, valor);
    }

    /**
     * Asignar valor String a propiedad en session
     *
     * @param llave
     * @param valor
     */
    public static void asignarValor(String llave, Object valor) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(llave, valor);
    }

    /**
     * Retorna el valor encontrado por la llave en la session
     *
     * @param llave
     * @return
     */
    public static String obtenerValor(String llave) {
        String value;
        try {
            value = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(llave).toString();
        } catch (Exception e) {
            value = "";
        }
        return value;
    }

    /**
     * Retorne el objeto si es encontrado e la session
     *
     * @param llave
     * @return
     */
    public static Object obtenerValorGeneric(String llave) {
        Object value;
        try {
            value = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(llave);
        } catch (Exception e) {
            value = null;
        }
        return value;
    }

    /**
     * Eliminado de valor de la session.
     *
     * @param llave
     */
    public static void eliminarValor(String llave) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(llave);
    }

    /**
     * Retorna el identificador de session asignado en el servidor de
     * aplicaciones.
     *
     * @param fCtx
     * @return
     */
    public static String getSessionID(FacesContext fCtx) {
        String asId = "";
        try {
            HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
            asId = session.getId();
        } catch (Exception e) {
            System.out.println("Error retornando ID de session, causado por "+e);
        }
        return asId;
    }

}
