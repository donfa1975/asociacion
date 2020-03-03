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
import modelo.TblZonas;

/**
 *
 * @author fabricio.diaz
 */
public class TblZonasJpaController implements Serializable {

    public TblZonasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblZonas tblZonas) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblZonas.getTblPacientesCollection() == null) {
            tblZonas.setTblPacientesCollection(new ArrayList<TblPacientes>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TblPacientes> attachedTblPacientesCollection = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionTblPacientesToAttach : tblZonas.getTblPacientesCollection()) {
                tblPacientesCollectionTblPacientesToAttach = em.getReference(tblPacientesCollectionTblPacientesToAttach.getClass(), tblPacientesCollectionTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollection.add(tblPacientesCollectionTblPacientesToAttach);
            }
            tblZonas.setTblPacientesCollection(attachedTblPacientesCollection);
            em.persist(tblZonas);
            for (TblPacientes tblPacientesCollectionTblPacientes : tblZonas.getTblPacientesCollection()) {
                TblZonas oldIdzonaOfTblPacientesCollectionTblPacientes = tblPacientesCollectionTblPacientes.getIdzona();
                tblPacientesCollectionTblPacientes.setIdzona(tblZonas);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
                if (oldIdzonaOfTblPacientesCollectionTblPacientes != null) {
                    oldIdzonaOfTblPacientesCollectionTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionTblPacientes);
                    oldIdzonaOfTblPacientesCollectionTblPacientes = em.merge(oldIdzonaOfTblPacientesCollectionTblPacientes);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblZonas(tblZonas.getIdZona()) != null) {
                throw new PreexistingEntityException("TblZonas " + tblZonas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblZonas tblZonas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblZonas persistentTblZonas = em.find(TblZonas.class, tblZonas.getIdZona());
            Collection<TblPacientes> tblPacientesCollectionOld = persistentTblZonas.getTblPacientesCollection();
            Collection<TblPacientes> tblPacientesCollectionNew = tblZonas.getTblPacientesCollection();
            Collection<TblPacientes> attachedTblPacientesCollectionNew = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionNewTblPacientesToAttach : tblPacientesCollectionNew) {
                tblPacientesCollectionNewTblPacientesToAttach = em.getReference(tblPacientesCollectionNewTblPacientesToAttach.getClass(), tblPacientesCollectionNewTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollectionNew.add(tblPacientesCollectionNewTblPacientesToAttach);
            }
            tblPacientesCollectionNew = attachedTblPacientesCollectionNew;
            tblZonas.setTblPacientesCollection(tblPacientesCollectionNew);
            tblZonas = em.merge(tblZonas);
            for (TblPacientes tblPacientesCollectionOldTblPacientes : tblPacientesCollectionOld) {
                if (!tblPacientesCollectionNew.contains(tblPacientesCollectionOldTblPacientes)) {
                    tblPacientesCollectionOldTblPacientes.setIdzona(null);
                    tblPacientesCollectionOldTblPacientes = em.merge(tblPacientesCollectionOldTblPacientes);
                }
            }
            for (TblPacientes tblPacientesCollectionNewTblPacientes : tblPacientesCollectionNew) {
                if (!tblPacientesCollectionOld.contains(tblPacientesCollectionNewTblPacientes)) {
                    TblZonas oldIdzonaOfTblPacientesCollectionNewTblPacientes = tblPacientesCollectionNewTblPacientes.getIdzona();
                    tblPacientesCollectionNewTblPacientes.setIdzona(tblZonas);
                    tblPacientesCollectionNewTblPacientes = em.merge(tblPacientesCollectionNewTblPacientes);
                    if (oldIdzonaOfTblPacientesCollectionNewTblPacientes != null && !oldIdzonaOfTblPacientesCollectionNewTblPacientes.equals(tblZonas)) {
                        oldIdzonaOfTblPacientesCollectionNewTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionNewTblPacientes);
                        oldIdzonaOfTblPacientesCollectionNewTblPacientes = em.merge(oldIdzonaOfTblPacientesCollectionNewTblPacientes);
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
                Integer id = tblZonas.getIdZona();
                if (findTblZonas(id) == null) {
                    throw new NonexistentEntityException("The tblZonas with id " + id + " no longer exists.");
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
            TblZonas tblZonas;
            try {
                tblZonas = em.getReference(TblZonas.class, id);
                tblZonas.getIdZona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblZonas with id " + id + " no longer exists.", enfe);
            }
            Collection<TblPacientes> tblPacientesCollection = tblZonas.getTblPacientesCollection();
            for (TblPacientes tblPacientesCollectionTblPacientes : tblPacientesCollection) {
                tblPacientesCollectionTblPacientes.setIdzona(null);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
            }
            em.remove(tblZonas);
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

    public List<TblZonas> findTblZonasEntities() {
        return findTblZonasEntities(true, -1, -1);
    }

    public List<TblZonas> findTblZonasEntities(int maxResults, int firstResult) {
        return findTblZonasEntities(false, maxResults, firstResult);
    }

    private List<TblZonas> findTblZonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblZonas.class));
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

    public TblZonas findTblZonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblZonas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblZonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblZonas> rt = cq.from(TblZonas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
