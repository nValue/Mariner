package co.com.realtech.mariner.util.primefaces.context;

import org.primefaces.context.RequestContext;
/**
 * Utilidades contexto primefaces.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.6
 */
public class PrimeFacesContext {
    /**
     * Ejecutar sentencia JavaScript dentro del contexto
     * @param command 
     */
    public static void execute(String command) {
        RequestContext.getCurrentInstance().execute(command);
    }
    /**
     * Actualizar componente mediante ajax asociado al css selector id
     * @param id 
     */
    public static void update(String id) {
        RequestContext.getCurrentInstance().update(id);
    }

}