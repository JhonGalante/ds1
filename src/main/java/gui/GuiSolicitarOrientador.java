/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import model.Professor;
import model.TermoCompromisso;

/**
 *
 * @author Ygor
 */
@Named(value = "guiSolicitarOrientador")
@SessionScoped
public class GuiSolicitarOrientador {
    /*
    private List<Professor> professoresOrientadores;
    private List<TermoCompromisso> termosCompromisso;
    private Professor professorOrientador;
    private TermoCompromisso termoCompromiso;
    
    @EJB
    ProfessorDAO professorDAO;
    @EJB
    TermoCompromissoDAO termoCompromissoDAO;
    
    
    public GuiSolicitarOrientador(){
        
    }
    
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
    
    */
}