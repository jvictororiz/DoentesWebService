package br.com.remedios.dao;

import br.com.remedios.model.Usuario;
import br.com.remedios.service.ServiceException;
import br.com.remedios.utils.CalendarUtils;
import br.com.remedios.utils.StatusCode;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    private PreparedStatement pst;
    private ResultSet rs;
    private Connection con;
    private String sql;

    public List<Usuario> getUsuarios() throws SQLException, ClassNotFoundException, ServiceException {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = null;
        sql = "SELECT * FROM  usuario ;";
        try {
            con = Conexao.conecta();

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setDataNascimento(rs.getString("dataNascimento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTelefone(rs.getString("telefone"));
                usuarios.add(usuario);
            }
        }finally {
            Conexao.desconecta();
        }
            return usuarios;
        }

    public Usuario getUser(int id) throws SQLException, ClassNotFoundException, ServiceException {
        Usuario usuario = null;
        sql = "SELECT * FROM  usuario where id = ? ;";
        try {
            usuario = new Usuario();
            con = Conexao.conecta();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setDataNascimento(rs.getString("dataNascimento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTelefone(rs.getString("telefone"));
            }
        }finally {
            Conexao.desconecta();
            return usuario;
        }
            
        }
    

    public void cadastrarUsuario(Usuario usuario) throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, ServiceException,SQLException {
        sql = "INSERT INTO usuario (dataNascimento, email, nome, senha, telefone) VALUES (?,?,?,?,?);";
        try {
            con = Conexao.conecta();
            pst = con.prepareStatement(sql);
            pst.setString(1, usuario.getDataNascimento());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getNome());
            pst.setString(4, usuario.getSenha());
            pst.setString(5, usuario.getTelefone());
            boolean naoSucesso = pst.execute();
            if (naoSucesso) {
                throw new ServiceException(StatusCode.ERRO_AO_CADASTRAR_USUARIO, "Erro ao cadastrar usuário!");
            }
        } catch(SQLException ex){
            if(ex.getMessage().contains("email_UNIQUE")){
                throw new ServiceException(StatusCode.EMAIL_JA_ESTA_SENDO_USADO, "Este email já está sendo usado,\n por favor\n escolha outro");
            }
        }finally {
            Conexao.desconecta();
        }
    }

    public void deletaUsuario(int id) throws SQLException, ClassNotFoundException, ServiceException {
        sql = "DELETE FROM usuario WHERE id = ?";
        try {
            con = Conexao.conecta();
            pst = con.prepareCall(sql);
            pst.setInt(1, id);
            boolean naoSucesso = pst.execute();
            if (naoSucesso) {
                throw new ServiceException(StatusCode.ERRO_AO_DELETAR_USUARIO, "Não foi possível deletar este usuário");
            }
        } finally {
            Conexao.desconecta();
        }

    }

    public void editarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException, ServiceException {
        sql = "UPDATE usuario SET dataNascimento = ?, email = ?, nome = ?, senha = ?, telefone = ? WHERE id = ?;";
        try {
            con = Conexao.conecta();
            pst = con.prepareCall(sql);
            pst.setString(1, usuario.getDataNascimento());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getNome());
            pst.setString(4, usuario.getSenha());
            pst.setString(5, usuario.getTelefone());
            pst.setLong(6, usuario.getId());
            int rs = pst.executeUpdate();
            if (rs != 1) {           
                throw new ServiceException(StatusCode.ERRO_AO_EDITAR_USUARIO, "Não foi possível editar este usuário");
            }
        } finally {
            Conexao.desconecta();
        }

    }

    public Usuario logar(String login, String senha) throws SQLException, ClassNotFoundException, ServiceException,MySQLIntegrityConstraintViolationException {
        Usuario usuario = null;
        sql = "SELECT * FROM  usuario where email = ? and senha = ? ;";
        con = Conexao.conecta();
        pst = con.prepareCall(sql);
        pst.setString(1, login);
        pst.setString(2, senha);

        rs = pst.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setEmail(rs.getString("email"));
            usuario.setNome(rs.getString("nome"));
            usuario.setDataNascimento((rs.getString("dataNascimento")));
            usuario.setEmail(rs.getString("email"));
            usuario.setId(rs.getLong("id"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setTelefone("telefone");

        } else {
            throw new ServiceException(StatusCode.NENHUM_USUARIO_ENCONTRADO, "Usuário não cadastrado!");
        }
        Conexao.desconecta();
        return usuario;
    }
}
