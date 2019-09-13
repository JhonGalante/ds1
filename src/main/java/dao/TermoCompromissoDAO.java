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
import model.TermoCompromisso;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author jhonata.galante
 */
public class TermoCompromissoDAO implements InterfaceDAO {

    private static TermoCompromissoDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static TermoCompromissoDAO getInstance(){
        if(instance == null){
            instance = new TermoCompromissoDAO();
        }
        return instance;
    }
    
    private TermoCompromissoDAO(){
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
        TermoCompromisso termo = (TermoCompromisso) objeto;
       try{
           em.getTransaction().begin();
           em.persist(termo);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        TermoCompromisso termo = (TermoCompromisso) objeto;
        try{
           em.getTransaction().begin();
           em.merge(termo);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        TermoCompromisso termo = (TermoCompromisso) objeto;
        try{
           em.getTransaction().begin();
           TermoCompromisso termoRemover = em.find(TermoCompromisso.class, termo.getId());
           em.remove(termoRemover);
           em.getTransaction().commit();
       }catch(Exception ex){
           ex.printStackTrace();
           em.getTransaction().rollback();
       }
    }

    @Override
    public List<TermoCompromisso> listar() throws Exception {
        Query q = em.createQuery("from TermoCompromisso as t order by t.id");
        return q.getResultList();
    }

    public TermoCompromisso pesquisarPorAlunoEtapa(Aluno aluno, int etapa) {
        Query q = em.createQuery("from TermoCompromisso as t where t.aluno.usuario.matricula='" + aluno.getUsuario().getMatricula()
                + "' AND t.etapaTcc=" + etapa);
        try {
            return (TermoCompromisso) q.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public TermoCompromisso pesquisarPorAluno(Aluno aluno){
        Query q = em.createQuery("from TermoCompromisso as t where t.aluno.usuario.matricula=" + aluno.getUsuario().getMatricula());
        try {
            return (TermoCompromisso) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<TermoCompromisso> buscarTermosPendentesAceitacao(String matriculaProfessor) throws Exception {
        Query q = em.createQuery("FROM TermoCompromisso as t WHERE t.professor.usuario.matricula =:matriculaProfessor AND t.estadoTermoCompromissoENUM =1"); // 1 = Analise
        return q.getResultList();
    }

}
