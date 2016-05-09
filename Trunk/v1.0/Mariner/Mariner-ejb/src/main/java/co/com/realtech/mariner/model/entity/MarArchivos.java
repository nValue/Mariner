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
@Table(name = "mar_archivos")
@NamedQueries({
    @NamedQuery(name = "MarArchivos.findAll", query = "SELECT m FROM MarArchivos m"),
    @NamedQuery(name = "MarArchivos.findByArcId", query = "SELECT m FROM MarArchivos m WHERE m.arcId = :arcId"),
    @NamedQuery(name = "MarArchivos.findByArcNombre", query = "SELECT m FROM MarArchivos m WHERE m.arcNombre = :arcNombre"),
    @NamedQuery(name = "MarArchivos.findByArcExtension", query = "SELECT m FROM MarArchivos m WHERE m.arcExtension = :arcExtension"),
    @NamedQuery(name = "MarArchivos.findByArcMimeType", query = "SELECT m FROM MarArchivos m WHERE m.arcMimeType = :arcMimeType"),
    @NamedQuery(name = "MarArchivos.findByArcTamano", query = "SELECT m FROM MarArchivos m WHERE m.arcTamano = :arcTamano"),
    @NamedQuery(name = "MarArchivos.findByArcPathInterno", query = "SELECT m FROM MarArchivos m WHERE m.arcPathInterno = :arcPathInterno"),
    @NamedQuery(name = "MarArchivos.findByArcHash", query = "SELECT m FROM MarArchivos m WHERE m.arcHash = :arcHash"),
    @NamedQuery(name = "MarArchivos.findByArcEstado", query = "SELECT m FROM MarArchivos m WHERE m.arcEstado = :arcEstado"),
    @NamedQuery(name = "MarArchivos.findByAudUsuario", query = "SELECT m FROM MarArchivos m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarArchivos.findByAudFecha", query = "SELECT m FROM MarArchivos m WHERE m.audFecha = :audFecha")})
public class MarArchivos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "arc_id")
    private BigDecimal arcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "arc_nombre")
    private String arcNombre;
    @Size(max = 10)
    @Column(name = "arc_extension")
    private String arcExtension;
    @Size(max = 100)
    @Column(name = "arc_mime_type")
    private String arcMimeType;
    @Column(name = "arc_tamano")
    private BigInteger arcTamano;
    @Size(max = 500)
    @Column(name = "arc_path_interno")
    private String arcPathInterno;
    @Size(max = 500)
    @Column(name = "arc_hash")
    private String arcHash;
    @Size(max = 1)
    @Column(name = "arc_estado")
    private String arcEstado;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @OneToMany(mappedBy = "arcIdBoletaFiscal")
    private List<MarRadicaciones> marRadicacionesList;
    @OneToMany(mappedBy = "arcIdReciboPago")
    private List<MarRadicaciones> marRadicacionesList1;
    @OneToMany(mappedBy = "arcId")
    private List<MarReportes> marReportesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arcId")
    private List<MarEscrituras> marEscriturasList;
    @JoinColumn(name = "pmo_id", referencedColumnName = "pmo_id")
    @ManyToOne(optional = false)
    private MarPuntosMontajes pmoId;

    public MarArchivos() {
    }

    public MarArchivos(BigDecimal arcId) {
        this.arcId = arcId;
    }

    public MarArchivos(BigDecimal arcId, String arcNombre) {
        this.arcId = arcId;
        this.arcNombre = arcNombre;
    }

    public BigDecimal getArcId() {
        return arcId;
    }

    public void setArcId(BigDecimal arcId) {
        this.arcId = arcId;
    }

    public String getArcNombre() {
        return arcNombre;
    }

    public void setArcNombre(String arcNombre) {
        this.arcNombre = arcNombre;
    }

    public String getArcExtension() {
        return arcExtension;
    }

    public void setArcExtension(String arcExtension) {
        this.arcExtension = arcExtension;
    }

    public String getArcMimeType() {
        return arcMimeType;
    }

    public void setArcMimeType(String arcMimeType) {
        this.arcMimeType = arcMimeType;
    }

    public BigInteger getArcTamano() {
        return arcTamano;
    }

    public void setArcTamano(BigInteger arcTamano) {
        this.arcTamano = arcTamano;
    }

    public String getArcPathInterno() {
        return arcPathInterno;
    }

    public void setArcPathInterno(String arcPathInterno) {
        this.arcPathInterno = arcPathInterno;
    }

    public String getArcHash() {
        return arcHash;
    }

    public void setArcHash(String arcHash) {
        this.arcHash = arcHash;
    }

    public String getArcEstado() {
        return arcEstado;
    }

    public void setArcEstado(String arcEstado) {
        this.arcEstado = arcEstado;
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

    public List<MarRadicaciones> getMarRadicacionesList() {
        return marRadicacionesList;
    }

    public void setMarRadicacionesList(List<MarRadicaciones> marRadicacionesList) {
        this.marRadicacionesList = marRadicacionesList;
    }

    public List<MarRadicaciones> getMarRadicacionesList1() {
        return marRadicacionesList1;
    }

    public void setMarRadicacionesList1(List<MarRadicaciones> marRadicacionesList1) {
        this.marRadicacionesList1 = marRadicacionesList1;
    }

    public List<MarReportes> getMarReportesList() {
        return marReportesList;
    }

    public void setMarReportesList(List<MarReportes> marReportesList) {
        this.marReportesList = marReportesList;
    }

    public List<MarEscrituras> getMarEscriturasList() {
        return marEscriturasList;
    }

    public void setMarEscriturasList(List<MarEscrituras> marEscriturasList) {
        this.marEscriturasList = marEscriturasList;
    }

    public MarPuntosMontajes getPmoId() {
        return pmoId;
    }

    public void setPmoId(MarPuntosMontajes pmoId) {
        this.pmoId = pmoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (arcId != null ? arcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarArchivos)) {
            return false;
        }
        MarArchivos other = (MarArchivos) object;
        if ((this.arcId == null && other.arcId != null) || (this.arcId != null && !this.arcId.equals(other.arcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarArchivos[ arcId=" + arcId + " ]";
    }
    
}
