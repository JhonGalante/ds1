/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.AlunoDao;
import dao.ProfessorDao;
import dao.SecretariaDao;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Aluno;
import model.Professor;
import model.Secretaria;
import model.Usuario;

/**
 *
 * @author Jardelmc
 */
@SessionScoped
@ManagedBean
public class LoginBean implements Serializable {

    @EJB
    AlunoDao daoAluno;
    @EJB
    ProfessorDao daoProfessor;
    @EJB
    SecretariaDao daoSecretaria;

    private String matricula;
    private String senha;
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
                
                Secretaria secretaria = daoSecretaria.find(Long.parseLong(matricula));
                if (secretaria != null) {
                    if (secretaria.getSenha().equals(senha)) {
                        new Usuario().setSecretaria(secretaria);
                        FacesContext.getCurrentInstance().getExternalContext().redirect("Secretaria/home.xhtml");
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
        new Usuario().setSecretaria(null);
        mensagem = "";
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
