package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        
        PreparedStatement instrucaoSqlPreparada = conexao.prepareStatement(insertSqlQuery);
        
         if(!Cliente.eMecanico(cliente))
            instrucaoSqlPreparada.setString(3, "");
         
        instrucaoSqlPreparada.setString(4, cliente.getEmail());
        instrucaoSqlPreparada.setString(5, cliente.getTelefone());
        instrucaoSqlPreparada.setString(6, cliente.getLogradouro());
        instrucaoSqlPreparada.setInt(7, cliente.getNumero());
        instrucaoSqlPreparada.setString(8, cliente.getComplemento());
        instrucaoSqlPreparada.setString(9, cliente.getBairro());
        instrucaoSqlPreparada.setString(10, cliente.getMunicipio());
        instrucaoSqlPreparada.setString(11, cliente.getEstado());
        instrucaoSqlPreparada.setString(12, cliente.getData_nascimento());
        
        if(Cliente.ePessoaFisica(cliente)){
            PessoaFisica pessoaFisica = (PessoaFisica)cliente;
            instrucaoSqlPreparada.setString(1, pessoaFisica.getCpf());
            instrucaoSqlPreparada.setString(2, pessoaFisica.getNome());
            instrucaoSqlPreparada.setInt(13, Cliente.PESSOA_FISICA);
        }else if(Cliente.ePessoaJuridica(cliente)){
            PessoaJuridica pessoaJuridica = (PessoaJuridica)cliente;
            instrucaoSqlPreparada.setString(1, pessoaJuridica.getCnpj());
            instrucaoSqlPreparada.setString(2, pessoaJuridica.getRazaoSocial());
            instrucaoSqlPreparada.setInt(13, Cliente.PESSOA_JURIDICA);
        }else if(Cliente.eMecanico(cliente)){
            Mecanico mecanico = (Mecanico)cliente;
            instrucaoSqlPreparada.setString(1, mecanico.getCpf());
            instrucaoSqlPreparada.setString(2, mecanico.getNome());
            instrucaoSqlPreparada.setString(3, mecanico.getSenha());
            instrucaoSqlPreparada.setInt(13, Cliente.MECANICO);
        }
        
        instrucaoSqlPreparada.execute();
    }
    
    public Cliente buscar(int id) throws SQLException{
        String selectSqlQuery = "SELECT * FROM "+tabela+" WHERE id = ?";
        PreparedStatement instrucaoSqlPreparada = conexao.prepareStatement(selectSqlQuery);
        instrucaoSqlPreparada.setInt(1, id);
        ResultSet resultadoSelect = instrucaoSqlPreparada.executeQuery();
        
        Cliente cliente;
        
        if(resultadoSelect.next()){
            int tipoCliente = resultadoSelect.getInt("tipo");
            
            cliente = new Cliente(
            resultadoSelect.getInt("id"),
            resultadoSelect.getString("email"),
            resultadoSelect.getString("telefone"),
            resultadoSelect.getString("logradouro"),
            resultadoSelect.getInt("numero"),
            resultadoSelect.getString("complemento"),
            resultadoSelect.getString("bairro"),
            resultadoSelect.getString("municipio"),
            resultadoSelect.getString("estado"),
            resultadoSelect.getString("data_nascimento"));
            
            switch (tipoCliente) {
                case Cliente.PESSOA_FISICA:
                    PessoaFisica pessoaFisica = new PessoaFisica(
                    cliente, 
                    resultadoSelect.getString("nome_rzsocial"),
                    resultadoSelect.getString("cpf_cnpj"));
                    return pessoaFisica;
                case Cliente.PESSOA_JURIDICA:
                    PessoaJuridica pessoaJuridica = new PessoaJuridica(
                    cliente,
                    resultadoSelect.getString("nome_rzsocial"),
                    resultadoSelect.getString("cpf_cnpj"));
                    return pessoaJuridica;
                case Cliente.MECANICO:
                    Mecanico mecanico = new Mecanico(
                    cliente, 
                    resultadoSelect.getString("nome_rzsocial"),
                    resultadoSelect.getString("cpf_cnpj"),
                    resultadoSelect.getString("senha"));
                    return mecanico;
                default:
                    break;
            }
        }
        
        return null;
    }
}
