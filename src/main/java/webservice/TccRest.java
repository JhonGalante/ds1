package webservice;

import com.google.gson.Gson;
import dao.ProfessorDAO;
import dao.TCCIDAO;
import dao.TCCIIDAO;
import dao.UsuarioDAO;
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
import model.Professor;
import model.TCCI;
import model.TCCII;
import model.Usuario;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;


/**
 *
 * @author jhona
 */
@Named
@Path("tcc")

@Consumes(MediaType.APPLICATION_JSON)
public class TccRest{
    
    private final UsuarioDAO dao = UsuarioDAO.getInstance();
    private final ProfessorDAO profDao = ProfessorDAO.getInstance();
    private final TCCIDAO tcciDao = TCCIDAO.getInstance();
    private final TCCIIDAO tcciiDao = TCCIIDAO.getInstance();
    private final Gson gson = new Gson();
   
    @POST
    @Path("buscar-tcci-professor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String buscarTCCIProfessor(String res){
        JSONObject obj = new JSONObject(res);
        JSONArray arrayObj = new JSONArray();
        Professor professor = profDao.buscarMatricula(obj.getString("matricula"));
        
        for(TCCI tcci : tcciDao.buscarPorProfessor(professor)){
            JSONObject objTemp = new JSONObject();
            objTemp.put("id-termo", tcci.getTermoCompromisso().getId());
            objTemp.put("tema", tcci.getTermoCompromisso().getTema());
            objTemp.put("aluno", tcci.getTermoCompromisso().getAluno().getUsuario().getNome());
            objTemp.put("curso", tcci.getTermoCompromisso().getAluno().getCurso());
            arrayObj.put(objTemp);
        }
        return arrayObj.toString();
    } 
    
    @POST
    @Path("buscar-tccii-professor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String buscarTCCIIProfessor(String res){
        JSONObject obj = new JSONObject(res);
        JSONArray arrayObj = new JSONArray();
        Professor professor = profDao.buscarMatricula(obj.getString("matricula"));
        
        for(TCCII tccii : tcciiDao.buscarPorProfessor(professor)){
            JSONObject objTemp = new JSONObject();
            objTemp.put("id-termo", tccii.getTermoCompromisso().getId());
            objTemp.put("tema", tccii.getTermoCompromisso().getTema());
            objTemp.put("aluno", tccii.getTermoCompromisso().getAluno().getUsuario().getNome());
            objTemp.put("curso", tccii.getTermoCompromisso().getAluno().getCurso());
            arrayObj.put(objTemp);
        }
        return arrayObj.toString();
    } 
}
