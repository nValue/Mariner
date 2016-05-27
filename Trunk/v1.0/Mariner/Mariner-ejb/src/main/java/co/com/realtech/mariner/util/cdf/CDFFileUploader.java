package co.com.realtech.mariner.util.cdf;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.model.entity.MarPuntosMontajes;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.files.FileUtils;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Clase encargada del almacenamiento de los archivos en el disco duro.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.0
 */
public class CDFFileUploader {

    boolean persistChanges = false;
    private GenericDAOBeanLocal genericDAOBean;
    private MarPuntosMontajes puntoMontaje;

    static Logger logger = Logger.getLogger(CDFFileUploader.class);

    /**
     * Factory de la clase CDFFileUploader
     *
     * @param pc
     * @return
     * @throws java.lang.Exception
     */
    public static CDFFileUploader create(boolean pc) throws Exception {
        return new CDFFileUploader(pc);
    }

    private CDFFileUploader(boolean pc) throws Exception {
        try {
            Context ic = new InitialContext();
            genericDAOBean = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
            this.persistChanges = pc;
        } catch (Exception e) {
            logger.error("Error creando CDFFileUploader, causado por ", e);
        }
    }

    /**
     * Guardado de archivo dentro depositorio central.
     *
     * @param fileName
     * @param content
     * @param size
     * @param mimeType
     * @param usuario
     * @param visibleCDN
     * @return
     * @throws Exception
     */
    public MarArchivos saveFile(String fileName, byte[] content, Long size, String mimeType, String usuario, boolean visibleCDN) throws Exception {
        MarArchivos file = null;
        try {
            // cargamos todos los puntos de montaje disponibles.
            List<MarPuntosMontajes> puntosMontaje;
            puntosMontaje = (List<MarPuntosMontajes>) genericDAOBean.findAllByColumn(MarPuntosMontajes.class, "pmaEstado", "A", true, "pmaId desc");
            if(!esTamanoValido(size)){
                throw new Exception("No puede subir el archivo seleccionado, el archivo supera el tamaño máximo permitido.");
            }
            if (!puntosMontaje.isEmpty()) {
                if (puntosMontaje.size() == 1) {
                    MarPuntosMontajes pm = puntosMontaje.get(0);
                    file = saveFile(pm, fileName, content, size, mimeType, usuario, visibleCDN);
                } else {
                    throw new Exception("Error guardando archivo en repositorio central de almacenamiento, se han encontrado " + puntosMontaje.size() + " puntos de montaje en estado A (Activo)");
                }
            } else {
                throw new Exception("Error guardando archivo en repositorio central de almacenamiento, no se han encontrado puntos de montajes configurados.");
            }
        } catch (Exception e) {
            throw new Exception("Error guardando archivo en repositorio central de almacenamiento: " + e.getMessage(), e);
        }
        return file;
    }

    /**
     * Guardado del archivo indicando el Punto de Montaje en donde sera
     * almacenado.
     *
     * @param pmInput
     * @param fileName
     * @param content
     * @param size
     * @param mimeType
     * @param usuario
     * @param visibleCDN
     * @return
     * @throws Exception
     */
    public MarArchivos saveFile(MarPuntosMontajes pmInput, String fileName, byte[] content, Long size, String mimeType, String usuario, boolean visibleCDN) throws Exception {
        MarArchivos file = null;
        try {
            puntoMontaje = pmInput;
            Date sysdate = new Date();
            if(!esTamanoValido(size)){
                throw new Exception("El archivo supera el tamaño máximo permitido.");
            }
            logger.debug("Encontrado punto de montaje Default " + puntoMontaje.getPmoNombre());
            // cargamos el hash que se le asignara al archivo
            String fileHash = nextSessionId();
            String fileExtension = FileUtils.extractExtension(fileName);
            String fileSeparator = System.getProperty("file.separator");
            String filePath = puntoMontaje.getPmoPath();
            String finalFileName = fileHash + "." + fileExtension;
            // Crea el subdirectorio en yyyy/mm/dd/hh24
            String fileSubPath = (sysdate.getYear() + 1900) + fileSeparator + (sysdate.getMonth() + 1) + fileSeparator + (sysdate.getDate()) + fileSeparator + (sysdate.getHours()) + fileSeparator;

            logger.debug("Definiendo carpetas base para escribir en  " + filePath + " con subdirectorio en " + fileSubPath);

            // creamos y verificamos estructura interna de carpetas
            File containerFolder = new File(filePath + fileSeparator + fileSubPath);
            if (!containerFolder.exists()) {
                containerFolder.mkdirs();
            }
            File destinationFile = new File(filePath + fileSeparator + fileSubPath + fileSeparator + finalFileName);
            // Guardamos en archivo en la ubicacion indicada.
            logger.debug("Preparando para escribir " + fileName + " con nombre Final " + finalFileName + " en directorio " + destinationFile);
            FileUtils.writeFile(destinationFile, content);
            logger.debug("Se escribio el archivo  " + fileName + " con nombre Final " + finalFileName + " en directorio " + destinationFile);

            // Creamos el registro en base de datos
            file = new MarArchivos();
            file.setAudFecha(new Date());
            file.setArcMimeType(mimeType);
            try {
                file.setArcNombre(fileName.substring(0, 49));
            } catch (Exception e) {
                file.setArcNombre(fileName);
            }
            file.setArcExtension(fileExtension);
            file.setArcHash(fileHash);
            file.setArcPathInterno(fileSubPath);
            file.setArcTamano(new BigInteger(size.toString()));
            file.setAudUsuario(usuario);
            file.setPmoId(puntoMontaje);

            if (persistChanges) {
                logger.debug("Guardando referencia del archivo en base de datos");
                genericDAOBean.save(file);
            }
        } catch (Exception e) {
            throw new Exception("Error guardando archivo en repositorio central de almacenamiento: " + e.getMessage(), e);
        }
        return file;
    }
    
    /**
     * Valida el tamaño del archivo para cargarlo, si no cumple con las condiciones informa al usuario
     * @param size
     * @return 
     */
    private boolean esTamanoValido(Long size) {
        String maxTamano = ConstantesUtils.cargarConstante("MAX-CARGUE-ARCH");
        Integer tamano = Integer.parseInt(maxTamano);
        tamano = tamano * 1000 * 1000;
        if (size > tamano) {
            return false;
        }
        return true;   
    }

    /**
     * Retorna el siguiente valor aleatorio para Strings.
     *
     * @return
     */
    public String nextSessionId() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(222, random).toString(32);
    }
}
