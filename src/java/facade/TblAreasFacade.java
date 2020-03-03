/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.TblAreas;
import modelo.TblAreas_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.TblPacientes;

/**
 *
 * @author fabricio.diaz
 */
@Stateless
public class TblAreasFacade extends AbstractFacade<TblAreas> {

	@PersistenceContext(unitName = "prueba1PU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public TblAreasFacade() {
		super(TblAreas.class);
	}

	public boolean isTblPacientesCollectionEmpty(TblAreas entity) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<TblAreas> tblAreas = cq.from(TblAreas.class);
		cq.select(cb.literal(1L)).distinct(true).where(cb.equal(tblAreas, entity), cb.isNotEmpty(tblAreas.get(TblAreas_.tblPacientesCollection)));
		return em.createQuery(cq).getResultList().isEmpty();
	}

	public Collection<TblPacientes> findTblPacientesCollection(TblAreas entity) {
		TblAreas mergedEntity = this.getMergedEntity(entity);
		Collection<TblPacientes> tblPacientesCollection = mergedEntity.getTblPacientesCollection();
		tblPacientesCollection.size();
		return tblPacientesCollection;
	}
	
}
