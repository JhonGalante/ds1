/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.UsuarioDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.CursoENUM;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author Ygor
 */
@SessionScoped
@ManagedBean
public class GuiCadastrarSecretaria {
    
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    
    private List<Usuario> usuarios;
    
    private Usuario usuario;
    
    private String matricula;
    private String email;
    private String nome;
    private String senha;
    private CursoENUM curso;
    
    public void iniciarListaSecretaria() throws IOException {
        try {
            usuarios = usuarioDAO.listar();
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Cadastrar() throws IOException {
        usuario = new Usuario();
        usuario.setMatricula(matricula);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senha);
        usuario.setTipoUsuarioENUM(TipoUsuarioENUM.SECRETARIA);
        
        try {
            usuarioDAO.incluir(usuario);
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        iniciarListaSecretaria();
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("Secretaria/cadastro-secretaria.xhtml");
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
