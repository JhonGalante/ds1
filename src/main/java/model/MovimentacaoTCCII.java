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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ygor
 */
@Entity
public class MovimentacaoTCCII implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private LocalDateTime dataHora;
    @NotNull
    @OneToOne
    private TCCII tccII;
    @NotNull
    @OneToOne
    private TipoMovimentacaoENUM tipoMovimentacaoENUM;
    @OneToOne
    private ArquivoMovimentacao arquivoMovimentacao;
    private String comentario;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public TCCII getTccII() {
        return tccII;
    }

    public void setTccII(TCCII tccII) {
        this.tccII = tccII;
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
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final MovimentacaoTCCII other = (MovimentacaoTCCII) obj;
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