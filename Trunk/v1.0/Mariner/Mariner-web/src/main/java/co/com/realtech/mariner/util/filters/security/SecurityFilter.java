package co.com.realtech.mariner.util.filters.security;

import co.com.realtech.mariner.model.entity.MarUsuarios;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Filtro de control de seguridad y permisos de los usuarios autenticados.
 *
 * @author Andres Rivera
 * @serial JDK1.8
 * @version 1.0
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/portal/*"})
public class SecurityFilter implements Filter {

    Logger logger = Logger.getLogger(SecurityFilter.class);

    public SecurityFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // Verificar que el usuario tenga una session activa y con datos validos
            if (request instanceof HttpServletRequest) {
                HttpServletRequest req = (HttpServletRequest) request;
                String path = req.getRequestURI().substring(req.getContextPath().length());
                MarUsuarios usuario = (MarUsuarios) req.getSession().getValue("marineruser");
                // Redireccionar al home
                if (usuario == null) {
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.sendRedirect(req.getContextPath());
                } else {
                    // Verificar los permisos del usuario.
                    List<String> paths = (List<String>) req.getSession().getValue("marinerpaths");

                    boolean valContext = false;
                    for (String mod : paths) {
                        if (path.startsWith(mod)) {
                            valContext = true;
                        }
                    }
                    if (!valContext) {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
                        httpResponse.sendRedirect(req.getContextPath() + "/forbidden-page.vur");
                    }
                }
                if (!response.isCommitted()) {
                    chain.doFilter(request, response);
                }
            }
        } catch (IOException | ServletException e) {
            logger.error("Error filtrando peticiones, error causado por " + e, e);
        }
    }

    @Override
    public void destroy() {
    }

}
