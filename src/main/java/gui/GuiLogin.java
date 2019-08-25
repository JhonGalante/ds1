package gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import dao.UsuarioDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author ygor.daudt
 */

@ManagedBean
public class GuiLogin {
    
    private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private Usuario usuario;
    private String matricula;
    private String senha;
    
    
    public String logar(){
        
        try {
            usuario = usuarioDAO.buscarMatricula(matricula);
        } catch (Exception ex) {
            Logger.getLogger(GuiLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (usuario.getSenha().equals(senha)){
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", usuario.getMatricula());
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("usuarioLogado", usuario);
            if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.ALUNO)){
                return "Aluno/home.xhtml";
            }
            if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.PROFESSOR)){
                return "Professor/home.xhtml";
            }
            if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.SECRETARIA)){
                return "Secretaria/home.xhtml";
            }
        }
        
        return "index.xhtml";
    }
    
    public String logout(){
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", null);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "index.xhtml";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
