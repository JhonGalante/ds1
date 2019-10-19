/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import dao.UsuarioDAO;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
public class UsuarioRest {
    
    private UsuarioDAO dao = UsuarioDAO.getInstance();
    
    @GET
    @Path("helloworld")
    public String helloWorld(){
        Usuario usuario = dao.buscarMatricula("333");
        return usuario.getNome();
    }
}
