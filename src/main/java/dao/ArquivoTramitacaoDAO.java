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
import model.ArquivoTramitacao;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class ArquivoTramitacaoDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        ArquivoTramitacao arquivoTramitacao = (ArquivoTramitacao) objeto;
        em.persist(arquivoTramitacao);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ArquivoTramitacao arquivoTramitacao = (ArquivoTramitacao) objeto;
        em.merge(arquivoTramitacao);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ArquivoTramitacao arquivoTramitacao = (ArquivoTramitacao) objeto;
        em.remove(arquivoTramitacao);
    }

    @Override
    public List<ArquivoTramitacao> listar() throws Exception {
        Query q = em.createQuery("select ar from ArquivoTramitacao ar order by ar.id");
        return q.getResultList();
    }

}