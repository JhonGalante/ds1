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
import model.Secretaria;

/**
 *
 * @author jhonata.galante
 */

public class SecretariaDAO implements InterfaceDAO{
    
    private static SecretariaDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static SecretariaDAO getInstance(){
        if(instance == null){
            instance = new SecretariaDAO();
        }
        return instance;
    }
    
    private SecretariaDAO(){
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
        Secretaria secretaria = (Secretaria) objeto;
        try{
            em.getTransaction().begin();
            em.persist(secretaria);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        Secretaria secretaria = (Secretaria) objeto;
        try{
            em.getTransaction().begin();
            em.merge(secretaria);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        Secretaria secretaria = (Secretaria) objeto;
        try{
            em.getTransaction().begin();
            Secretaria secretariaRemover = em.find(Secretaria.class, secretaria.getId());
            em.remove(secretariaRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<Secretaria> listar() throws Exception {
        Query q = em.createQuery("select s from Secretaria s order by s.usuario.nome");
        return q.getResultList();
    }

}
