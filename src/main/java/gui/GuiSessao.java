/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Usuario;

/**
 *
 * @author jhonata
 */

@ManagedBean
@SessionScoped
public class GuiSessao {
    
    private Usuario usuarioSessao;
    
    public Usuario getUsuarioSessao(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuarioSessao = (Usuario) session.getAttribute("usuarioLogado");
        return usuarioSessao;
    }
    
}
