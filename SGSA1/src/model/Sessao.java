package model;

/**
 *
 * @author Hércules M.
 */
public class Sessao {
    private Mecanico usuario;
    
    public Sessao(Mecanico usuario) throws Exception{
        if(usuario == null)
            throw new Exception("Usuário é nulo");

        this.usuario = usuario;
    }
    
    public void logoff(){
        this.usuario = null;
    }
    
    public Mecanico getUsuario(){
        return this.usuario;
    }
}
