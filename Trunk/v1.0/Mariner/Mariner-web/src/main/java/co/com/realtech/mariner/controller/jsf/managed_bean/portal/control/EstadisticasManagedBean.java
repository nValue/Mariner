package co.com.realtech.mariner.controller.jsf.managed_bean.portal.control;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.reportes.RolesReportesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarReportesGraficos;
import co.com.realtech.mariner.model.entity.MarReportesParametros;
import co.com.realtech.mariner.model.entity.MarRolesReportes;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 * ManagedBean encargado de la utilidad de Generacion de Estadisticas.
 *
 * @author Fabián Agudelo
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class EstadisticasManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "RolesReportesDAOBean")
    private RolesReportesDAOBeanLocal rolesReportesDAOBean;
    
    private Date fechaDesde;
    private Date fechaHasta;

    private List<MarReportesParametros> reportesParametros;
    private MarReportesParametros reporteParametroSel;

    private List<MarReportesGraficos> reportesGraficos;
    private MarReportesGraficos reporteGraficoSel;
    
    private List<MarRolesReportes> rolesReportes;
    private MarRolesReportes rolReporteSel;

    private List<List<Object[]>> listaListas;
    private List<Object[]> listaListasObjetosSel;

    private String parametros;

    @Override
    public void init() {
        try {
            parametros = "";
            Calendar cal = Calendar.getInstance();
            setFechaHasta(cal.getTime());
            setFechaDesde(cal.getTime());
            obtenerReportes();
            obtenerParametrosDelReporte();
        } catch (Exception e) {
            logger.error("Error inicializando ManagedBean de estadísticas, causado por ", e);
        }
    }

    /**
     * Obtiene todos los reportes estadísticos para todos los roles de un usuario.
     */
    public void obtenerReportes() {
        try {
            rolesReportes = rolesReportesDAOBean.obtenerReportesPorUsuario(usuarioSesion);
            if (!rolesReportes.isEmpty()) {
                rolReporteSel = rolesReportes.get(0);
            }
        } catch (Exception e) {
            logger.error("No se pueden extraer los reportes asociados al rol, debido a : " + e, e);
        }
    }

    /**
     * Obtiene el gráfico asociado al reporte si existe.
     */
    public void obtenerGrafico() {
        try {
            reporteGraficoSel = (MarReportesGraficos) genericDAOBean.findByColumn(MarReportesGraficos.class, "repId", rolReporteSel.getRepId());
        } catch (Exception e) {
            logger.error("No se puede extraer el gráfico del reporte, debido a : " + e, e);
        }
    }

    /**
     * Obtiene los parametros del reporte seleccionado.
     */
    public void obtenerParametrosDelReporte() {
        try {
            obtenerGrafico();
            reportesParametros = (List<MarReportesParametros>) genericDAOBean.findAllByColumn(MarReportesParametros.class, "repId", rolReporteSel.getRepId());
            if (!reportesParametros.isEmpty()) {
                crearListasDeParametros();
                filtrarEstadisticas();
            }
        } catch (Exception e) {
            logger.error("No se pueden obtener los parametros del reporte, debido a : " + e, e);
        }
    }

    /**
     * Genera un arrayList con todos los parámetros que sean tipo lista.
     */
    public void crearListasDeParametros() {
        listaListas = new ArrayList<>();
        listaListasObjetosSel = new ArrayList<>();
        List<Object[]> objetosLista;
        for (MarReportesParametros reporteParametro : reportesParametros) {
            objetosLista = new ArrayList<>();
            if (reporteParametro.getRpaTipo().equals("LISTA")) {
                objetosLista = obtenerListaDeReporteSel(reporteParametro);
            }
            if (objetosLista.isEmpty()) {
                listaListasObjetosSel.add(null);
            } else {
                listaListasObjetosSel.add(objetosLista.get(0));
            }
            listaListas.add(objetosLista);
        }
    }

    /**
     * Obtiene un arreglo de objetos para una lista de un reporte parámetro
     *
     * @param repPar
     * @return
     */
    public List<Object[]> obtenerListaDeReporteSel(MarReportesParametros repPar) {
        List<Object[]> valoresLista = new ArrayList<>();
        try {
            String consulta = repPar.getRpaQueryLista();
            consulta = consulta.replaceAll("%USUARIO_ACTUAL%", usuarioSesion.getUsuId().toString());
            valoresLista = (List<Object[]>) genericDAOBean.executeNativeQuery(consulta, false);
            if (valoresLista.isEmpty()) {
                valoresLista = new ArrayList<>();
            } else {
                listaListasObjetosSel.add(valoresLista.get(0));
            }
        } catch (Exception e) {
            logger.error("No se pueden traer las listas de valores para el parámetro " + repPar.getRpaNombre() + ", debido a : " + e, e);
        }
        return valoresLista;
    }

    /**
     * Realizar filtrado de Estadisticas.
     */
    public void filtrarEstadisticas() {
        try {
            parametros = "";
            UIComponent htmlForm = FacesContext.getCurrentInstance().getViewRoot();
            for (MarReportesParametros reporteParametro : reportesParametros) {
                String id = "cmp" + reporteParametro.getRpaTipo() + "_" + reporteParametro.getRpaId();
                UIComponent input = htmlForm.findComponent("formStatistics:" + id);
                Class obj = input.getClass();
                Method metValor = obj.getMethod("getSubmittedValue");
                Object valor = metValor.invoke(input);
                if (reporteParametro.getRpaTipo().equals("LISTA")) {
                    SelectOneMenu som = (SelectOneMenu) input;
                    Object value = som.getValue();
                    if (value == null) {
                        value = som.getSubmittedValue();
                    }
                    valor = value;
                } else if (valor == null) {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valores faltantes", "Debe llenar todos los campos para poder generar el reporte", true, false);
                    return;
                }
                parametros += valor + "-";
            }
        } catch (Exception e) {
            logger.info("Error realizando filtrado de estadisticas en StatisticsManagedBean, causado por " + e);
        }
    }

    /**
     * Retorna la fecha desde Formateada
     *
     * @return
     */
    public String getFechaDesdeFormated() {
        String salida;
        SimpleDateFormat ds = new SimpleDateFormat("dd/MM/yyyy");
        salida = ds.format(getFechaDesde());
        return salida;
    }

    /**
     * Retorna la fecha desde Formateada
     *
     * @return
     */
    public String getFechaHastaFormated() {
        String salida;
        SimpleDateFormat ds = new SimpleDateFormat("dd/MM/yyyy");
        salida = ds.format(getFechaHasta());
        return salida;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
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

    public MarRolesReportes getRolReporteSel() {
        return rolReporteSel;
    }

    public void setRolReporteSel(MarRolesReportes rolReporteSel) {
        this.rolReporteSel = rolReporteSel;
    }

    public MarReportesGraficos getReporteGraficoSel() {
        return reporteGraficoSel;
    }

    public void setReporteGraficoSel(MarReportesGraficos reporteGraficoSel) {
        this.reporteGraficoSel = reporteGraficoSel;
    }

    public List<MarRolesReportes> getRolesReportes() {
        return rolesReportes;
    }

    public void setRolesReportes(List<MarRolesReportes> rolesReportes) {
        this.rolesReportes = rolesReportes;
    }

    public List<MarReportesParametros> getReportesParametros() {
        return reportesParametros;
    }

    public void setReportesParametros(List<MarReportesParametros> reportesParametros) {
        this.reportesParametros = reportesParametros;
    }

    public MarReportesParametros getReporteParametroSel() {
        return reporteParametroSel;
    }

    public void setReporteParametroSel(MarReportesParametros reporteParametroSel) {
        this.reporteParametroSel = reporteParametroSel;
    }
    
    

}
