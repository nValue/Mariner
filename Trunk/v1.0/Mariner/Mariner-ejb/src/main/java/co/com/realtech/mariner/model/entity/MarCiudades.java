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
import javax.validation.constraints.Size;

/**
 *
 * @author fabianagudelo
 */
@Entity
@Table(name = "mar_ciudades")
@NamedQueries({
    @NamedQuery(name = "MarCiudades.findAll", query = "SELECT m FROM MarCiudades m"),
    @NamedQuery(name = "MarCiudades.findByCiuId", query = "SELECT m FROM MarCiudades m WHERE m.ciuId = :ciuId"),
    @NamedQuery(name = "MarCiudades.findByCiuNombre", query = "SELECT m FROM MarCiudades m WHERE m.ciuNombre = :ciuNombre"),
    @NamedQuery(name = "MarCiudades.findByCiuCodigoDane", query = "SELECT m FROM MarCiudades m WHERE m.ciuCodigoDane = :ciuCodigoDane"),
    @NamedQuery(name = "MarCiudades.findByAudUsuario", query = "SELECT m FROM MarCiudades m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarCiudades.findByAudFecha", query = "SELECT m FROM MarCiudades m WHERE m.audFecha = :audFecha")})
public class MarCiudades implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ciu_id")
    private BigDecimal ciuId;
    @Size(max = 100)
    @Column(name = "ciu_nombre")
    private String ciuNombre;
    @Size(max = 20)
    @Column(name = "ciu_codigo_dane")
    private String ciuCodigoDane;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciuId")
    private List<MarNotarias> marNotariasList;
    @JoinColumn(name = "dep_id", referencedColumnName = "dep_id")
    @ManyToOne(optional = false)
    private MarDepartamentos depId;

    public MarCiudades() {
    }

    public MarCiudades(BigDecimal ciuId) {
        this.ciuId = ciuId;
    }

    public BigDecimal getCiuId() {
        return ciuId;
    }

    public void setCiuId(BigDecimal ciuId) {
        this.ciuId = ciuId;
    }

    public String getCiuNombre() {
        return ciuNombre;
    }

    public void setCiuNombre(String ciuNombre) {
        this.ciuNombre = ciuNombre;
    }

    public String getCiuCodigoDane() {
        return ciuCodigoDane;
    }

    public void setCiuCodigoDane(String ciuCodigoDane) {
        this.ciuCodigoDane = ciuCodigoDane;
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

    public List<MarNotarias> getMarNotariasList() {
        return marNotariasList;
    }

    public void setMarNotariasList(List<MarNotarias> marNotariasList) {
        this.marNotariasList = marNotariasList;
    }

    public MarDepartamentos getDepId() {
        return depId;
    }

    public void setDepId(MarDepartamentos depId) {
        this.depId = depId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciuId != null ? ciuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarCiudades)) {
            return false;
        }
        MarCiudades other = (MarCiudades) object;
        if ((this.ciuId == null && other.ciuId != null) || (this.ciuId != null && !this.ciuId.equals(other.ciuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarCiudades[ ciuId=" + ciuId + " ]";
    }
    
}
