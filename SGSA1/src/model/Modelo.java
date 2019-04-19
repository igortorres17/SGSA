package model;

/**
 *
 * @author Hércules M.
 */
public class Modelo {
    
    private int id;
    private String tipo;
    private String nome;
    private String marca;
    private int quantidade_portas;
    private String motor;
    private int combustivel;

    public Modelo(int id, String tipo, String nome, String marca, int quantidade_portas, String motor, int combustivel) {
        this.id = id;
        this.tipo=tipo;
        this.nome = nome;
        this.marca = marca;
        this.quantidade_portas = quantidade_portas;
        this.motor = motor;
        this.combustivel = combustivel;
        
    }

    public Modelo(String tipo, String nome, String marca, int quantidade_portas, String motor, int combustivel) {
        this.tipo=tipo;
        this.nome = nome;
        this.marca = marca;
        this.quantidade_portas = quantidade_portas;
        this.motor = motor;
        this.combustivel = combustivel;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setQuantidade_portas(int quantidade_portas) {
        this.quantidade_portas = quantidade_portas;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public int getQuantidade_portas() {
        return quantidade_portas;
    }

    public String getMotor() {
        return motor;
    }

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }

    
}
