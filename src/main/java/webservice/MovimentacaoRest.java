package webservice;

import com.google.gson.Gson;
import dao.AlunoDAO;
import dao.MovimentacaoTCCIDAO;
import dao.MovimentacaoTCCIIDAO;
import dao.ProfessorDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import dao.TermoCompromissoDAO;
import dao.UsuarioDAO;
import gui.GuiSolicitarOrientador;
import helper.HashHelper;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
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
import model.EstadoTermoCompromissoENUM;
import model.MovimentacaoTCCI;
import model.MovimentacaoTCCII;
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
@Path("movimentacao")
@Consumes(MediaType.APPLICATION_JSON)
public class MovimentacaoRest{
    
    private final TermoCompromissoDAO termoDao = TermoCompromissoDAO.getInstance();
    private final AlunoDAO alunoDao = AlunoDAO.getInstance();
    private final TCCIDAO tcciDao = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDao = TCCIIDAO.getInstance();
    
    
    @POST
    @Path("buscar-movimentacao")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarMovimentacaoTCC(@FormParam("matricula") String matricula) throws Exception{
        
        JSONArray arrayObj = new JSONArray();
        Aluno aluno = alunoDao.buscarMatricula(matricula);
        TCCI tcci;
        TCCII tccii;
        
        if(aluno.getEtapaTcc() == 1){
            tcci = tcciDao.buscarPorTermo(termoDao.pesquisarPorAlunoEtapa(aluno, aluno.getEtapaTcc()));
            for(MovimentacaoTCCI movI : tcci.getMovimentacoesTCC()){
                JSONObject obj = new JSONObject();
                obj.put("comentario", movI.getComentario());
                obj.put("data-publicacao", movI.getDataHora());
                obj.put("data-prox-entrega", movI.getDataProximaEntrega());
                arrayObj.put(obj);
            }
        }else{
            tccii = tcciiDao.buscarPorTermo(termoDao.pesquisarPorAlunoEtapa(aluno, aluno.getEtapaTcc()));
            for(MovimentacaoTCCII movII : tccii.getMovimentacoesTCC()){
                JSONObject obj = new JSONObject();
                obj.put("comentario", movII.getComentario());
                obj.put("data-publicacao", movII.getDataHora());
                obj.put("data-prox-entrega", movII.getDataProximaEntrega());
                arrayObj.put(obj);
            }
        }
        return arrayObj.toString();
    }
}
