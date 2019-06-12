/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProfessorDao;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.CursoENUM;
import model.Professor;

/**
 *
 * @author Jardelmc
 */
@SessionScoped
@ManagedBean
public class GuiCadastrarProfessor {

    Professor professor = new Professor();
    private Integer curso;
    private String mensagem;

    @EJB
    ProfessorDao daoProfessor;

    public void cadastrarProfessor() {
        defineCurso();
        daoProfessor.create(professor);
        mensagemSucesso();
    }
    
    public void mensagemSucesso() {
        mensagem = professor.getNome() + " Cadastrado";
    }

    public void defineCurso() {
        if (curso == 1) {
            professor.setCurso(CursoENUM.ADMINISTRACAO);
        }
        if (curso == 2) {
            professor.setCurso(CursoENUM.ENG_PRODUCAO);
        }
        if (curso == 3) {
            professor.setCurso(CursoENUM.MATEMATICA);
        }
        if (curso == 4) {
            professor.setCurso(CursoENUM.SIS_INFORMACAO);
        }
        if (professor.getCurso() == null) {
            professor.setCurso(CursoENUM.SIS_INFORMACAO);
        }
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public ProfessorDao getDaoProfessor() {
        return daoProfessor;
    }

    public void setDaoProfessor(ProfessorDao daoProfessor) {
        this.daoProfessor = daoProfessor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
    

}
