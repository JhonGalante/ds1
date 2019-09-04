package gui;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.SecretariaDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import dao.UsuarioDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Aluno;
import model.Professor;
import model.Secretaria;
import model.Usuario;

/**
 *
 * @author ygor.daudt
 */
@ManagedBean
public class GuiLogin {
    
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private Usuario usuario;
    private String matricula;
    private String senha;
    private List<Aluno> alunos = new ArrayList<>();
    private List<Professor> professores = new ArrayList<>();
    private List<Secretaria> secretarias = new ArrayList<>();
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();
    private final ProfessorDAO professorDAO = ProfessorDAO.getInstance();
    private final SecretariaDAO secretariaDAO = SecretariaDAO.getInstance();
    
    public void logar() throws IOException, Exception{        
        try {
            usuario = usuarioDAO.buscarMatricula(matricula);
        } catch (Exception ex) {
            Logger.getLogger(GuiLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            validarLogin(usuario);
        } catch (Exception ex) {
            Logger.getLogger(GuiLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("usuarioLogado", usuario);
        
        try {
            alunos = alunoDAO.listar();
            professores = professorDAO.listar();
            secretarias = secretariaDAO.listar();
        } catch (Exception ex) {
            Logger.getLogger(GuiLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Aluno a : alunos)
            if (a.getUsuario().getMatricula().equals(usuario.getMatricula())) ext.redirect("Aluno/home.xhtml");
        
        for (Professor p : professores)
            if (p.getUsuario().getMatricula().equals(usuario.getMatricula())) ext.redirect("Professor/home.xhtml");
        
        for (Secretaria s : secretarias) 
            if (s.getUsuario().getMatricula().equals(usuario.getMatricula())) ext.redirect("Secretaria/home.xhtml");
        
    }

    public Boolean validarLogin(Usuario usuario) throws Exception {
        if (usuario.getSenha().equals(senha)) {
            return true;
        }
        else {
            throw new Exception ("Usuário ou senha inválida");
        }
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
