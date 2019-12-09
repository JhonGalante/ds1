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
    
    public TCCII buscarPorTermo(TermoCompromisso termo) throws Exception {
        Query q = em.createQuery("select t from TCCII t where t.termoCompromisso.id = :id")
                .setParameter("id", termo.getId());
        try{
            return (TCCII) q.getSingleResult();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;  
        }
    }
    
    public TCCII buscarPorId(Long id){
        Query q = em.createQuery("select t from TCCII as t where t.id = :id")
                .setParameter("id", id);
        try{
            return (TCCII) q.getSingleResult();
        }catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<TCCII> buscarPorProfessor(Professor professor) {
        Query q = em.createQuery("select t from TCCII as t where t.termoCompromisso.professor.id = :professorId")
                .setParameter("professorId", professor.getId());
        
        return q.getResultList();
    }
    

}
