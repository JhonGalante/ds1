/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProfessorDAO;
import helper.Sessao;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import dao.TermoCompromissoDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.EstadoTccENUM;
import model.EstadoTermoCompromissoENUM;
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;

/**
 *
 * @author ygor.daudt
 */
@ManagedBean
public class GuiAceitarSolicitacaoOrientacao {
    
    private List<TermoCompromisso> termosCompromisso;
    private TermoCompromisso termoCompromisso;
    private TCCI tccI;
    private TCCII tccII;
    private Professor professor;
    
    private Aluno aluno;
    
    private final TermoCompromissoDAO termoCompromissoDAO = TermoCompromissoDAO.getInstance();
    private final TCCIDAO tccIDAO = TCCIDAO.getInstance();
    private final TCCIIDAO tccIIDAO = TCCIIDAO.getInstance();
    private ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    private final Sessao sessao = Sessao.getInstance();
    
    public GuiAceitarSolicitacaoOrientacao() throws Exception {
        termosCompromisso = iniciarListaSolicitacoes();
    }
    
    public List<TermoCompromisso> iniciarListaSolicitacoes() throws IOException, Exception {
        limparSelecao();
        List<TermoCompromisso> termosTemp = new ArrayList<>();
        Professor professorTemp;
        try {
            professorTemp = professorDAO.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
        } catch(Exception ex) {
            Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        // Filtrei por for pois a cláusula WHERE não estava funcionando corretamente no BuscarTermosPendentesAceitacao
        for (TermoCompromisso termo : termoCompromissoDAO.listar()) {
            if ((EstadoTermoCompromissoENUM.SOLICITACAO_ANALISE) == termo.getEstadoTermoCompromissoENUM()
                    && termo.getProfessor().equals(professorTemp)) {
                termosTemp.add(termo);
            }
        }
        return termosTemp;
    }
    
    public void aceitarSolicitacao() throws IOException, Exception {
        //String matriculaAluno = termoCompromisso.getAluno().getUsuario().getMatricula();
        try {
            professor = professorDAO.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
            //termoCompromisso = termoCompromissoDAO.pesquisarPorMatricula(matriculaAluno);
        } catch(Exception ex) {
            Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_ACEITA);
        termoCompromisso.setDataHoraAceiteSolicitacao(LocalDate.now());

        if (termoCompromisso.getEtapaTcc() == 1) {
            tccI = new TCCI();
            tccI.setTermoCompromisso(termoCompromisso);
            tccI.setEstadoTccENUM(EstadoTccENUM.ENTREGA);
            // Coloquei o mesmo professor apenas para realizar testes.
            // Precisamos definir em que momento e como será definido quem será o professor de TCC
            tccI.setProfessorTcc(termoCompromisso.getProfessor());
            try {
                termoCompromissoDAO.alterar(termoCompromisso);
                tccIDAO.incluir(tccI);
                mensagemConfirma("Solicitação ACEITA com sucesso.");
            } catch(Exception ex) {
                Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
                mensagemRecusa("Não foi possível realizar esta operação.");
            }
        }
        if (termoCompromisso.getEtapaTcc() == 2) {
            tccII = new TCCII();
            tccII.setTermoCompromisso(termoCompromisso);
            tccII.setEstadoTccENUM(EstadoTccENUM.ENTREGA);
            tccII.setDispRepo(false);
            // Coloquei o mesmo professor apenas para realizar testes.
            // Precisamos definir em que momento e como será definido quem será o professor de TCC
            tccII.setProfessorTcc(termoCompromisso.getProfessor());
            try {
                termoCompromissoDAO.alterar(termoCompromisso);
                tccIIDAO.incluir(tccII);
                mensagemConfirma("Solicitação ACEITA com sucesso.");
            } catch(Exception ex) {
                Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
                mensagemRecusa("Não foi possível realizar esta operação.");
            }
        }

        termosCompromisso = iniciarListaSolicitacoes();
    }
    
    public void recusarSolicitacao() throws IOException, Exception {
        termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_RECUSADA);
        termoCompromisso.setDataHoraRecusaSolicitacao(LocalDate.now());
        try {
            termoCompromissoDAO.alterar(termoCompromisso);
            mensagemConfirma("Solicitação RECUSADA com sucesso.");
        } catch(Exception ex) {
            Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
            mensagemRecusa("Não foi possível realizar esta operação.");
        }
        termosCompromisso = iniciarListaSolicitacoes();
    }
    
    public void limparSelecao() {
        termoCompromisso = null;
        aluno = null;
        tccI = null;
        tccII = null;
    }
   
    public void mensagemConfirma(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso",  "Mensagem: " + mensagem) );
    }
    
    public void mensagemRecusa(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Solicitacao Inválida",  "Mensagem: " + mensagem) );
    }

    public List<TermoCompromisso> getTermosCompromisso() {
        return termosCompromisso;
    }

    public void setTermosCompromisso(List<TermoCompromisso> termosCompromisso) {
        this.termosCompromisso = termosCompromisso;
    }

    public TermoCompromisso getTermoCompromisso() {
        return termoCompromisso;
    }

    public void setTermoCompromisso(TermoCompromisso termoCompromisso) {
        this.termoCompromisso = termoCompromisso;
    }   

    public TCCI getTccI() {
        return tccI;
    }

    public void setTccI(TCCI tccI) {
        this.tccI = tccI;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public ProfessorDAO getProfessorDAO() {
        return professorDAO;
    }

    public void setProfessorDAO(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public TCCII getTccII() {
        return tccII;
    }

    public void setTccII(TCCII tccII) {
        this.tccII = tccII;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    
}
