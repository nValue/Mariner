package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarArchivos;
import co.com.realtech.mariner.model.entity.MarPuntosMontajes;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.cdf.CDFFileUploader;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import co.com.realtech.mariner.util.session.SessionUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

/**
 * ManagedBean encargado de la administracion de puntos de montaje y
 * administracion de archivos
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@ViewScoped
public class CDFManagedBean implements Serializable {

    final static Logger logger = Logger.getLogger(CDFManagedBean.class);

    @EJB(beanName = "GenericDAOBean")
    private GenericDAOBeanLocal genericDAOBean;

    private int limiteRegistros = 500;
    private MarArchivos archivo;
    private List<MarArchivos> archivos;
    private MarPuntosMontajes puntoMontaje;
    private List<MarPuntosMontajes> puntosMontaje;

    private AuditSessionUtils auditSessionUtils;

    public CDFManagedBean() {
    }

    @PostConstruct
    public void init() {
        try {
            auditSessionUtils = AuditSessionUtils.create();
            setPuntosMontaje((List<MarPuntosMontajes>) genericDAOBean.loadAllForEntity(MarPuntosMontajes.class, "pmoNombre asc"));
            if (!getPuntosMontaje().isEmpty()) {
                setPuntoMontaje(getPuntosMontaje().get(0));
                filtrarArchivosPuntoMontaje();
            }
        } catch (Exception e) {
            logger.error("Error inicializando CDFManagedBean, causado por " + e);
        }
    }

    /**
     * Filtrado de archivos en punto de montaje
     */
    public void filtrarArchivosPuntoMontaje() {
        try {
            setArchivos((List<MarArchivos>) genericDAOBean.findAllByColumn(MarArchivos.class, "pmoId", getPuntoMontaje(), true, "arcId desc", getLimiteRegistros()));
        } catch (Exception e) {
            logger.error("Error filtrando archivos punto de montaje, causado por " + e);
        }
    }

    /**
     * Agregar nuevo punto de montaje.
     */
    public void agregarPuntoMontaje() {
        setPuntoMontaje(new MarPuntosMontajes());
    }

    /**
     * Borrado punto de montaje.
     */
    public void borrarPuntoMontaje() {
        try {
            genericDAOBean.delete(getPuntoMontaje(), getPuntoMontaje().getPmoId());
            setPuntoMontaje(null);
            init();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Punto de montaje eliminado correctamente de la base de datos.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no se pudo borrar el punto de montaje, por favor intente nuevamente", true, false);
            logger.error("Error borrando punto de montaje, causado por " + e);
        }
    }

    /**
     * Guardar punto de montaje.
     */
    public void guardarPuntoMontaje() {
        try {
            auditSessionUtils.setAuditReflectedValues(getPuntoMontaje());
            if (getPuntoMontaje().getPmoId() == null) {
                genericDAOBean.save(getPuntoMontaje());
            } else {
                genericDAOBean.merge(getPuntoMontaje());
            }
            setPuntosMontaje((List<MarPuntosMontajes>) genericDAOBean.loadAllForEntity(MarPuntosMontajes.class, "pmoNombre asc"));
            filtrarArchivosPuntoMontaje();
            PrimeFacesContext.execute("PF('dialogPuntoMontaje').hide();");
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Punto de montaje guardado correctamente en base de datos.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no se pudo guardar el punto de montaje, por favor intente nuevamente", true, false);
            logger.error("Error guardando punto de montaje, causado por " + e);
        }
    }

    /**
     * Borrar archivo.
     */
    public void borrarArchivo() {
        try {
            genericDAOBean.delete(getArchivo(), getArchivo().getArcId());
            filtrarArchivosPuntoMontaje();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Archivo eliminado correctamente de la base de datos.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no se pudo borrar el Archivo, por favor intente nuevamente", true, false);
            logger.error("Error borrando Archivo, causado por " + e);
        }
    }

    /**
     * Cargue de archivo al punto de montaje.
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        try {
            CDFFileUploader fileUploader = CDFFileUploader.create(true);
            String fileName = event.getFile().getFileName();
            System.out.println("fileName = " + fileName);
            byte[] content = IOUtils.toByteArray(event.getFile().getInputstream());

            Long size = event.getFile().getSize();
            String mimeType = event.getFile().getContentType();
            String usuario = ((MarUsuarios) SessionUtils.obtenerValorGeneric("marineruser")).getUsuLogin();
            boolean visibleCDN = true;
            fileUploader.saveFile(getPuntoMontaje(), fileName, content, size, mimeType, usuario, visibleCDN);

            PrimeFacesContext.execute("PF('fileUploadDialog').hide();");
            filtrarArchivosPuntoMontaje();
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Archivo <b>" + event.getFile().getFileName() + "</b> cargado correctamente dentro del Punto de Montaje", true, false);
        } catch (IOException ex) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Upload.!", "No se pudo cargar el archivo seleccionado, erro interno: " + ex, true, false);
            logger.error("Error cargando archivo a punto de montaje " + getPuntoMontaje().getPmoNombre() + ", causado por " + ex, ex);
        } catch (Exception ex) {
            logger.error("Error cargando archivo a punto de montaje " + getPuntoMontaje().getPmoNombre() + ", causado por " + ex, ex);
        }
    }

    public MarArchivos getArchivo() {
        return archivo;
    }

    public void setArchivo(MarArchivos archivo) {
        this.archivo = archivo;
    }

    public List<MarArchivos> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<MarArchivos> archivos) {
        this.archivos = archivos;
    }

    public MarPuntosMontajes getPuntoMontaje() {
        return puntoMontaje;
    }

    public void setPuntoMontaje(MarPuntosMontajes puntoMontaje) {
        this.puntoMontaje = puntoMontaje;
    }

    public List<MarPuntosMontajes> getPuntosMontaje() {
        return puntosMontaje;
    }

    public void setPuntosMontaje(List<MarPuntosMontajes> puntosMontaje) {
        this.puntosMontaje = puntosMontaje;
    }

    public int getLimiteRegistros() {
        return limiteRegistros;
    }

    public void setLimiteRegistros(int limiteRegistros) {
        this.limiteRegistros = limiteRegistros;
    }

}
