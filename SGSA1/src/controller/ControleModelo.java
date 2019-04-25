/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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

        if (tipo.getSelectedIndex() == 4) {

            if (!outros.getText().isEmpty()) {
                tipoVar = outros.getText().toUpperCase();
            } else {
                alerta(outros);
                JOptionPane.showMessageDialog(null, "Digite o tipo do veiculo.");
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
            JOptionPane.showMessageDialog(null, "Digite o nome do veículo.");
            return 0;
        }

        if (!marca.getText().isEmpty()) {
            marcaVar = marca.getText().toUpperCase();
        } else {
            alerta(marca);
            JOptionPane.showMessageDialog(null, "Digite a marca do veículo.");
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
            JOptionPane.showMessageDialog(null, "Digite o nome do motor do veículo");
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

    public void relatorio(JTable tabela) {
        ArrayList<Modelo> models = new ArrayList<>();

        try {
            models = dao.relatorio();
        } catch (Exception ex) {
            mens.erroBanco(ex);
        }
        DefaultTableModel confTab = (DefaultTableModel) tabela.getModel();

        String[] titulo = {"ID", "TIPO", "MARCA", "N.PORTAS", "MOTOR", "COMBUSTIVEL"};

        confTab.addColumn(titulo);

        for (int i = 0; i < models.size(); i++) {
            Object[] obj = {models.get(i).getId(), models.get(i).getTipo(), models.get(i).getMarca(), models.get(i).getQuantidade_portas(), models.get(i).getMotor(), models.get(i).getCombustivel()};
            confTab.addRow(obj);
        }

    }

    public void buscar(JTextField buscaid, JTextField buscanome, JTextField id, JTextField nome, JTextField tipo, JTextField marca, JTextField nPortas, JTextField motor, JTextField combustivel) {
        Modelo model = null;
        if (!buscaid.getText().isEmpty()) {
            int idvar = Integer.parseInt(buscaid.getText());
            try {
                model = dao.buscar(idvar);
            } catch (Exception ex) {
                mens.erroBanco(ex);
            }
        } else {
            if (!buscanome.getText().isEmpty()) {
                String nomeVar = buscanome.getText().toUpperCase();
                try {
                    model = dao.buscar(nomeVar);
                } catch (Exception ex) {
                    mens.erroBanco(ex);
                }
            }
        }
        if (model != null) {

            id.setText(String.valueOf(model.getId()));
            tipo.setText(model.getTipo());
            nome.setText(model.getNome());
            marca.setText(model.getMarca());
            nPortas.setText(String.valueOf(model.getQuantidade_portas()));
            motor.setText(model.getMotor());
            String combus = null;
            switch (model.getCombustivel()) {
                case 1:
                    combus = "GASOLINA";
                    break;
                case 2:
                    combus = "ALCOOL";
                    break;
                case 3:
                    combus = "DIESEL";
                    break;
                case 4:
                    combus = "GNV";
                    break;
            }
            combustivel.setText(combus);

        } else {
            mens.erro(3);
        }

    }

    public int alterar(int id, JComboBox tipo, JTextField outros, JTextField nome, JTextField marca, JComboBox qtdPortas, JTextField motor, JComboBox combustivel) {
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
                JOptionPane.showMessageDialog(null, "Digite o tipo do veiculo.");
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
            JOptionPane.showMessageDialog(null, "Digite o nome do veículo.");
            return 0;
        }

        if (!marca.getText().isEmpty()) {
            marcaVar = marca.getText().toUpperCase();
        } else {
            alerta(marca);
            JOptionPane.showMessageDialog(null, "Digite a marca do veículo.");
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
            JOptionPane.showMessageDialog(null, "Digite o nome do motor do veículo");
            return 0;
        }

        combustivelVar = combustivel.getSelectedIndex();

        try {
            dao.alterar(new Modelo(id, tipoVar, nomeVar, marcaVar, qtdPortasVar, motorVar, combustivelVar));
            mens.sucesso(2);
        } catch (Exception ex) {
            mens.erroBanco(ex);

        }

        return 0;
    }

    public void excluir(int id) {

        if (JOptionPane.showConfirmDialog(null, "Gostaria de excluir este cadastro?") == 1) {
            try {
                dao.excluir(id);
            } catch (Exception ex) {
                mens.erroBanco(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cadastro mantido.");
        }

    }

}
