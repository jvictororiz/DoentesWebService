package br.com.remedios.dao;

import br.com.remedios.model.Usuario;
import br.com.remedios.service.ServiceException;
import br.com.remedios.utils.CalendarUtils;
import br.com.remedios.utils.StatusCode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = null;
        sql = "SELECT * FROM  usuario ;";
        try {
            con = Conexao.conecta();

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setNome(rs.getString("nome"));
                usuario.setDataNascimento(rs.getDate("dataNascimento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTelefone(rs.getString("telefone"));
                usuarios.add(usuario);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.desconecta();
        }
        return usuarios;
    }

    public int cadastrarUsuario(Usuario usuario) {
        sql = "INSERT INTO usuario (dataNascimento, email, nome, senha, telefone) VALUES (?,?,?,?,?);";
        try {
            java.sql.Date dataSql = new java.sql.Date(usuario.getDataNascimento().getTime());
            con = Conexao.conecta();
            pst = con.prepareStatement(sql);
            pst.setDate(1, dataSql);
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getNome());
            pst.setString(4, usuario.getSenha());
            pst.setString(5, usuario.getTelefone());
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, "deu certo");
            pst.execute();
            return StatusCode.SUCESSO;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return StatusCode.ERRO_AO_CONECTAR_COM_O_BANCO;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return StatusCode.ERRO_DE_SQL;
        } finally {
            Conexao.desconecta();
        }
    }

    public int deletaUsuario(int id) {
        sql = "DELETE FROM usuario WHERE id = ?";
        try {
            con = Conexao.conecta();
            pst = con.prepareCall(sql);
            pst.setInt(1, id);
            pst.execute();
            return StatusCode.SUCESSO;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return StatusCode.ERRO_AO_CONECTAR_COM_O_BANCO;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return StatusCode.ERRO_DE_SQL;
        } finally {
            Conexao.desconecta();
        }

    }

    public int editarUsuario(Usuario usuario) {
        sql = "UPDATE usuario SET dataNascimento = ?, email = ?, nome = ?, senha = ?, telefone = ? WHERE id = ?;";
        try {
            java.sql.Date dataSql = new java.sql.Date(usuario.getDataNascimento().getTime());
            con = Conexao.conecta();
            pst = con.prepareCall(sql);
            pst.setDate(1, dataSql);
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getNome());
            pst.setString(4, usuario.getSenha());
            pst.setString(5, usuario.getTelefone());
            pst.setLong(6, usuario.getId());
            int rs = pst.executeUpdate();
            if (rs == 1) {
                return StatusCode.SUCESSO;
            } else {
                return StatusCode.ERRO_AO_EDITAR_USUARIO;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return StatusCode.ERRO_AO_CONECTAR_COM_O_BANCO;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return StatusCode.ERRO_DE_SQL;
        } finally {
            Conexao.desconecta();
        }

    }

    public Usuario logar(String login, String senha) throws SQLException, ClassNotFoundException, ServiceException {
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
            usuario.setDataNascimento(CalendarUtils.convertDate(rs.getDate("dataNascimento")));
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
