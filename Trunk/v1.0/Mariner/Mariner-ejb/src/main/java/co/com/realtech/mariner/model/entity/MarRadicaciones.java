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
import javax.persistence.OneToOne;
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
@Table(name = "mar_radicaciones")
@NamedQueries({
    @NamedQuery(name = "MarRadicaciones.findAll", query = "SELECT m FROM MarRadicaciones m"),
    @NamedQuery(name = "MarRadicaciones.findByRadId", query = "SELECT m FROM MarRadicaciones m WHERE m.radId = :radId"),
    @NamedQuery(name = "MarRadicaciones.findByRadFecha", query = "SELECT m FROM MarRadicaciones m WHERE m.radFecha = :radFecha"),
    @NamedQuery(name = "MarRadicaciones.findByRadNumero", query = "SELECT m FROM MarRadicaciones m WHERE m.radNumero = :radNumero"),
    @NamedQuery(name = "MarRadicaciones.findByAudUsuario", query = "SELECT m FROM MarRadicaciones m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRadicaciones.findByAudFecha", query = "SELECT m FROM MarRadicaciones m WHERE m.audFecha = :audFecha")})
public class MarRadicaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rad_id")
    private BigDecimal radId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rad_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date radFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rad_numero")
    private String radNumero;
    @Size(max = 50)
    @Column(name = "aud_usuario")
    private String audUsuario;
    @Column(name = "aud_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "arc_id_boleta_fiscal", referencedColumnName = "arc_id")
    @ManyToOne
    private MarArchivos arcIdBoletaFiscal;
    @JoinColumn(name = "arc_id_recibo_pago", referencedColumnName = "arc_id")
    @ManyToOne
    private MarArchivos arcIdReciboPago;
    @JoinColumn(name = "esc_id", referencedColumnName = "esc_id")
    @OneToOne(optional = false)
    private MarEscrituras escId;
    @JoinColumn(name = "not_id", referencedColumnName = "not_id")
    @ManyToOne(optional = false)
    private MarNotarias notId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "radId")
    private List<MarRadicacionesFases> marRadicacionesFasesList;

    public MarRadicaciones() {
    }

    public MarRadicaciones(BigDecimal radId) {
        this.radId = radId;
    }

    public MarRadicaciones(BigDecimal radId, Date radFecha, String radNumero) {
        this.radId = radId;
        this.radFecha = radFecha;
        this.radNumero = radNumero;
    }

    public BigDecimal getRadId() {
        return radId;
    }

    public void setRadId(BigDecimal radId) {
        this.radId = radId;
    }

    public Date getRadFecha() {
        return radFecha;
    }

    public void setRadFecha(Date radFecha) {
        this.radFecha = radFecha;
    }

    public String getRadNumero() {
        return radNumero;
    }

    public void setRadNumero(String radNumero) {
        this.radNumero = radNumero;
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

    public MarArchivos getArcIdBoletaFiscal() {
        return arcIdBoletaFiscal;
    }

    public void setArcIdBoletaFiscal(MarArchivos arcIdBoletaFiscal) {
        this.arcIdBoletaFiscal = arcIdBoletaFiscal;
    }

    public MarArchivos getArcIdReciboPago() {
        return arcIdReciboPago;
    }

    public void setArcIdReciboPago(MarArchivos arcIdReciboPago) {
        this.arcIdReciboPago = arcIdReciboPago;
    }

    public MarEscrituras getEscId() {
        return escId;
    }

    public void setEscId(MarEscrituras escId) {
        this.escId = escId;
    }

    public MarNotarias getNotId() {
        return notId;
    }

    public void setNotId(MarNotarias notId) {
        this.notId = notId;
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
        hash += (radId != null ? radId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicaciones)) {
            return false;
        }
        MarRadicaciones other = (MarRadicaciones) object;
        if ((this.radId == null && other.radId != null) || (this.radId != null && !this.radId.equals(other.radId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicaciones[ radId=" + radId + " ]";
    }
    
}
