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
import modelo.TblAreas;
import modelo.TblCantones;
import modelo.TblOrigenes;
import modelo.TblZonas;
import modelo.TblArcPac;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.TblDiagnotiscos;
import modelo.TblMatriculas;
import modelo.TblCitas;
import modelo.TblPacientes;

/**
 *
 * @author fabricio.diaz
 */
public class TblPacientesJpaController implements Serializable {

    public TblPacientesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblPacientes tblPacientes) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblPacientes.getTblArcPacCollection() == null) {
            tblPacientes.setTblArcPacCollection(new ArrayList<TblArcPac>());
        }
        if (tblPacientes.getTblDiagnotiscosCollection() == null) {
            tblPacientes.setTblDiagnotiscosCollection(new ArrayList<TblDiagnotiscos>());
        }
        if (tblPacientes.getTblMatriculasCollection() == null) {
            tblPacientes.setTblMatriculasCollection(new ArrayList<TblMatriculas>());
        }
        if (tblPacientes.getTblCitasCollection() == null) {
            tblPacientes.setTblCitasCollection(new ArrayList<TblCitas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblAreas idArea = tblPacientes.getIdArea();
            if (idArea != null) {
                idArea = em.getReference(idArea.getClass(), idArea.getIdArea());
                tblPacientes.setIdArea(idArea);
            }
            TblCantones idCanton = tblPacientes.getIdCanton();
            if (idCanton != null) {
                idCanton = em.getReference(idCanton.getClass(), idCanton.getIdcanton());
                tblPacientes.setIdCanton(idCanton);
            }
            TblOrigenes idOrigen = tblPacientes.getIdOrigen();
            if (idOrigen != null) {
                idOrigen = em.getReference(idOrigen.getClass(), idOrigen.getIdorigen());
                tblPacientes.setIdOrigen(idOrigen);
            }
            TblZonas idzona = tblPacientes.getIdzona();
            if (idzona != null) {
                idzona = em.getReference(idzona.getClass(), idzona.getIdZona());
                tblPacientes.setIdzona(idzona);
            }
            Collection<TblArcPac> attachedTblArcPacCollection = new ArrayList<TblArcPac>();
            for (TblArcPac tblArcPacCollectionTblArcPacToAttach : tblPacientes.getTblArcPacCollection()) {
                tblArcPacCollectionTblArcPacToAttach = em.getReference(tblArcPacCollectionTblArcPacToAttach.getClass(), tblArcPacCollectionTblArcPacToAttach.getIdArPac());
                attachedTblArcPacCollection.add(tblArcPacCollectionTblArcPacToAttach);
            }
            tblPacientes.setTblArcPacCollection(attachedTblArcPacCollection);
            Collection<TblDiagnotiscos> attachedTblDiagnotiscosCollection = new ArrayList<TblDiagnotiscos>();
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscosToAttach : tblPacientes.getTblDiagnotiscosCollection()) {
                tblDiagnotiscosCollectionTblDiagnotiscosToAttach = em.getReference(tblDiagnotiscosCollectionTblDiagnotiscosToAttach.getClass(), tblDiagnotiscosCollectionTblDiagnotiscosToAttach.getIdDiagnostico());
                attachedTblDiagnotiscosCollection.add(tblDiagnotiscosCollectionTblDiagnotiscosToAttach);
            }
            tblPacientes.setTblDiagnotiscosCollection(attachedTblDiagnotiscosCollection);
            Collection<TblMatriculas> attachedTblMatriculasCollection = new ArrayList<TblMatriculas>();
            for (TblMatriculas tblMatriculasCollectionTblMatriculasToAttach : tblPacientes.getTblMatriculasCollection()) {
                tblMatriculasCollectionTblMatriculasToAttach = em.getReference(tblMatriculasCollectionTblMatriculasToAttach.getClass(), tblMatriculasCollectionTblMatriculasToAttach.getIdMatricula());
                attachedTblMatriculasCollection.add(tblMatriculasCollectionTblMatriculasToAttach);
            }
            tblPacientes.setTblMatriculasCollection(attachedTblMatriculasCollection);
            Collection<TblCitas> attachedTblCitasCollection = new ArrayList<TblCitas>();
            for (TblCitas tblCitasCollectionTblCitasToAttach : tblPacientes.getTblCitasCollection()) {
                tblCitasCollectionTblCitasToAttach = em.getReference(tblCitasCollectionTblCitasToAttach.getClass(), tblCitasCollectionTblCitasToAttach.getIdCita());
                attachedTblCitasCollection.add(tblCitasCollectionTblCitasToAttach);
            }
            tblPacientes.setTblCitasCollection(attachedTblCitasCollection);
            em.persist(tblPacientes);
            if (idArea != null) {
                idArea.getTblPacientesCollection().add(tblPacientes);
                idArea = em.merge(idArea);
            }
            if (idCanton != null) {
                idCanton.getTblPacientesCollection().add(tblPacientes);
                idCanton = em.merge(idCanton);
            }
            if (idOrigen != null) {
                idOrigen.getTblPacientesCollection().add(tblPacientes);
                idOrigen = em.merge(idOrigen);
            }
            if (idzona != null) {
                idzona.getTblPacientesCollection().add(tblPacientes);
                idzona = em.merge(idzona);
            }
            for (TblArcPac tblArcPacCollectionTblArcPac : tblPacientes.getTblArcPacCollection()) {
                TblPacientes oldIdPacienteOfTblArcPacCollectionTblArcPac = tblArcPacCollectionTblArcPac.getIdPaciente();
                tblArcPacCollectionTblArcPac.setIdPaciente(tblPacientes);
                tblArcPacCollectionTblArcPac = em.merge(tblArcPacCollectionTblArcPac);
                if (oldIdPacienteOfTblArcPacCollectionTblArcPac != null) {
                    oldIdPacienteOfTblArcPacCollectionTblArcPac.getTblArcPacCollection().remove(tblArcPacCollectionTblArcPac);
                    oldIdPacienteOfTblArcPacCollectionTblArcPac = em.merge(oldIdPacienteOfTblArcPacCollectionTblArcPac);
                }
            }
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscos : tblPacientes.getTblDiagnotiscosCollection()) {
                TblPacientes oldIdPacienteOfTblDiagnotiscosCollectionTblDiagnotiscos = tblDiagnotiscosCollectionTblDiagnotiscos.getIdPaciente();
                tblDiagnotiscosCollectionTblDiagnotiscos.setIdPaciente(tblPacientes);
                tblDiagnotiscosCollectionTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionTblDiagnotiscos);
                if (oldIdPacienteOfTblDiagnotiscosCollectionTblDiagnotiscos != null) {
                    oldIdPacienteOfTblDiagnotiscosCollectionTblDiagnotiscos.getTblDiagnotiscosCollection().remove(tblDiagnotiscosCollectionTblDiagnotiscos);
                    oldIdPacienteOfTblDiagnotiscosCollectionTblDiagnotiscos = em.merge(oldIdPacienteOfTblDiagnotiscosCollectionTblDiagnotiscos);
                }
            }
            for (TblMatriculas tblMatriculasCollectionTblMatriculas : tblPacientes.getTblMatriculasCollection()) {
                TblPacientes oldIdPacienteOfTblMatriculasCollectionTblMatriculas = tblMatriculasCollectionTblMatriculas.getIdPaciente();
                tblMatriculasCollectionTblMatriculas.setIdPaciente(tblPacientes);
                tblMatriculasCollectionTblMatriculas = em.merge(tblMatriculasCollectionTblMatriculas);
                if (oldIdPacienteOfTblMatriculasCollectionTblMatriculas != null) {
                    oldIdPacienteOfTblMatriculasCollectionTblMatriculas.getTblMatriculasCollection().remove(tblMatriculasCollectionTblMatriculas);
                    oldIdPacienteOfTblMatriculasCollectionTblMatriculas = em.merge(oldIdPacienteOfTblMatriculasCollectionTblMatriculas);
                }
            }
            for (TblCitas tblCitasCollectionTblCitas : tblPacientes.getTblCitasCollection()) {
                TblPacientes oldIdPacienteOfTblCitasCollectionTblCitas = tblCitasCollectionTblCitas.getIdPaciente();
                tblCitasCollectionTblCitas.setIdPaciente(tblPacientes);
                tblCitasCollectionTblCitas = em.merge(tblCitasCollectionTblCitas);
                if (oldIdPacienteOfTblCitasCollectionTblCitas != null) {
                    oldIdPacienteOfTblCitasCollectionTblCitas.getTblCitasCollection().remove(tblCitasCollectionTblCitas);
                    oldIdPacienteOfTblCitasCollectionTblCitas = em.merge(oldIdPacienteOfTblCitasCollectionTblCitas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblPacientes(tblPacientes.getIdPaciente()) != null) {
                throw new PreexistingEntityException("TblPacientes " + tblPacientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblPacientes tblPacientes) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TblPacientes persistentTblPacientes = em.find(TblPacientes.class, tblPacientes.getIdPaciente());
            TblAreas idAreaOld = persistentTblPacientes.getIdArea();
            TblAreas idAreaNew = tblPacientes.getIdArea();
            TblCantones idCantonOld = persistentTblPacientes.getIdCanton();
            TblCantones idCantonNew = tblPacientes.getIdCanton();
            TblOrigenes idOrigenOld = persistentTblPacientes.getIdOrigen();
            TblOrigenes idOrigenNew = tblPacientes.getIdOrigen();
            TblZonas idzonaOld = persistentTblPacientes.getIdzona();
            TblZonas idzonaNew = tblPacientes.getIdzona();
            Collection<TblArcPac> tblArcPacCollectionOld = persistentTblPacientes.getTblArcPacCollection();
            Collection<TblArcPac> tblArcPacCollectionNew = tblPacientes.getTblArcPacCollection();
            Collection<TblDiagnotiscos> tblDiagnotiscosCollectionOld = persistentTblPacientes.getTblDiagnotiscosCollection();
            Collection<TblDiagnotiscos> tblDiagnotiscosCollectionNew = tblPacientes.getTblDiagnotiscosCollection();
            Collection<TblMatriculas> tblMatriculasCollectionOld = persistentTblPacientes.getTblMatriculasCollection();
            Collection<TblMatriculas> tblMatriculasCollectionNew = tblPacientes.getTblMatriculasCollection();
            Collection<TblCitas> tblCitasCollectionOld = persistentTblPacientes.getTblCitasCollection();
            Collection<TblCitas> tblCitasCollectionNew = tblPacientes.getTblCitasCollection();
            List<String> illegalOrphanMessages = null;
            for (TblArcPac tblArcPacCollectionOldTblArcPac : tblArcPacCollectionOld) {
                if (!tblArcPacCollectionNew.contains(tblArcPacCollectionOldTblArcPac)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblArcPac " + tblArcPacCollectionOldTblArcPac + " since its idPaciente field is not nullable.");
                }
            }
            for (TblMatriculas tblMatriculasCollectionOldTblMatriculas : tblMatriculasCollectionOld) {
                if (!tblMatriculasCollectionNew.contains(tblMatriculasCollectionOldTblMatriculas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TblMatriculas " + tblMatriculasCollectionOldTblMatriculas + " since its idPaciente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idAreaNew != null) {
                idAreaNew = em.getReference(idAreaNew.getClass(), idAreaNew.getIdArea());
                tblPacientes.setIdArea(idAreaNew);
            }
            if (idCantonNew != null) {
                idCantonNew = em.getReference(idCantonNew.getClass(), idCantonNew.getIdcanton());
                tblPacientes.setIdCanton(idCantonNew);
            }
            if (idOrigenNew != null) {
                idOrigenNew = em.getReference(idOrigenNew.getClass(), idOrigenNew.getIdorigen());
                tblPacientes.setIdOrigen(idOrigenNew);
            }
            if (idzonaNew != null) {
                idzonaNew = em.getReference(idzonaNew.getClass(), idzonaNew.getIdZona());
                tblPacientes.setIdzona(idzonaNew);
            }
            Collection<TblArcPac> attachedTblArcPacCollectionNew = new ArrayList<TblArcPac>();
            for (TblArcPac tblArcPacCollectionNewTblArcPacToAttach : tblArcPacCollectionNew) {
                tblArcPacCollectionNewTblArcPacToAttach = em.getReference(tblArcPacCollectionNewTblArcPacToAttach.getClass(), tblArcPacCollectionNewTblArcPacToAttach.getIdArPac());
                attachedTblArcPacCollectionNew.add(tblArcPacCollectionNewTblArcPacToAttach);
            }
            tblArcPacCollectionNew = attachedTblArcPacCollectionNew;
            tblPacientes.setTblArcPacCollection(tblArcPacCollectionNew);
            Collection<TblDiagnotiscos> attachedTblDiagnotiscosCollectionNew = new ArrayList<TblDiagnotiscos>();
            for (TblDiagnotiscos tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach : tblDiagnotiscosCollectionNew) {
                tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach = em.getReference(tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach.getClass(), tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach.getIdDiagnostico());
                attachedTblDiagnotiscosCollectionNew.add(tblDiagnotiscosCollectionNewTblDiagnotiscosToAttach);
            }
            tblDiagnotiscosCollectionNew = attachedTblDiagnotiscosCollectionNew;
            tblPacientes.setTblDiagnotiscosCollection(tblDiagnotiscosCollectionNew);
            Collection<TblMatriculas> attachedTblMatriculasCollectionNew = new ArrayList<TblMatriculas>();
            for (TblMatriculas tblMatriculasCollectionNewTblMatriculasToAttach : tblMatriculasCollectionNew) {
                tblMatriculasCollectionNewTblMatriculasToAttach = em.getReference(tblMatriculasCollectionNewTblMatriculasToAttach.getClass(), tblMatriculasCollectionNewTblMatriculasToAttach.getIdMatricula());
                attachedTblMatriculasCollectionNew.add(tblMatriculasCollectionNewTblMatriculasToAttach);
            }
            tblMatriculasCollectionNew = attachedTblMatriculasCollectionNew;
            tblPacientes.setTblMatriculasCollection(tblMatriculasCollectionNew);
            Collection<TblCitas> attachedTblCitasCollectionNew = new ArrayList<TblCitas>();
            for (TblCitas tblCitasCollectionNewTblCitasToAttach : tblCitasCollectionNew) {
                tblCitasCollectionNewTblCitasToAttach = em.getReference(tblCitasCollectionNewTblCitasToAttach.getClass(), tblCitasCollectionNewTblCitasToAttach.getIdCita());
                attachedTblCitasCollectionNew.add(tblCitasCollectionNewTblCitasToAttach);
            }
            tblCitasCollectionNew = attachedTblCitasCollectionNew;
            tblPacientes.setTblCitasCollection(tblCitasCollectionNew);
            tblPacientes = em.merge(tblPacientes);
            if (idAreaOld != null && !idAreaOld.equals(idAreaNew)) {
                idAreaOld.getTblPacientesCollection().remove(tblPacientes);
                idAreaOld = em.merge(idAreaOld);
            }
            if (idAreaNew != null && !idAreaNew.equals(idAreaOld)) {
                idAreaNew.getTblPacientesCollection().add(tblPacientes);
                idAreaNew = em.merge(idAreaNew);
            }
            if (idCantonOld != null && !idCantonOld.equals(idCantonNew)) {
                idCantonOld.getTblPacientesCollection().remove(tblPacientes);
                idCantonOld = em.merge(idCantonOld);
            }
            if (idCantonNew != null && !idCantonNew.equals(idCantonOld)) {
                idCantonNew.getTblPacientesCollection().add(tblPacientes);
                idCantonNew = em.merge(idCantonNew);
            }
            if (idOrigenOld != null && !idOrigenOld.equals(idOrigenNew)) {
                idOrigenOld.getTblPacientesCollection().remove(tblPacientes);
                idOrigenOld = em.merge(idOrigenOld);
            }
            if (idOrigenNew != null && !idOrigenNew.equals(idOrigenOld)) {
                idOrigenNew.getTblPacientesCollection().add(tblPacientes);
                idOrigenNew = em.merge(idOrigenNew);
            }
            if (idzonaOld != null && !idzonaOld.equals(idzonaNew)) {
                idzonaOld.getTblPacientesCollection().remove(tblPacientes);
                idzonaOld = em.merge(idzonaOld);
            }
            if (idzonaNew != null && !idzonaNew.equals(idzonaOld)) {
                idzonaNew.getTblPacientesCollection().add(tblPacientes);
                idzonaNew = em.merge(idzonaNew);
            }
            for (TblArcPac tblArcPacCollectionNewTblArcPac : tblArcPacCollectionNew) {
                if (!tblArcPacCollectionOld.contains(tblArcPacCollectionNewTblArcPac)) {
                    TblPacientes oldIdPacienteOfTblArcPacCollectionNewTblArcPac = tblArcPacCollectionNewTblArcPac.getIdPaciente();
                    tblArcPacCollectionNewTblArcPac.setIdPaciente(tblPacientes);
                    tblArcPacCollectionNewTblArcPac = em.merge(tblArcPacCollectionNewTblArcPac);
                    if (oldIdPacienteOfTblArcPacCollectionNewTblArcPac != null && !oldIdPacienteOfTblArcPacCollectionNewTblArcPac.equals(tblPacientes)) {
                        oldIdPacienteOfTblArcPacCollectionNewTblArcPac.getTblArcPacCollection().remove(tblArcPacCollectionNewTblArcPac);
                        oldIdPacienteOfTblArcPacCollectionNewTblArcPac = em.merge(oldIdPacienteOfTblArcPacCollectionNewTblArcPac);
                    }
                }
            }
            for (TblDiagnotiscos tblDiagnotiscosCollectionOldTblDiagnotiscos : tblDiagnotiscosCollectionOld) {
                if (!tblDiagnotiscosCollectionNew.contains(tblDiagnotiscosCollectionOldTblDiagnotiscos)) {
                    tblDiagnotiscosCollectionOldTblDiagnotiscos.setIdPaciente(null);
                    tblDiagnotiscosCollectionOldTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionOldTblDiagnotiscos);
                }
            }
            for (TblDiagnotiscos tblDiagnotiscosCollectionNewTblDiagnotiscos : tblDiagnotiscosCollectionNew) {
                if (!tblDiagnotiscosCollectionOld.contains(tblDiagnotiscosCollectionNewTblDiagnotiscos)) {
                    TblPacientes oldIdPacienteOfTblDiagnotiscosCollectionNewTblDiagnotiscos = tblDiagnotiscosCollectionNewTblDiagnotiscos.getIdPaciente();
                    tblDiagnotiscosCollectionNewTblDiagnotiscos.setIdPaciente(tblPacientes);
                    tblDiagnotiscosCollectionNewTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionNewTblDiagnotiscos);
                    if (oldIdPacienteOfTblDiagnotiscosCollectionNewTblDiagnotiscos != null && !oldIdPacienteOfTblDiagnotiscosCollectionNewTblDiagnotiscos.equals(tblPacientes)) {
                        oldIdPacienteOfTblDiagnotiscosCollectionNewTblDiagnotiscos.getTblDiagnotiscosCollection().remove(tblDiagnotiscosCollectionNewTblDiagnotiscos);
                        oldIdPacienteOfTblDiagnotiscosCollectionNewTblDiagnotiscos = em.merge(oldIdPacienteOfTblDiagnotiscosCollectionNewTblDiagnotiscos);
                    }
                }
            }
            for (TblMatriculas tblMatriculasCollectionNewTblMatriculas : tblMatriculasCollectionNew) {
                if (!tblMatriculasCollectionOld.contains(tblMatriculasCollectionNewTblMatriculas)) {
                    TblPacientes oldIdPacienteOfTblMatriculasCollectionNewTblMatriculas = tblMatriculasCollectionNewTblMatriculas.getIdPaciente();
                    tblMatriculasCollectionNewTblMatriculas.setIdPaciente(tblPacientes);
                    tblMatriculasCollectionNewTblMatriculas = em.merge(tblMatriculasCollectionNewTblMatriculas);
                    if (oldIdPacienteOfTblMatriculasCollectionNewTblMatriculas != null && !oldIdPacienteOfTblMatriculasCollectionNewTblMatriculas.equals(tblPacientes)) {
                        oldIdPacienteOfTblMatriculasCollectionNewTblMatriculas.getTblMatriculasCollection().remove(tblMatriculasCollectionNewTblMatriculas);
                        oldIdPacienteOfTblMatriculasCollectionNewTblMatriculas = em.merge(oldIdPacienteOfTblMatriculasCollectionNewTblMatriculas);
                    }
                }
            }
            for (TblCitas tblCitasCollectionOldTblCitas : tblCitasCollectionOld) {
                if (!tblCitasCollectionNew.contains(tblCitasCollectionOldTblCitas)) {
                    tblCitasCollectionOldTblCitas.setIdPaciente(null);
                    tblCitasCollectionOldTblCitas = em.merge(tblCitasCollectionOldTblCitas);
                }
            }
            for (TblCitas tblCitasCollectionNewTblCitas : tblCitasCollectionNew) {
                if (!tblCitasCollectionOld.contains(tblCitasCollectionNewTblCitas)) {
                    TblPacientes oldIdPacienteOfTblCitasCollectionNewTblCitas = tblCitasCollectionNewTblCitas.getIdPaciente();
                    tblCitasCollectionNewTblCitas.setIdPaciente(tblPacientes);
                    tblCitasCollectionNewTblCitas = em.merge(tblCitasCollectionNewTblCitas);
                    if (oldIdPacienteOfTblCitasCollectionNewTblCitas != null && !oldIdPacienteOfTblCitasCollectionNewTblCitas.equals(tblPacientes)) {
                        oldIdPacienteOfTblCitasCollectionNewTblCitas.getTblCitasCollection().remove(tblCitasCollectionNewTblCitas);
                        oldIdPacienteOfTblCitasCollectionNewTblCitas = em.merge(oldIdPacienteOfTblCitasCollectionNewTblCitas);
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
                Integer id = tblPacientes.getIdPaciente();
                if (findTblPacientes(id) == null) {
                    throw new NonexistentEntityException("The tblPacientes with id " + id + " no longer exists.");
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
            TblPacientes tblPacientes;
            try {
                tblPacientes = em.getReference(TblPacientes.class, id);
                tblPacientes.getIdPaciente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblPacientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TblArcPac> tblArcPacCollectionOrphanCheck = tblPacientes.getTblArcPacCollection();
            for (TblArcPac tblArcPacCollectionOrphanCheckTblArcPac : tblArcPacCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblPacientes (" + tblPacientes + ") cannot be destroyed since the TblArcPac " + tblArcPacCollectionOrphanCheckTblArcPac + " in its tblArcPacCollection field has a non-nullable idPaciente field.");
            }
            Collection<TblMatriculas> tblMatriculasCollectionOrphanCheck = tblPacientes.getTblMatriculasCollection();
            for (TblMatriculas tblMatriculasCollectionOrphanCheckTblMatriculas : tblMatriculasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblPacientes (" + tblPacientes + ") cannot be destroyed since the TblMatriculas " + tblMatriculasCollectionOrphanCheckTblMatriculas + " in its tblMatriculasCollection field has a non-nullable idPaciente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblAreas idArea = tblPacientes.getIdArea();
            if (idArea != null) {
                idArea.getTblPacientesCollection().remove(tblPacientes);
                idArea = em.merge(idArea);
            }
            TblCantones idCanton = tblPacientes.getIdCanton();
            if (idCanton != null) {
                idCanton.getTblPacientesCollection().remove(tblPacientes);
                idCanton = em.merge(idCanton);
            }
            TblOrigenes idOrigen = tblPacientes.getIdOrigen();
            if (idOrigen != null) {
                idOrigen.getTblPacientesCollection().remove(tblPacientes);
                idOrigen = em.merge(idOrigen);
            }
            TblZonas idzona = tblPacientes.getIdzona();
            if (idzona != null) {
                idzona.getTblPacientesCollection().remove(tblPacientes);
                idzona = em.merge(idzona);
            }
            Collection<TblDiagnotiscos> tblDiagnotiscosCollection = tblPacientes.getTblDiagnotiscosCollection();
            for (TblDiagnotiscos tblDiagnotiscosCollectionTblDiagnotiscos : tblDiagnotiscosCollection) {
                tblDiagnotiscosCollectionTblDiagnotiscos.setIdPaciente(null);
                tblDiagnotiscosCollectionTblDiagnotiscos = em.merge(tblDiagnotiscosCollectionTblDiagnotiscos);
            }
            Collection<TblCitas> tblCitasCollection = tblPacientes.getTblCitasCollection();
            for (TblCitas tblCitasCollectionTblCitas : tblCitasCollection) {
                tblCitasCollectionTblCitas.setIdPaciente(null);
                tblCitasCollectionTblCitas = em.merge(tblCitasCollectionTblCitas);
            }
            em.remove(tblPacientes);
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

    public List<TblPacientes> findTblPacientesEntities() {
        return findTblPacientesEntities(true, -1, -1);
    }

    public List<TblPacientes> findTblPacientesEntities(int maxResults, int firstResult) {
        return findTblPacientesEntities(false, maxResults, firstResult);
    }

    private List<TblPacientes> findTblPacientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblPacientes.class));
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

    public TblPacientes findTblPacientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblPacientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblPacientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblPacientes> rt = cq.from(TblPacientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
