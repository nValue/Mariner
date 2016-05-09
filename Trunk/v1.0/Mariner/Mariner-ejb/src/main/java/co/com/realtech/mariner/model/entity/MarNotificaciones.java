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
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "mar_notificaciones")
@NamedQueries({
    @NamedQuery(name = "MarNotificaciones.findAll", query = "SELECT m FROM MarNotificaciones m"),
    @NamedQuery(name = "MarNotificaciones.findByNtfId", query = "SELECT m FROM MarNotificaciones m WHERE m.ntfId = :ntfId"),
    @NamedQuery(name = "MarNotificaciones.findByNtfMensaje", query = "SELECT m FROM MarNotificaciones m WHERE m.ntfMensaje = :ntfMensaje"),
    @NamedQuery(name = "MarNotificaciones.findByNftFecha", query = "SELECT m FROM MarNotificaciones m WHERE m.nftFecha = :nftFecha"),
    @NamedQuery(name = "MarNotificaciones.findByAudUsuario", query = "SELECT m FROM MarNotificaciones m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarNotificaciones.findByAudFecha", query = "SELECT m FROM MarNotificaciones m WHERE m.audFecha = :audFecha")})
public class MarNotificaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ntf_id", nullable = false, precision = 131089, scale = 0)
    private BigDecimal ntfId;
    @Size(max = 1000)
    @Column(name = "ntf_mensaje", length = 1000)
    private String ntfMensaje;
    @Column(name = "nft_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nftFecha;
    @Size(max = 50)
    @Column(name = "aud_usuario", length = 50)
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "per_id", referencedColumnName = "per_id", nullable = false)
    @ManyToOne(optional = false)
    private MarPersonas perId;

    public MarNotificaciones() {
    }

    public MarNotificaciones(BigDecimal ntfId) {
        this.ntfId = ntfId;
    }

    public BigDecimal getNtfId() {
        return ntfId;
    }

    public void setNtfId(BigDecimal ntfId) {
        this.ntfId = ntfId;
    }

    public String getNtfMensaje() {
        return ntfMensaje;
    }

    public void setNtfMensaje(String ntfMensaje) {
        this.ntfMensaje = ntfMensaje;
    }

    public Date getNftFecha() {
        return nftFecha;
    }

    public void setNftFecha(Date nftFecha) {
        this.nftFecha = nftFecha;
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

    public MarPersonas getPerId() {
        return perId;
    }

    public void setPerId(MarPersonas perId) {
        this.perId = perId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ntfId != null ? ntfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarNotificaciones)) {
            return false;
        }
        MarNotificaciones other = (MarNotificaciones) object;
        if ((this.ntfId == null && other.ntfId != null) || (this.ntfId != null && !this.ntfId.equals(other.ntfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarNotificaciones[ ntfId=" + ntfId + " ]";
    }
    
}
