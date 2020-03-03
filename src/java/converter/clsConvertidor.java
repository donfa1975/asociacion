/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelo.TblAcciones;
//import javax.enterprise.inject.spi.CDI;
import modelo.TblAreas;
import modelo.TblCantones;
import modelo.TblDiagnotiscos;
import modelo.TblOrigenes;
import modelo.TblTipoDiagnostico;
import modelo.TblZonas;

/**
 *
 * @author fabricio.diaz
 */
public class clsConvertidor {
    EntityManager em;
    public clsConvertidor() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        em = emf.createEntityManager();
    }
    public TblAreas getArea(String pid)
    {
        int id=Integer.valueOf(pid);
        TblAreas area;
        Query q=em.createQuery("select c from TblAreas c where c.idArea=:p");
        q.setParameter("p", id);
        try{
        area=(TblAreas)q.getSingleResult();
                }
        catch(Exception e)
        {
            area=null;
        }
        return area;
        
    }
   public TblAcciones getAccion(int pid)
    {
        
        TblAcciones o;
        Query q=em.createQuery("select c from TblAcciones c where c.idAccion=:p");
        q.setParameter("p", pid);
        try{
        o=(TblAcciones)q.getSingleResult();
                }
        catch(Exception e)
        {
            o=null;
        }
        return o;
        
    }
    public TblCantones getCanton(String pid)
    {
        int id=Integer.valueOf(pid);
        TblCantones c;
        Query q=em.createQuery("select c from TblCantones c where c.idcanton=:p");
        q.setParameter("p", id);
        try{
        c=(TblCantones)q.getSingleResult();
                }
        catch(Exception e)
        {
            c=null;
        }
        return c;
        
    }
    public TblDiagnotiscos getDiagnostico(String pid)
    {
    int id=Integer.valueOf(pid);
        TblDiagnotiscos c;
        Query q=em.createQuery("select c from TblDiagnotiscos c where c.idDiagnostico=:p");
        q.setParameter("p", id);
        try{
        c=(TblDiagnotiscos)q.getSingleResult();
                }
        catch(Exception e)
        {
            c=null;
        }
        return c;
    }
    public TblOrigenes getOrigen(String pid)
    {
    int id=Integer.valueOf(pid);
        TblOrigenes c;
        Query q=em.createQuery("select c from TblOrigenes c where c.idorigen=:p");
        q.setParameter("p", id);
        try{
        c=(TblOrigenes)q.getSingleResult();
                }
        catch(Exception e)
        {
            c=null;
        }
        return c;
    }
    public TblTipoDiagnostico getTipoDiag(String pid)
    {
    int id=Integer.valueOf(pid);
        TblTipoDiagnostico c;
        Query q=em.createQuery("select c from TblTipoDiagnostico c where c.idTipoDiagnostico=:p");
        q.setParameter("p", id);
        try{
        c=(TblTipoDiagnostico)q.getSingleResult();
                }
        catch(Exception e)
        {
            c=null;
        }
        return c;
    
    }
    public TblZonas getZona(String pid)
    {
    int id=Integer.valueOf(pid);
        TblZonas c;
        Query q=em.createQuery("select c from TblZonas c where c.idZona=:p");
        q.setParameter("p", id);
        try{
        c=(TblZonas)q.getSingleResult();
                }
        catch(Exception e)
        {
            c=null;
        }
        return c;
    }
}
