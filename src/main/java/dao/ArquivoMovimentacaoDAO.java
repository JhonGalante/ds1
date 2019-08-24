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
import model.ArquivoMovimentacao;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class ArquivoMovimentacaoDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        ArquivoMovimentacao arquivoMovimentacao = (ArquivoMovimentacao) objeto;
        em.persist(arquivoMovimentacao);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ArquivoMovimentacao arquivoMovimentacao = (ArquivoMovimentacao) objeto;
        em.merge(arquivoMovimentacao);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ArquivoMovimentacao arquivoMovimentacao = (ArquivoMovimentacao) objeto;
        em.remove(arquivoMovimentacao);
    }

    @Override
    public List<ArquivoMovimentacao> listar() throws Exception {
        Query q = em.createQuery("select ar from ArquivoMovimentacao ar order by ar.id");
        return q.getResultList();
    }

}