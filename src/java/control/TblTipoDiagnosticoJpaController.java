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
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.TblDiagnotiscos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.TblTipoDiagnostico;

/**
 *
 * @author fabricio.diaz
 */
public class TblTipoDiagnosticoJpaController implements Serializable {

    public TblTipoDiagnosticoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblTipoDiagnostico tblTipoDiagnostico) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblTipoDiagnostico.getTblDiagnotiscosCollection() == null) {
            tblTipoDiagnostico.setTblDiagnotiscosCollection(new ArrayList<TblDiagnotiscos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TblDiagnotiscos> attachedTblDiagnotiscosCollection = new ArrayList<TblDiagnotiscos>();
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscosToAttach : tblTipoDiagnostico.getTblDiagnotiscosCollection()) {
                tblDiagnotiscosCollectionTblDiagnotiscosToAttach = em.getReference(tblDiagnotiscosCollectionTblDiagnotiscosToAttach.getClass(), tblDiagnotiscosCollectionTblDiagnotiscosToAttach.getIdDiagnostico());
                attachedTblDiagnotiscosCollection.add(tblDiagnotiscosCollectionTblDiagnotiscosToAttach);
            }
            tblTipoDiagnostico.setTblDiagnotiscosCollection(attachedTblDiagnotiscosCollection);
            em.persist(tblTipoDiagnostico);
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscos : tblTipoDiagnostico.getTblDiagnotiscosCollection()) {
                TblTipoDiagnostico oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionTblDiagnotiscos = tblDiagnotiscosCollectionTblDiagnotiscos.getIdTipoDiagnostico();
                tblDiagnotiscosCollectionTblDiagnotiscos.setIdTipoDiagnostico(tblTipoDiagnostico);
                tblDiagnotiscosCollectionTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionTblDiagnotiscos);
                if (oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionTblDiagnotiscos != null) {
                    oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionTblDiagnotiscos.getTblDiagnotiscosCollection().remove(tblDiagnotiscosCollectionTblDiagnotiscos);
                    oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionTblDiagnotiscos = em.merge(oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionTblDiagnotiscos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblTipoDiagnostico(tblTipoDiagnostico.getIdTipoDiagnostico()) != null) {
                throw new PreexistingEntityException("TblTipoDiagnostico " + tblTipoDiagnostico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblTipoDiagnostico tblTipoDiagnostico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblTipoDiagnostico persistentTblTipoDiagnostico = em.find(TblTipoDiagnostico.class, tblTipoDiagnostico.getIdTipoDiagnostico());
            Collection<TblDiagnotiscos> tblDiagnotiscosCollectionOld = persistentTblTipoDiagnostico.getTblDiagnotiscosCollection();
            Collection<TblDiagnotiscos> tblDiagnotiscosCollectionNew = tblTipoDiagnostico.getTblDiagnotiscosCollection();
            Collection<TblDiagnotiscos> attachedTblDiagnotiscosCollectionNew = new ArrayList<TblDiagnotiscos>();
            for (TblDiagnotiscos tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach : tblDiagnotiscosCollectionNew) {
                tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach = em.getReference(tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach.getClass(), tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach.getIdDiagnostico());
                attachedTblDiagnotiscosCollectionNew.add(tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach);
            }
            tblDiagnotiscosCollectionNew = attachedTblDiagnotiscosCollectionNew;
            tblTipoDiagnostico.setTblDiagnotiscosCollection(tblDiagnotiscosCollectionNew);
            tblTipoDiagnostico = em.merge(tblTipoDiagnostico);
            for (TblDiagnotiscos tblDiagnotiscosCollectionOldTblDiagnotiscos : tblDiagnotiscosCollectionOld) {
                if (!tblDiagnotiscosCollectionNew.contains(tblDiagnotiscosCollectionOldTblDiagnotiscos)) {
                    tblDiagnotiscosCollectionOldTblDiagnotiscos.setIdTipoDiagnostico(null);
                    tblDiagnotiscosCollectionOldTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionOldTblDiagnotiscos);
                }
            }
            for (TblDiagnotiscos tblDiagnotiscosCollectionNewTblDiagnotiscos : tblDiagnotiscosCollectionNew) {
                if (!tblDiagnotiscosCollectionOld.contains(tblDiagnotiscosCollectionNewTblDiagnotiscos)) {
                    TblTipoDiagnostico oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionNewTblDiagnotiscos = tblDiagnotiscosCollectionNewTblDiagnotiscos.getIdTipoDiagnostico();
                    tblDiagnotiscosCollectionNewTblDiagnotiscos.setIdTipoDiagnostico(tblTipoDiagnostico);
                    tblDiagnotiscosCollectionNewTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionNewTblDiagnotiscos);
                    if (oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionNewTblDiagnotiscos != null && !oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionNewTblDiagnotiscos.equals(tblTipoDiagnostico)) {
                        oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionNewTblDiagnotiscos.getTblDiagnotiscosCollection().remove(tblDiagnotiscosCollectionNewTblDiagnotiscos);
                        oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionNewTblDiagnotiscos = em.merge(oldIdTipoDiagnosticoOfTblDiagnotiscosCollectionNewTblDiagnotiscos);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblTipoDiagnostico.getIdTipoDiagnostico();
                if (findTblTipoDiagnostico(id) == null) {
                    throw new NonexistentEntityException("The tblTipoDiagnostico with id " + id + " no longer exists.");
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
            TblTipoDiagnostico tblTipoDiagnostico;
            try {
                tblTipoDiagnostico = em.getReference(TblTipoDiagnostico.class, id);
                tblTipoDiagnostico.getIdTipoDiagnostico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblTipoDiagnostico with id " + id + " no longer exists.", enfe);
            }
            Collection<TblDiagnotiscos> tblDiagnotiscosCollection = tblTipoDiagnostico.getTblDiagnotiscosCollection();
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscos : tblDiagnotiscosCollection) {
                tblDiagnotiscosCollectionTblDiagnotiscos.setIdTipoDiagnostico(null);
                tblDiagnotiscosCollectionTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionTblDiagnotiscos);
            }
            em.remove(tblTipoDiagnostico);
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

    public List<TblTipoDiagnostico> findTblTipoDiagnosticoEntities() {
        return findTblTipoDiagnosticoEntities(true, -1, -1);
    }

    public List<TblTipoDiagnostico> findTblTipoDiagnosticoEntities(int maxResults, int firstResult) {
        return findTblTipoDiagnosticoEntities(false, maxResults, firstResult);
    }

    private List<TblTipoDiagnostico> findTblTipoDiagnosticoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblTipoDiagnostico.class));
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

    public TblTipoDiagnostico findTblTipoDiagnostico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblTipoDiagnostico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblTipoDiagnosticoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblTipoDiagnostico> rt = cq.from(TblTipoDiagnostico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
