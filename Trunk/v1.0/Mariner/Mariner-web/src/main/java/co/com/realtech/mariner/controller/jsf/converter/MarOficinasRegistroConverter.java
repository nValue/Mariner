package co.com.realtech.mariner.controller.jsf.converter;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarOficinasRegistros;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.math.BigDecimal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Conversor JSF entidad MarOficinasRegistro.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@FacesConverter(value = "MarOficinasRegistroConverter")
public class MarOficinasRegistroConverter implements Converter {

    final static Logger logger = Logger.getLogger(MarOficinasRegistroConverter.class);
    private GenericDAOBeanLocal genericDAOBeanLocal;

    public MarOficinasRegistroConverter() {
        try {
            InitialContext ic = new InitialContext();
            genericDAOBeanLocal = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
        } catch (Exception e) {
            logger.error("Error inicializando el converter MarOficinasRegistroConverter, causado por " + e, e);
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        MarOficinasRegistros tipo = null;
        try {
            tipo = (MarOficinasRegistros) genericDAOBeanLocal.findByID(MarOficinasRegistros.class, new BigDecimal(value));
        } catch (Exception ex) {
            tipo=null;
        }
        return tipo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((MarOficinasRegistros) value).getMorId());
        } catch (Exception e) {
            return null;
        }
    }

}
