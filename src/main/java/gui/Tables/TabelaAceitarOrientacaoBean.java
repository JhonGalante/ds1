/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Tables;

import dao.SolicitacaoOrientadorDao;
import dao.TccInicioDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.Professor;
import model.SolicitacaoOrientador;
import model.TccInicio;
import model.Usuario;

/**
 *
 * @author Jardelmc
 */
@ManagedBean
@ViewScoped
@SessionScoped
public class TabelaAceitarOrientacaoBean implements Serializable{

    @EJB
    SolicitacaoOrientadorDao daoSolicitarOrientador;
    @EJB
    TccInicioDao daoTccInicio;

    private Professor orientador;
    private Aluno aluno;
    private SolicitacaoOrientador solicitacao;
    private TccInicio termoCompromisso;

   
    public List<SolicitacaoOrientador> getSolicitacoesOrientacao() {
        List<SolicitacaoOrientador> listaTodasSolicitacoes = daoSolicitarOrientador.findAll();
        List<SolicitacaoOrientador> listaRetorno = new ArrayList<>();
        
        for (SolicitacaoOrientador s : listaTodasSolicitacoes) {
            if (s.getOrientador().getMatricula().equals(orientador.getMatricula())) {
                listaRetorno.add(s);
            }
        }
        return listaRetorno;
    }
    
    public void criarTermoDeCompromisso() {
        termoCompromisso = new TccInicio();
        termoCompromisso.setAluno(solicitacao.getAluno());
        termoCompromisso.setProfessorTcc(orientador);
        termoCompromisso.setOrientador(orientador);
        termoCompromisso.setTitulo(solicitacao.getTituloTcc());
        daoTccInicio.create(termoCompromisso);
        daoSolicitarOrientador.remove(solicitacao);
        saveMessage();
    }
    
    @PostConstruct
    public void init() {
        orientador = new Usuario().getProfessor();
    }
    
    

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public SolicitacaoOrientador getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoOrientador solicitacao) {
        this.solicitacao = solicitacao;
    }

    public SolicitacaoOrientadorDao getDaoSolicitarOrientador() {
        return daoSolicitarOrientador;
    }

    public void setDaoSolicitarOrientador(SolicitacaoOrientadorDao daoSolicitarOrientador) {
        this.daoSolicitarOrientador = daoSolicitarOrientador;
    }

    public TccInicioDao getDaoTccInicio() {
        return daoTccInicio;
    }

    public void setDaoTccInicio(TccInicioDao daoTccInicio) {
        this.daoTccInicio = daoTccInicio;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public TccInicio getTermoCompromisso() {
        return termoCompromisso;
    }

    public void setTermoCompromisso(TccInicio termoCompromisso) {
        this.termoCompromisso = termoCompromisso;
    }
    
    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Informações Gravadas.") );
    }
}
