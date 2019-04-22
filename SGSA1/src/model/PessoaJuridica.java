package model;


/**
 *
 * @author Hércules M.
 */
public class PessoaJuridica extends Cliente{
    private String razaoSocial;
    private String cnpj;
    
    public PessoaJuridica(){}
    public PessoaJuridica(int id, String razaoSocial, String cnpj, String email, String telefone, String logradouro, int numero, String complemento, String bairro, String municipio, String estado, String data_nascimento) {
        super(id, email, telefone, logradouro, numero, complemento, bairro, municipio, estado, data_nascimento);
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    
}
