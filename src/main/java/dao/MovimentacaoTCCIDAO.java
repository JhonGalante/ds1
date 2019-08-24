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
import model.MovimentacaoTCCI;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class MovimentacaoTCCIDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        MovimentacaoTCCI movimentacaoTCCI = (MovimentacaoTCCI) objeto;
        em.persist(movimentacaoTCCI);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        MovimentacaoTCCI movimentacaoTCCI = (MovimentacaoTCCI) objeto;
        em.merge(movimentacaoTCCI);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        MovimentacaoTCCI movimentacaoTCCI = (MovimentacaoTCCI) objeto;
        em.remove(movimentacaoTCCI);
    }

    @Override
    public List<MovimentacaoTCCI> listar() throws Exception {
        Query q = em.createQuery("select m from MovimentacaoTCCI m order by m.id");
        return q.getResultList();
    }

}
