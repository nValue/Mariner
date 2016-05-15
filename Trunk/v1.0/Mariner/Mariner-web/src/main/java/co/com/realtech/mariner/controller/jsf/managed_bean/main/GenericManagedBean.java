package co.com.realtech.mariner.controller.jsf.managed_bean.main;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.session.AuditSessionUtils;
import co.com.realtech.mariner.util.session.SessionUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.apache.log4j.Logger;

/**
 * Clase abstracta genérica para todos los ManagedBeans de la aplicación que contiene las 
 * implementaciones sugeridas.
 * @author fabianagudelo
 */
public abstract class GenericManagedBean implements Serializable{
    
    protected AuditSessionUtils auditSessionUtils = AuditSessionUtils.create();
    protected Logger logger = Logger.getLogger(super.getClass());
    protected MarUsuarios usuarioSesion = (MarUsuarios) SessionUtils.obtenerValorGeneric("marineruser");
    
    @EJB(beanName = "GenericDAOBean")
    protected GenericDAOBeanLocal genericDAOBean;
    
    @PostConstruct
    public abstract void init();
    
}
