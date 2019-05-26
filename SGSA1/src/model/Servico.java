package model;

/**
 *
 * @author igortorres
 */
public class Servico {
    private int id;
    private String nome;
    private float valor;
    
    public Servico(){
        
    }
    
    public Servico(int id, String nome, float valor){
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }
    
    public Servico(String nome, float valor){
        this.nome = nome;
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
