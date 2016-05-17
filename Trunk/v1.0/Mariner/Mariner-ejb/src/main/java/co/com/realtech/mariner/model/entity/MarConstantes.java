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
@Table(name = "MAR_CONSTANTES")
@NamedQueries({
    @NamedQuery(name = "MarConstantes.findAll", query = "SELECT m FROM MarConstantes m"),
    @NamedQuery(name = "MarConstantes.findByConId", query = "SELECT m FROM MarConstantes m WHERE m.conId = :conId"),
    @NamedQuery(name = "MarConstantes.findByConSigla", query = "SELECT m FROM MarConstantes m WHERE m.conSigla = :conSigla"),
    @NamedQuery(name = "MarConstantes.findByConNombre", query = "SELECT m FROM MarConstantes m WHERE m.conNombre = :conNombre"),
    @NamedQuery(name = "MarConstantes.findByConValorGenerico", query = "SELECT m FROM MarConstantes m WHERE m.conValorGenerico = :conValorGenerico"),
    @NamedQuery(name = "MarConstantes.findByConEstado", query = "SELECT m FROM MarConstantes m WHERE m.conEstado = :conEstado"),
    @NamedQuery(name = "MarConstantes.findByAudUsuario", query = "SELECT m FROM MarConstantes m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarConstantes.findByAudFecha", query = "SELECT m FROM MarConstantes m WHERE m.audFecha = :audFecha")})
public class MarConstantes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_constantes")
    @SequenceGenerator(name = "sq_mar_constantes", sequenceName = "sq_mar_constantes")
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal conId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CON_SIGLA", nullable = false, length = 50)
    private String conSigla;
    @Size(max = 500)
    @Column(name = "CON_NOMBRE", length = 500)
    private String conNombre;
    @Size(max = 500)
    @Column(name = "CON_VALOR_GENERICO", length = 500)
    private String conValorGenerico;
    @Size(max = 1)
    @Column(name = "CON_ESTADO", length = 1)
    private String conEstado;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;

    public MarConstantes() {
    }

    public MarConstantes(BigDecimal conId) {
        this.conId = conId;
    }

    public MarConstantes(BigDecimal conId, String conSigla) {
        this.conId = conId;
        this.conSigla = conSigla;
    }

    public BigDecimal getConId() {
        return conId;
    }

    public void setConId(BigDecimal conId) {
        this.conId = conId;
    }

    public String getConSigla() {
        return conSigla;
    }

    public void setConSigla(String conSigla) {
        this.conSigla = conSigla;
    }

    public String getConNombre() {
        return conNombre;
    }

    public void setConNombre(String conNombre) {
        this.conNombre = conNombre;
    }

    public String getConValorGenerico() {
        return conValorGenerico;
    }

    public void setConValorGenerico(String conValorGenerico) {
        this.conValorGenerico = conValorGenerico;
    }

    public String getConEstado() {
        return conEstado;
    }

    public void setConEstado(String conEstado) {
        this.conEstado = conEstado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conId != null ? conId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarConstantes)) {
            return false;
        }
        MarConstantes other = (MarConstantes) object;
        if ((this.conId == null && other.conId != null) || (this.conId != null && !this.conId.equals(other.conId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarConstantes[ conId=" + conId + " ]";
    }
    
}
