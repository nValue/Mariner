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
 * @author Fabian Agudelo
 */
@Entity
@Table(name = "MAR_VERIFICACION_BUSQ_LOG")
@NamedQueries({
    @NamedQuery(name = "MarVerificacionBusqLog.findAll", query = "SELECT m FROM MarVerificacionBusqLog m"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByVblId", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.vblId = :vblId"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByVblBusqUnica", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.vblBusqUnica = :vblBusqUnica"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByVblDetalle", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.vblDetalle = :vblDetalle"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByVblBoletaFiscal", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.vblBoletaFiscal = :vblBoletaFiscal"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByVblReciboPago", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.vblReciboPago = :vblReciboPago"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByVblEscritura", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.vblEscritura = :vblEscritura"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByAudUsuario", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarVerificacionBusqLog.findByAudFecha", query = "SELECT m FROM MarVerificacionBusqLog m WHERE m.audFecha = :audFecha")})
public class MarVerificacionBusqLog implements Serializable {

    @Column(name = "VBL_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vblFecha;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_verif_busq_log")
    @SequenceGenerator(name = "sq_mar_verif_busq_log", sequenceName = "sq_mar_verif_busq_log")
    @Basic(optional = false)
    @NotNull
    
    @Column(name = "VBL_ID")
    private BigDecimal vblId;
    @Size(max = 1)
    @Column(name = "VBL_BUSQ_UNICA")
    private String vblBusqUnica;
    @Size(max = 1)
    @Column(name = "VBL_DETALLE")
    private String vblDetalle;
    @Size(max = 1)
    @Column(name = "VBL_BOLETA_FISCAL")
    private String vblBoletaFiscal;
    @Size(max = 1)
    @Column(name = "VBL_RECIBO_PAGO")
    private String vblReciboPago;
    @Size(max = 1)
    @Column(name = "VBL_ESCRITURA")
    private String vblEscritura;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO")
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @JoinColumn(name = "RAD_ID", referencedColumnName = "RAD_ID")
    @ManyToOne(optional = false)
    private MarRadicaciones radId;
    @JoinColumn(name = "USU_ID", referencedColumnName = "USU_ID")
    @ManyToOne(optional = false)
    private MarUsuarios usuId;

    public MarVerificacionBusqLog() {
    }

    public MarVerificacionBusqLog(BigDecimal vblId) {
        this.vblId = vblId;
    }

    public BigDecimal getVblId() {
        return vblId;
    }

    public void setVblId(BigDecimal vblId) {
        this.vblId = vblId;
    }

    public String getVblBusqUnica() {
        return vblBusqUnica;
    }

    public void setVblBusqUnica(String vblBusqUnica) {
        this.vblBusqUnica = vblBusqUnica;
    }

    public String getVblDetalle() {
        return vblDetalle;
    }

    public void setVblDetalle(String vblDetalle) {
        this.vblDetalle = vblDetalle;
    }

    public String getVblBoletaFiscal() {
        return vblBoletaFiscal;
    }

    public void setVblBoletaFiscal(String vblBoletaFiscal) {
        this.vblBoletaFiscal = vblBoletaFiscal;
    }

    public String getVblReciboPago() {
        return vblReciboPago;
    }

    public void setVblReciboPago(String vblReciboPago) {
        this.vblReciboPago = vblReciboPago;
    }

    public String getVblEscritura() {
        return vblEscritura;
    }

    public void setVblEscritura(String vblEscritura) {
        this.vblEscritura = vblEscritura;
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

    public MarRadicaciones getRadId() {
        return radId;
    }

    public void setRadId(MarRadicaciones radId) {
        this.radId = radId;
    }

    public MarUsuarios getUsuId() {
        return usuId;
    }

    public void setUsuId(MarUsuarios usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vblId != null ? vblId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarVerificacionBusqLog)) {
            return false;
        }
        MarVerificacionBusqLog other = (MarVerificacionBusqLog) object;
        if ((this.vblId == null && other.vblId != null) || (this.vblId != null && !this.vblId.equals(other.vblId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarVerificacionBusqLog[ vblId=" + vblId + " ]";
    }

    public Date getVblFecha() {
        return vblFecha;
    }

    public void setVblFecha(Date vblFecha) {
        this.vblFecha = vblFecha;
    }
    
}
