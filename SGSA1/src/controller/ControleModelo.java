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
import org.w3c.dom.css.RGBColor;
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

        if (tipo.getSelectedIndex() == 4) {

            if (!outros.getText().isEmpty()) {
                tipoVar = outros.getText().toUpperCase();
            } else {
                alerta(outros);
                 JOptionPane.showMessageDialog(null,"Digite o tipo do veiculo.");
                return 0;
            }
        } else {
            switch (tipo.getSelectedIndex()) {
                case 0:
                    tipoVar = "CARRO DE PASSEIO";
                    break;
                case 1:
                    tipoVar = "VAN";
                    break;
                case 2:
                    tipoVar = "CAMINHAO";
                    break;
                case 3:
                    tipoVar = "CAMINHONETE";
                    break;
            }
        }


 
        if (!nome.getText().isEmpty()) {
            nomeVar = nome.getText().toUpperCase();
        } else {
            alerta(nome);
            JOptionPane.showMessageDialog(null,"Digite o nome do veículo.");
            return 0;
        }
        

        if (!marca.getText().isEmpty()) {
            marcaVar = marca.getText().toUpperCase();
        } else {
            alerta(marca);
            JOptionPane.showMessageDialog(null,"Digite a marca do veículo.");
            return 0;
        }
        
        switch (qtdPortas.getSelectedIndex()) {
            case 0:
                qtdPortasVar = 0;
                break;
            case 1:
                qtdPortasVar = 2;
                break;
            case 2:
                qtdPortasVar = 3;
                break;
            case 3:
                qtdPortasVar = 4;
                break;
            case 4:
                qtdPortasVar = 5;
                break;
        }

     
        if (!motor.getText().isEmpty()) {
            motorVar = motor.getText().toUpperCase();
        } else {
            alerta(motor);
            JOptionPane.showMessageDialog(null,"Digite o nome do motor do veículo");
            return 0;
        }
        
        
        combustivelVar = combustivel.getSelectedIndex();
        
        try {
            dao.inserir(new Modelo(tipoVar, nomeVar, marcaVar, qtdPortasVar, motorVar, combustivelVar));
            mens.sucesso(1);
        } catch (Exception ex) {
            mens.erroBanco(ex);
        }
        
        return 0;
    }

     
    private void alerta(JTextField comp) {
        comp.requestFocus();
        comp.setBackground(Color.RED);
    }

   
    


}