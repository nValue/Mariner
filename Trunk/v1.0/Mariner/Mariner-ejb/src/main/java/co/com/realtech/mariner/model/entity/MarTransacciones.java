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
    @NamedQuery(name = "MarTransacciones.findByMrtId", query = "SELECT m FROM MarTransacciones m WHERE m.mrtId = :mrtId"),
    @NamedQuery(name = "MarTransacciones.findByMrtCuantia", query = "SELECT m FROM MarTransacciones m WHERE m.mrtCuantia = :mrtCuantia"),
    @NamedQuery(name = "MarTransacciones.findByMrtValor", query = "SELECT m FROM MarTransacciones m WHERE m.mrtValor = :mrtValor"),
    @NamedQuery(name = "MarTransacciones.findByMrtFechaInicio", query = "SELECT m FROM MarTransacciones m WHERE m.mrtFechaInicio = :mrtFechaInicio"),
    @NamedQuery(name = "MarTransacciones.findByMrtFechaFinalizacion", query = "SELECT m FROM MarTransacciones m WHERE m.mrtFechaFinalizacion = :mrtFechaFinalizacion"),
    @NamedQuery(name = "MarTransacciones.findByMrtCus", query = "SELECT m FROM MarTransacciones m WHERE m.mrtCus = :mrtCus"),
    @NamedQuery(name = "MarTransacciones.findByMrtTipoPago", query = "SELECT m FROM MarTransacciones m WHERE m.mrtTipoPago = :mrtTipoPago"),
    @NamedQuery(name = "MarTransacciones.findByMrtEstado", query = "SELECT m FROM MarTransacciones m WHERE m.mrtEstado = :mrtEstado"),
    @NamedQuery(name = "MarTransacciones.findByMrtNotaMedioPago", query = "SELECT m FROM MarTransacciones m WHERE m.mrtNotaMedioPago = :mrtNotaMedioPago"),
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
    @Column(name = "MRT_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal mrtId;
    @Column(name = "MRT_CUANTIA")
    private BigInteger mrtCuantia;
    @Column(name = "MRT_VALOR")
    private BigInteger mrtValor;
    @Column(name = "MRT_FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mrtFechaInicio;
    @Column(name = "MRT_FECHA_FINALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mrtFechaFinalizacion;
    @Size(max = 30)
    @Column(name = "MRT_CUS", length = 30)
    private String mrtCus;
    @Size(max = 20)
    @Column(name = "MRT_TIPO_PAGO", length = 20)
    private String mrtTipoPago;
    @Size(max = 2)
    @Column(name = "MRT_ESTADO", length = 2)
    private String mrtEstado;
    @Size(max = 2000)
    @Column(name = "MRT_NOTA_MEDIO_PAGO", length = 2000)
    private String mrtNotaMedioPago;
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

    public MarTransacciones(BigDecimal mrtId) {
        this.mrtId = mrtId;
    }

    public BigDecimal getMrtId() {
        return mrtId;
    }

    public void setMrtId(BigDecimal mrtId) {
        this.mrtId = mrtId;
    }

    public BigInteger getMrtCuantia() {
        return mrtCuantia;
    }

    public void setMrtCuantia(BigInteger mrtCuantia) {
        this.mrtCuantia = mrtCuantia;
    }

    public BigInteger getMrtValor() {
        return mrtValor;
    }

    public void setMrtValor(BigInteger mrtValor) {
        this.mrtValor = mrtValor;
    }

    public Date getMrtFechaInicio() {
        return mrtFechaInicio;
    }

    public void setMrtFechaInicio(Date mrtFechaInicio) {
        this.mrtFechaInicio = mrtFechaInicio;
    }

    public Date getMrtFechaFinalizacion() {
        return mrtFechaFinalizacion;
    }

    public void setMrtFechaFinalizacion(Date mrtFechaFinalizacion) {
        this.mrtFechaFinalizacion = mrtFechaFinalizacion;
    }

    public String getMrtCus() {
        return mrtCus;
    }

    public void setMrtCus(String mrtCus) {
        this.mrtCus = mrtCus;
    }

    public String getMrtTipoPago() {
        return mrtTipoPago;
    }

    public void setMrtTipoPago(String mrtTipoPago) {
        this.mrtTipoPago = mrtTipoPago;
    }

    public String getMrtEstado() {
        return mrtEstado;
    }

    public void setMrtEstado(String mrtEstado) {
        this.mrtEstado = mrtEstado;
    }

    public String getMrtNotaMedioPago() {
        return mrtNotaMedioPago;
    }

    public void setMrtNotaMedioPago(String mrtNotaMedioPago) {
        this.mrtNotaMedioPago = mrtNotaMedioPago;
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
        hash += (mrtId != null ? mrtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarTransacciones)) {
            return false;
        }
        MarTransacciones other = (MarTransacciones) object;
        if ((this.mrtId == null && other.mrtId != null) || (this.mrtId != null && !this.mrtId.equals(other.mrtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarTransacciones[ mrtId=" + mrtId + " ]";
    }
    
}
