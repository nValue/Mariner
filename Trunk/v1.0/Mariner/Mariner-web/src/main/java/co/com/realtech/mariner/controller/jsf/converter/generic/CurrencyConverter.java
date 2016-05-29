package co.com.realtech.mariner.controller.jsf.converter.generic;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;


/**
 * Converter encargado de mostrar todos los números en formato de peso.
 * @author fabianagudelo
 */
@FacesConverter("CurrencyConverter")
public class CurrencyConverter implements Converter {

@Override
public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
    return null;
}

@Override
public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    if (value == null) {
        return null;
    } else {
        if (value.toString().trim().equals("")) {
            return null;
        }
        try {
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(0);
            return format.format(new BigDecimal(value.toString()));
        } catch (Exception exception) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en conversión", "No es un número válido"));
        }
    }
}}