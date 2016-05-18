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
@Table(name = "MAR_FASES_ESTADOS")
@NamedQueries({
    @NamedQuery(name = "MarFasesEstados.findAll", query = "SELECT m FROM MarFasesEstados m"),
    @NamedQuery(name = "MarFasesEstados.findByFesId", query = "SELECT m FROM MarFasesEstados m WHERE m.fesId = :fesId"),
    @NamedQuery(name = "MarFasesEstados.findByFesCodigo", query = "SELECT m FROM MarFasesEstados m WHERE m.fesCodigo = :fesCodigo"),
    @NamedQuery(name = "MarFasesEstados.findByFesNombre", query = "SELECT m FROM MarFasesEstados m WHERE m.fesNombre = :fesNombre"),
    @NamedQuery(name = "MarFasesEstados.findByFesOrden", query = "SELECT m FROM MarFasesEstados m WHERE m.fesOrden = :fesOrden"),
    @NamedQuery(name = "MarFasesEstados.findByAudUsuario", query = "SELECT m FROM MarFasesEstados m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarFasesEstados.findByAudFecha", query = "SELECT m FROM MarFasesEstados m WHERE m.audFecha = :audFecha")})
public class MarFasesEstados implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_fases_estados")
    @SequenceGenerator(name = "sq_mar_fases_estados", sequenceName = "sq_mar_fases_estados")
    @Basic(optional = false)
    @NotNull
    @Column(name = "FES_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal fesId;
    @Size(max = 20)
    @Column(name = "FES_CODIGO", length = 20)
    private String fesCodigo;
    @Size(max = 100)
    @Column(name = "FES_NOMBRE", length = 100)
    private String fesNombre;
    @Column(name = "FES_ORDEN")
    private Short fesOrden;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "FAS_ID", referencedColumnName = "FAS_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarFases fasId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fesId")
    private List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList;

    public MarFasesEstados() {
    }

    public MarFasesEstados(BigDecimal fesId) {
        this.fesId = fesId;
    }

    public BigDecimal getFesId() {
        return fesId;
    }

    public void setFesId(BigDecimal fesId) {
        this.fesId = fesId;
    }

    public String getFesCodigo() {
        return fesCodigo;
    }

    public void setFesCodigo(String fesCodigo) {
        this.fesCodigo = fesCodigo;
    }

    public String getFesNombre() {
        return fesNombre;
    }

    public void setFesNombre(String fesNombre) {
        this.fesNombre = fesNombre;
    }

    public Short getFesOrden() {
        return fesOrden;
    }

    public void setFesOrden(Short fesOrden) {
        this.fesOrden = fesOrden;
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

    public MarFases getFasId() {
        return fasId;
    }

    public void setFasId(MarFases fasId) {
        this.fasId = fasId;
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
        hash += (fesId != null ? fesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarFasesEstados)) {
            return false;
        }
        MarFasesEstados other = (MarFasesEstados) object;
        if ((this.fesId == null && other.fesId != null) || (this.fesId != null && !this.fesId.equals(other.fesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarFasesEstados[ fesId=" + fesId + " ]";
    }
    
}
