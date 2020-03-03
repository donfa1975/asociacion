/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabricio.diaz
 */
@Entity
@Table(name = "tblCitas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCitas.findAll", query = "SELECT t FROM TblCitas t")
    , @NamedQuery(name = "TblCitas.findByIdCita", query = "SELECT t FROM TblCitas t WHERE t.idCita = :idCita")
    , @NamedQuery(name = "TblCitas.findByFecha", query = "SELECT t FROM TblCitas t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "TblCitas.findByHora", query = "SELECT t FROM TblCitas t WHERE t.hora = :hora")
    , @NamedQuery(name = "TblCitas.findByFechaReg", query = "SELECT t FROM TblCitas t WHERE t.fechaReg = :fechaReg")
    , @NamedQuery(name = "TblCitas.findByIdusuario", query = "SELECT t FROM TblCitas t WHERE t.idusuario = :idusuario")
    , @NamedQuery(name = "TblCitas.findByEjecutada", query = "SELECT t FROM TblCitas t WHERE t.ejecutada = :ejecutada")
    , @NamedQuery(name = "TblCitas.findByNumeroCita", query = "SELECT t FROM TblCitas t WHERE t.numeroCita = :numeroCita")})
public class TblCitas implements Serializable {

    @Size(max = 50)
    @Column(name = "nombreDr")
    private String nombreDr;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCita")
    private Integer idCita;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 5)
    @Column(name = "hora")
    private String hora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaReg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReg;
    @Column(name = "idusuario")
    private Integer idusuario;
    @Column(name = "ejecutada")
    private Boolean ejecutada;
    @Size(max = 20)
    @Column(name = "numeroCita")
    private String numeroCita;
    @JoinColumn(name = "idAccion", referencedColumnName = "idAccion")
    @ManyToOne
    private TblAcciones idAccion;
    @JoinColumn(name = "idPaciente", referencedColumnName = "idPaciente")
    @ManyToOne
    private TblPacientes idPaciente;

    public TblCitas() {
    }

    public TblCitas(Integer idCita) {
        this.idCita = idCita;
    }

    public TblCitas(Integer idCita, Date fechaReg) {
        this.idCita = idCita;
        this.fechaReg = fechaReg;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Boolean getEjecutada() {
        return ejecutada;
    }

    public void setEjecutada(Boolean ejecutada) {
        this.ejecutada = ejecutada;
    }

    public String getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(String numeroCita) {
        this.numeroCita = numeroCita;
    }

    public TblAcciones getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(TblAcciones idAccion) {
        this.idAccion = idAccion;
    }

    public TblPacientes getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(TblPacientes idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCita != null ? idCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCitas)) {
            return false;
        }
        TblCitas other = (TblCitas) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblCitas[ idCita=" + idCita + " ]";
    }

    public String getNombreDr() {
        return nombreDr;
    }

    public void setNombreDr(String nombreDr) {
        this.nombreDr = nombreDr;
    }
    
}
