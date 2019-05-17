/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.AlunoDao;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Aluno;
import model.CursoENUM;

/**
 *
 * @author Jardelmc
 */
@SessionScoped
@ManagedBean
public class GuiCadastrarAluno {

    Aluno aluno = new Aluno();
    private Integer curso;
    private String mensagem;

    @EJB
    AlunoDao daoAluno;

    public void cadastrarAluno() {
        defineCurso();
        daoAluno.create(aluno);
        mensagemSucesso();
    }

    public void mensagemSucesso() {
        mensagem = aluno.getNome() + " Cadastrado";        
    }

    public void defineCurso() {
        if (curso == 1) {
            aluno.setCurso(CursoENUM.ADMINISTRACAO);
        }
        if (curso == 2) {
            aluno.setCurso(CursoENUM.ENG_PRODUCAO);
        }
        if (curso == 3) {
            aluno.setCurso(CursoENUM.MATEMATICA);
        }
        if (curso == 4) {
            aluno.setCurso(CursoENUM.SIS_INFORMACAO);
        }
        if (aluno.getCurso() == null) {
            aluno.setCurso(CursoENUM.SIS_INFORMACAO);
        }
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public AlunoDao getDaoAluno() {
        return daoAluno;
    }

    public void setDaoAluno(AlunoDao daoAluno) {
        this.daoAluno = daoAluno;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
