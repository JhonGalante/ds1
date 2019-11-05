package webservice;

import com.google.gson.Gson;
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
import model.Usuario;


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
    public Response validarLogin(@FormParam("matricula") String matricula, 
                                @FormParam("senha") String senha){
        Usuario usuario = dao.buscarMatricula(matricula);
        if(usuario == null){
            return Response.status(404)
                    .entity("Usuario não encontrado")
                    .build();
        }
        if(usuario.getSenha().compareTo(senha) != 0){
            return Response.status(501)
                    .entity("Senha incorreta")
                    .build();
        }
        return Response.status(200)
                    .entity("Usuario logado com sucesso!")
                    .build();
    }
    
}
