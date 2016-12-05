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
import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andres Rivera
 */
@Entity
@Table(name = "MAR_USUARIOS", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"USU_LOGIN"})})
@NamedQueries({
    @NamedQuery(name = "MarUsuarios.findAll", query = "SELECT m FROM MarUsuarios m"),
    @NamedQuery(name = "MarUsuarios.findByUsuId", query = "SELECT m FROM MarUsuarios m WHERE m.usuId = :usuId"),
    @NamedQuery(name = "MarUsuarios.findByUsuLogin", query = "SELECT m FROM MarUsuarios m WHERE m.usuLogin = :usuLogin"),
    @NamedQuery(name = "MarUsuarios.findByUsuPassword", query = "SELECT m FROM MarUsuarios m WHERE m.usuPassword = :usuPassword"),
    @NamedQuery(name = "MarUsuarios.findByUsuEstado", query = "SELECT m FROM MarUsuarios m WHERE m.usuEstado = :usuEstado"),
    @NamedQuery(name = "MarUsuarios.findByAudUsuario", query = "SELECT m FROM MarUsuarios m WHERE m.audUsuario = :audUsuario"),
    @NamedQuery(name = "MarUsuarios.findByAudFecha", query = "SELECT m FROM MarUsuarios m WHERE m.audFecha = :audFecha"),
    @NamedQuery(name = "MarUsuarios.findByUsuUltimoIngreso", query = "SELECT m FROM MarUsuarios m WHERE m.usuUltimoIngreso = :usuUltimoIngreso"),
    @NamedQuery(name = "MarUsuarios.findByUsuTipo", query = "SELECT m FROM MarUsuarios m WHERE m.usuTipo = :usuTipo"),
    @NamedQuery(name = "MarUsuarios.findByUsuAliasSap", query = "SELECT m FROM MarUsuarios m WHERE m.usuAliasSap = :usuAliasSap"),
    @NamedQuery(name = "MarUsuarios.findByUsuEsLiquidador", query = "SELECT m FROM MarUsuarios m WHERE m.usuEsLiquidador = :usuEsLiquidador"),
    @NamedQuery(name = "MarUsuarios.findByUsuEsNotario", query = "SELECT m FROM MarUsuarios m WHERE m.usuEsNotario = :usuEsNotario"),
    @NamedQuery(name = "MarUsuarios.findByUsuEsAprobador", query = "SELECT m FROM MarUsuarios m WHERE m.usuEsAprobador = :usuEsAprobador"),
    @NamedQuery(name = "MarUsuarios.findByUsuEsCalificador", query = "SELECT m FROM MarUsuarios m WHERE m.usuEsCalificador = :usuEsCalificador")})
public class MarUsuarios implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuId")
    private List<MarVerificacionBusqLog> marVerificacionBusqLogList;

    @Size(max = 1)
    @Column(name = "USU_LOGUEADO")
    private String usuLogueado;

    @Column(name = "USU_FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaInicio;
    @Column(name = "USU_FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaFin;
    @Size(max = 1)
    @Column(name = "USU_CAMBIO_CLAVE")
    private String usuCambioClave;
    @Column(name = "USU_INTENTOS_FAIL")
    private Short usuIntentosFail;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(generator = "sq_mar_usuarios")
    @SequenceGenerator(name = "sq_mar_usuarios", sequenceName = "sq_mar_usuarios")
    @Basic(optional = false)
    @NotNull
    @Column(name = "USU_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal usuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USU_LOGIN", nullable = false, length = 50)
    private String usuLogin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "USU_PASSWORD", nullable = false, length = 300)
    private String usuPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "USU_ESTADO", nullable = false, length = 1)
    private String usuEstado;
    @Size(max = 50)
    @Column(name = "AUD_USUARIO", length = 50)
    private String audUsuario;
    @Column(name = "AUD_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFecha;
    @Column(name = "USU_ULTIMO_INGRESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuUltimoIngreso;
    @Size(max = 20)
    @Column(name = "USU_TIPO", length = 20)
    private String usuTipo;
    @Size(max = 60)
    @Column(name = "USU_ALIAS_SAP", length = 60)
    private String usuAliasSap;
    @Size(max = 2)
    @Column(name = "USU_ES_LIQUIDADOR", length = 2)
    private String usuEsLiquidador;
    @Size(max = 2)
    @Column(name = "USU_ES_NOTARIO", length = 2)
    private String usuEsNotario;
    @Size(max = 2)
    @Column(name = "USU_ES_APROBADOR", length = 2)
    private String usuEsAprobador;
    @Size(max = 2)
    @Column(name = "USU_ES_CALIFICADOR", length = 2)
    private String usuEsCalificador;
    @OneToMany(mappedBy = "usuId")
    private List<MarCarguesAsobancaria> marCarguesAsobancariaList;
    @OneToMany(mappedBy = "usuId")
    private List<MarNotificaciones> marNotificacionesList;
    @OneToMany(mappedBy = "usuId")
    private List<MarRolesUsuarios> marRolesUsuariosList;
    @OneToMany(mappedBy = "usuId")
    private List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList;
    @OneToMany(mappedBy = "usuId")
    private List<MarTransacciones> marTransaccionesList;
    @JoinColumn(name = "NOT_ID", referencedColumnName = "NOT_ID")
    @ManyToOne
    private MarNotarias notId;
    @JoinColumn(name = "MOR_ID", referencedColumnName = "MOR_ID")
    @ManyToOne
    private MarOficinasRegistros morId;
    @JoinColumn(name = "PER_ID", referencedColumnName = "PER_ID", nullable = false)
    @ManyToOne(optional = false)
    private MarPersonas perId;

    public MarUsuarios() {
    }

    public MarUsuarios(BigDecimal usuId) {
        this.usuId = usuId;
    }

    public MarUsuarios(BigDecimal usuId, String usuLogin, String usuPassword, String usuEstado) {
        this.usuId = usuId;
        this.usuLogin = usuLogin;
        this.usuPassword = usuPassword;
        this.usuEstado = usuEstado;
    }

    public BigDecimal getUsuId() {
        return usuId;
    }

    public void setUsuId(BigDecimal usuId) {
        this.usuId = usuId;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuPassword() {
        return usuPassword;
    }

    public void setUsuPassword(String usuPassword) {
        this.usuPassword = usuPassword;
    }

    public String getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
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

    public Date getUsuUltimoIngreso() {
        return usuUltimoIngreso;
    }

    public void setUsuUltimoIngreso(Date usuUltimoIngreso) {
        this.usuUltimoIngreso = usuUltimoIngreso;
    }

    public String getUsuTipo() {
        return usuTipo;
    }

    public void setUsuTipo(String usuTipo) {
        this.usuTipo = usuTipo;
    }

    public String getUsuAliasSap() {
        return usuAliasSap;
    }

    public void setUsuAliasSap(String usuAliasSap) {
        this.usuAliasSap = usuAliasSap;
    }

    public String getUsuEsLiquidador() {
        return usuEsLiquidador;
    }

    public void setUsuEsLiquidador(String usuEsLiquidador) {
        this.usuEsLiquidador = usuEsLiquidador;
    }

    public String getUsuEsNotario() {
        return usuEsNotario;
    }

    public void setUsuEsNotario(String usuEsNotario) {
        this.usuEsNotario = usuEsNotario;
    }

    public String getUsuEsAprobador() {
        return usuEsAprobador;
    }

    public void setUsuEsAprobador(String usuEsAprobador) {
        this.usuEsAprobador = usuEsAprobador;
    }

    public String getUsuEsCalificador() {
        return usuEsCalificador;
    }

    public void setUsuEsCalificador(String usuEsCalificador) {
        this.usuEsCalificador = usuEsCalificador;
    }

    public List<MarCarguesAsobancaria> getMarCarguesAsobancariaList() {
        return marCarguesAsobancariaList;
    }

    public void setMarCarguesAsobancariaList(List<MarCarguesAsobancaria> marCarguesAsobancariaList) {
        this.marCarguesAsobancariaList = marCarguesAsobancariaList;
    }

    public List<MarNotificaciones> getMarNotificacionesList() {
        return marNotificacionesList;
    }

    public void setMarNotificacionesList(List<MarNotificaciones> marNotificacionesList) {
        this.marNotificacionesList = marNotificacionesList;
    }

    public List<MarRolesUsuarios> getMarRolesUsuariosList() {
        return marRolesUsuariosList;
    }

    public void setMarRolesUsuariosList(List<MarRolesUsuarios> marRolesUsuariosList) {
        this.marRolesUsuariosList = marRolesUsuariosList;
    }

    public List<MarRadicacionesFasesEstados> getMarRadicacionesFasesEstadosList() {
        return marRadicacionesFasesEstadosList;
    }

    public void setMarRadicacionesFasesEstadosList(List<MarRadicacionesFasesEstados> marRadicacionesFasesEstadosList) {
        this.marRadicacionesFasesEstadosList = marRadicacionesFasesEstadosList;
    }

    public List<MarTransacciones> getMarTransaccionesList() {
        return marTransaccionesList;
    }

    public void setMarTransaccionesList(List<MarTransacciones> marTransaccionesList) {
        this.marTransaccionesList = marTransaccionesList;
    }

    public MarNotarias getNotId() {
        return notId;
    }

    public void setNotId(MarNotarias notId) {
        this.notId = notId;
    }

    public MarOficinasRegistros getMorId() {
        return morId;
    }

    public void setMorId(MarOficinasRegistros morId) {
        this.morId = morId;
    }

    public MarPersonas getPerId() {
        return perId;
    }

    public void setPerId(MarPersonas perId) {
        this.perId = perId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarUsuarios)) {
            return false;
        }
        MarUsuarios other = (MarUsuarios) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.realtech.mariner.model.entity.MarUsuarios[ usuId=" + usuId + " ]";
    }

    public Date getUsuFechaInicio() {
        return usuFechaInicio;
    }

    public void setUsuFechaInicio(Date usuFechaInicio) {
        this.usuFechaInicio = usuFechaInicio;
    }

    public Date getUsuFechaFin() {
        return usuFechaFin;
    }

    public void setUsuFechaFin(Date usuFechaFin) {
        this.usuFechaFin = usuFechaFin;
    }

    public String getUsuCambioClave() {
        return usuCambioClave;
    }

    public void setUsuCambioClave(String usuCambioClave) {
        this.usuCambioClave = usuCambioClave;
    }

    public Short getUsuIntentosFail() {
        return usuIntentosFail;
    }

    public void setUsuIntentosFail(Short usuIntentosFail) {
        this.usuIntentosFail = usuIntentosFail;
    }

    public String getUsuLogueado() {
        return usuLogueado;
    }

    public void setUsuLogueado(String usuLogueado) {
        this.usuLogueado = usuLogueado;
    }

    public List<MarVerificacionBusqLog> getMarVerificacionBusqLogList() {
        return marVerificacionBusqLogList;
    }

    public void setMarVerificacionBusqLogList(List<MarVerificacionBusqLog> marVerificacionBusqLogList) {
        this.marVerificacionBusqLogList = marVerificacionBusqLogList;
    }
    
}
