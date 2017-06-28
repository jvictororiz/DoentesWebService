package br.com.remedios.service;

import br.com.remedios.dao.UsuarioDAO;
import br.com.remedios.model.Usuario;
import br.com.remedios.utils.StatusCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("Usuario")
public class UsuarioWS {

    @Context
    private UriInfo context;

    public UsuarioWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGson() {
        return "Funcionando";
    }

    @GET
    @Path("get/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() throws SQLException, ClassNotFoundException, ServiceException {
        Gson gson = new Gson();
        List<Usuario> usuarios = new ArrayList<>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String gsonString = gson.toJson(usuarioDAO.getUsuarios());
        return gsonString;
    }
    
    @GET
    @Path("get/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") int id) throws SQLException, ClassNotFoundException, ServiceException {
        Gson gson = new Gson();
        Usuario usuario = new Usuario();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String gsonString = gson.toJson(usuarioDAO.getUser(id));
        return gsonString;
    }

    @POST
    @Path("/inserirUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public void cadastrarUsuario(String content) throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, ServiceException, SQLException {
            Gson gson = new Gson();
            Usuario usuario = (Usuario) gson.fromJson(content, Usuario.class);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.cadastrarUsuario(usuario);
        
    }

    @GET    
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deltarUsuario/{id}")
    public void deletarUsuario(@PathParam("id") int id) throws SQLException, ClassNotFoundException, ServiceException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.deletaUsuario(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterarUsuario")
    public void updateUsuario(String content) throws SQLException, ClassNotFoundException, ServiceException {
        Gson gson = new Gson();
        Usuario usuario = (Usuario) gson.fromJson(content, Usuario.class);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.editarUsuario(usuario);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/logar")
        public String logarUsuario(String content) throws SQLException, ClassNotFoundException, ServiceException {
        Gson gson = new Gson();  
        Usuario request = new Usuario();
        request = (Usuario) gson.fromJson(content, Usuario.class); 
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario = usuarioDAO.logar(request.getEmail(), request.getSenha()); 
        return gson.toJson(usuario);
    }

}
