package co.com.realtech.mariner.controller.jsf.managed_bean.portal.utilidades.ws;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.ws.sap.WSSAPConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDPRNCAB;
import co.com.realtech.mariner.model.ejb.ws.sap.mappers.get_detail_method.ZPSCDTTVURDETAIL;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.ws.Holder;

/**
 * Controlador JSF manejo de utilidades consumo de WS SAP
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class WSSOAPTesterGetDetailManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "WSSAPConsumerBean")
    private WSSAPConsumerBeanLocal wSSAPConsumerBean;

    private String codigoLiquidacion;

    @Override
    public void init() {
        try {

        } catch (Exception e) {
            logger.error("Error inicializando WSSOAPTesterManagedBean, causado por " + e);
        }
    }

    /**
     * Ejecutar metodo getDetails del servicios web.
     */
    public void ejecutarMetodo() {
        try {
            Holder<ZPSCDPRNCAB> responseHeader = new Holder<>();
            Holder<ZPSCDTTVURDETAIL> responseDetail = new Holder<>();
            wSSAPConsumerBean.getDetail(getCodigoLiquidacion(), responseHeader, responseDetail);
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Servicio web ejecutado correctamente, parametro:"+responseHeader.value.getVFECLIQ(), true, false);
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Error consultando meotodo getDetail, causado por " + e, true, false);
            logger.error("Error ejecutando metodo get Details, causado por " + e);
        }
    }

    public String getCodigoLiquidacion() {
        return codigoLiquidacion;
    }

    public void setCodigoLiquidacion(String codigoLiquidacion) {
        this.codigoLiquidacion = codigoLiquidacion;
    }

}
