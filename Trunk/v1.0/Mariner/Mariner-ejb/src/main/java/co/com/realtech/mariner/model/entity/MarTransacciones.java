/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "MAR_TRANSACCIONES", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"RAD_ID"})})
@NamedQueries({
    @NamedQuery(name = "MarTransacciones.findAll", query = "SELECT m FROM MarTransacciones m"),
    @NamedQuery(name = "MarTransacciones.findByTraId", query = "SELECT m FROM MarTransacciones m WHERE m.traId = :traId"),
    @NamedQuery(name = "MarTransacciones.findByTraCuantia", query = "SELECT m FROM MarTransacciones m WHERE m.traCuantia = :traCuantia"),
    @NamedQuery(name = "MarTransacciones.findByTraValor", query = "SELECT m FROM MarTransacciones m WHERE m.traValor = :traValor"),
    @NamedQuery(name = "MarTransacciones.findByTraFechaInicio", query = "SELECT m FROM MarTransacciones m WHERE m.traFechaInicio = :traFechaInicio"),
    @NamedQuery(name = "MarTransacciones.findByTraFechaFinalizacion", query = "SELECT m FROM MarTransacciones m WHERE m.traFechaFinalizacion = :traFechaFinalizacion"),
    @NamedQuery(name = "MarTransacciones.findByTraCus", query = "SELECT m FROM MarTransacciones m WHERE m.traCus = :traCus"),
    @NamedQuery(name = "MarTransacciones.findByTraTipoPago", query = "SELECT m FROM MarTransacciones m WHERE m.traTipoPago = :traTipoPago"),
    @NamedQuery(name = "MarTransacciones.findByTraEstado", query = "SELECT m FROM MarTransacciones m WHERE m.traEstado = :traEstado"),
    @NamedQuery(name = "MarTransacciones.findByTraMedioPagoNota", query = "SELECT m FROM MarTransacciones m WHERE m.traMedioPagoNota = :traMedioPagoNota"),
    @NamedQuery(name = "MarTransacciones.findByAudFecha", query = "SELECT m FROM MarTransacciones m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarTransacciones.findByAudUsuario", query = "SELECT m FROM MarTransacciones m WHERE m.audUsuario = :audUsuario")})
public class MarTransacciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_transacciones")
    @SequenceGenerator(name = "sq_mar_transacciones", sequenceName = "sq_mar_transacciones")
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRA_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal traId;
    @Column(name = "TRA_CUANTIA")
    private BigInteger traCuantia;
    @Column(name = "TRA_VALOR")
    private BigInteger traValor;
    @Column(name = "TRA_FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date traFechaInicio;
    @Column(name = "TRA_FECHA_FINALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date traFechaFinalizacion;
    @Size(max = 30)
    @Column(name = "TRA_CUS", length = 30)
    private String traCus;
    @Size(max = 20)
    @Column(name = "TRA_TIPO_PAGO", length = 20)
    private String traTipoPago;
    @Size(max = 2)
    @Column(name = "TRA_ESTADO", length = 2)
    private String traEstado;
    @Size(max = 2000)
    @Column(name = "TRA_MEDIO_PAGO_NOTA", length = 2000)
    private String traMedioPagoNota;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 60)
    @Column(name = "AUD_USUARIO", length = 60)
    private String audUsuario;
    @JoinColumn(name = "RAD_ID", referencedColumnName = "RAD_ID")
    @OneToOne
    private MarRadicaciones radId;

    public MarTransacciones() {
    }

    public MarTransacciones(BigDecimal traId) {
        this.traId = traId;
    }

    public BigDecimal getTraId() {
        return traId;
    }

    public void setTraId(BigDecimal traId) {
        this.traId = traId;
    }

    public BigInteger getTraCuantia() {
        return traCuantia;
    }

    public void setTraCuantia(BigInteger traCuantia) {
        this.traCuantia = traCuantia;
    }

    public BigInteger getTraValor() {
        return traValor;
    }

    public void setTraValor(BigInteger traValor) {
        this.traValor = traValor;
    }

    public Date getTraFechaInicio() {
        return traFechaInicio;
    }

    public void setTraFechaInicio(Date traFechaInicio) {
        this.traFechaInicio = traFechaInicio;
    }

    public Date getTraFechaFinalizacion() {
        return traFechaFinalizacion;
    }

    public void setTraFechaFinalizacion(Date traFechaFinalizacion) {
        this.traFechaFinalizacion = traFechaFinalizacion;
    }

    public String getTraCus() {
        return traCus;
    }

    public void setTraCus(String traCus) {
        this.traCus = traCus;
    }

    public String getTraTipoPago() {
        return traTipoPago;
    }

    public void setTraTipoPago(String traTipoPago) {
        this.traTipoPago = traTipoPago;
    }

    public String getTraEstado() {
        return traEstado;
    }

    public void setTraEstado(String traEstado) {
        this.traEstado = traEstado;
    }

    public String getTraMedioPagoNota() {
        return traMedioPagoNota;
    }

    public void setTraMedioPagoNota(String traMedioPagoNota) {
        this.traMedioPagoNota = traMedioPagoNota;
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

    public MarRadicaciones getRadId() {
        return radId;
    }

    public void setRadId(MarRadicaciones radId) {
        this.radId = radId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (traId != null ? traId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarTransacciones)) {
            return false;
        }
        MarTransacciones other = (MarTransacciones) object;
        if ((this.traId == null && other.traId != null) || (this.traId != null && !this.traId.equals(other.traId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarTransacciones[ traId=" + traId + " ]";
    }
    
}
