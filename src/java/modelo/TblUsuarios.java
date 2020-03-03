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
@Table(name = "tblUsuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUsuarios.findAll", query = "SELECT t FROM TblUsuarios t")
    , @NamedQuery(name = "TblUsuarios.findByIdUsuario", query = "SELECT t FROM TblUsuarios t WHERE t.idUsuario = :idUsuario")
    , @NamedQuery(name = "TblUsuarios.findByUsuario", query = "SELECT t FROM TblUsuarios t WHERE t.usuario = :usuario")
    , @NamedQuery(name = "TblUsuarios.findByNombre", query = "SELECT t FROM TblUsuarios t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TblUsuarios.findByClave", query = "SELECT t FROM TblUsuarios t WHERE t.clave = :clave")
    , @NamedQuery(name = "TblUsuarios.findByCargo", query = "SELECT t FROM TblUsuarios t WHERE t.cargo = :cargo")
    , @NamedQuery(name = "TblUsuarios.findByCorreo", query = "SELECT t FROM TblUsuarios t WHERE t.correo = :correo")})
public class TblUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "clave")
    private String clave;
    @Size(max = 200)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 250)
    @Column(name = "correo")
    private String correo;
    @OneToMany(mappedBy = "idusuario")
    private Collection<TblDiagnotiscos> tblDiagnotiscosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idusuario")
    private Collection<TblMatriculas> tblMatriculasCollection;

    public TblUsuarios() {
    }

    public TblUsuarios(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TblUsuarios(Integer idUsuario, String usuario, String nombre, String clave) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.clave = clave;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @XmlTransient
    public Collection<TblDiagnotiscos> getTblDiagnotiscosCollection() {
        return tblDiagnotiscosCollection;
    }

    public void setTblDiagnotiscosCollection(Collection<TblDiagnotiscos> tblDiagnotiscosCollection) {
        this.tblDiagnotiscosCollection = tblDiagnotiscosCollection;
    }

    @XmlTransient
    public Collection<TblMatriculas> getTblMatriculasCollection() {
        return tblMatriculasCollection;
    }

    public void setTblMatriculasCollection(Collection<TblMatriculas> tblMatriculasCollection) {
        this.tblMatriculasCollection = tblMatriculasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUsuarios)) {
            return false;
        }
        TblUsuarios other = (TblUsuarios) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblUsuarios[ idUsuario=" + idUsuario + " ]";
    }
    
}
