package co.com.realtech.mariner.controller.jsf.managed_bean.util;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Controlador JSF de utilidades
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@ManagedBean
@RequestScoped
public class UtilManagedBean {

    private TimeZone timeZone;

    @PostConstruct
    public void init() {
        setTimeZone(TimeZone.getDefault());
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

}
