/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "mar_reportes")
@NamedQueries({
    @NamedQuery(name = "MarReportes.findAll", query = "SELECT m FROM MarReportes m"),
    @NamedQuery(name = "MarReportes.findByRepId", query = "SELECT m FROM MarReportes m WHERE m.repId = :repId"),
    @NamedQuery(name = "MarReportes.findByRepCodigo", query = "SELECT m FROM MarReportes m WHERE m.repCodigo = :repCodigo"),
    @NamedQuery(name = "MarReportes.findByRepNombre", query = "SELECT m FROM MarReportes m WHERE m.repNombre = :repNombre"),
    @NamedQuery(name = "MarReportes.findByRepEstado", query = "SELECT m FROM MarReportes m WHERE m.repEstado = :repEstado"),
    @NamedQuery(name = "MarReportes.findByRepExtension", query = "SELECT m FROM MarReportes m WHERE m.repExtension = :repExtension"),
    @NamedQuery(name = "MarReportes.findByRepQuery", query = "SELECT m FROM MarReportes m WHERE m.repQuery = :repQuery"),
    @NamedQuery(name = "MarReportes.findByAudUsuario", query = "SELECT m FROM MarReportes m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarReportes.findByAudFecha", query = "SELECT m FROM MarReportes m WHERE m.audFecha = :audFecha")})
public class MarReportes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rep_id")
    private BigDecimal repId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "rep_codigo")
    private String repCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rep_nombre")
    private String repNombre;
    @Size(max = 1)
    @Column(name = "rep_estado")
    private String repEstado;
    @Size(max = 10)
    @Column(name = "rep_extension")
    private String repExtension;
    @Size(max = 4000)
    @Column(name = "rep_query")
    private String repQuery;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "arc_id", referencedColumnName = "arc_id")
    @ManyToOne
    private MarArchivos arcId;
    @JoinColumn(name = "rti_id", referencedColumnName = "rti_id")
    @ManyToOne(optional = false)
    private MarReportesTipos rtiId;

    public MarReportes() {
    }

    public MarReportes(BigDecimal repId) {
        this.repId = repId;
    }

    public MarReportes(BigDecimal repId, String repCodigo, String repNombre) {
        this.repId = repId;
        this.repCodigo = repCodigo;
        this.repNombre = repNombre;
    }

    public BigDecimal getRepId() {
        return repId;
    }

    public void setRepId(BigDecimal repId) {
        this.repId = repId;
    }

    public String getRepCodigo() {
        return repCodigo;
    }

    public void setRepCodigo(String repCodigo) {
        this.repCodigo = repCodigo;
    }

    public String getRepNombre() {
        return repNombre;
    }

    public void setRepNombre(String repNombre) {
        this.repNombre = repNombre;
    }

    public String getRepEstado() {
        return repEstado;
    }

    public void setRepEstado(String repEstado) {
        this.repEstado = repEstado;
    }

    public String getRepExtension() {
        return repExtension;
    }

    public void setRepExtension(String repExtension) {
        this.repExtension = repExtension;
    }

    public String getRepQuery() {
        return repQuery;
    }

    public void setRepQuery(String repQuery) {
        this.repQuery = repQuery;
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

    public MarArchivos getArcId() {
        return arcId;
    }

    public void setArcId(MarArchivos arcId) {
        this.arcId = arcId;
    }

    public MarReportesTipos getRtiId() {
        return rtiId;
    }

    public void setRtiId(MarReportesTipos rtiId) {
        this.rtiId = rtiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repId != null ? repId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarReportes)) {
            return false;
        }
        MarReportes other = (MarReportes) object;
        if ((this.repId == null && other.repId != null) || (this.repId != null && !this.repId.equals(other.repId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarReportes[ repId=" + repId + " ]";
    }
    
}
