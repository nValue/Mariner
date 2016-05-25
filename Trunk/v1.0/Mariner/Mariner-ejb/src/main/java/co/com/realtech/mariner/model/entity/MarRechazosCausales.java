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
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "MAR_RECHAZOS_CAUSALES")
@NamedQueries({
    @NamedQuery(name = "MarRechazosCausales.findAll", query = "SELECT m FROM MarRechazosCausales m"),
    @NamedQuery(name = "MarRechazosCausales.findByRcaId", query = "SELECT m FROM MarRechazosCausales m WHERE m.rcaId = :rcaId"),
    @NamedQuery(name = "MarRechazosCausales.findByRcaNombres", query = "SELECT m FROM MarRechazosCausales m WHERE m.rcaNombres = :rcaNombres"),
    @NamedQuery(name = "MarRechazosCausales.findByAudUsuario", query = "SELECT m FROM MarRechazosCausales m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRechazosCausales.findByAudFecha", query = "SELECT m FROM MarRechazosCausales m WHERE m.audFecha = :audFecha")})
public class MarRechazosCausales implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_rechazos_causales")
    @SequenceGenerator(name = "sq_mar_rechazos_causales", sequenceName = "sq_mar_rechazos_causales")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RCA_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal rcaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "RCA_NOMBRES", nullable = false, length = 400)
    private String rcaNombres;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "rcaId")
    private List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList;

    public MarRechazosCausales() {
    }

    public MarRechazosCausales(BigDecimal rcaId) {
        this.rcaId = rcaId;
    }

    public MarRechazosCausales(BigDecimal rcaId, String rcaNombres) {
        this.rcaId = rcaId;
        this.rcaNombres = rcaNombres;
    }

    public BigDecimal getRcaId() {
        return rcaId;
    }

    public void setRcaId(BigDecimal rcaId) {
        this.rcaId = rcaId;
    }

    public String getRcaNombres() {
        return rcaNombres;
    }

    public void setRcaNombres(String rcaNombres) {
        this.rcaNombres = rcaNombres;
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

    public List<MarRadicacionesFasesEstados> getMarRadicacionesFasesEstadosList() {
        return marRadicacionesFasesEstadosList;
    }

    public void setMarRadicacionesFasesEstadosList(List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList) {
        this.marRadicacionesFasesEstadosList = marRadicacionesFasesEstadosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rcaId != null ? rcaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRechazosCausales)) {
            return false;
        }
        MarRechazosCausales other = (MarRechazosCausales) object;
        if ((this.rcaId == null && other.rcaId != null) || (this.rcaId != null && !this.rcaId.equals(other.rcaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRechazosCausales[ rcaId=" + rcaId + " ]";
    }
    
}
