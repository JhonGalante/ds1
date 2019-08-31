/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jhonata
 */
public class SessionListenerHelper implements PhaseListener{
    
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ext = fc.getExternalContext();
        HttpSession session = (HttpSession) ext.getSession(true);
        //Verifica se a página aberta não é a index
        if(!fc.getViewRoot().getViewId().equals("/index.xhtml")){
            //Verifica se não existe nenhum atributo de nome usuarioLogado
            if(session.getAttribute("usuarioLogado") == null){
                try{
                  session.invalidate();
                  ext.redirect(ext.getRequestContextPath() + "/index.xhtml");
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
