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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
@Table(name = "MAR_REPORTES_GRAFICOS")
@NamedQueries({
    @NamedQuery(name = "MarReportesGraficos.findAll", query = "SELECT m FROM MarReportesGraficos m"),
    @NamedQuery(name = "MarReportesGraficos.findByRgrId", query = "SELECT m FROM MarReportesGraficos m WHERE m.rgrId = :rgrId"),
    @NamedQuery(name = "MarReportesGraficos.findByRgrPropiedades", query = "SELECT m FROM MarReportesGraficos m WHERE m.rgrPropiedades = :rgrPropiedades"),
    @NamedQuery(name = "MarReportesGraficos.findByRgrTipo", query = "SELECT m FROM MarReportesGraficos m WHERE m.rgrTipo = :rgrTipo"),
    @NamedQuery(name = "MarReportesGraficos.findByRgrAdiciones", query = "SELECT m FROM MarReportesGraficos m WHERE m.rgrAdiciones = :rgrAdiciones"),
    @NamedQuery(name = "MarReportesGraficos.findByAudFecha", query = "SELECT m FROM MarReportesGraficos m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarReportesGraficos.findByAudUsuario", query = "SELECT m FROM MarReportesGraficos m WHERE m.audUsuario = :audUsuario")})
public class MarReportesGraficos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_reportes_graficos")
    @SequenceGenerator(name = "sq_mar_reportes_graficos", sequenceName = "sq_mar_reportes_graficos")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RGR_ID")
    private BigDecimal rgrId;
    @Size(max = 500)
    @Column(name = "RGR_PROPIEDADES")
    private String rgrPropiedades;
    @Size(max = 20)
    @Column(name = "RGR_TIPO")
    private String rgrTipo;
    @Size(max = 50)
    @Column(name = "RGR_ADICIONES")
    private String rgrAdiciones;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO")
    private String audUsuario;
    @JoinColumn(name = "REP_ID", referencedColumnName = "REP_ID")
    @ManyToOne(optional = false)
    private MarReportes repId;

    public MarReportesGraficos() {
    }

    public MarReportesGraficos(BigDecimal rgrId) {
        this.rgrId = rgrId;
    }

    public BigDecimal getRgrId() {
        return rgrId;
    }

    public void setRgrId(BigDecimal rgrId) {
        this.rgrId = rgrId;
    }

    public String getRgrPropiedades() {
        return rgrPropiedades;
    }

    public void setRgrPropiedades(String rgrPropiedades) {
        this.rgrPropiedades = rgrPropiedades;
    }

    public String getRgrTipo() {
        return rgrTipo;
    }

    public void setRgrTipo(String rgrTipo) {
        this.rgrTipo = rgrTipo;
    }

    public String getRgrAdiciones() {
        return rgrAdiciones;
    }

    public void setRgrAdiciones(String rgrAdiciones) {
        this.rgrAdiciones = rgrAdiciones;
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

    public MarReportes getRepId() {
        return repId;
    }

    public void setRepId(MarReportes repId) {
        this.repId = repId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rgrId != null ? rgrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarReportesGraficos)) {
            return false;
        }
        MarReportesGraficos other = (MarReportesGraficos) object;
        if ((this.rgrId == null && other.rgrId != null) || (this.rgrId != null && !this.rgrId.equals(other.rgrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarReportesGraficos[ rgrId=" + rgrId + " ]";
    }
    
}
