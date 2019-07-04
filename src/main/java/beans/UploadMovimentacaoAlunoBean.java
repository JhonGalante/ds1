/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controller.TccInicioController;
import dao.MovimentacaoDao;
import dao.TccInicioDao;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.ArquivoTcc;
import model.Movimentacao;
import model.TccInicio;
import model.Usuario;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jardelmc
 */
@Named(value = "uploadMovimentacaoAlunoBean")
@SessionScoped
public class UploadMovimentacaoAlunoBean implements Serializable {

    @EJB
    private MovimentacaoDao daoMovimentacao;
    @EJB
    private TccInicioDao daoTccInicio;
    
    Movimentacao movimentacao = new Movimentacao();
    private UploadedFile file;
    private String comentario;
    private ArquivoTcc tcc;
    
    
    public void uploadTcc() {
        try {
            InputStream input = file.getInputstream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            
            for(int tamanho = 0; (tamanho=input.read(buffer)) > 0;) {
                output.write(buffer, 0, tamanho);
            }        
            tcc = new ArquivoTcc();
            tcc.setTcc(output.toByteArray());            
            movimentacao.setInformacoesTcc(getInformacoesTccAluno(new Usuario().getAluno()));
            movimentacao.setData(LocalDate.now());
            movimentacao.setComentarioAluno(comentario);
            movimentacao.setTcc(tcc);
            daoMovimentacao.create(movimentacao);
            FacesMessage message = new FacesMessage("Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, message);  
            file = null;
            comentario = null;
            
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Erro. Por favor, tente novamente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public TccInicio getInformacoesTccAluno(Aluno aluno){
        List<TccInicio> listaTcc = daoTccInicio.findAll();
        for(TccInicio t : listaTcc) {
            if(aluno.getMatricula().equals(t.getAluno().getMatricula())){
                return t;
            }
        }
        return null;
    }
    
    
    public UploadMovimentacaoAlunoBean() {
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ArquivoTcc getTcc() {
        return tcc;
    }

    public void setTcc(ArquivoTcc tcc) {
        this.tcc = tcc;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    
    
}
