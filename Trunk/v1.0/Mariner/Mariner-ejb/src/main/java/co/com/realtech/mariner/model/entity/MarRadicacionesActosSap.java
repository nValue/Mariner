/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "MAR_RADICACIONES_ACTOS_SAP")
@NamedQueries({
    @NamedQuery(name = "MarRadicacionesActosSap.findAll", query = "SELECT m FROM MarRadicacionesActosSap m"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsId", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsId = :rdsId"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsCodigoActo", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsCodigoActo = :rdsCodigoActo"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsNomnbreAsr", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsNomnbreAsr = :rdsNomnbreAsr"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsTipoPer", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsTipoPer = :rdsTipoPer"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseIpp", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseIpp = :rdsBaseIpp"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseCio", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseCio = :rdsBaseCio"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseEst", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseEst = :rdsBaseEst"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsTarifaIpp", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsTarifaIpp = :rdsTarifaIpp"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsTarifaCio", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsTarifaCio = :rdsTarifaCio"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseEst1", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseEst1 = :rdsBaseEst1"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsImpIpp", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsImpIpp = :rdsImpIpp"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsImpCio", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsImpCio = :rdsImpCio"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseEst2", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseEst2 = :rdsBaseEst2"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsValorSinCuantia", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsValorSinCuantia = :rdsValorSinCuantia"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsValServInfo", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsValServInfo = :rdsValServInfo"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsValorIntereses", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsValorIntereses = :rdsValorIntereses"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsFechaDocumento", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsFechaDocumento = :rdsFechaDocumento"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsFechaVencimiento", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsFechaVencimiento = :rdsFechaVencimiento"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsFechaPago", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsFechaPago = :rdsFechaPago"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseEst3", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseEst3 = :rdsBaseEst3"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsVrEstadoProd", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsVrEstadoProd = :rdsVrEstadoProd"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseEst4", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseEst4 = :rdsBaseEst4"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByRdsBaseEst5", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.rdsBaseEst5 = :rdsBaseEst5"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByAudFecha", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarRadicacionesActosSap.findByAudUsuario", query = "SELECT m FROM MarRadicacionesActosSap m WHERE m.audUsuario = :audUsuario")})
public class MarRadicacionesActosSap implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_radicaciones_actos_sap")
    @SequenceGenerator(name = "sq_mar_radicaciones_actos_sap", sequenceName = "sq_mar_radicaciones_actos_sap")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RDS_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal rdsId;
    @Size(max = 100)
    @Column(name = "RDS_CODIGO_ACTO", length = 100)
    private String rdsCodigoActo;
    @Size(max = 200)
    @Column(name = "RDS_NOMNBRE_ASR", length = 200)
    private String rdsNomnbreAsr;
    @Size(max = 100)
    @Column(name = "RDS_TIPO_PER", length = 100)
    private String rdsTipoPer;
    @Column(name = "RDS_BASE_IPP", precision = 16, scale = 4)
    private BigDecimal rdsBaseIpp;
    @Column(name = "RDS_BASE_CIO", precision = 16, scale = 4)
    private BigDecimal rdsBaseCio;
    @Column(name = "RDS_BASE_EST", precision = 16, scale = 4)
    private BigDecimal rdsBaseEst;
    @Size(max = 20)
    @Column(name = "RDS_TARIFA_IPP", length = 20)
    private String rdsTarifaIpp;
    @Size(max = 20)
    @Column(name = "RDS_TARIFA_CIO", length = 20)
    private String rdsTarifaCio;
    @Column(name = "RDS_BASE_EST1", precision = 16, scale = 4)
    private BigDecimal rdsBaseEst1;
    @Column(name = "RDS_IMP_IPP", precision = 16, scale = 4)
    private BigDecimal rdsImpIpp;
    @Column(name = "RDS_IMP_CIO", precision = 16, scale = 4)
    private BigDecimal rdsImpCio;
    @Column(name = "RDS_BASE_EST2", precision = 16, scale = 4)
    private BigDecimal rdsBaseEst2;
    @Column(name = "RDS_VALOR_SIN_CUANTIA", precision = 16, scale = 4)
    private BigDecimal rdsValorSinCuantia;
    @Column(name = "RDS_VAL_SERV_INFO", precision = 16, scale = 4)
    private BigDecimal rdsValServInfo;
    @Column(name = "RDS_VALOR_INTERESES", precision = 16, scale = 4)
    private BigDecimal rdsValorIntereses;
    @Size(max = 20)
    @Column(name = "RDS_FECHA_DOCUMENTO", length = 20)
    private String rdsFechaDocumento;
    @Size(max = 20)
    @Column(name = "RDS_FECHA_VENCIMIENTO", length = 20)
    private String rdsFechaVencimiento;
    @Size(max = 20)
    @Column(name = "RDS_FECHA_PAGO", length = 20)
    private String rdsFechaPago;
    @Column(name = "RDS_BASE_EST3", precision = 16, scale = 4)
    private BigDecimal rdsBaseEst3;
    @Column(name = "RDS_VR_ESTADO_PROD", precision = 16, scale = 4)
    private BigDecimal rdsVrEstadoProd;
    @Column(name = "RDS_BASE_EST4", precision = 16, scale = 4)
    private BigDecimal rdsBaseEst4;
    @Column(name = "RDS_BASE_EST5", precision = 16, scale = 4)
    private BigDecimal rdsBaseEst5;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 60)
    @Column(name = "AUD_USUARIO", length = 60)
    private String audUsuario;
    @JoinColumn(name = "RDE_ID", referencedColumnName = "RDE_ID")
    @ManyToOne
    private MarRadicacionesDetallesSap rdeId;

    public MarRadicacionesActosSap() {
    }

    public MarRadicacionesActosSap(BigDecimal rdsId) {
        this.rdsId = rdsId;
    }

    public BigDecimal getRdsId() {
        return rdsId;
    }

    public void setRdsId(BigDecimal rdsId) {
        this.rdsId = rdsId;
    }

    public String getRdsCodigoActo() {
        return rdsCodigoActo;
    }

    public void setRdsCodigoActo(String rdsCodigoActo) {
        this.rdsCodigoActo = rdsCodigoActo;
    }

    public String getRdsNomnbreAsr() {
        return rdsNomnbreAsr;
    }

    public void setRdsNomnbreAsr(String rdsNomnbreAsr) {
        this.rdsNomnbreAsr = rdsNomnbreAsr;
    }

    public String getRdsTipoPer() {
        return rdsTipoPer;
    }

    public void setRdsTipoPer(String rdsTipoPer) {
        this.rdsTipoPer = rdsTipoPer;
    }

    public BigDecimal getRdsBaseIpp() {
        return rdsBaseIpp;
    }

    public void setRdsBaseIpp(BigDecimal rdsBaseIpp) {
        this.rdsBaseIpp = rdsBaseIpp;
    }

    public BigDecimal getRdsBaseCio() {
        return rdsBaseCio;
    }

    public void setRdsBaseCio(BigDecimal rdsBaseCio) {
        this.rdsBaseCio = rdsBaseCio;
    }

    public BigDecimal getRdsBaseEst() {
        return rdsBaseEst;
    }

    public void setRdsBaseEst(BigDecimal rdsBaseEst) {
        this.rdsBaseEst = rdsBaseEst;
    }

    public String getRdsTarifaIpp() {
        return rdsTarifaIpp;
    }

    public void setRdsTarifaIpp(String rdsTarifaIpp) {
        this.rdsTarifaIpp = rdsTarifaIpp;
    }

    public String getRdsTarifaCio() {
        return rdsTarifaCio;
    }

    public void setRdsTarifaCio(String rdsTarifaCio) {
        this.rdsTarifaCio = rdsTarifaCio;
    }

    public BigDecimal getRdsBaseEst1() {
        return rdsBaseEst1;
    }

    public void setRdsBaseEst1(BigDecimal rdsBaseEst1) {
        this.rdsBaseEst1 = rdsBaseEst1;
    }

    public BigDecimal getRdsImpIpp() {
        return rdsImpIpp;
    }

    public void setRdsImpIpp(BigDecimal rdsImpIpp) {
        this.rdsImpIpp = rdsImpIpp;
    }

    public BigDecimal getRdsImpCio() {
        return rdsImpCio;
    }

    public void setRdsImpCio(BigDecimal rdsImpCio) {
        this.rdsImpCio = rdsImpCio;
    }

    public BigDecimal getRdsBaseEst2() {
        return rdsBaseEst2;
    }

    public void setRdsBaseEst2(BigDecimal rdsBaseEst2) {
        this.rdsBaseEst2 = rdsBaseEst2;
    }

    public BigDecimal getRdsValorSinCuantia() {
        return rdsValorSinCuantia;
    }

    public void setRdsValorSinCuantia(BigDecimal rdsValorSinCuantia) {
        this.rdsValorSinCuantia = rdsValorSinCuantia;
    }

    public BigDecimal getRdsValServInfo() {
        return rdsValServInfo;
    }

    public void setRdsValServInfo(BigDecimal rdsValServInfo) {
        this.rdsValServInfo = rdsValServInfo;
    }

    public BigDecimal getRdsValorIntereses() {
        return rdsValorIntereses;
    }

    public void setRdsValorIntereses(BigDecimal rdsValorIntereses) {
        this.rdsValorIntereses = rdsValorIntereses;
    }

    public String getRdsFechaDocumento() {
        return rdsFechaDocumento;
    }

    public void setRdsFechaDocumento(String rdsFechaDocumento) {
        this.rdsFechaDocumento = rdsFechaDocumento;
    }

    public String getRdsFechaVencimiento() {
        return rdsFechaVencimiento;
    }

    public void setRdsFechaVencimiento(String rdsFechaVencimiento) {
        this.rdsFechaVencimiento = rdsFechaVencimiento;
    }

    public String getRdsFechaPago() {
        return rdsFechaPago;
    }

    public void setRdsFechaPago(String rdsFechaPago) {
        this.rdsFechaPago = rdsFechaPago;
    }

    public BigDecimal getRdsBaseEst3() {
        return rdsBaseEst3;
    }

    public void setRdsBaseEst3(BigDecimal rdsBaseEst3) {
        this.rdsBaseEst3 = rdsBaseEst3;
    }

    public BigDecimal getRdsVrEstadoProd() {
        return rdsVrEstadoProd;
    }

    public void setRdsVrEstadoProd(BigDecimal rdsVrEstadoProd) {
        this.rdsVrEstadoProd = rdsVrEstadoProd;
    }

    public BigDecimal getRdsBaseEst4() {
        return rdsBaseEst4;
    }

    public void setRdsBaseEst4(BigDecimal rdsBaseEst4) {
        this.rdsBaseEst4 = rdsBaseEst4;
    }

    public BigDecimal getRdsBaseEst5() {
        return rdsBaseEst5;
    }

    public void setRdsBaseEst5(BigDecimal rdsBaseEst5) {
        this.rdsBaseEst5 = rdsBaseEst5;
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

    public MarRadicacionesDetallesSap getRdeId() {
        return rdeId;
    }

    public void setRdeId(MarRadicacionesDetallesSap rdeId) {
        this.rdeId = rdeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rdsId != null ? rdsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicacionesActosSap)) {
            return false;
        }
        MarRadicacionesActosSap other = (MarRadicacionesActosSap) object;
        if ((this.rdsId == null && other.rdsId != null) || (this.rdsId != null && !this.rdsId.equals(other.rdsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicacionesActosSap[ rdsId=" + rdsId + " ]";
    }
    
}
