/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import modelo.TblPacientes;

/**
 *
 * @author fabricio.diaz
 */
public class clsPaciente {
    private int _id;
    private String _fullname;
    private String _direccion;
    private Date _fecha;
    private int _edad;
    private String _sexo;
    private String _cedula;
    public clsPaciente(TblPacientes p) {
        this._id = p.getIdPaciente();
        this._fullname = p.getNombre().toUpperCase() + " "+ p.getApellido().toUpperCase();
        this._direccion = p.getDireccion();
        this._fecha = p.getFechaNace();
        this._sexo = p.getSexo();
        this._cedula=p.getCedula();
        LocalDate ahora=LocalDate.now();
	LocalDate fe1=_fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	Period periodo=Period.between(fe1, ahora);
        this._edad = periodo.getYears();
    }

    /**
     * @return the _id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void setId(int _id) {
        this._id = _id;
    }

    /**
     * @return the _fullname
     */
    public String getFullname() {
        return _fullname;
    }

    /**
     * @param _fullname the _fullname to set
     */
    public void setFullname(String _fullname) {
        this._fullname = _fullname;
    }

    /**
     * @return the _direccion
     */
    public String getDireccion() {
        return _direccion;
    }

    /**
     * @param _direccion the _direccion to set
     */
    public void setDireccion(String _direccion) {
        this._direccion = _direccion;
    }

    /**
     * @return the _fecha
     */
    public Date getFecha() {
        return _fecha;
    }

    /**
     * @param _fecha the _fecha to set
     */
    public void setFecha(Date _fecha) {
        this._fecha = _fecha;
    }

    /**
     * @return the _edad
     */
    public int getEdad() {
        return _edad;
    }

    /**
     * @param _edad the _edad to set
     */
    public void setEdad(int _edad) {
        this._edad = _edad;
    }

    /**
     * @return the _sexo
     */
    public String getSexo() {
        return _sexo;
    }

    /**
     * @param _sexo the _sexo to set
     */
    public void setSexo(String _sexo) {
        this._sexo = _sexo;
    }

    /**
     * @return the _cedula
     */
    public String getCedula() {
        return _cedula;
    }

    /**
     * @param _cedula the _cedula to set
     */
    public void setCedula(String _cedula) {
        this._cedula = _cedula;
    }
    
}
