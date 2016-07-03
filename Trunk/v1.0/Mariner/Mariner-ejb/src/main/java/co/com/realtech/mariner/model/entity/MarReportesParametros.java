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
@Table(name = "MAR_REPORTES_PARAMETROS")
@NamedQueries({
    @NamedQuery(name = "MarReportesParametros.findAll", query = "SELECT m FROM MarReportesParametros m"),
    @NamedQuery(name = "MarReportesParametros.findByRpaId", query = "SELECT m FROM MarReportesParametros m WHERE m.rpaId = :rpaId"),
    @NamedQuery(name = "MarReportesParametros.findByRpaNombre", query = "SELECT m FROM MarReportesParametros m WHERE m.rpaNombre = :rpaNombre"),
    @NamedQuery(name = "MarReportesParametros.findByRpaRequerido", query = "SELECT m FROM MarReportesParametros m WHERE m.rpaRequerido = :rpaRequerido"),
    @NamedQuery(name = "MarReportesParametros.findByRpaTipo", query = "SELECT m FROM MarReportesParametros m WHERE m.rpaTipo = :rpaTipo"),
    @NamedQuery(name = "MarReportesParametros.findByRpaQueryLista", query = "SELECT m FROM MarReportesParametros m WHERE m.rpaQueryLista = :rpaQueryLista"),
    @NamedQuery(name = "MarReportesParametros.findByRpaAlias", query = "SELECT m FROM MarReportesParametros m WHERE m.rpaAlias = :rpaAlias"),
    @NamedQuery(name = "MarReportesParametros.findByAudFecha", query = "SELECT m FROM MarReportesParametros m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarReportesParametros.findByAudUsuario", query = "SELECT m FROM MarReportesParametros m WHERE m.audUsuario = :audUsuario")})
public class MarReportesParametros implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_reportes_parametros")
    @SequenceGenerator(name = "sq_mar_reportes_parametros", sequenceName = "sq_mar_reportes_parametros")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RPA_ID")
    private BigDecimal rpaId;
    @Size(max = 100)
    @Column(name = "RPA_NOMBRE")
    private String rpaNombre;
    @Size(max = 1)
    @Column(name = "RPA_REQUERIDO")
    private String rpaRequerido;
    @Size(max = 100)
    @Column(name = "RPA_TIPO")
    private String rpaTipo;
    @Size(max = 2000)
    @Column(name = "RPA_QUERY_LISTA")
    private String rpaQueryLista;
    @Size(max = 100)
    @Column(name = "RPA_ALIAS")
    private String rpaAlias;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO")
    private String audUsuario;
    @JoinColumn(name = "REP_ID", referencedColumnName = "REP_ID")
    @ManyToOne(optional = false)
    private MarReportes repId;

    public MarReportesParametros() {
    }

    public MarReportesParametros(BigDecimal rpaId) {
        this.rpaId = rpaId;
    }

    public BigDecimal getRpaId() {
        return rpaId;
    }

    public void setRpaId(BigDecimal rpaId) {
        this.rpaId = rpaId;
    }

    public String getRpaNombre() {
        return rpaNombre;
    }

    public void setRpaNombre(String rpaNombre) {
        this.rpaNombre = rpaNombre;
    }

    public String getRpaRequerido() {
        return rpaRequerido;
    }

    public void setRpaRequerido(String rpaRequerido) {
        this.rpaRequerido = rpaRequerido;
    }

    public String getRpaTipo() {
        return rpaTipo;
    }

    public void setRpaTipo(String rpaTipo) {
        this.rpaTipo = rpaTipo;
    }

    public String getRpaQueryLista() {
        return rpaQueryLista;
    }

    public void setRpaQueryLista(String rpaQueryLista) {
        this.rpaQueryLista = rpaQueryLista;
    }

    public String getRpaAlias() {
        return rpaAlias;
    }

    public void setRpaAlias(String rpaAlias) {
        this.rpaAlias = rpaAlias;
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
        hash += (rpaId != null ? rpaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarReportesParametros)) {
            return false;
        }
        MarReportesParametros other = (MarReportesParametros) object;
        if ((this.rpaId == null && other.rpaId != null) || (this.rpaId != null && !this.rpaId.equals(other.rpaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarReportesParametros[ rpaId=" + rpaId + " ]";
    }
    
}
