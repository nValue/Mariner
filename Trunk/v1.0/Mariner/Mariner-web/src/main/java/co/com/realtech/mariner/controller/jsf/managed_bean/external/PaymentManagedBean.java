package co.com.realtech.mariner.controller.jsf.managed_bean.external;

import co.com.realtech.mariner.controller.jsf.managed_bean.main.GenericManagedBean;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicFasesEstadosDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.dao.entity_based.radicaciones.RadicacionesDAOBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.pasarela.PSEWSConsumerBeanLocal;
import co.com.realtech.mariner.model.ejb.ws.pasarela.mappers.Transaccion;
import co.com.realtech.mariner.model.entity.MarRadicaciones;
import co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados;
import co.com.realtech.mariner.model.entity.MarTiposDocumentos;
import co.com.realtech.mariner.model.entity.MarTransacciones;
import co.com.realtech.mariner.model.entity.MarUsuarios;
import co.com.realtech.mariner.util.cdf.CDFFileDispatcher;
import co.com.realtech.mariner.util.constantes.ConstantesUtils;
import co.com.realtech.mariner.util.exceptions.MarinerPersistanceException;
import co.com.realtech.mariner.util.primefaces.context.PrimeFacesContext;
import co.com.realtech.mariner.util.primefaces.dialogos.Effects;
import co.com.realtech.mariner.util.primefaces.dialogos.PrimeFacesPopup;
import co.com.realtech.mariner.util.session.SessionUtils;
import co.com.realtech.mariner.ws.sdo.transacciones.VURTransaccionLogSDO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * ManagedBean encargado de la logica de pagos.
 *
 * @author Andres Rivera
 * @version 1.0
 * @since JDK1.8
 */
@ManagedBean
@ViewScoped
public class PaymentManagedBean extends GenericManagedBean implements Serializable {

    @EJB(beanName = "RadicacionesDAOBean")
    private RadicacionesDAOBeanLocal radicacionesDAOBean;

    @EJB(beanName = "RadicFasesEstadosDAOBean")
    private RadicFasesEstadosDAOBeanLocal radicFasesEstadosDAOBean;

    @EJB(beanName = "PSEWSConsumerBean")
    protected PSEWSConsumerBeanLocal pseWSConsumerBean;

    private String tipoPago;
    private String autenticado;
    private String tipoBusqueda;
    private String codigoBusqueda;
    private MarRadicaciones radicacion;
    private List<MarRadicaciones> radicaciones;
    private MarTransacciones transaccion;
    private List<MarTiposDocumentos> tiposDocumentos;
    private MarUsuarios usuarioTransaccionSistema;

    @Override
    public void init() {
        try {
            setUsuarioTransaccionSistema((MarUsuarios) genericDAOBean.findByColumn(MarUsuarios.class, "usuLogin", "MARINER"));

            if (getUsuarioTransaccionSistema() == null) {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero el usuario <b>Mariner</b> no esta listo para hacer transacciones.", true, false);
            } else {
                setAutenticado(SessionUtils.obtenerValor("auth"));
                setTiposDocumentos((List<MarTiposDocumentos>) genericDAOBean.loadAllForEntity(MarTiposDocumentos.class, "tdcNombre asc"));
                if (getAutenticado().equals("S")) {
                    setRadicaciones(radicacionesDAOBean.obtenerRadsAtendidasYFaseFinal(usuarioSesion, "'I-P'", "'R-A'"));
                    if (!getRadicaciones().isEmpty()) {
                        setRadicacion(getRadicaciones().get(0));
                    } else {
                        PrimeFacesPopup.lanzarDialog(Effects.Fold, "Notificacion", "No se han encontrado radicaciones pendientes por pago", true, false);
                    }
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Por favor ingrese el numero de la liquidacion de la cual desea realizar el pago.", true, false);
                }
            }
            // Validar si viene un parametro por get en la URL con nombre (CUS)
            try {
                String parametroCUSPasarela = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("CUS");
                if (parametroCUSPasarela != null && !parametroCUSPasarela.equals("")) {
                    validarPagoPasarela(parametroCUSPasarela);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            logger.error("Error inicializando PaymentManagedBean, causado por " + e);
        }
    }

    /**
     * Validacion de pagos cuando viene una redireccion de la pasarela de pagos.
     *
     * @param cus
     */
    public void validarPagoPasarela(String cus) {
        try {
            // Verificamos la transaccion en la pasarela de pagos
            String codigoEmpresa = ConstantesUtils.cargarConstante("WS-PASARELA-CODIGO-EMPRESA");
            String cusPruebas = ConstantesUtils.cargarConstante("WS-PASARELA-CUS-PRUEBAS");
            String pasarelaModoPruebas = ConstantesUtils.cargarConstante("WS-PASARELA-MODO-PRUEBAS");
            Transaccion transaccionPasarela = pseWSConsumerBean.consultarTransaccion(pasarelaModoPruebas.equals("S") ? cusPruebas : cus, codigoEmpresa);

            System.out.println("En pasarela " + transaccionPasarela.getEstado());
            // cargamos la informacion de la transaccion.
            BigDecimal referencia = new BigDecimal(transaccionPasarela.getReferencia());
            setRadicacion((MarRadicaciones) genericDAOBean.findByColumn(MarRadicaciones.class, "radId", referencia));

            if (getRadicacion() != null) {
                if (transaccionPasarela.getEstado().equalsIgnoreCase("OK")) {
                    // Verificamos que la radicacion siga en estado pendiente de pago. y cargamos la trancaccion
                    MarTransacciones transaccionBD = getRadicacion().getMarTransacciones();
                    MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(transaccionBD.getRadId());

                    if (estado.getFesId().getFesCodigo().equals("R-A")) {
                        // verificamos la fecha de la transaccion sea dd/MM/yyyy hh:mm:ss
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                        // Guardamos la nueva fase estado del a transaccion.
                        BigDecimal salida = (BigDecimal) genericDAOBean.callGenericFunction("PKG_VUR_CORE.fn_ingresar_fase_estado", transaccionBD.getRadId(), "P-A", "A", transaccionBD.getUsuId(), "", null);

                        try {
                            if (salida.intValue() != -999) {
                                try {
                                    transaccionBD.setTraFechaFinalizacion(sdf.parse(transaccionPasarela.getFechaFin()));
                                } catch (Exception e) {
                                    transaccionBD.setTraFechaFinalizacion(new Date());
                                }
                                transaccionBD.setTraReferenciaRecibo(transaccionPasarela.getReferencia());
                                transaccionBD.setTraEstado("A");
                                transaccionBD.setTraCus(transaccionPasarela.getCus());
                                transaccionBD.setTraValorPagadoPse(transaccionPasarela.getValor());

                                genericDAOBean.merge(transaccionBD);
                                PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Transaccion finalizada correctamente, su Boleta Fiscar ya esta disponible en la ORIP para su visualizacion y descarga", true, false);
                                logger.error("Transaccion finalizada correctamente para CUS " + cus);
                            }
                        } catch (Exception e) {
                            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Transaccion no confirmada en la plataforma, Si su transaccion fue exitosa en la entidad Bancaria por favor contacte al administrador del sistema", true, false);
                            logger.error("Transaccion no confirmada en la plataforma, error fecha El parametro fecha de la transaccion debe estar en formato DD/MM/YYYY HH24:MI:SS");
                        }
                    } else {
                        PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Transaccion no confirmada en la plataforma, No se encuentra activa para confirmacion", true, false);
                        logger.error("Transaccion no confirmada en la plataforma, No se encuentra activa para confirmacion");
                    }
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "El Proceso de finalizacion de pago ha finalizado con estado " + transaccionPasarela.getEstado(), true, false);
                    logger.error("Error validando informacion procedente de pasarela, la pasarela ha retornado " + transaccionPasarela.getEstado() + " para el cus " + cus);
                }
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no se ha encontrado la Liquidacion con Referencia " + cus, true, false);
                logger.error("Error validando informacion procedente de pasarela, causado por, no se ha encontrado la radicacion con codigo radicacion " + cus);
            }
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no fue posible validar la informacion de la pasarela, por favor intente nuevamente o contacte al administrador del sistema.", true, false);
            logger.error("Error realizando validacion de pasarela de pagos, causado por " + e);
        }
    }

    /**
     * Buscar radicacion segun filtro.
     */
    public
            void buscarRadicacion() {
        try {
            if (getTipoBusqueda().equals("L")) {
                List<MarRadicaciones> radiaciones = (List<MarRadicaciones>) genericDAOBean.findAllByColumn(MarRadicaciones.class, "radLiquidacion", getCodigoBusqueda(), true, "radId desc");
                if (!radiaciones.isEmpty()) {
                    setRadicacion(radiaciones.get(0));
                }
            } else {
                List<MarRadicaciones> radiaciones = (List<MarRadicaciones>) genericDAOBean.findAllByColumn(MarRadicaciones.class, "radNumero", getCodigoBusqueda(), true, "radId desc");
                if (!radiaciones.isEmpty()) {
                    setRadicacion(radiaciones.get(0));
                }
            }

            if (getRadicacion() != null) {
                MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(getRadicacion());
                if (!estado.getFesId().getFesCodigo().equals("R-A")) {
                    setRadicacion(null);
                    PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "Lo sentimos pero la radicacion seleccionada, no se encuentra en proceso de pago.", true, false);
                }
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "No se han encontrado radicaciones vinculadas al filtro seleccionado.", true, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error buscando el registro, por favor intente nuevamente.", true, false);
            logger.error("Error buscando radicacion, causado por " + e);
        }
    }

    /**
     * Realizar pago de radicacion seleccionada.
     *
     * @param ttPago
     */
    public void seleccionarMedioPago(String ttPago) {
        try {
            // verificar que la transaccion no tenga una en curso.
            setTipoPago(ttPago);
            MarTransacciones transVerificacion = (MarTransacciones) genericDAOBean.findByColumn(MarTransacciones.class, "radId", getRadicacion());

            if (transVerificacion
                    != null) {
                if (transVerificacion.getTraEstado().equals("T")) {
                    setTransaccion(transVerificacion);
                    PrimeFacesContext.execute("PF('dialogTransaccion').show();");
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos esta radicacion ya tiene un proceso de pago en el sistema, si tiene dudas por favor contacte al administrador del sistema.", true, false);
                }
            } else {
                setTransaccion(new MarTransacciones());
                getTransaccion().setTraTipoPago(ttPago);
                getTransaccion().setRadId(getRadicacion());
                getTransaccion().setTraReferencia(getRadicacion().getRadId().toString());
                getTransaccion().setTraValor(getRadicacion().getRadValorLiq());
                getTransaccion().setTraCuantia(getRadicacion().getRadCuantia());
                getTransaccion().setTraFechaInicio(new Date());
                getTransaccion().setTraEstado("T");
                getTransaccion().setUsuId(getUsuarioTransaccionSistema());

                if (getAutenticado().equals("S")) {
                    getTransaccion().setTraApellidos(usuarioSesion.getPerId().getPerApellidos());
                    getTransaccion().setTraNombres(usuarioSesion.getPerId().getPerNombres());
                    getTransaccion().setTraTelefono(usuarioSesion.getPerId().getPerTelefono());
                    getTransaccion().setTraCorreo(usuarioSesion.getPerId().getPerEmail());
                    getTransaccion().setTraDocumento(usuarioSesion.getPerId().getPerDocumento());
                    getTransaccion().setTdcId(usuarioSesion.getPerId().getTdcId());
                }
                PrimeFacesContext.execute("PF('dialogTransaccion').show();");
            }
        } catch (Exception e) {
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error seleccionando el medio de pago.", true, false);
            logger.error("Error realizando seleccion de medio de pago radicacion, causado por " + e);
        }
    }

    /**
     * Realizar transaccion, confirmacion de la transaccion.
     */
    public void realizarTransaccion() {
        try {
            auditSessionUtils.setAuditReflectedValues(getTransaccion());
            getTransaccion().setTraFechaInicio(new Date());

            // Verificamos que la radicacion siga en estado pendiente de pago.
            MarRadicacionesFasesEstados estado = radicFasesEstadosDAOBean.obtenerUltimaFaseDeRadicacion(getRadicacion());
            if (estado.getFesId().getFesCodigo().equals("R-A")) {
                if (getTransaccion().getTraId() == null) {
                    genericDAOBean.save(getTransaccion());
                } else {
                    genericDAOBean.merge(getTransaccion());
                }

                if (getTipoPago().equals("PSE")) {
                    // Logica para pagos en linea.
                    String urlServletRedireccionPasarela = ConstantesUtils.cargarConstante("WS-URL-PASARELA-WEB");
                    // nemthys pasarela
                    urlServletRedireccionPasarela += "?CODIGO=" + getTransaccion().getTraId();
                    // ponemos en session temporal
                    SessionUtils.asignarValor("TRANSACCION_TEMPORAL", getTransaccion().getTraId());
                    // Redireccionar a la pasarela de pagos.
                    FacesContext.getCurrentInstance().getExternalContext().redirect(urlServletRedireccionPasarela);
                } else {
                    PrimeFacesPopup.lanzarDialog(Effects.Slide, "Notificacion", "Recibo de Pago generado correctamente, presione Descargar para proceder obtener este documento.", true, false);
                }
            } else {
                PrimeFacesPopup.lanzarDialog(Effects.Clip, "Notificacion", "Lo sentimos pero la radicacion seleccionada ya no se encuentra en proceso pendiente de pago.", true, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero ha ocurrido un error iniciando la transaccion, por favor intente nuevamente.", true, false);
            logger.error("Error realizando inicio de transaccion, causado por " + e);
        }
    }

    /**
     * Descargar recibo de pago.
     */
    public void descargarRecibo() {
        try {
            // Descargar recibo de pago de SAP.
            CDFFileDispatcher dispatcher = CDFFileDispatcher.create();
            dispatcher.findFile(getRadicacion().getArcIdReciboPago().getArcId());
            if (dispatcher.getFileContent() == null) {
                PrimeFacesPopup.lanzarDialog(Effects.Explode, "Error", "Lo sentimos pero no se ha encontrado el Recibo en el Sistema, por favor intente nuevamente.", true, false);
            } else {
                descargarArhivoFacesContext(FacesContext.getCurrentInstance(), dispatcher.getFileContent(), "comprobantePagoLiquidacion" + getRadicacion().getRadLiquidacion() + ".pdf");
            }
        } catch (Exception e) {
        }
    }

    /**
     * Descargar archivo a través del FacesContext.
     *
     * @param context
     * @param bytes
     * @param nombreArchivo
     */
    private void descargarArhivoFacesContext(FacesContext context, byte[] bytes, String nombreArchivo) {
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        try {
            if (bytes == null) {
                System.out.println("Bytes nullos en respyesta de PDF");
            } else {
                try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                    response.addHeader("Content-Type", "application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + ".pdf");
                    response.setContentLength(bytes.length);
                    response.setContentType("application/pdf");
                    servletOutputStream.write(bytes);
                    servletOutputStream.flush();
                    context.responseComplete();
                }
            }
        } catch (Exception e) {
            System.out.println("Error enviando archivo PDF, error causado por " + e);
        }
    }

    public String getAutenticado() {
        return autenticado;
    }

    public void setAutenticado(String autenticado) {
        this.autenticado = autenticado;
    }

    public MarRadicaciones getRadicacion() {
        return radicacion;
    }

    public void setRadicacion(MarRadicaciones radicacion) {
        this.radicacion = radicacion;
    }

    public List<MarRadicaciones> getRadicaciones() {
        return radicaciones;
    }

    public void setRadicaciones(List<MarRadicaciones> radicaciones) {
        this.radicaciones = radicaciones;
    }

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public MarTransacciones getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(MarTransacciones transaccion) {
        this.transaccion = transaccion;
    }

    public List<MarTiposDocumentos> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<MarTiposDocumentos> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public MarUsuarios getUsuarioTransaccionSistema() {
        return usuarioTransaccionSistema;
    }

    public void setUsuarioTransaccionSistema(MarUsuarios usuarioTransaccionSistema) {
        this.usuarioTransaccionSistema = usuarioTransaccionSistema;
    }

}
