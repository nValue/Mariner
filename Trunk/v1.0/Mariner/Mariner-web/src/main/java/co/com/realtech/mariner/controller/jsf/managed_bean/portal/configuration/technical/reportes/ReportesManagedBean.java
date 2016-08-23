package co.com.realtech.mariner.controller.jsf.managed_bean.portal.configuration.technical.reportes;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.reportes.RolesReportesDAOBeanLocal;
import co.com.realtech.mariner.model.entity.MarReportesParametros;
import co.com.realtech.mariner.model.entity.MarRolesReportes;
import co.com.realtech.mariner.util.jsf.file.FileDownloader;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 *
 * @author fabianagudelo
 */
@ManagedBean
@ViewScoped
public class ReportesManagedBean extends GenericManagedBean implements Serializable{

    @EJB(beanName = "RolesReportesDAOBean")
    private RolesReportesDAOBeanLocal rolesReportesDAOBean;
    
    private List<MarRolesReportes> rolesReportes;
    private MarRolesReportes rolReporteSel;
    
    private List<MarReportesParametros> reportesParametros;
    private MarReportesParametros reporteParametroSel;
    
    private List<List<Object[]>> listaListas;
    private List<Object[]> listaListasObjetosSel;
    
    @PostConstruct
    public void init(){
        rolesReportes = new ArrayList<>();
        obtenerReportes();
        obtenerParametrosDelReporte();
    }
    
    /**
     * Obtiene todos los reportes para todos los roles de un usuario.
     */
    public void obtenerReportes(){
        try {
            rolesReportes = rolesReportes = rolesReportesDAOBean.obtenerReportesPorUsuarioYTipo(usuarioSesion,"ESPECIFICOS");
            if(!rolesReportes.isEmpty()){
                rolReporteSel = rolesReportes.get(0);
            }
        } catch (Exception e) {
            logger.error("No se pueden extraer los reportes asociados al rol, debido a : " + e, e);
        }
    }
    
    /**
     * Creates a new instance of ReportsManagedBean
     */
    public ReportesManagedBean() {
    }
    
    /**
     * Obtiene los parametros del reporte seleccionado.
     */
    public void obtenerParametrosDelReporte(){
        try {
            reportesParametros = (List<MarReportesParametros>)genericDAOBean.findAllByColumn(MarReportesParametros.class, "repId", rolReporteSel.getRepId());
            crearListasDeParametros();
        } catch (Exception e) {
            logger.error("No se pueden obtener los parametros del reporte, debido a : " + e, e);
        }
    }
    
    /**
     * Genera un arrayList con todos los parámetros que sean tipo lista.
     */
    public void crearListasDeParametros(){
        listaListas = new ArrayList<>();
        listaListasObjetosSel = new ArrayList<>();
        List<Object[]> objetosLista;
        for (MarReportesParametros reporteParametro : reportesParametros) {
            objetosLista = new ArrayList<>();
            if(reporteParametro.getRpaTipo().equals("LISTA")){
                objetosLista = obtenerListaDeReporteSel(reporteParametro);
            }
            if(objetosLista.isEmpty()){
                listaListasObjetosSel.add(null);
            }else{
                listaListasObjetosSel.add(objetosLista.get(0));
            }
            listaListas.add(objetosLista);
        }
    }
    
    /**
     * Obtiene un arreglo de objetos para una lista de un reporte parámetro
     * @param repPar
     * @return 
     */
    public List<Object[]> obtenerListaDeReporteSel(MarReportesParametros repPar){
        List<Object[]> valoresLista = new ArrayList<>();
        try {
            String consulta = repPar.getRpaQueryLista();
            consulta = consulta.replaceAll("%USUARIO_ACTUAL%", usuarioSesion.getUsuId().toString());
            valoresLista = (List<Object[]>)genericDAOBean.executeNativeQuery(consulta, false);
            if(valoresLista.isEmpty()){
               valoresLista = new ArrayList<>();
            }else{
                listaListasObjetosSel.add(valoresLista.get(0));
            }
        } catch (Exception e) {
            logger.error("No se pueden traer las listas de valores para el parámetro " + repPar.getRpaNombre() + ", debido a : " + e, e);
        }
        return valoresLista;
    }
    
    /**
     * Genera el reporte en formato excel.
     */
    public void generarReporte() {
        try {
            String query = rolReporteSel.getRepId().getRepConsulta();
            UIComponent htmlForm = FacesContext.getCurrentInstance().getViewRoot();
            for (MarReportesParametros reporteParametro : reportesParametros) {
                if(reporteParametro.getRpaTipo().equals("FECHAS")){
                    String id1 = "cmp1" + reporteParametro.getRpaTipo() + "_" + reporteParametro.getRpaId();
                    String id2 = "cmp2" + reporteParametro.getRpaTipo() + "_" + reporteParametro.getRpaId();
                    UIComponent input1 = htmlForm.findComponent("formReporte:"+id1);
                    UIComponent input2 = htmlForm.findComponent("formReporte:"+id2);
                    Class obj1 = input1.getClass();
                    Method metValor1 = obj1.getMethod("getSubmittedValue");
                    Class obj2 = input1.getClass();
                    Method metValor2 = obj2.getMethod("getSubmittedValue");
                    Object valor1 = metValor1.invoke(input1);
                    Object valor2 = metValor2.invoke(input2);
                    String alias1 = reporteParametro.getRpaAlias() + "1";
                    query = query.replaceAll(alias1, valor1.toString());
                    query = query.replaceAll((reporteParametro.getRpaAlias() + "2"), valor2.toString());
                } else if(reporteParametro.getRpaTipo().equals("USUARIO_ACTUAL")){
                    query = query.replaceAll(reporteParametro.getRpaAlias(), usuarioSesion.getUsuId().toString());
                } else {
                    String id = "cmp" + reporteParametro.getRpaTipo() + "_" + reporteParametro.getRpaId();
                    UIComponent input = htmlForm.findComponent("formReporte:" + id);
                    Class obj = input.getClass();
                    Method metValor = obj.getMethod("getSubmittedValue");
                    Object valor = metValor.invoke(input);
                    if (valor == null) {
                        PrimeFacesPopup.lanzarDialog(Effects.Slide, "Valores faltantes", "Debe llenar todos los campos para poder generar el reporte", true, false);
                        return;
                    }else if(reporteParametro.getRpaTipo().equals("LISTA")){
                        SelectOneMenu som = (SelectOneMenu)input;
                        Object value = som.getSubmittedValue();
                        valor = value;
                    }
                    query = query.replaceAll(reporteParametro.getRpaAlias(), valor.toString());
                }
            }
            if(query.charAt(query.length()-1) == ';'){
                query = query.substring(0, query.length()-1);
            }
            List<String> columnas = genericDAOBean.getColumnNamesFromQuery(query);
            if(columnas.isEmpty()){
                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "No hay datos para los parámetros seleccionados.", true, false);
                return;
            }
            List<Object> objeto = (List<Object>)genericDAOBean.executeNativeQuery(query, false);
            FileDownloader fd = new FileDownloader();
            fd.construirExcel(columnas, objeto, "Reporte_1", rolReporteSel.getRepId().getRepNombre(), FacesContext.getCurrentInstance(),true);
        } catch (Exception e) {
            String msj = "Ocurrió un error al generar el excel, causado por : " + e;
            PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", msj, true, false);
            logger.error(msj,e);
        }
        
    }

    public List<MarRolesReportes> getRolesReportes() {
        return rolesReportes;
    }

    public void setRolesReportes(List<MarRolesReportes> rolesReportes) {
        this.rolesReportes = rolesReportes;
    }

    public MarRolesReportes getRolReporteSel() {
        return rolReporteSel;
    }

    public void setRolReporteSel(MarRolesReportes rolReporteSel) {
        this.rolReporteSel = rolReporteSel;
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

    public List<Object[]> getListaActual(String val){
	int action = Integer.parseInt(val);
        return listaListas.get(action);
    }
    
    public void setListaActual(String val){
        System.out.println("val = " + val);
        
        //listaListasObjetosSel.set(action, listaListas.get(action).get(action));
    }
    
    public Object[] getValorSel(){
        String valorSel = "";
        System.out.println("valorSel = " + valorSel);
        Object[] o = {"OTRO_VALOR","ANOT"};
        
        Map<String,String> params = 
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String action = params.get("action");
        System.out.println("action = " + action);
        return o;
    }
    
    public void setValorSel(Object[] obj){
        System.out.println("obj = " + obj);
    }
    
    public void cambio(Object objetoSel){
        String filterValue = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("validation");
        System.out.println("filterValue = " + filterValue);
        System.out.println("objetoSel = " + objetoSel);
        System.out.println("Cambio!");
    }
    
}
