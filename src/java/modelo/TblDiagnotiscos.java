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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabricio.diaz
 */
@Entity
@Table(name = "tblDiagnotiscos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblDiagnotiscos.findAll", query = "SELECT t FROM TblDiagnotiscos t")
    , @NamedQuery(name = "TblDiagnotiscos.findByIdDiagnostico", query = "SELECT t FROM TblDiagnotiscos t WHERE t.idDiagnostico = :idDiagnostico")
    , @NamedQuery(name = "TblDiagnotiscos.findByFechaDiagnostico", query = "SELECT t FROM TblDiagnotiscos t WHERE t.fechaDiagnostico = :fechaDiagnostico")
    , @NamedQuery(name = "TblDiagnotiscos.findByIdmedico", query = "SELECT t FROM TblDiagnotiscos t WHERE t.idmedico = :idmedico")})
public class TblDiagnotiscos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDiagnostico")
    private Integer idDiagnostico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaDiagnostico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDiagnostico;
    @Column(name = "idmedico")
    private Integer idmedico;
    @JoinColumn(name = "idPaciente", referencedColumnName = "idPaciente")
    @ManyToOne
    private TblPacientes idPaciente;
    @JoinColumn(name = "idTipoDiagnostico", referencedColumnName = "idTipoDiagnostico")
    @ManyToOne
    private TblTipoDiagnostico idTipoDiagnostico;
    @JoinColumn(name = "idusuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private TblUsuarios idusuario;

    public TblDiagnotiscos() {
    }

    public TblDiagnotiscos(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public TblDiagnotiscos(Integer idDiagnostico, Date fechaDiagnostico) {
        this.idDiagnostico = idDiagnostico;
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public Integer getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    public Integer getIdmedico() {
        return idmedico;
    }

    public void setIdmedico(Integer idmedico) {
        this.idmedico = idmedico;
    }

    public TblPacientes getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(TblPacientes idPaciente) {
        this.idPaciente = idPaciente;
    }

    public TblTipoDiagnostico getIdTipoDiagnostico() {
        return idTipoDiagnostico;
    }

    public void setIdTipoDiagnostico(TblTipoDiagnostico idTipoDiagnostico) {
        this.idTipoDiagnostico = idTipoDiagnostico;
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
        hash += (idDiagnostico != null ? idDiagnostico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDiagnotiscos)) {
            return false;
        }
        TblDiagnotiscos other = (TblDiagnotiscos) object;
        if ((this.idDiagnostico == null && other.idDiagnostico != null) || (this.idDiagnostico != null && !this.idDiagnostico.equals(other.idDiagnostico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblDiagnotiscos[ idDiagnostico=" + idDiagnostico + " ]";
    }
    
}
