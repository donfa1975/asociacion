/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import control.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.TblArcPac;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.TblAreas;
import modelo.TblPacientes;

/**
 *
 * @author fabricio.diaz
 */
public class TblAreasJpaController implements Serializable {

    public TblAreasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblAreas tblAreas) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblAreas.getTblArcPacCollection() == null) {
            tblAreas.setTblArcPacCollection(new ArrayList<TblArcPac>());
        }
        if (tblAreas.getTblPacientesCollection() == null) {
            tblAreas.setTblPacientesCollection(new ArrayList<TblPacientes>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TblArcPac> attachedTblArcPacCollection = new ArrayList<TblArcPac>();
            for (TblArcPac tblArcPacCollectionTblArcPacToAttach : tblAreas.getTblArcPacCollection()) {
                tblArcPacCollectionTblArcPacToAttach = em.getReference(tblArcPacCollectionTblArcPacToAttach.getClass(), tblArcPacCollectionTblArcPacToAttach.getIdArPac());
                attachedTblArcPacCollection.add(tblArcPacCollectionTblArcPacToAttach);
            }
            tblAreas.setTblArcPacCollection(attachedTblArcPacCollection);
            Collection<TblPacientes> attachedTblPacientesCollection = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionTblPacientesToAttach : tblAreas.getTblPacientesCollection()) {
                tblPacientesCollectionTblPacientesToAttach = em.getReference(tblPacientesCollectionTblPacientesToAttach.getClass(), tblPacientesCollectionTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollection.add(tblPacientesCollectionTblPacientesToAttach);
            }
            tblAreas.setTblPacientesCollection(attachedTblPacientesCollection);
            em.persist(tblAreas);
            for (TblArcPac tblArcPacCollectionTblArcPac : tblAreas.getTblArcPacCollection()) {
                TblAreas oldIdAreaOfTblArcPacCollectionTblArcPac = tblArcPacCollectionTblArcPac.getIdArea();
                tblArcPacCollectionTblArcPac.setIdArea(tblAreas);
                tblArcPacCollectionTblArcPac = em.merge(tblArcPacCollectionTblArcPac);
                if (oldIdAreaOfTblArcPacCollectionTblArcPac != null) {
                    oldIdAreaOfTblArcPacCollectionTblArcPac.getTblArcPacCollection().remove(tblArcPacCollectionTblArcPac);
                    oldIdAreaOfTblArcPacCollectionTblArcPac = em.merge(oldIdAreaOfTblArcPacCollectionTblArcPac);
                }
            }
            for (TblPacientes tblPacientesCollectionTblPacientes : tblAreas.getTblPacientesCollection()) {
                TblAreas oldIdAreaOfTblPacientesCollectionTblPacientes = tblPacientesCollectionTblPacientes.getIdArea();
                tblPacientesCollectionTblPacientes.setIdArea(tblAreas);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
                if (oldIdAreaOfTblPacientesCollectionTblPacientes != null) {
                    oldIdAreaOfTblPacientesCollectionTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionTblPacientes);
                    oldIdAreaOfTblPacientesCollectionTblPacientes = em.merge(oldIdAreaOfTblPacientesCollectionTblPacientes);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblAreas(tblAreas.getIdArea()) != null) {
                throw new PreexistingEntityException("TblAreas " + tblAreas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblAreas tblAreas) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblAreas persistentTblAreas = em.find(TblAreas.class, tblAreas.getIdArea());
            Collection<TblArcPac> tblArcPacCollectionOld = persistentTblAreas.getTblArcPacCollection();
            Collection<TblArcPac> tblArcPacCollectionNew = tblAreas.getTblArcPacCollection();
            Collection<TblPacientes> tblPacientesCollectionOld = persistentTblAreas.getTblPacientesCollection();
            Collection<TblPacientes> tblPacientesCollectionNew = tblAreas.getTblPacientesCollection();
            List<String> illegalOrphanMessages = null;
            for (TblArcPac tblArcPacCollectionOldTblArcPac : tblArcPacCollectionOld) {
                if (!tblArcPacCollectionNew.contains(tblArcPacCollectionOldTblArcPac)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblArcPac " + tblArcPacCollectionOldTblArcPac + " since its idArea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TblArcPac> attachedTblArcPacCollectionNew = new ArrayList<TblArcPac>();
            for (TblArcPac tblArcPacCollectionNewTblArcPacToAttach : tblArcPacCollectionNew) {
                tblArcPacCollectionNewTblArcPacToAttach = em.getReference(tblArcPacCollectionNewTblArcPacToAttach.getClass(), tblArcPacCollectionNewTblArcPacToAttach.getIdArPac());
                attachedTblArcPacCollectionNew.add(tblArcPacCollectionNewTblArcPacToAttach);
            }
            tblArcPacCollectionNew = attachedTblArcPacCollectionNew;
            tblAreas.setTblArcPacCollection(tblArcPacCollectionNew);
            Collection<TblPacientes> attachedTblPacientesCollectionNew = new ArrayList<TblPacientes>();
            for (TblPacientes tblPacientesCollectionNewTblPacientesToAttach : tblPacientesCollectionNew) {
                tblPacientesCollectionNewTblPacientesToAttach = em.getReference(tblPacientesCollectionNewTblPacientesToAttach.getClass(), tblPacientesCollectionNewTblPacientesToAttach.getIdPaciente());
                attachedTblPacientesCollectionNew.add(tblPacientesCollectionNewTblPacientesToAttach);
            }
            tblPacientesCollectionNew = attachedTblPacientesCollectionNew;
            tblAreas.setTblPacientesCollection(tblPacientesCollectionNew);
            tblAreas = em.merge(tblAreas);
            for (TblArcPac tblArcPacCollectionNewTblArcPac : tblArcPacCollectionNew) {
                if (!tblArcPacCollectionOld.contains(tblArcPacCollectionNewTblArcPac)) {
                    TblAreas oldIdAreaOfTblArcPacCollectionNewTblArcPac = tblArcPacCollectionNewTblArcPac.getIdArea();
                    tblArcPacCollectionNewTblArcPac.setIdArea(tblAreas);
                    tblArcPacCollectionNewTblArcPac = em.merge(tblArcPacCollectionNewTblArcPac);
                    if (oldIdAreaOfTblArcPacCollectionNewTblArcPac != null && !oldIdAreaOfTblArcPacCollectionNewTblArcPac.equals(tblAreas)) {
                        oldIdAreaOfTblArcPacCollectionNewTblArcPac.getTblArcPacCollection().remove(tblArcPacCollectionNewTblArcPac);
                        oldIdAreaOfTblArcPacCollectionNewTblArcPac = em.merge(oldIdAreaOfTblArcPacCollectionNewTblArcPac);
                    }
                }
            }
            for (TblPacientes tblPacientesCollectionOldTblPacientes : tblPacientesCollectionOld) {
                if (!tblPacientesCollectionNew.contains(tblPacientesCollectionOldTblPacientes)) {
                    tblPacientesCollectionOldTblPacientes.setIdArea(null);
                    tblPacientesCollectionOldTblPacientes = em.merge(tblPacientesCollectionOldTblPacientes);
                }
            }
            for (TblPacientes tblPacientesCollectionNewTblPacientes : tblPacientesCollectionNew) {
                if (!tblPacientesCollectionOld.contains(tblPacientesCollectionNewTblPacientes)) {
                    TblAreas oldIdAreaOfTblPacientesCollectionNewTblPacientes = tblPacientesCollectionNewTblPacientes.getIdArea();
                    tblPacientesCollectionNewTblPacientes.setIdArea(tblAreas);
                    tblPacientesCollectionNewTblPacientes = em.merge(tblPacientesCollectionNewTblPacientes);
                    if (oldIdAreaOfTblPacientesCollectionNewTblPacientes != null && !oldIdAreaOfTblPacientesCollectionNewTblPacientes.equals(tblAreas)) {
                        oldIdAreaOfTblPacientesCollectionNewTblPacientes.getTblPacientesCollection().remove(tblPacientesCollectionNewTblPacientes);
                        oldIdAreaOfTblPacientesCollectionNewTblPacientes = em.merge(oldIdAreaOfTblPacientesCollectionNewTblPacientes);
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
                Integer id = tblAreas.getIdArea();
                if (findTblAreas(id) == null) {
                    throw new NonexistentEntityException("The tblAreas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblAreas tblAreas;
            try {
                tblAreas = em.getReference(TblAreas.class, id);
                tblAreas.getIdArea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblAreas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblArcPac> tblArcPacCollectionOrphanCheck = tblAreas.getTblArcPacCollection();
            for (TblArcPac tblArcPacCollectionOrphanCheckTblArcPac : tblArcPacCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblAreas (" + tblAreas + ") cannot be destroyed since the TblArcPac " + tblArcPacCollectionOrphanCheckTblArcPac + " in its tblArcPacCollection field has a non-nullable idArea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TblPacientes> tblPacientesCollection = tblAreas.getTblPacientesCollection();
            for (TblPacientes tblPacientesCollectionTblPacientes : tblPacientesCollection) {
                tblPacientesCollectionTblPacientes.setIdArea(null);
                tblPacientesCollectionTblPacientes = em.merge(tblPacientesCollectionTblPacientes);
            }
            em.remove(tblAreas);
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

    public List<TblAreas> findTblAreasEntities() {
        return findTblAreasEntities(true, -1, -1);
    }

    public List<TblAreas> findTblAreasEntities(int maxResults, int firstResult) {
        return findTblAreasEntities(false, maxResults, firstResult);
    }

    private List<TblAreas> findTblAreasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblAreas.class));
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

    public TblAreas findTblAreas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblAreas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblAreasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblAreas> rt = cq.from(TblAreas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
