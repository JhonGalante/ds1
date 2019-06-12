/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author jhona
 */
@Entity
public class Movimentacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    @OneToOne(cascade=CascadeType.ALL)
    private ArquivoTcc tcc;
    @OneToOne
    private TccInicio informacoesTcc;
    private String comentarioAluno;
    private String comentarioProfessor;
    private String comentarioOrientador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public ArquivoTcc getTcc() {
        return tcc;
    }

    public void setTcc(ArquivoTcc tcc) {
        this.tcc = tcc;
    }

    public TccInicio getInformacoesTcc() {
        return informacoesTcc;
    }

    public void setInformacoesTcc(TccInicio informacoesTcc) {
        this.informacoesTcc = informacoesTcc;
    }

    public String getComentarioAluno() {
        return comentarioAluno;
    }

    public void setComentarioAluno(String comentarioAluno) {
        this.comentarioAluno = comentarioAluno;
    }

    public String getComentarioProfessor() {
        return comentarioProfessor;
    }

    public void setComentarioProfessor(String comentarioProfessor) {
        this.comentarioProfessor = comentarioProfessor;
    }

    public String getComentarioOrientador() {
        return comentarioOrientador;
    }

    public void setComentarioOrientador(String comentarioOrientador) {
        this.comentarioOrientador = comentarioOrientador;
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
        if (!(object instanceof Movimentacao)) {
            return false;
        }
        Movimentacao other = (Movimentacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
