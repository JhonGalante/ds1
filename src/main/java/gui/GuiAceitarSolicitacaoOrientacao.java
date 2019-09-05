/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import helper.Sessao;
import dao.TCCIDAO;
import dao.TermoCompromissoDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.EstadoTccENUM;
import model.EstadoTermoCompromissoENUM;
import model.TCCI;
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
    private Sessao guiSessao;
    
    private final TermoCompromissoDAO termoCompromissoDAO = TermoCompromissoDAO.getInstance();
    private final TCCIDAO tccIDAO = TCCIDAO.getInstance();
    
    public GuiAceitarSolicitacaoOrientacao() throws Exception {
        termosCompromisso = termoCompromissoDAO.listar();
    }
    
    public void iniciarListaSolicitacoes() throws IOException {
        try {
            String matriculaProfessor = guiSessao.getUsuarioSessao().getMatricula();
            termosCompromisso = termoCompromissoDAO.buscarTermosPendentesAceitacao(matriculaProfessor);
        } catch(Exception ex) {
            Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void aceitarSolicitacao() {
        termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_ACEITA);
        tccI = new TCCI();
        tccI.setTermoCompromisso(termoCompromisso);
        tccI.setProfessorTcc(termoCompromisso.getProfessor());
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

        limparSelecao();
    }
    
    public void recusarSolicitacao() {
        termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_RECUSADA);
        try {
            termoCompromissoDAO.alterar(termoCompromisso);
            mensagemConfirma("Solicitação RECUSADA com sucesso.");
        } catch(Exception ex) {
            Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
            mensagemRecusa("Não foi possível realizar esta operação.");
        }
        limparSelecao();
    }
    
    public void limparSelecao() {
        termoCompromisso = null;
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
    
}
