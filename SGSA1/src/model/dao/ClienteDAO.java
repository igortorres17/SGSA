package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
import model.Mecanico;
        
/**
 *
 * @author Hércules M.
 */
public class ClienteDAO extends BaseDAO{
  
    public ClienteDAO(){
        super();
        tabela = "cliente";
    }
    
    private Cliente converterResultSetEmCliente(ResultSet resultSet) throws SQLException{
        int tipoCliente = resultSet.getInt("tipo");
        Cliente cliente;
        cliente = new Cliente(
        resultSet.getInt("id"),
        resultSet.getString("email"),
        resultSet.getString("telefone"),
        resultSet.getString("logradouro"),
        resultSet.getInt("numero"),
        resultSet.getString("complemento"),
        resultSet.getString("bairro"),
        resultSet.getString("municipio"),
        resultSet.getString("estado"),
        resultSet.getString("data_nascimento"));

        switch (tipoCliente) {
            case Cliente.PESSOA_FISICA:
                PessoaFisica pessoaFisica = new PessoaFisica(
                cliente, 
                resultSet.getString("nome_rzsocial"),
                resultSet.getString("cpf_cnpj"));
                return pessoaFisica;
            case Cliente.PESSOA_JURIDICA:
                PessoaJuridica pessoaJuridica = new PessoaJuridica(
                cliente,
                resultSet.getString("nome_rzsocial"),
                resultSet.getString("cpf_cnpj"));
                return pessoaJuridica;
            case Cliente.MECANICO:
                Mecanico mecanico = new Mecanico(
                cliente, 
                resultSet.getString("nome_rzsocial"),
                resultSet.getString("cpf_cnpj"),
                resultSet.getString("senha"));
                return mecanico;
            default:
                break;
        }
        
        return null;
    }
    public void inserir(Cliente cliente) throws SQLException{
        String insertSqlQuery = ""
        + "INSERT INTO "+tabela+" ("
        + "cpf_cnpj,"
        + "nome_rzsocial,"
        + "senha,"
        + "email,"
        + "telefone,"
        + "logradouro,"
        + "numero,"
        + "complemento,"
        + "bairro,"
        + "municipio,"
        + "estado,"
        + "data_nascimento,"
        + "tipo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(insertSqlQuery);
        
         if(!Cliente.eMecanico(cliente))
            instrucaoPreparada.setNull(3, Types.VARCHAR);
         
        instrucaoPreparada.setString(4, cliente.getEmail());
        instrucaoPreparada.setString(5, cliente.getTelefone());
        instrucaoPreparada.setString(6, cliente.getLogradouro());
        instrucaoPreparada.setInt(7, cliente.getNumero());
        instrucaoPreparada.setString(8, cliente.getComplemento());
        instrucaoPreparada.setString(9, cliente.getBairro());
        instrucaoPreparada.setString(10, cliente.getMunicipio());
        instrucaoPreparada.setString(11, cliente.getEstado());
        instrucaoPreparada.setString(12, cliente.getData_nascimento());
        
        if(Cliente.ePessoaFisica(cliente)){
            PessoaFisica pessoaFisica = (PessoaFisica)cliente;
            instrucaoPreparada.setString(1, pessoaFisica.getCpf());
            instrucaoPreparada.setString(2, pessoaFisica.getNome());
            instrucaoPreparada.setInt(13, Cliente.PESSOA_FISICA);
        }else if(Cliente.ePessoaJuridica(cliente)){
            PessoaJuridica pessoaJuridica = (PessoaJuridica)cliente;
            instrucaoPreparada.setString(1, pessoaJuridica.getCnpj());
            instrucaoPreparada.setString(2, pessoaJuridica.getRazaoSocial());
            instrucaoPreparada.setInt(13, Cliente.PESSOA_JURIDICA);
        }else if(Cliente.eMecanico(cliente)){
            Mecanico mecanico = (Mecanico)cliente;
            instrucaoPreparada.setString(1, mecanico.getCpf());
            instrucaoPreparada.setString(2, mecanico.getNome());
            instrucaoPreparada.setString(3, mecanico.getSenha());
            instrucaoPreparada.setInt(13, Cliente.MECANICO);
        }
        
        instrucaoPreparada.execute();
    }
    
    public Cliente buscar(int id) throws SQLException{
        String selectSqlQuery = "SELECT * FROM "+tabela+" WHERE id = ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(selectSqlQuery);
        instrucaoPreparada.setInt(1, id);
        ResultSet resultadoSelect = instrucaoPreparada.executeQuery();
        
        if(resultadoSelect.next()){
            return converterResultSetEmCliente(resultadoSelect);
        }
        
        return null;
    }
    
    public ArrayList<Cliente> buscar(String nome) throws SQLException
    {
        ArrayList<Cliente> listaClientes = new ArrayList();
        
        String selectSqlQuery = "SELECT * FROM "+tabela+" WHERE nome_rzsocial like ?";
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(selectSqlQuery);
        instrucaoPreparada.setString(1, "%"+nome+"%");
        ResultSet resultadoSelect = instrucaoPreparada.executeQuery();    
        
        while(resultadoSelect.next()){
            listaClientes.add(this.converterResultSetEmCliente(resultadoSelect));
        }
        
        return listaClientes;
    }
    
    public void alterar(Cliente cliente) throws SQLException {
            
        String updateSqlQuery = "UPDATE "+tabela+" SET "
        + "cpf_cnpj = ?,"
        + "nome_rzsocial = ?,"
        + "senha = ?,"
        + "email = ?,"
        + "telefone = ?,"
        + "logradouro = ?,"
        + "numero = ?,"
        + "complemento = ?,"
        + "bairro = ?,"
        + "municipio = ?,"
        + "estado = ?,"
        + "data_nascimento = ?,"
        + "tipo = ?"
        + " WHERE id = ?";

        PreparedStatement instrucaoPreparada = conexao.prepareStatement(updateSqlQuery);

        if(!Cliente.eMecanico(cliente))
            instrucaoPreparada.setNull(3, Types.VARCHAR);

        instrucaoPreparada.setString(4, cliente.getEmail());
        instrucaoPreparada.setString(5, cliente.getTelefone());
        instrucaoPreparada.setString(6, cliente.getLogradouro());
        instrucaoPreparada.setInt(7, cliente.getNumero());
        instrucaoPreparada.setString(8, cliente.getComplemento());
        instrucaoPreparada.setString(9, cliente.getBairro());
        instrucaoPreparada.setString(10, cliente.getMunicipio());
        instrucaoPreparada.setString(11, cliente.getEstado());
        instrucaoPreparada.setString(12, cliente.getData_nascimento());
        instrucaoPreparada.setInt(14, cliente.getId());

        if(Cliente.ePessoaFisica(cliente)){
            PessoaFisica pessoaFisica = (PessoaFisica)cliente;
            instrucaoPreparada.setString(1, pessoaFisica.getCpf());
            instrucaoPreparada.setString(2, pessoaFisica.getNome());
            instrucaoPreparada.setInt(13, Cliente.PESSOA_FISICA);
        }else if(Cliente.ePessoaJuridica(cliente)){
            PessoaJuridica pessoaJuridica = (PessoaJuridica)cliente;
            instrucaoPreparada.setString(1, pessoaJuridica.getCnpj());
            instrucaoPreparada.setString(2, pessoaJuridica.getRazaoSocial());
            instrucaoPreparada.setInt(13, Cliente.PESSOA_JURIDICA);
        }else if(Cliente.eMecanico(cliente)){
            Mecanico mecanico = (Mecanico)cliente;
            instrucaoPreparada.setString(1, mecanico.getCpf());
            instrucaoPreparada.setString(2, mecanico.getNome());
            instrucaoPreparada.setString(3, mecanico.getSenha());
            instrucaoPreparada.setInt(13, Cliente.MECANICO);
        }
        
        instrucaoPreparada.execute();
                
    }
    
    public void excluir(int id) throws SQLException {
        String deleteSqlQuery = "DELETE FROM "+tabela+" WHERE id = ?";
        
        PreparedStatement instrucaoPreparada = conexao.prepareStatement(deleteSqlQuery);
        instrucaoPreparada.setInt(1, id);
        instrucaoPreparada.execute();
    }
    
    public void excluir(Cliente cliente) throws SQLException{
        this.excluir(cliente.getId());
    }
}
