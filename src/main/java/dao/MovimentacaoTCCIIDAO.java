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

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class MovimentacaoTCCIIDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        MovimentacaoTCCIIDAO movimentacaoTCCII = (MovimentacaoTCCIIDAO) objeto;
        em.persist(movimentacaoTCCII);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        MovimentacaoTCCIIDAO movimentacaoTCCII = (MovimentacaoTCCIIDAO) objeto;
        em.merge(movimentacaoTCCII);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        MovimentacaoTCCIIDAO movimentacaoTCCII = (MovimentacaoTCCIIDAO) objeto;
        em.remove(movimentacaoTCCII);
    }

    @Override
    public List<Object> listar() throws Exception {
        Query q = em.createQuery("select m from MovimentacaoTCCII m order by m.id");
        return q.getResultList();
    }

}
