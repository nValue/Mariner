package co.com.realtech.mariner.util.html;

import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.string.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Generador de HTML dinamico con permisos de usuario autenticado.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
public class DynamicHTMLMenuGenerator {

    static Logger logger = Logger.getLogger(DynamicHTMLMenuGenerator.class);

    private String dynamicHtml = "\n";
    private final List<String> validPaths = new ArrayList<>();
    private String contextPath = "";

    /**
     * Generacion de menu de usuario
     *
     * @param usuario
     * @param modulos
     * @param level
     * @param context
     */
    public void buildMenu(MarUsuarios usuario, List<MarModulos> modulos, Integer level, String context) {
        contextPath = context;
        try {
            this.dynamicHtml += TextUtils.generateTabs(level) + "<ul>\n";
            generateDynamicHTMLMenu(modulos, level);
            this.dynamicHtml += TextUtils.generateTabs(level) + "</ul>\n";
        } catch (Exception e) {
            logger.error("Error generando menu HTML dinamico de usuario " + usuario.getUsuLogin() + " causado por:" + e, e);
        }
    }

    /**
     * Recorrido de arreglo con los modulos encontrados.
     *
     * @param modulos
     * @param level
     */
    public void generateDynamicHTMLMenu(List<MarModulos> modulos, Integer level) {
        for (MarModulos module : modulos) {
            boolean valLastDeep;
            valLastDeep = module.getMarModulosList().isEmpty();

            if (!valLastDeep) {
                this.dynamicHtml += TextUtils.generateTabs(level) + "<li class=\"has-sub\"> \n";
            } else {
                this.dynamicHtml += TextUtils.generateTabs(level) + "<li>\n";
            }

            if (module.getModUrl().equals("#")) {
                this.dynamicHtml += TextUtils.generateTabs(level) + "<a>" + module.getModNombre() + "</a>";
            } else {
                this.dynamicHtml += TextUtils.generateTabs(level) + "<a href=\"" + contextPath + module.getModUrl() + "\">" + module.getModNombre() + "</a>";
                this.getValidPaths().add(module.getModUrl());
            }

            if (!valLastDeep) {
                this.dynamicHtml += TextUtils.generateTabs(level) + "<ul>\n";
            }
            generateDynamicHTMLMenu(module.getModulosMenu(), (level + 1));
            if (!valLastDeep) {
                this.dynamicHtml += TextUtils.generateTabs(level) + "</ul>\n";
            }
            this.dynamicHtml += TextUtils.generateTabs(level) + "</li>\n";
        }
    }

    public String getDynamicHTML() {
        return dynamicHtml;
    }

    public List<String> getValidPaths() {
        return validPaths;
    }

}
