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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "mar_paises")
@NamedQueries({
    @NamedQuery(name = "MarPaises.findAll", query = "SELECT m FROM MarPaises m"),
    @NamedQuery(name = "MarPaises.findByPaiId", query = "SELECT m FROM MarPaises m WHERE m.paiId = :paiId"),
    @NamedQuery(name = "MarPaises.findByPaiNombre", query = "SELECT m FROM MarPaises m WHERE m.paiNombre = :paiNombre"),
    @NamedQuery(name = "MarPaises.findByPaiSigla", query = "SELECT m FROM MarPaises m WHERE m.paiSigla = :paiSigla"),
    @NamedQuery(name = "MarPaises.findByAudUsuario", query = "SELECT m FROM MarPaises m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarPaises.findByAudFecha", query = "SELECT m FROM MarPaises m WHERE m.audFecha = :audFecha")})
public class MarPaises implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pai_id", nullable = false, precision = 131089, scale = 0)
    private BigDecimal paiId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "pai_nombre", nullable = false, length = 100)
    private String paiNombre;
    @Size(max = 5)
    @Column(name = "pai_sigla", length = 5)
    private String paiSigla;
    @Size(max = 50)
    @Column(name = "aud_usuario", length = 50)
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paiId")
    private List<MarDepartamentos> marDepartamentosList;

    public MarPaises() {
    }

    public MarPaises(BigDecimal paiId) {
        this.paiId = paiId;
    }

    public MarPaises(BigDecimal paiId, String paiNombre) {
        this.paiId = paiId;
        this.paiNombre = paiNombre;
    }

    public BigDecimal getPaiId() {
        return paiId;
    }

    public void setPaiId(BigDecimal paiId) {
        this.paiId = paiId;
    }

    public String getPaiNombre() {
        return paiNombre;
    }

    public void setPaiNombre(String paiNombre) {
        this.paiNombre = paiNombre;
    }

    public String getPaiSigla() {
        return paiSigla;
    }

    public void setPaiSigla(String paiSigla) {
        this.paiSigla = paiSigla;
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

    public List<MarDepartamentos> getMarDepartamentosList() {
        return marDepartamentosList;
    }

    public void setMarDepartamentosList(List<MarDepartamentos> marDepartamentosList) {
        this.marDepartamentosList = marDepartamentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paiId != null ? paiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPaises)) {
            return false;
        }
        MarPaises other = (MarPaises) object;
        if ((this.paiId == null && other.paiId != null) || (this.paiId != null && !this.paiId.equals(other.paiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarPaises[ paiId=" + paiId + " ]";
    }
    
}
