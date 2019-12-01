package gui;

import dao.ProfessorDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import dao.UsuarioDAO;
import helper.HashHelper;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Professor;
import model.TipoUsuarioENUM;
import model.Usuario;

/**
 *
 * @author ygor.daudt
 */
@ManagedBean
public class GuiLogin {
    
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    private Usuario usuario;
    private String matricula;
    private String senha;
    private String senhaCript;
    
    
    public String logar() throws IOException{
        try {
            usuario = usuarioDAO.buscarMatricula(matricula);
            senhaCript = HashHelper.criptografarSenha(senha);
        } catch (Exception ex) {
            Logger.getLogger(GuiLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (usuario.getSenha().equals(senhaCript) && usuario != null){
            ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("usuarioLogado", usuario);

            try{
                if (usuario.getTipo().equals(TipoUsuarioENUM.ALUNO)){
                    ext.redirect("Aluno/home.xhtml");
                    return null;
                }
                if (usuario.getTipo().equals(TipoUsuarioENUM.PROFESSOR)){
                    Professor professor = professorDAO.buscarMatricula(usuario.getMatricula());
                    if(professor.isProfessorTCCI() || professor.isProfessorTCCII()){
                        ext.redirect("ProfessorTCC/home.xhtml");
                    }else{
                        ext.redirect("Professor/home.xhtml");
                    }
                    return null;
                }
                if (usuario.getTipo().equals(TipoUsuarioENUM.SECRETARIA)){
                    ext.redirect("Secretaria/home.xhtml");
                    return null;
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        } else if ((usuario.getSenha() != senha) || usuario.equals(null)){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Falha ao logar",  "Mensagem: Usuário ou senha inválidos."));
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
