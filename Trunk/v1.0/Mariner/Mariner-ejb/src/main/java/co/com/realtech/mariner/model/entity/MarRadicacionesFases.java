/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fabianagudelo
 */
@Entity
@Table(name = "mar_radicaciones_fases")
@NamedQueries({
    @NamedQuery(name = "MarRadicacionesFases.findAll", query = "SELECT m FROM MarRadicacionesFases m"),
    @NamedQuery(name = "MarRadicacionesFases.findByRfaId", query = "SELECT m FROM MarRadicacionesFases m WHERE m.rfaId = :rfaId"),
    @NamedQuery(name = "MarRadicacionesFases.findByRfaFecha", query = "SELECT m FROM MarRadicacionesFases m WHERE m.rfaFecha = :rfaFecha"),
    @NamedQuery(name = "MarRadicacionesFases.findByRfaEstado", query = "SELECT m FROM MarRadicacionesFases m WHERE m.rfaEstado = :rfaEstado"),
    @NamedQuery(name = "MarRadicacionesFases.findByRfaCodigoSap", query = "SELECT m FROM MarRadicacionesFases m WHERE m.rfaCodigoSap = :rfaCodigoSap"),
    @NamedQuery(name = "MarRadicacionesFases.findByRfaObservaciones", query = "SELECT m FROM MarRadicacionesFases m WHERE m.rfaObservaciones = :rfaObservaciones"),
    @NamedQuery(name = "MarRadicacionesFases.findByRfaIp", query = "SELECT m FROM MarRadicacionesFases m WHERE m.rfaIp = :rfaIp"),
    @NamedQuery(name = "MarRadicacionesFases.findByAudUsuario", query = "SELECT m FROM MarRadicacionesFases m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRadicacionesFases.findByAudFecha", query = "SELECT m FROM MarRadicacionesFases m WHERE m.audFecha = :audFecha")})
public class MarRadicacionesFases implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rfa_id")
    private BigDecimal rfaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rfa_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rfaFecha;
    @Size(max = 1)
    @Column(name = "rfa_estado")
    private String rfaEstado;
    @Size(max = 200)
    @Column(name = "rfa_codigo_sap")
    private String rfaCodigoSap;
    @Size(max = 1000)
    @Column(name = "rfa_observaciones")
    private String rfaObservaciones;
    @Size(max = 20)
    @Column(name = "rfa_ip")
    private String rfaIp;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "fas_id", referencedColumnName = "fas_id")
    @ManyToOne(optional = false)
    private MarFases fasId;
    @JoinColumn(name = "rad_id", referencedColumnName = "rad_id")
    @ManyToOne(optional = false)
    private MarRadicaciones radId;
    @JoinColumn(name = "usu_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private MarUsuarios usuId;

    public MarRadicacionesFases() {
    }

    public MarRadicacionesFases(BigDecimal rfaId) {
        this.rfaId = rfaId;
    }

    public MarRadicacionesFases(BigDecimal rfaId, Date rfaFecha) {
        this.rfaId = rfaId;
        this.rfaFecha = rfaFecha;
    }

    public BigDecimal getRfaId() {
        return rfaId;
    }

    public void setRfaId(BigDecimal rfaId) {
        this.rfaId = rfaId;
    }

    public Date getRfaFecha() {
        return rfaFecha;
    }

    public void setRfaFecha(Date rfaFecha) {
        this.rfaFecha = rfaFecha;
    }

    public String getRfaEstado() {
        return rfaEstado;
    }

    public void setRfaEstado(String rfaEstado) {
        this.rfaEstado = rfaEstado;
    }

    public String getRfaCodigoSap() {
        return rfaCodigoSap;
    }

    public void setRfaCodigoSap(String rfaCodigoSap) {
        this.rfaCodigoSap = rfaCodigoSap;
    }

    public String getRfaObservaciones() {
        return rfaObservaciones;
    }

    public void setRfaObservaciones(String rfaObservaciones) {
        this.rfaObservaciones = rfaObservaciones;
    }

    public String getRfaIp() {
        return rfaIp;
    }

    public void setRfaIp(String rfaIp) {
        this.rfaIp = rfaIp;
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

    public MarFases getFasId() {
        return fasId;
    }

    public void setFasId(MarFases fasId) {
        this.fasId = fasId;
    }

    public MarRadicaciones getRadId() {
        return radId;
    }

    public void setRadId(MarRadicaciones radId) {
        this.radId = radId;
    }

    public MarUsuarios getUsuId() {
        return usuId;
    }

    public void setUsuId(MarUsuarios usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rfaId != null ? rfaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicacionesFases)) {
            return false;
        }
        MarRadicacionesFases other = (MarRadicacionesFases) object;
        if ((this.rfaId == null && other.rfaId != null) || (this.rfaId != null && !this.rfaId.equals(other.rfaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicacionesFases[ rfaId=" + rfaId + " ]";
    }
    
}
