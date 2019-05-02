package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
           resultSet.getString("obs"),
           resultSet.getInt("status"),
           resultSet.getString("data")
        );
        
        return ordem;
    }
    
    public void inserir(OrdemServico ordemServico) throws SQLException, Exception{
        String sqlQuery = "INSERT INTO " + tabela + "(valor, veiculo_id, obs, status, data) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        instrucaoPreparada.setFloat(1, ordemServico.getValor());
        instrucaoPreparada.setInt(2, ordemServico.getVeiculo().getId());
        instrucaoPreparada.setString(3, ordemServico.getObservacao());
        instrucaoPreparada.setInt(4, ordemServico.getStatus());
        instrucaoPreparada.setString(5, ordemServico.getData());
        instrucaoPreparada.executeUpdate();
        ResultSet resultado = instrucaoPreparada.getGeneratedKeys();
        
        if(!resultado.next())
            throw new Exception("Falha ao obter último ID");
        
        int id = resultado.getInt(1);
        OrdemTemPecaDAO ordemTemPeca = new OrdemTemPecaDAO();
        OrdemTemServicoDAO ordemTemServico = new OrdemTemServicoDAO();
        ordemTemPeca.inserir(ordemServico.getPecas(), id);
        ordemTemServico.inserir(ordemServico.getServicos(), id);        
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
    
    public void cancelar(OrdemServico ordemServico) throws SQLException{
        String sqlQuery = "UPDATE " + tabela + " SET status = ? WHERE id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
        instrucaoPreparada.setInt(1, OrdemServico.CANCELADA);
        instrucaoPreparada.setInt(2, ordemServico.getId());
        instrucaoPreparada.execute();
    }
    
    public void excluir(int id) throws SQLException{
        String sqlQuery = "DELETE FROM " + tabela + " WHERE id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareCall(sqlQuery);
        instrucaoPreparada.setInt(1, id);
        instrucaoPreparada.execute();
    }
    
    public void excluir(OrdemServico ordemServico) throws SQLException{
        this.excluir(ordemServico.getId());
    }
}
