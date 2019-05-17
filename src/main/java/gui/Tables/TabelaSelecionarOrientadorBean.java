/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Tables;

import dao.ProfessorDao;
import dao.SolicitacaoOrientadorDao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Professor;
import model.SolicitacaoOrientador;
import model.Usuario;

/**
 *
 * @author Jardelmc
 */
@ManagedBean
@ViewScoped
public class TabelaSelecionarOrientadorBean implements Serializable{
    
    @EJB 
    ProfessorDao daoProfessor;
    @EJB
    SolicitacaoOrientadorDao daoSolicitarOrientador;
    
    private Professor orientador;
    private SolicitacaoOrientador solicitacao = new SolicitacaoOrientador();
    private String tema;
    
    public List<Professor> orientadores() {
        return daoProfessor.findAll();
    }
    
    public void definirOrientador() {
        solicitacao.setAluno(new Usuario().getAluno());
        solicitacao.setOrientador(orientador);
        solicitacao.setDescricaoTema(tema);
        daoSolicitarOrientador.create(solicitacao);
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public SolicitacaoOrientador getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoOrientador solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
    
    
    
    
}
