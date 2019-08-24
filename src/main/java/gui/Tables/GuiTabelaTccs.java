package gui.Tables;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import dao.MovimentacaoDao;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.Movimentacao;
import model.Professor;

import model.Usuario;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author malum
 */
@Named(value = "tabelaTccsBean")
@ManagedBean
@ViewScoped
@SessionScoped
public class GuiTabelaTccs implements Serializable {

    @EJB
    private MovimentacaoDao daoMovimentacao;
    private String comentarioAluno;
    private String comentarioProfessor;
    
    private Movimentacao movimentacaoSelecionada;

    private List<Movimentacao> movimentacoesAluno;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private StreamedContent file;

    public List<Movimentacao> getRetornaMovimentacoesAluno() {
        movimentacoesAluno = new ArrayList<>();
        Professor professor = new Usuario().getProfessor();
        List<Movimentacao> todasMovimentacoes = daoMovimentacao.findAll();

        for (Movimentacao m : todasMovimentacoes) {
            if (professor.getMatricula().equals(m.getInformacoesTcc().getOrientador().getMatricula())) {
                movimentacoesAluno.add(m);
            }
        }
        Collections.reverse(movimentacoesAluno);
        return movimentacoesAluno;
    }
    
     public void atualizaInformacao(Movimentacao movimentacao){
         this.comentarioAluno = movimentacao.getComentarioAluno();  
         this.movimentacaoSelecionada = movimentacao;
    }
     
    public void salvaComentarioProfessor(){
        movimentacaoSelecionada.setComentarioOrientador(comentarioProfessor);
        daoMovimentacao.edit(movimentacaoSelecionada);
        saveMessage();
        
    }

    public GuiTabelaTccs() {
    }

    public List<Movimentacao> getMovimentacoesAluno() {
        return movimentacoesAluno;
    }

    public void setMovimentacoesAluno(List<Movimentacao> movimentacoesAluno) {
        this.movimentacoesAluno = movimentacoesAluno;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Movimentacao getMovimentacaoSelecionada() {
        return movimentacaoSelecionada;
    }

    public void setMovimentacaoSelecionada(Movimentacao movimentacaoSelecionada) {
        this.movimentacaoSelecionada = movimentacaoSelecionada;
    }

    public String getComentarioAluno() {
        return comentarioAluno;
    }

    public void setComentarioAluno(String comentarioAluno) {
        this.comentarioAluno = comentarioAluno;
    }

    public String getComentarioProfessor() {
        return comentarioProfessor;
    }

    public void setComentarioProfessor(String comentarioProfessor) {
        this.comentarioProfessor = comentarioProfessor;
    }
    
    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Informações Gravadas!") );
    }

}
