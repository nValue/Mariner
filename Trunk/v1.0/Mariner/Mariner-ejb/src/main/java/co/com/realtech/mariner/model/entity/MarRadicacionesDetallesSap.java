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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "MAR_RADICACIONES_DETALLES_SAP", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"RAD_ID"})})
@NamedQueries({
    @NamedQuery(name = "MarRadicacionesDetallesSap.findAll", query = "SELECT m FROM MarRadicacionesDetallesSap m"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeId", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeId = :rdeId"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeLiqNumero", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeLiqNumero = :rdeLiqNumero"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeOtoNombre", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeOtoNombre = :rdeOtoNombre"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeBenNombre", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeBenNombre = :rdeBenNombre"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeOtoCc", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeOtoCc = :rdeOtoCc"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeBenCc", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeBenCc = :rdeBenCc"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeOtoNit", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeOtoNit = :rdeOtoNit"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeBenNit", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeBenNit = :rdeBenNit"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeOtoNum", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeOtoNum = :rdeOtoNum"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeBenNum", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeBenNum = :rdeBenNum"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeMunicipio", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeMunicipio = :rdeMunicipio"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeClavePer", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeClavePer = :rdeClavePer"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeMunNombre", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeMunNombre = :rdeMunNombre"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeFechaLiquidacion", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeFechaLiquidacion = :rdeFechaLiquidacion"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeUname", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeUname = :rdeUname"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeFechaLdp", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeFechaLdp = :rdeFechaLdp"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeTexto", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeTexto = :rdeTexto"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeClase", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeClase = :rdeClase"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeDocumento", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeDocumento = :rdeDocumento"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeTotal", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeTotal = :rdeTotal"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeDescladoc", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeDescladoc = :rdeDescladoc"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeDescoridoc", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeDescoridoc = :rdeDescoridoc"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeFechaDoc", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeFechaDoc = :rdeFechaDoc"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeMatricula", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeMatricula = :rdeMatricula"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeTintMora", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeTintMora = :rdeTintMora"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeCodTarifa", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeCodTarifa = :rdeCodTarifa"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeNotaria", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeNotaria = :rdeNotaria"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeDescInt", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeDescInt = :rdeDescInt"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeDescImp", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeDescImp = :rdeDescImp"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeTotalDesc", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeTotalDesc = :rdeTotalDesc"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeFechaLimite", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeFechaLimite = :rdeFechaLimite"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByRdeNorodrad", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.rdeNorodrad = :rdeNorodrad"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByAudFecha", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByAudUsuario", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarRadicacionesDetallesSap.findByColumn3", query = "SELECT m FROM MarRadicacionesDetallesSap m WHERE m.column3 = :column3")})
public class MarRadicacionesDetallesSap implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_radicaciones_detalles_s")
    @SequenceGenerator(name = "sq_mar_radicaciones_detalles_s", sequenceName = "sq_mar_radicaciones_detalles_s")
    @Basic(optional = false)
    @NotNull
    @Column(name = "RDE_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal rdeId;
    @Size(max = 100)
    @Column(name = "RDE_LIQ_NUMERO", length = 100)
    private String rdeLiqNumero;
    @Size(max = 200)
    @Column(name = "RDE_OTO_NOMBRE", length = 200)
    private String rdeOtoNombre;
    @Size(max = 200)
    @Column(name = "RDE_BEN_NOMBRE", length = 200)
    private String rdeBenNombre;
    @Size(max = 20)
    @Column(name = "RDE_OTO_CC", length = 20)
    private String rdeOtoCc;
    @Size(max = 20)
    @Column(name = "RDE_BEN_CC", length = 20)
    private String rdeBenCc;
    @Size(max = 20)
    @Column(name = "RDE_OTO_NIT", length = 20)
    private String rdeOtoNit;
    @Size(max = 20)
    @Column(name = "RDE_BEN_NIT", length = 20)
    private String rdeBenNit;
    @Size(max = 40)
    @Column(name = "RDE_OTO_NUM", length = 40)
    private String rdeOtoNum;
    @Size(max = 40)
    @Column(name = "RDE_BEN_NUM", length = 40)
    private String rdeBenNum;
    @Size(max = 50)
    @Column(name = "RDE_MUNICIPIO", length = 50)
    private String rdeMunicipio;
    @Size(max = 50)
    @Column(name = "RDE_CLAVE_PER", length = 50)
    private String rdeClavePer;
    @Size(max = 50)
    @Column(name = "RDE_MUN_NOMBRE", length = 50)
    private String rdeMunNombre;
    @Size(max = 20)
    @Column(name = "RDE_FECHA_LIQUIDACION", length = 20)
    private String rdeFechaLiquidacion;
    @Size(max = 50)
    @Column(name = "RDE_UNAME", length = 50)
    private String rdeUname;
    @Size(max = 20)
    @Column(name = "RDE_FECHA_LDP", length = 20)
    private String rdeFechaLdp;
    @Size(max = 2000)
    @Column(name = "RDE_TEXTO", length = 2000)
    private String rdeTexto;
    @Size(max = 20)
    @Column(name = "RDE_CLASE", length = 20)
    private String rdeClase;
    @Size(max = 40)
    @Column(name = "RDE_DOCUMENTO", length = 40)
    private String rdeDocumento;
    @Column(name = "RDE_TOTAL", precision = 16, scale = 4)
    private BigDecimal rdeTotal;
    @Size(max = 20)
    @Column(name = "RDE_DESCLADOC", length = 20)
    private String rdeDescladoc;
    @Size(max = 20)
    @Column(name = "RDE_DESCORIDOC", length = 20)
    private String rdeDescoridoc;
    @Size(max = 20)
    @Column(name = "RDE_FECHA_DOC", length = 20)
    private String rdeFechaDoc;
    @Size(max = 60)
    @Column(name = "RDE_MATRICULA", length = 60)
    private String rdeMatricula;
    @Column(name = "RDE_TINT_MORA", precision = 14, scale = 4)
    private BigDecimal rdeTintMora;
    @Size(max = 20)
    @Column(name = "RDE_COD_TARIFA", length = 20)
    private String rdeCodTarifa;
    @Size(max = 90)
    @Column(name = "RDE_NOTARIA", length = 90)
    private String rdeNotaria;
    @Column(name = "RDE_DESC_INT", precision = 16, scale = 4)
    private BigDecimal rdeDescInt;
    @Column(name = "RDE_DESC_IMP", precision = 16, scale = 4)
    private BigDecimal rdeDescImp;
    @Column(name = "RDE_TOTAL_DESC", precision = 16, scale = 4)
    private BigDecimal rdeTotalDesc;
    @Size(max = 20)
    @Column(name = "RDE_FECHA_LIMITE", length = 20)
    private String rdeFechaLimite;
    @Size(max = 50)
    @Column(name = "RDE_NORODRAD", length = 50)
    private String rdeNorodrad;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 60)
    @Column(name = "AUD_USUARIO", length = 60)
    private String audUsuario;
    @Size(max = 20)
    @Column(length = 20)
    private String column3;
    @JoinColumn(name = "RAD_ID", referencedColumnName = "RAD_ID")
    @OneToOne
    private MarRadicaciones radId;
    @OneToMany(mappedBy = "rdeId")
    private List<MarRadicacionesActosSap> marRadicacionesActosSapList;

    public MarRadicacionesDetallesSap() {
    }

    public MarRadicacionesDetallesSap(BigDecimal rdeId) {
        this.rdeId = rdeId;
    }

    public BigDecimal getRdeId() {
        return rdeId;
    }

    public void setRdeId(BigDecimal rdeId) {
        this.rdeId = rdeId;
    }

    public String getRdeLiqNumero() {
        return rdeLiqNumero;
    }

    public void setRdeLiqNumero(String rdeLiqNumero) {
        this.rdeLiqNumero = rdeLiqNumero;
    }

    public String getRdeOtoNombre() {
        return rdeOtoNombre;
    }

    public void setRdeOtoNombre(String rdeOtoNombre) {
        this.rdeOtoNombre = rdeOtoNombre;
    }

    public String getRdeBenNombre() {
        return rdeBenNombre;
    }

    public void setRdeBenNombre(String rdeBenNombre) {
        this.rdeBenNombre = rdeBenNombre;
    }

    public String getRdeOtoCc() {
        return rdeOtoCc;
    }

    public void setRdeOtoCc(String rdeOtoCc) {
        this.rdeOtoCc = rdeOtoCc;
    }

    public String getRdeBenCc() {
        return rdeBenCc;
    }

    public void setRdeBenCc(String rdeBenCc) {
        this.rdeBenCc = rdeBenCc;
    }

    public String getRdeOtoNit() {
        return rdeOtoNit;
    }

    public void setRdeOtoNit(String rdeOtoNit) {
        this.rdeOtoNit = rdeOtoNit;
    }

    public String getRdeBenNit() {
        return rdeBenNit;
    }

    public void setRdeBenNit(String rdeBenNit) {
        this.rdeBenNit = rdeBenNit;
    }

    public String getRdeOtoNum() {
        return rdeOtoNum;
    }

    public void setRdeOtoNum(String rdeOtoNum) {
        this.rdeOtoNum = rdeOtoNum;
    }

    public String getRdeBenNum() {
        return rdeBenNum;
    }

    public void setRdeBenNum(String rdeBenNum) {
        this.rdeBenNum = rdeBenNum;
    }

    public String getRdeMunicipio() {
        return rdeMunicipio;
    }

    public void setRdeMunicipio(String rdeMunicipio) {
        this.rdeMunicipio = rdeMunicipio;
    }

    public String getRdeClavePer() {
        return rdeClavePer;
    }

    public void setRdeClavePer(String rdeClavePer) {
        this.rdeClavePer = rdeClavePer;
    }

    public String getRdeMunNombre() {
        return rdeMunNombre;
    }

    public void setRdeMunNombre(String rdeMunNombre) {
        this.rdeMunNombre = rdeMunNombre;
    }

    public String getRdeFechaLiquidacion() {
        return rdeFechaLiquidacion;
    }

    public void setRdeFechaLiquidacion(String rdeFechaLiquidacion) {
        this.rdeFechaLiquidacion = rdeFechaLiquidacion;
    }

    public String getRdeUname() {
        return rdeUname;
    }

    public void setRdeUname(String rdeUname) {
        this.rdeUname = rdeUname;
    }

    public String getRdeFechaLdp() {
        return rdeFechaLdp;
    }

    public void setRdeFechaLdp(String rdeFechaLdp) {
        this.rdeFechaLdp = rdeFechaLdp;
    }

    public String getRdeTexto() {
        return rdeTexto;
    }

    public void setRdeTexto(String rdeTexto) {
        this.rdeTexto = rdeTexto;
    }

    public String getRdeClase() {
        return rdeClase;
    }

    public void setRdeClase(String rdeClase) {
        this.rdeClase = rdeClase;
    }

    public String getRdeDocumento() {
        return rdeDocumento;
    }

    public void setRdeDocumento(String rdeDocumento) {
        this.rdeDocumento = rdeDocumento;
    }

    public BigDecimal getRdeTotal() {
        return rdeTotal;
    }

    public void setRdeTotal(BigDecimal rdeTotal) {
        this.rdeTotal = rdeTotal;
    }

    public String getRdeDescladoc() {
        return rdeDescladoc;
    }

    public void setRdeDescladoc(String rdeDescladoc) {
        this.rdeDescladoc = rdeDescladoc;
    }

    public String getRdeDescoridoc() {
        return rdeDescoridoc;
    }

    public void setRdeDescoridoc(String rdeDescoridoc) {
        this.rdeDescoridoc = rdeDescoridoc;
    }

    public String getRdeFechaDoc() {
        return rdeFechaDoc;
    }

    public void setRdeFechaDoc(String rdeFechaDoc) {
        this.rdeFechaDoc = rdeFechaDoc;
    }

    public String getRdeMatricula() {
        return rdeMatricula;
    }

    public void setRdeMatricula(String rdeMatricula) {
        this.rdeMatricula = rdeMatricula;
    }

    public BigDecimal getRdeTintMora() {
        return rdeTintMora;
    }

    public void setRdeTintMora(BigDecimal rdeTintMora) {
        this.rdeTintMora = rdeTintMora;
    }

    public String getRdeCodTarifa() {
        return rdeCodTarifa;
    }

    public void setRdeCodTarifa(String rdeCodTarifa) {
        this.rdeCodTarifa = rdeCodTarifa;
    }

    public String getRdeNotaria() {
        return rdeNotaria;
    }

    public void setRdeNotaria(String rdeNotaria) {
        this.rdeNotaria = rdeNotaria;
    }

    public BigDecimal getRdeDescInt() {
        return rdeDescInt;
    }

    public void setRdeDescInt(BigDecimal rdeDescInt) {
        this.rdeDescInt = rdeDescInt;
    }

    public BigDecimal getRdeDescImp() {
        return rdeDescImp;
    }

    public void setRdeDescImp(BigDecimal rdeDescImp) {
        this.rdeDescImp = rdeDescImp;
    }

    public BigDecimal getRdeTotalDesc() {
        return rdeTotalDesc;
    }

    public void setRdeTotalDesc(BigDecimal rdeTotalDesc) {
        this.rdeTotalDesc = rdeTotalDesc;
    }

    public String getRdeFechaLimite() {
        return rdeFechaLimite;
    }

    public void setRdeFechaLimite(String rdeFechaLimite) {
        this.rdeFechaLimite = rdeFechaLimite;
    }

    public String getRdeNorodrad() {
        return rdeNorodrad;
    }

    public void setRdeNorodrad(String rdeNorodrad) {
        this.rdeNorodrad = rdeNorodrad;
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

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public MarRadicaciones getRadId() {
        return radId;
    }

    public void setRadId(MarRadicaciones radId) {
        this.radId = radId;
    }

    public List<MarRadicacionesActosSap> getMarRadicacionesActosSapList() {
        return marRadicacionesActosSapList;
    }

    public void setMarRadicacionesActosSapList(List<MarRadicacionesActosSap> marRadicacionesActosSapList) {
        this.marRadicacionesActosSapList = marRadicacionesActosSapList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rdeId != null ? rdeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarRadicacionesDetallesSap)) {
            return false;
        }
        MarRadicacionesDetallesSap other = (MarRadicacionesDetallesSap) object;
        if ((this.rdeId == null && other.rdeId != null) || (this.rdeId != null && !this.rdeId.equals(other.rdeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarRadicacionesDetallesSap[ rdeId=" + rdeId + " ]";
    }
    
}
