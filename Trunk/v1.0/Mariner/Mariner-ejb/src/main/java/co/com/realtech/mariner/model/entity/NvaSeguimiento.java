/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "NVA_SEGUIMIENTO")
@NamedQueries({
    @NamedQuery(name = "NvaSeguimiento.findAll", query = "SELECT n FROM NvaSeguimiento n"),
    @NamedQuery(name = "NvaSeguimiento.findBySegId", query = "SELECT n FROM NvaSeguimiento n WHERE n.segId = :segId"),
    @NamedQuery(name = "NvaSeguimiento.findBySegClave", query = "SELECT n FROM NvaSeguimiento n WHERE n.segClave = :segClave"),
    @NamedQuery(name = "NvaSeguimiento.findBySegMensaje", query = "SELECT n FROM NvaSeguimiento n WHERE n.segMensaje = :segMensaje"),
    @NamedQuery(name = "NvaSeguimiento.findByAudFecha", query = "SELECT n FROM NvaSeguimiento n WHERE n.audFecha = :audFecha")})
public class NvaSeguimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_nva_seguimiento")
    @SequenceGenerator(name = "sq_nva_seguimiento", sequenceName = "sq_nva_seguimiento")
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEG_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal segId;
    @Size(max = 100)
    @Column(name = "SEG_CLAVE", length = 100)
    private String segClave;
    @Size(max = 1000)
    @Column(name = "SEG_MENSAJE", length = 1000)
    private String segMensaje;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;

    public NvaSeguimiento() {
    }

    public NvaSeguimiento(BigDecimal segId) {
        this.segId = segId;
    }

    public BigDecimal getSegId() {
        return segId;
    }

    public void setSegId(BigDecimal segId) {
        this.segId = segId;
    }

    public String getSegClave() {
        return segClave;
    }

    public void setSegClave(String segClave) {
        this.segClave = segClave;
    }

    public String getSegMensaje() {
        return segMensaje;
    }

    public void setSegMensaje(String segMensaje) {
        this.segMensaje = segMensaje;
    }

    public Date getAudFecha() {
        return audFecha;
    }

    public void setAudFecha(Date audFecha) {
        this.audFecha = audFecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (segId != null ? segId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NvaSeguimiento)) {
            return false;
        }
        NvaSeguimiento other = (NvaSeguimiento) object;
        if ((this.segId == null && other.segId != null) || (this.segId != null && !this.segId.equals(other.segId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.NvaSeguimiento[ segId=" + segId + " ]";
    }
    
}
