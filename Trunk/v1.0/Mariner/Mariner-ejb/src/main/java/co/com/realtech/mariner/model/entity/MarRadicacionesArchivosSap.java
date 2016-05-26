/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "MAR_RADICACIONES_ARCHIVOS_SAP")
@NamedQueries({
    @NamedQuery(name = "MarRadicacionesArchivosSap.findAll", query = "SELECT m FROM MarRadicacionesArchivosSap m"),
    @NamedQuery(name = "MarRadicacionesArchivosSap.findByArsId", query = "SELECT m FROM MarRadicacionesArchivosSap m WHERE m.arsId = :arsId"),
    @NamedQuery(name = "MarRadicacionesArchivosSap.findByArsLiquidacion", query = "SELECT m FROM MarRadicacionesArchivosSap m WHERE m.arsLiquidacion = :arsLiquidacion"),
    @NamedQuery(name = "MarRadicacionesArchivosSap.findByAudFecha", query = "SELECT m FROM MarRadicacionesArchivosSap m WHERE m.audFecha = :audFecha")})
public class MarRadicacionesArchivosSap implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_radicaciones_archivos_s")
    @SequenceGenerator(name = "sq_mar_radicaciones_archivos_s", sequenceName = "sq_mar_radicaciones_archivos_s")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ARS_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal arsId;
    @Size(max = 40)
    @Column(name = "ARS_LIQUIDACION", length = 40)
    private String arsLiquidacion;
    @Size(max = 20)
    @Column(name = "AUD_FECHA", length = 20)
    private String audFecha;
    @JoinColumn(name = "ARC_ID_BOLETA", referencedColumnName = "ARC_ID")
    @ManyToOne
    private MarArchivos arcIdBoleta;
    @JoinColumn(name = "ARC_ID_RECIBO", referencedColumnName = "ARC_ID")
    @ManyToOne
    private MarArchivos arcIdRecibo;

    public MarRadicacionesArchivosSap() {
    }

    public MarRadicacionesArchivosSap(BigDecimal arsId) {
        this.arsId = arsId;
    }

    public BigDecimal getArsId() {
        return arsId;
    }

    public void setArsId(BigDecimal arsId) {
        this.arsId = arsId;
    }

    public String getArsLiquidacion() {
        return arsLiquidacion;
    }

    public void setArsLiquidacion(String arsLiquidacion) {
        this.arsLiquidacion = arsLiquidacion;
    }

    public String getAudFecha() {
        return audFecha;
    }

    public void setAudFecha(String audFecha) {
        this.audFecha = audFecha;
    }

    public MarArchivos getArcIdBoleta() {
        return arcIdBoleta;
    }

    public void setArcIdBoleta(MarArchivos arcIdBoleta) {
        this.arcIdBoleta = arcIdBoleta;
    }

    public MarArchivos getArcIdRecibo() {
        return arcIdRecibo;
    }

    public void setArcIdRecibo(MarArchivos arcIdRecibo) {
        this.arcIdRecibo = arcIdRecibo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (arsId != null ? arsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicacionesArchivosSap)) {
            return false;
        }
        MarRadicacionesArchivosSap other = (MarRadicacionesArchivosSap) object;
        if ((this.arsId == null && other.arsId != null) || (this.arsId != null && !this.arsId.equals(other.arsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicacionesArchivosSap[ arsId=" + arsId + " ]";
    }
    
}
