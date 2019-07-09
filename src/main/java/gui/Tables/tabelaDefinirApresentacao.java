/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Tables;

import dao.ProfessorDao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Professor;
import model.TccFinal;

/**
 *
 * @author Ronald Tadeu
 */
@ManagedBean
@ViewScoped
public class tabelaDefinirApresentacao implements Serializable {

    /**
     * Creates a new instance of tabelaDefinirApresentacao
     */
    
    @EJB
    ProfessorDao professorDao;
    
    private List<Professor> professoresSelec;
    private Professor professor;
    private Date data;
    private final TccFinal tcc;
    
    public tabelaDefinirApresentacao() {
        tcc = new TccFinal();
    }
    
    public void agendar(){
        tcc.setDataApresentacao(data);
        tcc.setProfessoresBanca(professoresSelec);
        saveMessage();
    }
    
    public List<Professor> banca(){
        return professorDao.findAll();
    }

    public List<Professor> getProfessoresSelec() {
        return professoresSelec;
    }

    public void setProfessoresSelec(List<Professor> professoresSelec) {
        this.professoresSelec = professoresSelec;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Apresentação agendada com sucesso!") );
    }
    
    
    
    
    
    

    
    
    
    
    
}
