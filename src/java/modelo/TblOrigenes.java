/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fabricio.diaz
 */
@Entity
@Table(name = "tblOrigenes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblOrigenes.findAll", query = "SELECT t FROM TblOrigenes t")
    , @NamedQuery(name = "TblOrigenes.findByIdorigen", query = "SELECT t FROM TblOrigenes t WHERE t.idorigen = :idorigen")
    , @NamedQuery(name = "TblOrigenes.findByOrigen", query = "SELECT t FROM TblOrigenes t WHERE t.origen = :origen")})
public class TblOrigenes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idorigen")
    private Integer idorigen;
    @Size(max = 150)
    @Column(name = "Origen")
    private String origen;
    @OneToMany(mappedBy = "idOrigen")
    private Collection<TblPacientes> tblPacientesCollection;

    public TblOrigenes() {
    }

    public TblOrigenes(Integer idorigen) {
        this.idorigen = idorigen;
    }

    public Integer getIdorigen() {
        return idorigen;
    }

    public void setIdorigen(Integer idorigen) {
        this.idorigen = idorigen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @XmlTransient
    public Collection<TblPacientes> getTblPacientesCollection() {
        return tblPacientesCollection;
    }

    public void setTblPacientesCollection(Collection<TblPacientes> tblPacientesCollection) {
        this.tblPacientesCollection = tblPacientesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorigen != null ? idorigen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblOrigenes)) {
            return false;
        }
        TblOrigenes other = (TblOrigenes) object;
        if ((this.idorigen == null && other.idorigen != null) || (this.idorigen != null && !this.idorigen.equals(other.idorigen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblOrigenes[ idorigen=" + idorigen + " ]";
    }
    
}
