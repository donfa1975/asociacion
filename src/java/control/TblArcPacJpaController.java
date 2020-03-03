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
import modelo.TblArcPac;
import modelo.TblAreas;
import modelo.TblPacientes;

/**
 *
 * @author fabricio.diaz
 */
public class TblArcPacJpaController implements Serializable {

    public TblArcPacJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblArcPac tblArcPac) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblAreas idArea = tblArcPac.getIdArea();
            if (idArea != null) {
                idArea = em.getReference(idArea.getClass(), idArea.getIdArea());
                tblArcPac.setIdArea(idArea);
            }
            TblPacientes idPaciente = tblArcPac.getIdPaciente();
            if (idPaciente != null) {
                idPaciente = em.getReference(idPaciente.getClass(), idPaciente.getIdPaciente());
                tblArcPac.setIdPaciente(idPaciente);
            }
            em.persist(tblArcPac);
            if (idArea != null) {
                idArea.getTblArcPacCollection().add(tblArcPac);
                idArea = em.merge(idArea);
            }
            if (idPaciente != null) {
                idPaciente.getTblArcPacCollection().add(tblArcPac);
                idPaciente = em.merge(idPaciente);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblArcPac(tblArcPac.getIdArPac()) != null) {
                throw new PreexistingEntityException("TblArcPac " + tblArcPac + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblArcPac tblArcPac) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblArcPac persistentTblArcPac = em.find(TblArcPac.class, tblArcPac.getIdArPac());
            TblAreas idAreaOld = persistentTblArcPac.getIdArea();
            TblAreas idAreaNew = tblArcPac.getIdArea();
            TblPacientes idPacienteOld = persistentTblArcPac.getIdPaciente();
            TblPacientes idPacienteNew = tblArcPac.getIdPaciente();
            if (idAreaNew != null) {
                idAreaNew = em.getReference(idAreaNew.getClass(), idAreaNew.getIdArea());
                tblArcPac.setIdArea(idAreaNew);
            }
            if (idPacienteNew != null) {
                idPacienteNew = em.getReference(idPacienteNew.getClass(), idPacienteNew.getIdPaciente());
                tblArcPac.setIdPaciente(idPacienteNew);
            }
            tblArcPac = em.merge(tblArcPac);
            if (idAreaOld != null && !idAreaOld.equals(idAreaNew)) {
                idAreaOld.getTblArcPacCollection().remove(tblArcPac);
                idAreaOld = em.merge(idAreaOld);
            }
            if (idAreaNew != null && !idAreaNew.equals(idAreaOld)) {
                idAreaNew.getTblArcPacCollection().add(tblArcPac);
                idAreaNew = em.merge(idAreaNew);
            }
            if (idPacienteOld != null && !idPacienteOld.equals(idPacienteNew)) {
                idPacienteOld.getTblArcPacCollection().remove(tblArcPac);
                idPacienteOld = em.merge(idPacienteOld);
            }
            if (idPacienteNew != null && !idPacienteNew.equals(idPacienteOld)) {
                idPacienteNew.getTblArcPacCollection().add(tblArcPac);
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
                Integer id = tblArcPac.getIdArPac();
                if (findTblArcPac(id) == null) {
                    throw new NonexistentEntityException("The tblArcPac with id " + id + " no longer exists.");
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
            TblArcPac tblArcPac;
            try {
                tblArcPac = em.getReference(TblArcPac.class, id);
                tblArcPac.getIdArPac();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblArcPac with id " + id + " no longer exists.", enfe);
            }
            TblAreas idArea = tblArcPac.getIdArea();
            if (idArea != null) {
                idArea.getTblArcPacCollection().remove(tblArcPac);
                idArea = em.merge(idArea);
            }
            TblPacientes idPaciente = tblArcPac.getIdPaciente();
            if (idPaciente != null) {
                idPaciente.getTblArcPacCollection().remove(tblArcPac);
                idPaciente = em.merge(idPaciente);
            }
            em.remove(tblArcPac);
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

    public List<TblArcPac> findTblArcPacEntities() {
        return findTblArcPacEntities(true, -1, -1);
    }

    public List<TblArcPac> findTblArcPacEntities(int maxResults, int firstResult) {
        return findTblArcPacEntities(false, maxResults, firstResult);
    }

    private List<TblArcPac> findTblArcPacEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblArcPac.class));
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

    public TblArcPac findTblArcPac(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblArcPac.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblArcPacCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblArcPac> rt = cq.from(TblArcPac.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
