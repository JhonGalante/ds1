package webservice;

import com.google.gson.Gson;
import dao.ProfessorDAO;
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
@Path("professor")

@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorRest{
    
    private final ProfessorDAO dao = ProfessorDAO.getInstance();
    private final Gson gson = new Gson();
    
    @POST
    @Path("buscar-orinteadores-disponiveis")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarPorId() throws Exception{
        return gson.toJson(dao.listarProfessorDisponiveis());
    }
    
}
