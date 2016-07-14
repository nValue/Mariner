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
@Table(name = "MAR_REPORTES_TIPOS")
@NamedQueries({
    @NamedQuery(name = "MarReportesTipos.findAll", query = "SELECT m FROM MarReportesTipos m"),
    @NamedQuery(name = "MarReportesTipos.findByRtiId", query = "SELECT m FROM MarReportesTipos m WHERE m.rtiId = :rtiId"),
    @NamedQuery(name = "MarReportesTipos.findByRtiCodigo", query = "SELECT m FROM MarReportesTipos m WHERE m.rtiCodigo = :rtiCodigo"),
    @NamedQuery(name = "MarReportesTipos.findByRtiNombre", query = "SELECT m FROM MarReportesTipos m WHERE m.rtiNombre = :rtiNombre"),
    @NamedQuery(name = "MarReportesTipos.findByAudFecha", query = "SELECT m FROM MarReportesTipos m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarReportesTipos.findByAudUsuario", query = "SELECT m FROM MarReportesTipos m WHERE m.audUsuario = :audUsuario")})
public class MarReportesTipos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_reportes_tipos")
    @SequenceGenerator(name = "sq_mar_reportes_tipos", sequenceName = "sq_mar_reportes_tipos")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RTI_ID", nullable = false, precision = 38, scale = 0)
    private BigDecimal rtiId;
    @Size(max = 20)
    @Column(name = "RTI_CODIGO", length = 20)
    private String rtiCodigo;
    @Size(max = 200)
    @Column(name = "RTI_NOMBRE", length = 200)
    private String rtiNombre;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @OneToMany(mappedBy = "rtiId")
    private List<MarReportes> marReportesList;

    public MarReportesTipos() {
    }

    public MarReportesTipos(BigDecimal rtiId) {
        this.rtiId = rtiId;
    }

    public BigDecimal getRtiId() {
        return rtiId;
    }

    public void setRtiId(BigDecimal rtiId) {
        this.rtiId = rtiId;
    }

    public String getRtiCodigo() {
        return rtiCodigo;
    }

    public void setRtiCodigo(String rtiCodigo) {
        this.rtiCodigo = rtiCodigo;
    }

    public String getRtiNombre() {
        return rtiNombre;
    }

    public void setRtiNombre(String rtiNombre) {
        this.rtiNombre = rtiNombre;
    }

    public Date getAudFecha() {
        return audFecha;
    }

    public void setAudFecha(Date audFecha) {
        this.audFecha = audFecha;
    }

    public String getAudUsuario() {
        return audUsuario;
    }

    public void setAudUsuario(String audUsuario) {
        this.audUsuario = audUsuario;
    }

    public List<MarReportes> getMarReportesList() {
        return marReportesList;
    }

    public void setMarReportesList(List<MarReportes> marReportesList) {
        this.marReportesList = marReportesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rtiId != null ? rtiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarReportesTipos)) {
            return false;
        }
        MarReportesTipos other = (MarReportesTipos) object;
        if ((this.rtiId == null && other.rtiId != null) || (this.rtiId != null && !this.rtiId.equals(other.rtiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarReportesTipos[ rtiId=" + rtiId + " ]";
    }
    
}
