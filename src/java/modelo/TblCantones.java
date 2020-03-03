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
@Table(name = "tblCantones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCantones.findAll", query = "SELECT t FROM TblCantones t")
    , @NamedQuery(name = "TblCantones.findByIdcanton", query = "SELECT t FROM TblCantones t WHERE t.idcanton = :idcanton")
    , @NamedQuery(name = "TblCantones.findByCanton", query = "SELECT t FROM TblCantones t WHERE t.canton = :canton")})
public class TblCantones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcanton")
    private Integer idcanton;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "canton")
    private String canton;
    @OneToMany(mappedBy = "idCanton")
    private Collection<TblPacientes> tblPacientesCollection;

    public TblCantones() {
    }

    public TblCantones(Integer idcanton) {
        this.idcanton = idcanton;
    }

    public TblCantones(Integer idcanton, String canton) {
        this.idcanton = idcanton;
        this.canton = canton;
    }

    public Integer getIdcanton() {
        return idcanton;
    }

    public void setIdcanton(Integer idcanton) {
        this.idcanton = idcanton;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
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
        hash += (idcanton != null ? idcanton.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCantones)) {
            return false;
        }
        TblCantones other = (TblCantones) object;
        if ((this.idcanton == null && other.idcanton != null) || (this.idcanton != null && !this.idcanton.equals(other.idcanton))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblCantones[ idcanton=" + idcanton + " ]";
    }
    
}
