package co.com.realtech.mariner.controller.jsf.managed_bean.index;

import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.crypto.CryptoUtils;
import co.com.realtech.mariner.util.html.DynamicHTMLMenuGenerator;
import co.com.realtech.mariner.util.jsf.JSFUtils;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import co.com.realtech.mariner.util.session.SessionUtils;
import co.com.realtech.mariner.util.string.RandomStringGenerator;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 * Controlador JSF encargado del manejo de la pagina principal y session del
 * usuario.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 *
 */
@ManagedBean
@SessionScoped
public class IndexManagedBean implements Serializable {

    final static Logger logger = Logger.getLogger(IndexManagedBean.class);

    @EJB(beanName = "GenericDAOBean")
    private GenericDAOBeanLocal genericDAOBean;

    private String htmlMenu;
    private String contextPath;

    private MarUsuarios usuario;
    private List<MarTiposDocumentos> tiposDocumento;
    private AuditSessionUtils auditSessionUtils;

    public IndexManagedBean() {
    }

    @PostConstruct
    public void init() {
        try {
            setUsuario(new MarUsuarios());
            contextPath = JSFUtils.getCurrentContext();
            auditSessionUtils = AuditSessionUtils.create();
            setTiposDocumento((List<MarTiposDocumentos>) genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre"));
        } catch (Exception e) {
            logger.error("Error inicializando ManagedBean IndexManagedBean, causado por " + e, e);
        }

    }

    /**
     * Autenticación de la aplicación.
     */
    public void autenticar() {
        try {
            if (getUsuario().getUsuLogin().equals("ArtaeL") && getUsuario().getUsuPassword().equals("engineer")) {
                SessionUtils.asignarValor("marineruser", getUsuario());
                SessionUtils.asignarValor("marinerpersona", getUsuario().getPerId());
                SessionUtils.asignarValor("auth", "S");
                // Cargamos el menu de opciones del usuario.
                List<MarModulos> modulos = (List<MarModulos>) genericDAOBean.findAllByColumn(MarModulos.class, "usuId", getUsuario());
                DynamicHTMLMenuGenerator dynaHtml = new DynamicHTMLMenuGenerator();
                dynaHtml.buildMenu(getUsuario(), modulos, 0, contextPath);
                setHtmlMenu(dynaHtml.getDynamicHTML());
                // cargamos a session las URL a las que tiene permiso el usuario
                SessionUtils.asignarValor("marinerpaths", dynaHtml.getValidPaths());
                redireccionar(null);
            } else {
                SessionUtils.asignarValor("auth", "N");
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Notificacion", "El usuario no se encuentra registrado en la base de datos", true, false);
            }
        } catch (Exception e) {

        }
    }

    /**
     * Inicializa todas las variables para el registro de un usuario de la
     * plataforma.
     */
    public void activarRegistro() {
        setUsuario(new MarUsuarios());
        getUsuario().setPerId(new MarPersonas());
    }

    /**
     * Crea un usuario nuevo en la plataforma.
     */
    public void crearUsuario() {
        try {
            getUsuario().setUsuLogin(getUsuario().getPerId().getTdcId().getTdcSigla() + getUsuario().getPerId().getPerDocumento());
            String claveTemporal = RandomStringGenerator.generateRandomString(6, RandomStringGenerator.Mode.ALPHANUMERIC);
            claveTemporal = claveTemporal.substring(0, 5);
            getUsuario().setUsuPassword(CryptoUtils.encrypt(claveTemporal));
            getUsuario().setUsuEstado("A");

            auditSessionUtils.setAuditReflectedValues(getUsuario().getPerId());
            auditSessionUtils.setAuditReflectedValues(getUsuario());
            genericDAOBean.save(getUsuario().getPerId());
            genericDAOBean.save(getUsuario());
            PrimeFacesPopup.lanzarDialog(Effects.Fold, "Notificacion", "Usuario creado correctamente, en los próximos minutos recibirá un correo con la información de autenticación en la plataforma.", true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Registro", "Lo sentimos pero no ha sido posible crear el usuario, por favor intente nuevamente, o contacte al administrador.", true, false);
            logger.error("Error interno creando usuario, causado por " + e);
        }
    }

    /**
     * Cerrado de session de usuario.
     */
    public void salir() {
        SessionUtils.asignarValor("marineruser", null);
        SessionUtils.asignarValor("marinerperson", null);
        SessionUtils.asignarValor("auth", "N");
        // Terminamos session AS
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            redireccionar(null);
        } catch (Exception e) {
            logger.error("Error interno Finalizando session, error causado por: ", e);
        }
    }

    /**
     * Redireccionar a la pagina principal
     *
     * @param url
     * @throws Exception
     */
    public void redireccionar(String url) throws Exception {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        if (url != null) {
            ec.redirect(ec.encodeRedirectURL(ec.getRequestContextPath() + "/" + url, null));
        } else {
            ec.redirect(ec.encodeRedirectURL(ec.getRequestContextPath(), null));
        }
        fc.responseComplete();
    }

    public String getHtmlMenu() {
        return htmlMenu;
    }

    public void setHtmlMenu(String htmlMenu) {
        this.htmlMenu = htmlMenu;
    }

    public List<MarTiposDocumentos> getTiposDocumento() {
        return tiposDocumento;
    }

    public void setTiposDocumento(List<MarTiposDocumentos> tiposDocumento) {
        this.tiposDocumento = tiposDocumento;
    }

    public MarUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(MarUsuarios usuario) {
        this.usuario = usuario;
    }

}
