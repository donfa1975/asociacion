/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author fabricio.diaz
 */
public class clsDBX {
EntityManager em;
    public clsDBX() {
        
    }
    public String insertar(Object T)
    {String r="";
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        em=emf.createEntityManager();
        try {
                UserTransaction t = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
		t.begin();
		em.joinTransaction();
		em.persist(T);
		t.commit();
		em.close();
		r="Ok";
                emf.close();
            } catch (Exception ex) {
		  r=ex.getMessage();
            }
          
          return r;
    }
    public String eliminar(Object T,int ID)
    {String r="";
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        
        em=emf.createEntityManager();
        try {
                UserTransaction t = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
		t.begin();
		em.joinTransaction();
		em.remove( em.find(T.getClass(),ID ) );
                
		t.commit();
		em.close();
		r="Ok";
                emf.close();
            } catch (Exception ex) {
		  r=ex.getMessage();
            }
          
          return r;
    }
    public String ejecutar(String consulta)
    {//"insert into risk_tMonitoreo (idRiesgo,mecanismo,idResponsable,periodicidad) values (?,?,?,?)"
        String r="Ok";
        Query q = em.createNativeQuery(consulta);
		try {
			UserTransaction t = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			em.joinTransaction();
			q.executeUpdate();
			t.commit();
                        em.close();
                } catch (Exception ex) {
			r=ex.getMessage();
		} 
		return r;        
    }
}
