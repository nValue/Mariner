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
import javax.persistence.CascadeType;
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
 * @author fabianagudelo
 */
@Entity
@Table(name = "mar_personas")
@NamedQueries({
    @NamedQuery(name = "MarPersonas.findAll", query = "SELECT m FROM MarPersonas m"),
    @NamedQuery(name = "MarPersonas.findByPerId", query = "SELECT m FROM MarPersonas m WHERE m.perId = :perId"),
    @NamedQuery(name = "MarPersonas.findByPerNombres", query = "SELECT m FROM MarPersonas m WHERE m.perNombres = :perNombres"),
    @NamedQuery(name = "MarPersonas.findByPerApellidos", query = "SELECT m FROM MarPersonas m WHERE m.perApellidos = :perApellidos"),
    @NamedQuery(name = "MarPersonas.findByPerDocumento", query = "SELECT m FROM MarPersonas m WHERE m.perDocumento = :perDocumento"),
    @NamedQuery(name = "MarPersonas.findByPerEmail", query = "SELECT m FROM MarPersonas m WHERE m.perEmail = :perEmail"),
    @NamedQuery(name = "MarPersonas.findByPerTelefono", query = "SELECT m FROM MarPersonas m WHERE m.perTelefono = :perTelefono"),
    @NamedQuery(name = "MarPersonas.findByAudUsuario", query = "SELECT m FROM MarPersonas m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarPersonas.findByAudFecha", query = "SELECT m FROM MarPersonas m WHERE m.audFecha = :audFecha")})
public class MarPersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "per_id")
    private BigDecimal perId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "per_nombres")
    private String perNombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "per_apellidos")
    private String perApellidos;
    @Size(max = 50)
    @Column(name = "per_documento")
    private String perDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "per_email")
    private String perEmail;
    @Size(max = 20)
    @Column(name = "per_telefono")
    private String perTelefono;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "tdc_id", referencedColumnName = "tdc_id")
    @ManyToOne(optional = false)
    private MarTiposDocumentos tdcId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perId")
    private List<MarUsuarios> marUsuariosList;

    public MarPersonas() {
    }

    public MarPersonas(BigDecimal perId) {
        this.perId = perId;
    }

    public MarPersonas(BigDecimal perId, String perNombres, String perApellidos, String perEmail) {
        this.perId = perId;
        this.perNombres = perNombres;
        this.perApellidos = perApellidos;
        this.perEmail = perEmail;
    }

    public BigDecimal getPerId() {
        return perId;
    }

    public void setPerId(BigDecimal perId) {
        this.perId = perId;
    }

    public String getPerNombres() {
        return perNombres;
    }

    public void setPerNombres(String perNombres) {
        this.perNombres = perNombres;
    }

    public String getPerApellidos() {
        return perApellidos;
    }

    public void setPerApellidos(String perApellidos) {
        this.perApellidos = perApellidos;
    }

    public String getPerDocumento() {
        return perDocumento;
    }

    public void setPerDocumento(String perDocumento) {
        this.perDocumento = perDocumento;
    }

    public String getPerEmail() {
        return perEmail;
    }

    public void setPerEmail(String perEmail) {
        this.perEmail = perEmail;
    }

    public String getPerTelefono() {
        return perTelefono;
    }

    public void setPerTelefono(String perTelefono) {
        this.perTelefono = perTelefono;
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

    public MarTiposDocumentos getTdcId() {
        return tdcId;
    }

    public void setTdcId(MarTiposDocumentos tdcId) {
        this.tdcId = tdcId;
    }

    public List<MarUsuarios> getMarUsuariosList() {
        return marUsuariosList;
    }

    public void setMarUsuariosList(List<MarUsuarios> marUsuariosList) {
        this.marUsuariosList = marUsuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perId != null ? perId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPersonas)) {
            return false;
        }
        MarPersonas other = (MarPersonas) object;
        if ((this.perId == null && other.perId != null) || (this.perId != null && !this.perId.equals(other.perId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarPersonas[ perId=" + perId + " ]";
    }
    
}
