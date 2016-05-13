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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "mar_usuarios")
@NamedQueries({
    @NamedQuery(name = "MarUsuarios.findAll", query = "SELECT m FROM MarUsuarios m"),
    @NamedQuery(name = "MarUsuarios.findByUsuId", query = "SELECT m FROM MarUsuarios m WHERE m.usuId = :usuId"),
    @NamedQuery(name = "MarUsuarios.findByUsuLogin", query = "SELECT m FROM MarUsuarios m WHERE m.usuLogin = :usuLogin"),
    @NamedQuery(name = "MarUsuarios.findByUsuPassword", query = "SELECT m FROM MarUsuarios m WHERE m.usuPassword = :usuPassword"),
    @NamedQuery(name = "MarUsuarios.findByUsuEstado", query = "SELECT m FROM MarUsuarios m WHERE m.usuEstado = :usuEstado"),
    @NamedQuery(name = "MarUsuarios.findByAudUsuario", query = "SELECT m FROM MarUsuarios m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarUsuarios.findByAudFecha", query = "SELECT m FROM MarUsuarios m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarUsuarios.findByUsuUltimoIngreso", query = "SELECT m FROM MarUsuarios m WHERE m.usuUltimoIngreso = :usuUltimoIngreso")})
public class MarUsuarios implements Serializable {

    @OneToMany(mappedBy = "usuId")
    private List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usu_id", nullable = false, precision = 131089, scale = 0)
    private BigDecimal usuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_login", nullable = false, length = 50)
    private String usuLogin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "usu_password", nullable = false, length = 300)
    private String usuPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "usu_estado", nullable = false, length = 1)
    private String usuEstado;
    @Size(max = 50)
    @Column(name = "aud_usuario", length = 50)
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Column(name = "usu_ultimo_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuUltimoIngreso;
    @JoinColumn(name = "per_id", referencedColumnName = "per_id", nullable = false)
    @ManyToOne(optional = false)
    private MarPersonas perId;
    @OneToMany(mappedBy = "usuId")
    private List<MarRolesUsuarios> marRolesUsuariosList;
    @OneToMany(mappedBy = "usuId")
    private List<MarRadicacionesFases> marRadicacionesFasesList;

    public MarUsuarios() {
    }

    public MarUsuarios(BigDecimal usuId) {
        this.usuId = usuId;
    }

    public MarUsuarios(BigDecimal usuId, String usuLogin, String usuPassword, String usuEstado) {
        this.usuId = usuId;
        this.usuLogin = usuLogin;
        this.usuPassword = usuPassword;
        this.usuEstado = usuEstado;
    }

    public BigDecimal getUsuId() {
        return usuId;
    }

    public void setUsuId(BigDecimal usuId) {
        this.usuId = usuId;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuPassword() {
        return usuPassword;
    }

    public void setUsuPassword(String usuPassword) {
        this.usuPassword = usuPassword;
    }

    public String getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
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

    public Date getUsuUltimoIngreso() {
        return usuUltimoIngreso;
    }

    public void setUsuUltimoIngreso(Date usuUltimoIngreso) {
        this.usuUltimoIngreso = usuUltimoIngreso;
    }

    public MarPersonas getPerId() {
        return perId;
    }

    public void setPerId(MarPersonas perId) {
        this.perId = perId;
    }

    public List<MarRolesUsuarios> getMarRolesUsuariosList() {
        return marRolesUsuariosList;
    }

    public void setMarRolesUsuariosList(List<MarRolesUsuarios> marRolesUsuariosList) {
        this.marRolesUsuariosList = marRolesUsuariosList;
    }

    public List<MarRadicacionesFases> getMarRadicacionesFasesList() {
        return marRadicacionesFasesList;
    }

    public void setMarRadicacionesFasesList(List<MarRadicacionesFases> marRadicacionesFasesList) {
        this.marRadicacionesFasesList = marRadicacionesFasesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarUsuarios)) {
            return false;
        }
        MarUsuarios other = (MarUsuarios) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarUsuarios[ usuId=" + usuId + " ]";
    }

    public List<MarRadicacionesFasesEstados> getMarRadicacionesFasesEstadosList() {
        return marRadicacionesFasesEstadosList;
    }

    public void setMarRadicacionesFasesEstadosList(List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList) {
        this.marRadicacionesFasesEstadosList = marRadicacionesFasesEstadosList;
    }
    
}
