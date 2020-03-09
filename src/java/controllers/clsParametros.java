/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import modelo.*;

/**
 *
 * @author fabricio.diaz
 */
public class clsParametros {
	
	private List<TblAreas> _areas;
	private List<TblCantones> _cantones;
	private List<TblOrigenes> _origenes;
	private List<TblZonas> _zonas;
	private List<TblTipoDiagnostico> _tipoDiag;
	
	
	
	
	public clsParametros() {
		loadData();
	}
	
	public void loadData()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        EntityManager em = emf.createEntityManager();
		
		Query q1=em.createQuery("select c from TblAreas c order by c.area");
		_areas=q1.getResultList();
		
		Query q2=em.createQuery("select c from TblCantones c order by c.canton");
		_cantones=q2.getResultList();
		
		Query q3=em.createQuery("select c from TblOrigenes c order by c.origen");
		_origenes=q3.getResultList();
		
		Query q4=em.createQuery("select c from TblZonas c order by c.zona");
		_zonas=q4.getResultList();
		
		Query q5=em.createQuery("select c from TblTipoDiagnostico c order by c.codigo");
		_tipoDiag=q5.getResultList();
		
		
		
	}
	//////////////////////////////////
	public TblAreas getMiArea(int idarea)
	{
		
		TblAreas ar=_areas.stream().filter(a -> idarea== a.getIdArea()).findAny().orElse(null);
		
		return ar;
	}
	public void addObjeto(Object o)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        EntityManager em = emf.createEntityManager();
		try {
			
			UserTransaction t = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			t.begin();
			em.joinTransaction();
			
			em.persist(o);
			t.commit();
			em.close();
			emf.close();
			} catch (Exception ex) {
			
		}
	}
	//////////////////////////////////
	public void updateDatos(int k, String nuevo,int opcion)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        EntityManager em = emf.createEntityManager();
		Query q=null;
		
		switch(opcion)
		{
			case 1:
				q=em.createQuery("UPDATE TblAreas c set c.area=:p1 where c.idArea=:p2");
				q.setParameter("p1", nuevo);
				q.setParameter("p2", k);break;
			case 2:
				q=em.createQuery("UPDATE TblCantones c set c.canton=:p1 where c.idcanton=:p2");
				q.setParameter("p1", nuevo);
				q.setParameter("p2", k);break;
			case 3:
				q=em.createQuery("UPDATE TblOrigenes c set c.origen=:p1 where c.idorigen=:p2");
				q.setParameter("p1", nuevo);
				q.setParameter("p2", k);break;	
			case 4:
				q=em.createQuery("UPDATE TblZonas c set c.zona=:p1 where c.zona=:p2");
				q.setParameter("p1", nuevo);
				q.setParameter("p2", k);break;	
				
				
		}
		
		
		
		try {
			
			UserTransaction t = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			t.begin();
			
			em.joinTransaction();
			
			q.executeUpdate();
				
			t.commit();
			em.close();
			emf.close();
			} catch (Exception ex) {
			
		}
	}
	//////////////////////////////////
	/*public String addArea(TblAreas area)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JP2PU");
        EntityManager em = emf.createEntityManager();
		
		try {
			area.setIdArea(0);
			UserTransaction t = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			t.begin();
			em.joinTransaction();
			
			em.persist(area);
			t.commit();
			em.close();
			emf.close();
			} catch (Exception ex) {
			return ex.getMessage();
		}
		return "ok";
	}*/
	
//<editor-fold desc="propiedades">
	public List<TblAreas> getAreas() {
		return _areas;
	}

	public void setAreas(List<TblAreas> _areas) {
		this._areas = _areas;
	}

	public List<TblCantones> getCantones() {
		return _cantones;
	}

	public void setCantones(List<TblCantones> _cantones) {
		this._cantones = _cantones;
	}

	public List<TblOrigenes> getOrigenes() {
		return _origenes;
	}

	public void setOrigenes(List<TblOrigenes> _origenes) {
		this._origenes = _origenes;
	}

	public List<TblZonas> getZonas() {
		return _zonas;
	}

	public void setZonas(List<TblZonas> _zonas) {
		this._zonas = _zonas;
	}

	public List<TblTipoDiagnostico> getTipoDiag() {
		return _tipoDiag;
	}

	public void setTipoDiag(List<TblTipoDiagnostico> _tipoDiag) {
		this._tipoDiag = _tipoDiag;
	}
	
	

//</editor-fold>
}
