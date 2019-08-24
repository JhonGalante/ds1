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
import model.ApresentacaoTCC;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class ApresentacaoTCCDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        ApresentacaoTCC apresentacaoTCC = (ApresentacaoTCC) objeto;
        em.persist(apresentacaoTCC);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ApresentacaoTCC apresentacaoTCC = (ApresentacaoTCC) objeto;
        em.merge(apresentacaoTCC);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ApresentacaoTCC apresentacaoTCC = (ApresentacaoTCC) objeto;
        em.remove(apresentacaoTCC);
    }

    @Override
    public List<ApresentacaoTCC> listar() throws Exception {
        Query q = em.createQuery("select ap from ApresentacaoTCC ap order by ap.dataApresentacao");
        return q.getResultList();
    }

}
