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
 * @author Andres Rivera
 */
@Entity
@Table(name = "MAR_RADICACIONES_FASES_ESTADOS")
@NamedQueries({
    @NamedQuery(name = "MarRadicacionesFasesEstados.findAll", query = "SELECT m FROM MarRadicacionesFasesEstados m"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeId", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeId = :rfeId"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeFechaInicio", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeFechaInicio = :rfeFechaInicio"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeEstado", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeEstado = :rfeEstado"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeCodigoSap", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeCodigoSap = :rfeCodigoSap"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeObservaciones", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeObservaciones = :rfeObservaciones"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeIp", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeIp = :rfeIp"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByAudUsuario", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByAudFecha", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeFechaFin", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeFechaFin = :rfeFechaFin"),
    @NamedQuery(name = "MarRadicacionesFasesEstados.findByRfeEstadoAprobacion", query = "SELECT m FROM MarRadicacionesFasesEstados m WHERE m.rfeEstadoAprobacion = :rfeEstadoAprobacion")})
public class MarRadicacionesFasesEstados implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_radicaciones_fases_esta")
    @SequenceGenerator(name = "sq_mar_radicaciones_fases_esta", sequenceName = "sq_mar_radicaciones_fases_esta")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RFE_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal rfeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RFE_FECHA_INICIO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date rfeFechaInicio;
    @Size(max = 1)
    @Column(name = "RFE_ESTADO", length = 1)
    private String rfeEstado;
    @Size(max = 200)
    @Column(name = "RFE_CODIGO_SAP", length = 200)
    private String rfeCodigoSap;
    @Size(max = 1000)
    @Column(name = "RFE_OBSERVACIONES", length = 1000)
    private String rfeObservaciones;
    @Size(max = 20)
    @Column(name = "RFE_IP", length = 20)
    private String rfeIp;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Column(name = "RFE_FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rfeFechaFin;
    @Size(max = 1)
    @Column(name = "RFE_ESTADO_APROBACION", length = 1)
    private String rfeEstadoAprobacion;
    @JoinColumn(name = "FES_ID", referencedColumnName = "FES_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarFasesEstados fesId;
    @JoinColumn(name = "RAD_ID", referencedColumnName = "RAD_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarRadicaciones radId;
    @JoinColumn(name = "USU_ID", referencedColumnName = "USU_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarUsuarios usuId;

    public MarRadicacionesFasesEstados() {
    }

    public MarRadicacionesFasesEstados(BigDecimal rfeId) {
        this.rfeId = rfeId;
    }

    public MarRadicacionesFasesEstados(BigDecimal rfeId, Date rfeFechaInicio) {
        this.rfeId = rfeId;
        this.rfeFechaInicio = rfeFechaInicio;
    }

    public BigDecimal getRfeId() {
        return rfeId;
    }

    public void setRfeId(BigDecimal rfeId) {
        this.rfeId = rfeId;
    }

    public Date getRfeFechaInicio() {
        return rfeFechaInicio;
    }

    public void setRfeFechaInicio(Date rfeFechaInicio) {
        this.rfeFechaInicio = rfeFechaInicio;
    }

    public String getRfeEstado() {
        return rfeEstado;
    }

    public void setRfeEstado(String rfeEstado) {
        this.rfeEstado = rfeEstado;
    }

    public String getRfeCodigoSap() {
        return rfeCodigoSap;
    }

    public void setRfeCodigoSap(String rfeCodigoSap) {
        this.rfeCodigoSap = rfeCodigoSap;
    }

    public String getRfeObservaciones() {
        return rfeObservaciones;
    }

    public void setRfeObservaciones(String rfeObservaciones) {
        this.rfeObservaciones = rfeObservaciones;
    }

    public String getRfeIp() {
        return rfeIp;
    }

    public void setRfeIp(String rfeIp) {
        this.rfeIp = rfeIp;
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

    public Date getRfeFechaFin() {
        return rfeFechaFin;
    }

    public void setRfeFechaFin(Date rfeFechaFin) {
        this.rfeFechaFin = rfeFechaFin;
    }

    public String getRfeEstadoAprobacion() {
        return rfeEstadoAprobacion;
    }

    public void setRfeEstadoAprobacion(String rfeEstadoAprobacion) {
        this.rfeEstadoAprobacion = rfeEstadoAprobacion;
    }

    public MarFasesEstados getFesId() {
        return fesId;
    }

    public void setFesId(MarFasesEstados fesId) {
        this.fesId = fesId;
    }

    public MarRadicaciones getRadId() {
        return radId;
    }

    public void setRadId(MarRadicaciones radId) {
        this.radId = radId;
    }

    public MarUsuarios getUsuId() {
        return usuId;
    }

    public void setUsuId(MarUsuarios usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rfeId != null ? rfeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicacionesFasesEstados)) {
            return false;
        }
        MarRadicacionesFasesEstados other = (MarRadicacionesFasesEstados) object;
        if ((this.rfeId == null && other.rfeId != null) || (this.rfeId != null && !this.rfeId.equals(other.rfeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicacionesFasesEstados[ rfeId=" + rfeId + " ]";
    }
    
}
