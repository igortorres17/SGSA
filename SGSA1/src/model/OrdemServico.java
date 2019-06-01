package model;

import java.util.ArrayList;

/**
 *
 * @author Hércules M.
 */
public class OrdemServico {
    public static final int ABERTA = 0;
    public static final int CONCLUIDA = 1;
    public static final int CANCELADA = 3;
    private int id;
    private Veiculo veiculo;
    private float valor;
    private ArrayList<Servico> servicos;
    private ArrayList<Peca> pecas;
    private String observacao;
    private int status;
    private String data;
    
    public OrdemServico(int id, Veiculo veiculo, float valor, ArrayList<Servico> servicos, ArrayList<Peca> pecas, String observacao, int status, String data ) {
        this.id  = id;
        this.veiculo = veiculo;
        this.valor = valor;
        this.servicos = servicos;
        this.pecas = pecas;
        this.observacao = observacao;
        this.status = status;
        this.data = data;
    }
    
    public OrdemServico(Veiculo veiculo, float valor, ArrayList<Servico> servicos, ArrayList<Peca> pecas, String observacao, int status, String data ) {
        this.veiculo = veiculo;
        this.valor = valor;
        this.servicos = servicos;
        this.pecas = pecas;
        this.observacao = observacao;
        this.status = status;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public ArrayList<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(ArrayList<Servico> servicos) {
        this.servicos = servicos;
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(ArrayList<Peca> pecas) {
        this.pecas = pecas;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public String getVeiculoStr(){
        return veiculo.getModelo().getNome() + " (" + veiculo.getPlaca() + ") / " + veiculo.getNomeProprietario();
    }
    
}
