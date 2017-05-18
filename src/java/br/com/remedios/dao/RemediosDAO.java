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

    public List<Remedio> getRemedios(int idUsuario) throws SQLException, ClassNotFoundException, ServiceException {
        List<Remedio> remedios = new ArrayList<>();
        Remedio remedio = null;
        Usuario usuario = null;
        sql = "SELECT * FROM  remedio where usuario_id = ?;";
        try {
            con = Conexao.conecta();
            pst = con.prepareStatement(sql);
            pst.setInt(1, idUsuario);
            rs = pst.executeQuery();

            while (rs.next()) {
                remedio = new Remedio();
                usuario = new Usuario();
                remedio.setId(rs.getInt("id"));
                remedio.setDataCadastro(CalendarUtils.convertDate(rs.getDate("dataCadastro")));
                remedio.setFrequencia(rs.getInt("frequencia"));
                remedio.setHoraIncial(rs.getString("horaIncial"));
                remedio.setIsNotificar(rs.getBoolean("isNotificar"));
                remedio.setIsNotificarCompanheiro(rs.getBoolean("isNotificarCompanheiro"));
                remedio.setNome(rs.getString("nome"));
                remedio.setObservacao(rs.getString("observacao"));
                remedio.setIdUser(idUsuario);
                remedios.add(remedio);
            }
        } finally {
            Conexao.desconecta();
        }
        return remedios;
    }

    public void insereRemedio(Remedio remedio) throws ClassNotFoundException, ServiceException, SQLException {
        sql = "INSERT INTO remedio (dataCadastro, frequencia, horaInicial, isNotificar, isNotificarCompanheiro, nome, observacao ,tarja, usuario_id) VALUES (?,?,?,?,?,?,?,?,?);";
        try {
            java.sql.Date dataSql = new java.sql.Date(remedio.getDataCadastro().getTime());
            con = Conexao.conecta();
            pst = con.prepareStatement(sql);
            pst.setDate(1, dataSql);
            pst.setInt(2, remedio.getFrequencia());
            pst.setString(3, remedio.getHoraIncial());
            pst.setBoolean(4, remedio.isIsNotificar());
            pst.setBoolean(5, remedio.isIsNotificarCompanheiro());
            pst.setString(6, remedio.getNome());
            pst.setString(7, remedio.getObservacao());
            pst.setString(8, remedio.getTarja().name());
            pst.setInt(9, remedio.getIdUser());
            
            boolean naoSucesso = pst.execute();
            if (naoSucesso) {
                throw new ServiceException(StatusCode.ERRO_AO_CADASTRAR_REMEDIO, "Erro ao cadastrar o remédio!");
            }
        }  finally {
            Conexao.desconecta();
        }
    }

    public void deletaRemedio(int id) throws SQLException, ClassNotFoundException, ServiceException {
        sql = "DELETE FROM remedio WHERE id = ?";
        try {
            con = Conexao.conecta();
            pst = con.prepareCall(sql);
            pst.setInt(1, id);
            boolean naoSucesso = pst.execute();
            if (naoSucesso) {
                throw new ServiceException(StatusCode.ERRO_AO_DELETAR_REMEDIO, "Não foi possível deletar este remédio");
            }
        } finally {
            Conexao.desconecta();
        }

    }

    public void alterarRemedio(Remedio remedio) throws SQLException, ClassNotFoundException, ServiceException {
        sql = "UPDATE remedio SET dataCadastro = ?, frequencia = ? ,horaInicial = ? ,isNotificar = ? ,isNotificarCompanheiro = ?, nome = ? ,observacao = ?,tarja = ? , usuario_id = ? WHERE id = ?;";
        try {
            java.sql.Date dataSql = new java.sql.Date(remedio.getDataCadastro().getTime());
            con = Conexao.conecta();
            pst = con.prepareCall(sql);
            pst.setDate(1, dataSql);
            pst.setInt(2, remedio.getFrequencia());
            pst.setString(3, remedio.getHoraIncial());
            pst.setBoolean(4, remedio.isIsNotificar());
            pst.setBoolean(5, remedio.isIsNotificarCompanheiro());
            pst.setString(5, remedio.getNome());
            pst.setString(5, remedio.getObservacao());
            pst.setString(5, remedio.getTarja().name());
            pst.setInt(5, remedio.getIdUser());
            int rs = pst.executeUpdate();
            if (rs != 1) {
                throw new ServiceException(StatusCode.ERRO_AO_EDITAR_REMEDIO, "Não foi possível editar este rémedio");
            }
        } finally {
            Conexao.desconecta();
        }

    }
}
