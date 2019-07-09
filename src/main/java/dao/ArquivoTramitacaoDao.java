/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.ArquivoTcc;
import model.ArquivoTramitacao;

/**
 *
 * @author jhona
 */

@Stateless
public class ArquivoTramitacaoDao extends AbstractFacade<ArquivoTramitacao>{
    
    @PersistenceContext(unitName = "br.com.femass_gestao-tcc-femass_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArquivoTramitacaoDao() {
        super(ArquivoTramitacao.class);
    }
    
}
