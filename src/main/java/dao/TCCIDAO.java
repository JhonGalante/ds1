/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;

/**
 *
 * @author jhonata.galante
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
        Query q = em.createQuery("select t from TCCI as t order by t.id");
        return q.getResultList();
    }
    
    public TCCI buscarPorId(Long id){
        Query q = em.createQuery("select t from TCCI as t where t.id = :id")
                .setParameter("id", id);
        try{
            return (TCCI) q.getSingleResult();
        }catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public TCCI buscarPorTermo(TermoCompromisso termo){
        Query q = em.createQuery("select t from TCCI as t where t.termoCompromisso.id = :id")
                .setParameter("id", termo.getId());
        try{
            return (TCCI) q.getSingleResult();
        }catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<TCCI> buscarPorProfessor(Professor professor){
        Query q = em.createQuery("select t from TCCI as t where t.termoCompromisso.professor.id = :professorId")
                .setParameter("professorId", professor.getId());
        
        return q.getResultList();
    }

}
