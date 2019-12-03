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
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.ApresentacaoTCC;
import model.ArquivoMovimentacao;
import model.EstadoTccENUM;
import model.MovimentacaoTCCI;
import model.MovimentacaoTCCII;
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;
import model.TipoMovimentacaoENUM;
import org.apache.commons.lang.ArrayUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author jhonata
 */
@ManagedBean
@ViewScoped
public class GuiControleTCCAluno {

    private UploadedFile file;
    private String comentario;
    private TCCI tcci;
    private TCCII tccii;
    private LocalDate dataApresentacao;
    private String localApresentacao;
    private String banca;
    private String tema;
    private String titulo;
    private String professorOrientador;
    private String status;
    private int etapaTcc;
    private boolean versaoFinal;
    private Aluno aluno;
    private Float nota;
    
    private List<Object> movs;
    private Object selectedMov;
    private StreamedContent selectedMovFile;
    
    private final Sessao sessao;
    private final TCCIDAO tccIDao = TCCIDAO.getInstance();
    private final TCCIIDAO tccIIDao = TCCIIDAO.getInstance();
    private final TermoCompromissoDAO termoDao = TermoCompromissoDAO.getInstance();
    private final AlunoDAO alunoDao = AlunoDAO.getInstance();

    
    public GuiControleTCCAluno() throws Exception {
        sessao = new Sessao();
        movs = new ArrayList<>();
        banca = "";
        
        //Declarando duas instancias de TCCI e II para caso o aluno não tenha um TCC ainda
        //Não dê erro no sistema
        tcci = new TCCI();
        tccii = new TCCII();
        
        ApresentacaoTCC apresentacao;
        aluno = alunoDao.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
        List<TermoCompromisso> termos = termoDao.listar();
        TermoCompromisso termo = null;

        for (TermoCompromisso termoTemp : termos) {
            if (termoTemp.getAluno().equals(aluno)
                    && termoTemp.getEtapaTcc() == aluno.getEtapaTcc()) {
                termo = termoTemp;
            }
        }
        
        try{
            if (aluno.getEtapaTcc() == 1) {
                tcci = tccIDao.buscarPorTermo(termo);
                preencherListaMovTCCI(tcci);
                apresentacao = tcci.getApresentacao();
                if(apresentacao != null){
                    dataApresentacao = apresentacao.getDataApresentacao();
                    localApresentacao = apresentacao.getLocalApresentacao();
                    for(Professor professor : apresentacao.getProfessoresBanca()){
                        banca += professor.getUsuario().getNome() + ", ";
                    }
                }
                
                tema = tcci.getTermoCompromisso().getTema();
                titulo = tcci.getTermoCompromisso().getTitulo();
                professorOrientador = tcci.getTermoCompromisso().getProfessor().getUsuario().getNome();
                status = tcci.getEstadoTccENUM().toString();
                nota = tcci.getNota();
                etapaTcc = tcci.getTermoCompromisso().getEtapaTcc();

            } else if (aluno.getEtapaTcc() == 2) {
                tccii = tccIIDao.buscarPorTermo(termo);
                preencherListaMovTCCII(tccii);
                apresentacao = tccii.getApresentacao();
                if(apresentacao != null){
                    dataApresentacao = apresentacao.getDataApresentacao();
                    localApresentacao = apresentacao.getLocalApresentacao();
                    for(Professor professor : apresentacao.getProfessoresBanca()){
                        banca += professor.getUsuario().getNome() + ";";
                    }
                } 
                
                tema = tccii.getTermoCompromisso().getTema();
                titulo = tccii.getTermoCompromisso().getTitulo();
                professorOrientador = tccii.getTermoCompromisso().getProfessor().getUsuario().getNome();
                status = tccii.getEstadoTccENUM().toString();
                etapaTcc = tccii.getTermoCompromisso().getEtapaTcc();
                nota = tccii.getNota();
                
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Erro", "Nenhum TCC encontrado designado ao aluno"));
            }
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }
    
    public void realizarUpload(){
        if(aluno.getEtapaTcc() == 1){
            uploadTCCI();
            preencherListaMovTCCI(tcci);
            
        }else if(aluno.getEtapaTcc() == 2){
            uploadTCCII();
            preencherListaMovTCCII(tccii);
            
        }
        comentario = null;
        file = null;
    }
    
    public void verificarTCC() throws IOException{
        Aluno aluno = alunoDao.buscarMatricula(sessao.getUsuarioSessao().getMatricula());
        TermoCompromisso termo = termoDao.pesquisarPorAlunoEtapa(aluno, aluno.getEtapaTcc());
        if(termo != null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("./controle-tcc-aluno.xhtml");
        }
        addMessage("Nenhum TCC encontrado designado ao aluno!");
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
            mov.setUsuarioMovimento(Sessao.getInstance().getUsuarioSessao());
            
            mov.setTcci(tcci);

            //Puxa a lista de movimentações do objeto TCC, adiciona a nova movimentacao a lista e retorna ao objeto
            List<MovimentacaoTCCI> movimentacoes = tcci.getMovimentacoesTCC();
            movimentacoes.add(mov);
            tcci.setMovimentacoesTCC(movimentacoes);
            tcci.setEstadoTccENUM(EstadoTccENUM.ANALISE);
            
            if (versaoFinal) tcci.setEstadoTccENUM(EstadoTccENUM.AGUARDANDO_NOTA);

            //Atualiza o objeto no banco
            tccIDao.alterar(tcci);
            addMessage("Upload realizado com sucesso!");
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
            mov.setUsuarioMovimento(Sessao.getInstance().getUsuarioSessao());
            mov.setTccii(tccii);

            //Puxa a lista de movimentações do objeto TCC, adiciona a nova movimentacao a lista e retorna ao objeto
            List<MovimentacaoTCCII> movimentacoes = tccii.getMovimentacoes();
            movimentacoes.add(mov);
            tccii.setMovimentacoes(movimentacoes);
            tccii.setEstadoTccENUM(EstadoTccENUM.ANALISE);
            
            if (versaoFinal) tccii.setEstadoTccENUM(EstadoTccENUM.AGUARDANDO_NOTA);            
            
            //Atualiza o objeto no banco
            tccIIDao.alterar(tccii);
            addMessage("Upload realizado com sucesso!");
            
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
                                                                                    movI.getTcci().getTermoCompromisso().getAluno().getUsuario().getNome() + ".pdf");
    }
    
    public void downloadTCCII() throws FileNotFoundException{
        MovimentacaoTCCII movII = (MovimentacaoTCCII) selectedMov;
        byte[] arquivoByte = ArrayUtils.toPrimitive(movII.getArquivoMovimentacao().getBinario());
        
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        selectedMovFile = new DefaultStreamedContent(stream, "application/pdf",     movII.getTccii().getTermoCompromisso().getTema() + " - " +
                                                                                    movII.getDataHora() + " - " + 
                                                                                    movII.getTccii().getTermoCompromisso().getAluno().getUsuario().getNome() + ".pdf");
    }
    
    private void preencherListaMovTCCI(TCCI tcci){
        movs.clear();
        for(MovimentacaoTCCI movI : tcci.getMovimentacoesTCC()){
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

    public void setFile(FileUploadEvent event){
        file = event.getFile();
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

    public LocalDate getDataApresentacao() {
        return dataApresentacao;
    }

    public String getLocalApresentacao() {
        return localApresentacao;
    }

    public String getBanca() {
        return banca;
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

    public String getProfessorOrientador() {
        return professorOrientador;
    }

    public void setProfessorOrientador(String professorOrientador) {
        this.professorOrientador = professorOrientador;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEtapaTcc() {
        return etapaTcc;
    }

    public void setEtapaTcc(int etapaTcc) {
        this.etapaTcc = etapaTcc;
    }

    public boolean isVersaoFinal() {
        return versaoFinal;
    }

    public void setVersaoFinal(boolean versaoFinal) {
        this.versaoFinal = versaoFinal;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }
    
    public static void addMessage(String summary) {
        FacesMessage message = new FacesMessage("Notificação", summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    
}
