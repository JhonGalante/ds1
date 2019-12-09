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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarLogin(String res) throws NoSuchAlgorithmException{
        JSONObject obj = new JSONObject(res);
        Usuario usuario = dao.buscarMatricula(obj.getString("matricula"));
        JSONObject response = new JSONObject();
        if(usuario == null){
            response.put("token", "404");
            return Response.status(404).entity(response.toString()).build();
        }
        if(usuario.getSenha().compareTo(obj.getString("senha")) != 0){
            response.put("token", "501");
            return Response.status(501).entity(response.toString()).build();
        }
        if(usuario.getTipo() == TipoUsuarioENUM.ALUNO){
            response.put("token", "201");
            return Response.status(201).entity(response.toString()).build();
        }
        if(usuario.getTipo() == TipoUsuarioENUM.PROFESSOR){
            response.put("token", "202");
            return Response.status(202).entity(response.toString()).build();
        }
        response.put("token", "203");
        return Response.status(203).entity(response.toString()).build();
    }
    
}
