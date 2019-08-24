/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ygor.daudt
 */
@Named(value = "guiLogin")
@SessionScoped
public class GuiLogin {
    @EJB
    AlunoDAO alunoDAO;
    @EJB
    ProfessorDAO professorDAO;

    private String matricula;
    private String senha;
    

    
}
