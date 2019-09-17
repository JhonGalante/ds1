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
import model.Professor;
import model.Secretaria;
import model.Usuario;

/**
 *
 * @author jhonata.galante
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
        if (em.find(Usuario.class, matricula) == null) {
            return null;
        }
        return em.find(Usuario.class, matricula);
    }
    
    public Object buscarUsuarioPorMatricula(String matricula) {

        Query alunoQuery = em.createQuery("SELECT a FROM Aluno a WHERE a.usuario.matricula =:matricula");
        Query professorQuery = em.createQuery("SELECT p FROM Professor p WHERE p.usuario.matricula =:matricula");
        Query secretariaQuery = em.createQuery("SELECT s FROM Secretaria s WHERE s.usuario.matricula =:matricula");

        List<Aluno> alunos = alunoQuery.getResultList();
        List<Professor> professores = professorQuery.getResultList();
        List<Secretaria> secretarias = secretariaQuery.getResultList();
        
        if (!alunoQuery.getResultList().isEmpty()) {
            Object objeto = alunoQuery.getSingleResult();
            return objeto;
        }
        
        if(!professorQuery.getResultList().isEmpty()) {
            Object objeto = professorQuery.getSingleResult();
            return objeto;
        }
        
        if(!secretariaQuery.getResultList().isEmpty()) {
            Object objeto = secretariaQuery.getSingleResult();
            return objeto;
        }
        
        return null;

        /*
        
                Query mescla = em.createQuery(
                        "SELECT usu.matricula FROM ("
                            + "SELECT usu.matricula "
                                + "FROM Usuario usu "
                                + "INNER JOIN Aluno alu ON (usu.matricula = alu.usuario.matricula "
                                + "INNER JOIN Professor pro ON (usu.matricula = pro.usuario.matricula "
                                + "INNER JOIN Secretaria sec ON (usu.matricula = sec.usuario.matricula "
                                + "ORDER BY usu.nome)"
                            + "WHERE usu.matricula =:matricula");

                Usuario usuario = (Usuario) mescla.getSingleResult();
        */
   }

}
