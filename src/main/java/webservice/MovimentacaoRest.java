package webservice;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import dao.TermoCompromissoDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Aluno;
import model.MovimentacaoTCCI;
import model.MovimentacaoTCCII;
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.TermoCompromisso;
import model.TipoMovimentacaoENUM;
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
    private final ProfessorDAO professorDao = ProfessorDAO.getInstance();
    private final TCCIDAO tcciDao = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDao = TCCIIDAO.getInstance();
    
    
    @POST
    @Path("buscar-movimentacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarMovimentacaoTCC(String res) throws Exception{
        JSONObject obj = new JSONObject(res);
        JSONArray arrayObj = new JSONArray();
        Aluno aluno = alunoDao.buscarMatricula(obj.getString("matricula"));
        TCCI tcci;
        TCCII tccii;
        
        if(aluno.getEtapaTcc() == 1){
            tcci = tcciDao.buscarPorTermo(termoDao.pesquisarPorAlunoEtapa(aluno, aluno.getEtapaTcc()));
            for(MovimentacaoTCCI movI : tcci.getMovimentacoesTCC()){
                JSONObject objTemp = new JSONObject();
                objTemp.put("comentario", movI.getComentario());
                objTemp.put("data-publicacao", movI.getDataHora());
                objTemp.put("data-prox-entrega", movI.getDataProximaEntrega());
                arrayObj.put(objTemp);
            }
        }else{
            tccii = tcciiDao.buscarPorTermo(termoDao.pesquisarPorAlunoEtapa(aluno, aluno.getEtapaTcc()));
            for(MovimentacaoTCCII movII : tccii.getMovimentacoesTCC()){
                JSONObject objTemp = new JSONObject();
                objTemp.put("comentario", movII.getComentario());
                objTemp.put("data-publicacao", movII.getDataHora());
                objTemp.put("data-prox-entrega", movII.getDataProximaEntrega());
                arrayObj.put(objTemp);
            }
        }
        return arrayObj.toString();
    }
    
    @POST
    @Path("enviar-movimentacao-aluno")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarMovimentacaoAluno(String res) throws Exception{
        JSONObject obj = new JSONObject(res);

        Aluno aluno = alunoDao.buscarMatricula(obj.getString("matricula"));
        TCCI tcci;
        TCCII tccii;
        
        if(aluno.getEtapaTcc() == 1){
            tcci = tcciDao.buscarPorTermo(termoDao.pesquisarPorAlunoEtapa(aluno, aluno.getEtapaTcc()));
            MovimentacaoTCCI movI = new MovimentacaoTCCI(LocalDateTime.now(), TipoMovimentacaoENUM.ENTREGA, obj.getString("comentario"), aluno.getUsuario(), tcci);
            tcci.getMovimentacoesTCC().add(movI);
            tcciDao.alterar(tcci);
            
        }else{
            tccii = tcciiDao.buscarPorTermo(termoDao.pesquisarPorAlunoEtapa(aluno, aluno.getEtapaTcc()));
            MovimentacaoTCCII movII = new MovimentacaoTCCII(LocalDateTime.now(), TipoMovimentacaoENUM.ENTREGA, obj.getString("comentario"),aluno.getUsuario(), tccii);
            tccii.getMovimentacoesTCC().add(movII);
            tcciDao.alterar(tccii);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("enviar-movimentacao-professor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarMovimentacaoProfessor(String res) throws Exception{
        JSONObject obj = new JSONObject(res);

        Professor professor = professorDao.buscarMatricula(obj.getString("matricula"));
        TCCI tcci;
        TCCII tccii;
        TermoCompromisso termo = termoDao.pesquisarPorId(Long.parseLong(obj.getString("id-termo")));
        
        if(termo.getEtapaTcc() == 1){
            tcci = tcciDao.buscarPorTermo(termo);
            MovimentacaoTCCI movI = new MovimentacaoTCCI(LocalDateTime.now(), TipoMovimentacaoENUM.ENTREGA, obj.getString("comentario"), professor.getUsuario(),LocalDate.parse(obj.getString("proxima-entrega"),DateTimeFormatter.ofPattern("d/MM/yyyy")), tcci);
            tcci.getMovimentacoesTCC().add(movI);
            tcciDao.alterar(tcci);
            
        }else{
            tccii = tcciiDao.buscarPorTermo(termo);
            MovimentacaoTCCII movII = new MovimentacaoTCCII(LocalDateTime.now(), TipoMovimentacaoENUM.ENTREGA, obj.getString("comentario"),professor.getUsuario(),LocalDate.parse(obj.getString("proxima-entrega"),DateTimeFormatter.ofPattern("d/MM/yyyy")), tccii);
            tccii.getMovimentacoesTCC().add(movII);
            tcciDao.alterar(tccii);
        }
        return Response.ok().build();
    }
}
