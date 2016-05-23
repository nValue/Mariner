package co.com.realtech.mariner.util.session;

import co.com.realtech.mariner.model.entity.MarUsuarios;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * Gestor de utilidades auditoria de la session.
 *
 * @author Andres Rivera
 * @since JDK1.6
 * @version 1.0
 */
public class AuditSessionUtils implements Serializable {

    public static AuditSessionUtils create() {
        return new AuditSessionUtils();
    }

    private AuditSessionUtils() {
    }

    Logger logger = Logger.getLogger(AuditSessionUtils.class);

    /**
     * Asigna el usuario y la fecha de modificacion del objeto.
     *
     * @param obj
     */
    public void setAuditReflectedValues(Object obj) {
        String user;
        try {
            Class clazz = obj.getClass();
            // Asignar fecha de modificacion del objeto
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            MarUsuarios usuario = ((MarUsuarios) request.getSession().getValue("marineruser"));
            try {
                user = usuario.getUsuId() + "-" + usuario.getUsuLogin();
            } catch (Exception e) {
                user = "";
            }

            try {
                Field audUsuario = clazz.getDeclaredField("audUsuario");
                audUsuario.setAccessible(true);
                audUsuario.set(obj, user);
                audUsuario.setAccessible(false);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                Field audUsuario = clazz.getDeclaredField("audUser");
                audUsuario.setAccessible(true);
                audUsuario.set(obj, user);
                audUsuario.setAccessible(false);
            }
            // Asignar fecha de modificacion del objeto
            try {
                Field audFecha = clazz.getDeclaredField("audFecha");
                audFecha.setAccessible(true);
                audFecha.set(obj, new Date());
                audFecha.setAccessible(false);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                Field audFecha = clazz.getDeclaredField("audDate");
                audFecha.setAccessible(true);
                audFecha.set(obj, new Date());
                audFecha.setAccessible(false);
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            logger.error("Error seteando auditoria de usuario, causado por: ", e);
        }
    }
}
