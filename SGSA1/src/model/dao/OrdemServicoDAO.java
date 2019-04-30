package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Peca;
import model.Servico;
import model.Veiculo;
import model.OrdemServico;


/**
 *
 * @author Hércules M.
 */
public class OrdemServicoDAO extends BaseDAO{
    
    public OrdemServicoDAO(){
        super();
        tabela = "ordem_servico";
    }
    
    private OrdemServico converterResultSetEmOrdemServico(ResultSet resultSet, Veiculo veiculo) throws SQLException{
        ArrayList<Peca> pecas = new OrdemTemPecaDAO().buscar(resultSet.getInt("id"));
        ArrayList<Servico> servicos = new OrdemTemServicoDAO().buscar(resultSet.getInt("id"));
        OrdemServico ordem = new OrdemServico(
           resultSet.getInt("id"),
           veiculo,
           resultSet.getFloat("valor"),
           servicos,
           pecas,
           resultSet.getString("obs")
        );
        
        return ordem;
    }
    
    public void inserir(OrdemServico ordemServico){
        
    }
    
    public ArrayList<OrdemServico> buscar(String placa) throws SQLException, Exception{
        Veiculo veiculo = new VeiculoDAO().buscar(placa);
        if(veiculo == null)
            throw new Exception("Placa não encontrada");
        
        String sqlQuery = "SELECT * FROM " + tabela + " WHERE veiculo_id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setInt(1, veiculo.getId());
        
        ResultSet resultado = instrucaoPreparada.executeQuery();
        ArrayList<OrdemServico> ordens = new ArrayList();
        
        while(resultado.next())
            ordens.add(converterResultSetEmOrdemServico(resultado, veiculo));
        
        return ordens;
    }
    
    public void alterar(OrdemServico ordemServico){
        
    }
    
    public void excluir(){
        
    }
}
