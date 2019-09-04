/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TermoCompromissoDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
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
    private GuiSessao guiSessao;
    
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();
    private ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    private TermoCompromissoDAO termoCompromissoDAO = TermoCompromissoDAO.getInstance();
    
    public GuiSolicitarOrientador() throws Exception {
        this.professoresOrientadores = professorDAO.listar();
    }
    
    public void iniciarListaProfessoresOrientadores() throws IOException {
        try {
            professoresOrientadores = professorDAO.listar();
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
        }
        
        if (validarSolicitacao(aluno)) {
            termoCompromisso = new TermoCompromisso();
            termoCompromisso.setAluno(aluno);
            termoCompromisso.setProfessor(professorOrientador);
            termoCompromisso.setTema(tema);
            termoCompromisso.setTitulo(titulo);
            termoCompromisso.setDataHoraSolicitacao(LocalDateTime.now());
            termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_ANALISE);
            try {
                termoCompromissoDAO.incluir(termoCompromisso);
            } catch(Exception ex) {
                Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        ext.redirect("home.xhtml");
        limparCampos();
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
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (TermoCompromisso termo : termosCompromisso) {
            if (termo.getAluno().equals(aluno)
                && !(termo.getEstadoTermoCompromissoENUM().equals(EstadoTermoCompromissoENUM.SOLICITACAO_RECUSADA))) {
                    mensagemRecusa("O aluno " + aluno.getUsuario().getNome()
                            + " já possui uma solicitação em análise ou TCC em andamento.");
                    return false;
            }
        }
        mensagemConfirma("Solicitação realizada com sucesso!");
        return true;
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

    public GuiSessao getGuiSessao() {
        return guiSessao;
    }

    public void setGuiSessao(GuiSessao guiSessao) {
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


    
}
