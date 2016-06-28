package co.com.realtech.mariner.controller.jsf.push;

import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

/**
 *
 * @author fabianagudelo
 */
@PushEndpoint("/estados/{usuId}")
@Singleton
public class NotifyResource {
    
    @PathParam("usuId")
    private String usuId;

    @Inject
    private ServletContext ctx;

    
    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
        return message;
    }
 
}