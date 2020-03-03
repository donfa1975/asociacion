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
import modelo.TblPacientes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.TblCantones;

/**
 *
 * @author fabricio.diaz
 */
public class TblCantonesJpaController implements Serializable {

    public TblCantonesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblCantones tblCantones) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblCantones.getTblPacientesCollection() == null) {
            tblCantones.setTblPacientesCollection(new ArrayList<TblPacientes>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TblPacientes> attachedTblPacientesCollection = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionTblPacientesToAttach : tblCantones.getTblPacientesCollection()) {
                tblPacientesCollectionTblPacientesToAttach = em.getReference(tblPacientesCollectionTblPacientesToAttach.getClass(), tblPacientesCollectionTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollection.add(tblPacientesCollectionTblPacientesToAttach);
            }
            tblCantones.setTblPacientesCollection(attachedTblPacientesCollection);
            em.persist(tblCantones);
            for (TblPacientes tblPacientesCollectionTblPacientes : tblCantones.getTblPacientesCollection()) {
                TblCantones oldIdCantonOfTblPacientesCollectionTblPacientes = tblPacientesCollectionTblPacientes.getIdCanton();
                tblPacientesCollectionTblPacientes.setIdCanton(tblCantones);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
                if (oldIdCantonOfTblPacientesCollectionTblPacientes != null) {
                    oldIdCantonOfTblPacientesCollectionTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionTblPacientes);
                    oldIdCantonOfTblPacientesCollectionTblPacientes = em.merge(oldIdCantonOfTblPacientesCollectionTblPacientes);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblCantones(tblCantones.getIdcanton()) != null) {
                throw new PreexistingEntityException("TblCantones " + tblCantones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblCantones tblCantones) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblCantones persistentTblCantones = em.find(TblCantones.class, tblCantones.getIdcanton());
            Collection<TblPacientes> tblPacientesCollectionOld = persistentTblCantones.getTblPacientesCollection();
            Collection<TblPacientes> tblPacientesCollectionNew = tblCantones.getTblPacientesCollection();
            Collection<TblPacientes> attachedTblPacientesCollectionNew = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionNewTblPacientesToAttach : tblPacientesCollectionNew) {
                tblPacientesCollectionNewTblPacientesToAttach = em.getReference(tblPacientesCollectionNewTblPacientesToAttach.getClass(), tblPacientesCollectionNewTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollectionNew.add(tblPacientesCollectionNewTblPacientesToAttach);
            }
            tblPacientesCollectionNew = attachedTblPacientesCollectionNew;
            tblCantones.setTblPacientesCollection(tblPacientesCollectionNew);
            tblCantones = em.merge(tblCantones);
            for (TblPacientes tblPacientesCollectionOldTblPacientes : tblPacientesCollectionOld) {
                if (!tblPacientesCollectionNew.contains(tblPacientesCollectionOldTblPacientes)) {
                    tblPacientesCollectionOldTblPacientes.setIdCanton(null);
                    tblPacientesCollectionOldTblPacientes = em.merge(tblPacientesCollectionOldTblPacientes);
                }
            }
            for (TblPacientes tblPacientesCollectionNewTblPacientes : tblPacientesCollectionNew) {
                if (!tblPacientesCollectionOld.contains(tblPacientesCollectionNewTblPacientes)) {
                    TblCantones oldIdCantonOfTblPacientesCollectionNewTblPacientes = tblPacientesCollectionNewTblPacientes.getIdCanton();
                    tblPacientesCollectionNewTblPacientes.setIdCanton(tblCantones);
                    tblPacientesCollectionNewTblPacientes = em.merge(tblPacientesCollectionNewTblPacientes);
                    if (oldIdCantonOfTblPacientesCollectionNewTblPacientes != null && !oldIdCantonOfTblPacientesCollectionNewTblPacientes.equals(tblCantones)) {
                        oldIdCantonOfTblPacientesCollectionNewTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionNewTblPacientes);
                        oldIdCantonOfTblPacientesCollectionNewTblPacientes = em.merge(oldIdCantonOfTblPacientesCollectionNewTblPacientes);
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
                Integer id = tblCantones.getIdcanton();
                if (findTblCantones(id) == null) {
                    throw new NonexistentEntityException("The tblCantones with id " + id + " no longer exists.");
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
            TblCantones tblCantones;
            try {
                tblCantones = em.getReference(TblCantones.class, id);
                tblCantones.getIdcanton();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblCantones with id " + id + " no longer exists.", enfe);
            }
            Collection<TblPacientes> tblPacientesCollection = tblCantones.getTblPacientesCollection();
            for (TblPacientes tblPacientesCollectionTblPacientes : tblPacientesCollection) {
                tblPacientesCollectionTblPacientes.setIdCanton(null);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
            }
            em.remove(tblCantones);
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

    public List<TblCantones> findTblCantonesEntities() {
        return findTblCantonesEntities(true, -1, -1);
    }

    public List<TblCantones> findTblCantonesEntities(int maxResults, int firstResult) {
        return findTblCantonesEntities(false, maxResults, firstResult);
    }

    private List<TblCantones> findTblCantonesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblCantones.class));
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

    public TblCantones findTblCantones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblCantones.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblCantonesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblCantones> rt = cq.from(TblCantones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
