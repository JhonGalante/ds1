/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class TCCII implements Serializable {
    
    //Atributos
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @OneToOne
    private TermoCompromisso termoCompromisso;
    
    @OneToOne
    private TCCI tccI;
    
    @OneToOne
    private Professor professorTcc;
    
    @NotNull
    private EstadoTccENUM estadoTccENUM;
    
    @OneToOne(cascade=CascadeType.ALL)
    private ApresentacaoTCC apresentacao;
    
    @OneToOne(cascade=CascadeType.ALL)
    private ArquivoTramitacao arquivoTramitacao;
    
    @OneToOne(cascade=CascadeType.ALL)
    private ArquivoAprovacao arquivoAprovacao;
    
    private Float nota;
    
    private boolean dispRepo;
    
    private LocalDate dataEntregaFinal;
    
    @OneToMany(mappedBy = "tccII", targetEntity = MovimentacaoTCCII.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MovimentacaoTCCII> movimentacoesTCC;
    
    
    public TCCII(){
        movimentacoesTCC = new ArrayList<>();
    }
    
    
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

    public TCCI getTccI() {
        return tccI;
    }

    public void setTccI(TCCI tccI) {
        this.tccI = tccI;
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

    public ArquivoAprovacao getArquivoAprovacao() {
        return arquivoAprovacao;
    }

    public void setArquivoAprovacao(ArquivoAprovacao arquivoAprovacao) {
        this.arquivoAprovacao = arquivoAprovacao;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public boolean isDispRepo() {
        return dispRepo;
    }

    public void setDispRepo(boolean dispRepo) {
        this.dispRepo = dispRepo;
    }

    public List<MovimentacaoTCCII> getMovimentacoes() {
        return movimentacoesTCC;
    }

    public void setMovimentacoes(List<MovimentacaoTCCII> movimentacoes) {
        this.movimentacoesTCC = movimentacoes;
    }

    public LocalDate getDataEntregaFinal() {
        return dataEntregaFinal;
    }

    public void setDataEntregaFinal(LocalDate dataEntregaFinal) {
        this.dataEntregaFinal = dataEntregaFinal;
    }

    public List<MovimentacaoTCCII> getMovimentacoesTCC() {
        return movimentacoesTCC;
    }

    public void setMovimentacoesTCC(List<MovimentacaoTCCII> movimentacoesTCC) {
        this.movimentacoesTCC = movimentacoesTCC;
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
        if (!(object instanceof TCCII)) {
            return false;
        }
        TCCII other = (TCCII) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return termoCompromisso.getTema();
    }
}

