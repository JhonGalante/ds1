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
import model.ArquivoAprovacao;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class ArquivoAprovacaoDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        ArquivoAprovacao arquivoAprovacao = (ArquivoAprovacao) objeto;
        em.persist(arquivoAprovacao);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ArquivoAprovacao arquivoAprovacao = (ArquivoAprovacao) objeto;
        em.merge(arquivoAprovacao);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ArquivoAprovacao arquivoAprovacao = (ArquivoAprovacao) objeto;
        em.remove(arquivoAprovacao);
    }

    @Override
    public List<Object> listar() throws Exception {
        Query q = em.createQuery("select ar from ArquivoAprovacao ar order by ar.id");
        return q.getResultList();
    }

}
