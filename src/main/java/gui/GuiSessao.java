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
@SessionScoped
@ManagedBean
public class GuiSessao {

    public Usuario getUsuarioSessao(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if(usuarioLogado != null){
            return usuarioLogado;
        }
        session.invalidate();
        return null;
    }
}
