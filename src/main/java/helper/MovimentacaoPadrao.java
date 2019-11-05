/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import model.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Jhonata
 */

public class MovimentacaoPadrao{
    
    private Long id;
    private LocalDateTime dataHora;
    private TipoMovimentacaoENUM tipoMovimentacaoENUM;
    private ArquivoMovimentacao arquivoMovimentacao;
    private String comentario;

    public MovimentacaoPadrao(Long id, LocalDateTime dataHora, TipoMovimentacaoENUM tipoMovimentacaoENUM, ArquivoMovimentacao arquivoMovimentacao, String comentario) {
        this.id = id;
        this.dataHora = dataHora;
        this.tipoMovimentacaoENUM = tipoMovimentacaoENUM;
        this.arquivoMovimentacao = arquivoMovimentacao;
        this.comentario = comentario;
    }

    
    //Métodos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public TipoMovimentacaoENUM getTipoMovimentacaoENUM() {
        return tipoMovimentacaoENUM;
    }

    public void setTipoMovimentacaoENUM(TipoMovimentacaoENUM tipoMovimentacaoENUM) {
        this.tipoMovimentacaoENUM = tipoMovimentacaoENUM;
    }

    public ArquivoMovimentacao getArquivoMovimentacao() {
        return arquivoMovimentacao;
    }

    public void setArquivoMovimentacao(ArquivoMovimentacao arquivoMovimentacao) {
        this.arquivoMovimentacao = arquivoMovimentacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovimentacaoPadrao other = (MovimentacaoPadrao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return dataHora.toString();
    }
    
}
