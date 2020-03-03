/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controller.util.JsfListas;
import converter.clsConvertidor;
import converter.clsPaciente;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import modelo.TblArcPac;
import modelo.TblDiagnotiscos;
import modelo.TblPacientes;
import modelo.TblTipoDiagnostico;
import modelo.TblUsuarios;

/**
 *
 * @author fabricio.diaz
 */
public class PacientesController {
    EntityManager em;
    private List<TblPacientes> listado;
    private List<clsPaciente> filtrado;
    private TblPacientes nuevo;
    private TblPacientes seleccionado;
    private List<clsPaciente> pacientes;
    private clsPaciente selPrev;
    private String _filtro;
    private JsfListas _listas;
    private List<String> _nuevaArea;
    private List<TblTipoDiagnostico> _nuevosDiag;
    private String _pidArea;
    private String _pidCanton;
    private String _pidOrigen;
    private String _pidTipoDiag;
    private String _pidZona;
    private String _coddiag;
    private TblUsuarios _usuario;

    public PacientesController() {
        inicializar();
       
    }
    //////////////////////
    private void inicializar()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        em = emf.createEntityManager();
        this.seleccionado=null;
        this.selPrev=null;
        this.nuevo=new TblPacientes();
        _nuevaArea=new ArrayList<>();
        _nuevosDiag=new ArrayList<>();
       
        Query qusu=em.createQuery("select c from TblUsuarios c");
        _usuario=(TblUsuarios)qusu.getResultList().get(0);
        
       Query q1=em.createQuery("select c from TblPacientes c  order by c.apellido, c.nombre");
       listado= q1.getResultList();
       pacientes=new ArrayList<>();
       for(TblPacientes p:listado)
       {
            pacientes.add(new clsPaciente(p));
       }
       this.filtrado=pacientes;
       _listas=new JsfListas();
    }
    //////////////////////
    public void filtrar()
    {String par;
        par=_filtro.toUpperCase();
       // filtrado=null;
       filtrado = pacientes.stream().filter(line -> line.getFullname().contains(par)).collect(Collectors.toList());
       //pacientes=filtrado;
    }
    public void verTodo()
    {   
       filtrado=pacientes;
    }
    public int getEdad(Date fechanac)
	{
		LocalDate ahora=LocalDate.now();
		LocalDate fe1=fechanac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period periodo=Period.between(fe1, ahora);
		
		return periodo.getYears();
		
	}
  
    public  void prepareCreate()
    {
        this.setNuevo(new TblPacientes());
        //return "/frCreaPaciente";
    }
    public  String prepareEditar(clsPaciente paciente)
    {
       seleccionado = listado.stream().filter(pac -> paciente.getId()==pac.getIdPaciente()).findAny().orElse(null);
       
       return "/frCreaPaciente?faces-redirect=true";
    }
    public void guardar()
    {
        clsConvertidor convert=new clsConvertidor();
        String valor="";
     //   int id=this.nuevo.getIdPaciente();
        
        nuevo.setApellido(nuevo.getApellido().toUpperCase());
        nuevo.setNombre(nuevo.getNombre().toUpperCase());
        nuevo.setNombreMadre(nuevo.getNombreMadre().toUpperCase());
        nuevo.setNombrePadre(nuevo.getNombrePadre().toUpperCase());
        nuevo.setNombreRepresentante(nuevo.getNombreRepresentante().toUpperCase());
        nuevo.setIdPaciente(0);
        nuevo.setDireccion(nuevo.getDireccion().toUpperCase());
        nuevo.setFechaReg(new Date());
        this._pidArea=this._nuevaArea.get(0);
        nuevo.setIdArea(convert.getArea(this._pidArea));
        nuevo.setIdCanton(convert.getCanton(this._pidCanton));
        nuevo.setIdOrigen(convert.getOrigen(this._pidOrigen));
        nuevo.setIdzona(convert.getZona(this._pidZona));
        nuevo.setTblArcPacCollection(new ArrayList<>());
        TblArcPac ap;
        for(String s:this._nuevaArea)
        {
            ap=new TblArcPac();
            ap.setIdArPac(0);
            ap.setAlta(false);
            ap.setIdArea(convert.getArea(s));
            ap.setIdPaciente(nuevo);
            nuevo.getTblArcPacCollection().add(ap);
        }
        String r;
        ///////////////////////////
       // try {
            clsDBX dbx=new clsDBX();
            
            r=dbx.insertar(nuevo);
            if(r=="Ok")
            {
                this.addMessage("Paciente creado", "Paciente "+ nuevo.getApellido()+" " +nuevo.getNombre() + " creado con exito");
                inicializar();
            }
            
            
        ///////////////////////////
        
    }
    public void sumadiagnostico()
    {
        
        diagnosticosControl ctdiag=new diagnosticosControl();
        
        ctdiag.setCodigo(_coddiag);
        ctdiag.buscaCodigo();
        
        
        if(!"ND".equals(ctdiag.getDiag()))
        {String r="";
            TblDiagnotiscos td=new TblDiagnotiscos();
            td.setIdDiagnostico(0);
            td.setIdPaciente(seleccionado);
            td.setIdTipoDiagnostico(ctdiag.getTipo());
            td.setFechaDiagnostico(new Date());
            td.setIdmedico(0);
            td.setIdusuario(_usuario);
            clsDBX linq=new clsDBX();
            r=linq.insertar(td);
            if(r=="Ok")
            {
                seleccionado.getTblDiagnotiscosCollection().add(td);
            }
        }
        
    }
     public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
//<editor-fold desc="propiedades">
    /**
     * @return the listado
     */
    public List<TblPacientes> getListado() {
        return listado;
    }

    /**
     * @param listado the listado to set
     */
    public void setListado(List<TblPacientes> listado) {
        this.listado = listado;
    }

    /**
     * @return the filtrado
     */
    public List<clsPaciente> getFiltrado() {
        return filtrado;
    }

    /**
     * @param filtrado the filtrado to set
     */
    public void setFiltrado(List<clsPaciente> filtrado) {
        this.filtrado = filtrado;
    }

    /**
     * @return the nuevo
     */
    public TblPacientes getNuevo() {
        return nuevo;
    }

    /**
     * @param nuevo the nuevo to set
     */
    public void setNuevo(TblPacientes nuevo) {
        this.nuevo = nuevo;
    }

    /**
     * @return the seleccionado
     */
    public TblPacientes getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(TblPacientes seleccionado) {
        this.seleccionado = seleccionado;
    }
  //</editor-fold> 

    /**
     * @return the pacientes
     */
    public List<clsPaciente> getPacientes() {
        return pacientes;
    }

    /**
     * @param pacientes the pacientes to set
     */
    public void setPacientes(List<clsPaciente> pacientes) {
        this.pacientes = pacientes;
    }

    /**
     * @return the _filtro
     */
    public String getFiltro() {
        return _filtro;
    }

    /**
     * @param _filtro the _filtro to set
     */
    public void setFiltro(String _filtro) {
        this._filtro = _filtro;
    }

    /**
     * @return the selPrev
     */
    public clsPaciente getSelPrev() {
        return selPrev;
    }

    /**
     * @param selPrev the selPrev to set
     */
    public void setSelPrev(clsPaciente selPrev) {
        if(selPrev==null)
            return;
        this.selPrev = selPrev;
        seleccionado = listado.stream().filter(pac -> selPrev.getId()==pac.getIdPaciente()).findAny().orElse(null);
    }

    /**
     * @return the _listas
     */
    public JsfListas getListas() {
        return _listas;
    }

    /**
     * @param _listas the _listas to set
     */
    public void setListas(JsfListas _listas) {
        this._listas = _listas;
    }

    /**
     * @return the _nuevaArea
     */
    public List<String> getNuevaArea() {
        return _nuevaArea;
    }

    /**
     * @param _nuevaArea the _nuevaArea to set
     */
    public void setNuevaArea(List<String> _nuevaArea) {
        this._nuevaArea = _nuevaArea;
    }

    /**
     * @return the _nuevosDiag
     */
    public List<TblTipoDiagnostico> getNuevosDiag() {
        return _nuevosDiag;
    }

    /**
     * @param _nuevosDiag the _nuevosDiag to set
     */
    public void setNuevosDiag(List<TblTipoDiagnostico> _nuevosDiag) {
        this._nuevosDiag = _nuevosDiag;
    }

    /**
     * @return the _pidArea
     */
    public String getPidArea() {
        return _pidArea;
    }

    /**
     * @param _pidArea the _pidArea to set
     */
    public void setPidArea(String _pidArea) {
        this._pidArea = _pidArea;
    }

    /**
     * @return the _pidCanton
     */
    public String getPidCanton() {
        return _pidCanton;
    }

    /**
     * @param _pidCanton the _pidCanton to set
     */
    public void setPidCanton(String _pidCanton) {
        this._pidCanton = _pidCanton;
        if(_pidCanton.isEmpty())
            return;
        this._listas.filtraZonas(_pidCanton);
    }

    /**
     * @return the _pidOrigen
     */
    public String getPidOrigen() {
        return _pidOrigen;
    }

    /**
     * @param _pidOrigen the _pidOrigen to set
     */
    public void setPidOrigen(String _pidOrigen) {
        this._pidOrigen = _pidOrigen;
    }

    /**
     * @return the _pidTipoDiag
     */
    public String getPidTipoDiag() {
        return _pidTipoDiag;
    }

    /**
     * @param _pidTipoDiag the _pidTipoDiag to set
     */
    public void setPidTipoDiag(String _pidTipoDiag) {
        this._pidTipoDiag = _pidTipoDiag;
    }

    /**
     * @return the _pidZona
     */
    public String getPidZona() {
        return _pidZona;
    }

    /**
     * @param _pidZona the _pidZona to set
     */
    public void setPidZona(String _pidZona) {
        this._pidZona = _pidZona;
    }

    /**
     * @return the _coddiag
     */
    public String getCoddiag() {
        return _coddiag;
    }

    /**
     * @param _coddiag the _coddiag to set
     */
    public void setCoddiag(String _coddiag) {
        this._coddiag = _coddiag;
    }
        
        
}
