/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ygor
 */
@Entity
public class MovimentacaoTCCII implements Serializable {
    
    //Atributos
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private LocalDateTime dataHora;
    private TipoMovimentacaoENUM tipoMovimentacaoENUM;
    @OneToOne(cascade=CascadeType.ALL)
    private ArquivoMovimentacao arquivoMovimentacao;
    private String comentario;
    private LocalDate dataProximaEntrega;
    @OneToOne
    private Usuario usuarioMovimento;
    @ManyToOne
    private TCCII tccII;

    public MovimentacaoTCCII(LocalDateTime dataHora, TipoMovimentacaoENUM tipoMovimentacaoENUM, String comentario, Usuario usuarioMovimento, TCCII tccII) {
        this.dataHora = dataHora;
        this.tipoMovimentacaoENUM = tipoMovimentacaoENUM;
        this.comentario = comentario;
        this.usuarioMovimento = usuarioMovimento;
        this.tccII = tccII;
    }

    public MovimentacaoTCCII(LocalDateTime dataHora, TipoMovimentacaoENUM tipoMovimentacaoENUM, String comentario,Usuario usuarioMovimento, LocalDate dataProximaEntrega, TCCII tccII) {
        this.dataHora = dataHora;
        this.tipoMovimentacaoENUM = tipoMovimentacaoENUM;
        this.comentario = comentario;
        this.dataProximaEntrega = dataProximaEntrega;
        this.usuarioMovimento = usuarioMovimento;
        this.tccII = tccII;
    }

    public MovimentacaoTCCII() {
    }
    
    //Métodos
    public Long getId() {
        return id;
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

    public TCCII getTccii() {
        return tccII;
    }

    public void setTccii(TCCII tccII) {
        this.tccII = tccII;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public LocalDate getDataProximaEntrega() {
        return dataProximaEntrega;
    }

    public void setDataProximaEntrega(LocalDate dataProximaEntrega) {
        this.dataProximaEntrega = dataProximaEntrega;
    }

    public Usuario getUsuarioMovimento() {
        return usuarioMovimento;
    }

    public void setUsuarioMovimento(Usuario usuarioMovimento) {
        this.usuarioMovimento = usuarioMovimento;
    }

    public TCCII getTccII() {
        return tccII;
    }

    public void setTccII(TCCII tccII) {
        this.tccII = tccII;
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
