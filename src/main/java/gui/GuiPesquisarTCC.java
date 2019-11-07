/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.TCCIDAO;
import dao.TCCIIDAO;
import helper.Sessao;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.MovimentacaoTCCI;
import model.MovimentacaoTCCII;
import model.TCCI;
import model.TCCII;
import model.TipoUsuarioENUM;
import model.Usuario;
import org.apache.commons.lang.ArrayUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author jhonata
 */

@ManagedBean
public class GuiPesquisarTCC implements Serializable {
    
    private List<TCCI> tccsI;
    private List<TCCII> tccsII;
    
    private TCCI selectedTccI;
    private TCCII selectedTccII;
    private StreamedContent selectedTccIFile;
    private StreamedContent selectedTccIIFile;
    
    private List<TCCI> tccsIFiltred;
    private List<TCCII> tccsIIFiltred;
    
    private String campoPesquisar;
    private Sessao sessao;
    
    private final TCCIDAO tcciDao = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDao = TCCIIDAO.getInstance();
    
    public GuiPesquisarTCC() throws Exception{
        sessao = new Sessao();
        tccsI = new ArrayList<>();
        tccsII = new ArrayList<>();
        
        try{
            Usuario usuario = sessao.getUsuarioSessao();
            if(usuario.getTipo() == TipoUsuarioENUM.ALUNO){
                for(TCCI tcci: tcciDao.listar()){
                    if(tcci.isDispRepo()){
                        tccsI.add(tcci);
                    }
                }
                for(TCCII tccii: tcciiDao.listar()){
                    if(tccii.isDispRepo()){
                        tccsII.add(tccii);
                    }
                }
            }
            
            if(usuario.getTipo() == TipoUsuarioENUM.PROFESSOR){
                for(TCCI tcci: tcciDao.listar()){
                    tccsI.add(tcci);
                    
                }
                for(TCCII tccii: tcciiDao.listar()){
                    tccsII.add(tccii);
                }
            }
            
        }catch(NullPointerException ex){
            ex.printStackTrace();
            
            for(TCCII tccii: tcciiDao.listar()){
                    tccsII.add(tccii);
            }
        }
    }
    
    public StreamedContent downloadTCCI() throws FileNotFoundException{
        List <MovimentacaoTCCI> movimentacoes = selectedTccI.getMovimentacoesTCC();
        MovimentacaoTCCI ultimaMovimentacao = movimentacoes.get(movimentacoes.size()-1);
        byte[] arquivoByte = ArrayUtils.toPrimitive(ultimaMovimentacao.getArquivoMovimentacao().getBinario());
        
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        selectedTccIFile = new DefaultStreamedContent(stream, "application/pdf", selectedTccI.getTermoCompromisso().getTema() + " - " + 
                                                                        selectedTccI.getTermoCompromisso().getAluno().getUsuario().getNome()+".pdf");
        return selectedTccIFile;
    }
    
    public StreamedContent downloadTCCII(){
        List <MovimentacaoTCCII> movimentacoes = selectedTccII.getMovimentacoes();
        MovimentacaoTCCII ultimaMovimentacao = movimentacoes.get(movimentacoes.size()-1);
        byte[] arquivoByte = ArrayUtils.toPrimitive(ultimaMovimentacao.getArquivoMovimentacao().getBinario());
        InputStream stream = new ByteArrayInputStream(arquivoByte);
        selectedTccIIFile = new DefaultStreamedContent(stream, "application/pdf", selectedTccII.getTermoCompromisso().getTema() + " - " + 
                                                                        selectedTccII.getTermoCompromisso().getAluno().getUsuario().getNome()+".pdf");
        
        return selectedTccIIFile;
    }

    public String getCampoPesquisar() {
        return campoPesquisar;
    }

    public void setCampoPesquisar(String campoPesquisar) {
        this.campoPesquisar = campoPesquisar;
    }

    public List<TCCI> getTccsI() {
        return tccsI;
    }

    public void setTccsI(List<TCCI> tccsI) {
        this.tccsI = tccsI;
    }

    public List<TCCII> getTccsII() {
        return tccsII;
    }

    public void setTccsII(List<TCCII> tccsII) {
        this.tccsII = tccsII;
    }

    public TCCI getSelectedTccI() {
        return selectedTccI;
    }

    public void setSelectedTccI(TCCI selectedTccI) {
        this.selectedTccI = selectedTccI;
    }

    public TCCII getSelectedTccII() {
        return selectedTccII;
    }

    public void setSelectedTccII(TCCII selectedTccII) {
        this.selectedTccII = selectedTccII;
    }

    public List<TCCI> getTccsIFiltred() {
        return tccsIFiltred;
    }

    public void setTccsIFiltred(List<TCCI> tccsIFiltred) {
        this.tccsIFiltred = tccsIFiltred;
    }

    public List<TCCII> getTccsIIFiltred() {
        return tccsIIFiltred;
    }

    public void setTccsIIFiltred(List<TCCII> tccsIIFiltred) {
        this.tccsIIFiltred = tccsIIFiltred;
    }

    public StreamedContent getSelectedTccIFile() {
        return selectedTccIFile;
    }

    public void setSelectedTccIFile(StreamedContent selectedTccIFile) {
        this.selectedTccIFile = selectedTccIFile;
    }

    public StreamedContent getSelectedTccIIFile() {
        return selectedTccIIFile;
    }

    public void setSelectedTccIIFile(StreamedContent selectedTccIIFile) {
        this.selectedTccIIFile = selectedTccIIFile;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
