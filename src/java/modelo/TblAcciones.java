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
@Table(name = "tblAcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAcciones.findAll", query = "SELECT t FROM TblAcciones t")
    , @NamedQuery(name = "TblAcciones.findByIdAccion", query = "SELECT t FROM TblAcciones t WHERE t.idAccion = :idAccion")
    , @NamedQuery(name = "TblAcciones.findByAccion", query = "SELECT t FROM TblAcciones t WHERE t.accion = :accion")
    , @NamedQuery(name = "TblAcciones.findByTipo", query = "SELECT t FROM TblAcciones t WHERE t.tipo = :tipo")})
public class TblAcciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAccion")
    private Integer idAccion;
    @Size(max = 450)
    @Column(name = "Accion")
    private String accion;
    @Column(name = "tipo")
    private Integer tipo;
    @OneToMany(mappedBy = "idAccion")
    private Collection<TblCitas> tblCitasCollection;

    public TblAcciones() {
    }

    public TblAcciones(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<TblCitas> getTblCitasCollection() {
        return tblCitasCollection;
    }

    public void setTblCitasCollection(Collection<TblCitas> tblCitasCollection) {
        this.tblCitasCollection = tblCitasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccion != null ? idAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblAcciones)) {
            return false;
        }
        TblAcciones other = (TblAcciones) object;
        if ((this.idAccion == null && other.idAccion != null) || (this.idAccion != null && !this.idAccion.equals(other.idAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblAcciones[ idAccion=" + idAccion + " ]";
    }
    
}
