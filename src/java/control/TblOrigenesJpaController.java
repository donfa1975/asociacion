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
import modelo.TblOrigenes;

/**
 *
 * @author fabricio.diaz
 */
public class TblOrigenesJpaController implements Serializable {

    public TblOrigenesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblOrigenes tblOrigenes) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblOrigenes.getTblPacientesCollection() == null) {
            tblOrigenes.setTblPacientesCollection(new ArrayList<TblPacientes>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TblPacientes> attachedTblPacientesCollection = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionTblPacientesToAttach : tblOrigenes.getTblPacientesCollection()) {
                tblPacientesCollectionTblPacientesToAttach = em.getReference(tblPacientesCollectionTblPacientesToAttach.getClass(), tblPacientesCollectionTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollection.add(tblPacientesCollectionTblPacientesToAttach);
            }
            tblOrigenes.setTblPacientesCollection(attachedTblPacientesCollection);
            em.persist(tblOrigenes);
            for (TblPacientes tblPacientesCollectionTblPacientes : tblOrigenes.getTblPacientesCollection()) {
                TblOrigenes oldIdOrigenOfTblPacientesCollectionTblPacientes = tblPacientesCollectionTblPacientes.getIdOrigen();
                tblPacientesCollectionTblPacientes.setIdOrigen(tblOrigenes);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
                if (oldIdOrigenOfTblPacientesCollectionTblPacientes != null) {
                    oldIdOrigenOfTblPacientesCollectionTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionTblPacientes);
                    oldIdOrigenOfTblPacientesCollectionTblPacientes = em.merge(oldIdOrigenOfTblPacientesCollectionTblPacientes);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblOrigenes(tblOrigenes.getIdorigen()) != null) {
                throw new PreexistingEntityException("TblOrigenes " + tblOrigenes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblOrigenes tblOrigenes) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblOrigenes persistentTblOrigenes = em.find(TblOrigenes.class, tblOrigenes.getIdorigen());
            Collection<TblPacientes> tblPacientesCollectionOld = persistentTblOrigenes.getTblPacientesCollection();
            Collection<TblPacientes> tblPacientesCollectionNew = tblOrigenes.getTblPacientesCollection();
            Collection<TblPacientes> attachedTblPacientesCollectionNew = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionNewTblPacientesToAttach : tblPacientesCollectionNew) {
                tblPacientesCollectionNewTblPacientesToAttach = em.getReference(tblPacientesCollectionNewTblPacientesToAttach.getClass(), tblPacientesCollectionNewTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollectionNew.add(tblPacientesCollectionNewTblPacientesToAttach);
            }
            tblPacientesCollectionNew = attachedTblPacientesCollectionNew;
            tblOrigenes.setTblPacientesCollection(tblPacientesCollectionNew);
            tblOrigenes = em.merge(tblOrigenes);
            for (TblPacientes tblPacientesCollectionOldTblPacientes : tblPacientesCollectionOld) {
                if (!tblPacientesCollectionNew.contains(tblPacientesCollectionOldTblPacientes)) {
                    tblPacientesCollectionOldTblPacientes.setIdOrigen(null);
                    tblPacientesCollectionOldTblPacientes = em.merge(tblPacientesCollectionOldTblPacientes);
                }
            }
            for (TblPacientes tblPacientesCollectionNewTblPacientes : tblPacientesCollectionNew) {
                if (!tblPacientesCollectionOld.contains(tblPacientesCollectionNewTblPacientes)) {
                    TblOrigenes oldIdOrigenOfTblPacientesCollectionNewTblPacientes = tblPacientesCollectionNewTblPacientes.getIdOrigen();
                    tblPacientesCollectionNewTblPacientes.setIdOrigen(tblOrigenes);
                    tblPacientesCollectionNewTblPacientes = em.merge(tblPacientesCollectionNewTblPacientes);
                    if (oldIdOrigenOfTblPacientesCollectionNewTblPacientes != null && !oldIdOrigenOfTblPacientesCollectionNewTblPacientes.equals(tblOrigenes)) {
                        oldIdOrigenOfTblPacientesCollectionNewTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionNewTblPacientes);
                        oldIdOrigenOfTblPacientesCollectionNewTblPacientes = em.merge(oldIdOrigenOfTblPacientesCollectionNewTblPacientes);
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
                Integer id = tblOrigenes.getIdorigen();
                if (findTblOrigenes(id) == null) {
                    throw new NonexistentEntityException("The tblOrigenes with id " + id + " no longer exists.");
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
            TblOrigenes tblOrigenes;
            try {
                tblOrigenes = em.getReference(TblOrigenes.class, id);
                tblOrigenes.getIdorigen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblOrigenes with id " + id + " no longer exists.", enfe);
            }
            Collection<TblPacientes> tblPacientesCollection = tblOrigenes.getTblPacientesCollection();
            for (TblPacientes tblPacientesCollectionTblPacientes : tblPacientesCollection) {
                tblPacientesCollectionTblPacientes.setIdOrigen(null);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
            }
            em.remove(tblOrigenes);
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

    public List<TblOrigenes> findTblOrigenesEntities() {
        return findTblOrigenesEntities(true, -1, -1);
    }

    public List<TblOrigenes> findTblOrigenesEntities(int maxResults, int firstResult) {
        return findTblOrigenesEntities(false, maxResults, firstResult);
    }

    private List<TblOrigenes> findTblOrigenesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblOrigenes.class));
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

    public TblOrigenes findTblOrigenes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblOrigenes.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblOrigenesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblOrigenes> rt = cq.from(TblOrigenes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
