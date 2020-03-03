/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;
 

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelo.TblAreas;
import modelo.TblCantones;
import modelo.TblDiagnotiscos;
import modelo.TblOrigenes;
import modelo.TblPeriodos;
import modelo.TblTipoDiagnostico;
import modelo.TblZonas;

/**
 *
 * @author fabricio.diaz
 */
public class JsfListas {
    EntityManager em;
    private List<TblAreas> _areas;
    private List<TblCantones> _cantones;
    private List<TblOrigenes> _origenes;
    private List<TblPeriodos> _periodos;
    private List<TblZonas> _zonas;
    private List<TblZonas> _todasZonas;
    
    
    public JsfListas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        em = emf.createEntityManager();
        Query q1=em.createQuery("select c from TblAreas c  order by c.area");
       _areas= q1.getResultList();
        Query q2=em.createQuery("select c from TblCantones c  order by c.canton");
       _cantones= q2.getResultList();
       Query q3=em.createQuery("select c from TblOrigenes c  order by c.origen");
       _origenes= q3.getResultList();
       Query q4=em.createQuery("select c from TblPeriodos c  order by c.periodo");
       _periodos= q4.getResultList();

       Query q5=em.createQuery("select c from TblZonas c  order by c.zona");
       _todasZonas= q5.getResultList();
       _zonas=new ArrayList<>();

    }
    public TblTipoDiagnostico getDiagnostico(String codigo)
    {
        Query q=em.createQuery("select c from TblTipoDiagnostico c  where c.codigo=:p");
        q.setParameter("p", codigo);
        TblTipoDiagnostico t=new TblTipoDiagnostico();
        try
        {
            t=(TblTipoDiagnostico)q.getSingleResult();
        }catch(Exception e)
        {
            t.setDiagnostico("ND");
        }
        return t;
        
    }

    /**
     * @param idcan
     */
    public void filtraZonas(String idcan)
    {
        int idc=Integer.valueOf(idcan);
        _zonas=_todasZonas.stream().filter(p->p.getIdcanton()==idc).collect(Collectors.toList());
        
    }
    public List<TblAreas> getAreas() {
        return _areas;
    }

    /**
     * @param _areas the _areas to set
     */
    public void setAreas(List<TblAreas> _areas) {
        this._areas = _areas;
    }

    /**
     * @return the _cantones
     */
    public List<TblCantones> getCantones() {
        return _cantones;
    }

    /**
     * @param _cantones the _cantones to set
     */
    public void setCantones(List<TblCantones> _cantones) {
        this._cantones = _cantones;
    }

    /**
     * @return the _origenes
     */
    public List<TblOrigenes> getOrigenes() {
        return _origenes;
    }

    /**
     * @param _origenes the _origenes to set
     */
    public void setOrigenes(List<TblOrigenes> _origenes) {
        this._origenes = _origenes;
    }

    /**
     * @return the _periodos
     */
    public List<TblPeriodos> getPeriodos() {
        return _periodos;
    }

    /**
     * @param _periodos the _periodos to set
     */
    public void setPeriodos(List<TblPeriodos> _periodos) {
        this._periodos = _periodos;
    }

    /**
     * @return the _zonas
     */
    public List<TblZonas> getZonas() {
        return _zonas;
    }

    /**
     * @param _zonas the _zonas to set
     */
    public void setZonas(List<TblZonas> _zonas) {
        this._zonas = _zonas;
    }
    
}
