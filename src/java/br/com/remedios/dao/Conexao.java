package br.com.remedios.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Início da classe de conexão//
public class Conexao {
    private static String URL = "jdbc:mysql://127.0.0.1:3306/db_appmeusremedios";
    private static String DRIVER  ="com.mysql.jdbc.Driver";
    private static String USER = "root";
    private static String SENHA = "";
    
    private static Connection  con;
    
    public static  Connection conecta() throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, USER, SENHA);
        return con;
    }
    
    public static void desconecta() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
