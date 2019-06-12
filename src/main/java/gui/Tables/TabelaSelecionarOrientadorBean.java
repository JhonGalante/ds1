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
public class TabelaSelecionarOrientadorBean implements Serializable {

    @EJB
    ProfessorDao daoProfessor;
    @EJB
    SolicitacaoOrientadorDao daoSolicitarOrientador;

    private Professor orientador;
    private SolicitacaoOrientador solicitacao = new SolicitacaoOrientador();
    private String tema;
    private String titulo;
    private String mensagem;

    public List<Professor> orientadores() {
        return daoProfessor.findAll();
    }

    public void definirOrientador() {
        try {
            if (checaSeAlunoJaSolicitouOrientador()) {
                mensagemJaPossuiSolicitacao();
            } else if (titulo.trim().equals("")) {
                mensagemFaltaTitulo();
            } else if (tema.trim().equals("")) {
                mensagemFaltaDescricao();
            } else if (orientador == null) {
                mensagemFaltaOrientador();
            } else {
                solicitacao.setAluno(new Usuario().getAluno());
                solicitacao.setOrientador(orientador);
                solicitacao.setDescricaoTema(tema);
                solicitacao.setTituloTcc(titulo);
                daoSolicitarOrientador.create(solicitacao);
                mensagemSucesso();
            }

        } catch (Exception ex) {
            mensagemFalha();
        }
    }

    public boolean checaSeAlunoJaSolicitouOrientador() {
        List<SolicitacaoOrientador> solicitacoes = daoSolicitarOrientador.findAll();
        for (SolicitacaoOrientador s : solicitacoes) {
            if (s.getAluno().getMatricula().equals(new Usuario().getAluno().getMatricula())) {
                return true;
            }
        }
        return false;
    }

    public void mensagemSucesso() {
        mensagem = "Solicitação registrada com sucesso";
    }

    public void mensagemJaPossuiSolicitacao() {
        mensagem = "Erro. Você já possui uma solicitação em aberto";
    }

    public void mensagemFalha() {
        mensagem = "Erro desconhecido";
    }

    public void mensagemFaltaOrientador() {
        mensagem = "Por favor, selecione um orientador";
    }

    public void mensagemFaltaTitulo() {
        mensagem = "Por favor, escreva o título do seu TCC. Pode ser um título temporário";
    }

    public void mensagemFaltaDescricao() {
        mensagem = "Por favor, escreva um pouco sobre seu tema";
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

    public ProfessorDao getDaoProfessor() {
        return daoProfessor;
    }

    public void setDaoProfessor(ProfessorDao daoProfessor) {
        this.daoProfessor = daoProfessor;
    }

    public SolicitacaoOrientadorDao getDaoSolicitarOrientador() {
        return daoSolicitarOrientador;
    }

    public void setDaoSolicitarOrientador(SolicitacaoOrientadorDao daoSolicitarOrientador) {
        this.daoSolicitarOrientador = daoSolicitarOrientador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
