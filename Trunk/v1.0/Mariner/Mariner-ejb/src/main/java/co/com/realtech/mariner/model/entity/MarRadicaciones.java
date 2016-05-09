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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "mar_radicaciones", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"esc_id"})})
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
    @NamedQuery(name = "MarRadicaciones.findByRadMedioPago", query = "SELECT m FROM MarRadicaciones m WHERE m.radMedioPago = :radMedioPago"),
    @NamedQuery(name = "MarRadicaciones.findByRadCus", query = "SELECT m FROM MarRadicaciones m WHERE m.radCus = :radCus"),
    @NamedQuery(name = "MarRadicaciones.findByRadMedioPagoEstado", query = "SELECT m FROM MarRadicaciones m WHERE m.radMedioPagoEstado = :radMedioPagoEstado"),
    @NamedQuery(name = "MarRadicaciones.findByRadEstado", query = "SELECT m FROM MarRadicaciones m WHERE m.radEstado = :radEstado")})
public class MarRadicaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rad_id", nullable = false, precision = 131089, scale = 0)
    private BigDecimal radId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rad_fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date radFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rad_numero", nullable = false, length = 100)
    private String radNumero;
    @Size(max = 50)
    @Column(name = "aud_usuario", length = 50)
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 50)
    @Column(name = "rad_doc_otorgante", length = 50)
    private String radDocOtorgante;
    @Size(max = 50)
    @Column(name = "rad_doc_receptor", length = 50)
    private String radDocReceptor;
    @Size(max = 200)
    @Column(name = "rad_codigo_acto", length = 200)
    private String radCodigoActo;
    @Column(name = "rad_cuantia")
    private BigInteger radCuantia;
    @Column(name = "rad_valor_liq")
    private BigInteger radValorLiq;
    @Size(max = 1)
    @Column(name = "rad_es_exterior", length = 1)
    private String radEsExterior;
    @Size(max = 20)
    @Column(name = "rad_medio_pago", length = 20)
    private String radMedioPago;
    @Size(max = 100)
    @Column(name = "rad_cus", length = 100)
    private String radCus;
    @Size(max = 20)
    @Column(name = "rad_medio_pago_estado", length = 20)
    private String radMedioPagoEstado;
    @Size(max = 1)
    @Column(name = "rad_estado", length = 1)
    private String radEstado;
    @JoinColumn(name = "arc_id_boleta_fiscal", referencedColumnName = "arc_id")
    @ManyToOne
    private MarArchivos arcIdBoletaFiscal;
    @JoinColumn(name = "arc_id_recibo_pago", referencedColumnName = "arc_id")
    @ManyToOne
    private MarArchivos arcIdReciboPago;
    @JoinColumn(name = "esc_id", referencedColumnName = "esc_id", nullable = false)
    @OneToOne(optional = false)
    private MarEscrituras escId;
    @JoinColumn(name = "not_id", referencedColumnName = "not_id", nullable = false)
    @ManyToOne(optional = false)
    private MarNotarias notId;
    @JoinColumn(name = "tdc_id_otorgante", referencedColumnName = "tdc_id", nullable = false)
    @ManyToOne(optional = false)
    private MarTiposDocumentos tdcIdOtorgante;
    @JoinColumn(name = "tdc_id_receptor", referencedColumnName = "tdc_id", nullable = false)
    @ManyToOne(optional = false)
    private MarTiposDocumentos tdcIdReceptor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "radId")
    private List<MarRadicacionesFases> marRadicacionesFasesList;

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

    public BigInteger getRadValorLiq() {
        return radValorLiq;
    }

    public void setRadValorLiq(BigInteger radValorLiq) {
        this.radValorLiq = radValorLiq;
    }

    public String getRadEsExterior() {
        return radEsExterior;
    }

    public void setRadEsExterior(String radEsExterior) {
        this.radEsExterior = radEsExterior;
    }

    public String getRadMedioPago() {
        return radMedioPago;
    }

    public void setRadMedioPago(String radMedioPago) {
        this.radMedioPago = radMedioPago;
    }

    public String getRadCus() {
        return radCus;
    }

    public void setRadCus(String radCus) {
        this.radCus = radCus;
    }

    public String getRadMedioPagoEstado() {
        return radMedioPagoEstado;
    }

    public void setRadMedioPagoEstado(String radMedioPagoEstado) {
        this.radMedioPagoEstado = radMedioPagoEstado;
    }

    public String getRadEstado() {
        return radEstado;
    }

    public void setRadEstado(String radEstado) {
        this.radEstado = radEstado;
    }

    public MarArchivos getArcIdBoletaFiscal() {
        return arcIdBoletaFiscal;
    }

    public void setArcIdBoletaFiscal(MarArchivos arcIdBoletaFiscal) {
        this.arcIdBoletaFiscal = arcIdBoletaFiscal;
    }

    public MarArchivos getArcIdReciboPago() {
        return arcIdReciboPago;
    }

    public void setArcIdReciboPago(MarArchivos arcIdReciboPago) {
        this.arcIdReciboPago = arcIdReciboPago;
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

    public MarTiposDocumentos getTdcIdOtorgante() {
        return tdcIdOtorgante;
    }

    public void setTdcIdOtorgante(MarTiposDocumentos tdcIdOtorgante) {
        this.tdcIdOtorgante = tdcIdOtorgante;
    }

    public MarTiposDocumentos getTdcIdReceptor() {
        return tdcIdReceptor;
    }

    public void setTdcIdReceptor(MarTiposDocumentos tdcIdReceptor) {
        this.tdcIdReceptor = tdcIdReceptor;
    }

    public List<MarRadicacionesFases> getMarRadicacionesFasesList() {
        return marRadicacionesFasesList;
    }

    public void setMarRadicacionesFasesList(List<MarRadicacionesFases> marRadicacionesFasesList) {
        this.marRadicacionesFasesList = marRadicacionesFasesList;
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
    
}
