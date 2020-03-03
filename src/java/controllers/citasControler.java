/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import converter.clsConvertidor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelo.TblAcciones;
import modelo.TblCitas;
import modelo.TblPacientes;

/**
 *
 * @author fabricio.diaz
 */
public class citasControler {
    EntityManager em;
    private List<TblCitas> _citas;
    private TblPacientes _mipac;
    private String _nombre;
    private String _representante;
    private String _telefono;
    private String _telefonoMadre;
    private String _telefonoPadre;
    private String _cedula;
    private String _madre;
    private String _padre;
    private Date _fecha;
    private Date _hora;
    private String _doctor;
    private String _idconsulta;
    private String _idterapia;
    private List<TblAcciones> _terapias;
    private List<TblAcciones> _consultas;
    
    public citasControler(int idpaciente) {
        inicializar();
        
        Query qt=em.createQuery("select c from TblAcciones c where c.tipo=2"); // terapias
        _terapias=qt.getResultList();
        
        Query qc=em.createQuery("select c from TblAcciones c where c.tipo=1"); // consultas
        _consultas=qc.getResultList();
        
      this.cargaDatos(idpaciente);
        
    }
    public void inicializar()
    {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        em = emf.createEntityManager();
    }
    private void cargaDatos(int idpaciente)
    {
              
        Query q=em.createQuery("select c from TblPacientes c where c.idPaciente=:id");
        q.setParameter("id", idpaciente);
        try
        {
            _mipac=(TblPacientes)q.getSingleResult();
            _citas=new ArrayList<>(getMipac().getTblCitasCollection());
            _citas=_citas.stream().sorted(Comparator.comparingInt(TblCitas::getIdCita).reversed()).collect(Collectors.toList());
            
            _nombre=_mipac.getNombre()+" "+ _mipac.getApellido();
            _representante=_mipac.getNombreRepresentante();
            _telefono=_mipac.getTelefono();
            _telefonoMadre=_mipac.getTelMadre();
            _telefonoPadre=_mipac.getTelPadre();
            _padre=_mipac.getNombrePadre();
            _madre=_mipac.getNombreMadre();
            _cedula=_mipac.getCedula();
        }
        catch(Exception ex)
        {
            _mipac=null;
        
        }
        
    }
    public void addCitaConsulta()
    {
        SimpleDateFormat sdfr = new SimpleDateFormat("HH:mm");
        String strHora=sdfr.format(_hora);
        this.addCita(Integer.valueOf(this._idconsulta), _fecha, strHora, _doctor);
    }
    public void addCitaTerapia()
    {
        SimpleDateFormat sdfr = new SimpleDateFormat("HH:mm");
        String strHora=sdfr.format(_hora);
        this.addCita(Integer.valueOf(this._idterapia), _fecha, strHora, _doctor);
        
    }
    private void addCita(int idaccion, Date fecha, String hora,String doctor)
    { clsConvertidor conv=new clsConvertidor();
      clsDBX dbx=new clsDBX();
      String r="";      
      TblCitas nuevaCita=new TblCitas();
      nuevaCita.setEjecutada(Boolean.FALSE);
      nuevaCita.setFecha(fecha);
      nuevaCita.setFechaReg(new Date());
      nuevaCita.setHora(hora);
      nuevaCita.setIdAccion(conv.getAccion(idaccion));
      nuevaCita.setIdCita(0);
      nuevaCita.setIdPaciente(_mipac);
      nuevaCita.setIdusuario(0);
      nuevaCita.setNombreDr(doctor);
      nuevaCita.setNumeroCita("00");
      r=dbx.insertar(nuevaCita);
       if(r=="Ok")
            {
                this.addMessage("Cita creada","Cita creada con exito");
              //  inicializar();
            }
       else
       {
                this.addMessage("Error",r);
       }
    }
    public void delCita(TblCitas cita)
    {
      clsDBX dbx=new clsDBX();
      String r="";   
      
      r=dbx.eliminar(cita, cita.getIdCita());
      this.addMessage("Cita elimnada","Cita elimnada");
      
    }
     public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    /**
     * @return the _citas
     */
    public List<TblCitas> getCitas() {
        return _citas;
    }

    /**
     * @param _citas the _citas to set
     */
    public void setCitas(List<TblCitas> _citas) {
        this._citas = _citas;
    }

    /**
     * @return the _mipac
     */
    public TblPacientes getMipac() {
        return _mipac;
    }

    /**
     * @param _mipac the _mipac to set
     */
    public void setMipac(TblPacientes _mipac) {
        this._mipac = _mipac;
    }

    /**
     * @return the _nombre
     */
    public String getNombre() {
        return _nombre;
    }

    /**
     * @param _nombre the _nombre to set
     */
    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    /**
     * @return the _representante
     */
    public String getRepresentante() {
        return _representante;
    }

    /**
     * @param _representante the _representante to set
     */
    public void setRepresentante(String _representante) {
        this._representante = _representante;
    }

    /**
     * @return the _telefono
     */
    public String getTelefono() {
        return _telefono;
    }

    /**
     * @param _telefono the _telefono to set
     */
    public void setTelefono(String _telefono) {
        this._telefono = _telefono;
    }

    /**
     * @return the _telefonoMadre
     */
    public String getTelefonoMadre() {
        return _telefonoMadre;
    }

    /**
     * @param _telefonoMadre the _telefonoMadre to set
     */
    public void setTelefonoMadre(String _telefonoMadre) {
        this._telefonoMadre = _telefonoMadre;
    }

    /**
     * @return the _telefonoPadre
     */
    public String getTelefonoPadre() {
        return _telefonoPadre;
    }

    /**
     * @param _telefonoPadre the _telefonoPadre to set
     */
    public void setTelefonoPadre(String _telefonoPadre) {
        this._telefonoPadre = _telefonoPadre;
    }

    /**
     * @return the _terapias
     */
    public List<TblAcciones> getTerapias() {
        return _terapias;
    }

    /**
     * @param _terapias the _terapias to set
     */
    public void setTerapias(List<TblAcciones> _terapias) {
        this._terapias = _terapias;
    }

    /**
     * @return the _consultas
     */
    public List<TblAcciones> getConsultas() {
        return _consultas;
    }

    /**
     * @param _consultas the _consultas to set
     */
    public void setConsultas(List<TblAcciones> _consultas) {
        this._consultas = _consultas;
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

    /**
     * @return the _madre
     */
    public String getMadre() {
        return _madre;
    }

    /**
     * @param _madre the _madre to set
     */
    public void setMadre(String _madre) {
        this._madre = _madre;
    }

    /**
     * @return the _padre
     */
    public String getPadre() {
        return _padre;
    }

    /**
     * @param _padre the _padre to set
     */
    public void setPadre(String _padre) {
        this._padre = _padre;
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
     * @return the _hora
     */
    public Date getHora() {
        return _hora;
    }

    /**
     * @param _hora the _hora to set
     */
    public void setHora(Date _hora) {
        this._hora = _hora;
    }

    /**
     * @return the _doctor
     */
    public String getDoctor() {
        return _doctor;
    }

    /**
     * @param _doctor the _doctor to set
     */
    public void setDoctor(String _doctor) {
        this._doctor = _doctor;
    }

    /**
     * @return the _idconsulta
     */
    public String getIdconsulta() {
        return _idconsulta;
    }

    /**
     * @param _idconsulta the _idconsulta to set
     */
    public void setIdconsulta(String _idconsulta) {
        this._idconsulta = _idconsulta;
    }

    /**
     * @return the _idterapia
     */
    public String getIdterapia() {
        return _idterapia;
    }

    /**
     * @param _idterapia the _idterapia to set
     */
    public void setIdterapia(String _idterapia) {
        this._idterapia = _idterapia;
    }
}
