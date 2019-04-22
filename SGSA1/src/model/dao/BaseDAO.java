package model.dao;

import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author Hércules M.
 */
public abstract class BaseDAO {
    protected Connection conexao;
    protected String tabela;
    
    public BaseDAO()
    {
        try {
            conexao = Conexao.get();
            System.out.println("Conectado!");
        }catch(SQLException e) {
            System.out.println("Erro ao conectar-se: " + e.getMessage());
        }
    }
}
