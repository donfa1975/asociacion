/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "tblAreas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAreas.findAll", query = "SELECT t FROM TblAreas t")
    , @NamedQuery(name = "TblAreas.findByIdArea", query = "SELECT t FROM TblAreas t WHERE t.idArea = :idArea")
    , @NamedQuery(name = "TblAreas.findByArea", query = "SELECT t FROM TblAreas t WHERE t.area = :area")})
public class TblAreas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArea")
    private Integer idArea;
    @Size(max = 350)
    @Column(name = "Area")
    private String area;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArea")
    private Collection<TblArcPac> tblArcPacCollection;
    @OneToMany(mappedBy = "idArea")
    private Collection<TblPacientes> tblPacientesCollection;

    public TblAreas() {
    }

    public TblAreas(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @XmlTransient
    public Collection<TblArcPac> getTblArcPacCollection() {
        return tblArcPacCollection;
    }

    public void setTblArcPacCollection(Collection<TblArcPac> tblArcPacCollection) {
        this.tblArcPacCollection = tblArcPacCollection;
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
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblAreas)) {
            return false;
        }
        TblAreas other = (TblAreas) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblAreas[ idArea=" + idArea + " ]";
    }
    
}
