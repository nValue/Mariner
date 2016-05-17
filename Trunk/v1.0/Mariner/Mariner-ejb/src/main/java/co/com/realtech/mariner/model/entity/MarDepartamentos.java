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
@Table(name = "MAR_DEPARTAMENTOS")
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
    @GeneratedValue(generator = "sq_mar_departamentos")
    @SequenceGenerator(name = "sq_mar_departamentos", sequenceName = "sq_mar_departamentos")
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEP_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal depId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DEP_NOMBRE", nullable = false, length = 100)
    private String depNombre;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId")
    private List<MarCiudades> marCiudadesList;
    @JoinColumn(name = "PAI_ID", referencedColumnName = "PAI_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarPaises paiId;

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

    public List<MarCiudades> getMarCiudadesList() {
        return marCiudadesList;
    }

    public void setMarCiudadesList(List<MarCiudades> marCiudadesList) {
        this.marCiudadesList = marCiudadesList;
    }

    public MarPaises getPaiId() {
        return paiId;
    }

    public void setPaiId(MarPaises paiId) {
        this.paiId = paiId;
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
