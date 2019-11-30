/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.SecretariaDAO;
import dao.UsuarioDAO;
import helper.HashHelper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import model.Secretaria;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author Ygor
 */
@ManagedBean
public class GuiCadastrarSecretaria {
    
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final SecretariaDAO secretariaDAO = SecretariaDAO.getInstance();
    
    private List<Usuario> usuarios;
    private List<Secretaria> secretarias;
    
    private Usuario usuario;
    private Secretaria secretaria;
    
    private String matricula;
    private String email;
    private String nome;
    private String senha;

    public GuiCadastrarSecretaria() throws Exception {
        iniciarListaSecretaria();
    }
    
    public void iniciarListaSecretaria() throws IOException {
        try {
            secretarias = secretariaDAO.listar();
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limparCampos() {
        matricula = null;
        email = null;
        nome = null;
        senha = null;
    }
    
    public void cadastrar() throws IOException, NoSuchAlgorithmException {
        usuario = new Usuario();
        usuario.setMatricula(matricula);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(HashHelper.criptografarSenha(senha));
        usuario.setTipo(TipoUsuarioENUM.SECRETARIA);
        secretaria = new Secretaria();
        secretaria.setUsuario(usuario);
        
        try {
            usuarioDAO.incluir(usuario);
            secretariaDAO.incluir(secretaria);
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }
        limparCampos();
        iniciarListaSecretaria();
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

    public List<Secretaria> getSecretarias() {
        return secretarias;
    }

    public void setSecretarias(List<Secretaria> secretarias) {
        this.secretarias = secretarias;
    }

    public Secretaria getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Secretaria secretaria) {
        this.secretaria = secretaria;
    }
    
    
    

}
