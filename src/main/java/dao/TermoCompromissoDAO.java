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
import model.TermoCompromisso;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class TermoCompromissoDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        TermoCompromisso termoCompromisso = (TermoCompromisso) objeto;
        em.persist(termoCompromisso);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        TermoCompromisso termoCompromisso = (TermoCompromisso) objeto;
        em.merge(termoCompromisso);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        TermoCompromisso termoCompromisso = (TermoCompromisso) objeto;
        em.remove(termoCompromisso);
    }

    @Override
    public List<TermoCompromisso> listar() throws Exception {
        Query q = em.createQuery("select t from TermoCompromisso t order by t.id");
        return q.getResultList();
    }

}
