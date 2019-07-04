/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.MovimentacaoDao;
import dao.TccInicioDao;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Movimentacao;
import model.TccInicio;
import model.TipoMovimentacaoENUM;

/**
 *
 * @author Ygor
 */
@SessionScoped
@ManagedBean
public class GuiAcompanharTcc {
    @EJB
    TccInicioDao daoTccInicio; // Ygor: Confirmar se realmente será a classe início, uma vez que o tcc final terá movimentações também
    @EJB
    MovimentacaoDao daoMovimentacao;
    private Movimentacao movimentacao = new Movimentacao();;
    private TccInicio tccInicio; // Ygor: Confirmar se realmente será a classe início, uma vez que o tcc final terá movimentações também
    private TipoMovimentacaoENUM tipoMovimentacao;
    private String comentario;
    private String mensagem;
    
    
    public void registrarMovimentacao() {
        movimentacao.setInformacoesTcc(tccInicio);
      //  movimentacao.setTipoMovimentacao(tipoMovimentacao);
       // movimentacao.setComentario(comentario);
        movimentacao.setData(LocalDate.now()); // Ygor: Verificar possível obtenção da data do servidor.
        daoMovimentacao.create(movimentacao);
        mensagemSucesso();
        
    }

    public void mensagemSucesso() {
        mensagem = "Movimentação Registrada.";        
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

    public void setTipoMovimentacao(TipoMovimentacaoENUM tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public void setDaoTccInicio(TccInicioDao daoTccInicio) {
        this.daoTccInicio = daoTccInicio;
    }

    public void setDaoMovimentacao(MovimentacaoDao daoMovimentacao) {
        this.daoMovimentacao = daoMovimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public void setTccInicio(TccInicio tccInicio) {
        this.tccInicio = tccInicio;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
