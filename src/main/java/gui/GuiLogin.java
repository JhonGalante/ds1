/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import dao.UsuarioDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author ygor.daudt
 */
@Named(value = "guiLogin")
@SessionScoped
@ManagedBean
public class GuiLogin {
    @EJB
    UsuarioDAO usuarioDAO;
    
    private Usuario usuario;
    private String matricula;
    private String senha;
    
    public String logar(){
        try {
            usuario = usuarioDAO.buscarMatricula(matricula);
        } catch (Exception ex) {
            Logger.getLogger(GuiLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (usuario.getSenha().equals(senha)){
            if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.ALUNO)){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", usuario.getMatricula());
                return "Aluno/home.xhtml";
            }
            if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.PROFESSOR)){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", usuario.getMatricula());
                return "Professor/home.xhtml";
            }
            if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.SECRETARIA)){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", usuario.getMatricula());
                return "Secretaria/home.xhtml";
            }
        }
        return "index.xhtml";
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", null);
        return "index.xhtml";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    
}
