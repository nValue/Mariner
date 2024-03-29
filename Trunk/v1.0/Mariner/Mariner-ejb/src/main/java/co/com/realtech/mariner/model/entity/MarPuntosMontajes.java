/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "MAR_PUNTOS_MONTAJES")
@NamedQueries({
    @NamedQuery(name = "MarPuntosMontajes.findAll", query = "SELECT m FROM MarPuntosMontajes m"),
    @NamedQuery(name = "MarPuntosMontajes.findByPmoId", query = "SELECT m FROM MarPuntosMontajes m WHERE m.pmoId = :pmoId"),
    @NamedQuery(name = "MarPuntosMontajes.findByPmoNombre", query = "SELECT m FROM MarPuntosMontajes m WHERE m.pmoNombre = :pmoNombre"),
    @NamedQuery(name = "MarPuntosMontajes.findByPmoOs", query = "SELECT m FROM MarPuntosMontajes m WHERE m.pmoOs = :pmoOs"),
    @NamedQuery(name = "MarPuntosMontajes.findByPmoPath", query = "SELECT m FROM MarPuntosMontajes m WHERE m.pmoPath = :pmoPath"),
    @NamedQuery(name = "MarPuntosMontajes.findByPmoTamanoMax", query = "SELECT m FROM MarPuntosMontajes m WHERE m.pmoTamanoMax = :pmoTamanoMax"),
    @NamedQuery(name = "MarPuntosMontajes.findByPmoEstado", query = "SELECT m FROM MarPuntosMontajes m WHERE m.pmoEstado = :pmoEstado"),
    @NamedQuery(name = "MarPuntosMontajes.findByAudUsuario", query = "SELECT m FROM MarPuntosMontajes m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarPuntosMontajes.findByAudFecha", query = "SELECT m FROM MarPuntosMontajes m WHERE m.audFecha = :audFecha")})
public class MarPuntosMontajes implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_puntos_montajes")
    @SequenceGenerator(name = "sq_mar_puntos_montajes", sequenceName = "sq_mar_puntos_montajes")
    @Basic(optional = false)
    @NotNull
    @Column(name = "PMO_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal pmoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PMO_NOMBRE", nullable = false, length = 50)
    private String pmoNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PMO_OS", nullable = false, length = 20)
    private String pmoOs;
    @Size(max = 100)
    @Column(name = "PMO_PATH", length = 100)
    private String pmoPath;
    @Column(name = "PMO_TAMANO_MAX")
    private BigInteger pmoTamanoMax;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "PMO_ESTADO", nullable = false, length = 1)
    private String pmoEstado;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "pmoId")
    private List<MarArchivos> marArchivosList;

    public MarPuntosMontajes() {
    }

    public MarPuntosMontajes(BigDecimal pmoId) {
        this.pmoId = pmoId;
    }

    public MarPuntosMontajes(BigDecimal pmoId, String pmoNombre, String pmoOs, String pmoEstado) {
        this.pmoId = pmoId;
        this.pmoNombre = pmoNombre;
        this.pmoOs = pmoOs;
        this.pmoEstado = pmoEstado;
    }

    public BigDecimal getPmoId() {
        return pmoId;
    }

    public void setPmoId(BigDecimal pmoId) {
        this.pmoId = pmoId;
    }

    public String getPmoNombre() {
        return pmoNombre;
    }

    public void setPmoNombre(String pmoNombre) {
        this.pmoNombre = pmoNombre;
    }

    public String getPmoOs() {
        return pmoOs;
    }

    public void setPmoOs(String pmoOs) {
        this.pmoOs = pmoOs;
    }

    public String getPmoPath() {
        return pmoPath;
    }

    public void setPmoPath(String pmoPath) {
        this.pmoPath = pmoPath;
    }

    public BigInteger getPmoTamanoMax() {
        return pmoTamanoMax;
    }

    public void setPmoTamanoMax(BigInteger pmoTamanoMax) {
        this.pmoTamanoMax = pmoTamanoMax;
    }

    public String getPmoEstado() {
        return pmoEstado;
    }

    public void setPmoEstado(String pmoEstado) {
        this.pmoEstado = pmoEstado;
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

    public List<MarArchivos> getMarArchivosList() {
        return marArchivosList;
    }

    public void setMarArchivosList(List<MarArchivos> marArchivosList) {
        this.marArchivosList = marArchivosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pmoId != null ? pmoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarPuntosMontajes)) {
            return false;
        }
        MarPuntosMontajes other = (MarPuntosMontajes) object;
        if ((this.pmoId == null && other.pmoId != null) || (this.pmoId != null && !this.pmoId.equals(other.pmoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarPuntosMontajes[ pmoId=" + pmoId + " ]";
    }
    
}
