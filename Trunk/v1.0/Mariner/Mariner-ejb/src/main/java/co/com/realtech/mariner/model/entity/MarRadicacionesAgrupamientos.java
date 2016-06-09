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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "MAR_RADICACIONES_AGRUPAMIENTOS")
@NamedQueries({
    @NamedQuery(name = "MarRadicacionesAgrupamientos.findAll", query = "SELECT m FROM MarRadicacionesAgrupamientos m"),
    @NamedQuery(name = "MarRadicacionesAgrupamientos.findByRaaId", query = "SELECT m FROM MarRadicacionesAgrupamientos m WHERE m.raaId = :raaId"),
    @NamedQuery(name = "MarRadicacionesAgrupamientos.findByRaaAgrupaLiqs", query = "SELECT m FROM MarRadicacionesAgrupamientos m WHERE m.raaAgrupaLiqs = :raaAgrupaLiqs"),
    @NamedQuery(name = "MarRadicacionesAgrupamientos.findByRaaAgrupaEscs", query = "SELECT m FROM MarRadicacionesAgrupamientos m WHERE m.raaAgrupaEscs = :raaAgrupaEscs"),
    @NamedQuery(name = "MarRadicacionesAgrupamientos.findByAudUsuario", query = "SELECT m FROM MarRadicacionesAgrupamientos m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRadicacionesAgrupamientos.findByAudFecha", query = "SELECT m FROM MarRadicacionesAgrupamientos m WHERE m.audFecha = :audFecha")})
public class MarRadicacionesAgrupamientos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_radicaciones_agrupamientos")
    @SequenceGenerator(name = "sq_radicaciones_agrupamientos", sequenceName = "sq_radicaciones_agrupamientos")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RAA_ID")
    private BigDecimal raaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RAA_AGRUPA_LIQS")
    private String raaAgrupaLiqs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "RAA_AGRUPA_ESCS")
    private String raaAgrupaEscs;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO")
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "raaId")
    private List<MarRadicaciones> marRadicacionesList;

    public MarRadicacionesAgrupamientos() {
    }

    public MarRadicacionesAgrupamientos(BigDecimal raaId) {
        this.raaId = raaId;
    }

    public MarRadicacionesAgrupamientos(BigDecimal raaId, String raaAgrupaLiqs, String raaAgrupaEscs) {
        this.raaId = raaId;
        this.raaAgrupaLiqs = raaAgrupaLiqs;
        this.raaAgrupaEscs = raaAgrupaEscs;
    }

    public BigDecimal getRaaId() {
        return raaId;
    }

    public void setRaaId(BigDecimal raaId) {
        this.raaId = raaId;
    }

    public String getRaaAgrupaLiqs() {
        return raaAgrupaLiqs;
    }

    public void setRaaAgrupaLiqs(String raaAgrupaLiqs) {
        this.raaAgrupaLiqs = raaAgrupaLiqs;
    }

    public String getRaaAgrupaEscs() {
        return raaAgrupaEscs;
    }

    public void setRaaAgrupaEscs(String raaAgrupaEscs) {
        this.raaAgrupaEscs = raaAgrupaEscs;
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

    public List<MarRadicaciones> getMarRadicacionesList() {
        return marRadicacionesList;
    }

    public void setMarRadicacionesList(List<MarRadicaciones> marRadicacionesList) {
        this.marRadicacionesList = marRadicacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (raaId != null ? raaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicacionesAgrupamientos)) {
            return false;
        }
        MarRadicacionesAgrupamientos other = (MarRadicacionesAgrupamientos) object;
        if ((this.raaId == null && other.raaId != null) || (this.raaId != null && !this.raaId.equals(other.raaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicacionesAgrupamientos[ raaId=" + raaId + " ]";
    }
    
}
