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
@Table(name = "tblTipoDiagnostico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblTipoDiagnostico.findAll", query = "SELECT t FROM TblTipoDiagnostico t")
    , @NamedQuery(name = "TblTipoDiagnostico.findByIdTipoDiagnostico", query = "SELECT t FROM TblTipoDiagnostico t WHERE t.idTipoDiagnostico = :idTipoDiagnostico")
    , @NamedQuery(name = "TblTipoDiagnostico.findByCodigo", query = "SELECT t FROM TblTipoDiagnostico t WHERE t.codigo = :codigo")
    , @NamedQuery(name = "TblTipoDiagnostico.findByDiagnostico", query = "SELECT t FROM TblTipoDiagnostico t WHERE t.diagnostico = :diagnostico")})
public class TblTipoDiagnostico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTipoDiagnostico")
    private Integer idTipoDiagnostico;
    @Size(max = 10)
    @Column(name = "Codigo")
    private String codigo;
    @Size(max = 400)
    @Column(name = "Diagnostico")
    private String diagnostico;
    @OneToMany(mappedBy = "idTipoDiagnostico")
    private Collection<TblDiagnotiscos> tblDiagnotiscosCollection;

    public TblTipoDiagnostico() {
    }

    public TblTipoDiagnostico(Integer idTipoDiagnostico) {
        this.idTipoDiagnostico = idTipoDiagnostico;
    }

    public Integer getIdTipoDiagnostico() {
        return idTipoDiagnostico;
    }

    public void setIdTipoDiagnostico(Integer idTipoDiagnostico) {
        this.idTipoDiagnostico = idTipoDiagnostico;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @XmlTransient
    public Collection<TblDiagnotiscos> getTblDiagnotiscosCollection() {
        return tblDiagnotiscosCollection;
    }

    public void setTblDiagnotiscosCollection(Collection<TblDiagnotiscos> tblDiagnotiscosCollection) {
        this.tblDiagnotiscosCollection = tblDiagnotiscosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDiagnostico != null ? idTipoDiagnostico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTipoDiagnostico)) {
            return false;
        }
        TblTipoDiagnostico other = (TblTipoDiagnostico) object;
        if ((this.idTipoDiagnostico == null && other.idTipoDiagnostico != null) || (this.idTipoDiagnostico != null && !this.idTipoDiagnostico.equals(other.idTipoDiagnostico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblTipoDiagnostico[ idTipoDiagnostico=" + idTipoDiagnostico + " ]";
    }
    
}
