package co.com.realtech.mariner.controller.jsf.converter;

import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBean;
import co.com.realtech.mariner.model.ejb.dao.generic.GenericDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarReportesTipos;
import co.com.realtech.mariner.util.jdni.JDNIUtils;
import java.math.BigDecimal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import org.apache.log4j.Logger;

/**
 * Conversor JSF entidad MarReportesTiposConverter.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
@FacesConverter(value = "MarReportesTiposConverter")
public class MarReportesTiposConverter implements Converter {

    final static Logger logger = Logger.getLogger(MarReportesTiposConverter.class);

    private GenericDAOBeanLocal genericDAOBeanLocal;

    public MarReportesTiposConverter() {
        try {
            InitialContext ic = new InitialContext();
            genericDAOBeanLocal = (GenericDAOBeanLocal) ic.lookup(JDNIUtils.getEJBJDNIName(GenericDAOBean.class, Boolean.TRUE));
        } catch (Exception e) {
            logger.error("Error inicializando el converter MarCiudadesConverter, causado por " + e, e);
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        MarReportesTipos tipo = null;
        try {
            tipo = (MarReportesTipos) genericDAOBeanLocal.findByID(MarReportesTipos.class, new BigDecimal(value));
        } catch (Exception ex) {
            logger.error("Error en Conversor MarCiudadesConverter, causado por ", ex);
        }
        return tipo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((MarReportesTipos) value).getRtiId());
    }

}
