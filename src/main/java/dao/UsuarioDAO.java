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
import model.Usuario;

/**
 *
 * @author ygor.daudt
 */

public class UsuarioDAO implements InterfaceDAO{
    
    private static UsuarioDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static UsuarioDAO getInstance(){
        if(instance == null){
            instance = new UsuarioDAO();
        }
        return instance;
    }
    
    private UsuarioDAO(){
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
        Usuario usuario = (Usuario) objeto;
       try{
           em.getTransaction().begin();
           em.persist(usuario);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    public void alterar(Object objeto) throws Exception {
        Usuario usuario = (Usuario) objeto;
        try{
           em.getTransaction().begin();
           em.merge(usuario);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    public void excluir(Object objeto) throws Exception {
        Usuario usuario = (Usuario) objeto;
        try{
           em.getTransaction().begin();
           Usuario usuarioRemover = em.find(Usuario.class, usuario.getMatricula());
           em.remove(usuarioRemover);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    public List<Usuario> listar() throws Exception {
        Query q = em.createQuery("select u from Usuario u order by u.nome");
        return q.getResultList();
    }
    
    public Usuario buscarMatricula(String matricula) {
        return em.find(Usuario.class, matricula);
    }

}
