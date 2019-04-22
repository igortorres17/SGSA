package model;


/**
 *
 * @author Hércules M.
 */
public class PessoaFisica extends Cliente{
    private String nome;
    private String cpf;
        
    public PessoaFisica(){
    
    }
    
    public PessoaFisica(int id, String nome, String cpf, String email, String telefone, String logradouro, int numero, String complemento, String bairro, String municipio, String estado, String data_nascimento) {
        super(id, email, telefone, logradouro, numero, complemento, bairro, municipio, estado, data_nascimento);
        this.nome = nome;
        this.cpf = cpf;
    }
    
    public PessoaFisica(String nome, String cpf, String email, String telefone, String logradouro, int numero, String complemento, String bairro, String municipio, String estado, String data_nascimento) {
        super(email, telefone, logradouro, numero, complemento, bairro, municipio, estado, data_nascimento);
        this.nome = nome;
        this.cpf = cpf;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    
}
