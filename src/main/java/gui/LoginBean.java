/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.AlunoDao;
import dao.ProfessorDao;
<<<<<<< HEAD
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
=======
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
>>>>>>> controletcc
import model.Aluno;
import model.Professor;
import model.Usuario;

/**
 *
 * @author Jardelmc
 */
@SessionScoped
@ManagedBean
<<<<<<< HEAD
public class LoginBean {
=======
public class LoginBean implements Serializable {
>>>>>>> controletcc

    @EJB
    AlunoDao daoAluno;
    @EJB
    ProfessorDao daoProfessor;

    private String matricula;
    private String senha;
<<<<<<< HEAD

    public String logar() {
        try {
            Aluno aluno = daoAluno.find(Long.parseLong(matricula));
            if (aluno != null) {
                if (aluno.getSenha().equals(senha)) {
                    new Usuario().setAluno(aluno);
                    return "Aluno/home";
                } else {
                    System.err.println("Senha incorreta");
                }
            }

            Professor professor = daoProfessor.find(Long.parseLong(matricula));
            if (professor != null) {
                if (professor.getSenha().equals(senha)) {
                    new Usuario().setProfessor(professor);
                    return "Professor/home";
                } else {
                    System.err.println("Senha incorreta");
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return null;
    }

    public String logout() {
        new Usuario().setAluno(null);
        new Usuario().setProfessor(null);
        return "login.xhtml";
=======
    private String mensagem;

    public void logar() throws IOException {
        logout();
        if (!matricula.trim().equals("")) {
            try {
                Aluno aluno = daoAluno.find(Long.parseLong(matricula));
                if (aluno != null) {
                    if (aluno.getSenha().equals(senha)) {
                        new Usuario().setAluno(aluno);
                        FacesContext.getCurrentInstance().getExternalContext().redirect("Aluno/home.xhtml");
                        return;
                    }
                }

                Professor professor = daoProfessor.find(Long.parseLong(matricula));
                if (professor != null) {
                    if (professor.getSenha().equals(senha)) {
                        new Usuario().setProfessor(professor);
                        FacesContext.getCurrentInstance().getExternalContext().redirect("Professor/home.xhtml");
                    }
                }
            } catch (Exception ex) {
                FacesMessage message = new FacesMessage("Erro desconhecido");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            mensagem = "Digite sua matrícula";
        }
    }

    public void logout() throws IOException {
        new Usuario().setAluno(null);
        new Usuario().setProfessor(null);
        mensagem = "";
>>>>>>> controletcc
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

<<<<<<< HEAD
=======
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

>>>>>>> controletcc
}
