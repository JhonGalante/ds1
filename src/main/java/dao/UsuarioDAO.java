/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Usuario;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class UsuarioDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        Usuario usuario = (Usuario) objeto;
        em.persist(usuario);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        Usuario usuario = (Usuario) objeto;
        em.merge(usuario);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        Usuario usuario = (Usuario) objeto;
        em.remove(usuario);
    }

    @Override
    public List<Usuario> listar() throws Exception {
        Query q = em.createQuery("select u from Usuario u order by u.nome");
        return q.getResultList();
    }
    
    public Usuario buscarMatricula(String matricula) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.matricula = :matricula");
        Usuario usuario = (Usuario) q.getSingleResult();
        return usuario;
    }

}
