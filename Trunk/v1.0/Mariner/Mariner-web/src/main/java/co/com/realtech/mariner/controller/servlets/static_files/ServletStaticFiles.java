package co.com.realtech.mariner.controller.servlets.static_files;

import co.com.realtech.mariner.util.cdf.CDFFileDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Controlador Tipo Servlet encargado de retornar un archivo de repositorio de
 * archivos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@WebServlet(name = "ServletStaticFiles", urlPatterns = {"/static/fileDispatcher/*"})
public class ServletStaticFiles extends HttpServlet {

    Logger logger = Logger.getLogger(ServletStaticFiles.class);

    /**
     * Retorna el archivo estatico si es encontrado en base de datos y si el
     * HASH es correcto.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String encodedfileName;
            String fileCode;
            String fileHash;

            if (pathInfo.length() > 2) {
                encodedfileName = pathInfo.substring(1, pathInfo.length());
                encodedfileName = encodedfileName.substring(0, encodedfileName.lastIndexOf("."));
                
                String[] splitName = encodedfileName.split("-");
                fileCode = splitName[0];
                fileHash = splitName[1];

                CDFFileDispatcher dispatcher = CDFFileDispatcher.create();
                dispatcher.findFile(fileCode, fileHash, encodedfileName);

                if (dispatcher.getError().equals("")) {
                    // Retornamos el binario de la imagen con todos sus encabezados
                    response.setHeader("content-type", dispatcher.getMimeType());

                    if (dispatcher.getMimeType().startsWith("image")) {
                        response.addHeader("content-disposition", "inline; filename=" + encodedfileName+"."+dispatcher.getFileExtension());
                        response.addHeader("Cache-Control", "max-age=3600");
                        response.addHeader("Pragma", "no-cache");
                        response.setDateHeader("Expires", 0);
                    } else {
                        response.addHeader("content-disposition", "attachment; filename=" + encodedfileName+"."+dispatcher.getFileExtension());
                    }
                    response.setContentType(dispatcher.getMimeType());
                    response.setContentLength(dispatcher.getFileContent().length);

                    try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                        servletOutputStream.write(dispatcher.getFileContent(), 0, dispatcher.getFileContent().length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                    }
                } else {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<h1>" + dispatcher.getError() + "</h1>");
                }
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h1>" + "Path de consulta de archivo, invalido" + "</h1>");
            }
        } catch (Exception e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>" + "Error de ingreso de parametros, IE:"+e + "</h1>");
            logger.error("Error intentando gestion doGet-ServletStaticFiles, causado por " + e, e);
        }
    }

}
