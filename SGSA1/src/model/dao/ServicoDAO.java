package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Servico;
/**
 *
 * @author Hércules M.
 */
public class ServicoDAO extends BaseDAO{
    
    public ServicoDAO(){
       super();
       tabela = "servico";
    }
    
    private Servico converterResultSetEmServico(ResultSet resultSet) throws SQLException{
       Servico servico = new Servico(
       resultSet.getInt("id"),
       resultSet.getString("nome"),
       resultSet.getFloat("valor"));
       
       return servico;
    }
    
    public void inserir(Servico servico) throws SQLException{
        String sqlQuery = "INSERT INTO " + tabela + "(nome, valor) VALUES(?,?)";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setString(1, servico.getNome());
        instrucaoPreparada.setFloat(2, servico.getValor());
        instrucaoPreparada.execute();
    }
    
    public Servico buscar(int id) throws SQLException{
        String sqlQuery = "SELECT * FROM " + tabela + " WHERE id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setInt(1, id);
        ResultSet resultado = instrucaoPreparada.executeQuery();
        
        if(resultado.next())
            return converterResultSetEmServico(resultado);
        
        return null;
    }
    
    public ArrayList<Servico> buscar(String nome) throws SQLException{
        String sqlQuery = "SELECT * FROM " + tabela + " WHERE nome like ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setString(1, "%"+nome+"%");
        ResultSet resultado = instrucaoPreparada.executeQuery();
        
        ArrayList<Servico> servicos = new ArrayList();
        
        while(resultado.next())
            servicos.add(converterResultSetEmServico(resultado));
        
        return servicos;
    }
    
    public void alterar(Servico servico) throws SQLException{
        String sqlQuery = "UPDATE " + tabela + " SET nome = ?, valor = ? WHERE id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setString(1, servico.getNome());
        instrucaoPreparada.setFloat(2, servico.getValor());
        instrucaoPreparada.setInt(3, servico.getId());
        instrucaoPreparada.execute();
    }
    
    public void excluir(int id) throws SQLException{
       String sqlQuery = "DELETE FROM " + tabela + " WHERE id = ?"; 
       PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
       instrucaoPreparada.setInt(1, id);
       instrucaoPreparada.execute();
    }
    
    public void excluir(Servico servico) throws SQLException{
        this.excluir(servico.getId());
    }
}
