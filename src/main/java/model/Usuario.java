/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jhona
 */
@ManagedBean
public class Usuario implements Serializable{
    
    @Id
    private long id;
    @NotNull
    private String login;
    @NotNull
    private String senha;
    @NotNull
    private String email;
    @NotNull
    private TipoUsuarioENUM tipoUsuario;

    
    @Override
    public String toString() {
        return this.login;
    }
    
    public String getUsuario() {
        return login;
    }

    public void setUsuario(String usuario) {
        this.login = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuarioENUM getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioENUM tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public long getId() {
        return id;
    }
    
}
