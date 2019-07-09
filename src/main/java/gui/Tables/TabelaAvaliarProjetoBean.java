/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Tables;

import dao.TccFinalDao;
import gui.mensagem.Mensagens;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.EstadoTccENUM;
import model.Professor;
import model.TccFinal;

/**
 *
 * @author Ygor
 */
@ManagedBean
@ViewScoped
@SessionScoped
public class TabelaAvaliarProjetoBean implements Serializable{
    
    @EJB
    TccFinalDao daoTccFinal;

    private TccFinal projeto;
    private Professor orientador;
    private Double nota;
    private LocalDate dataEntrega;
    private String mensagem;
    
    public List<TccFinal> getProjetos() {
        List<TccFinal> listaTodosProjetos = daoTccFinal.findAll();
        List<TccFinal> listaRetorno = new ArrayList<>();
        
        for (TccFinal t : listaTodosProjetos) {
            if (t.getOrientador().getMatricula().equals(orientador.getMatricula())
                ) {
                listaRetorno.add(t);
            }
        }
        return listaRetorno;
    }

    public void mensagemSucesso() {
        mensagem = "Informações Gravadas com Sucesso.";        
    }
    
    public void mensagemFalha() {
        mensagem = "Falha na Operação.";
    }
    
    public void avaliarProjeto() {
        projeto.setEstado(EstadoTccENUM.FINALIZADO);
        projeto.setNota(nota);

        try {
            daoTccFinal.edit(projeto);

        } catch (Exception e) {
        mensagem = e.getMessage();
        Mensagens m = new Mensagens();
        m.erro(mensagem);

        } finally {
            mensagemSucesso();
            Mensagens m = new Mensagens();
            m.sucesso(mensagem);
        }
    }
    
    public void definirNovaEntrega() {
        projeto.setEstado(EstadoTccENUM.ENTREGA);
        projeto.setDataEntrega(dataEntrega);
        
        try {
            daoTccFinal.edit(projeto);
            
        } catch (Exception e) {
            mensagem = e.getMessage();
            Mensagens m = new Mensagens();
            m.erro(mensagem);
        } finally {
            mensagemSucesso();
            Mensagens m = new Mensagens();
            m.sucesso(mensagem);
        }

    }
    
    public TccFinalDao getDaoTccFinal() {
        return daoTccFinal;
    }

    public void setDaoTccFinal(TccFinalDao daoTccFinal) {
        this.daoTccFinal = daoTccFinal;
    }

    public TccFinal getProjeto() {
        return projeto;
    }

    public void setProjeto(TccFinal projeto) {
        this.projeto = projeto;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
}
