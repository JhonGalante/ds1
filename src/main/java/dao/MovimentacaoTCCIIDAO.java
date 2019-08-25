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
import model.MovimentacaoTCCII;

/**
 *
 * @author jhonata.galante
 */

public class MovimentacaoTCCIIDAO implements InterfaceDAO{
    
    private static MovimentacaoTCCIIDAO instance;
    protected EntityManager em;
    
    //Singleton
    public static MovimentacaoTCCIIDAO getInstance(){
        if(instance == null){
            instance = new MovimentacaoTCCIIDAO();
        }
        return instance;
    }
    
    private MovimentacaoTCCIIDAO(){
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
        MovimentacaoTCCII movimentacaoTCCII = (MovimentacaoTCCII) objeto;
        try{
            em.getTransaction().begin();
            em.persist(movimentacaoTCCII);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void alterar(Object objeto) throws Exception {
        MovimentacaoTCCII movimentacaoTCCII = (MovimentacaoTCCII) objeto;
        try{
            em.getTransaction().begin();
            em.merge(movimentacaoTCCII);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void excluir(Object objeto) throws Exception {
        MovimentacaoTCCII movimentacaoTCCII = (MovimentacaoTCCII) objeto;
        try{
            em.getTransaction().begin();
            MovimentacaoTCCII movimentacaoTCCIIRemover = em.find(MovimentacaoTCCII.class, movimentacaoTCCII.getId());
            em.remove(movimentacaoTCCIIRemover);
            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<MovimentacaoTCCII> listar() throws Exception {
        Query q = em.createQuery("select m from MovimentacaoTCCII m order by m.id");
        return q.getResultList();
    }

}
