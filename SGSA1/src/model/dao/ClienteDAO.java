package model.dao;

import java.sql.PreparedStatement;
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
    
    private boolean ePessoaFisica(Cliente cliente){
        return ((cliente instanceof PessoaFisica) && !(cliente instanceof Mecanico));
    }
    
    private boolean ePessoaJuridica(Cliente cliente){
        return cliente instanceof PessoaJuridica;
    }
    
    private boolean eMecanico(Cliente cliente){
        return cliente instanceof Mecanico;
    }
    
    private boolean eCliente(Cliente cliente){
        return (!(cliente instanceof PessoaFisica) && !(cliente instanceof PessoaJuridica) && !(cliente instanceof Mecanico));
    }
    
    public void inserir(Cliente cliente) throws SQLException{
        String insertSqlQuery = ""
        + "INSERT INTO cliente ("
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
        
         if(!eMecanico(cliente))
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
        
        if(ePessoaFisica(cliente)){
            PessoaFisica pessoaFisica = (PessoaFisica)cliente;
            instrucaoSqlPreparada.setString(1, pessoaFisica.getCpf());
            instrucaoSqlPreparada.setString(2, pessoaFisica.getNome());
            instrucaoSqlPreparada.setInt(13, Cliente.PESSOA_FISICA);
        }else if(ePessoaJuridica(cliente)){
            PessoaJuridica pessoaJuridica = (PessoaJuridica)cliente;
            instrucaoSqlPreparada.setString(1, pessoaJuridica.getCnpj());
            instrucaoSqlPreparada.setString(2, pessoaJuridica.getRazaoSocial());
            instrucaoSqlPreparada.setInt(13, Cliente.PESSOA_JURIDICA);
        }else if(eMecanico(cliente)){
            Mecanico mecanico = (Mecanico)cliente;
            instrucaoSqlPreparada.setString(1, mecanico.getCpf());
            instrucaoSqlPreparada.setString(2, mecanico.getNome());
            instrucaoSqlPreparada.setString(3, mecanico.getSenha());
            instrucaoSqlPreparada.setInt(13, Cliente.MECANICO);
        }
        
        instrucaoSqlPreparada.execute();
    }
}
