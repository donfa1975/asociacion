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
import modelo.TblDiagnotiscos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.TblMatriculas;
import modelo.TblUsuarios;

/**
 *
 * @author fabricio.diaz
 */
public class TblUsuariosJpaController implements Serializable {

    public TblUsuariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblUsuarios tblUsuarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblUsuarios.getTblDiagnotiscosCollection() == null) {
            tblUsuarios.setTblDiagnotiscosCollection(new ArrayList<TblDiagnotiscos>());
        }
        if (tblUsuarios.getTblMatriculasCollection() == null) {
            tblUsuarios.setTblMatriculasCollection(new ArrayList<TblMatriculas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TblDiagnotiscos> attachedTblDiagnotiscosCollection = new ArrayList<TblDiagnotiscos>();
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscosToAttach : tblUsuarios.getTblDiagnotiscosCollection()) {
                tblDiagnotiscosCollectionTblDiagnotiscosToAttach = em.getReference(tblDiagnotiscosCollectionTblDiagnotiscosToAttach.getClass(), tblDiagnotiscosCollectionTblDiagnotiscosToAttach.getIdDiagnostico());
                attachedTblDiagnotiscosCollection.add(tblDiagnotiscosCollectionTblDiagnotiscosToAttach);
            }
            tblUsuarios.setTblDiagnotiscosCollection(attachedTblDiagnotiscosCollection);
            Collection<TblMatriculas> attachedTblMatriculasCollection = new ArrayList<TblMatriculas>();
            for (TblMatriculas tblMatriculasCollectionTblMatriculasToAttach : tblUsuarios.getTblMatriculasCollection()) {
                tblMatriculasCollectionTblMatriculasToAttach = em.getReference(tblMatriculasCollectionTblMatriculasToAttach.getClass(), tblMatriculasCollectionTblMatriculasToAttach.getIdMatricula());
                attachedTblMatriculasCollection.add(tblMatriculasCollectionTblMatriculasToAttach);
            }
            tblUsuarios.setTblMatriculasCollection(attachedTblMatriculasCollection);
            em.persist(tblUsuarios);
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscos : tblUsuarios.getTblDiagnotiscosCollection()) {
                TblUsuarios oldIdusuarioOfTblDiagnotiscosCollectionTblDiagnotiscos = tblDiagnotiscosCollectionTblDiagnotiscos.getIdusuario();
                tblDiagnotiscosCollectionTblDiagnotiscos.setIdusuario(tblUsuarios);
                tblDiagnotiscosCollectionTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionTblDiagnotiscos);
                if (oldIdusuarioOfTblDiagnotiscosCollectionTblDiagnotiscos != null) {
                    oldIdusuarioOfTblDiagnotiscosCollectionTblDiagnotiscos.getTblDiagnotiscosCollection().remove(tblDiagnotiscosCollectionTblDiagnotiscos);
                    oldIdusuarioOfTblDiagnotiscosCollectionTblDiagnotiscos = em.merge(oldIdusuarioOfTblDiagnotiscosCollectionTblDiagnotiscos);
                }
            }
            for (TblMatriculas tblMatriculasCollectionTblMatriculas : tblUsuarios.getTblMatriculasCollection()) {
                TblUsuarios oldIdusuarioOfTblMatriculasCollectionTblMatriculas = tblMatriculasCollectionTblMatriculas.getIdusuario();
                tblMatriculasCollectionTblMatriculas.setIdusuario(tblUsuarios);
                tblMatriculasCollectionTblMatriculas = em.merge(tblMatriculasCollectionTblMatriculas);
                if (oldIdusuarioOfTblMatriculasCollectionTblMatriculas != null) {
                    oldIdusuarioOfTblMatriculasCollectionTblMatriculas.getTblMatriculasCollection().remove(tblMatriculasCollectionTblMatriculas);
                    oldIdusuarioOfTblMatriculasCollectionTblMatriculas = em.merge(oldIdusuarioOfTblMatriculasCollectionTblMatriculas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblUsuarios(tblUsuarios.getIdUsuario()) != null) {
                throw new PreexistingEntityException("TblUsuarios " + tblUsuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblUsuarios tblUsuarios) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblUsuarios persistentTblUsuarios = em.find(TblUsuarios.class, tblUsuarios.getIdUsuario());
            Collection<TblDiagnotiscos> tblDiagnotiscosCollectionOld = persistentTblUsuarios.getTblDiagnotiscosCollection();
            Collection<TblDiagnotiscos> tblDiagnotiscosCollectionNew = tblUsuarios.getTblDiagnotiscosCollection();
            Collection<TblMatriculas> tblMatriculasCollectionOld = persistentTblUsuarios.getTblMatriculasCollection();
            Collection<TblMatriculas> tblMatriculasCollectionNew = tblUsuarios.getTblMatriculasCollection();
            List<String> illegalOrphanMessages = null;
            for (TblMatriculas tblMatriculasCollectionOldTblMatriculas : tblMatriculasCollectionOld) {
                if (!tblMatriculasCollectionNew.contains(tblMatriculasCollectionOldTblMatriculas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblMatriculas " + tblMatriculasCollectionOldTblMatriculas + " since its idusuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TblDiagnotiscos> attachedTblDiagnotiscosCollectionNew = new ArrayList<TblDiagnotiscos>();
            for (TblDiagnotiscos tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach : tblDiagnotiscosCollectionNew) {
                tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach = em.getReference(tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach.getClass(), tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach.getIdDiagnostico());
                attachedTblDiagnotiscosCollectionNew.add(tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach);
            }
            tblDiagnotiscosCollectionNew = attachedTblDiagnotiscosCollectionNew;
            tblUsuarios.setTblDiagnotiscosCollection(tblDiagnotiscosCollectionNew);
            Collection<TblMatriculas> attachedTblMatriculasCollectionNew = new ArrayList<TblMatriculas>();
            for (TblMatriculas tblMatriculasCollectionNewTblMatriculasToAttach : tblMatriculasCollectionNew) {
                tblMatriculasCollectionNewTblMatriculasToAttach = em.getReference(tblMatriculasCollectionNewTblMatriculasToAttach.getClass(), tblMatriculasCollectionNewTblMatriculasToAttach.getIdMatricula());
                attachedTblMatriculasCollectionNew.add(tblMatriculasCollectionNewTblMatriculasToAttach);
            }
            tblMatriculasCollectionNew = attachedTblMatriculasCollectionNew;
            tblUsuarios.setTblMatriculasCollection(tblMatriculasCollectionNew);
            tblUsuarios = em.merge(tblUsuarios);
            for (TblDiagnotiscos tblDiagnotiscosCollectionOldTblDiagnotiscos : tblDiagnotiscosCollectionOld) {
                if (!tblDiagnotiscosCollectionNew.contains(tblDiagnotiscosCollectionOldTblDiagnotiscos)) {
                    tblDiagnotiscosCollectionOldTblDiagnotiscos.setIdusuario(null);
                    tblDiagnotiscosCollectionOldTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionOldTblDiagnotiscos);
                }
            }
            for (TblDiagnotiscos tblDiagnotiscosCollectionNewTblDiagnotiscos : tblDiagnotiscosCollectionNew) {
                if (!tblDiagnotiscosCollectionOld.contains(tblDiagnotiscosCollectionNewTblDiagnotiscos)) {
                    TblUsuarios oldIdusuarioOfTblDiagnotiscosCollectionNewTblDiagnotiscos = tblDiagnotiscosCollectionNewTblDiagnotiscos.getIdusuario();
                    tblDiagnotiscosCollectionNewTblDiagnotiscos.setIdusuario(tblUsuarios);
                    tblDiagnotiscosCollectionNewTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionNewTblDiagnotiscos);
                    if (oldIdusuarioOfTblDiagnotiscosCollectionNewTblDiagnotiscos != null && !oldIdusuarioOfTblDiagnotiscosCollectionNewTblDiagnotiscos.equals(tblUsuarios)) {
                        oldIdusuarioOfTblDiagnotiscosCollectionNewTblDiagnotiscos.getTblDiagnotiscosCollection().remove(tblDiagnotiscosCollectionNewTblDiagnotiscos);
                        oldIdusuarioOfTblDiagnotiscosCollectionNewTblDiagnotiscos = em.merge(oldIdusuarioOfTblDiagnotiscosCollectionNewTblDiagnotiscos);
                    }
                }
            }
            for (TblMatriculas tblMatriculasCollectionNewTblMatriculas : tblMatriculasCollectionNew) {
                if (!tblMatriculasCollectionOld.contains(tblMatriculasCollectionNewTblMatriculas)) {
                    TblUsuarios oldIdusuarioOfTblMatriculasCollectionNewTblMatriculas = tblMatriculasCollectionNewTblMatriculas.getIdusuario();
                    tblMatriculasCollectionNewTblMatriculas.setIdusuario(tblUsuarios);
                    tblMatriculasCollectionNewTblMatriculas = em.merge(tblMatriculasCollectionNewTblMatriculas);
                    if (oldIdusuarioOfTblMatriculasCollectionNewTblMatriculas != null && !oldIdusuarioOfTblMatriculasCollectionNewTblMatriculas.equals(tblUsuarios)) {
                        oldIdusuarioOfTblMatriculasCollectionNewTblMatriculas.getTblMatriculasCollection().remove(tblMatriculasCollectionNewTblMatriculas);
                        oldIdusuarioOfTblMatriculasCollectionNewTblMatriculas = em.merge(oldIdusuarioOfTblMatriculasCollectionNewTblMatriculas);
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
                Integer id = tblUsuarios.getIdUsuario();
                if (findTblUsuarios(id) == null) {
                    throw new NonexistentEntityException("The tblUsuarios with id " + id + " no longer exists.");
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
            TblUsuarios tblUsuarios;
            try {
                tblUsuarios = em.getReference(TblUsuarios.class, id);
                tblUsuarios.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblUsuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblMatriculas> tblMatriculasCollectionOrphanCheck = tblUsuarios.getTblMatriculasCollection();
            for (TblMatriculas tblMatriculasCollectionOrphanCheckTblMatriculas : tblMatriculasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblUsuarios (" + tblUsuarios + ") cannot be destroyed since the TblMatriculas " + tblMatriculasCollectionOrphanCheckTblMatriculas + " in its tblMatriculasCollection field has a non-nullable idusuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TblDiagnotiscos> tblDiagnotiscosCollection = tblUsuarios.getTblDiagnotiscosCollection();
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscos : tblDiagnotiscosCollection) {
                tblDiagnotiscosCollectionTblDiagnotiscos.setIdusuario(null);
                tblDiagnotiscosCollectionTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionTblDiagnotiscos);
            }
            em.remove(tblUsuarios);
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

    public List<TblUsuarios> findTblUsuariosEntities() {
        return findTblUsuariosEntities(true, -1, -1);
    }

    public List<TblUsuarios> findTblUsuariosEntities(int maxResults, int firstResult) {
        return findTblUsuariosEntities(false, maxResults, firstResult);
    }

    private List<TblUsuarios> findTblUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblUsuarios.class));
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

    public TblUsuarios findTblUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblUsuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblUsuarios> rt = cq.from(TblUsuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
