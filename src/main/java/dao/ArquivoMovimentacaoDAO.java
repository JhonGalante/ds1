/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.ArquivoMovimentacao;

/**
 *
 * @author jhonata.galante
 */

public class ArquivoMovimentacaoDAO implements InterfaceDAO{
    
    private static ArquivoMovimentacaoDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static ArquivoMovimentacaoDAO getInstance(){
        if(instance == null){
            instance = new ArquivoMovimentacaoDAO();
        }
        return instance;
    }
    
    private ArquivoMovimentacaoDAO(){
        em = getEntityManager();
    }
    
    private EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestaotccPU");
        if(em == null){
            em = factory.createEntityManager();
        }
        return em;
    }

    @Override
    public void incluir(Object objeto) throws Exception {
        ArquivoMovimentacao arquivoMovimentacao = (ArquivoMovimentacao) objeto;
        try{
            em.getTransaction().begin();
            em.persist(arquivoMovimentacao);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ArquivoMovimentacao arquivoMovimentacao = (ArquivoMovimentacao) objeto;
        try{
            em.getTransaction().begin();
            em.merge(arquivoMovimentacao);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ArquivoMovimentacao arquivoMovimentacao = (ArquivoMovimentacao) objeto;
        try{
            em.getTransaction().begin();
            ArquivoMovimentacao arquivoMovimentacaoRemover = em.find(ArquivoMovimentacao.class, arquivoMovimentacao.getId());
            em.remove(arquivoMovimentacaoRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<ArquivoMovimentacao> listar() throws Exception {
        Query q = em.createQuery("select ar from ArquivoMovimentacao ar order by ar.id");
        return q.getResultList();
    }

}
