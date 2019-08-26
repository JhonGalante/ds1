/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import dao.ProfessorDAO;
import dao.TermoCompromissoDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import model.Professor;
import model.TermoCompromisso;

/**
 *
 * @author Ygor
 */
@SessionScoped
@ManagedBean
public class GuiSolicitarOrientador {
    
    private List<Professor> professoresOrientadores;
    private List<TermoCompromisso> termosCompromisso;
    private Professor professorOrientador;
    private TermoCompromisso termoCompromiso;
    
    
    ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    TermoCompromissoDAO termoCompromissoDAO = TermoCompromissoDAO.getInstance();
    
    public String iniciarListaProfessores(){
        try {
            professoresOrientadores = professorDAO.listar();
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "SolicitacaoOrientador";
    }
    
    public String novaSolicitacao(){
        try {
            termosCompromisso = termoCompromissoDAO.listar();
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(TermoCompromisso t : termosCompromisso){

        }
        termoCompromiso = new TermoCompromisso();
        return null;
    }

    public List<Professor> getProfessoresOrientadores() {
        return professoresOrientadores;
    }

    public void setProfessoresOrientadores(List<Professor> professoresOrientadores) {
        this.professoresOrientadores = professoresOrientadores;
    }

    public List<TermoCompromisso> getTermosCompromisso() {
        return termosCompromisso;
    }

    public void setTermosCompromisso(List<TermoCompromisso> termosCompromisso) {
        this.termosCompromisso = termosCompromisso;
    }

    public Professor getProfessorOrientador() {
        return professorOrientador;
    }

    public void setProfessorOrientador(Professor professorOrientador) {
        this.professorOrientador = professorOrientador;
    }

    public TermoCompromisso getTermoCompromiso() {
        return termoCompromiso;
    }

    public void setTermoCompromiso(TermoCompromisso termoCompromiso) {
        this.termoCompromiso = termoCompromiso;
    }

    public ProfessorDAO getProfessorDAO() {
        return professorDAO;
    }

    public void setProfessorDAO(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    public TermoCompromissoDAO getTermoCompromissoDAO() {
        return termoCompromissoDAO;
    }

    public void setTermoCompromissoDAO(TermoCompromissoDAO termoCompromissoDAO) {
        this.termoCompromissoDAO = termoCompromissoDAO;
    }
    
    
}
