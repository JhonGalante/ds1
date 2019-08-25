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
import model.ArquivoTramitacao;

/**
 *
 * @author jhonata.galante
 */

public class ArquivoTramitacaoDAO implements InterfaceDAO{
    
    private static ArquivoTramitacaoDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static ArquivoTramitacaoDAO getInstance(){
        if(instance == null){
            instance = new ArquivoTramitacaoDAO();
        }
        return instance;
    }
    
    private ArquivoTramitacaoDAO(){
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
        ArquivoTramitacao arquivoTramitacao = (ArquivoTramitacao) objeto;
        try{
            em.getTransaction().begin();
            em.persist(arquivoTramitacao);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ArquivoTramitacao arquivoTramitacao = (ArquivoTramitacao) objeto;
        try{
            em.getTransaction().begin();
            em.merge(arquivoTramitacao);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ArquivoTramitacao arquivoTramitacao = (ArquivoTramitacao) objeto;
        try{
            em.getTransaction().begin();
            ArquivoTramitacao arquivoTramitacaoRemover = em.find(ArquivoTramitacao.class, arquivoTramitacao.getId());
            em.remove(arquivoTramitacaoRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<ArquivoTramitacao> listar() throws Exception {
        Query q = em.createQuery("select ar from ArquivoTramitacao ar order by ar.id");
        return q.getResultList();
    }

}
