/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ygor
 */
@Entity
public class Usuario implements Serializable {
    
    //Atributos
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    private String matricula;
    @NotNull
    private String senha;
    private String email;
    @NotNull
    private String nome;
    private TipoUsuarioENUM tipoUsuarioENUM;
    
    //Métodos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuarioENUM getTipo() {
        return tipoUsuarioENUM;
    }

    public void setTipo(TipoUsuarioENUM tipoUsuarioENUM) {
        this.tipoUsuarioENUM = tipoUsuarioENUM;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.matricula);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return matricula;
    }
    
}
