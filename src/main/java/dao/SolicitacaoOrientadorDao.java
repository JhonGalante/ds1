/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.SolicitacaoOrientador;

/**
 *
 * @author Windows 10
 */
@Stateless
public class SolicitacaoOrientadorDao extends AbstractFacade<SolicitacaoOrientador> {

    @PersistenceContext(unitName = "br.com.femass_gestao-tcc-femass_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolicitacaoOrientadorDao() {
        super(SolicitacaoOrientador.class);
    }
    
}
