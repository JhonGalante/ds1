/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.TermoCompromissoDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.EstadoTermoCompromissoENUM;
import model.TermoCompromisso;

/**
 *
 * @author ygor.daudt
 */
@ManagedBean
@ViewScoped
public class GuiAceitarSolicitacaoOrientacao {
    
    private List<TermoCompromisso> termosCompromisso;
    private TermoCompromisso termoCompromisso;
    private GuiSessao guiSessao;
    
    private final TermoCompromissoDAO termoCompromissoDAO = TermoCompromissoDAO.getInstance();
    
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
        mensagemConfirma("Solicitação ACEITA com sucesso.");
        try {
            termoCompromissoDAO.alterar(termoCompromisso);
        } catch(Exception ex) {
            Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        limparSelecao();
    }
    
    public void recusarSolicitacao() {
        termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_RECUSADA);
        mensagemConfirma("Solicitação RECUSADA com sucesso.");
        try {
            termoCompromissoDAO.alterar(termoCompromisso);
        } catch(Exception ex) {
            Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
