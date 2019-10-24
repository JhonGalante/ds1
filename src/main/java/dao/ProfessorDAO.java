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

/**
 *
 * @author jhonata.galante
 */

public class ProfessorDAO implements InterfaceDAO{
    
    private static ProfessorDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static ProfessorDAO getInstance(){
        if(instance == null){
            instance = new ProfessorDAO();
        }
        return instance;
    }
    
    private ProfessorDAO(){
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
        Professor professor = (Professor) objeto;
        try{
            em.getTransaction().begin();
            em.persist(professor);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        Professor professor = (Professor) objeto;
        try{
            em.getTransaction().begin();
            em.merge(professor);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        Professor professor = (Professor) objeto;
        try{
            em.getTransaction().begin();
            Professor professorRemover = em.find(Professor.class, professor.getId());
            em.remove(professorRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<Professor> listar() throws Exception {
        Query q = em.createQuery("select p from Professor p order by p.usuario.nome");
        return q.getResultList();
    }
    
    public Professor buscarMatricula(String matricula) {
        Query q = em.createQuery("SELECT p FROM Professor p WHERE p.usuario.matricula =:matricula")
                .setParameter("matricula", matricula);
        Professor professor = (Professor) q.getSingleResult();
        return professor;
    }

}
