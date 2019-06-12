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
import model.Aluno;
import model.TccInicio;

/**
 *
 * @author Windows 10
 */
@Stateless
public class TccInicioDao extends AbstractFacade<TccInicio> {

    @PersistenceContext(unitName = "br.com.femass_gestao-tcc-femass_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TccInicioDao() {
        super(TccInicio.class);
    }
    
    public TccInicio tccInicio(Aluno aluno) {
        List<TccInicio> listaInformacoes = super.findAll();
        for(TccInicio t : listaInformacoes) {
            if(aluno.getMatricula().equals(t)) {
                return t;
            }
        }
        return null;
    }
    
}
