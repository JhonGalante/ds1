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
import model.MovimentacaoTCC;

/**
 *
 * @author jhonata.galante
 */

public class MovimentacaoTCCDAO implements InterfaceDAO{
    
    private static MovimentacaoTCCDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static MovimentacaoTCCDAO getInstance(){
        if(instance == null){
            instance = new MovimentacaoTCCDAO();
        }
        return instance;
    }
    
    private MovimentacaoTCCDAO(){
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
        MovimentacaoTCC movimentacaoTCCI = (MovimentacaoTCC) objeto;
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
        MovimentacaoTCC movimentacaoTCCI = (MovimentacaoTCC) objeto;
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
        MovimentacaoTCC movimentacaoTCCI = (MovimentacaoTCC) objeto;
        try{
            em.getTransaction().begin();
            MovimentacaoTCC movimentacaoTCCIRemover = em.find(MovimentacaoTCC.class, movimentacaoTCCI.getId());
            em.remove(movimentacaoTCCIRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<MovimentacaoTCC> listar() throws Exception {
        Query q = em.createQuery("select m from MovimentacaoTCCI m order by m.id");
        return q.getResultList();
    }

}
