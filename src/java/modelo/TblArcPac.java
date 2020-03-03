/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabricio.diaz
 */
@Entity
@Table(name = "tblArcPac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblArcPac.findAll", query = "SELECT t FROM TblArcPac t")
    , @NamedQuery(name = "TblArcPac.findByIdArPac", query = "SELECT t FROM TblArcPac t WHERE t.idArPac = :idArPac")})
public class TblArcPac implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "alta")
    private boolean alta;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArPac")
    private Integer idArPac;
    @JoinColumn(name = "idArea", referencedColumnName = "idArea")
    @ManyToOne(optional = false)
    private TblAreas idArea;
    @JoinColumn(name = "idPaciente", referencedColumnName = "idPaciente")
    @ManyToOne(optional = false)
    private TblPacientes idPaciente;

    public TblArcPac() {
    }

    public TblArcPac(Integer idArPac) {
        this.idArPac = idArPac;
    }

    public Integer getIdArPac() {
        return idArPac;
    }

    public void setIdArPac(Integer idArPac) {
        this.idArPac = idArPac;
    }

    public TblAreas getIdArea() {
        return idArea;
    }

    public void setIdArea(TblAreas idArea) {
        this.idArea = idArea;
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
        hash += (idArPac != null ? idArPac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblArcPac)) {
            return false;
        }
        TblArcPac other = (TblArcPac) object;
        if ((this.idArPac == null && other.idArPac != null) || (this.idArPac != null && !this.idArPac.equals(other.idArPac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblArcPac[ idArPac=" + idArPac + " ]";
    }

    public boolean getAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }
    
}
