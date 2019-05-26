package model;

/**
 *
 * @author Hércules M.
 */
public class Peca {
    private int id;
    private String nome;
    private String serial;
    private float valor;
    
    public Peca()
    {
        
    }
    
    public Peca(int id, String nome, String serial, float valor){
        this.id = id;
        this.nome = nome;
        this.serial = serial;
        this.valor = valor;
    }
    
    public Peca(String nome, String serial, float valor){
        this.nome = nome;
        this.serial = serial;
        this.valor = valor;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    
    
    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString(){
        return this.nome + " - R$" + this.valor;
    }
    
    
}
