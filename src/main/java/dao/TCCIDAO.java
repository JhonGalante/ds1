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
import model.TCCI;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class TCCIDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        TCCI tccI = (TCCI) objeto;
        em.persist(tccI);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        TCCI tccI = (TCCI) objeto;
        em.merge(tccI);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        TCCI tccI = (TCCI) objeto;
        em.remove(tccI);
    }

    @Override
    public List<Object> listar() throws Exception {
        Query q = em.createQuery("select t from TCCI t order by t.id");
        return q.getResultList();
    }

}
