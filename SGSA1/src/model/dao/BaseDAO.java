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
            Conexao.get();
        }catch(SQLException e) {
            
        }
    }
}
