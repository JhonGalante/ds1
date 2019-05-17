/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jhona
 */
@Entity
public class Professor implements Serializable{
    
    @Id    
    private Long matricula;
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    private String senha;
    @NotNull
    private CursoENUM curso;

    @Override
    public String toString() {
        return this.nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public CursoENUM getCurso() {
        return curso;
    }

    public void setCurso(CursoENUM curso) {
        this.curso = curso;
    }
    
    

}
