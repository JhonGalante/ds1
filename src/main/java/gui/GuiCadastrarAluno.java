/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.AlunoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import model.Aluno;
import model.CursoENUM;
import model.Usuario;

/**
 *
 * @author Ygor
 */
@ManagedBean
public class GuiCadastrarAluno {
    
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();
    
    private List<Aluno> alunos;
    
    private Usuario usuario;
    private Aluno aluno;
    
    private String matricula;
    private String email;
    private String nome;
    private String senha;
    private CursoENUM curso;

    public GuiCadastrarAluno() throws Exception {
        this.alunos = alunoDAO.listar();
    }
    
    public void iniciarListaAlunos() throws IOException {
        try {
            alunos = alunoDAO.listar();
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limparCampos() {
        matricula = null;
        email = null;
        nome = null;
        senha = null;
        curso = null;
    }
    
    public void cadastrar() throws IOException {
        usuario = new Usuario();
        usuario.setMatricula(matricula);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senha);
        aluno = new Aluno();
        aluno.setCurso(curso);
        aluno.setUsuario(usuario);
        
        try {
            usuarioDAO.incluir(usuario);
            alunoDAO.incluir(aluno);
        } catch (Exception ex) {
            Logger.getLogger(GuiCadastrarAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        limparCampos();
        iniciarListaAlunos();
    }
    
    public CursoENUM[] getCursosENUM() {
        return CursoENUM.values();
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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
