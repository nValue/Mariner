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
@Table(name = "MAR_TIPOS_DOCUMENTOS")
@NamedQueries({
    @NamedQuery(name = "MarTiposDocumentos.findAll", query = "SELECT m FROM MarTiposDocumentos m"),
    @NamedQuery(name = "MarTiposDocumentos.findByTdcId", query = "SELECT m FROM MarTiposDocumentos m WHERE m.tdcId = :tdcId"),
    @NamedQuery(name = "MarTiposDocumentos.findByTdcSigla", query = "SELECT m FROM MarTiposDocumentos m WHERE m.tdcSigla = :tdcSigla"),
    @NamedQuery(name = "MarTiposDocumentos.findByTdcNombre", query = "SELECT m FROM MarTiposDocumentos m WHERE m.tdcNombre = :tdcNombre"),
    @NamedQuery(name = "MarTiposDocumentos.findByAudUsuario", query = "SELECT m FROM MarTiposDocumentos m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarTiposDocumentos.findByAudFecha", query = "SELECT m FROM MarTiposDocumentos m WHERE m.audFecha = :audFecha")})
public class MarTiposDocumentos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_tipos_documentos")
    @SequenceGenerator(name = "sq_mar_tipos_documentos", sequenceName = "sq_mar_tipos_documentos")
    @Basic(optional = false)
    @NotNull
    @Column(name = "TDC_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal tdcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "TDC_SIGLA", nullable = false, length = 5)
    private String tdcSigla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TDC_NOMBRE", nullable = false, length = 100)
    private String tdcNombre;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "tdcId")
    private List<MarPersonas> marPersonasList;
    @OneToMany(mappedBy = "tdcId")
    private List<MarTransacciones> marTransaccionesList;

    public MarTiposDocumentos() {
    }

    public MarTiposDocumentos(BigDecimal tdcId) {
        this.tdcId = tdcId;
    }

    public MarTiposDocumentos(BigDecimal tdcId, String tdcSigla, String tdcNombre) {
        this.tdcId = tdcId;
        this.tdcSigla = tdcSigla;
        this.tdcNombre = tdcNombre;
    }

    public BigDecimal getTdcId() {
        return tdcId;
    }

    public void setTdcId(BigDecimal tdcId) {
        this.tdcId = tdcId;
    }

    public String getTdcSigla() {
        return tdcSigla;
    }

    public void setTdcSigla(String tdcSigla) {
        this.tdcSigla = tdcSigla;
    }

    public String getTdcNombre() {
        return tdcNombre;
    }

    public void setTdcNombre(String tdcNombre) {
        this.tdcNombre = tdcNombre;
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

    public List<MarPersonas> getMarPersonasList() {
        return marPersonasList;
    }

    public void setMarPersonasList(List<MarPersonas> marPersonasList) {
        this.marPersonasList = marPersonasList;
    }

    public List<MarTransacciones> getMarTransaccionesList() {
        return marTransaccionesList;
    }

    public void setMarTransaccionesList(List<MarTransacciones> marTransaccionesList) {
        this.marTransaccionesList = marTransaccionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdcId != null ? tdcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarTiposDocumentos)) {
            return false;
        }
        MarTiposDocumentos other = (MarTiposDocumentos) object;
        if ((this.tdcId == null && other.tdcId != null) || (this.tdcId != null && !this.tdcId.equals(other.tdcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarTiposDocumentos[ tdcId=" + tdcId + " ]";
    }
    
}
