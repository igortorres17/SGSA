package model;

/**
 *
 * @author Hércules M.
 */
public class Veiculo {
    private int id;
    private String placa;
    private String chassi;
    private int ano;
    private int quilometragem;
    private Cliente proprietario;
    private Modelo modelo;

    public Veiculo(int id, String placa, String chassi, int ano, int quilometragem, Cliente proprietario, Modelo modelo) {
        this.id = id;
        this.placa = placa;
        this.chassi = chassi;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.proprietario = proprietario;
        this.modelo = modelo;
    }

    public Veiculo(String placa, String chassi, int ano, int quilometragem, Cliente proprietario, Modelo modelo) {
        this.placa = placa;
        this.chassi = chassi;
        this.ano = ano;
        this.quilometragem = quilometragem;
        this.proprietario = proprietario;
        this.modelo = modelo;
    }

    public int getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Cliente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
   
    
}
