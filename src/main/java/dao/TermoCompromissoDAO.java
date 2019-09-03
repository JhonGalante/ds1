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
import model.TermoCompromisso;

/**
 *
 * @author jhonata.galante
 */

public class TermoCompromissoDAO implements InterfaceDAO{
    
    private static TermoCompromissoDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static TermoCompromissoDAO getInstance(){
        if(instance == null){
            instance = new TermoCompromissoDAO();
        }
        return instance;
    }
    
    private TermoCompromissoDAO(){
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
        TermoCompromisso termoCompromisso = (TermoCompromisso) objeto;
        try{
            em.getTransaction().begin();
            em.persist(termoCompromisso);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        TermoCompromisso termoCompromisso = (TermoCompromisso) objeto;
        try{
            em.getTransaction().begin();
            em.merge(termoCompromisso);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        TermoCompromisso termoCompromisso = (TermoCompromisso) objeto;
        try{
            em.getTransaction().begin();
            TermoCompromisso termoCompromissoRemover = em.find(TermoCompromisso.class, termoCompromisso.getId());
            em.remove(termoCompromissoRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<TermoCompromisso> listar() throws Exception {
        Query q = em.createQuery("select t from TermoCompromisso t order by t.id");
        return q.getResultList();
    }
    
    public List<TermoCompromisso> buscarTermosPendentesAceitacao(String matriculaProfessor) throws Exception {
        Query q = em.createQuery("SELECT t FROM TermoCompromisso t WHERE t.professor.usuario.matricula =:matriculaProfessor AND t.estadoTermoCompromissoENUM =1"); // 1 = Analise
        return q.getResultList();
    }
}
