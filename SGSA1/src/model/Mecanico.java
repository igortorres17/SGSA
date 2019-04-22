package model;

import java.util.Date;

/**
 *
 * @author Hércules M.
 */
public class Mecanico extends PessoaFisica{
    private String senha;
    
    public Mecanico(){
    
    }
    
    public Mecanico(int id, String nome, String cpf, String senha, String email, String telefone, String logradouro, int numero, String complemento, String bairro, String municipio, String estado, String data_nascimento) {
        super(id, nome, cpf, email, telefone, logradouro, numero, complemento, bairro, municipio, estado, data_nascimento);
        this.senha = senha;
    }
    
    public Mecanico(String nome, String cpf, String senha, String email, String telefone, String logradouro, int numero, String complemento, String bairro, String municipio, String estado, String data_nascimento) {
        super(nome, cpf, email, telefone, logradouro, numero, complemento, bairro, municipio, estado, data_nascimento);
        this.senha = senha;
    }
    
    public Mecanico(Cliente cliente, String nome, String cpf, String senha){
        super(cliente, nome, cpf);
        this.senha = senha;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
