/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Jardelmc
 */
@Entity
public class SolicitacaoOrientador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Aluno aluno;
    @OneToOne
    private Professor orientador;
    private String descricaoTema;
    private String tituloTcc;
    
    @Override
    public String toString() {
        return this.aluno.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescricaoTema() {
        return descricaoTema;
    }

    public void setDescricaoTema(String descricaoTema) {
        this.descricaoTema = descricaoTema;
    }

    public String getTituloTcc() {
        return tituloTcc;
    }

    public void setTituloTcc(String tituloTcc) {
        this.tituloTcc = tituloTcc;
    }
    
    
    
}
