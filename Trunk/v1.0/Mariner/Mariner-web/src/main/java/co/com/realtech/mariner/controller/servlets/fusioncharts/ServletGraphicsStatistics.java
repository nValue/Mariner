package co.com.realtech.mariner.controller.servlets.fusioncharts;

import co.com.realtech.mariner.model.ejb.dao.procedure_based.ProcedureBasedBeanLocal;
import co.com.realtech.mariner.util.string.SQLInjectionUtils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet encargado de la generacion de graficos Estadisticos de la plataforma.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@WebServlet(name = "ServletGraphicsStatistics", urlPatterns = {"/graphics/ServletGraphicsStatistics"})
public class ServletGraphicsStatistics extends HttpServlet {

    @EJB(beanName = "ProcedureBasedBean")
    ProcedureBasedBeanLocal procedureBasedBeanLocal;

    Logger logger = Logger.getLogger(ServletGraphicsStatistics.class);

    /**
     * Retorna El XML Necesario por FusionCharts para su despliegue.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/xml");

        String tipoReporte = request.getParameter("id");
        String fechaDesde = request.getParameter("fechaDesde");
        String fechaHasta = request.getParameter("fechaHasta");
        String parametros = request.getParameter("pars");

        System.out.println("tipoReporte = " + tipoReporte);
        System.out.println("fechaDesde = " + fechaDesde);
        System.out.println("fechaHasta = " + fechaHasta);
        System.out.println("parametros = " + parametros);
        
        if (SQLInjectionUtils.containsSQLInjection(tipoReporte) || SQLInjectionUtils.containsSQLInjection(fechaDesde) || SQLInjectionUtils.containsSQLInjection(fechaHasta)) {
            response.setContentType("application/txt");
            try (PrintWriter out = response.getWriter()) {
                out.println("");
            }
        } else {
            try {
                try (PrintWriter out = response.getWriter()) {
                    String xmlGeneratedData;
                    xmlGeneratedData = procedureBasedBeanLocal.cargarXMLGraficoEstadistica(tipoReporte, fechaDesde, fechaHasta,parametros);
                    //System.out.println("xmlGeneratedData = " + xmlGeneratedData);
                    out.println(xmlGeneratedData);                    
                }
            } catch (Exception e) {
                logger.error("Error generando XML de grafico para Estadistica causado por " + e, e);
            }
        }
    }
}
