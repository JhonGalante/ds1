/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.TCCII;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class TCCIIDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        TCCII tccII = (TCCII) objeto;
        em.persist(tccII);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        TCCII tccII = (TCCII) objeto;
        em.merge(tccII);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        TCCII tccII = (TCCII) objeto;
        em.remove(tccII);
    }

    @Override
    public List<Object> listar() throws Exception {
        Query q = em.createQuery("select t from TCCII t order by t.id");
        return q.getResultList();
    }

}
