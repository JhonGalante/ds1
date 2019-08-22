/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ronald Tadeu
 */
public class TCCI implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String tema;
    @NotNull
    @OneToOne
    private Aluno aluno;
    @OneToOne
    private Professor orientador;
    @NotNull
    @OneToOne
    private Professor professorTCC;
    private LocalDate dataApresentacao;
    private float nota;
    private EstadoTccENUM estado;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public Professor getProfessorTCC() {
        return professorTCC;
    }

    public void setProfessorTCC(Professor professorTCC) {
        this.professorTCC = professorTCC;
    }

    public LocalDate getDataApresentacao() {
        return dataApresentacao;
    }

    public void setDataApresentacao(LocalDate dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
    
    
    
}
