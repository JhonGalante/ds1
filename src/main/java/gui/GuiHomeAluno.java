/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.AlunoDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import dao.TermoCompromissoDAO;
import helper.Sessao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Aluno;
import model.ApresentacaoTCC;
import model.EstadoTccENUM;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;

/**
 *
 * @author Ygor
 */
@ManagedBean
@ViewScoped
public class GuiHomeAluno {

    private TCCI tcci;
    private TCCII tccii;
    private String mensagem;
    
    private final Sessao sessao;
    private final TCCIDAO tccIDao = TCCIDAO.getInstance();
    private final TCCIIDAO tccIIDao = TCCIIDAO.getInstance();
    private final TermoCompromissoDAO termoDao = TermoCompromissoDAO.getInstance();
    private final AlunoDAO alunoDao = AlunoDAO.getInstance();

    
    public GuiHomeAluno() throws Exception {
        sessao = new Sessao();
        mensagem = null;
        EstadoTccENUM estadoTcc = null;
        
        //Declarando duas instancias de TCCI e II para caso o aluno não tenha um TCC ainda
        //Não dê erro no sistema
        tcci = new TCCI();
        tccii = new TCCII();
        
        Aluno aluno = alunoDao.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
        List<TermoCompromisso> termos = termoDao.listar();
        TermoCompromisso termo = null;

        for (TermoCompromisso termoTemp : termos) {
            if (termoTemp.getAluno().equals(aluno)
                    && termoTemp.getEtapaTcc() == aluno.getEtapaTcc()) {
                termo = termoTemp;
            }
        }
        if(termo != null){
            try{
                if (aluno.getEtapaTcc() == 1) {
                    tcci = tccIDao.buscarPorTermo(termo);
                    if(tcci != null){
                        estadoTcc = tcci.getEstadoTccENUM();
                    }
                } else if (aluno.getEtapaTcc() == 2) {
                    tccii = tccIIDao.buscarPorTermo(termo);
                    if(tccii != null){
                        estadoTcc = tccii.getEstadoTccENUM();
                    }
                }

            } catch(NullPointerException ex){
                ex.printStackTrace();
                
            }

            this.setMensagem(exibirMensagem(estadoTcc));
        }else{
            mensagem = "Você ainda não possui um orientador para o seu TCC. Acesse a página de Controle de TCC para solicitar um ou acompanhar a sua solicitação.";
        }

    }
    
    public String exibirMensagem(EstadoTccENUM estadoTccENUM){
        if(estadoTccENUM == null){
            mensagem = "Sua solicitação ainda não foi aceita pelo orientador.";
            return mensagem;
        }
        if(estadoTccENUM.equals(EstadoTccENUM.AGUARDANDO_NOTA)){
            mensagem = "O seu TCC já foi enviado e será avaliado em breve pelo professor.";
        }
        
        if(estadoTccENUM.equals(EstadoTccENUM.ANALISE)){
            mensagem = "O seu TCC já foi enviado e está sendo analisado pelo professor.";
        }
        
        if(estadoTccENUM.equals(EstadoTccENUM.ENTREGA)){
            mensagem = "A sua solicitação de orientação foi aceita! Acesse a página de controle de TCC e faça o envio da primeira versão!";
        }
        
        if(estadoTccENUM.equals(EstadoTccENUM.FINALIZADO)){
            mensagem = "O seu TCC já foi avaliado pelo professor. Acesse a página de Controle de TCC para mais detalhes.";
        }
        
        if(estadoTccENUM.equals(EstadoTccENUM.NOVA_ENTREGA)){
            mensagem = "O seu TCC já foi analisado pelo professor e está pendente de correções. Acesse a página de Controle de TCC para mais detalhes.";
        }
        return mensagem;
    }

    public TCCI getTcci() {
        return tcci;
    }

    public void setTcci(TCCI tcci) {
        this.tcci = tcci;
    }

    public TCCII getTccii() {
        return tccii;
    }

    public void setTccii(TCCII tccii) {
        this.tccii = tccii;
    }    

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
