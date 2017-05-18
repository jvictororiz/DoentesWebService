package br.com.remedios.service;

import br.com.remedios.dao.RemediosDAO;
import br.com.remedios.dao.UsuarioDAO;
import br.com.remedios.model.Remedio;
import br.com.remedios.model.Usuario;
import br.com.remedios.utils.StatusCode;
import com.google.gson.Gson;
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
import javax.ws.rs.core.MediaType;

@Path("Remedio")
public class RemedioWS {

    @Context
    private UriInfo context;

    public RemedioWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGson() {
        return "Remedio";
    }

    @GET
    @Path("get/remedios/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRemediosDoUsuario(@PathParam("idUsuario") int idUsuario) throws SQLException, ClassNotFoundException, ServiceException {
        Gson gson = new Gson();
        List<Remedio> remedios = new ArrayList<>();
        RemediosDAO remediosDAO = new RemediosDAO();
        String gsonString = gson.toJson(remediosDAO.getRemedios(idUsuario));
        return gsonString;
    }

    @POST
    @Path("/insereRemedio")
    @Consumes(MediaType.APPLICATION_JSON)
    public void cadastrarUsuario(String content) throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, ServiceException, SQLException {
            Gson gson = new Gson();
            Remedio remedio = (Remedio) gson.fromJson(content, Remedio.class);
            RemediosDAO remedioDAO = new RemediosDAO();
            remedioDAO.insereRemedio(remedio);
        
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deletaRemedio/{id}")
    public void deletarUsuario(@PathParam("id") int id) throws SQLException, ClassNotFoundException, ServiceException {
        RemediosDAO remedioDAO = new RemediosDAO();
        remedioDAO.deletaRemedio(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/alterarRemedio")
    public void updateUsuario(String content) throws SQLException, ClassNotFoundException, ServiceException {
        Gson gson = new Gson();
        Remedio remedio = (Remedio) gson.fromJson(content, Remedio.class);
        RemediosDAO usuarioDAO = new RemediosDAO();
        usuarioDAO.alterarRemedio(remedio);
    }
}
