package co.com.realtech.mariner.util.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.IOUtils;

/**
 * Utilidades persistencia.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
public class HibernateUtils {

    public static void main(String... args) {
        System.out.println("Iniciando" + new Date());
        try {
            String rutaBase = "C:\\VURValle\\fuentes\\Mariner\\Mariner-ejb\\src\\main\\java\\co\\com\\realtech\\mariner\\model\\entity";
            String anotacion;

            File folder = new File(rutaBase);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                anotacion = "    @GeneratedValue(generator = \"!SQLNOMBRE\")\n"
                        + "    @SequenceGenerator(name = \"!SQLNOMBRE\", sequenceName = \"!SQLNOMBRE\")";
                if (file.isFile() && file.getName().endsWith("java")) {
                    System.out.println("Analizando archivo : " + file.getName());
                    InputStream in;
                    List<String> lines;
                    in = new FileInputStream(file);
                    lines = IOUtils.readLines(in);
                    int counter = 0;
                    int indexAnotation = 0;
                    String tabla = "";
                    List<String> finalLines = new ArrayList<String>();
                    for (String line : lines) {
                        finalLines.add(line);
                        if (line.contains("Table(name =")) {
                            tabla = line.split("\"")[1];
                            if (tabla.length() > 27) {
                                tabla = tabla.substring(0, 27);
                            }
                        }
                        if (line.contains("@Id")) {
                            anotacion = anotacion.replaceAll("!SQLNOMBRE", ("SQ_" + tabla).toLowerCase());
                            finalLines.add(anotacion);
                            indexAnotation = counter;
                        }
                        counter++;
                    }

                    System.out.println("Entidad:" + tabla + " en " + indexAnotation + " - " + anotacion);
                    for (String g : finalLines) {
                        System.out.println(g);
                    }

                    FileWriter f0 = new FileWriter(file, false);
                    boolean valImp = false;
                    for (String g : finalLines) {
                        if (!valImp) {
                            if (g.startsWith("import javax.persistence")) {
                                f0.write("import javax.persistence.GeneratedValue;\n");
                                f0.write("import javax.persistence.SequenceGenerator;\n");
                                valImp = true;
                            }
                        }
                        f0.write(g + "\n");
                    }
                    f0.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error generando secuencias, causado por " + e);
        }
        System.out.println("Finalizado " + new Date());
    }
}
