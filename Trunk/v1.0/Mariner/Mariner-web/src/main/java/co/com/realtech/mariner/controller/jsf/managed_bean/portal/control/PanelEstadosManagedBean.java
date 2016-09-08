package co.com.realtech.mariner.controller.jsf.managed_bean.portal.control;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.entity.MarReportes;
import co.com.realtech.mariner.model.entity.MarReportesGraficos;
import co.com.realtech.mariner.model.entity.MarReportesParametros;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * ManagedBean encargado del graficador de estados
 *
 * @author Fabián Agudelo
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class PanelEstadosManagedBean extends GenericManagedBean implements Serializable {

    private Date fecha;

    private MarReportes reporteSel;
    private MarReportesGraficos reporteGraficoSel;
    
    private List<List<Object[]>> listaListas;
    private List<Object[]> listaListasObjetosSel;

    private String parametros;

    @Override
    public void init() {
        try {
            parametros = "";
            Calendar cal = Calendar.getInstance();
            fecha = (cal.getTime());
            obtenerReporte();
            obtenerGrafico();
        } catch (Exception e) {
            logger.error("Error inicializando ManagedBean de estadísticas, causado por ", e);
        }
    }

    /**
     * Obtiene el reporte necesario para visualizar el panel de los estados.
     */
    public void obtenerReporte() {
        try {
            reporteSel = (MarReportes) genericDAOBean.findByColumn(MarReportes.class, "repCodigo", "HTM");
        } catch (Exception e) {
            logger.error("No se pueden extraer los reportes asociados al rol, debido a : " + e, e);
        }
    }

    /**
     * Obtiene el gráfico asociado al reporte si existe.
     */
    public void obtenerGrafico() {
        try {
            reporteGraficoSel = (MarReportesGraficos) genericDAOBean.findByColumn(MarReportesGraficos.class, "repId", reporteSel);
        } catch (Exception e) {
            logger.error("No se puede extraer el gráfico del reporte, debido a : " + e, e);
        }
    }

    /**
     * Realizar filtrado de Estadisticas.
     */
    public void filtrarEstadisticas() {
        try {
            parametros = "";
            UIComponent htmlForm = FacesContext.getCurrentInstance().getViewRoot();
        } catch (Exception e) {
            logger.info("Error realizando filtrado de estadisticas en StatisticsManagedBean, causado por " + e);
        }
    }

    /**
     * Retorna la fecha desde Formateada
     *
     * @return
     */
    public String getFechaFormated() {
        String salida;
        SimpleDateFormat ds = new SimpleDateFormat("dd/MM/yyyy");
        salida = ds.format(fecha);
        return salida;
    }
    
    /**
     * Obtiene las propiedades con el tamaño dinámico para mostrar el gráfico
     * @return 
     */
    public String obtenerPropiedades(){
        String props = "type: \"heatmap\",renderAt: \"chartContainerPie\",width: \"86%\",height: \"%WIDTH%\"";
        int altoInt = 200;
        try {
            HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String path = origRequest.getRequestURL().toString();
            path = path.substring(0, path.length() - origRequest.getRequestURI().length()) + origRequest.getContextPath() + "/";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            path = path + "/graphics/ServletGraphicsStatistics?id=" + reporteSel.getRepId() + "&fechaDesde=" + getFechaFormated() + "&fechaHasta=" + getFechaFormated();
            
            InputStream inputStream = new URL(path).openStream();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            Document doc = db.parse(is);

            NodeList list = doc.getElementsByTagName("row");
            altoInt = altoInt + (list.getLength() * 20);
            props = reporteGraficoSel.getRgrPropiedades();
        } catch (Exception e) {
            logger.error("No se puede obtener el XML gráfico para el tamaño, causado por : " + e );
        }
        String alto = String.valueOf(altoInt);
        props = props.replace("%WIDTH%", alto);
        System.out.println("props = " + props);
        return props;
    }

    public List<List<Object[]>> getListaListas() {
        return listaListas;
    }

    public void setListaListas(List<List<Object[]>> listaListas) {
        this.listaListas = listaListas;
    }

    public List<Object[]> getListaListasObjetosSel() {
        return listaListasObjetosSel;
    }

    public void setListaListasObjetosSel(List<Object[]> listaListasObjetosSel) {
        this.listaListasObjetosSel = listaListasObjetosSel;
    }

    public List<Object[]> getListaActual(String val) {
        int action = Integer.parseInt(val);
        return listaListas.get(action);
    }

    public void setListaActual(String val) {
        System.out.println("val = " + val);
    }

    public Object[] getValorSel() {
        String valorSel = "";
        Object[] o = {"OTRO_VALOR", "ANOT"};

        Map<String, String> params
                = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String action = params.get("action");
        return o;
    }

    public void setValorSel(Object[] obj) {
        System.out.println("obj = " + obj);
    }

    public void cambio(Object objetoSel) {
        String filterValue = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("validation");
        filtrarEstadisticas();
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public MarReportesGraficos getReporteGraficoSel() {
        return reporteGraficoSel;
    }

    public void setReporteGraficoSel(MarReportesGraficos reporteGraficoSel) {
        this.reporteGraficoSel = reporteGraficoSel;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public MarReportes getReporteSel() {
        return reporteSel;
    }

    public void setReporteSel(MarReportes reporteSel) {
        this.reporteSel = reporteSel;
    }

    
    
}
