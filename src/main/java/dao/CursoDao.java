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
import model.Curso;

/**
 *
 * @author acg
 */
@Stateless
public class CursoDao {
   
    @PersistenceContext
    EntityManager em;

    public CursoDao() {
    } 
    
    
    public void gravar(Curso curso) {
        em.persist(curso);
    }
    
    public void alterar(Curso curso) {
        em.merge(curso);
    }
    
    public void excluir(Curso curso) {
        em.remove(curso);
    }
    
    public List<Curso> getCursos() {
        Query q = em.createQuery("select c from Curso c order by c.nome");
        return q.getResultList();
    } 
    
    
}
