/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.controller.jsf.managed_bean.index;

import co.com.realtech.mariner.ctls.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.ctls.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.crypto.CryptoUtils;
import co.com.realtech.mariner.util.html.DynamicHTMLMenuGenerator;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import co.com.realtech.mariner.util.session.SessionUtils;
import co.com.realtech.mariner.util.string.RandomStringGenerator;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author fabianagudelo
 */
@ManagedBean
@SessionScoped
public class IndexManagedBean implements Serializable {

    final static Logger logger = Logger.getLogger(IndexManagedBean.class);
    
    @EJB(beanName = "GenericDAOBean")
    private GenericDAOBeanLocal genericDAOBean;
    
    private AuditSessionUtils auditSessionUtils;
    
    private List<MarTiposDocumentos> tiposDocumento;
    
    private MarUsuarios usuarioActual;
    
    private String nombre;
    private String password;
    private String htmlMenu;
    private String contextPath;
    
    public IndexManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        nombre = "";
        password = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        contextPath = request.getContextPath();
        auditSessionUtils = AuditSessionUtils.create();
    }
    
    /**
     * Autenticación de la aplicación.
     */
    public void autenticar(){
        if(nombre.equals("ArtaeL") && password.equals("engineer")){
            SessionUtils.asignarValor("auth", "S");
            cargarMenu();
        }else{
            PrimeFacesPopup.lanzarDialog(Effects.Fold, "Información inválida", "El usuario no se encuentra registrado", Boolean.TRUE);
            Object obj = SessionUtils.obtenerValor("dialog");
            System.out.println("obj = " + obj);        
        }
    }
    
    /**
     * Trae dinámicamente todo el menú para ser precargado dependiendo del perfil del usuario.
     */
    public void cargarMenu(){
        try {
            // Cargamos el menu de opciones del usuario.
            List<MarModulos> modulos = (List<MarModulos>)genericDAOBean.findAllByColumn(MarModulos.class, "usuId", usuarioActual);
            DynamicHTMLMenuGenerator dynaHtml = new DynamicHTMLMenuGenerator();
            dynaHtml.buildMenu(usuarioActual, modulos, 0, contextPath);
            setHtmlMenu(dynaHtml.getDynamicHTML());

            // cargamos a session las URL a las que tiene permiso el usuario
            SessionUtils.asignarValor("marinerpaths", dynaHtml.getValidPaths());
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Fold, "Menú no válido", "No se puede cargar el menú para este usuario.", Boolean.TRUE);
        }

    }
    
    /**
     * Inicializa todas las variables para el registro de un usuario de la plataforma.
     */
    public void activarRegistro(){
        System.out.println("Inicializando registro..");
        usuarioActual = new MarUsuarios();
        usuarioActual.setPerId(new MarPersonas());
        obtenerTiposDocumento();
    }
    
    
    /**
     * Obtiene los tipos de documento del sistema.
     */
    public void obtenerTiposDocumento(){
        try {
            tiposDocumento = (List<MarTiposDocumentos>)genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcId");
            System.out.println("tiposDocumento = " + tiposDocumento);
        } catch (Exception e) {
            logger.error("No se pueden obtener los tipos de documento, debido a : " + e.getMessage());
        }
    }
    
    /**
     * Crea un usuario nuevo en la plataforma.
     */
    public void crearUsuario(){
        try {
            usuarioActual.setUsuLogin(usuarioActual.getPerId().getTdcId().getTdcSigla()+usuarioActual.getPerId().getPerDocumento());
            String claveTemporal = RandomStringGenerator.generateRandomString(6, RandomStringGenerator.Mode.ALPHANUMERIC);
            claveTemporal = claveTemporal.substring(0, 5);
            usuarioActual.setUsuPassword(CryptoUtils.encrypt(claveTemporal));
            usuarioActual.setUsuEstado("A");
            SessionUtils.asignarValor("marineruser", usuarioActual);
            auditSessionUtils.setAuditReflectedValues(usuarioActual.getPerId());
            auditSessionUtils.setAuditReflectedValues(usuarioActual);
            genericDAOBean.save(usuarioActual.getPerId());
            genericDAOBean.save(usuarioActual);
            PrimeFacesPopup.lanzarDialog(Effects.Fold, "Usuario creado", "Usuario creado correctamente, en los próximos minutos recibirá un correo con la información de autenticación en la plataforma.", Boolean.TRUE);
        } catch (Exception e) {
            String mensaje = "No se puede crear el usuario debido a " + e.getMessage();
            logger.error(mensaje);
            PrimeFacesPopup.lanzarDialog(Effects.Fold, "Creación detenida", mensaje, Boolean.TRUE);
        }
    }
    
    /**
     * Salida de la aplicación.
     */
    public void salir(){
        SessionUtils.asignarValor("auth", "N");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHtmlMenu() {
        return htmlMenu;
    }

    public void setHtmlMenu(String htmlMenu) {
        this.htmlMenu = htmlMenu;
    }

    public MarUsuarios getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(MarUsuarios usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public List<MarTiposDocumentos> getTiposDocumento() {
        return tiposDocumento;
    }

    public void setTiposDocumento(List<MarTiposDocumentos> tiposDocumento) {
        this.tiposDocumento = tiposDocumento;
    }
    
    
   
    
}
