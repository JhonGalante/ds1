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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.ArquivoMovimentacao;
import model.MovimentacaoTCCI;
import model.MovimentacaoTCCII;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;
import model.TipoMovimentacaoENUM;
import org.apache.commons.lang.ArrayUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jhonata
 */
@ManagedBean
public class GuiControleTCCProfessor {

    private UploadedFile file;
    private String comentario;
    private TCCI tcci;
    private TCCII tccii;
    
    private List<Object> movs;
    private Object selectedMov;
    private StreamedContent selectedMovFile;
    
    private final Sessao sessao;
    private final TCCIDAO tccIDao = TCCIDAO.getInstance();
    private final TCCIIDAO tccIIDao = TCCIIDAO.getInstance();
    private final TermoCompromissoDAO termoDao = TermoCompromissoDAO.getInstance();
    private final AlunoDAO alunoDao = AlunoDAO.getInstance();

    
    public GuiControleTCCProfessor() throws Exception {
        sessao = new Sessao();
        movs = new ArrayList<>();
        Aluno aluno = alunoDao.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
        List<TermoCompromisso> termos = termoDao.listar();
        TermoCompromisso termo = null;

        for (TermoCompromisso termoTemp : termos) {
            if (termoTemp.getAluno().equals(aluno)
                    && termoTemp.getEtapaTcc() == aluno.getEtapaTcc()) {
                termo = termoTemp;
            }
        }

        if (aluno.getEtapaTcc() == 1) {
            tcci = tccIDao.buscarPorTermo(termo);
            preencherListaMovTCCI(tcci);
            
        } else if (aluno.getEtapaTcc() == 2) {
            tccii = tccIIDao.buscarPorTermo(termo);
            preencherListaMovTCCII(tccii);
            
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro", "Nenhum TCC encontrado designado ao aluno"));
        }
        

    }
    
    public void realizarUpload(){
        if(tcci != null){
            uploadTCCI();
            preencherListaMovTCCI(tcci);
        }else if(tccii != null){
            uploadTCCII();
            preencherListaMovTCCII(tccii);
        }
    }

    public void uploadTCCI() {
        MovimentacaoTCCI mov = new MovimentacaoTCCI();
        ArquivoMovimentacao arqMov = new ArquivoMovimentacao();
        
        try {
            InputStream input = file.getInputstream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            for (int tamanho = 0; (tamanho = input.read(buffer)) > 0;) {
                output.write(buffer, 0, tamanho);
            }

            arqMov.setBinario(ArrayUtils.toObject(output.toByteArray()));
            mov.setArquivoMovimentacao(arqMov);
            mov.setComentario(comentario);
            mov.setTipoMovimentacaoENUM(TipoMovimentacaoENUM.ENTREGA);
            mov.setDataHora(LocalDateTime.now());
            mov.setTcci(tcci);

            //Puxa a lista de movimentações do objeto TCC, adiciona a nova movimentacao a lista e retorna ao objeto
            List<MovimentacaoTCCI> movimentacoes = tcci.getMovimentacoes();
            movimentacoes.add(mov);
            tcci.setMovimentacoes(movimentacoes);

            //Atualiza o objeto no banco
            tccIDao.alterar(tcci);

        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage message = new FacesMessage("Erro. Por favor, tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void uploadTCCII() {
        MovimentacaoTCCII mov = new MovimentacaoTCCII();
        ArquivoMovimentacao arqMov = new ArquivoMovimentacao();
        
        try {
            InputStream input = file.getInputstream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            for (int tamanho = 0; (tamanho = input.read(buffer)) > 0;) {
                output.write(buffer, 0, tamanho);
            }

            arqMov.setBinario(ArrayUtils.toObject(output.toByteArray()));
            mov.setArquivoMovimentacao(arqMov);
            mov.setComentario(comentario);
            mov.setTipoMovimentacaoENUM(TipoMovimentacaoENUM.ENTREGA);
            mov.setDataHora(LocalDateTime.now());
            mov.setTccii(tccii);

            //Puxa a lista de movimentações do objeto TCC, adiciona a nova movimentacao a lista e retorna ao objeto
            List<MovimentacaoTCCII> movimentacoes = tccii.getMovimentacoes();
            movimentacoes.add(mov);
            tccii.setMovimentacoes(movimentacoes);

            //Atualiza o objeto no banco
            tccIIDao.alterar(tccii);

        } catch (Exception ex) {
            ex.printStackTrace();
            FacesMessage message = new FacesMessage("Erro. Por favor, tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public StreamedContent realizarDownload() throws FileNotFoundException{
        if(tcci != null){
            downloadTCCI();
            return selectedMovFile;
        }else if(tccii != null){
            downloadTCCII();
            return selectedMovFile;
        }
        return null;
    }
    
    public void downloadTCCI() throws FileNotFoundException{
        MovimentacaoTCCI movI = (MovimentacaoTCCI) selectedMov;
        byte[] arquivoByte = ArrayUtils.toPrimitive(movI.getArquivoMovimentacao().getBinario());
        
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        selectedMovFile = new DefaultStreamedContent(stream, "application/pdf",     movI.getTcci().getTermoCompromisso().getTema() + " - " +
                                                                                    movI.getDataHora() + " - " + 
                                                                                    movI.getTcci().getTermoCompromisso().getAluno().getUsuario().getNome());
    }
    
    public void downloadTCCII() throws FileNotFoundException{
        MovimentacaoTCCII movII = (MovimentacaoTCCII) selectedMov;
        byte[] arquivoByte = ArrayUtils.toPrimitive(movII.getArquivoMovimentacao().getBinario());
        
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        selectedMovFile = new DefaultStreamedContent(stream, "application/pdf",     movII.getTccii().getTermoCompromisso().getTema() + " - " +
                                                                                    movII.getDataHora() + " - " + 
                                                                                    movII.getTccii().getTermoCompromisso().getAluno().getUsuario().getNome());
    }
    
    private void preencherListaMovTCCI(TCCI tcci){
        movs.clear();
        for(MovimentacaoTCCI movI : tcci.getMovimentacoes()){
                movs.add((Object)movI);
            }
        
    }
    
    private void preencherListaMovTCCII(TCCII tccii){
        movs.clear();
        for(MovimentacaoTCCII movII : tccii.getMovimentacoes()){
                movs.add((Object)movII);
            }
        
    }
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public List<Object> getMovs() {
        return movs;
    }

    public void setMovs(List<Object> movs) {
        this.movs = movs;
    }

    public Object getSelectedMov() {
        return selectedMov;
    }

    public void setSelectedMov(Object selectedMov) {
        this.selectedMov = selectedMov;
    }
    
    
}
