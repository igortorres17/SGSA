package model;

import java.util.ArrayList;

/**
 *
 * @author Hércules M.
 */
public class OrdemServico {
    private int id;
    private Veiculo veiculo;
    private float valor;
    private ArrayList<Servico> servicos;
    private ArrayList<Peca> pecas;
    private String observacao;
    
    public OrdemServico(int id, Veiculo veiculo, float valor, ArrayList<Servico> servicos, ArrayList<Peca> pecas, String observacao ) {
        this.id  = id;
        this.veiculo = veiculo;
        this.valor = valor;
        this.servicos = servicos;
        this.pecas = pecas;
        this.observacao = observacao;
    }
    
    public OrdemServico(Veiculo veiculo, float valor, ArrayList<Servico> servicos, ArrayList<Peca> pecas ) {
        this.veiculo = veiculo;
        this.valor = valor;
        this.servicos = servicos;
        this.pecas = pecas;
        this.observacao = observacao;
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
    
    
}
