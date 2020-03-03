/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import modelo.TblUsuarios;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fabricio.diaz
 */
@Named(value = "bean1")
@SessionScoped
public class bean1 implements Serializable {
    private int op1;
    private int op2;
    private double r;
    private clsUsuario c1;
    private List<TblUsuarios> listado;
    /**
     * @return the op1
     */
    public int getOp1() {
        return op1;
    }

    /**
     * @param op1 the op1 to set
     */
    public void setOp1(int op1) {
        this.op1 = op1;
    }

    /**
     * @return the op2
     */
    public int getOp2() {
        return op2;
    }

    /**
     * @param op2 the op2 to set
     */
    public void setOp2(int op2) {
        this.op2 = op2;
    }

    /**
     * @return the r
     */
    public double getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(double r) {
        this.r = r;
    }
    
   
    /**
     * Creates a new instance of bean1
     */
    public bean1() {
        op1=0;
        op2=0;
        r=0;
        c1=new clsUsuario();
        listado=c1.listado();
    }

    /**
     * @return the c1
     */
    public clsUsuario getC1() {
        return c1;
    }

    /**
     * @param c1 the c1 to set
     */
    public void setC1(clsUsuario c1) {
        this.c1 = c1;
    }

    /**
     * @return the listado
     */
    public List<TblUsuarios> getListado() {
        return listado;
    }

    /**
     * @param listado the listado to set
     */
    public void setListado(List<TblUsuarios> listado) {
        this.listado = listado;
    }
    
}
