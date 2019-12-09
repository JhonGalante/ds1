package webservice;

import com.google.gson.Gson;
import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import dao.TermoCompromissoDAO;
import dao.UsuarioDAO;
import gui.GuiAceitarSolicitacaoOrientacao;
import gui.GuiSolicitarOrientador;
import helper.HashHelper;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Aluno;
import model.EstadoTccENUM;
import model.EstadoTermoCompromissoENUM;
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;
import model.TipoUsuarioENUM;
import model.Usuario;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;


/**
 *
 * @author jhona
 */
@Named
@Path("termo")
@Consumes(MediaType.APPLICATION_JSON)
public class TermoRest{
    
    private final TermoCompromissoDAO termoDao = TermoCompromissoDAO.getInstance();
    private final ProfessorDAO profDao = ProfessorDAO.getInstance();
    private final AlunoDAO alunoDao = AlunoDAO.getInstance();
    private final TCCIDAO tcciDao = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDao = TCCIIDAO.getInstance();
    private final Gson gson = new Gson();
    
    
    @POST
    @Path("buscar-termos-professor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarTermosProfessor(String res) throws Exception{
        JSONObject obj = new JSONObject(res);
        JSONArray arrayObject = new JSONArray();
        
        for(TermoCompromisso termo : termoDao.buscarTermosPendentesAceitacao(obj.getString("matricula"))){
            JSONObject object = new JSONObject();
            object.put("id", termo.getId());
            object.put("aluno", termo.getAluno().getUsuario().getNome());
            object.put("etapa", termo.getAluno().getEtapaTcc());
            object.put("curso", termo.getAluno().getCurso());
            object.put("tema", termo.getTema());
            object.put("titulo", termo.getTitulo());
            object.put("data-solicitacao", termo.getDataHoraSolicitacao());
            arrayObject.put(object);
        }
        return arrayObject.toString();
    }
    
    @POST
    @Path("enviar-solicitacao")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enviarSolicitacao(String res) throws NoSuchAlgorithmException, Exception{
        
        TermoCompromisso termo = new TermoCompromisso();
        JSONObject obj = new JSONObject(res);
        Aluno aluno = alunoDao.buscarMatricula(obj.getString("matricula-aluno"));
        Professor professor = profDao.buscarMatricula(obj.getString("matricula-professor"));
        if(!validarSolicitacao(aluno, professor)) return Response.status(Response.Status.CONFLICT).build();
        
        termo.setAluno(aluno);
        termo.setTema(obj.getString("tema"));
        termo.setTitulo(obj.getString("titulo"));
        termo.setPalavrasChave(obj.getString("palavras-chave"));
        termo.setProfessor(professor);
        termo.setDataHoraSolicitacao(LocalDate.now());
        termo.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_ANALISE);
        
        try{
            termoDao.incluir(termo);
            return Response.ok().build();
        }catch(Exception ex){
            ex.printStackTrace();
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }  
    }
    
    @POST
    @Path("aceitar-solicitacao")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response aceitarSolicitacao(String res) throws NoSuchAlgorithmException, Exception{
        JSONObject obj = new JSONObject(res);
        TCCI tccI;
        TCCII tccII;
        TermoCompromisso termoCompromisso = termoDao.pesquisarPorId(Long.parseLong(obj.getString("id")));
        termoCompromisso.setEstadoTermoCompromissoENUM(EstadoTermoCompromissoENUM.SOLICITACAO_ACEITA);
        termoCompromisso.setDataHoraAceiteSolicitacao(LocalDate.now());

        if (termoCompromisso.getEtapaTcc() == 1) {
            tccI = new TCCI();
            tccI.setTermoCompromisso(termoCompromisso);
            tccI.setEstadoTccENUM(EstadoTccENUM.ENTREGA);

            try{
                //tccI.setProfessorTcc(profDao.buscarProfessorTCCI());
            }catch(NoResultException ex){
                ex.printStackTrace();
                tccI.setProfessorTcc(null);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            try {
                termoDao.alterar(termoCompromisso);
                tcciDao.incluir(tccI);
            } catch(Exception ex) {
                Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
        if (termoCompromisso.getEtapaTcc() == 2) {
            tccII = new TCCII();
            tccII.setTermoCompromisso(termoCompromisso);
            tccII.setEstadoTccENUM(EstadoTccENUM.ENTREGA);
            tccII.setDispRepo(false);
            try {
                //tccII.setProfessorTcc(profDao.buscarProfessorTCCII());
                termoDao.alterar(termoCompromisso);
                tcciiDao.incluir(tccII);
            } catch(Exception ex) {
                Logger.getLogger(GuiAceitarSolicitacaoOrientacao.class.getName()).log(Level.SEVERE, null, ex);
                return Response.status(Response.Status.CONFLICT).build();
            } 
        }
        return Response.ok().build();
    }
 
    public Boolean validarSolicitacao(Aluno aluno, Professor professorOrientador) throws Exception {
        if (professorOrientador == null) {
            return false;
        }
        try {
            for (TermoCompromisso termo : termoDao.listar()) {
                if (termo.getAluno().equals(aluno)
                    && !(termo.getEstadoTermoCompromissoENUM().equals(EstadoTermoCompromissoENUM.SOLICITACAO_RECUSADA))) {
                        return false;
                }
            }
        } catch(Exception ex) {
            Logger.getLogger(GuiSolicitarOrientador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
}
