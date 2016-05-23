package co.com.realtech.mariner.controller.jsf.converter.generic;

import co.com.realtech.mariner.controller.jsf.managed_bean.portal.business.FinalizacionManagedBean;
import co.com.realtech.mariner.model.entity.generic.ClaveValor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Conversor JSF entidad genérica ClaveValorConverter
 *
 * @author Fabián Agudelo
 * @version 1.0
 * @since JDK1.8
 */
@FacesConverter(value = "ClaveValorConverter")
public class ClaveValorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        FacesContext contexto = FacesContext.getCurrentInstance();
        FinalizacionManagedBean finalizacionMB = (FinalizacionManagedBean) contexto.getELContext().getELResolver().getValue(context.getELContext(), null, "finalizacionManagedBean");
        Object obj = finalizacionMB.obtenerClaveValor(value);
        return obj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((ClaveValor) value).getClave());
    }

}
