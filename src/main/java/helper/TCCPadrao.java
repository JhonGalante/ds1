/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.ApresentacaoTCC;
import model.ArquivoTramitacao;
import model.EstadoTccENUM;
import model.Professor;
import model.TermoCompromisso;

/**
 *
 * @author jhona
 */
public class TCCPadrao implements Serializable {
    
    private Long id;
    private TermoCompromisso termoCompromisso;
    private Professor professorTcc;
    private EstadoTccENUM estadoTccENUM;
    private ApresentacaoTCC apresentacao;
    private ArquivoTramitacao arquivoTramitacao;
    private Float nota;
    private boolean dispRepo;
    private LocalDate dataEntregaFinal;
    private LocalDateTime ultimaEntrega;
    private int etapaTCC;

    public TCCPadrao(Long id, float nota, TermoCompromisso termoCompromisso, LocalDate dataEntregaFinal, int etapaTCC) {
        this.termoCompromisso = termoCompromisso;
        this.dataEntregaFinal = dataEntregaFinal;
        this.id = id;
        this.nota = nota;
        this.etapaTCC = etapaTCC;
    }
    
    public TCCPadrao(Long id, float nota, TermoCompromisso termoCompromisso, LocalDateTime ultimaEntrega, int etapaTCC) {
        this.termoCompromisso = termoCompromisso;
        this.ultimaEntrega = ultimaEntrega;
        this.id = id;
        this.nota = nota;
        this.etapaTCC = etapaTCC;
    }
    
    public TCCPadrao(Long id, TermoCompromisso termoCompromisso, int etapaTCC) {
        this.termoCompromisso = termoCompromisso;
        this.id = id;
        this.etapaTCC = etapaTCC;
    }
    
    public TCCPadrao(float nota){
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDispRepo() {
        return dispRepo;
    }

    public void setDispRepo(boolean dispRepo) {
        this.dispRepo = dispRepo;
    }

    public LocalDate getDataEntregaFinal() {
        return dataEntregaFinal;
    }

    public void setDataEntregaFinal(LocalDate dataEntregaFinal) {
        this.dataEntregaFinal = dataEntregaFinal;
    }

    public int getEtapaTCC() {
        return etapaTCC;
    }

    public void setEtapaTCC(int etapaTCC) {
        this.etapaTCC = etapaTCC;
    }

    public LocalDateTime getUltimaEntrega() {
        return ultimaEntrega;
    }

    public void setUltimaEntrega(LocalDateTime ultimaEntrega) {
        this.ultimaEntrega = ultimaEntrega;
    }
}
