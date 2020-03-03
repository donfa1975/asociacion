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
import modelo.TblDiagnotiscos;
import modelo.TblPacientes;
import modelo.TblTipoDiagnostico;
import modelo.TblUsuarios;

/**
 *
 * @author fabricio.diaz
 */
public class TblDiagnotiscosJpaController implements Serializable {

    public TblDiagnotiscosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblDiagnotiscos tblDiagnotiscos) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblPacientes idPaciente = tblDiagnotiscos.getIdPaciente();
            if (idPaciente != null) {
                idPaciente = em.getReference(idPaciente.getClass(), idPaciente.getIdPaciente());
                tblDiagnotiscos.setIdPaciente(idPaciente);
            }
            TblTipoDiagnostico idTipoDiagnostico = tblDiagnotiscos.getIdTipoDiagnostico();
            if (idTipoDiagnostico != null) {
                idTipoDiagnostico = em.getReference(idTipoDiagnostico.getClass(), idTipoDiagnostico.getIdTipoDiagnostico());
                tblDiagnotiscos.setIdTipoDiagnostico(idTipoDiagnostico);
            }
            TblUsuarios idusuario = tblDiagnotiscos.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdUsuario());
                tblDiagnotiscos.setIdusuario(idusuario);
            }
            em.persist(tblDiagnotiscos);
            if (idPaciente != null) {
                idPaciente.getTblDiagnotiscosCollection().add(tblDiagnotiscos);
                idPaciente = em.merge(idPaciente);
            }
            if (idTipoDiagnostico != null) {
                idTipoDiagnostico.getTblDiagnotiscosCollection().add(tblDiagnotiscos);
                idTipoDiagnostico = em.merge(idTipoDiagnostico);
            }
            if (idusuario != null) {
                idusuario.getTblDiagnotiscosCollection().add(tblDiagnotiscos);
                idusuario = em.merge(idusuario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblDiagnotiscos(tblDiagnotiscos.getIdDiagnostico()) != null) {
                throw new PreexistingEntityException("TblDiagnotiscos " + tblDiagnotiscos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblDiagnotiscos tblDiagnotiscos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblDiagnotiscos persistentTblDiagnotiscos = em.find(TblDiagnotiscos.class, tblDiagnotiscos.getIdDiagnostico());
            TblPacientes idPacienteOld = persistentTblDiagnotiscos.getIdPaciente();
            TblPacientes idPacienteNew = tblDiagnotiscos.getIdPaciente();
            TblTipoDiagnostico idTipoDiagnosticoOld = persistentTblDiagnotiscos.getIdTipoDiagnostico();
            TblTipoDiagnostico idTipoDiagnosticoNew = tblDiagnotiscos.getIdTipoDiagnostico();
            TblUsuarios idusuarioOld = persistentTblDiagnotiscos.getIdusuario();
            TblUsuarios idusuarioNew = tblDiagnotiscos.getIdusuario();
            if (idPacienteNew != null) {
                idPacienteNew = em.getReference(idPacienteNew.getClass(), idPacienteNew.getIdPaciente());
                tblDiagnotiscos.setIdPaciente(idPacienteNew);
            }
            if (idTipoDiagnosticoNew != null) {
                idTipoDiagnosticoNew = em.getReference(idTipoDiagnosticoNew.getClass(), idTipoDiagnosticoNew.getIdTipoDiagnostico());
                tblDiagnotiscos.setIdTipoDiagnostico(idTipoDiagnosticoNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdUsuario());
                tblDiagnotiscos.setIdusuario(idusuarioNew);
            }
            tblDiagnotiscos = em.merge(tblDiagnotiscos);
            if (idPacienteOld != null && !idPacienteOld.equals(idPacienteNew)) {
                idPacienteOld.getTblDiagnotiscosCollection().remove(tblDiagnotiscos);
                idPacienteOld = em.merge(idPacienteOld);
            }
            if (idPacienteNew != null && !idPacienteNew.equals(idPacienteOld)) {
                idPacienteNew.getTblDiagnotiscosCollection().add(tblDiagnotiscos);
                idPacienteNew = em.merge(idPacienteNew);
            }
            if (idTipoDiagnosticoOld != null && !idTipoDiagnosticoOld.equals(idTipoDiagnosticoNew)) {
                idTipoDiagnosticoOld.getTblDiagnotiscosCollection().remove(tblDiagnotiscos);
                idTipoDiagnosticoOld = em.merge(idTipoDiagnosticoOld);
            }
            if (idTipoDiagnosticoNew != null && !idTipoDiagnosticoNew.equals(idTipoDiagnosticoOld)) {
                idTipoDiagnosticoNew.getTblDiagnotiscosCollection().add(tblDiagnotiscos);
                idTipoDiagnosticoNew = em.merge(idTipoDiagnosticoNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getTblDiagnotiscosCollection().remove(tblDiagnotiscos);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getTblDiagnotiscosCollection().add(tblDiagnotiscos);
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
                Integer id = tblDiagnotiscos.getIdDiagnostico();
                if (findTblDiagnotiscos(id) == null) {
                    throw new NonexistentEntityException("The tblDiagnotiscos with id " + id + " no longer exists.");
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
            TblDiagnotiscos tblDiagnotiscos;
            try {
                tblDiagnotiscos = em.getReference(TblDiagnotiscos.class, id);
                tblDiagnotiscos.getIdDiagnostico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblDiagnotiscos with id " + id + " no longer exists.", enfe);
            }
            TblPacientes idPaciente = tblDiagnotiscos.getIdPaciente();
            if (idPaciente != null) {
                idPaciente.getTblDiagnotiscosCollection().remove(tblDiagnotiscos);
                idPaciente = em.merge(idPaciente);
            }
            TblTipoDiagnostico idTipoDiagnostico = tblDiagnotiscos.getIdTipoDiagnostico();
            if (idTipoDiagnostico != null) {
                idTipoDiagnostico.getTblDiagnotiscosCollection().remove(tblDiagnotiscos);
                idTipoDiagnostico = em.merge(idTipoDiagnostico);
            }
            TblUsuarios idusuario = tblDiagnotiscos.getIdusuario();
            if (idusuario != null) {
                idusuario.getTblDiagnotiscosCollection().remove(tblDiagnotiscos);
                idusuario = em.merge(idusuario);
            }
            em.remove(tblDiagnotiscos);
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

    public List<TblDiagnotiscos> findTblDiagnotiscosEntities() {
        return findTblDiagnotiscosEntities(true, -1, -1);
    }

    public List<TblDiagnotiscos> findTblDiagnotiscosEntities(int maxResults, int firstResult) {
        return findTblDiagnotiscosEntities(false, maxResults, firstResult);
    }

    private List<TblDiagnotiscos> findTblDiagnotiscosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblDiagnotiscos.class));
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

    public TblDiagnotiscos findTblDiagnotiscos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblDiagnotiscos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblDiagnotiscosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblDiagnotiscos> rt = cq.from(TblDiagnotiscos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
