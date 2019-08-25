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
import model.ApresentacaoTCC;

/**
 *
 * @author jhonata.galante
 */

public class ApresentacaoTCCDAO implements InterfaceDAO{
    
    private static ApresentacaoTCCDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static ApresentacaoTCCDAO getInstance(){
        if(instance == null){
            instance = new ApresentacaoTCCDAO();
        }
        return instance;
    }
    
    private ApresentacaoTCCDAO(){
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
        ApresentacaoTCC apresentacaoTCC = (ApresentacaoTCC) objeto;
        try{
            em.getTransaction().begin();
            em.persist(apresentacaoTCC);
            em.getTransaction().commit();
        }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
        }
        
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        ApresentacaoTCC apresentacaoTCC = (ApresentacaoTCC) objeto;
        try{
            em.getTransaction().begin();
            em.merge(apresentacaoTCC);
            em.getTransaction().commit();
        }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        ApresentacaoTCC apresentacaoTCC = (ApresentacaoTCC) objeto;
        try{
            em.getTransaction().begin();
            ApresentacaoTCC apresentacaoRemover = em.find(ApresentacaoTCC.class, apresentacaoTCC.getId());
            em.remove(apresentacaoRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
        }
    }

    @Override
    public List<ApresentacaoTCC> listar() throws Exception {
        Query q = em.createQuery("select ap from ApresentacaoTCC ap order by ap.dataApresentacao");
        return q.getResultList();
    }

}
