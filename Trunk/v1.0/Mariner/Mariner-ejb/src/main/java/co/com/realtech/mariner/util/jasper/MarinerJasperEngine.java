package co.com.realtech.mariner.util.jasper;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.naming.InitialContext;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Motor de manejo de Reportes Mariner.
 *
 * @author Fabian Agudelo
 * @version 1.0
 * @since JDK1.7
 */
public class MarinerJasperEngine {

    private Logger logger = Logger.getLogger(MarinerJasperEngine.class);

    public static MarinerJasperEngine create() {
        return new MarinerJasperEngine();
    }

    private InitialContext ic;
    private GenericDAOBeanLocal genericDAOBean;

    private MarinerJasperEngine() {
        try {
            ic = new InitialContext();
            genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName("GenericDAOBeanLocal", Boolean.TRUE));
        } catch (Exception e) {
            logger.error("No se puede iniciar el JasperEngine, debido a " + e, e);
        }
    }

    /**
     * Realiza la ejecucion del reporte ingresado.
     *
     * @param parametros
     * @param reporte
     * @param extension
     * @return
     * @throws Exception
     */
    public byte[] executeReport(HashMap<String, Object> parametros, MarReportes reporte, String extension) throws Exception {
        byte[] bytes;
        bytes = innerExecution(parametros, reporte, extension);
        return bytes;
    }

    /**
     * Ejecucion interna de generacion del reporte.
     *
     * @param parametros
     * @param cmpId
     * @return
     * @throws Exception
     */
    private byte[] innerExecution(HashMap<String, Object> parametros, MarReportes reporte, String extension) throws Exception {
        byte[] bytes = null;
        if (reporte != null) {
            String fileSeparator = System.getProperty("file.separator");
            MarArchivos archivo = reporte.getArcIdReporte();
            String fileSubpath = archivo.getArcPathInterno();
            if (!fileSubpath.contains(fileSeparator)) {
                fileSubpath = fileSubpath.replaceAll("\\\\", fileSeparator);
            }
            String reportHome = archivo.getPmoId().getPmoPath() + fileSubpath;
            parametros.put("REPORT_HOME", reportHome);
            parametros.put("SUBREPORT_DIR", reportHome);
            parametros.put("FILE_SEPARATOR", fileSeparator);

            // Obtenemos conexion JDBC para enviarsela al reporte.
            Connection conexion = genericDAOBean.extractJDBCConnection();
            try {
                if (extension.contains("xls")) {
                    JasperReport jasperReport = JasperCompileManager.compileReport(reportHome + reporte.getRepJasperNombre() + ".jrxml");
                    JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros, conexion);
                    File xlsx = new File(reportHome + reporte.getRepJasperNombre() + ".xlsx");
                    JRXlsxExporter Xlsxexporter = new JRXlsxExporter();
                    Xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                    Xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE, xlsx);
                    Xlsxexporter.exportReport();
                    System.out.println("Reporte creado");
                    InputStream is = new FileInputStream(xlsx);
                    xlsx.delete();
                    bytes = IOUtils.toByteArray(is);
                } else {
                    JasperReport jasperReport = JasperCompileManager.compileReport(reportHome + reporte.getRepJasperNombre() + ".jrxml");
                    bytes = JasperRunManager.runReportToPdf(jasperReport, parametros, conexion);
                }
            } catch (Exception e) {
                logger.error("Error en la generación del reporte, causado por " + e);
            } finally {
                try {
                    conexion.close();
                } catch (Exception e) {
                    logger.warn("No se ha podido cerrar la conexion en MarinerJasperEngine, error interno " + e);
                }
            }
        } else {
            logger.error("No se puede ejecutar el reporte, o bien no se encuentra en el repositorio");
        }
        return bytes;
    }

}
