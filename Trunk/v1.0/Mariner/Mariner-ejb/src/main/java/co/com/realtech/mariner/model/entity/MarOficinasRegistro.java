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
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "MAR_OFICINAS_REGISTRO")
@NamedQueries({
    @NamedQuery(name = "MarOficinasRegistro.findAll", query = "SELECT m FROM MarOficinasRegistro m"),
    @NamedQuery(name = "MarOficinasRegistro.findByMorId", query = "SELECT m FROM MarOficinasRegistro m WHERE m.morId = :morId"),
    @NamedQuery(name = "MarOficinasRegistro.findByMorCodigo", query = "SELECT m FROM MarOficinasRegistro m WHERE m.morCodigo = :morCodigo"),
    @NamedQuery(name = "MarOficinasRegistro.findByMorNombre", query = "SELECT m FROM MarOficinasRegistro m WHERE m.morNombre = :morNombre"),
    @NamedQuery(name = "MarOficinasRegistro.findByMorEstado", query = "SELECT m FROM MarOficinasRegistro m WHERE m.morEstado = :morEstado"),
    @NamedQuery(name = "MarOficinasRegistro.findByMorPlataforma", query = "SELECT m FROM MarOficinasRegistro m WHERE m.morPlataforma = :morPlataforma"),
    @NamedQuery(name = "MarOficinasRegistro.findByAudFecha", query = "SELECT m FROM MarOficinasRegistro m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarOficinasRegistro.findByAudUsuario", query = "SELECT m FROM MarOficinasRegistro m WHERE m.audUsuario = :audUsuario")})
public class MarOficinasRegistro implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_oficinas_registro")
    @SequenceGenerator(name = "sq_mar_oficinas_registro", sequenceName = "sq_mar_oficinas_registro")
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOR_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal morId;
    @Size(max = 20)
    @Column(name = "MOR_CODIGO", length = 20)
    private String morCodigo;
    @Size(max = 60)
    @Column(name = "MOR_NOMBRE", length = 60)
    private String morNombre;
    @Size(max = 2)
    @Column(name = "MOR_ESTADO", length = 2)
    private String morEstado;
    @Size(max = 20)
    @Column(name = "MOR_PLATAFORMA", length = 20)
    private String morPlataforma;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 60)
    @Column(name = "AUD_USUARIO", length = 60)
    private String audUsuario;
    @JoinColumn(name = "CIU_ID", referencedColumnName = "CIU_ID")
    @ManyToOne
    private MarCiudades ciuId;
    @OneToMany(mappedBy = "morId")
    private List<MarNotarias> marNotariasList;
    @OneToMany(mappedBy = "morId")
    private List<MarUsuarios> marUsuariosList;

    public MarOficinasRegistro() {
    }

    public MarOficinasRegistro(BigDecimal morId) {
        this.morId = morId;
    }

    public BigDecimal getMorId() {
        return morId;
    }

    public void setMorId(BigDecimal morId) {
        this.morId = morId;
    }

    public String getMorCodigo() {
        return morCodigo;
    }

    public void setMorCodigo(String morCodigo) {
        this.morCodigo = morCodigo;
    }

    public String getMorNombre() {
        return morNombre;
    }

    public void setMorNombre(String morNombre) {
        this.morNombre = morNombre;
    }

    public String getMorEstado() {
        return morEstado;
    }

    public void setMorEstado(String morEstado) {
        this.morEstado = morEstado;
    }

    public String getMorPlataforma() {
        return morPlataforma;
    }

    public void setMorPlataforma(String morPlataforma) {
        this.morPlataforma = morPlataforma;
    }

    public Date getAudFecha() {
        return audFecha;
    }

    public void setAudFecha(Date audFecha) {
        this.audFecha = audFecha;
    }

    public String getAudUsuario() {
        return audUsuario;
    }

    public void setAudUsuario(String audUsuario) {
        this.audUsuario = audUsuario;
    }

    public MarCiudades getCiuId() {
        return ciuId;
    }

    public void setCiuId(MarCiudades ciuId) {
        this.ciuId = ciuId;
    }

    public List<MarNotarias> getMarNotariasList() {
        return marNotariasList;
    }

    public void setMarNotariasList(List<MarNotarias> marNotariasList) {
        this.marNotariasList = marNotariasList;
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
        hash += (morId != null ? morId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarOficinasRegistro)) {
            return false;
        }
        MarOficinasRegistro other = (MarOficinasRegistro) object;
        if ((this.morId == null && other.morId != null) || (this.morId != null && !this.morId.equals(other.morId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarOficinasRegistro[ morId=" + morId + " ]";
    }
    
}
