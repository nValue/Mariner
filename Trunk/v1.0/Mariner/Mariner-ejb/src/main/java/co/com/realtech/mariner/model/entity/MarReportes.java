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
@Table(name = "MAR_REPORTES")
@NamedQueries({
    @NamedQuery(name = "MarReportes.findAll", query = "SELECT m FROM MarReportes m"),
    @NamedQuery(name = "MarReportes.findByRepId", query = "SELECT m FROM MarReportes m WHERE m.repId = :repId"),
    @NamedQuery(name = "MarReportes.findByRepCodigo", query = "SELECT m FROM MarReportes m WHERE m.repCodigo = :repCodigo"),
    @NamedQuery(name = "MarReportes.findByRepNombre", query = "SELECT m FROM MarReportes m WHERE m.repNombre = :repNombre"),
    @NamedQuery(name = "MarReportes.findByRepEstado", query = "SELECT m FROM MarReportes m WHERE m.repEstado = :repEstado"),
    @NamedQuery(name = "MarReportes.findByRepExtension", query = "SELECT m FROM MarReportes m WHERE m.repExtension = :repExtension"),
    @NamedQuery(name = "MarReportes.findByRepJasperNombre", query = "SELECT m FROM MarReportes m WHERE m.repJasperNombre = :repJasperNombre"),
    @NamedQuery(name = "MarReportes.findByRepConsulta", query = "SELECT m FROM MarReportes m WHERE m.repConsulta = :repConsulta"),
    @NamedQuery(name = "MarReportes.findByAudFecha", query = "SELECT m FROM MarReportes m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarReportes.findByAudUsuario", query = "SELECT m FROM MarReportes m WHERE m.audUsuario = :audUsuario")})
public class MarReportes implements Serializable {

    @OneToMany(mappedBy = "repId")
    private List<MarRolesReportes> marRolesReportesList;

    @JoinColumn(name = "ARC_ID_REPORTE", referencedColumnName = "ARC_ID")
    @ManyToOne
    private MarArchivos arcIdReporte;

    @Size(max = 100)
    @Column(name = "REP_JASPER_NOMBRE")
    private String repJasperNombre;
    @Size(max = 4000)
    @Column(name = "REP_CONSULTA")
    private String repConsulta;
    @OneToMany(mappedBy = "repId")
    private List<MarReportesParametros> marReportesParametrosList;
    @OneToMany(mappedBy = "repId")
    private List<MarReportesGraficos> marReportesGraficosList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_reportes")
    @SequenceGenerator(name = "sq_mar_reportes", sequenceName = "sq_mar_reportes")
    @Basic(optional = false)
    @NotNull
    @Column(name = "REP_ID", nullable = false, precision = 38, scale = 0)
    private BigDecimal repId;
    @Size(max = 20)
    @Column(name = "REP_CODIGO", length = 20)
    private String repCodigo;
    @Size(max = 200)
    @Column(name = "REP_NOMBRE", length = 200)
    private String repNombre;
    @Size(max = 1)
    @Column(name = "REP_ESTADO", length = 1)
    private String repEstado;
    @Size(max = 5)
    @Column(name = "REP_EXTENSION", length = 5)
    private String repExtension;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @JoinColumn(name = "RTI_ID", referencedColumnName = "RTI_ID")
    @ManyToOne
    private MarReportesTipos rtiId;

    public MarReportes() {
    }

    public MarReportes(BigDecimal repId) {
        this.repId = repId;
    }

    public BigDecimal getRepId() {
        return repId;
    }

    public void setRepId(BigDecimal repId) {
        this.repId = repId;
    }

    public String getRepCodigo() {
        return repCodigo;
    }

    public void setRepCodigo(String repCodigo) {
        this.repCodigo = repCodigo;
    }

    public String getRepNombre() {
        return repNombre;
    }

    public void setRepNombre(String repNombre) {
        this.repNombre = repNombre;
    }

    public String getRepEstado() {
        return repEstado;
    }

    public void setRepEstado(String repEstado) {
        this.repEstado = repEstado;
    }

    public String getRepExtension() {
        return repExtension;
    }

    public void setRepExtension(String repExtension) {
        this.repExtension = repExtension;
    }

    public String getRepJasperNombre() {
        return repJasperNombre;
    }

    public void setRepJasperNombre(String repJasperNombre) {
        this.repJasperNombre = repJasperNombre;
    }

    public String getRepConsulta() {
        return repConsulta;
    }

    public void setRepConsulta(String repConsulta) {
        this.repConsulta = repConsulta;
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

    public List<MarReportesGraficos> getMarReportesGraficosList() {
        return marReportesGraficosList;
    }

    public void setMarReportesGraficosList(List<MarReportesGraficos> marReportesGraficosList) {
        this.marReportesGraficosList = marReportesGraficosList;
    }

    public List<MarReportesParametros> getMarReportesParametrosList() {
        return marReportesParametrosList;
    }

    public void setMarReportesParametrosList(List<MarReportesParametros> marReportesParametrosList) {
        this.marReportesParametrosList = marReportesParametrosList;
    }

    public MarArchivos getArcIdReporte() {
        return arcIdReporte;
    }

    public void setArcIdReporte(MarArchivos arcIdReporte) {
        this.arcIdReporte = arcIdReporte;
    }

    public MarReportesTipos getRtiId() {
        return rtiId;
    }

    public void setRtiId(MarReportesTipos rtiId) {
        this.rtiId = rtiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repId != null ? repId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarReportes)) {
            return false;
        }
        MarReportes other = (MarReportes) object;
        if ((this.repId == null && other.repId != null) || (this.repId != null && !this.repId.equals(other.repId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarReportes[ repId=" + repId + " ]";
    }

    public List<MarRolesReportes> getMarRolesReportesList() {
        return marRolesReportesList;
    }

    public void setMarRolesReportesList(List<MarRolesReportes> marRolesReportesList) {
        this.marRolesReportesList = marRolesReportesList;
    }
    
}
