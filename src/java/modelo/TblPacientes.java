/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

//import java.beans.Transient;
//import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fabricio.diaz
 */
@Entity
@Table(name = "tblPacientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPacientes.findAll", query = "SELECT t FROM TblPacientes t")
    , @NamedQuery(name = "TblPacientes.findByIdPaciente", query = "SELECT t FROM TblPacientes t WHERE t.idPaciente = :idPaciente")
    , @NamedQuery(name = "TblPacientes.findByCedula", query = "SELECT t FROM TblPacientes t WHERE t.cedula = :cedula")
    , @NamedQuery(name = "TblPacientes.findByApellido", query = "SELECT t FROM TblPacientes t WHERE t.apellido = :apellido")
    , @NamedQuery(name = "TblPacientes.findByNombre", query = "SELECT t FROM TblPacientes t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TblPacientes.findByFechaNace", query = "SELECT t FROM TblPacientes t WHERE t.fechaNace = :fechaNace")
    , @NamedQuery(name = "TblPacientes.findByCedRepresentante", query = "SELECT t FROM TblPacientes t WHERE t.cedRepresentante = :cedRepresentante")
    , @NamedQuery(name = "TblPacientes.findByNombreRepresentante", query = "SELECT t FROM TblPacientes t WHERE t.nombreRepresentante = :nombreRepresentante")
    , @NamedQuery(name = "TblPacientes.findByNombrePadre", query = "SELECT t FROM TblPacientes t WHERE t.nombrePadre = :nombrePadre")
    , @NamedQuery(name = "TblPacientes.findByNombreMadre", query = "SELECT t FROM TblPacientes t WHERE t.nombreMadre = :nombreMadre")
    , @NamedQuery(name = "TblPacientes.findBySexo", query = "SELECT t FROM TblPacientes t WHERE t.sexo = :sexo")
    , @NamedQuery(name = "TblPacientes.findByDireccion", query = "SELECT t FROM TblPacientes t WHERE t.direccion = :direccion")
    , @NamedQuery(name = "TblPacientes.findByTelefono", query = "SELECT t FROM TblPacientes t WHERE t.telefono = :telefono")
    , @NamedQuery(name = "TblPacientes.findByFechaReg", query = "SELECT t FROM TblPacientes t WHERE t.fechaReg = :fechaReg")
    , @NamedQuery(name = "TblPacientes.findByTelMadre", query = "SELECT t FROM TblPacientes t WHERE t.telMadre = :telMadre")
    , @NamedQuery(name = "TblPacientes.findByTelPadre", query = "SELECT t FROM TblPacientes t WHERE t.telPadre = :telPadre")})
public class TblPacientes implements Serializable {

    @Size(max = 20)
    @Column(name = "matriculado")
    private String matriculado;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPaciente")
    private Integer idPaciente;
    @Size(max = 10)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Apellido")
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaNace")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNace;
    @Size(max = 10)
    @Column(name = "cedRepresentante")
    private String cedRepresentante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NombreRepresentante")
    private String nombreRepresentante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NombrePadre")
    private String nombrePadre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NombreMadre")
    private String nombreMadre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "sexo")
    private String sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Direccion")
    private String direccion;
    @Size(max = 50)
    @Column(name = "Telefono")
    private String telefono;
    @Column(name = "fechaReg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReg;
    @Size(max = 20)
    @Column(name = "telMadre")
    private String telMadre;
    @Size(max = 20)
    @Column(name = "telPadre")
    private String telPadre;
    ////////////
    /*
    @Transient
    @Column(name="fullName")
    private String fullName;*/
    ///////////////
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private Collection<TblArcPac> tblArcPacCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<TblDiagnotiscos> tblDiagnotiscosCollection;
    @JoinColumn(name = "idArea", referencedColumnName = "idArea")
    @ManyToOne
    private TblAreas idArea;
    @JoinColumn(name = "idCanton", referencedColumnName = "idcanton")
    @ManyToOne
    private TblCantones idCanton;
    @JoinColumn(name = "idOrigen", referencedColumnName = "idorigen")
    @ManyToOne
    private TblOrigenes idOrigen;
    @JoinColumn(name = "idzona", referencedColumnName = "idZona")
    @ManyToOne
    private TblZonas idzona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private Collection<TblMatriculas> tblMatriculasCollection;
    @OneToMany(mappedBy = "idPaciente")
    private Collection<TblCitas> tblCitasCollection;

    public TblPacientes() {
//        fullName=this.nombre.toUpperCase() + " "+ this.apellido.toUpperCase();
    }

    public TblPacientes(Integer idPaciente) {
        this.idPaciente = idPaciente;
      //  fullName=this.nombre.toUpperCase() + " "+ this.apellido.toUpperCase();
    }

    public TblPacientes(Integer idPaciente, String apellido, String nombre, Date fechaNace, String nombreRepresentante, String nombrePadre, String nombreMadre, String sexo, String direccion) {
        this.idPaciente = idPaciente;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNace = fechaNace;
        this.nombreRepresentante = nombreRepresentante;
        this.nombrePadre = nombrePadre;
        this.nombreMadre = nombreMadre;
        this.sexo = sexo;
        this.direccion = direccion;
       // fullName=this.nombre.toUpperCase() + " "+ this.apellido.toUpperCase();
        
    }

  
    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNace() {
        return fechaNace;
    }

    public void setFechaNace(Date fechaNace) {
        this.fechaNace = fechaNace;
    }

    public String getCedRepresentante() {
        return cedRepresentante;
    }

    public void setCedRepresentante(String cedRepresentante) {
        this.cedRepresentante = cedRepresentante;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getNombreMadre() {
        return nombreMadre;
    }

    public void setNombreMadre(String nombreMadre) {
        this.nombreMadre = nombreMadre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public String getTelMadre() {
        return telMadre;
    }

    public void setTelMadre(String telMadre) {
        this.telMadre = telMadre;
    }

    public String getTelPadre() {
        return telPadre;
    }

    public void setTelPadre(String telPadre) {
        this.telPadre = telPadre;
    }

    @XmlTransient
    public Collection<TblArcPac> getTblArcPacCollection() {
        return tblArcPacCollection;
    }

    public void setTblArcPacCollection(Collection<TblArcPac> tblArcPacCollection) {
        this.tblArcPacCollection = tblArcPacCollection;
    }

    @XmlTransient
    public Collection<TblDiagnotiscos> getTblDiagnotiscosCollection() {
        return tblDiagnotiscosCollection;
    }

    public void setTblDiagnotiscosCollection(Collection<TblDiagnotiscos> tblDiagnotiscosCollection) {
        this.tblDiagnotiscosCollection = tblDiagnotiscosCollection;
    }

    public TblAreas getIdArea() {
        return idArea;
    }

    public void setIdArea(TblAreas idArea) {
        this.idArea = idArea;
    }

    public TblCantones getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(TblCantones idCanton) {
        this.idCanton = idCanton;
    }

    public TblOrigenes getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(TblOrigenes idOrigen) {
        this.idOrigen = idOrigen;
    }

    public TblZonas getIdzona() {
        return idzona;
    }

    public void setIdzona(TblZonas idzona) {
        this.idzona = idzona;
    }
    
    @XmlTransient
    public Collection<TblMatriculas> getTblMatriculasCollection() {
        return tblMatriculasCollection;
    }

    public void setTblMatriculasCollection(Collection<TblMatriculas> tblMatriculasCollection) {
        this.tblMatriculasCollection = tblMatriculasCollection;
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
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPacientes)) {
            return false;
        }
        TblPacientes other = (TblPacientes) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TblPacientes[ idPaciente=" + idPaciente + " ]";
    }

    /**
     * @return the fullName
     */
  /*  public String getFullName() {
//       return this.fullName;
        // return this.nombre.toUpperCase() + " "+ this.apellido.toUpperCase();
        
    }

    /**
     * @param fullName the fullName to set
     */
  //  @Transient
    public int getEdad() {
        LocalDate ahora=LocalDate.now();
		LocalDate fe1=this.fechaNace.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period periodo=Period.between(fe1, ahora);
		
		return periodo.getYears();
    }

    public String getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(String matriculado) {
        this.matriculado = matriculado;
    }
    
}
