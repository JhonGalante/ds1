/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import java.util.List;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Usuario;


/**
 *
 * @author jhona
 */
@Named
@Path("usuario")
@Produces(MediaType.APPLICATION_JSON)
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
    public String buscarPorId(@PathParam("matricula") String matricula){
        Usuario usuario = dao.buscarMatricula(matricula);
        if(usuario != null){
            String json = gson.toJson(usuario);
            return json;
        }
        return null;
    }
    
}
