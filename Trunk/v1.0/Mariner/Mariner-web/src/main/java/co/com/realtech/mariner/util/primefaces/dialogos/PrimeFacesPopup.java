package co.com.realtech.mariner.util.primefaces.dialogos;

import co.com.realtech.mariner.util.session.SessionUtils;
import java.util.Iterator;
import java.util.Map;
import org.primefaces.context.RequestContext;

/**
 * Clase encargada de la ejecucion de ventanas Emergentes del portal
 *
 * @author Andres Rivera
 * @since JDK1.6
 * @version 1.0
 */
public class PrimeFacesPopup {

    /**
     * Ejecucion Dialogs Generico PrimeFaces
     *
     * @param efecto
     * @param titulo
     * @param mensaje
     * @param modal
     */
    public static void lanzarDialog(Effects efecto, String titulo, String mensaje, Boolean modal) {
        try {
            SessionUtils.asignarValor("dialog", new PopupModel(titulo, mensaje, efecto.getTipoEfecto(), modal));
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('genericDialog').show()");
            context.update("modalDialog");
        } catch (Exception e) {
            System.out.println("Error ejecutando Dialog primefaces, causado por " + e);
        }
    }

    /**
     * Ejecucion Dialogs Generico PrimeFaces
     *
     * @param efecto
     * @param titulo
     * @param mensaje
     * @param modal
     * @param usaI18N
     */
    public static void lanzarDialog(Effects efecto, String titulo, String mensaje, Boolean modal, Boolean usaI18N) {
        try {
            SessionUtils.asignarValor("dialog", new PopupModel(titulo, mensaje, efecto.getTipoEfecto(), modal, usaI18N));
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('genericDialog').show()");
            context.update("modalDialog");
        } catch (Exception e) {
            System.out.println("Error ejecutando Dialog primefaces, causado por " + e);
        }
    }

    /**
     * Ejecutar llamado ventana emergente usando parametros de remplazo
     *
     * @param efecto
     * @param titulo
     * @param mensaje
     * @param modal
     * @param replaceParams
     */
    public static void lanzarDialog(Effects efecto, String titulo, String mensaje, Boolean modal, Map<String, Object> replaceParams) {
        try {
            // Remplazado de parametros en la cadena del mensaje
            Iterator<String> en = replaceParams.keySet().iterator();
            while (en.hasNext()) {
                mensaje = mensaje.replaceAll(en.next(), replaceParams.get(en.next()).toString());
            }
            SessionUtils.asignarValor("dialog", new PopupModel(titulo, mensaje, efecto.getTipoEfecto(), modal));
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('genericDialog').show()");
            context.update("modalDialog");
        } catch (Exception e) {
            System.out.println("Error ejecutando Dialog primefaces, causado por " + e);
        }
    }
}
