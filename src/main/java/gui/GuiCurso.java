/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.CursoDao;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import model.Curso;

/**
 *
 * @author acg
 */
@Named(value = "guiCurso")
@SessionScoped
public class GuiCurso implements Serializable {

    private List<Curso> cursos;
    private Curso curso;
    private Boolean alterar;

    @EJB
    CursoDao daoCurso;

    /**
     * Creates a new instance of GuiCurso
     */
    public GuiCurso() {
    }

    public String iniciarLista() {
        cursos = daoCurso.getCursos();
        return "FrmLstCursos";
    }

    public String incluir() {
        alterar = false;
        curso = new Curso();
        return "FrmCadCurso";
    }

    public String alterar(Curso curso) {
        alterar = true;
        this.curso = curso;
        return "FrmCadCurso";
    }

    public String excluir(Curso curso) {
        daoCurso.excluir(curso);
        cursos = daoCurso.getCursos();
        return null;
    }

    public String gravar() {
        if (alterar) {
            daoCurso.alterar(curso);
        } else {
            daoCurso.gravar(curso);
        }
        
        cursos = daoCurso.getCursos();
        return "FrmLstCursos";
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Boolean getAlterar() {
        return alterar;
    }

    public void setAlterar(Boolean alterar) {
        this.alterar = alterar;
    }

    public CursoDao getDaoCurso() {
        return daoCurso;
    }

    public void setDaoCurso(CursoDao daoCurso) {
        this.daoCurso = daoCurso;
    }
    
    
}
