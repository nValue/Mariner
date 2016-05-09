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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fabianagudelo
 */
@Entity
@Table(name = "mar_modulos")
@NamedQueries({
    @NamedQuery(name = "MarModulos.findAll", query = "SELECT m FROM MarModulos m"),
    @NamedQuery(name = "MarModulos.findByModId", query = "SELECT m FROM MarModulos m WHERE m.modId = :modId"),
    @NamedQuery(name = "MarModulos.findByModNombre", query = "SELECT m FROM MarModulos m WHERE m.modNombre = :modNombre"),
    @NamedQuery(name = "MarModulos.findByModUrl", query = "SELECT m FROM MarModulos m WHERE m.modUrl = :modUrl"),
    @NamedQuery(name = "MarModulos.findByModIcono", query = "SELECT m FROM MarModulos m WHERE m.modIcono = :modIcono"),
    @NamedQuery(name = "MarModulos.findByModDescripcion", query = "SELECT m FROM MarModulos m WHERE m.modDescripcion = :modDescripcion"),
    @NamedQuery(name = "MarModulos.findByModEstado", query = "SELECT m FROM MarModulos m WHERE m.modEstado = :modEstado"),
    @NamedQuery(name = "MarModulos.findByModOrden", query = "SELECT m FROM MarModulos m WHERE m.modOrden = :modOrden"),
    @NamedQuery(name = "MarModulos.findByAudUsuario", query = "SELECT m FROM MarModulos m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarModulos.findByAudFecha", query = "SELECT m FROM MarModulos m WHERE m.audFecha = :audFecha")})
public class MarModulos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mod_id")
    private BigDecimal modId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mod_nombre")
    private String modNombre;
    @Size(max = 500)
    @Column(name = "mod_url")
    private String modUrl;
    @Size(max = 50)
    @Column(name = "mod_icono")
    private String modIcono;
    @Size(max = 500)
    @Column(name = "mod_descripcion")
    private String modDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "mod_estado")
    private String modEstado;
    @Column(name = "mod_orden")
    private Integer modOrden;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "modIdPadre")
    private List<MarModulos> marModulosList;
    @JoinColumn(name = "mod_id_padre", referencedColumnName = "mod_id")
    @ManyToOne
    private MarModulos modIdPadre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modId")
    private List<MarRolesModulos> marRolesModulosList;
    
    @Transient
    private List<MarModulos> modulosMenu;

    public MarModulos() {
    }

    public MarModulos(BigDecimal modId) {
        this.modId = modId;
    }

    public MarModulos(BigDecimal modId, String modNombre, String modEstado) {
        this.modId = modId;
        this.modNombre = modNombre;
        this.modEstado = modEstado;
    }

    public BigDecimal getModId() {
        return modId;
    }

    public void setModId(BigDecimal modId) {
        this.modId = modId;
    }

    public String getModNombre() {
        return modNombre;
    }

    public void setModNombre(String modNombre) {
        this.modNombre = modNombre;
    }

    public String getModUrl() {
        return modUrl;
    }

    public void setModUrl(String modUrl) {
        this.modUrl = modUrl;
    }

    public String getModIcono() {
        return modIcono;
    }

    public void setModIcono(String modIcono) {
        this.modIcono = modIcono;
    }

    public String getModDescripcion() {
        return modDescripcion;
    }

    public void setModDescripcion(String modDescripcion) {
        this.modDescripcion = modDescripcion;
    }

    public String getModEstado() {
        return modEstado;
    }

    public void setModEstado(String modEstado) {
        this.modEstado = modEstado;
    }

    public Integer getModOrden() {
        return modOrden;
    }

    public void setModOrden(Integer modOrden) {
        this.modOrden = modOrden;
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

    public List<MarModulos> getMarModulosList() {
        return marModulosList;
    }

    public void setMarModulosList(List<MarModulos> marModulosList) {
        this.marModulosList = marModulosList;
    }

    public MarModulos getModIdPadre() {
        return modIdPadre;
    }

    public void setModIdPadre(MarModulos modIdPadre) {
        this.modIdPadre = modIdPadre;
    }

    public List<MarRolesModulos> getMarRolesModulosList() {
        return marRolesModulosList;
    }

    public void setMarRolesModulosList(List<MarRolesModulos> marRolesModulosList) {
        this.marRolesModulosList = marRolesModulosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modId != null ? modId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarModulos)) {
            return false;
        }
        MarModulos other = (MarModulos) object;
        if ((this.modId == null && other.modId != null) || (this.modId != null && !this.modId.equals(other.modId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarModulos[ modId=" + modId + " ]";
    }

    public List<MarModulos> getModulosMenu() {
        return modulosMenu;
    }

    public void setModulosMenu(List<MarModulos> modulosMenu) {
        this.modulosMenu = modulosMenu;
    }
    
    
    
}
