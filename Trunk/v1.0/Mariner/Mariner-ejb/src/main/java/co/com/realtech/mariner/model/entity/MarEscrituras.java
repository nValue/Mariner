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
@Table(name = "MAR_ESCRITURAS")
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
    @GeneratedValue(generator = "sq_mar_escrituras")
    @SequenceGenerator(name = "sq_mar_escrituras", sequenceName = "sq_mar_escrituras")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESC_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal escId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESC_FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date escFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ESC_NUMERO", nullable = false, length = 100)
    private String escNumero;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "escId")
    private List<MarRadicaciones> marRadicacionesList;
    @JoinColumn(name = "ARC_ID", referencedColumnName = "ARC_ID", nullable = false)
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

    public List<MarRadicaciones> getMarRadicacionesList() {
        return marRadicacionesList;
    }

    public void setMarRadicacionesList(List<MarRadicaciones> marRadicacionesList) {
        this.marRadicacionesList = marRadicacionesList;
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
