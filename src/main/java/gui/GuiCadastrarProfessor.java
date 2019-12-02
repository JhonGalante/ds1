/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProfessorDAO;
import dao.UsuarioDAO;
import helper.HashHelper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import model.Professor;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author Ygor
 */
@ManagedBean
public class GuiCadastrarProfessor {
    
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    
    private List<Professor> professores;
    
    private Usuario usuario;
    private Professor professor;
    
    private String matricula;
    private String email;
    private String nome;
    private String senha;
    private boolean profTCCI;
    private boolean profTCCII;

    public GuiCadastrarProfessor() throws Exception {
        this.professores = professorDAO.listar();
    }
    
    public void iniciarListaProfessores() throws IOException {
        try {
            professores = professorDAO.listar();
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limparCampos() {
        matricula = null;
        email = null;
        nome = null;
        senha = null;
        profTCCI = false;
        profTCCII = false;
    }
    
    public void cadastrar() throws IOException, NoSuchAlgorithmException {
        usuario = new Usuario();
        usuario.setMatricula(matricula);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(HashHelper.criptografarSenha(senha));
        usuario.setTipo(TipoUsuarioENUM.PROFESSOR);
        professor = new Professor();
        professor.setUsuario(usuario);
        if(!profTCCI && !profTCCII){
            professor.setDisponibilidade(true);
        }
        professor.setProfessorTCCI(profTCCI);
        professor.setProfessorTCCII(profTCCII);
        
        try {
            usuarioDAO.incluir(usuario);
            professorDAO.incluir(professor);
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        limparCampos();
        iniciarListaProfessores();
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

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public boolean isProfTCCI() {
        return profTCCI;
    }

    public void setProfTCCI(boolean profTCCI) {
        this.profTCCI = profTCCI;
    }

    public boolean isProfTCCII() {
        return profTCCII;
    }

    public void setProfTCCII(boolean profTCCII) {
        this.profTCCII = profTCCII;
    }
}
