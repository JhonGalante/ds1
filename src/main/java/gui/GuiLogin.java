package gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import dao.UsuarioDAO;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author ygor.daudt
 */
@SessionScoped
@ManagedBean
public class GuiLogin {
    
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private Usuario usuario;
    private String matricula;
    private String senha;
    
    
    public String logar() throws IOException{
        try {
            usuario = usuarioDAO.buscarMatricula(matricula);
        } catch (Exception ex) {
            Logger.getLogger(GuiLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (usuario.getSenha().equals(senha)){
            ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("usuarioLogado", usuario);

            try{
                if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.ALUNO)){
                    ext.redirect("Aluno/home.xhtml");
                    return null;
                }
                if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.PROFESSOR)){
                    ext.redirect("Professor/home.xhtml");
                    return null;
                }
                if (usuario.getTipoUsuarioENUM().equals(TipoUsuarioENUM.SECRETARIA)){
                    ext.redirect("Secretaria/home.xhtml");
                    return null;
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        return null;
    }
    
    public void logout(){
        try{
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
        }catch(Exception ex){
            ex.printStackTrace();
        }
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
