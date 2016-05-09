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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author fabianagudelo
 */
@Entity
@Table(name = "mar_fases")
@NamedQueries({
    @NamedQuery(name = "MarFases.findAll", query = "SELECT m FROM MarFases m"),
    @NamedQuery(name = "MarFases.findByFasId", query = "SELECT m FROM MarFases m WHERE m.fasId = :fasId"),
    @NamedQuery(name = "MarFases.findByFasCodigo", query = "SELECT m FROM MarFases m WHERE m.fasCodigo = :fasCodigo"),
    @NamedQuery(name = "MarFases.findByFasNombre", query = "SELECT m FROM MarFases m WHERE m.fasNombre = :fasNombre"),
    @NamedQuery(name = "MarFases.findByFasOrden", query = "SELECT m FROM MarFases m WHERE m.fasOrden = :fasOrden"),
    @NamedQuery(name = "MarFases.findByAudUsuario", query = "SELECT m FROM MarFases m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarFases.findByAudFecha", query = "SELECT m FROM MarFases m WHERE m.audFecha = :audFecha")})
public class MarFases implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fas_id")
    private BigDecimal fasId;
    @Size(max = 5)
    @Column(name = "fas_codigo")
    private String fasCodigo;
    @Size(max = 50)
    @Column(name = "fas_nombre")
    private String fasNombre;
    @Column(name = "fas_orden")
    private Short fasOrden;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fasId")
    private List<MarRadicacionesFases> marRadicacionesFasesList;

    public MarFases() {
    }

    public MarFases(BigDecimal fasId) {
        this.fasId = fasId;
    }

    public BigDecimal getFasId() {
        return fasId;
    }

    public void setFasId(BigDecimal fasId) {
        this.fasId = fasId;
    }

    public String getFasCodigo() {
        return fasCodigo;
    }

    public void setFasCodigo(String fasCodigo) {
        this.fasCodigo = fasCodigo;
    }

    public String getFasNombre() {
        return fasNombre;
    }

    public void setFasNombre(String fasNombre) {
        this.fasNombre = fasNombre;
    }

    public Short getFasOrden() {
        return fasOrden;
    }

    public void setFasOrden(Short fasOrden) {
        this.fasOrden = fasOrden;
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

    public List<MarRadicacionesFases> getMarRadicacionesFasesList() {
        return marRadicacionesFasesList;
    }

    public void setMarRadicacionesFasesList(List<MarRadicacionesFases> marRadicacionesFasesList) {
        this.marRadicacionesFasesList = marRadicacionesFasesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fasId != null ? fasId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarFases)) {
            return false;
        }
        MarFases other = (MarFases) object;
        if ((this.fasId == null && other.fasId != null) || (this.fasId != null && !this.fasId.equals(other.fasId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarFases[ fasId=" + fasId + " ]";
    }
    
}
