package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Cliente;
import model.Mecanico;
import model.Modelo;
import model.Peca;
import model.Servico;
import model.Veiculo;
import model.OrdemServico;
import model.PessoaFisica;
import model.PessoaJuridica;
import org.json.*;

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
        
        if(ordemServico.getServicos() != null && !ordemServico.getServicos().isEmpty()){            
            OrdemTemServicoDAO ordemTemServico = new OrdemTemServicoDAO();
            ordemTemServico.inserir(ordemServico.getServicos(), id);
        }
        
        if(ordemServico.getPecas() != null && !ordemServico.getPecas().isEmpty()){ 
            OrdemTemPecaDAO ordemTemPeca = new OrdemTemPecaDAO();
            ordemTemPeca.inserir(ordemServico.getPecas(), id);
        }
                
    }
        
    public ArrayList<OrdemServico> buscar(String placa, int limite) throws SQLException, Exception{
        ArrayList<OrdemServico> ordens = new ArrayList();
        ArrayList<Veiculo> veiculos = new VeiculoDAO().buscar(placa, limite);
        if(veiculos == null)
            throw new Exception("Placa não encontrada");
        
        String sqlQuery = "SELECT * FROM " + tabela + " WHERE veiculo_id = ?";
        
        for(int i = 0; i < veiculos.size(); i++){
            PreparedStatement instrucaoPreparada = conexao.prepareStatement(sqlQuery);
            instrucaoPreparada.setInt(1, veiculos.get(i).getId());
            ResultSet resultado = instrucaoPreparada.executeQuery();
            while(resultado.next())
                ordens.add(converterResultSetEmOrdemServico(resultado, veiculos.get(i)));
        }

        
        return ordens;
    }
    
        public ArrayList<OrdemServico> bucar_em_high_speed(String placa) throws SQLException{
        String sql = "CALL obter_os_json(?)";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(sql);
        instrucaoPreparada.setString(1, placa);
        ResultSet resultado = instrucaoPreparada.executeQuery();
        int i = 0;
        ArrayList<OrdemServico> ordens = new ArrayList();
        while(resultado.next()){
            JSONObject json_veiculo = new JSONObject(resultado.getString("veiculo_json"));
            JSONObject json_servicos = new JSONObject(resultado.getString("servicos_json"));            
            JSONObject json_pecas = new JSONObject(resultado.getString("pecas_json"));
            JSONArray servicos_array = json_servicos.getJSONArray("servicos");
            JSONArray pecas_array = json_pecas.getJSONArray("pecas");
            
            // Parse proprietário
            JSONObject json_cliente = json_veiculo.getJSONObject("cliente");
            Cliente cliente = new Cliente(
                json_cliente.getInt("id"), 
                json_cliente.getString("email"), 
                json_cliente.getString("telefone"), 
                json_cliente.getString("logradouro"), 
                json_cliente.getInt("numero"), 
                json_cliente.getString("complemento"), 
                json_cliente.getString("bairro"), 
                json_cliente.getString("municipio"), 
                json_cliente.getString("estado"), 
                json_cliente.getString("data_nascimento")
            );
            int tipo = json_cliente.getInt("tipo");
            
            if(tipo == Cliente.MECANICO){
                cliente = new Mecanico(
                        cliente, 
                        json_cliente.getString("nome_rzsocial"), 
                        json_cliente.getString("cpf_cnpj"),
                        json_cliente.getString("senha")
                );
            }else if(tipo == Cliente.PESSOA_FISICA || tipo == Cliente.MECANICO){
                cliente = new PessoaFisica(
                        cliente, 
                        json_cliente.getString("nome_rzsocial"), 
                        json_cliente.getString("cpf_cnpj")
                );
            }else if(tipo == Cliente.PESSOA_JURIDICA){
                cliente = new PessoaJuridica(
                        cliente,
                        json_cliente.getString("nome_rzsocial"),
                        json_cliente.getString("cpf_cnpj")
                );
            }
            
            // Parse modelo
            JSONObject modelo_json = json_veiculo.getJSONObject("modelo");
            Modelo modelo = new Modelo(
                    modelo_json.getInt("id"), 
                    modelo_json.getString("tipo"), 
                    modelo_json.getString("nome"), 
                    modelo_json.getString("marca"), 
                    modelo_json.getInt("quantidade_portas"), 
                    modelo_json.getString("motor"), 
                    modelo_json.getInt("combustivel")
            );
            
            // Parse veículo
            Veiculo veiculo = new Veiculo(
                    json_veiculo.getInt("id"),
                    json_veiculo.getString("placa"),
                    json_veiculo.getString("chassi"),
                    json_veiculo.getInt("ano"),
                    json_veiculo.getInt("quilometragem"),
                    cliente,
                    modelo
            );
            
            ArrayList<Servico> servicos = new ArrayList();            
            for(int j = 0; j < servicos_array.length(); j++){
                JSONObject servico_object = (JSONObject) servicos_array.get(i);
                Servico servico = new Servico(
                       servico_object.getInt("id"),
                       servico_object.getString("nome"),
                       servico_object.getFloat("valor")
                );
                servicos.add(servico);
            }
            
            ArrayList<Peca> pecas = new ArrayList();
            for(int j = 0; j < pecas_array.length(); j++){
                JSONObject peca_object = (JSONObject) pecas_array.get(i);
                Peca peca = new Peca(
                       peca_object.getInt("id"),
                       peca_object.getString("nome"),
                       peca_object.getString("codigo"),
                       peca_object.getFloat("valor")
                );
                pecas.add(peca);
            }
            
            OrdemServico os = new OrdemServico(
                    resultado.getInt("id"), 
                    veiculo, 
                    resultado.getFloat("valor"), 
                    servicos, 
                    pecas, 
                    resultado.getString("obs"), 
                    resultado.getInt("status"), 
                    resultado.getString("data")
            );
            
            ordens.add(os);
        }
        
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
