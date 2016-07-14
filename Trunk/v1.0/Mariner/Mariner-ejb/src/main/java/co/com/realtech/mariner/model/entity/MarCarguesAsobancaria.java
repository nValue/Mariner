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
@Table(name = "MAR_CARGUES_ASOBANCARIA")
@NamedQueries({
    @NamedQuery(name = "MarCarguesAsobancaria.findAll", query = "SELECT m FROM MarCarguesAsobancaria m"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByCasId", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.casId = :casId"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByCasReferencia", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.casReferencia = :casReferencia"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByCasValor", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.casValor = :casValor"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByCasLineaCompleta", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.casLineaCompleta = :casLineaCompleta"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByCasFecha", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.casFecha = :casFecha"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByAudUsuario", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByAudFecha", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarCarguesAsobancaria.findByCasBloque", query = "SELECT m FROM MarCarguesAsobancaria m WHERE m.casBloque = :casBloque")})
public class MarCarguesAsobancaria implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_cargues_asobancaria")
    @SequenceGenerator(name = "sq_mar_cargues_asobancaria", sequenceName = "sq_mar_cargues_asobancaria")
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAS_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal casId;
    @Size(max = 40)
    @Column(name = "CAS_REFERENCIA", length = 40)
    private String casReferencia;
    @Column(name = "CAS_VALOR")
    private BigInteger casValor;
    @Size(max = 4000)
    @Column(name = "CAS_LINEA_COMPLETA", length = 4000)
    private String casLineaCompleta;
    @Column(name = "CAS_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date casFecha;
    @Size(max = 60)
    @Column(name = "AUD_USUARIO", length = 60)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Column(name = "CAS_BLOQUE")
    private Integer casBloque;
    @Size(max = 2)
    @Column(name = "CAS_ESTADO", length = 2)
    private String casEstado;
    @Size(max = 4000)
    @Column(name = "CAS_LOG", length = 4000)
    private String casLog;
    @JoinColumn(name = "USU_ID", referencedColumnName = "USU_ID")
    @ManyToOne
    private MarUsuarios usuId;

    public MarCarguesAsobancaria() {
    }

    public MarCarguesAsobancaria(BigDecimal casId) {
        this.casId = casId;
    }

    public BigDecimal getCasId() {
        return casId;
    }

    public void setCasId(BigDecimal casId) {
        this.casId = casId;
    }

    public String getCasReferencia() {
        return casReferencia;
    }

    public void setCasReferencia(String casReferencia) {
        this.casReferencia = casReferencia;
    }

    public BigInteger getCasValor() {
        return casValor;
    }

    public void setCasValor(BigInteger casValor) {
        this.casValor = casValor;
    }

    public String getCasLineaCompleta() {
        return casLineaCompleta;
    }

    public void setCasLineaCompleta(String casLineaCompleta) {
        this.casLineaCompleta = casLineaCompleta;
    }

    public Date getCasFecha() {
        return casFecha;
    }

    public void setCasFecha(Date casFecha) {
        this.casFecha = casFecha;
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

    public Integer getCasBloque() {
        return casBloque;
    }

    public void setCasBloque(Integer casBloque) {
        this.casBloque = casBloque;
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
        hash += (casId != null ? casId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarCarguesAsobancaria)) {
            return false;
        }
        MarCarguesAsobancaria other = (MarCarguesAsobancaria) object;
        if ((this.casId == null && other.casId != null) || (this.casId != null && !this.casId.equals(other.casId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarCarguesAsobancaria[ casId=" + casId + " ]";
    }

    public String getCasEstado() {
        return casEstado;
    }

    public void setCasEstado(String casEstado) {
        this.casEstado = casEstado;
    }

    public String getCasLog() {
        return casLog;
    }

    public void setCasLog(String casLog) {
        this.casLog = casLog;
    }
    
}
