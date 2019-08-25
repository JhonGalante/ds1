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
import model.Aluno;
import model.Usuario;

/**
 *
 * @author jhonata.galante
 */

public class AlunoDAO implements InterfaceDAO{
    
    private static AlunoDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static AlunoDAO getInstance(){
        if(instance == null){
            instance = new AlunoDAO();
        }
        return instance;
    }
    
    private AlunoDAO(){
        em = getEntityManager();
    }
    
    private EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestaotccPU");
        if(em == null){
            em = factory.createEntityManager();
        }
        return em;
    }

    public void incluir(Object objeto) throws Exception {
        Aluno aluno = (Aluno) objeto;
       try{
           em.getTransaction().begin();
           em.persist(aluno);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    public void alterar(Object objeto) throws Exception {
        Aluno aluno = (Aluno) objeto;
        try{
           em.getTransaction().begin();
           em.merge(aluno);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    public void excluir(Object objeto) throws Exception {
        Aluno aluno = (Aluno) objeto;
        try{
           em.getTransaction().begin();
           Aluno alunoRemover = em.find(Aluno.class, aluno.getId());
           em.remove(alunoRemover);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    public List<Usuario> listar() throws Exception {
        Query q = em.createQuery("select a from Aluno a order by a.id");
        return q.getResultList();
    }
    
    public Aluno buscarMatricula(long id) {
        return em.find(Aluno.class, id);
    }

}
