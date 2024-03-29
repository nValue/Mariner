/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "MAR_RADICACIONES", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"RAD_LIQUIDACION"})})
@NamedQueries({
    @NamedQuery(name = "MarRadicaciones.findAll", query = "SELECT m FROM MarRadicaciones m"),
    @NamedQuery(name = "MarRadicaciones.findByRadId", query = "SELECT m FROM MarRadicaciones m WHERE m.radId = :radId"),
    @NamedQuery(name = "MarRadicaciones.findByRadFecha", query = "SELECT m FROM MarRadicaciones m WHERE m.radFecha = :radFecha"),
    @NamedQuery(name = "MarRadicaciones.findByRadNumero", query = "SELECT m FROM MarRadicaciones m WHERE m.radNumero = :radNumero"),
    @NamedQuery(name = "MarRadicaciones.findByAudUsuario", query = "SELECT m FROM MarRadicaciones m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRadicaciones.findByAudFecha", query = "SELECT m FROM MarRadicaciones m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarRadicaciones.findByRadDocOtorgante", query = "SELECT m FROM MarRadicaciones m WHERE m.radDocOtorgante = :radDocOtorgante"),
    @NamedQuery(name = "MarRadicaciones.findByRadDocReceptor", query = "SELECT m FROM MarRadicaciones m WHERE m.radDocReceptor = :radDocReceptor"),
    @NamedQuery(name = "MarRadicaciones.findByRadCodigoActo", query = "SELECT m FROM MarRadicaciones m WHERE m.radCodigoActo = :radCodigoActo"),
    @NamedQuery(name = "MarRadicaciones.findByRadCuantia", query = "SELECT m FROM MarRadicaciones m WHERE m.radCuantia = :radCuantia"),
    @NamedQuery(name = "MarRadicaciones.findByRadValorLiq", query = "SELECT m FROM MarRadicaciones m WHERE m.radValorLiq = :radValorLiq"),
    @NamedQuery(name = "MarRadicaciones.findByRadEsExterior", query = "SELECT m FROM MarRadicaciones m WHERE m.radEsExterior = :radEsExterior"),
    @NamedQuery(name = "MarRadicaciones.findByRadEstado", query = "SELECT m FROM MarRadicaciones m WHERE m.radEstado = :radEstado"),
    @NamedQuery(name = "MarRadicaciones.findByRadLiquidacion", query = "SELECT m FROM MarRadicaciones m WHERE m.radLiquidacion = :radLiquidacion"),
    @NamedQuery(name = "MarRadicaciones.findByRadTurno", query = "SELECT m FROM MarRadicaciones m WHERE m.radTurno = :radTurno"),
    @NamedQuery(name = "MarRadicaciones.findByRadEsImpresion", query = "SELECT m FROM MarRadicaciones m WHERE m.radEsImpresion = :radEsImpresion")})
public class MarRadicaciones implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "radId")
    private List<MarVerificacionBusqLog> marVerificacionBusqLogList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_radicaciones")
    @SequenceGenerator(name = "sq_mar_radicaciones", sequenceName = "sq_mar_radicaciones")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RAD_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal radId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RAD_FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date radFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RAD_NUMERO", nullable = false, length = 100)
    private String radNumero;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 50)
    @Column(name = "RAD_DOC_OTORGANTE", length = 50)
    private String radDocOtorgante;
    @Size(max = 50)
    @Column(name = "RAD_DOC_RECEPTOR", length = 50)
    private String radDocReceptor;
    @Size(max = 200)
    @Column(name = "RAD_CODIGO_ACTO", length = 200)
    private String radCodigoActo;
    @Column(name = "RAD_CUANTIA")
    private BigInteger radCuantia;
    @Column(name = "RAD_VALOR_LIQ", precision = 14, scale = 3)
    private BigDecimal radValorLiq;
    @Size(max = 1)
    @Column(name = "RAD_ES_EXTERIOR", length = 1)
    private String radEsExterior;
    @Size(max = 1)
    @Column(name = "RAD_ESTADO", length = 1)
    private String radEstado;
    @Size(max = 100)
    @Column(name = "RAD_LIQUIDACION", length = 100)
    private String radLiquidacion;
    @Size(max = 20)
    @Column(name = "RAD_TURNO", length = 20)
    private String radTurno;
    @Size(max = 1)
    @Column(name = "RAD_ES_IMPRESION", length = 1)
    private String radEsImpresion;
    @JoinColumn(name = "ARC_ID_RECIBO_PAGO", referencedColumnName = "ARC_ID")
    @ManyToOne
    private MarArchivos arcIdReciboPago;
    @JoinColumn(name = "ARC_ID_BOLETA_FISCAL", referencedColumnName = "ARC_ID")
    @ManyToOne
    private MarArchivos arcIdBoletaFiscal;
    @JoinColumn(name = "ESC_ID", referencedColumnName = "ESC_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarEscrituras escId;
    @JoinColumn(name = "NOT_ID", referencedColumnName = "NOT_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarNotarias notId;
    @JoinColumn(name = "PRI_ID", referencedColumnName = "PRI_ID")
    @ManyToOne
    private MarPrioridades priId;
    @JoinColumn(name = "RAA_ID", referencedColumnName = "RAA_ID")
    @ManyToOne
    private MarRadicacionesAgrupamientos raaId;
    @OneToOne(mappedBy = "radId")
    private MarRadicacionesDetallesSap marRadicacionesDetallesSap;
    @OneToMany(mappedBy = "radId")
    private List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList;
    @OneToOne(mappedBy = "radId")
    private MarTransacciones marTransacciones;

    public MarRadicaciones() {
    }

    public MarRadicaciones(BigDecimal radId) {
        this.radId = radId;
    }

    public MarRadicaciones(BigDecimal radId, Date radFecha, String radNumero) {
        this.radId = radId;
        this.radFecha = radFecha;
        this.radNumero = radNumero;
    }

    public BigDecimal getRadId() {
        return radId;
    }

    public void setRadId(BigDecimal radId) {
        this.radId = radId;
    }

    public Date getRadFecha() {
        return radFecha;
    }

    public void setRadFecha(Date radFecha) {
        this.radFecha = radFecha;
    }

    public String getRadNumero() {
        return radNumero;
    }

    public void setRadNumero(String radNumero) {
        this.radNumero = radNumero;
    }

    public String getAudUsuario() {
        return audUsuario;
    }

    public void setAudUsuario(String audUsuario) {
        this.audUsuario = audUsuario;
    }

    public Date getAudFecha() {
        return audFecha;
    }

    public void setAudFecha(Date audFecha) {
        this.audFecha = audFecha;
    }

    public String getRadDocOtorgante() {
        return radDocOtorgante;
    }

    public void setRadDocOtorgante(String radDocOtorgante) {
        this.radDocOtorgante = radDocOtorgante;
    }

    public String getRadDocReceptor() {
        return radDocReceptor;
    }

    public void setRadDocReceptor(String radDocReceptor) {
        this.radDocReceptor = radDocReceptor;
    }

    public String getRadCodigoActo() {
        return radCodigoActo;
    }

    public void setRadCodigoActo(String radCodigoActo) {
        this.radCodigoActo = radCodigoActo;
    }

    public BigInteger getRadCuantia() {
        return radCuantia;
    }

    public void setRadCuantia(BigInteger radCuantia) {
        this.radCuantia = radCuantia;
    }

    public BigDecimal getRadValorLiq() {
        return radValorLiq;
    }

    public void setRadValorLiq(BigDecimal radValorLiq) {
        this.radValorLiq = radValorLiq;
    }

    public String getRadEsExterior() {
        return radEsExterior;
    }

    public void setRadEsExterior(String radEsExterior) {
        this.radEsExterior = radEsExterior;
    }

    public String getRadEstado() {
        return radEstado;
    }

    public void setRadEstado(String radEstado) {
        this.radEstado = radEstado;
    }

    public String getRadLiquidacion() {
        return radLiquidacion;
    }

    public void setRadLiquidacion(String radLiquidacion) {
        this.radLiquidacion = radLiquidacion;
    }

    public String getRadTurno() {
        return radTurno;
    }

    public void setRadTurno(String radTurno) {
        this.radTurno = radTurno;
    }

    public String getRadEsImpresion() {
        return radEsImpresion;
    }

    public void setRadEsImpresion(String radEsImpresion) {
        this.radEsImpresion = radEsImpresion;
    }

    public MarArchivos getArcIdReciboPago() {
        return arcIdReciboPago;
    }

    public void setArcIdReciboPago(MarArchivos arcIdReciboPago) {
        this.arcIdReciboPago = arcIdReciboPago;
    }

    public MarArchivos getArcIdBoletaFiscal() {
        return arcIdBoletaFiscal;
    }

    public void setArcIdBoletaFiscal(MarArchivos arcIdBoletaFiscal) {
        this.arcIdBoletaFiscal = arcIdBoletaFiscal;
    }

    public MarEscrituras getEscId() {
        return escId;
    }

    public void setEscId(MarEscrituras escId) {
        this.escId = escId;
    }

    public MarNotarias getNotId() {
        return notId;
    }

    public void setNotId(MarNotarias notId) {
        this.notId = notId;
    }

    public MarPrioridades getPriId() {
        return priId;
    }

    public void setPriId(MarPrioridades priId) {
        this.priId = priId;
    }

    public MarRadicacionesAgrupamientos getRaaId() {
        return raaId;
    }

    public void setRaaId(MarRadicacionesAgrupamientos raaId) {
        this.raaId = raaId;
    }

    public MarRadicacionesDetallesSap getMarRadicacionesDetallesSap() {
        return marRadicacionesDetallesSap;
    }

    public void setMarRadicacionesDetallesSap(MarRadicacionesDetallesSap marRadicacionesDetallesSap) {
        this.marRadicacionesDetallesSap = marRadicacionesDetallesSap;
    }

    public List<MarRadicacionesFasesEstados> getMarRadicacionesFasesEstadosList() {
        return marRadicacionesFasesEstadosList;
    }

    public void setMarRadicacionesFasesEstadosList(List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList) {
        this.marRadicacionesFasesEstadosList = marRadicacionesFasesEstadosList;
    }

    public MarTransacciones getMarTransacciones() {
        return marTransacciones;
    }

    public void setMarTransacciones(MarTransacciones marTransacciones) {
        this.marTransacciones = marTransacciones;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (radId != null ? radId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicaciones)) {
            return false;
        }
        MarRadicaciones other = (MarRadicaciones) object;
        if ((this.radId == null && other.radId != null) || (this.radId != null && !this.radId.equals(other.radId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicaciones[ radId=" + radId + " ]";
    }

    public List<MarVerificacionBusqLog> getMarVerificacionBusqLogList() {
        return marVerificacionBusqLogList;
    }

    public void setMarVerificacionBusqLogList(List<MarVerificacionBusqLog> marVerificacionBusqLogList) {
        this.marVerificacionBusqLogList = marVerificacionBusqLogList;
    }
    
}
