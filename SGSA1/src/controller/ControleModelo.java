/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Modelo;
import model.dao.ModeloDAO;
import view.TelaMensagens;

/**
 *
 * @author root
 */
public class ControleModelo extends JFrame {

    private ModeloDAO dao;
    private TelaMensagens mens;

    public ControleModelo() {
        dao = new ModeloDAO();
        mens = new TelaMensagens();
    }

    /**
     *
     * @param tipo
     * @param outros
     * @param nome
     * @param marca
     * @param qtdPortas
     * @param motor
     * @param combustivel
     */
    public int inserir(JComboBox tipo, JTextField outros, JTextField nome, JTextField marca, JComboBox qtdPortas, JTextField motor, JComboBox combustivel) {
        String tipoVar = null;
        String nomeVar = null;
        String marcaVar = null;
        int qtdPortasVar = 0;
        String motorVar = null;
        int combustivelVar = 0;

        if (tipo.getSelectedIndex() == 5) {

            if (outros.getText() != null) {
                tipoVar = outros.getText();
            } else {
                alerta(outros);
                outros.setToolTipText("Digite o tipo do veiculo.");
                return 0;
            }
        } else {
            switch (tipo.getSelectedIndex()) {
                case 1:
                    tipoVar = "CARRO DE PASSEIO";
                    break;
                case 2:
                    tipoVar = "VAN";
                    break;
                case 3:
                    tipoVar = "CAMINHAO";
                    break;
                case 4:
                    tipoVar = "CAMINHONETE";
                    break;
            }
        }
        if (nome.getText() != null) {
            nomeVar = nome.getText();
        } else {
            alerta(nome);
            nome.setToolTipText("Digite o nome do veículo.");
            return 0;
        }

        if (marca.getText() != null) {
            marcaVar = marca.getText();
        } else {
            alerta(marca);
            marca.setToolTipText("Digite a marca do veículo.");
            return 0;
        }

        switch (qtdPortas.getSelectedIndex()) {
            case 1:
                qtdPortasVar = 0;
                break;
            case 2:
                qtdPortasVar = 2;
                break;
            case 3:
                qtdPortasVar = 3;
                break;
            case 4:
                qtdPortasVar = 4;
                break;
            case 5:
                qtdPortasVar = 5;
                break;
        }
        if (motor.getText() != null) {
            motorVar = motor.getText();
        } else {
            alerta(motor);
            motor.setToolTipText("Digite o nome do motor do veículo");
            return 0;
        }

        combustivelVar = combustivel.getSelectedIndex();
        if(tipoVar.isEmpty() && nomeVar.isEmpty() && marcaVar.isEmpty() && motorVar.isEmpty()){
            JOptionPane.showMessageDialog(null, "ta dando certo");
        }
        else{
        try {
            dao.inserir(new Modelo(tipoVar, nomeVar, marcaVar, qtdPortasVar, motorVar, combustivelVar));
            mens.sucesso(1);
        } catch (SQLException ex) {
            mens.erroBanco(ex);
        }
        }
        return 0;
    }

    private void alerta(JTextField comp) {
        comp.requestFocus();
        comp.setBackground(Color.red);
    }

}
