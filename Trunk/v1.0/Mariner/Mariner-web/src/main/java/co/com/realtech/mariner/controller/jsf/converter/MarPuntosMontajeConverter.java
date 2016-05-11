package co.com.realtech.mariner.controller.jsf.converter;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarPuntosMontajes;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.math.BigDecimal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Conversor JSF entidad MarPuntosMontajes.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@FacesConverter(value = "MarPuntosMontajeConverter")
public class MarPuntosMontajeConverter implements Converter {

    final static Logger logger = Logger.getLogger(MarPuntosMontajeConverter.class);

    private GenericDAOBeanLocal genericDAOBeanLocal;

    public MarPuntosMontajeConverter() {
        try {
            InitialContext ic = new InitialContext();
            genericDAOBeanLocal = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
        } catch (Exception e) {
            logger.error("Error inicializando el converter, causado por " + e, e);
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        MarPuntosMontajes tipo = null;
        try {
            tipo = (MarPuntosMontajes) genericDAOBeanLocal.findByID(MarPuntosMontajes.class, new BigDecimal(value));
        } catch (Exception ex) {
            logger.error("Error en Conversor, causado por ", ex);
        }
        return tipo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((MarPuntosMontajes) value).getPmoId());
    }

}
