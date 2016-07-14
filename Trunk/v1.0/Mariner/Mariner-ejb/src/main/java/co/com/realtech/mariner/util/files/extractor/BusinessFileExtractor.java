package co.com.realtech.mariner.util.files.extractor;

import co.com.realtech.mariner.model.entity.MarCarguesAsobancaria;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Utilidades de extraccion de archivos planos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
public class BusinessFileExtractor {

    /**
     * Extraer registro de archivo asobancaria
     *
     * @param usuId
     * @param is
     * @param omitirLineas
     * @param segmentoReferencia
     * @param segmentoValor
     * @param bloque
     * @return
     * @throws Exception
     */
    public static List<MarCarguesAsobancaria> extraerDatosArchivoBancario(MarUsuarios usuId,InputStream is, String omitirLineas, String segmentoReferencia, String segmentoValor, Integer bloque) throws Exception {
        List<MarCarguesAsobancaria> datos = new ArrayList<>();
        try {
            BufferedReader br = null;
            int segReferenciaInicial = new Integer(segmentoReferencia.split("-")[0]);
            int segReferenciaFinal = new Integer(segmentoReferencia.split("-")[1]);
            int segValorInicial = new Integer(segmentoValor.split("-")[0]);
            int segValorFinal = new Integer(segmentoValor.split("-")[1]);
            List<Integer> arregloExcluciones=new ArrayList<>();
            
            for(String val:omitirLineas.split("-")){
                arregloExcluciones.add(new Integer(val));
            }
            
            System.out.println("NEMTHYS:" + omitirLineas + "," + segmentoReferencia + "," + segmentoValor + "," + bloque);

            String line;
            br = new BufferedReader(new InputStreamReader(is));
            Integer counter = 1;
            while ((line = br.readLine()) != null) {
                if(!arregloExcluciones.contains(counter)){
                    try {
                        MarCarguesAsobancaria datoBancaria=new MarCarguesAsobancaria();
                        datoBancaria.setCasBloque(bloque);
                        datoBancaria.setCasEstado("P");
                        datoBancaria.setCasFecha(new Date());
                        datoBancaria.setCasLineaCompleta(line);
                        datoBancaria.setCasReferencia(line.substring(segReferenciaInicial, segReferenciaFinal));
                        System.out.println("Valor :"+line.substring(segValorInicial, segValorFinal)+"+"+segValorInicial+"+"+segValorFinal);
                        datoBancaria.setCasValor(new BigInteger(line.substring(segValorInicial, segValorFinal)));
                        datoBancaria.setUsuId(usuId);
                        datos.add(datoBancaria);
                        System.out.println("Line:" + line);
                    } catch (Exception e) {
                        System.out.println("Omitida linea "+line+", causado por "+e);
                    }                    
                }                
                counter++;
            }
        } catch (NumberFormatException | IOException e) {
            throw e;
        }
        return datos;
    }

}
