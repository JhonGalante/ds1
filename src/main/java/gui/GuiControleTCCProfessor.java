/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProfessorDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import helper.MovimentacaoPadrao;
import helper.Sessao;
import helper.TCCPadrao;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.ArquivoMovimentacao;
import model.MovimentacaoTCCI;
import model.MovimentacaoTCCII;
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.TipoMovimentacaoENUM;
import org.apache.commons.lang.ArrayUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jhonata
 */
@ManagedBean
@ViewScoped
public class GuiControleTCCProfessor {

    private List<TCCPadrao> projetos;
    private List<MovimentacaoPadrao> movs;
    private TCCPadrao projetoSelecionado;
    private MovimentacaoPadrao movSelecionado;
    private String comentario;
    private Date prazoProximaEntrega;
    private UploadedFile file;

    private final TCCIDAO tcciDAO = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDAO = TCCIIDAO.getInstance();
    private final ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    private final Sessao sessao = Sessao.getInstance();

    public GuiControleTCCProfessor() throws Exception {
        Professor professorTemp = professorDAO.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
        projetos = new ArrayList<>();
        movs = new ArrayList<>();
        
        for (TCCI tcci : tcciDAO.listar()) {
            if ((tcci.getProfessorTcc().equals(professorTemp) || tcci.getTermoCompromisso().getProfessor().equals(professorTemp))
                    && !tcci.getMovimentacoesTCC().isEmpty()) {
                projetos.add(new TCCPadrao(tcci.getId(), 0, tcci.getTermoCompromisso(),
                        tcci.getMovimentacoesTCC().get(tcci.getMovimentacoesTCC().size() - 1).getDataHora(), 1));
            }
        }

        for (TCCII tccii : tcciiDAO.listar()) {
            if ((tccii.getProfessorTcc().equals(professorTemp) || tccii.getTermoCompromisso().getProfessor().equals(professorTemp))
                    && !tccii.getMovimentacoes().isEmpty()) {
                projetos.add(new TCCPadrao(tccii.getId(), 0, tccii.getTermoCompromisso(),
                        tccii.getMovimentacoes().get(tccii.getMovimentacoes().size() - 1).getDataHora(), 2));
            }
        }
    }
    
    public void selecionarProjeto(){
       if (projetoSelecionado != null) {
            if (projetoSelecionado.getEtapaTCC() == 1) {
                TCCI tcci = tcciDAO.buscarPorId(projetoSelecionado.getId());
                preencherListaMovTCCI(tcci);
            } else {
                TCCII tccii = tcciiDAO.buscarPorId(projetoSelecionado.getId());
                preencherListaMovTCCII(tccii);
            }
        }
    }
    
    public void realizarUpload() {
        if (projetoSelecionado != null) {
            if (projetoSelecionado.getEtapaTCC() == 1) {
                TCCI tcci = tcciDAO.buscarPorId(projetoSelecionado.getId());
                uploadTCCI(tcci);
                preencherListaMovTCCI(tcci);
                comentario = null;
                file = null;
                prazoProximaEntrega = null;
            } else {
                TCCII tccii = tcciiDAO.buscarPorId(projetoSelecionado.getId());
                uploadTCCII(tccii);
                preencherListaMovTCCII(tccii);
                comentario = null;
                file = null;
                prazoProximaEntrega = null;
            }
            addMessage("Upload realizado com sucesso!");
        }else{
            addMessage("Favor selecionar algum projeto");
        }
    }
    
    public StreamedContent realizarDownload() throws FileNotFoundException{
        if(projetoSelecionado.getEtapaTCC() == 1){ 
            return downloadTCCI();
        }else if(projetoSelecionado.getEtapaTCC() == 2){
            return downloadTCCII();
        }
        return null;
    }
    
    public StreamedContent downloadTCCI() throws FileNotFoundException{
        TCCI tcci = tcciDAO.buscarPorId(projetoSelecionado.getId());
        MovimentacaoTCCI movI = tcci.getMovimentacoesTCC().get(tcci.getMovimentacoesTCC().size() - 1);
        byte[] arquivoByte = ArrayUtils.toPrimitive(movI.getArquivoMovimentacao().getBinario());
        
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        return new DefaultStreamedContent(stream, "application/pdf",     movI.getTcci().getTermoCompromisso().getTema() + " - " +
                                                                                    movI.getDataHora() + " - " + 
                                                                                    movI.getTcci().getTermoCompromisso().getAluno().getUsuario().getNome() + ".pdf");
    }
    
    public StreamedContent downloadTCCII() throws FileNotFoundException{
        TCCII tccii = tcciiDAO.buscarPorId(projetoSelecionado.getId());
        MovimentacaoTCCII movII = tccii.getMovimentacoesTCC().get(tccii.getMovimentacoesTCC().size() - 1);
        byte[] arquivoByte = ArrayUtils.toPrimitive(movII.getArquivoMovimentacao().getBinario());
        
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        return new DefaultStreamedContent(stream, "application/pdf",     movII.getTccii().getTermoCompromisso().getTema() + " - " +
                                                                                    movII.getDataHora() + " - " + 
                                                                                    movII.getTccii().getTermoCompromisso().getAluno().getUsuario().getNome() + ".pdf");
    }

    public void uploadTCCI(TCCI tcci) {
        MovimentacaoTCCI mov = new MovimentacaoTCCI();
        ArquivoMovimentacao arqMov = new ArquivoMovimentacao();

        try {
            // Corrige obrigatoriedade de upload de arquivo por parte do professor ao tecer comentário
            if (file != null) {
                InputStream input = file.getInputstream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];

                for (int tamanho = 0; (tamanho = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, tamanho);
                }

                arqMov.setBinario(ArrayUtils.toObject(output.toByteArray()));
                mov.setArquivoMovimentacao(arqMov); 
            }

            mov.setComentario(comentario);
            mov.setTipoMovimentacaoENUM(TipoMovimentacaoENUM.CONSULTA);
            mov.setDataHora(LocalDateTime.now());
            mov.setDataProximaEntrega(prazoProximaEntrega.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            mov.setUsuarioMovimento(sessao.getUsuarioSessao());
            mov.setTcci(tcci);

            //Puxa a lista de movimentações do objeto TCC, adiciona a nova movimentacao a lista e retorna ao objeto
            List<MovimentacaoTCCI> movimentacoes = tcci.getMovimentacoesTCC();
            movimentacoes.add(mov);
            tcci.setMovimentacoesTCC(movimentacoes);

            //Atualiza o objeto no banco
            tcciDAO.alterar(tcci);

        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage message = new FacesMessage("Erro. Por favor, tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void uploadTCCII(TCCII tccii) {
        MovimentacaoTCCII mov = new MovimentacaoTCCII();
        ArquivoMovimentacao arqMov = new ArquivoMovimentacao();

        try {
            // Corrige obrigatoriedade de upload de arquivo por parte do professor ao tecer comentário
            if (file != null) {
                InputStream input = file.getInputstream();
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];

                for (int tamanho = 0; (tamanho = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, tamanho);
                }

                arqMov.setBinario(ArrayUtils.toObject(output.toByteArray()));
                mov.setArquivoMovimentacao(arqMov); 
            }
            
            
            mov.setComentario(comentario);
            mov.setTipoMovimentacaoENUM(TipoMovimentacaoENUM.CONSULTA);
            mov.setDataHora(LocalDateTime.now());
            mov.setDataProximaEntrega(prazoProximaEntrega.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            mov.setUsuarioMovimento(sessao.getUsuarioSessao());
            mov.setTccii(tccii);

            //Puxa a lista de movimentações do objeto TCC, adiciona a nova movimentacao a lista e retorna ao objeto
            List<MovimentacaoTCCII> movimentacoes = tccii.getMovimentacoes();
            movimentacoes.add(mov);
            tccii.setMovimentacoes(movimentacoes);

            //Atualiza o objeto no banco
            tcciiDAO.alterar(tccii);

        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage message = new FacesMessage("Erro. Por favor, tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    private void preencherListaMovTCCI(TCCI tcci) {
        movs.clear();
        for (MovimentacaoTCCI movI : tcci.getMovimentacoesTCC()) {
            movs.add(new MovimentacaoPadrao(movI.getId(), movI.getDataHora(), movI.getTipoMovimentacaoENUM(), 
                        movI.getArquivoMovimentacao(), movI.getComentario(), movI.getDataProximaEntrega(), movI.getUsuarioMovimento()));
        }
    }

    private void preencherListaMovTCCII(TCCII tccii) {
        movs.clear();
        for (MovimentacaoTCCII movII : tccii.getMovimentacoes()) {
            movs.add(new MovimentacaoPadrao(movII.getId(), movII.getDataHora(), movII.getTipoMovimentacaoENUM(), 
                        movII.getArquivoMovimentacao(), movII.getComentario(), movII.getDataProximaEntrega(), movII.getUsuarioMovimento()));
        }
    }

    public List<TCCPadrao> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<TCCPadrao> projetos) {
        this.projetos = projetos;
    }

    public TCCPadrao getProjetoSelecionado() {
        return projetoSelecionado;
    }

    public void setProjetoSelecionado(TCCPadrao projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(FileUploadEvent event){
        file = event.getFile();
    }

    public List<MovimentacaoPadrao> getMovs() {
        return movs;
    }

    public void setMovs(List<MovimentacaoPadrao> movs) {
        this.movs = movs;
    }

    public MovimentacaoPadrao getMovSelecionado() {
        return movSelecionado;
    }

    public void setMovSelecionado(MovimentacaoPadrao movSelecionado) {
        this.movSelecionado = movSelecionado;
    }

    public Date getPrazoProximaEntrega() {
        return prazoProximaEntrega;
    }

    public void setPrazoProximaEntrega(Date prazoProximaEntrega) {
        this.prazoProximaEntrega = prazoProximaEntrega;
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage("Notificação", summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
