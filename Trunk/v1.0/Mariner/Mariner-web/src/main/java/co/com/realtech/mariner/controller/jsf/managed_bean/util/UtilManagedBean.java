package co.com.realtech.mariner.controller.jsf.managed_bean.util;

import java.io.Serializable;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Controlador de Utilidades Request JSF
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@RequestScoped
public class UtilManagedBean implements Serializable {

    private TimeZone timeZone;

    public UtilManagedBean() {
    }

    @PostConstruct
    public void init() { 
    }

    public TimeZone getTimeZone() { 
        return TimeZone.getTimeZone("GMT-5:00");
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

}
