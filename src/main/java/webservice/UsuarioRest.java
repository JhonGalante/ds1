package webservice;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import helper.HashHelper;
import java.security.NoSuchAlgorithmException;
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
import model.TipoUsuarioENUM;
import model.Usuario;
import org.primefaces.json.JSONObject;


/**
 *
 * @author jhona
 */
@Named
@Path("usuario")

@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioRest{
    
    private final UsuarioDAO dao = UsuarioDAO.getInstance();
    private final Gson gson = new Gson();
    
    @GET
    @Path("helloworld")
    public String helloWorld(){
        Usuario usuario = dao.buscarMatricula("333");
        return usuario.getNome();
    }
    
    @GET
    @Path("buscar-por-id/{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarPorId(@PathParam("matricula") String matricula){
        Usuario usuario = dao.buscarMatricula(matricula);
        if(usuario != null){
            String json = gson.toJson(usuario);
            return json;
        }
        return null;
    }
    
    @POST
    @Path("validar-login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public String validarLogin(@FormParam("matricula") String matricula, 
                                @FormParam("senha") String senha) throws NoSuchAlgorithmException{
        Usuario usuario = dao.buscarMatricula(matricula);
        JSONObject response = new JSONObject();
        if(usuario == null){
            response.put("token", "404");
        }
        if(usuario.getSenha().compareTo(senha) != 0){
            response.put("token", "501");
        }
        if(usuario.getTipo() == TipoUsuarioENUM.ALUNO){
            response.put("token", "201");
        }
        if(usuario.getTipo() == TipoUsuarioENUM.PROFESSOR){
            response.put("token", "202");
        }
        return response.toString();
    }
    
}
