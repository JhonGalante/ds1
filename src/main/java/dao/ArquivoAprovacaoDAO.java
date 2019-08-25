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
import model.ArquivoAprovacao;

/**
 *
 * @author jhonata.galante
 */

public class ArquivoAprovacaoDAO implements InterfaceDAO{
    
    private static ArquivoAprovacaoDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static ArquivoAprovacaoDAO getInstance(){
        if(instance == null){
            instance = new ArquivoAprovacaoDAO();
        }
        return instance;
    }
    
    private ArquivoAprovacaoDAO(){
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
        ArquivoAprovacao arquivoAprovacao = (ArquivoAprovacao) objeto;
        try{
            em.getTransaction().begin();
            em.persist(arquivoAprovacao);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ArquivoAprovacao arquivoAprovacao = (ArquivoAprovacao) objeto;
        try{
            em.getTransaction().begin();
            em.merge(arquivoAprovacao);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ArquivoAprovacao arquivoAprovacao = (ArquivoAprovacao) objeto;
        try{
            em.getTransaction().begin();
            ArquivoAprovacao arquivoExcluir = em.find(ArquivoAprovacao.class, arquivoAprovacao.getId());
            em.remove(arquivoExcluir);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<ArquivoAprovacao> listar() throws Exception {
        Query q = em.createQuery("select ar from ArquivoAprovacao ar order by ar.id");
        return q.getResultList();
    }

}
