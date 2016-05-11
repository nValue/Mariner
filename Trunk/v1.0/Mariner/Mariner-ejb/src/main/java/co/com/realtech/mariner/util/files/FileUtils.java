package co.com.realtech.mariner.util.files;

import java.io.File;

/**
 * Utilidades de acceso a disco.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
public class FileUtils {

    /**
     * Grabado de archivo en el unidad de almacenamiento.
     *
     * @param filePath
     * @param fileName
     * @param content
     * @throws Exception
     */
    public static void writeFile(String filePath, String fileName, byte[] content) throws Exception {
        try {
            File destination = new File(filePath + fileName);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(destination, content);
        } catch (Exception e) {
            throw new Exception("Error escribiendo archivo " + fileName + " en directorio " + filePath, e);
        }
    }

    /**
     * Guardado de archivo en ubicacion fisica.
     *
     * @param file
     * @param content
     * @throws Exception
     */
    public static void writeFile(File file, byte[] content) throws Exception {
        try {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(file, content);
        } catch (Exception e) {
            throw new Exception("Error escribiendo archivo " + file, e);
        }
    }

    /**
     * Extraccion de extension del nombre del archivo ingresado.
     *
     * @param fileName
     * @return
     */
    public static String extractExtension(String fileName) {
        String ext = "";
        try {
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                ext = fileName.substring(i + 1);
            }
        } catch (Exception e) {
            return null;
        }
        return ext;
    }

}
