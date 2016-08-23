package co.com.realtech.mariner.controller.jsf.managed_bean.index;

import co.com.realtech.mariner.model.ejb.dao.entity_based.modulo.ModulosDAOBeanLocal;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarModulos;
import co.com.realtech.mariner.model.entity.MarPersonas;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.model.entity.generic.ContrasenaEntidad;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.crypto.CryptoUtils;
import co.com.realtech.mariner.util.html.DynamicHTMLMenuGenerator;
import co.com.realtech.mariner.util.jsf.JSFUtils;
import co.com.realtech.mariner.util.login.LoginUtils;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import co.com.realtech.mariner.util.session.SessionUtils;
import co.com.realtech.mariner.util.string.RandomStringGenerator;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
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
    
    @EJB(beanName = "ModulosDAOBean")
    private ModulosDAOBeanLocal modulosDAOBean;

    private String htmlMenu;
    private String contextPath;
    
    private String claveAntigua;
    private String claveNueva;
    private String claveNuevaRep;
    
    private boolean tieneCaptcha;

    private MarUsuarios usuario;
    private List<MarTiposDocumentos> tiposDocumento;
    private AuditSessionUtils auditSessionUtils;
    
    private ContrasenaEntidad contrasenaEntidad;
    
    private boolean logged;

    public IndexManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        setUsuario(new MarUsuarios());
        contextPath = JSFUtils.getCurrentContext();
        auditSessionUtils = AuditSessionUtils.create();
        limpiarCampos();
        traerCaptcha();
        logged = false;
        contrasenaEntidad = LoginUtils.obtenerEntidadDeConstante();
    }
    
    /**
     * Cierra la sesión en la base de datos para evitar la multisesión.
     */
    @PreDestroy
    public void cerrarSesionDB(){
        try {
            usuario.setUsuLogueado("N");
            genericDAOBean.merge(usuario);
        } catch (Exception e) {
            String msj = "No se puede cerrar la sesión del usuario en la base de datos, causado por :" + e.getMessage();
            logger.error(msj,e);
        }
    }

    /**
     * Autenticación de la aplicación.
     */
    public void autenticar() {
        try {
            logged = false;
            Date fechaActual = new Date();
            //Busca el usuario con el Login registrado
            System.out.println("Buscando usuario...");
            MarUsuarios usuarioALoguear = (MarUsuarios)genericDAOBean.findByColumn(MarUsuarios.class, "usuLogin", usuario.getUsuLogin());
            if(usuarioALoguear != null){
                //Valida si el password coincide
                System.out.println("Validando contraseña...");
                String passDec = CryptoUtils.decrypt(usuarioALoguear.getUsuPassword());
                if (passDec.equals(usuario.getUsuPassword())) {
                    //Verifica si el usuario está activo
                    if (usuarioALoguear.getUsuEstado().equals("I")) {
                        PrimeFacesPopup.lanzarDialog(Effects.Explode, "Usuario no válido", "El usuario no se encuentra activo.", true, false);
                        return;
                    }

                    //Valida que el usuario tenga permisos de acceso por tiempo
                    if(usuarioALoguear.getUsuFechaInicio().after(fechaActual) || usuarioALoguear.getUsuFechaFin().before(fechaActual)){
                        PrimeFacesPopup.lanzarDialog(Effects.Explode, "Usuario no válido", "El usuario ha expirado.", true, false);
                        return;
                    }
                    usuario = usuarioALoguear;
                    //Verifica que el usuario deba o no cambiar la contraseña
                    if(usuarioALoguear.getUsuCambioClave().equals("S")){
                        PrimeFacesContext.execute("PF('dialogContrasena').show()");
                        PrimeFacesPopup.lanzarDialog(Effects.Explode, "Cambio de contraseña", "Debe cambiar su contraseña antes de ingresar a la plataforma", true, false);
                        return;
                    }
                    
                    //Verifica que el usuario no tenga otra sesión abierta
                    if(usuarioALoguear.getUsuLogueado().equals("S")){
                        PrimeFacesPopup.lanzarDialog(Effects.Explode, "Sesión en proceso", "El usuario ya tiene una sesión activa, debe cerrarla antes de ingresar con una nueva, o esperar a que la sesión expire automáticamente", true, false);
                        return;
                    }
                    
                    logged = true;
                    //Se guarda el ingreso a la aplicación
                    usuario.setUsuUltimoIngreso(new Date());
                    usuario.setUsuIntentosFail(Short.parseShort("0"));
                    usuario.setUsuLogueado("S");
                    genericDAOBean.merge(usuario);
                    //El usuario es correcto, se cargan las variables de sesión
                    System.out.println("Colocando variables...");
                    SessionUtils.asignarValor("marineruser", getUsuario());
                    SessionUtils.asignarValor("marinerpersona", getUsuario().getPerId());
                    SessionUtils.asignarValor("auth", "S");
                    // Cargamos el menu de opciones del usuario.
                    System.out.println("Cargando menú...");
                    List<MarModulos> modulos = modulosDAOBean.obtenerModulosDeUsuario(usuario);
                    DynamicHTMLMenuGenerator dynaHtml = new DynamicHTMLMenuGenerator();
                    dynaHtml.buildMenu(getUsuario(), modulos, 0, contextPath);
                    setHtmlMenu(dynaHtml.getDynamicHTML());
                    // cargamos a session las URL a las que tiene permiso el usuario
                    SessionUtils.asignarValor("marinerpaths", dynaHtml.getValidPaths());
                    redireccionar(null);
                    System.out.println("Usuario logueado...");
                }else{
                    String maximo = (ConstantesUtils.cargarConstante("MAX-INTENTOS-LOGIN"));
                    int max = Integer.parseInt(maximo);
                    Short veces = usuarioALoguear.getUsuIntentosFail();
                    veces++;
                    usuarioALoguear.setUsuIntentosFail(veces);
                    if(veces >= max){
                        usuarioALoguear.setUsuEstado("I");
                    }
                    genericDAOBean.merge(usuarioALoguear);
                }
            }
            if(!logged){
                SessionUtils.asignarValor("auth", "N");
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Notificacion", "Usuario o contraseña incorrectos", true, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("No se puede validar el usuario, causado por: " + e);
        }
    }
    
    /**
     * Valida si debe o no mostrar el captcha en la pantalla principal.
     */
    public void traerCaptcha(){
        try {
            tieneCaptcha = false;
            String valor = (ConstantesUtils.cargarConstante("VUR-USA-CAPTCHA"));
            if(valor != null && valor.equals("S")){
                tieneCaptcha = true;
            }
        } catch (Exception e) {
            logger.error("Error obteniendo el captcha, causado por " + e, e);
        }
    }

    /**
     * Inicializa todas las variables para el registro de un usuario de la
     * plataforma.
     */
    public void activarRegistro() {
        try {
            setTiposDocumento((List<MarTiposDocumentos>) genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre"));
            setUsuario(new MarUsuarios());
            getUsuario().setPerId(new MarPersonas());
        } catch (Exception e) {
            logger.error("Error activando el registro, causado por " + e, e);
        }
    }

    /**
     * Crea un usuario nuevo en la plataforma.
     */
    public void crearUsuario() {
        try {
            String login = getUsuario().getPerId().getTdcId().getTdcSigla() + getUsuario().getPerId().getPerDocumento();
            
            //Valida que ese tipo y número de cédula no exista en la base de datos.
            MarUsuarios usuarioARegistrar = (MarUsuarios)genericDAOBean.findByColumn(MarUsuarios.class, "usuLogin", login);
            if(usuarioARegistrar != null){
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Usuario existente", "El tipo y número de documento ya se encuentra registrado en nuestro sistema, si le pertenece puede comunicarse con su administrador.", true, false);
                return;
            }
            getUsuario().setUsuLogin(login);
            String claveTemporal = RandomStringGenerator.generateRandomString(6, RandomStringGenerator.Mode.ALPHANUMERIC);
            claveTemporal = claveTemporal.substring(0, 5);
            getUsuario().setUsuPassword(CryptoUtils.encrypt(claveTemporal));
            getUsuario().setUsuEstado("A");
            System.out.println("claveTemporal = " + claveTemporal);
            
            //Se colocan las auditorías manualmente puesto que el usuario todavía no está en sesión
            usuario.setAudFecha(new Date());
            usuario.setAudUsuario("ArtaeL");
            usuario.getPerId().setAudFecha(new Date());
            usuario.getPerId().setAudUsuario("ArtaeL");
            genericDAOBean.save(getUsuario().getPerId());
            genericDAOBean.save(getUsuario());
            PrimeFacesPopup.lanzarDialog(Effects.Fold, "Notificacion", "Usuario creado correctamente, en los próximos minutos recibirá un correo con la información de autenticación en la plataforma.", true, false);
            PrimeFacesContext.execute("PF('dialogRegistro').hide()");
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error Registro", "Lo sentimos pero no ha sido posible crear el usuario, por favor intente nuevamente, o contacte al administrador.", true, false);
            logger.error("Error interno creando usuario, causado por " + e);
        }
    }

    /**
     * Cerrado de session de usuario.
     */
    public void salir(boolean redirec) {
        SessionUtils.asignarValor("marineruser", null);
        SessionUtils.asignarValor("marinerperson", null);
        SessionUtils.asignarValor("auth", "N");
        // Terminamos session AS
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            if(redirec){
                redireccionar(null);
            }
        } catch (Exception e) {
            logger.error("Error interno Finalizando session, error causado por: ", e);
        }
    }
    
    /**
     * Obtiene la URL de la página actual.
     * @return 
     */
    public String obtenerPaginaActual(){
        HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String URL = origRequest.getRequestURL().toString();
        return URL;
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
    
    /**
     * Verifica que la clave ingresada para cambiar el password sea la misma.
     * 
     * @return 
     */
    public boolean verificarClaveAntigua(){
        boolean valido = false;
        try {
            String claveActual = CryptoUtils.decrypt(usuario.getUsuPassword());
            if(claveAntigua.equals(claveActual)){
                valido = true;
            }
        } catch (Exception e) {
            logger.error("Error validando las claves, causado por: " + e);
        }
        return valido;
    }
    
    /**
     * Limpia todos los campos de contraseña para el cambio.
     */
    public void limpiarCampos(){
        claveAntigua = "";
        claveNueva = "";
        claveNueva = "";
    }
    
    /**
     * Cambia la contraseña actual del usuario.
     */
    public void cambiarContrasena() throws ValidatorException {
        try {
            if (verificarClaveAntigua()) {
                if(claveAntigua.equals(claveNueva)){
                    PrimeFacesPopup.lanzarDialog(Effects.Bounce, "Claves iguales", "La clave nueva no puede ser igual a la anterior", true, false);
                    return;
                }
                contrasenaEntidad = LoginUtils.obtenerEntidadDeConstante();
                if(!LoginUtils.validarPatronContrasena(claveNueva, contrasenaEntidad)){
                    String msj = "La contraseña no cumple las reglas definidas, debe tener al menos: ";
                    if(contrasenaEntidad.getLongitudMin() > 0 ){
                        msj = msj + contrasenaEntidad.getLongitudMin() + " caracteres. ";
                    }
                    if(contrasenaEntidad.getMinusculasMin() > 0 ){
                        msj = msj + contrasenaEntidad.getMinusculasMin() + " minúsculas. ";
                    }
                    if(contrasenaEntidad.getMayusculasMin() > 0 ){
                        msj = msj + contrasenaEntidad.getMayusculasMin() + " mayúsculas. ";
                    }
                    if(contrasenaEntidad.getNumerosMin() > 0 ){
                        msj = msj + contrasenaEntidad.getNumerosMin() + " números. ";
                    }
                    if(contrasenaEntidad.getSimbolosMin() > 0 ){
                        msj = msj + contrasenaEntidad.getSimbolosMin() + " símbolos. ";
                    }       
                    PrimeFacesPopup.lanzarDialog(Effects.Clip, "Contraseña insegura", msj, true, false);
                    return;
                }
                usuario.setUsuPassword(CryptoUtils.encrypt(claveNueva));
                usuario.setUsuCambioClave("N");
                auditSessionUtils.setAuditReflectedValues(usuario);
                genericDAOBean.merge(usuario);
                PrimeFacesPopup.lanzarDialog(Effects.Clip, "Cambio realizado", "Contraseña cambiada correctamente", true, false);
                PrimeFacesContext.execute("PF('dialogContrasena').hide();");
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Bounce, "Clave inválida", "La contraseña antigua no coincide", true, false);
            }
        } catch (Exception e) {
            logger.error("Error cambiado las claves, causado por: " + e);
        }
    }
    
    /**
     * Cierra la sesión y notifica al usuario.
     */
    public void cerrarSesionTiempo(){
        salir(false);
        PrimeFacesContext.execute("PF('dialogExpirado').show()");
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

    public String getClaveAntigua() {
        return claveAntigua;
    }

    public void setClaveAntigua(String claveAntigua) {
        this.claveAntigua = claveAntigua;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getClaveNuevaRep() {
        return claveNuevaRep;
    }

    public void setClaveNuevaRep(String claveNuevaRep) {
        this.claveNuevaRep = claveNuevaRep;
    }

    public boolean isTieneCaptcha() {
        return tieneCaptcha;
    }

    public void setTieneCaptcha(boolean tieneCaptcha) {
        this.tieneCaptcha = tieneCaptcha;
    }

    public ContrasenaEntidad getContrasenaEntidad() {
        return contrasenaEntidad;
    }

    public void setContrasenaEntidad(ContrasenaEntidad contrasenaEntidad) {
        this.contrasenaEntidad = contrasenaEntidad;
    }
    
    
    

}
