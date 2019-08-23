/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ygor
 */
@Entity
public class MovimentacaoTCC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private LocalDateTime dataHora;
    @NotNull
    @OneToOne
    private TipoMovimentacaoENUM tipoMovimentacaoENUM;
    @OneToOne
    private ArquivoMovimentacao arquivoMovimentacao;
    private String comentario;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.dataHora);
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
        final MovimentacaoTCC other = (MovimentacaoTCC) obj;
        if (!Objects.equals(this.dataHora, other.dataHora)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return dataHora.toString();
    }
    
}
