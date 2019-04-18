package model.dao;

import java.sql.*;

/**
 *
 * @author Hércules M.
 */
public class Conexao {
    private static final String SERVIDOR = "db4free.net";
    private static final String USUARIO = System.getenv("SGSA_DBUSER");
    private static final String SENHA = System.getenv("SGSA_DBPASS");
    private static final String BANCO = "db_sgsa";
    private static final String URL_CONEXAO = "jdbc:mysql://" + SERVIDOR + "/" + BANCO + "?useSSL=false";
    private static Connection conn;
    
    public static Connection get() throws SQLException
    {
        if(conn == null)
            conn = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
        
        return conn;            
    }
    
    public static void fechar() throws SQLException
    {
        if(conn != null){
            conn.close();
            conn = null;
        }
    }
}
