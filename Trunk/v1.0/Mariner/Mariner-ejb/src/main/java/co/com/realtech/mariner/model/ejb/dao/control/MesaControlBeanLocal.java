package co.com.realtech.mariner.model.ejb.dao.control;

import co.com.realtech.mariner.model.entity.MarRadicaciones;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
/**
 * EJB encargado de toda la funcionalidad de persistencia de la mesa de control.
 * 
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@Local
public interface MesaControlBeanLocal {
    
    public List<MarRadicaciones> filtrarRadicacionesMesaControl(String tipoFiltro, String valor, String tipoDato,Date fechaInicial,Date fechaFinal) ;
    
}
