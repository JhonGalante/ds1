/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import javax.faces.bean.ManagedBean;

/**
 *
 * @author jhona
 */
@ManagedBean
public class Usuario{

    private static Aluno aluno = null;
    private static Professor professor = null;

    public Aluno getAluno() {
        return aluno;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    
    
}
