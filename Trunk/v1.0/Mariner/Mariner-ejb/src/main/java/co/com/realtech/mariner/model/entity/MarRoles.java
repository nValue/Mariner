/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "MAR_ROLES")
@NamedQueries({
    @NamedQuery(name = "MarRoles.findAll", query = "SELECT m FROM MarRoles m"),
    @NamedQuery(name = "MarRoles.findByRolId", query = "SELECT m FROM MarRoles m WHERE m.rolId = :rolId"),
    @NamedQuery(name = "MarRoles.findByRolNombre", query = "SELECT m FROM MarRoles m WHERE m.rolNombre = :rolNombre"),
    @NamedQuery(name = "MarRoles.findByRolDescripcion", query = "SELECT m FROM MarRoles m WHERE m.rolDescripcion = :rolDescripcion"),
    @NamedQuery(name = "MarRoles.findByAudUsuario", query = "SELECT m FROM MarRoles m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRoles.findByAudFecha", query = "SELECT m FROM MarRoles m WHERE m.audFecha = :audFecha")})
public class MarRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_roles")
    @SequenceGenerator(name = "sq_mar_roles", sequenceName = "sq_mar_roles")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROL_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal rolId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ROL_NOMBRE", nullable = false, length = 100)
    private String rolNombre;
    @Size(max = 200)
    @Column(name = "ROL_DESCRIPCION", length = 200)
    private String rolDescripcion;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolId")
    private List<MarRolesModulos> marRolesModulosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolId")
    private List<MarRolesUsuarios> marRolesUsuariosList;

    public MarRoles() {
    }

    public MarRoles(BigDecimal rolId) {
        this.rolId = rolId;
    }

    public MarRoles(BigDecimal rolId, String rolNombre) {
        this.rolId = rolId;
        this.rolNombre = rolNombre;
    }

    public BigDecimal getRolId() {
        return rolId;
    }

    public void setRolId(BigDecimal rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public String getRolDescripcion() {
        return rolDescripcion;
    }

    public void setRolDescripcion(String rolDescripcion) {
        this.rolDescripcion = rolDescripcion;
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

    public List<MarRolesModulos> getMarRolesModulosList() {
        return marRolesModulosList;
    }

    public void setMarRolesModulosList(List<MarRolesModulos> marRolesModulosList) {
        this.marRolesModulosList = marRolesModulosList;
    }

    public List<MarRolesUsuarios> getMarRolesUsuariosList() {
        return marRolesUsuariosList;
    }

    public void setMarRolesUsuariosList(List<MarRolesUsuarios> marRolesUsuariosList) {
        this.marRolesUsuariosList = marRolesUsuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolId != null ? rolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRoles)) {
            return false;
        }
        MarRoles other = (MarRoles) object;
        if ((this.rolId == null && other.rolId != null) || (this.rolId != null && !this.rolId.equals(other.rolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRoles[ rolId=" + rolId + " ]";
    }
    
}
