package co.com.realtech.mariner.util.mail;

import java.io.File;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * Gestor de envio de correos electronicos, utiliza hilos para que la interfaces
 * no se queden esperando respuesta en caso de que el servidor de correos falle.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.9
 */
public class MailSender {

    final static Logger logger = Logger.getLogger(MailSender.class);

    /**
     * Envio de correo electronico utilizando Hilos.
     *
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     */
    public static void sendMail(final String to, final String cc, final String bcc, final String subject, final String content) {
        try {
            Runnable t = new Runnable() {
                @Override
                public void run() {
                    // Procesado de correo electronico en nuevo hilo de procesamiento.
                    MailUtils mailUtils = new MailUtils();
                    mailUtils.sendMail(to, cc, bcc, subject, content, null, null);
                }
            };
            Thread hilo = new Thread(t);
            hilo.start();
        } catch (Exception e) {
            logger.error("Error enviando correo electronico a " + to + " causado por:", e);
        }
    }

    /**
     * Envio de correo electronico utilizando Hilos y plantilla Velocity
     * indicada.
     *
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     * @param vmTemplate
     * @param parameters
     * @param useThread
     */
    public static void sendMail(final String to, final String cc, final String bcc, final String subject, final String content, final String vmTemplate, final HashMap<String, Object> parameters, boolean useThread) {
        try {
            if (useThread) {
                Runnable t = new Runnable() {
                    @Override
                    public void run() {
                        // Procesado de correo electronico en nuevo hilo de procesamiento.                        
                        MailUtils mailUtils = new MailUtils();
                        mailUtils.sendMail(to, cc, bcc, subject, content, vmTemplate, parameters);
                    }
                };
                Thread hilo = new Thread(t);
                hilo.start();
            } else {
                MailUtils mailUtils = new MailUtils();
                mailUtils.sendMail(to, cc, bcc, subject, content, vmTemplate, parameters);
            }
        } catch (Exception e) {
            logger.error("Error enviando correo electronico a " + to + " causado por:", e);
        }
    }

    /**
     * Envio de correo electronico utilizando Hilos y enviando adjunto indicada.
     *
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     * @param vmTemplate
     * @param parameters
     * @param useThread
     * @param archivo
     * @param extension
     */
    public static void sendMailWithAttachment(final String to, final String cc, final String bcc, final String subject, final String content, final String vmTemplate, final HashMap<String, Object> parameters, boolean useThread, final File archivo, final String extension) {
        try {
            if (useThread) {
                Runnable t = new Runnable() {
                    @Override
                    public void run() {
                        // Procesado de correo electronico en nuevo hilo de procesamiento.                        
                        MailUtils mailUtils = new MailUtils();
                        try {
                            mailUtils.sendMail(to, cc, bcc, subject, content, archivo, extension, vmTemplate, parameters);
                        } catch (Exception ex) {
                            logger.error("Error enviando correo electronico a " + to + " causado por:", ex);
                        }
                    }
                };
                Thread hilo = new Thread(t);
                hilo.start();
            } else {
                MailUtils mailUtils = new MailUtils();
                mailUtils.sendMail(to, cc, bcc, subject, content, archivo, extension, vmTemplate, parameters);
            }
        } catch (Exception e) {
            logger.error("Error enviando correo electronico a " + to + " causado por:", e);
        }
    }
}
