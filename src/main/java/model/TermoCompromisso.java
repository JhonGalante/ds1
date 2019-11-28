/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ygor
 */
@Entity
public class TermoCompromisso implements Serializable {  //substitui o "SolicitacaoOrientador"
    
    //Atributos
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @OneToOne
    @JoinColumn(name="aluno_id")
    private Aluno aluno;
    @NotNull
    @OneToOne
    @JoinColumn(name="professor_id")
    private Professor professor;
    @NotNull
    private String tema;
    @NotNull
    private String titulo;
    @NotNull
    private LocalDate dataHoraSolicitacao;
    private LocalDate dataHoraAceiteSolicitacao;
    private LocalDate dataHoraRecusaSolicitacao;
    @NotNull
    private EstadoTermoCompromissoENUM estadoTermoCompromissoENUM;
    @NotNull
    private int etapaTcc;
    @NotNull
    private String palavrasChave;
    
    //Métodos
    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDate dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public EstadoTermoCompromissoENUM getEstadoTermoCompromissoENUM() {
        return estadoTermoCompromissoENUM;
    }

    public void setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM estadoTermoCompromissoENUM) {
        this.estadoTermoCompromissoENUM = estadoTermoCompromissoENUM;
    }

    public int getEtapaTcc() {
        return etapaTcc;
    }

    public void setEtapaTcc(int etapaTcc) {
        this.etapaTcc = etapaTcc;
    }

    public LocalDate getDataHoraAceiteSolicitacao() {
        return dataHoraAceiteSolicitacao;
    }

    public void setDataHoraAceiteSolicitacao(LocalDate dataHoraAceiteSolicitacao) {
        this.dataHoraAceiteSolicitacao = dataHoraAceiteSolicitacao;
    }

    public LocalDate getDataHoraRecusaSolicitacao() {
        return dataHoraRecusaSolicitacao;
    }

    public void setDataHoraRecusaSolicitacao(LocalDate dataHoraRecusaSolicitacao) {
        this.dataHoraRecusaSolicitacao = dataHoraRecusaSolicitacao;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
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
        if (!(object instanceof TermoCompromisso)) {
            return false;
        }
        TermoCompromisso other = (TermoCompromisso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return aluno.getUsuario().getNome();
    }
    
}
