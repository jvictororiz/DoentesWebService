package br.com.remedios.service;

import br.com.remedios.dao.UsuarioDAO;
import br.com.remedios.model.Usuario;
import br.com.remedios.utils.StatusCode;
import com.google.gson.Gson;
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
import javax.ws.rs.core.MediaType;

@Path("Usuario")
public class UsuarioWS {

    @Context
    private UriInfo context;

    public UsuarioWS() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGson(){
        return "Funcionando";
    }
    
    @GET
    @Path("get/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers(){
        Gson gson = new Gson();
        List<Usuario> usuarios = new ArrayList<>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String gsonString = gson.toJson(usuarioDAO.getUsuarios());
        return gsonString;
    }

    
    
    @POST
    @Path("/inserirUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public int cadastrarUsuario(String content){
        Gson gson = new Gson();
        Usuario usuario = (Usuario) gson.fromJson(content, Usuario.class);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.cadastrarUsuario(usuario);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deltarUsuario/{id}")
    public String deletarUsuario(@PathParam("id")int id) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int cod = usuarioDAO.deletaUsuario(id);
        if(cod == 0){
            return "Usuário deletado com sucesso !";
        }else{
            return "Erro ao deletar usuário, código: "+ cod;
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateUsuario")
    public int updateUsuario(String content){
        Gson gson = new Gson();
        Usuario usuario = (Usuario) gson.fromJson(content, Usuario.class);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.editarUsuario(usuario);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logar/{login}/{senha}")
    public String logarUsuario (@PathParam("login")String login,@PathParam("senha")String senha ) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = null;
        try {
           usuario = usuarioDAO.logar(login, senha);
        
        Gson gson = new Gson();
        if(usuario!= null){
            return gson.toJson(usuario);
        }
        } catch (SQLException ex) {
            return ex.getMessage();
        } catch (ClassNotFoundException ex) {
            return ex.getMessage();
        } catch (ServiceException ex) {
            Logger.getLogger(UsuarioWS.class.getName()).log(Level.SEVERE, null, ex);
            if(ex.getCodigoErro() == StatusCode.NENHUM_USUARIO_ENCONTRADO){
                return "Nenhum usuário encontrado";
            }            
        }
        return usuario.getNome();
    }
    

}
