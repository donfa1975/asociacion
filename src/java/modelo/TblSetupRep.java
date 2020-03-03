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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabricio.diaz
 */
@Entity
@Table(name = "tblSetupRep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSetupRep.findAll", query = "SELECT t FROM TblSetupRep t")
    , @NamedQuery(name = "TblSetupRep.findByIdSetup", query = "SELECT t FROM TblSetupRep t WHERE t.idSetup = :idSetup")
    , @NamedQuery(name = "TblSetupRep.findByServidor", query = "SELECT t FROM TblSetupRep t WHERE t.servidor = :servidor")
    , @NamedQuery(name = "TblSetupRep.findByRuta", query = "SELECT t FROM TblSetupRep t WHERE t.ruta = :ruta")})
public class TblSetupRep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idSetup")
    private Integer idSetup;
    @Size(max = 2147483647)
    @Column(name = "servidor")
    private String servidor;
    @Size(max = 2147483647)
    @Column(name = "ruta")
    private String ruta;

    public TblSetupRep() {
    }

    public TblSetupRep(Integer idSetup) {
        this.idSetup = idSetup;
    }

    public Integer getIdSetup() {
        return idSetup;
    }

    public void setIdSetup(Integer idSetup) {
        this.idSetup = idSetup;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSetup != null ? idSetup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblSetupRep)) {
            return false;
        }
        TblSetupRep other = (TblSetupRep) object;
        if ((this.idSetup == null && other.idSetup != null) || (this.idSetup != null && !this.idSetup.equals(other.idSetup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblSetupRep[ idSetup=" + idSetup + " ]";
    }
    
}
