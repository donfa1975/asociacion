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
import modelo.TblCitas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.TblAcciones;

/**
 *
 * @author fabricio.diaz
 */
public class TblAccionesJpaController implements Serializable {

    public TblAccionesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblAcciones tblAcciones) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblAcciones.getTblCitasCollection() == null) {
            tblAcciones.setTblCitasCollection(new ArrayList<TblCitas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TblCitas> attachedTblCitasCollection = new ArrayList<TblCitas>();
            for (TblCitas tblCitasCollectionTblCitasToAttach : tblAcciones.getTblCitasCollection()) {
                tblCitasCollectionTblCitasToAttach = em.getReference(tblCitasCollectionTblCitasToAttach.getClass(), tblCitasCollectionTblCitasToAttach.getIdCita());
                attachedTblCitasCollection.add(tblCitasCollectionTblCitasToAttach);
            }
            tblAcciones.setTblCitasCollection(attachedTblCitasCollection);
            em.persist(tblAcciones);
            for (TblCitas tblCitasCollectionTblCitas : tblAcciones.getTblCitasCollection()) {
                TblAcciones oldIdAccionOfTblCitasCollectionTblCitas = tblCitasCollectionTblCitas.getIdAccion();
                tblCitasCollectionTblCitas.setIdAccion(tblAcciones);
                tblCitasCollectionTblCitas = em.merge(tblCitasCollectionTblCitas);
                if (oldIdAccionOfTblCitasCollectionTblCitas != null) {
                    oldIdAccionOfTblCitasCollectionTblCitas.getTblCitasCollection().remove(tblCitasCollectionTblCitas);
                    oldIdAccionOfTblCitasCollectionTblCitas = em.merge(oldIdAccionOfTblCitasCollectionTblCitas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblAcciones(tblAcciones.getIdAccion()) != null) {
                throw new PreexistingEntityException("TblAcciones " + tblAcciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblAcciones tblAcciones) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblAcciones persistentTblAcciones = em.find(TblAcciones.class, tblAcciones.getIdAccion());
            Collection<TblCitas> tblCitasCollectionOld = persistentTblAcciones.getTblCitasCollection();
            Collection<TblCitas> tblCitasCollectionNew = tblAcciones.getTblCitasCollection();
            Collection<TblCitas> attachedTblCitasCollectionNew = new ArrayList<TblCitas>();
            for (TblCitas tblCitasCollectionNewTblCitasToAttach : tblCitasCollectionNew) {
                tblCitasCollectionNewTblCitasToAttach = em.getReference(tblCitasCollectionNewTblCitasToAttach.getClass(), tblCitasCollectionNewTblCitasToAttach.getIdCita());
                attachedTblCitasCollectionNew.add(tblCitasCollectionNewTblCitasToAttach);
            }
            tblCitasCollectionNew = attachedTblCitasCollectionNew;
            tblAcciones.setTblCitasCollection(tblCitasCollectionNew);
            tblAcciones = em.merge(tblAcciones);
            for (TblCitas tblCitasCollectionOldTblCitas : tblCitasCollectionOld) {
                if (!tblCitasCollectionNew.contains(tblCitasCollectionOldTblCitas)) {
                    tblCitasCollectionOldTblCitas.setIdAccion(null);
                    tblCitasCollectionOldTblCitas = em.merge(tblCitasCollectionOldTblCitas);
                }
            }
            for (TblCitas tblCitasCollectionNewTblCitas : tblCitasCollectionNew) {
                if (!tblCitasCollectionOld.contains(tblCitasCollectionNewTblCitas)) {
                    TblAcciones oldIdAccionOfTblCitasCollectionNewTblCitas = tblCitasCollectionNewTblCitas.getIdAccion();
                    tblCitasCollectionNewTblCitas.setIdAccion(tblAcciones);
                    tblCitasCollectionNewTblCitas = em.merge(tblCitasCollectionNewTblCitas);
                    if (oldIdAccionOfTblCitasCollectionNewTblCitas != null && !oldIdAccionOfTblCitasCollectionNewTblCitas.equals(tblAcciones)) {
                        oldIdAccionOfTblCitasCollectionNewTblCitas.getTblCitasCollection().remove(tblCitasCollectionNewTblCitas);
                        oldIdAccionOfTblCitasCollectionNewTblCitas = em.merge(oldIdAccionOfTblCitasCollectionNewTblCitas);
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
                Integer id = tblAcciones.getIdAccion();
                if (findTblAcciones(id) == null) {
                    throw new NonexistentEntityException("The tblAcciones with id " + id + " no longer exists.");
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
            TblAcciones tblAcciones;
            try {
                tblAcciones = em.getReference(TblAcciones.class, id);
                tblAcciones.getIdAccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblAcciones with id " + id + " no longer exists.", enfe);
            }
            Collection<TblCitas> tblCitasCollection = tblAcciones.getTblCitasCollection();
            for (TblCitas tblCitasCollectionTblCitas : tblCitasCollection) {
                tblCitasCollectionTblCitas.setIdAccion(null);
                tblCitasCollectionTblCitas = em.merge(tblCitasCollectionTblCitas);
            }
            em.remove(tblAcciones);
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

    public List<TblAcciones> findTblAccionesEntities() {
        return findTblAccionesEntities(true, -1, -1);
    }

    public List<TblAcciones> findTblAccionesEntities(int maxResults, int firstResult) {
        return findTblAccionesEntities(false, maxResults, firstResult);
    }

    private List<TblAcciones> findTblAccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblAcciones.class));
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

    public TblAcciones findTblAcciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblAcciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblAccionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblAcciones> rt = cq.from(TblAcciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
