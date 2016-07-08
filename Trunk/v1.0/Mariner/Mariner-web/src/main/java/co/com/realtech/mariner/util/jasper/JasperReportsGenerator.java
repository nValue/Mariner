package co.com.realtech.mariner.util.jasper;

import co.com.realtech.mariner.model.entity.MarReportes;
import java.util.HashMap;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Contiene las funcionalidades de generacion de Reportes.
 *
 * @author Fabian Agudelo
 * @version 1.0
 * @since JDK1.7
 */
public class JasperReportsGenerator {

    private byte[] outputBytes;

    public static JasperReportsGenerator create() {
        return new JasperReportsGenerator();
    }

    private JasperReportsGenerator() {
    }

    /**
     * Ejecutar reporte asociado a la configuracion del reporte
     *
     * @param context
     * @param parametros
     * @param reporte
     * @param extension
     * @throws Exception
     */
    public void executeReport(FacesContext context, HashMap<String, Object> parametros, MarReportes reporte, String extension) throws Exception {
        try {
            if(extension == null || extension.equals("")){
                extension = reporte.getRepExtension();
            }
            MarinerJasperEngine jasperEngine = MarinerJasperEngine.create();
            byte[] bytes = jasperEngine.executeReport(parametros, reporte, extension);
            if (extension.contains("xls")) {
                descargarExcel(context, bytes, reporte.getRepJasperNombre());
            } else {
                descargarPdf(context, bytes, reporte.getRepJasperNombre());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Descargar archivo a través del FacesContext.
     *
     * @param context
     * @param bytes
     * @param nombreArchivo
     */
    private void descargarPdf(FacesContext context, byte[] bytes, String nombreArchivo) {
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        try {
            if (bytes == null) {
                System.out.println("Bytes nulos en respuesta de PDF");
            } else {
                try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                    response.addHeader("Content-Type", "application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + ".pdf");
                    response.setContentLength(bytes.length);
                    response.setContentType("application/pdf");
                    servletOutputStream.write(bytes);
                    servletOutputStream.flush();
                    context.responseComplete();
                }
            }
        } catch (Exception e) {
            System.out.println("Error enviando reporte al cliente, error causado por " + e);
        }
    }

    /**
     * Descargar excel a través del FacesContext.
     *
     * @param context
     * @param bytes
     * @param nombreArchivo
     */
    private void descargarExcel(FacesContext context, byte[] bytes, String nombreArchivo) {
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        try {
            try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                response.addHeader("Content-Type", "application/xlsx");
                response.addHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + ".xlsx");
                response.setContentLength(bytes.length);
                response.setContentType("application/xlsx");
                servletOutputStream.write(bytes);
                servletOutputStream.flush();
                context.responseComplete();
            }
        } catch (Exception e) {
            System.out.println("Error enviando reporte al cliente, error causado por " + e);
        }
    }

    public byte[] getOutputBytes() {
        return outputBytes;
    }
}
