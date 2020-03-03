/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import control.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import modelo.TblSetupRep;

/**
 *
 * @author fabricio.diaz
 */
public class TblSetupRepJpaController implements Serializable {

    public TblSetupRepJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblSetupRep tblSetupRep) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(tblSetupRep);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblSetupRep(tblSetupRep.getIdSetup()) != null) {
                throw new PreexistingEntityException("TblSetupRep " + tblSetupRep + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblSetupRep tblSetupRep) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            tblSetupRep = em.merge(tblSetupRep);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblSetupRep.getIdSetup();
                if (findTblSetupRep(id) == null) {
                    throw new NonexistentEntityException("The tblSetupRep with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblSetupRep tblSetupRep;
            try {
                tblSetupRep = em.getReference(TblSetupRep.class, id);
                tblSetupRep.getIdSetup();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblSetupRep with id " + id + " no longer exists.", enfe);
            }
            em.remove(tblSetupRep);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TblSetupRep> findTblSetupRepEntities() {
        return findTblSetupRepEntities(true, -1, -1);
    }

    public List<TblSetupRep> findTblSetupRepEntities(int maxResults, int firstResult) {
        return findTblSetupRepEntities(false, maxResults, firstResult);
    }

    private List<TblSetupRep> findTblSetupRepEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblSetupRep.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TblSetupRep findTblSetupRep(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblSetupRep.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblSetupRepCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblSetupRep> rt = cq.from(TblSetupRep.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
