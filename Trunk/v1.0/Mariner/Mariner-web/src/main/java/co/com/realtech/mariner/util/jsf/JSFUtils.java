package co.com.realtech.mariner.util.jsf;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Clase encargada del manejo y de las utilidades JSF.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class JSFUtils implements Serializable {

    /**
     * Retorna el contexto actual por el que esta corriendo la aplicacion.
     *
     * @return
     */
    public static String getCurrentContext() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getContextPath();
    }

}
