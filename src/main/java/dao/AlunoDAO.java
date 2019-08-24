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

/**
 *
 * @author ygor.daudt
 */

@Stateless
public class AlunoDAO implements InterfaceDAO{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void incluir(Object objeto) throws Exception {
        Aluno aluno = (Aluno) objeto;
        em.persist(aluno);
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        Aluno aluno = (Aluno) objeto;
        em.merge(aluno);
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        Aluno aluno = (Aluno) objeto;
        em.remove(aluno);
    }

    @Override
    public List<Object> listar() throws Exception {
        Query q = em.createQuery("select a from Aluno a order by a.usuario.nome");
        return q.getResultList();
    }

}
