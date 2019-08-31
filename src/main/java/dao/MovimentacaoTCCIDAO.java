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
import model.MovimentacaoTCCI;

/**
 *
 * @author jhonata.galante
 */

public class MovimentacaoTCCIDAO implements InterfaceDAO{
    
    private static MovimentacaoTCCIDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static MovimentacaoTCCIDAO getInstance(){
        if(instance == null){
            instance = new MovimentacaoTCCIDAO();
        }
        return instance;
    }
    
    private MovimentacaoTCCIDAO(){
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
        MovimentacaoTCCI movimentacaoTCCI = (MovimentacaoTCCI) objeto;
        try{
            em.getTransaction().begin();
            em.persist(movimentacaoTCCI);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        MovimentacaoTCCI movimentacaoTCCI = (MovimentacaoTCCI) objeto;
        try{
            em.getTransaction().begin();
            em.merge(movimentacaoTCCI);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        MovimentacaoTCCI movimentacaoTCCI = (MovimentacaoTCCI) objeto;
        try{
            em.getTransaction().begin();
            MovimentacaoTCCI movimentacaoTCCIRemover = em.find(MovimentacaoTCCI.class, movimentacaoTCCI.getId());
            em.remove(movimentacaoTCCIRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<MovimentacaoTCCI> listar() throws Exception {
        Query q = em.createQuery("select m from MovimentacaoTCCI m order by m.id");
        return q.getResultList();
    }

}
