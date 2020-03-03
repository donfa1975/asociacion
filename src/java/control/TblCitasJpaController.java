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
import modelo.TblAcciones;
import modelo.TblCitas;
import modelo.TblPacientes;

/**
 *
 * @author fabricio.diaz
 */
public class TblCitasJpaController implements Serializable {

    public TblCitasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblCitas tblCitas) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblAcciones idAccion = tblCitas.getIdAccion();
            if (idAccion != null) {
                idAccion = em.getReference(idAccion.getClass(), idAccion.getIdAccion());
                tblCitas.setIdAccion(idAccion);
            }
            TblPacientes idPaciente = tblCitas.getIdPaciente();
            if (idPaciente != null) {
                idPaciente = em.getReference(idPaciente.getClass(), idPaciente.getIdPaciente());
                tblCitas.setIdPaciente(idPaciente);
            }
            em.persist(tblCitas);
            if (idAccion != null) {
                idAccion.getTblCitasCollection().add(tblCitas);
                idAccion = em.merge(idAccion);
            }
            if (idPaciente != null) {
                idPaciente.getTblCitasCollection().add(tblCitas);
                idPaciente = em.merge(idPaciente);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblCitas(tblCitas.getIdCita()) != null) {
                throw new PreexistingEntityException("TblCitas " + tblCitas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblCitas tblCitas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblCitas persistentTblCitas = em.find(TblCitas.class, tblCitas.getIdCita());
            TblAcciones idAccionOld = persistentTblCitas.getIdAccion();
            TblAcciones idAccionNew = tblCitas.getIdAccion();
            TblPacientes idPacienteOld = persistentTblCitas.getIdPaciente();
            TblPacientes idPacienteNew = tblCitas.getIdPaciente();
            if (idAccionNew != null) {
                idAccionNew = em.getReference(idAccionNew.getClass(), idAccionNew.getIdAccion());
                tblCitas.setIdAccion(idAccionNew);
            }
            if (idPacienteNew != null) {
                idPacienteNew = em.getReference(idPacienteNew.getClass(), idPacienteNew.getIdPaciente());
                tblCitas.setIdPaciente(idPacienteNew);
            }
            tblCitas = em.merge(tblCitas);
            if (idAccionOld != null && !idAccionOld.equals(idAccionNew)) {
                idAccionOld.getTblCitasCollection().remove(tblCitas);
                idAccionOld = em.merge(idAccionOld);
            }
            if (idAccionNew != null && !idAccionNew.equals(idAccionOld)) {
                idAccionNew.getTblCitasCollection().add(tblCitas);
                idAccionNew = em.merge(idAccionNew);
            }
            if (idPacienteOld != null && !idPacienteOld.equals(idPacienteNew)) {
                idPacienteOld.getTblCitasCollection().remove(tblCitas);
                idPacienteOld = em.merge(idPacienteOld);
            }
            if (idPacienteNew != null && !idPacienteNew.equals(idPacienteOld)) {
                idPacienteNew.getTblCitasCollection().add(tblCitas);
                idPacienteNew = em.merge(idPacienteNew);
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
                Integer id = tblCitas.getIdCita();
                if (findTblCitas(id) == null) {
                    throw new NonexistentEntityException("The tblCitas with id " + id + " no longer exists.");
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
            TblCitas tblCitas;
            try {
                tblCitas = em.getReference(TblCitas.class, id);
                tblCitas.getIdCita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblCitas with id " + id + " no longer exists.", enfe);
            }
            TblAcciones idAccion = tblCitas.getIdAccion();
            if (idAccion != null) {
                idAccion.getTblCitasCollection().remove(tblCitas);
                idAccion = em.merge(idAccion);
            }
            TblPacientes idPaciente = tblCitas.getIdPaciente();
            if (idPaciente != null) {
                idPaciente.getTblCitasCollection().remove(tblCitas);
                idPaciente = em.merge(idPaciente);
            }
            em.remove(tblCitas);
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

    public List<TblCitas> findTblCitasEntities() {
        return findTblCitasEntities(true, -1, -1);
    }

    public List<TblCitas> findTblCitasEntities(int maxResults, int firstResult) {
        return findTblCitasEntities(false, maxResults, firstResult);
    }

    private List<TblCitas> findTblCitasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblCitas.class));
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

    public TblCitas findTblCitas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblCitas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblCitasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblCitas> rt = cq.from(TblCitas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
