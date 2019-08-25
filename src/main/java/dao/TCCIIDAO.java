/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.TCCI;
import model.TCCII;

/**
 *
 * @author ygor.daudt
 */

public class TCCIIDAO implements InterfaceDAO{
    
    private static TCCIIDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static TCCIIDAO getInstance(){
        if(instance == null){
            instance = new TCCIIDAO();
        }
        return instance;
    }
    
    private TCCIIDAO(){
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
        TCCII tccII = (TCCII) objeto;
        try{
            em.getTransaction().begin();
            em.persist(tccII);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        TCCII tccII = (TCCII) objeto;
        try{
            em.getTransaction().begin();
            em.merge(tccII);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        TCCII tccII = (TCCII) objeto;
        try{
            em.getTransaction().begin();
            TCCII tccIIRemover = em.find(TCCII.class, tccII.getId());
            em.remove(tccIIRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<TCCII> listar() throws Exception {
        Query q = em.createQuery("select t from TCCII t order by t.id");
        return q.getResultList();
    }

}
