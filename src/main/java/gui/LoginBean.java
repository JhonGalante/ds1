/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.AlunoDao;
import dao.ProfessorDao;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Aluno;
import model.Professor;
import model.Usuario;

/**
 *
 * @author Jardelmc
 */
@SessionScoped
@ManagedBean
public class LoginBean {

    @EJB
    AlunoDao daoAluno;
    @EJB
    ProfessorDao daoProfessor;

    private String matricula;
    private String senha;

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
