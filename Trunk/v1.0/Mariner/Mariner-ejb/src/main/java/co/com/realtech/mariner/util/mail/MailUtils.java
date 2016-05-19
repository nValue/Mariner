package co.com.realtech.mariner.util.mail;

import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * Clase encargada del a gestion de envio de correos electronicos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.7
 */
public class MailUtils {

    final Logger logger = Logger.getLogger(MailUtils.class);
    private File archivo = null;
    private String extension;

    /**
     * Envio de correo con archivo adjunto.
     *
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     * @param archivo
     * @param extension
     * @param vmTemplate
     * @param parameters
     * @throws Exception
     */
    public void sendMail(String to, String cc, String bcc, String subject, String content, File archivo, String extension, String vmTemplate, HashMap<String, Object> parameters) throws Exception {
        setArchivo(archivo);
        setExtension(extension);
        sendMail(to, cc, bcc, subject, content, vmTemplate, parameters);
    }

    /**
     * Envio generico de correo electronico.
     *
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     * @param vmTemplate
     * @param parameters
     */
    public void sendMail(String to, String cc, String bcc, String subject, String content, String vmTemplate, HashMap<String, Object> parameters) {
        try {
            final String username = ConstantesUtils.cargarConstante("SMTP-USER");
            final String password = ConstantesUtils.cargarConstante("SMTP-PASS");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", ConstantesUtils.cargarConstante("SMTP-HOST"));
            props.put("mail.smtp.port", ConstantesUtils.cargarConstante("SMTP-PORT"));

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSentDate(new Date());

            // envio a varios destinatarios
            if (to != null) {
                for (String tos : to.split(";")) {
                    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(tos));
                }
            } else {
                throw new Exception("Debe ingresar una direccion de correo electronico valida para el Destinatario");
            }

            if (cc != null) {
                for (String ccs : cc.split(";")) {
                    message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccs));
                }
            }

            if (bcc != null) {
                for (String bccs : bcc.split(";")) {
                    message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccs));
                }
            }
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            if (vmTemplate == null) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(content, "text/html; charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
            } else {
                // Cargamos las ubicaciones del contexto de plantillas velocity.
                String snrHome = ConstantesUtils.cargarConstante("VUR-HOME");
                String pathSeparator = File.separator;

                String templatePath = snrHome + "velocity" + pathSeparator + "templates" + pathSeparator;

                // utilizamos plantilla velocity indicada
                VelocityEngine ve = new VelocityEngine();
                VelocityContext context = new VelocityContext();

                Properties p = new Properties();
                p.setProperty("file.resource.loader.path", templatePath);

                ve.init(p);

                for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                    try {
                        context.put(entry.getKey(), entry.getValue().toString().trim());
                    } catch (Exception e) {
                        context.put(entry.getKey(), entry.getValue());
                    }
                }

                Template t = ve.getTemplate(vmTemplate);
                t.setEncoding("UTF-8");
                StringWriter writer = new StringWriter();
                t.merge(context, writer);

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(writer.toString(), "text/html; charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
            }

            // Verficamos si tiene adjunto
            if (archivo != null) {
                MimeBodyPart attachMent = new MimeBodyPart();
                DataSource source = new FileDataSource(getArchivo());
                attachMent.setDataHandler(new DataHandler(source));
                attachMent.setFileName(getArchivo().getName() + "." + getExtension());
                multipart.addBodyPart(attachMent);
            }
            message.setContent(multipart);
            Transport.send(message);
            logger.info("Correo electronico enviado correctamente a la direccion " + to);
        } catch (Exception e) {
            logger.error("Error enviando correo electronico a direccion " + to + " causado por: ", e);
        }
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
