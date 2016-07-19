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
@Table(name = "MAR_ROLES_REPORTES")
@NamedQueries({
    @NamedQuery(name = "MarRolesReportes.findAll", query = "SELECT m FROM MarRolesReportes m"),
    @NamedQuery(name = "MarRolesReportes.findByRreId", query = "SELECT m FROM MarRolesReportes m WHERE m.rreId = :rreId"),
    @NamedQuery(name = "MarRolesReportes.findByAudUsuario", query = "SELECT m FROM MarRolesReportes m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRolesReportes.findByAudFecha", query = "SELECT m FROM MarRolesReportes m WHERE m.audFecha = :audFecha")})
public class MarRolesReportes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_roles_reportes")
    @SequenceGenerator(name = "sq_mar_roles_reportes", sequenceName = "sq_mar_roles_reportes")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RRE_ID")
    private BigDecimal rreId;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO")
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "REP_ID", referencedColumnName = "REP_ID")
    @ManyToOne
    private MarReportes repId;
    @JoinColumn(name = "ROL_ID", referencedColumnName = "ROL_ID")
    @ManyToOne
    private MarRoles rolId;

    public MarRolesReportes() {
    }

    public MarRolesReportes(BigDecimal rreId) {
        this.rreId = rreId;
    }

    public BigDecimal getRreId() {
        return rreId;
    }

    public void setRreId(BigDecimal rreId) {
        this.rreId = rreId;
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

    public MarReportes getRepId() {
        return repId;
    }

    public void setRepId(MarReportes repId) {
        this.repId = repId;
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
        hash += (rreId != null ? rreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRolesReportes)) {
            return false;
        }
        MarRolesReportes other = (MarRolesReportes) object;
        if ((this.rreId == null && other.rreId != null) || (this.rreId != null && !this.rreId.equals(other.rreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRolesReportes[ rreId=" + rreId + " ]";
    }
    
}
