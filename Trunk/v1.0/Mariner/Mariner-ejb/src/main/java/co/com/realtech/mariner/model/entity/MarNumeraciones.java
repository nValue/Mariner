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
@Table(name = "MAR_NUMERACIONES")
@NamedQueries({
    @NamedQuery(name = "MarNumeraciones.findAll", query = "SELECT m FROM MarNumeraciones m"),
    @NamedQuery(name = "MarNumeraciones.findByNumId", query = "SELECT m FROM MarNumeraciones m WHERE m.numId = :numId"),
    @NamedQuery(name = "MarNumeraciones.findByNumCodigo", query = "SELECT m FROM MarNumeraciones m WHERE m.numCodigo = :numCodigo"),
    @NamedQuery(name = "MarNumeraciones.findByNumNombre", query = "SELECT m FROM MarNumeraciones m WHERE m.numNombre = :numNombre"),
    @NamedQuery(name = "MarNumeraciones.findByNumPrefijo", query = "SELECT m FROM MarNumeraciones m WHERE m.numPrefijo = :numPrefijo"),
    @NamedQuery(name = "MarNumeraciones.findByNumNumero", query = "SELECT m FROM MarNumeraciones m WHERE m.numNumero = :numNumero"),
    @NamedQuery(name = "MarNumeraciones.findByNumSufijo", query = "SELECT m FROM MarNumeraciones m WHERE m.numSufijo = :numSufijo"),
    @NamedQuery(name = "MarNumeraciones.findByAudUsuario", query = "SELECT m FROM MarNumeraciones m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarNumeraciones.findByAudFecha", query = "SELECT m FROM MarNumeraciones m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarNumeraciones.findByNumUsaNumCiudad", query = "SELECT m FROM MarNumeraciones m WHERE m.numUsaNumCiudad = :numUsaNumCiudad")})
public class MarNumeraciones implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_numeraciones")
    @SequenceGenerator(name = "sq_mar_numeraciones", sequenceName = "sq_mar_numeraciones")
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal numId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NUM_CODIGO", nullable = false, length = 50)
    private String numCodigo;
    @Size(max = 200)
    @Column(name = "NUM_NOMBRE", length = 200)
    private String numNombre;
    @Size(max = 10)
    @Column(name = "NUM_PREFIJO", length = 10)
    private String numPrefijo;
    @Column(name = "NUM_NUMERO")
    private BigInteger numNumero;
    @Size(max = 10)
    @Column(name = "NUM_SUFIJO", length = 10)
    private String numSufijo;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 2)
    @Column(name = "NUM_USA_NUM_CIUDAD", length = 2)
    private String numUsaNumCiudad;

    public MarNumeraciones() {
    }

    public MarNumeraciones(BigDecimal numId) {
        this.numId = numId;
    }

    public MarNumeraciones(BigDecimal numId, String numCodigo) {
        this.numId = numId;
        this.numCodigo = numCodigo;
    }

    public BigDecimal getNumId() {
        return numId;
    }

    public void setNumId(BigDecimal numId) {
        this.numId = numId;
    }

    public String getNumCodigo() {
        return numCodigo;
    }

    public void setNumCodigo(String numCodigo) {
        this.numCodigo = numCodigo;
    }

    public String getNumNombre() {
        return numNombre;
    }

    public void setNumNombre(String numNombre) {
        this.numNombre = numNombre;
    }

    public String getNumPrefijo() {
        return numPrefijo;
    }

    public void setNumPrefijo(String numPrefijo) {
        this.numPrefijo = numPrefijo;
    }

    public BigInteger getNumNumero() {
        return numNumero;
    }

    public void setNumNumero(BigInteger numNumero) {
        this.numNumero = numNumero;
    }

    public String getNumSufijo() {
        return numSufijo;
    }

    public void setNumSufijo(String numSufijo) {
        this.numSufijo = numSufijo;
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

    public String getNumUsaNumCiudad() {
        return numUsaNumCiudad;
    }

    public void setNumUsaNumCiudad(String numUsaNumCiudad) {
        this.numUsaNumCiudad = numUsaNumCiudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numId != null ? numId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarNumeraciones)) {
            return false;
        }
        MarNumeraciones other = (MarNumeraciones) object;
        if ((this.numId == null && other.numId != null) || (this.numId != null && !this.numId.equals(other.numId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarNumeraciones[ numId=" + numId + " ]";
    }
    
}
