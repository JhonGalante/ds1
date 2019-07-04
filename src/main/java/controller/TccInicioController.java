/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TccInicioDao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import model.Aluno;
import model.TccInicio;

/**
 *
 * @author Jardelmc
 */
@SessionScoped
@ManagedBean
public class TccInicioController implements Serializable{
    
    @EJB
    TccInicioDao daoTccInicio;
    
    public TccInicio getInformacoesTccAluno(Aluno aluno){
        List<TccInicio> listaTcc = daoTccInicio.findAll();
        for(TccInicio t : listaTcc) {
            if(aluno.getMatricula().equals(t.getAluno().getMatricula())){
                return t;
            }
        }
        return null;
    }
    
}
