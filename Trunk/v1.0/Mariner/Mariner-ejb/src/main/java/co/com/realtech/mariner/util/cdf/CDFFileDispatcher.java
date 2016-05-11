package co.com.realtech.mariner.util.cdf;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Utilidad de cargue de archivos del repositorio central.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.0
 */
public class CDFFileDispatcher {

    private GenericDAOBeanLocal genericDAOBean;

    private String fileName;
    private String fileExtension;
    private String mimeType;
    private Long fileSize;
    private byte[] fileContent;
    private String error = "";

    static Logger logger = Logger.getLogger(CDFFileDispatcher.class);

    public static CDFFileDispatcher create() {
        return new CDFFileDispatcher();
    }

    private CDFFileDispatcher() {
        try {
            Context ic = new InitialContext();
            genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
        } catch (Exception e) {
            logger.error("Error creando CDFFileDispatcher, causado por ", e);
        }
    }

    /**
     * Busqueda de archivo y cargue de propiedades al objeto.
     *
     * @param code
     * @param hash
     * @param encodeFileName
     * @throws Exception
     */
    public void findFile(String code, String hash, String encodeFileName) throws Exception {
        try {
            logger.debug("Iniciando busqueda de archivo en CDF, para codigo " + code + " con hash " + hash);
            // Cargamos la configuracion y el archivo
            MarArchivos archivo = (MarArchivos) genericDAOBean.findByColumn(MarArchivos.class, "arcId", new BigDecimal(code));

            if (archivo != null) {
                if (archivo.getArcHash().endsWith(hash)) {
                    String fileSeparator = System.getProperty("file.separator");

                    logger.debug("Encontrado registro en base de datos, para codigo " + code + " con hash " + hash);
                    setFileName(archivo.getArcNombre());
                    setFileExtension(archivo.getArcExtension());
                    setFileSize(archivo.getArcTamano().longValue());
                    setMimeType(archivo.getArcMimeType());
                    logger.debug("Iniciando busqueda de archivo en Repositorio " + archivo.getPmoId().getPmoNombre() + " - " + archivo.getPmoId().getPmoPath() + " en subdirectorio " + archivo.getArcPathInterno());

                    String fileSubpath = archivo.getArcPathInterno();

                    if (!fileSubpath.contains(fileSeparator)) {
                        fileSubpath = fileSubpath.replaceAll("\\\\", fileSeparator);
                        //fileSubpath=fileSubpath.replaceAll("//", fileSeparator);
                    }

                    String filePath = archivo.getPmoId().getPmoPath() + fileSubpath + archivo.getArcHash() + "." + archivo.getArcExtension();
                    logger.debug("Leyendo archivo en ruta " + filePath);
                    setFileContent(IOUtils.toByteArray(new FileInputStream(new File(filePath))));
                    logger.debug("Lectura de archivo finalizada correctamente con un tamaño de " + getFileContent().length);
                } else {
                    logger.error("Error buscando archivo con hash de acceso " + hash);
                    setError("No se ha encontrado un archivo asociado al hash de acceso " + encodeFileName);
                }
            } else {
                setError("No se ha encontrado un archivo asociado al hash de acceso " + encodeFileName + ", o usted no tiene los permisos para acceder a este.");
            }
        } catch (MarinerPersistanceException | IOException e) {
            logger.error("Error buscando CDFFileDispatcher, causado por ", e);
            throw new Exception("Error buscando archivo en CDF", e);
        }
    }

    /**
     * Busqueda de archivo por Codigo llave ARC_ID
     *
     * @param arcId
     * @throws Exception
     */
    public void findFile(BigDecimal arcId) throws Exception {
        try {
            logger.debug("Iniciando busqueda de archivo en CDF, para codigo " + arcId);
            // Cargamos la configuracion y el archivo
            MarArchivos archivo = (MarArchivos) genericDAOBean.findByColumn(MarArchivos.class, "arcId", arcId);

            if (archivo != null) {
                String fileSeparator = System.getProperty("file.separator");
                setFileName(archivo.getArcNombre());
                setFileExtension(archivo.getArcExtension());
                setFileSize(archivo.getArcTamano().longValue());
                setMimeType(archivo.getArcMimeType());
                logger.debug("Iniciando busqueda de archivo en Repositorio " + archivo.getPmoId().getPmoNombre() + " - " + archivo.getPmoId().getPmoPath() + " en subdirectorio " + archivo.getArcPathInterno());

                String fileSubpath = archivo.getArcPathInterno();

                if (!fileSubpath.contains(fileSeparator)) {
                    fileSubpath = fileSubpath.replaceAll("\\\\", fileSeparator);
                }
                String filePath = archivo.getPmoId().getPmoPath() + fileSubpath + archivo.getArcHash() + "." + archivo.getArcExtension();
                logger.debug("Leyendo archivo en ruta " + filePath);
                setFileContent(IOUtils.toByteArray(new FileInputStream(new File(filePath))));
                logger.debug("Lectura de archivo finalizada correctamente con un tamaño de " + getFileContent().length);
            } else {
                setError("No se ha encontrado un archivo asociado al codigo ARC_ID " + arcId);
            }
        } catch (MarinerPersistanceException e) {
            logger.error("Error buscando CDFFileDispatcher, causado por ", e);
            throw new Exception("Error buscando por ARC_ID archivo en CDF", e);
        } catch (IOException e) {
            logger.error("Error buscando CDFFileDispatcher, causado por ", e);
            throw new Exception("Error buscando por ARC_ID archivo en CDF", e);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
