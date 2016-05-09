/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "mar_notarias")
@NamedQueries({
    @NamedQuery(name = "MarNotarias.findAll", query = "SELECT m FROM MarNotarias m"),
    @NamedQuery(name = "MarNotarias.findByNotId", query = "SELECT m FROM MarNotarias m WHERE m.notId = :notId"),
    @NamedQuery(name = "MarNotarias.findByNotNombre", query = "SELECT m FROM MarNotarias m WHERE m.notNombre = :notNombre"),
    @NamedQuery(name = "MarNotarias.findByNotCodigo", query = "SELECT m FROM MarNotarias m WHERE m.notCodigo = :notCodigo"),
    @NamedQuery(name = "MarNotarias.findByNotEstado", query = "SELECT m FROM MarNotarias m WHERE m.notEstado = :notEstado"),
    @NamedQuery(name = "MarNotarias.findByAudUsuario", query = "SELECT m FROM MarNotarias m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarNotarias.findByAudFecha", query = "SELECT m FROM MarNotarias m WHERE m.audFecha = :audFecha")})
public class MarNotarias implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "not_id", nullable = false, precision = 131089, scale = 0)
    private BigDecimal notId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "not_nombre", nullable = false, length = 100)
    private String notNombre;
    @Size(max = 50)
    @Column(name = "not_codigo", length = 50)
    private String notCodigo;
    @Size(max = 1)
    @Column(name = "not_estado", length = 1)
    private String notEstado;
    @Size(max = 50)
    @Column(name = "aud_usuario", length = 50)
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "ciu_id", referencedColumnName = "ciu_id", nullable = false)
    @ManyToOne(optional = false)
    private MarCiudades ciuId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notId")
    private List<MarRadicaciones> marRadicacionesList;

    public MarNotarias() {
    }

    public MarNotarias(BigDecimal notId) {
        this.notId = notId;
    }

    public MarNotarias(BigDecimal notId, String notNombre) {
        this.notId = notId;
        this.notNombre = notNombre;
    }

    public BigDecimal getNotId() {
        return notId;
    }

    public void setNotId(BigDecimal notId) {
        this.notId = notId;
    }

    public String getNotNombre() {
        return notNombre;
    }

    public void setNotNombre(String notNombre) {
        this.notNombre = notNombre;
    }

    public String getNotCodigo() {
        return notCodigo;
    }

    public void setNotCodigo(String notCodigo) {
        this.notCodigo = notCodigo;
    }

    public String getNotEstado() {
        return notEstado;
    }

    public void setNotEstado(String notEstado) {
        this.notEstado = notEstado;
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

    public MarCiudades getCiuId() {
        return ciuId;
    }

    public void setCiuId(MarCiudades ciuId) {
        this.ciuId = ciuId;
    }

    public List<MarRadicaciones> getMarRadicacionesList() {
        return marRadicacionesList;
    }

    public void setMarRadicacionesList(List<MarRadicaciones> marRadicacionesList) {
        this.marRadicacionesList = marRadicacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notId != null ? notId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarNotarias)) {
            return false;
        }
        MarNotarias other = (MarNotarias) object;
        if ((this.notId == null && other.notId != null) || (this.notId != null && !this.notId.equals(other.notId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarNotarias[ notId=" + notId + " ]";
    }
    
}
