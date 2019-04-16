package model.dao;

import java.sql.*;

/**
 *
 * @author Hércules M.
 */
public class Conexao {
    private static final String SERVIDOR = "db4free.net";
    private static final String USUARIO = "usr_opressor";
    private static final String SENHA = "NossaSenha17";
    private static final String BANCO = "db_sgsa";
    private static final String URL_CONEXAO = "jdbc:mysql://" + SERVIDOR + "/" + BANCO;
    private static Connection conn;
    
    public static Connection get() throws SQLException
    {
        if(conn == null)
            conn = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
        
        return conn;            
    }
}
