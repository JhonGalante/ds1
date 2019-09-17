/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import helper.Sessao;
import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TermoCompromissoDAO;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.EstadoTermoCompromissoENUM;
import model.Professor;
import model.TermoCompromisso;

/**
 *
 * @author Ygor
 */
@ManagedBean
public class GuiSolicitarOrientador {
    
    private List<Professor> professoresOrientadores;
    private List<TermoCompromisso> termosCompromisso;
    
    private Aluno aluno;
    private Professor professorOrientador;
    private TermoCompromisso termoCompromisso;
    private String tema;
    private String titulo;
    private int etapa;
    private Sessao guiSessao;
    private String mensagem;
    
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();
    private ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    private TermoCompromissoDAO termoCompromissoDAO = TermoCompromissoDAO.getInstance();
    
    public GuiSolicitarOrientador() throws Exception {
        this.professoresOrientadores = professorDAO.listar();
        this.guiSessao = new Sessao();
    }
    
    public void iniciarListaProfessoresOrientadores() throws IOException {
        try {
            professoresOrientadores = professorDAO.listar();
            termosCompromisso = termoCompromissoDAO.listar();
            aluno = alunoDAO.buscarMatricula(guiSessao.getUsuarioSessao().getMatricula());
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limparCampos() {
        titulo = null;
        tema = null;
        aluno = null;
        professorOrientador = null;
        
    }
    
    public void realizarSolicitacao() throws Exception{
        try {
            aluno = alunoDAO.buscarMatricula(guiSessao.getUsuarioSessao().getMatricula());
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if (validarSolicitacao(aluno)) {
            termoCompromisso = new TermoCompromisso();
            termoCompromisso.setAluno(aluno);
            termoCompromisso.setProfessor(professorOrientador);
            termoCompromisso.setEtapaTcc(aluno.getEtapaTcc());
            termoCompromisso.setTema(tema);
            termoCompromisso.setTitulo(titulo);
            termoCompromisso.setDataHoraSolicitacao(LocalDate.now());
            termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_ANALISE);
            try {
                termoCompromissoDAO.incluir(termoCompromisso);
                mensagemConfirma("Solicitação realizada com sucesso!");
                limparCampos();
            } catch(Exception ex) {
                Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
     * Verifica já existe alguma solicitação no estado 'PENDENTE' ou 'ACEITA'
     * do aluno em questão, de forma a inibir duplicidade
     */
    public Boolean validarSolicitacao(Aluno aluno) throws Exception {
        if (professorOrientador == null) {
            mensagemRecusa("Por favor, selecione um professor.");
            return false;
        }
        try {
            termosCompromisso = termoCompromissoDAO.listar();
            for (TermoCompromisso termo : termosCompromisso) {
                if (termo.getAluno().equals(aluno)
                    && !(termo.getEstadoTermoCompromissoENUM().equals(EstadoTermoCompromissoENUM.SOLICITACAO_RECUSADA))) {
                        mensagemRecusa("O aluno " + aluno.getUsuario().getNome()
                                + " já possui uma solicitação em análise ou TCC em andamento com o professor."
                                + termo.getProfessor().getUsuario().getNome());
                        return false;
                }
            }
            
            return true;
            
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void mensagemConfirma(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Mensagem: " + mensagem) );
    }
    
    public void mensagemRecusa(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Solicitacao Inválida",  "Mensagem: " + mensagem) );
    }
    
    public List<Professor> getProfessoresOrientadores() {
        try {
            iniciarListaProfessoresOrientadores();
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return professoresOrientadores;
    }

    public List<TermoCompromisso> getTermosCompromisso() {
        return termosCompromisso;
    }

    public void setTermosCompromisso(List<TermoCompromisso> termosCompromisso) {
        this.termosCompromisso = termosCompromisso;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getProfessorOrientador() {
        return professorOrientador;
    }

    public void setProfessorOrientador(Professor professorOrientador) {
        this.professorOrientador = professorOrientador;
    }

    public TermoCompromisso getTermoCompromisso() {
        return termoCompromisso;
    }

    public void setTermoCompromisso(TermoCompromisso termoCompromisso) {
        this.termoCompromisso = termoCompromisso;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Sessao getGuiSessao() {
        return guiSessao;
    }

    public void setGuiSessao(Sessao guiSessao) {
        this.guiSessao = guiSessao;
    }

    public ProfessorDAO getProfessorDAO() {
        return professorDAO;
    }

    public void setProfessorDAO(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public TermoCompromissoDAO getTermoCompromissoDAO() {
        return termoCompromissoDAO;
    }

    public void setTermoCompromissoDAO(TermoCompromissoDAO termoCompromissoDAO) {
        this.termoCompromissoDAO = termoCompromissoDAO;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    


    
}
