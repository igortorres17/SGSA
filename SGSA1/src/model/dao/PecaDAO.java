package model.dao;

import java.sql.PreparedStatement;
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
   
   public void inserir(Peca peca) throws SQLException {
       String insertSqlQuery = "INSERT INTO "+tabela+"(nome, codigo, valor) VALUES(?,?,?)";
       PreparedStatement instrucaoPreparada = conexao.prepareStatement(insertSqlQuery);
       instrucaoPreparada.setString(1, peca.getNome());
       instrucaoPreparada.setString(2, peca.getSerial());
       instrucaoPreparada.setFloat(3, peca.getValor());
       instrucaoPreparada.execute();
   }
   
   public Peca buscar(int id) {
    return null;   
   }
   
   public ArrayList<Peca> buscar(String nome) {
       return null;
   }
   
   public void alterar(Peca peca) {
       
   }
   
   public void excluir (int id) {
       
   }
   
   public void excluir(Peca peca) {
       this.excluir(peca.getId());
   }
}
