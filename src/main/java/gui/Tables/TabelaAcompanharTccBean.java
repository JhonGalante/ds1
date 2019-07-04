/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Tables;

import dao.MovimentacaoDao;
import dao.ProfessorDao;
import dao.SolicitacaoOrientadorDao;
import dao.TccInicioDao;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Movimentacao;
import model.Professor;
import model.SolicitacaoOrientador;
import model.TccInicio;
import model.TipoMovimentacaoENUM;
import model.Usuario;

/**
 *
 * @author Jardelmc
 */
@ManagedBean
@ViewScoped
public class TabelaAcompanharTccBean implements Serializable{
    
    @EJB
    TccInicioDao daoTccInicio; // Ygor: Confirmar se realmente será a classe início, uma vez que o tcc final terá movimentações também
    @EJB
    MovimentacaoDao daoMovimentacao;
    
    private TccInicio tccInicio; // Ygor: Confirmar se realmente será a classe início, uma vez que o tcc final terá movimentações também
    private Movimentacao movimentacao = new Movimentacao();
    private TipoMovimentacaoENUM tipoMovimentacao;
    private String comentario;
    
    public List<Movimentacao> movimentacoes() { // Ygor: Confirmar se realmente será a classe início, uma vez que o tcc final terá movimentações também
        return daoMovimentacao.findAll();
    }

    public TccInicioDao getDaoTccInicio() {
        return daoTccInicio;
    }

    public MovimentacaoDao getDaoMovimentacao() {
        return daoMovimentacao;
    }

    public TccInicio getTccInicio() {
        return tccInicio;
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public TipoMovimentacaoENUM getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setTccInicio(TccInicio tccInicio) {
        this.tccInicio = tccInicio;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacaoENUM tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }  
    
    
}
