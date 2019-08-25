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
import model.Professor;
import model.TCCI;

/**
 *
 * @author ygor.daudt
 */

public class TCCIDAO implements InterfaceDAO{
    
    private static TCCIDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static TCCIDAO getInstance(){
        if(instance == null){
            instance = new TCCIDAO();
        }
        return instance;
    }
    
    private TCCIDAO(){
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
        TCCI tccI = (TCCI) objeto;
        try{
            em.getTransaction().begin();
            em.persist(tccI);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        TCCI tccI = (TCCI) objeto;
        try{
            em.getTransaction().begin();
            em.merge(tccI);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        TCCI tccI = (TCCI) objeto;
        try{
            em.getTransaction().begin();
            TCCI tccIRemover = em.find(TCCI.class, tccI.getId());
            em.remove(tccIRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<TCCI> listar() throws Exception {
        Query q = em.createQuery("select t from TCCI t order by t.id");
        return q.getResultList();
    }

}
