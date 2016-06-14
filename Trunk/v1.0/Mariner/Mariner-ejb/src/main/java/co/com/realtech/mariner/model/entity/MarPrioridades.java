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
@Table(name = "MAR_PRIORIDADES")
@NamedQueries({
    @NamedQuery(name = "MarPrioridades.findAll", query = "SELECT m FROM MarPrioridades m"),
    @NamedQuery(name = "MarPrioridades.findByPriId", query = "SELECT m FROM MarPrioridades m WHERE m.priId = :priId"),
    @NamedQuery(name = "MarPrioridades.findByPriCodigo", query = "SELECT m FROM MarPrioridades m WHERE m.priCodigo = :priCodigo"),
    @NamedQuery(name = "MarPrioridades.findByPriOrden", query = "SELECT m FROM MarPrioridades m WHERE m.priOrden = :priOrden"),
    @NamedQuery(name = "MarPrioridades.findByPriNombre", query = "SELECT m FROM MarPrioridades m WHERE m.priNombre = :priNombre"),
    @NamedQuery(name = "MarPrioridades.findByAudUsuario", query = "SELECT m FROM MarPrioridades m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarPrioridades.findByAudFecha", query = "SELECT m FROM MarPrioridades m WHERE m.audFecha = :audFecha")})
public class MarPrioridades implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRI_ID")
    private BigDecimal priId;
    @Size(max = 10)
    @Column(name = "PRI_CODIGO")
    private String priCodigo;
    @Column(name = "PRI_ORDEN")
    private Short priOrden;
    @Size(max = 100)
    @Column(name = "PRI_NOMBRE")
    private String priNombre;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO")
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "priId")
    private List<MarRadicaciones> marRadicacionesList;

    public MarPrioridades() {
    }

    public MarPrioridades(BigDecimal priId) {
        this.priId = priId;
    }

    public BigDecimal getPriId() {
        return priId;
    }

    public void setPriId(BigDecimal priId) {
        this.priId = priId;
    }

    public String getPriCodigo() {
        return priCodigo;
    }

    public void setPriCodigo(String priCodigo) {
        this.priCodigo = priCodigo;
    }

    public Short getPriOrden() {
        return priOrden;
    }

    public void setPriOrden(Short priOrden) {
        this.priOrden = priOrden;
    }

    public String getPriNombre() {
        return priNombre;
    }

    public void setPriNombre(String priNombre) {
        this.priNombre = priNombre;
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
        hash += (priId != null ? priId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPrioridades)) {
            return false;
        }
        MarPrioridades other = (MarPrioridades) object;
        if ((this.priId == null && other.priId != null) || (this.priId != null && !this.priId.equals(other.priId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarPrioridades[ priId=" + priId + " ]";
    }
    
}
