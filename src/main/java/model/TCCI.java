/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ygor
 */
@Entity
public class TCCI implements Serializable{

    private static final long serialVersionUID = 1L;
    
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @OneToOne
    private TermoCompromisso termoCompromisso;
    
    @NotNull
    @OneToOne
    private Professor professorTcc;
    
    @NotNull
    private EstadoTccENUM estadoTccENUM;
    
    @OneToOne
    private ApresentacaoTCC apresentacao;
    
    @OneToOne
    private ArquivoTramitacao arquivoTramitacao;
    
    private Float nota;
    
    @OneToMany(mappedBy = "TCCI", targetEntity = MovimentacaoTCCI.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MovimentacaoTCCI> movimentacoesTCCI;

    //Métodos
    public Long getId() {
        return id;
    }

    public TermoCompromisso getTermoCompromisso() {
        return termoCompromisso;
    }

    public void setTermoCompromisso(TermoCompromisso termoCompromisso) {
        this.termoCompromisso = termoCompromisso;
    }

    public Professor getProfessorTcc() {
        return professorTcc;
    }

    public void setProfessorTcc(Professor professorTcc) {
        this.professorTcc = professorTcc;
    }

    public EstadoTccENUM getEstadoTccENUM() {
        return estadoTccENUM;
    }

    public void setEstadoTccENUM(EstadoTccENUM estadoTccENUM) {
        this.estadoTccENUM = estadoTccENUM;
    }

    public ApresentacaoTCC getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(ApresentacaoTCC apresentacao) {
        this.apresentacao = apresentacao;
    }

    public ArquivoTramitacao getArquivoTramitacao() {
        return arquivoTramitacao;
    }

    public void setArquivoTramitacao(ArquivoTramitacao arquivoTramitacao) {
        this.arquivoTramitacao = arquivoTramitacao;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public List<MovimentacaoTCCI> getMovimentacoes() {
        return movimentacoesTCCI;
    }

    public void setMovimentacoes(List<MovimentacaoTCCI> movimentacoes) {
        this.movimentacoesTCCI = movimentacoes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TCCI)) {
            return false;
        }
        TCCI other = (TCCI) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return termoCompromisso.getTema();
    }
    
    public void adicionarMovimentacao(MovimentacaoTCCI movimentacao){
        this.movimentacoesTCCI.add(new MovimentacaoTCCI());
    }
    
    public void removerMovimentacao(MovimentacaoTCCI movimentacao){
        this.movimentacoesTCCI.remove(movimentacao);
    }
    
    public void finalizarTcc(Float nota){
        this.estadoTccENUM = EstadoTccENUM.FINALIZADO;
        this.nota = nota;
    }
    
}