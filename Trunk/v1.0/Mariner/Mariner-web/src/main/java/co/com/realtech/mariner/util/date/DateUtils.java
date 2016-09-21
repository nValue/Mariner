package co.com.realtech.mariner.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utilidades para manejo de fechas.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class DateUtils {

    /**
     * Retorna la fecha libre de horas minutos y segundos.
     *
     * @param fecha
     * @return
     */
    public static Date getZeroTimeDate(Date fecha) {
        Date res = fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        res = calendar.getTime();
        return res;
    }
    /**
     * Validacion para validar fecha con horario adicional Banco.
     * @param fechaVencimiento
     * @return 
     */
    public static boolean permitePagoHorarioAdicional(String fechaVencimiento) {
        boolean salida = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(fechaVencimiento);
            Date fechaActual = DateUtils.getZeroTimeDate(new Date());

            if (fecha.equals(fechaActual)) {
                Date horaActual = new Date();
                if (horaActual.getHours() >= 16) {
                    salida = false;
                }
            }
        } catch (Exception e) {
            salida = true;
        }
        return salida;
    }

}
