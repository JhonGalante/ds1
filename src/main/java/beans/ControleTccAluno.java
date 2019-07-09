/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.MovimentacaoDao;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import model.Aluno;
import model.ArquivoTcc;
import model.Movimentacao;
import model.Usuario;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jardelmc
 */
@Named(value = "controleTccAluno")
@SessionScoped
public class ControleTccAluno implements Serializable {

    @EJB
    private MovimentacaoDao daoMovimentacaoDao;
    
    
    private List<Movimentacao> movimentacoesAluno;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private StreamedContent file;
    
    public void retornaMovimentacoesAluno(){
        movimentacoesAluno = new ArrayList<>();
        Aluno aluno = new Usuario().getAluno();
        List<Movimentacao> todasMovimentacoes = daoMovimentacaoDao.findAll();
        
        for(Movimentacao m : todasMovimentacoes) {
            if(aluno.getMatricula().equals(m.getInformacoesTcc().getAluno().getMatricula())){
                movimentacoesAluno.add(m);
            }
        }
        Collections.reverse(movimentacoesAluno);
    }
    
    public void download(Movimentacao movimentacao){
        try {
            ArquivoTcc tcc = movimentacao.getTcc();
            byte[] arquivo = tcc.getTcc();
            arquivo.getClass().getResourceAsStream("tcc");
            InputStream stream = new ByteArrayInputStream(arquivo);
            file = new DefaultStreamedContent(stream, "application/pdf", "download.pdf");
            
            
        } catch (Exception e) {
        }
    }
    
   
    
    public ControleTccAluno() {
    }

    public List<Movimentacao> getMovimentacoesAluno() {
        return movimentacoesAluno;
    }

    public void setMovimentacoesAluno(List<Movimentacao> movimentacoesAluno) {
        this.movimentacoesAluno = movimentacoesAluno;
    }

    public StreamedContent getFile() {        
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

   
    
    
}
