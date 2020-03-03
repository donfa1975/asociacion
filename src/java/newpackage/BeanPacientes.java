/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import controllers.PacientesController;
import controllers.citasControler;
import converter.clsPaciente;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author fabricio.diaz
 */
@Named(value = "beanPacientes")
@SessionScoped
public class BeanPacientes implements Serializable {
    private PacientesController ctPacientes;
    private citasControler ctCitas;
    
    /**
     * Creates a new instance of BeanPacientes
     */
    public BeanPacientes() {
        ctPacientes=new PacientesController();
    }
//<editor-fold desc="propiedades">
    /**
     * @return the ctPacientes
     */
    public PacientesController getCtPacientes() {
        return ctPacientes;
    }

    /**
     * @param ctPacientes the ctPacientes to set
     */
    public void setCtPacientes(PacientesController ctPacientes) {
        this.ctPacientes = ctPacientes;
    }
    public void preparaCita(clsPaciente c)
    {
        //this.ctPacientes.
        this.ctCitas=new citasControler(c.getId());
    }
  //</editor-fold>  

    /**
     * @return the ctCitas
     */
    public citasControler getCtCitas() {
        return ctCitas;
    }

    /**
     * @param ctCitas the ctCitas to set
     */
    public void setCtCitas(citasControler ctCitas) {
        this.ctCitas = ctCitas;
    }
}
