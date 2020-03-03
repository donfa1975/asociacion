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
@Table(name = "tblPeriodos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPeriodos.findAll", query = "SELECT t FROM TblPeriodos t")
    , @NamedQuery(name = "TblPeriodos.findByIdperiodo", query = "SELECT t FROM TblPeriodos t WHERE t.idperiodo = :idperiodo")
    , @NamedQuery(name = "TblPeriodos.findByPeriodo", query = "SELECT t FROM TblPeriodos t WHERE t.periodo = :periodo")})
public class TblPeriodos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idperiodo")
    private Integer idperiodo;
    @Size(max = 10)
    @Column(name = "periodo")
    private String periodo;

    public TblPeriodos() {
    }

    public TblPeriodos(Integer idperiodo) {
        this.idperiodo = idperiodo;
    }

    public Integer getIdperiodo() {
        return idperiodo;
    }

    public void setIdperiodo(Integer idperiodo) {
        this.idperiodo = idperiodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idperiodo != null ? idperiodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPeriodos)) {
            return false;
        }
        TblPeriodos other = (TblPeriodos) object;
        if ((this.idperiodo == null && other.idperiodo != null) || (this.idperiodo != null && !this.idperiodo.equals(other.idperiodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblPeriodos[ idperiodo=" + idperiodo + " ]";
    }
    
}
