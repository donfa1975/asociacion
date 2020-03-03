/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelo.TblTipoDiagnostico;

/**
 *
 * @author fabricio.diaz
 */
public class diagnosticosControl {
    EntityManager em;
    private String _codigo;
    private int _id;
    private String _diag;
    private TblTipoDiagnostico _tipo;
    
    
    public diagnosticosControl() {
        inicializar();
    }
    private void inicializar()
    {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("prueba1PU");
        em = emf.createEntityManager();
    }

    public void buscaCodigo()
    {
        Query q=em.createQuery("select c from TblTipoDiagnostico c where c.codigo=:cod");
        q.setParameter("cod", _codigo);
        List<TblTipoDiagnostico> lista=q.getResultList();
        if(lista.isEmpty())//no encontro nada
        {
            _id=0;
           this._diag="ND";
        }
        else
        {
            _tipo=lista.get(0);
            _id=_tipo.getIdTipoDiagnostico();
            _diag=_tipo.getDiagnostico();
        }
    }
    
    /**
     * @return the _codigo
     */
    public String getCodigo() {
        return _codigo;
    }

    /**
     * @param _codigo the _codigo to set
     */
    public void setCodigo(String _codigo) {
        this._codigo = _codigo;
    }

    /**
     * @return the _id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void setId(int _id) {
        this._id = _id;
    }

    /**
     * @return the _diag
     */
    public String getDiag() {
        return _diag;
    }

    /**
     * @param _diag the _diag to set
     */
    public void setDiag(String _diag) {
        this._diag = _diag;
    }

    /**
     * @return the _tipo
     */
    public TblTipoDiagnostico getTipo() {
        return _tipo;
    }

    /**
     * @param _tipo the _tipo to set
     */
    public void setTipo(TblTipoDiagnostico _tipo) {
        this._tipo = _tipo;
    }
    
    
}
