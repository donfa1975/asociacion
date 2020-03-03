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
@Table(name = "tblMatriculas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMatriculas.findAll", query = "SELECT t FROM TblMatriculas t")
    , @NamedQuery(name = "TblMatriculas.findByIdMatricula", query = "SELECT t FROM TblMatriculas t WHERE t.idMatricula = :idMatricula")
    , @NamedQuery(name = "TblMatriculas.findByAnio", query = "SELECT t FROM TblMatriculas t WHERE t.anio = :anio")
    , @NamedQuery(name = "TblMatriculas.findByFechaMat", query = "SELECT t FROM TblMatriculas t WHERE t.fechaMat = :fechaMat")
    , @NamedQuery(name = "TblMatriculas.findByNumero", query = "SELECT t FROM TblMatriculas t WHERE t.numero = :numero")
    , @NamedQuery(name = "TblMatriculas.findByNumMat", query = "SELECT t FROM TblMatriculas t WHERE t.numMat = :numMat")})
public class TblMatriculas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idMatricula")
    private Integer idMatricula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private int anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaMat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMat;
    @Column(name = "numero")
    private Integer numero;
    @Size(max = 20)
    @Column(name = "numMat")
    private String numMat;
    @JoinColumn(name = "idPaciente", referencedColumnName = "idPaciente")
    @ManyToOne(optional = false)
    private TblPacientes idPaciente;
    @JoinColumn(name = "idusuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private TblUsuarios idusuario;

    public TblMatriculas() {
    }

    public TblMatriculas(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public TblMatriculas(Integer idMatricula, int anio, Date fechaMat) {
        this.idMatricula = idMatricula;
        this.anio = anio;
        this.fechaMat = fechaMat;
    }

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechaMat() {
        return fechaMat;
    }

    public void setFechaMat(Date fechaMat) {
        this.fechaMat = fechaMat;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNumMat() {
        return numMat;
    }

    public void setNumMat(String numMat) {
        this.numMat = numMat;
    }

    public TblPacientes getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(TblPacientes idPaciente) {
        this.idPaciente = idPaciente;
    }

    public TblUsuarios getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(TblUsuarios idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMatricula != null ? idMatricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblMatriculas)) {
            return false;
        }
        TblMatriculas other = (TblMatriculas) object;
        if ((this.idMatricula == null && other.idMatricula != null) || (this.idMatricula != null && !this.idMatricula.equals(other.idMatricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblMatriculas[ idMatricula=" + idMatricula + " ]";
    }
    
}
