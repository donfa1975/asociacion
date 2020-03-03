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
import modelo.TblMatriculas;
import modelo.TblPacientes;
import modelo.TblUsuarios;

/**
 *
 * @author fabricio.diaz
 */
public class TblMatriculasJpaController implements Serializable {

    public TblMatriculasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblMatriculas tblMatriculas) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblPacientes idPaciente = tblMatriculas.getIdPaciente();
            if (idPaciente != null) {
                idPaciente = em.getReference(idPaciente.getClass(), idPaciente.getIdPaciente());
                tblMatriculas.setIdPaciente(idPaciente);
            }
            TblUsuarios idusuario = tblMatriculas.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdUsuario());
                tblMatriculas.setIdusuario(idusuario);
            }
            em.persist(tblMatriculas);
            if (idPaciente != null) {
                idPaciente.getTblMatriculasCollection().add(tblMatriculas);
                idPaciente = em.merge(idPaciente);
            }
            if (idusuario != null) {
                idusuario.getTblMatriculasCollection().add(tblMatriculas);
                idusuario = em.merge(idusuario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblMatriculas(tblMatriculas.getIdMatricula()) != null) {
                throw new PreexistingEntityException("TblMatriculas " + tblMatriculas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblMatriculas tblMatriculas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblMatriculas persistentTblMatriculas = em.find(TblMatriculas.class, tblMatriculas.getIdMatricula());
            TblPacientes idPacienteOld = persistentTblMatriculas.getIdPaciente();
            TblPacientes idPacienteNew = tblMatriculas.getIdPaciente();
            TblUsuarios idusuarioOld = persistentTblMatriculas.getIdusuario();
            TblUsuarios idusuarioNew = tblMatriculas.getIdusuario();
            if (idPacienteNew != null) {
                idPacienteNew = em.getReference(idPacienteNew.getClass(), idPacienteNew.getIdPaciente());
                tblMatriculas.setIdPaciente(idPacienteNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdUsuario());
                tblMatriculas.setIdusuario(idusuarioNew);
            }
            tblMatriculas = em.merge(tblMatriculas);
            if (idPacienteOld != null && !idPacienteOld.equals(idPacienteNew)) {
                idPacienteOld.getTblMatriculasCollection().remove(tblMatriculas);
                idPacienteOld = em.merge(idPacienteOld);
            }
            if (idPacienteNew != null && !idPacienteNew.equals(idPacienteOld)) {
                idPacienteNew.getTblMatriculasCollection().add(tblMatriculas);
                idPacienteNew = em.merge(idPacienteNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getTblMatriculasCollection().remove(tblMatriculas);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getTblMatriculasCollection().add(tblMatriculas);
                idusuarioNew = em.merge(idusuarioNew);
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
                Integer id = tblMatriculas.getIdMatricula();
                if (findTblMatriculas(id) == null) {
                    throw new NonexistentEntityException("The tblMatriculas with id " + id + " no longer exists.");
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
            TblMatriculas tblMatriculas;
            try {
                tblMatriculas = em.getReference(TblMatriculas.class, id);
                tblMatriculas.getIdMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblMatriculas with id " + id + " no longer exists.", enfe);
            }
            TblPacientes idPaciente = tblMatriculas.getIdPaciente();
            if (idPaciente != null) {
                idPaciente.getTblMatriculasCollection().remove(tblMatriculas);
                idPaciente = em.merge(idPaciente);
            }
            TblUsuarios idusuario = tblMatriculas.getIdusuario();
            if (idusuario != null) {
                idusuario.getTblMatriculasCollection().remove(tblMatriculas);
                idusuario = em.merge(idusuario);
            }
            em.remove(tblMatriculas);
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

    public List<TblMatriculas> findTblMatriculasEntities() {
        return findTblMatriculasEntities(true, -1, -1);
    }

    public List<TblMatriculas> findTblMatriculasEntities(int maxResults, int firstResult) {
        return findTblMatriculasEntities(false, maxResults, firstResult);
    }

    private List<TblMatriculas> findTblMatriculasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblMatriculas.class));
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

    public TblMatriculas findTblMatriculas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblMatriculas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblMatriculasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblMatriculas> rt = cq.from(TblMatriculas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
