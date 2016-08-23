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
@Table(name = "MAR_ROLES_USUARIOS")
@NamedQueries({
    @NamedQuery(name = "MarRolesUsuarios.findAll", query = "SELECT m FROM MarRolesUsuarios m"),
    @NamedQuery(name = "MarRolesUsuarios.findByRusId", query = "SELECT m FROM MarRolesUsuarios m WHERE m.rusId = :rusId"),
    @NamedQuery(name = "MarRolesUsuarios.findByAudUsuario", query = "SELECT m FROM MarRolesUsuarios m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRolesUsuarios.findByAudFecha", query = "SELECT m FROM MarRolesUsuarios m WHERE m.audFecha = :audFecha")})
public class MarRolesUsuarios implements Serializable {

    @Column(name = "RUS_FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rusFechaInicio;
    @Column(name = "RUS_FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rusFechaFin;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_roles_usuarios")
    @SequenceGenerator(name = "sq_mar_roles_usuarios", sequenceName = "sq_mar_roles_usuarios")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RUS_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal rusId;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarRoles rolId;
    @JoinColumn(name = "USU_ID", referencedColumnName = "USU_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarUsuarios usuId;

    public MarRolesUsuarios() {
    }

    public MarRolesUsuarios(BigDecimal rusId) {
        this.rusId = rusId;
    }

    public BigDecimal getRusId() {
        return rusId;
    }

    public void setRusId(BigDecimal rusId) {
        this.rusId = rusId;
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

    public MarRoles getRolId() {
        return rolId;
    }

    public void setRolId(MarRoles rolId) {
        this.rolId = rolId;
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
        hash += (rusId != null ? rusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRolesUsuarios)) {
            return false;
        }
        MarRolesUsuarios other = (MarRolesUsuarios) object;
        if ((this.rusId == null && other.rusId != null) || (this.rusId != null && !this.rusId.equals(other.rusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRolesUsuarios[ rusId=" + rusId + " ]";
    }

    public Date getRusFechaInicio() {
        return rusFechaInicio;
    }

    public void setRusFechaInicio(Date rusFechaInicio) {
        this.rusFechaInicio = rusFechaInicio;
    }

    public Date getRusFechaFin() {
        return rusFechaFin;
    }

    public void setRusFechaFin(Date rusFechaFin) {
        this.rusFechaFin = rusFechaFin;
    }
    
}
