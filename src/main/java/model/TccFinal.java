/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author jhona
 */
@Entity
public class TccFinal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tema;
    @OneToOne
    private Aluno autor;
    @OneToOne
    private Professor orientador;
    @OneToOne
    private Professor professorTcc;
    private List<String> professoresBanca;
    private Double nota;
    @OneToOne
    private ArquivoTcc tcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof TccFinal)) {
            return false;
        }
        TccFinal other = (TccFinal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.tema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Aluno getAutor() {
        return autor;
    }

    public void setAutor(Aluno autor) {
        this.autor = autor;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public Professor getProfessorTcc() {
        return professorTcc;
    }

    public void setProfessorTcc(Professor professorTcc) {
        this.professorTcc = professorTcc;
    }

    public List<String> getProfessoresBanca() {
        return professoresBanca;
    }

    public void setProfessoresBanca(List<String> professoresBanca) {
        this.professoresBanca = professoresBanca;
    }    

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public ArquivoTcc getTcc() {
        return tcc;
    }

    public void setTcc(ArquivoTcc tcc) {
        this.tcc = tcc;
    }
    
    
}
