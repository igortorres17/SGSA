package model;

/**
 *
 * @author Hércules M.
 */
public class Sessao {
    private static PessoaFisica usuario;
    
    public static void setUsuario(PessoaFisica pessoa) throws Exception{
        if(pessoa == null)
            throw new Exception("Usuário é nulo");

        usuario = pessoa;
    }
    
    public static PessoaFisica getUsuario(){
        return usuario;
    }
    
    public void logoff(){
        usuario = null;
    }
    

}
