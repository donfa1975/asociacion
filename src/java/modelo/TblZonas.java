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
@Table(name = "tblZonas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblZonas.findAll", query = "SELECT t FROM TblZonas t")
    , @NamedQuery(name = "TblZonas.findByIdZona", query = "SELECT t FROM TblZonas t WHERE t.idZona = :idZona")
    , @NamedQuery(name = "TblZonas.findByZona", query = "SELECT t FROM TblZonas t WHERE t.zona = :zona")
    , @NamedQuery(name = "TblZonas.findByIdcanton", query = "SELECT t FROM TblZonas t WHERE t.idcanton = :idcanton")})
public class TblZonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idZona")
    private Integer idZona;
    @Size(max = 150)
    @Column(name = "Zona")
    private String zona;
    @Column(name = "idcanton")
    private Integer idcanton;
    @OneToMany(mappedBy = "idzona")
    private Collection<TblPacientes> tblPacientesCollection;

    public TblZonas() {
    }

    public TblZonas(Integer idZona) {
        this.idZona = idZona;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Integer getIdcanton() {
        return idcanton;
    }

    public void setIdcanton(Integer idcanton) {
        this.idcanton = idcanton;
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
        hash += (idZona != null ? idZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblZonas)) {
            return false;
        }
        TblZonas other = (TblZonas) object;
        if ((this.idZona == null && other.idZona != null) || (this.idZona != null && !this.idZona.equals(other.idZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblZonas[ idZona=" + idZona + " ]";
    }
    
}
