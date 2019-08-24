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
import model.Professor;

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class ProfessorDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        Professor professor = (Professor) objeto;
        em.persist(professor);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        Professor professor = (Professor) objeto;
        em.merge(professor);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        Professor professor = (Professor) objeto;
        em.remove(professor);
    }

    @Override
    public List<Object> listar() throws Exception {
        Query q = em.createQuery("select ap from Professor");
        return q.getResultList();
    }

}
