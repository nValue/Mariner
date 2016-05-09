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
 * @author fabianagudelo
 */
@Entity
@Table(name = "mar_departamentos")
@NamedQueries({
    @NamedQuery(name = "MarDepartamentos.findAll", query = "SELECT m FROM MarDepartamentos m"),
    @NamedQuery(name = "MarDepartamentos.findByDepId", query = "SELECT m FROM MarDepartamentos m WHERE m.depId = :depId"),
    @NamedQuery(name = "MarDepartamentos.findByDepNombre", query = "SELECT m FROM MarDepartamentos m WHERE m.depNombre = :depNombre"),
    @NamedQuery(name = "MarDepartamentos.findByAudUsuario", query = "SELECT m FROM MarDepartamentos m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarDepartamentos.findByAudFecha", query = "SELECT m FROM MarDepartamentos m WHERE m.audFecha = :audFecha")})
public class MarDepartamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dep_id")
    private BigDecimal depId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "dep_nombre")
    private String depNombre;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "pai_id", referencedColumnName = "pai_id")
    @ManyToOne(optional = false)
    private MarPaises paiId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId")
    private List<MarCiudades> marCiudadesList;

    public MarDepartamentos() {
    }

    public MarDepartamentos(BigDecimal depId) {
        this.depId = depId;
    }

    public MarDepartamentos(BigDecimal depId, String depNombre) {
        this.depId = depId;
        this.depNombre = depNombre;
    }

    public BigDecimal getDepId() {
        return depId;
    }

    public void setDepId(BigDecimal depId) {
        this.depId = depId;
    }

    public String getDepNombre() {
        return depNombre;
    }

    public void setDepNombre(String depNombre) {
        this.depNombre = depNombre;
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

    public MarPaises getPaiId() {
        return paiId;
    }

    public void setPaiId(MarPaises paiId) {
        this.paiId = paiId;
    }

    public List<MarCiudades> getMarCiudadesList() {
        return marCiudadesList;
    }

    public void setMarCiudadesList(List<MarCiudades> marCiudadesList) {
        this.marCiudadesList = marCiudadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depId != null ? depId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarDepartamentos)) {
            return false;
        }
        MarDepartamentos other = (MarDepartamentos) object;
        if ((this.depId == null && other.depId != null) || (this.depId != null && !this.depId.equals(other.depId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarDepartamentos[ depId=" + depId + " ]";
    }
    
}
