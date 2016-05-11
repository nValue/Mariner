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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "mar_escrituras")
@NamedQueries({
    @NamedQuery(name = "MarEscrituras.findAll", query = "SELECT m FROM MarEscrituras m"),
    @NamedQuery(name = "MarEscrituras.findByEscId", query = "SELECT m FROM MarEscrituras m WHERE m.escId = :escId"),
    @NamedQuery(name = "MarEscrituras.findByEscFecha", query = "SELECT m FROM MarEscrituras m WHERE m.escFecha = :escFecha"),
    @NamedQuery(name = "MarEscrituras.findByEscNumero", query = "SELECT m FROM MarEscrituras m WHERE m.escNumero = :escNumero"),
    @NamedQuery(name = "MarEscrituras.findByAudUsuario", query = "SELECT m FROM MarEscrituras m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarEscrituras.findByAudFecha", query = "SELECT m FROM MarEscrituras m WHERE m.audFecha = :audFecha")})
public class MarEscrituras implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "esc_id", nullable = false, precision = 131089, scale = 0)
    private BigDecimal escId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "esc_fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date escFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "esc_numero", nullable = false, length = 100)
    private String escNumero;
    @Size(max = 50)
    @Column(name = "aud_usuario", length = 50)
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToOne(mappedBy = "escId")
    private MarRadicaciones marRadicaciones;
    @JoinColumn(name = "arc_id", referencedColumnName = "arc_id", nullable = false)
    @ManyToOne(optional = false)
    private MarArchivos arcId;

    public MarEscrituras() {
    }

    public MarEscrituras(BigDecimal escId) {
        this.escId = escId;
    }

    public MarEscrituras(BigDecimal escId, Date escFecha, String escNumero) {
        this.escId = escId;
        this.escFecha = escFecha;
        this.escNumero = escNumero;
    }

    public BigDecimal getEscId() {
        return escId;
    }

    public void setEscId(BigDecimal escId) {
        this.escId = escId;
    }

    public Date getEscFecha() {
        return escFecha;
    }

    public void setEscFecha(Date escFecha) {
        this.escFecha = escFecha;
    }

    public String getEscNumero() {
        return escNumero;
    }

    public void setEscNumero(String escNumero) {
        this.escNumero = escNumero;
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

    public MarRadicaciones getMarRadicaciones() {
        return marRadicaciones;
    }

    public void setMarRadicaciones(MarRadicaciones marRadicaciones) {
        this.marRadicaciones = marRadicaciones;
    }

    public MarArchivos getArcId() {
        return arcId;
    }

    public void setArcId(MarArchivos arcId) {
        this.arcId = arcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (escId != null ? escId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarEscrituras)) {
            return false;
        }
        MarEscrituras other = (MarEscrituras) object;
        if ((this.escId == null && other.escId != null) || (this.escId != null && !this.escId.equals(other.escId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarEscrituras[ escId=" + escId + " ]";
    }
    
}
