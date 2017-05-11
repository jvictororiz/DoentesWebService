package br.com.remedios.dao;

import br.com.remedios.model.Remedio;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RemediosDAO {

    private PreparedStatement pst;
    private ResultSet rs;
    private Connection con;
    private String sql;

    public List<Remedio> getRemedios() throws SQLException, ClassNotFoundException, ServiceException {
        List<Remedio> remedios = new ArrayList<>();
        Remedio remedio = null;
        Usuario usuario = null;
        sql = "SELECT * FROM  remedio ;";
        try {
            con = Conexao.conecta();

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                remedio = new Remedio();
                usuario = new Usuario();
                remedio.setId(rs.getInt("id"));
                remedio.setDataCadastro(CalendarUtils.convertDate(rs.getDate("dataCadastro")));
                remedio.setFrequencia(rs.getInt("frequencia"));
                remedio.setHoraIncial(rs.getDate("horaIncial"));
                remedio.setNotificar(rs.getBoolean("isNotificar"));
                remedio.setNotificarCompanheiro(rs.getBoolean("isNotificarCompanheiro"));
                remedio.setNome(rs.getString("nome"));
                remedio.setObservacao(rs.getString("observacao"));
                int id = rs.getInt("usuario_id");
                usuario = getUser(id);
                remedio.setUsuario(usuario);
                remedios.add(remedio);
            }
        }finally {
            Conexao.desconecta();
        }
            return remedios;
        }
    
    private Usuario getUser (int id) throws SQLException, ClassNotFoundException, ServiceException{
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.getUser(id);
    }

    

    public void cadastrarUsuario(Usuario usuario) throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, ServiceException,SQLException {
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
            Logger.getLogger(RemediosDAO.class.getName()).log(Level.SEVERE, null, "deu certo");
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
            if (rs != 1) {           
                throw new ServiceException(StatusCode.ERRO_AO_EDITAR_USUARIO, "Não foi possível editar este usuário");
            }
        } finally {
            Conexao.desconecta();
        }

    }
}
