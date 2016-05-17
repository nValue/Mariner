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
@Table(name = "MAR_ROLES_MODULOS")
@NamedQueries({
    @NamedQuery(name = "MarRolesModulos.findAll", query = "SELECT m FROM MarRolesModulos m"),
    @NamedQuery(name = "MarRolesModulos.findByRmoId", query = "SELECT m FROM MarRolesModulos m WHERE m.rmoId = :rmoId"),
    @NamedQuery(name = "MarRolesModulos.findByAudUsuario", query = "SELECT m FROM MarRolesModulos m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRolesModulos.findByAudFecha", query = "SELECT m FROM MarRolesModulos m WHERE m.audFecha = :audFecha")})
public class MarRolesModulos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_roles_modulos")
    @SequenceGenerator(name = "sq_mar_roles_modulos", sequenceName = "sq_mar_roles_modulos")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RMO_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal rmoId;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "MOD_ID", referencedColumnName = "MOD_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarModulos modId;
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarRoles rolId;

    public MarRolesModulos() {
    }

    public MarRolesModulos(BigDecimal rmoId) {
        this.rmoId = rmoId;
    }

    public BigDecimal getRmoId() {
        return rmoId;
    }

    public void setRmoId(BigDecimal rmoId) {
        this.rmoId = rmoId;
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

    public MarModulos getModId() {
        return modId;
    }

    public void setModId(MarModulos modId) {
        this.modId = modId;
    }

    public MarRoles getRolId() {
        return rolId;
    }

    public void setRolId(MarRoles rolId) {
        this.rolId = rolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rmoId != null ? rmoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRolesModulos)) {
            return false;
        }
        MarRolesModulos other = (MarRolesModulos) object;
        if ((this.rmoId == null && other.rmoId != null) || (this.rmoId != null && !this.rmoId.equals(other.rmoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRolesModulos[ rmoId=" + rmoId + " ]";
    }
    
}
