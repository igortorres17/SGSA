package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Peca;

/**
 *
 * @author Hércules M.
 */
public class PecaDAO extends BaseDAO {
   public PecaDAO(){
       super();
       tabela = "peca";
   }
   
   private Peca converterResultSetEmPeca(ResultSet resultSet) throws SQLException{
       Peca peca = new Peca(
       resultSet.getInt("id"),
       resultSet.getString("nome"),
       resultSet.getString("codigo"),
       resultSet.getFloat("valor"));
       
       return peca;
   }
   
   public void inserir(Peca peca) throws SQLException {
       String insertSqlQuery = "INSERT INTO "+tabela+"(nome, codigo, valor) VALUES(?,?,?)";
       PreparedStatement instrucaoPreparada = conexao.prepareStatement(insertSqlQuery);
       instrucaoPreparada.setString(1, peca.getNome());
       instrucaoPreparada.setString(2, peca.getSerial());
       instrucaoPreparada.setFloat(3, peca.getValor());
       instrucaoPreparada.execute();
   }
   
   public Peca buscar(int id) throws SQLException {
       String selectSqlQuery = "SELECT * FROM "+tabela+" WHERE id = ? AND apagado = 0";
       PreparedStatement instrucaoPreparada = conexao.prepareStatement(selectSqlQuery);
       instrucaoPreparada.setInt(1, id);
       ResultSet resultado = instrucaoPreparada.executeQuery();
       
       if(resultado.next())
           return converterResultSetEmPeca(resultado);
       
    return null;   
   }
   
   public ArrayList<Peca> buscar(String nome, int limite) throws SQLException {
       ArrayList<Peca> pecas = new ArrayList();
       
       String selectSqlQuery = "SELECT * FROM "+tabela+" WHERE nome like ? AND apagado = 0 ORDER BY id DESC LIMIT ? ";
       PreparedStatement instrucaoPreparada = conexao.prepareStatement(selectSqlQuery);
       instrucaoPreparada.setString(1, "%"+nome+"%");
       instrucaoPreparada.setInt(2, limite);
       ResultSet resultado = instrucaoPreparada.executeQuery();
       
       while(resultado.next())
           pecas.add(converterResultSetEmPeca(resultado));
       
       return pecas;
   }
   
   public void alterar(Peca peca) throws SQLException {
       String updateSqlQuery = "UPDATE "+tabela+" SET nome = ?, codigo = ?, valor = ? WHERE id = ?";
       PreparedStatement instrucaoPreparada = conexao.prepareStatement(updateSqlQuery);
       instrucaoPreparada.setString(1, peca.getNome());
       instrucaoPreparada.setString(2, peca.getSerial());
       instrucaoPreparada.setFloat(3, peca.getValor());
       instrucaoPreparada.setInt(4, peca.getId());
       instrucaoPreparada.execute();
   }
   
   public void excluir (int id) throws SQLException {
       String deleteSqlQuery = "UPDATE "+tabela+" SET apagado = 1 WHERE id = ?";
       PreparedStatement instrucaoPreparada = conexao.prepareStatement(deleteSqlQuery);
       instrucaoPreparada.setInt(1, id);
       instrucaoPreparada.execute();
   }
   
   public void excluir(Peca peca) throws SQLException {
       this.excluir(peca.getId());
   }
}
