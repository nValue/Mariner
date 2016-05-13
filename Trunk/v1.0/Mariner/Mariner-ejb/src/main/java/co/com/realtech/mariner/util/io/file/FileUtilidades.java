package co.com.realtech.mariner.util.io.file;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarConstantes;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.io.File;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Utilidades de acceso a disco.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
public class FileUtilidades {

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

    /**
     * Pregunta si el tipo de archivo es válido para ser ingresado al CDF
     * @param mimeType
     * @return 
     */
    public static boolean invalidFile(String mimeType) {
        ArrayList<String> extensionesInv = new ArrayList<String>();
        extensionesInv.add("x-sh");
        extensionesInv.add("script.sh");
        //extensionesInv.add("octet-stream");
        extensionesInv.add("java");
        for (String ext : extensionesInv) {
            if (mimeType.contains(ext)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Valida si el tamaño del archivo es inválido para subir al CDF
     * @param tamano
     * @return
     * @throws Exception 
     */
    public static boolean invalidSize(int tamano) throws Exception{
        try {
            Context ic = new InitialContext();
            GenericDAOBeanLocal genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName("GenericDAOBeanLocal", Boolean.TRUE));
            MarConstantes cons = (MarConstantes)genericDAOBean.findByColumn(MarConstantes.class, "conSigla", "MAR_TAM_ARCH_MAX");
            if(cons == null){
                throw new Exception("Constante MAR_TAM_ARCH_MAX no parametrizada en el sistema");
            }
            String valor = cons.getConValorGenerico();
            return Integer.parseInt(valor) < tamano;
        } catch (Exception e) {
            throw e;
        }
    }
    
    
}
