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
import model.Aluno;
import model.Curso;

/**
 *
 * @author acg
 */
@Stateless
public class AlunoDao {
   
    @PersistenceContext
    EntityManager em;

    public AlunoDao() {
    } 
    
    
    public void gravar(Aluno aluno) {
        em.persist(aluno);
    }
    
    public void alterar(Aluno aluno) {
        em.merge(aluno);
    }
    
    public void excluir(Aluno aluno) {
        em.remove(aluno);
    }
    
    public List<Aluno> getAlunos() {
        Query q = em.createQuery("select a from Aluno a order by a.nome");
        return q.getResultList();
    } 
    
    public List<Aluno> getAlunosUmCurso(Curso curso) {
        Query q = em.createQuery("select a from Aluno a where a.curso.id = :id");
        q.setParameter("id", curso.getId());
        return q.getResultList();
    }
    
}
