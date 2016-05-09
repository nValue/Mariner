package co.com.realtech.mariner.util.jdni;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 * Utilidades acceso JDNI.
 *
 * @author Andres Rivera
 * @since JDK1.6
 * @version 1.0
 */
public class JDNIUtils {

    static Context ctx;
    final static Logger logger = Logger.getLogger(JDNIUtils.class);

    static {
        try {
            ctx = new InitialContext();
        } catch (NamingException ex) {
            logger.error("Error inicianlizando contexto estatico para JDNIUtils", ex);
        }
    }

    /**
     * Retorna la cadena completa para buscar un ejb en el contexto JDNI.
     *
     * @param clazz
     * @param isEjbModule
     * @return
     */
    public static String getEJBJDNIName(Class clazz, Boolean isEjbModule) {
        String fullJDNIName = "";
        try {
            String appName = ctx.lookup("java:app/AppName").toString();
            String moduleName = ctx.lookup("java:module/ModuleName").toString();
            if (isEjbModule) {
                moduleName = moduleName.replaceAll("-web-", "-ejb-");
            }
            fullJDNIName = "java:global/" + appName + "/" + moduleName + "/" + clazz.getSimpleName();
            //
        } catch (NamingException ex) {
            logger.error("Error Obteniendo referencia JDNI, causado por ", ex);
        }
        return fullJDNIName;
    }

    /**
     * Retorna la cadena completa para buscar un ejb en el contexto JDNI.
     *
     * @param ejbName
     * @param isEjbModule
     * @return
     */
    public static String getEJBJDNIName(String ejbName, Boolean isEjbModule) {
        String fullJDNIName = "";
        try {
            String appName = ctx.lookup("java:app/AppName").toString();
            String moduleName = ctx.lookup("java:module/ModuleName").toString();
            if (isEjbModule) {
                moduleName = moduleName.replaceAll("-web-", "-ejb-");
            }
            fullJDNIName = "java:global/" + appName + "/" + moduleName + "/" + ejbName;
        } catch (NamingException ex) {
            logger.error("Error Obteniendo referencia JDNI, causado por ", ex);
        }
        return fullJDNIName;
    }
}
