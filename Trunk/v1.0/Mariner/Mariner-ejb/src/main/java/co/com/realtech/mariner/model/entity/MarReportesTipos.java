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
 * @author fabianagudelo
 */
@Entity
@Table(name = "mar_reportes_tipos")
@NamedQueries({
    @NamedQuery(name = "MarReportesTipos.findAll", query = "SELECT m FROM MarReportesTipos m"),
    @NamedQuery(name = "MarReportesTipos.findByRtiId", query = "SELECT m FROM MarReportesTipos m WHERE m.rtiId = :rtiId"),
    @NamedQuery(name = "MarReportesTipos.findByRtiCodigo", query = "SELECT m FROM MarReportesTipos m WHERE m.rtiCodigo = :rtiCodigo"),
    @NamedQuery(name = "MarReportesTipos.findByRtiNombre", query = "SELECT m FROM MarReportesTipos m WHERE m.rtiNombre = :rtiNombre"),
    @NamedQuery(name = "MarReportesTipos.findByAudUsuario", query = "SELECT m FROM MarReportesTipos m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarReportesTipos.findByAudFecha", query = "SELECT m FROM MarReportesTipos m WHERE m.audFecha = :audFecha")})
public class MarReportesTipos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rti_id")
    private BigDecimal rtiId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "rti_codigo")
    private String rtiCodigo;
    @Size(max = 200)
    @Column(name = "rti_nombre")
    private String rtiNombre;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rtiId")
    private List<MarReportes> marReportesList;

    public MarReportesTipos() {
    }

    public MarReportesTipos(BigDecimal rtiId) {
        this.rtiId = rtiId;
    }

    public MarReportesTipos(BigDecimal rtiId, String rtiCodigo) {
        this.rtiId = rtiId;
        this.rtiCodigo = rtiCodigo;
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
