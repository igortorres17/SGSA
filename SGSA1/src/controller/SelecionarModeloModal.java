/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Modelo;
import model.dao.ModeloDAO;

/**
 * FXML Controller class
 *
 * @author root
 */
public class SelecionarModeloModal extends ControleBase implements Initializable {

    @FXML
    private TabPane abas;
    @FXML
    private TableView tabelaModelo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnVisualizar;
    @FXML
    private Button btnSelecionar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtMarca;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TextField txtMotor;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtQtdportas;
    @FXML
    private ComboBox cboxCombustivel;
    @FXML
    private TextField txtEditMarca;
    @FXML
    private TextField txtEditMotor;
    @FXML
    private TextField txtEditQtdPortas;
    @FXML
    private TextField txtEditTipo;
    @FXML
    private TextField txtEditNome;
    @FXML
    private Button btnEditSalvar;
    @FXML
    private Button btnEditCancelar;
    @FXML
    private ComboBox cbEditCombustivel;
    @FXML
    private Button btnViewVoltar;
    @FXML
    private Button btnViewModificar;
    @FXML
    private Button btnViewImprimir;
    @FXML
    private Label viewTipo;
    @FXML
    private Label viewMotor;
    @FXML
    private Label viewPortas;
    @FXML
    private Label viewNome;
    @FXML
    private Label viewMarca;
    @FXML
    private Label viewCombustível;
    @FXML
    private TextField txtEditId;
    @FXML
    private Button btnExcluir;

    private ModeloDAO daoModelo = new ModeloDAO();
    private Modelo modeloSelecionado;
    private boolean controle = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarComboBox();
        configurarTabela();
        atualizarTabela();
        btnEditar.setDisable(false);
        btnVisualizar.setDisable(false);
    }

    private void configurarTabela() {
        TableColumn colId = (TableColumn) tabelaModelo.getColumns().get(0);
        TableColumn colTipo = (TableColumn) tabelaModelo.getColumns().get(1);
        TableColumn colNome = (TableColumn) tabelaModelo.getColumns().get(2);
        TableColumn colMarca = (TableColumn) tabelaModelo.getColumns().get(3);
        TableColumn colQtdPortas = (TableColumn) tabelaModelo.getColumns().get(4);
        TableColumn colMotor = (TableColumn) tabelaModelo.getColumns().get(5);
        TableColumn colCombustivel = (TableColumn) tabelaModelo.getColumns().get(6);
        colId.setResizable(false);
        colTipo.setResizable(false);
        colNome.setResizable(false);
        colMarca.setResizable(false);
        colQtdPortas.setResizable(false);
        colMotor.setResizable(false);
        colCombustivel.setResizable(false);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colQtdPortas.setCellValueFactory(new PropertyValueFactory<>("quantidade_portas"));
        colMotor.setCellValueFactory(new PropertyValueFactory<>("motor"));
        colCombustivel.setCellValueFactory(new PropertyValueFactory<>("nomeCombustivel"));
    }

    private void atualizarTabela() {
        ArrayList<Modelo> modelos = new ArrayList<>();
        tabelaModelo.getItems().clear();
        try {
            modelos = daoModelo.relatorio();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro ao buscar o Modelo.", ButtonType.OK).showAndWait();
            System.out.println("Erro ao buscar o relatório de Modelo: " + ex.getMessage());
        }

        for (int i = 0; i < modelos.size(); i++) {
            tabelaModelo.getItems().add(modelos.get(i));
        }
    }

    @FXML
    private void txtPesquisar_keypressed(KeyEvent event) {
        ArrayList<Modelo> modelos = new ArrayList<>();
        if (event.getCode().toString() == "ENTER") {
            tabelaModelo.getItems().clear();
            try {
                modelos = daoModelo.buscar(txtPesquisar.getText(), 8);
            } catch (Exception ex) {
                exibirErro(ex);
                System.out.println("Erro ao buscar o Modelo pesquisar: " + ex.getMessage());
                new Alert(Alert.AlertType.ERROR, "Erro ao buscar o Modelo.", ButtonType.OK).showAndWait();
            }
            controle = true;
            for (int i = 0; i < modelos.size(); i++) {
                tabelaModelo.getItems().add(modelos.get(i));
            }
        } else if (event.getCode().toString() == "BACK_SPACE") {
            if (txtPesquisar.getText().isEmpty() && controle) {
                tabelaModelo.getItems().clear();
                atualizarTabela();
                controle = false;
            }
        }
    }

    @FXML
    private void btnEditar_pressed(ActionEvent event) {
        btnEditSalvar.setDisable(false);
        btnEditCancelar.setDisable(false);
        Modelo mod = (Modelo) tabelaModelo.getSelectionModel().getSelectedItem();
        if (mod != null) {
            abas.getSelectionModel().select(2);
            abas.getTabs().get(0).setDisable(true);
            abas.getTabs().get(1).setDisable(true);
            abas.getTabs().get(2).setDisable(false);
            preencherEditar(mod);
        } else {
            Alert mens = new Alert(Alert.AlertType.INFORMATION, "Selecione um Modelo na Tabela.", ButtonType.OK);
            mens.showAndWait();
        }

    }

    private void configurarComboBox() {
        cboxCombustivel.getItems().addAll("GASOLINA", "ETANOL", "FLEX", "DIESEL", "GNV", "--");
        cboxCombustivel.getSelectionModel().select(5);
        cbEditCombustivel.getItems().addAll("GASOLINA", "ETANOL", "FLEX", "DIESEL", "GNV", "--");
        cbEditCombustivel.getSelectionModel().select(5);
    }

    private void preencherEditar(Modelo mod) {
        txtEditId.setText(String.valueOf(mod.getId()));
        txtEditTipo.setText(mod.getTipo());
        txtEditNome.setText(mod.getNome());
        txtEditMarca.setText(mod.getMarca());
        txtEditQtdPortas.setText(String.valueOf(mod.getQuantidade_portas()));
        txtEditMotor.setText(mod.getMotor());
        cbEditCombustivel.getSelectionModel().select(mod.getCombustivel());
    }

    @FXML
    private void btnVisualizar_pressed(ActionEvent event) {
        Modelo mod = (Modelo) tabelaModelo.getSelectionModel().getSelectedItem();
        if (mod != null) {
            abas.getSelectionModel().select(3);
            abas.getTabs().get(0).setDisable(true);
            abas.getTabs().get(1).setDisable(true);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(false);
            preencherVisualizar(mod);
        } else {
            Alert mens = new Alert(Alert.AlertType.INFORMATION, "Selecione um Modelo na Tabela.", ButtonType.OK);
            mens.showAndWait();
        }
    }

    private void preencherVisualizar(Modelo mod) {
        viewTipo.setText(mod.getTipo());
        viewNome.setText(mod.getNome());
        viewMotor.setText(mod.getMotor());
        viewMarca.setText(mod.getMarca());
        viewPortas.setText(String.valueOf(mod.getQuantidade_portas()));
        viewCombustível.setText(mod.getNomeCombustivel());
        preencherEditar(mod);
    }

    @FXML
    private void btnSelecionar_press(ActionEvent event) {
        modeloSelecionado = (Modelo) tabelaModelo.getSelectionModel().getSelectedItem();
        if (modeloSelecionado != null) {
            getStage().close();
        } else {

            Alert mens = new Alert(Alert.AlertType.INFORMATION, "Selecione um Modelo na Tabela.", ButtonType.OK);
            mens.showAndWait();

        }
    }

    public Modelo buscarModelo() {
        return modeloSelecionado;
    }

    @FXML
    private void btnCadastrar_pressed(ActionEvent event) {
        if (validarCampoCadastrar()) {
            try {
                daoModelo.inserir(new Modelo(txtTipo.getText(), txtNome.getText(),
                        txtMarca.getText(), Integer.valueOf(txtQtdportas.getText()), txtMotor.getText(), cboxCombustivel.getSelectionModel().getSelectedIndex()));
                new Alert(Alert.AlertType.INFORMATION, "Cadastrado com sucesso!", ButtonType.OK);
            } catch (Exception ex) {
                exibirErro(ex);
                System.out.println("Erro ao inserir o modelo: " + ex.getMessage());
                new Alert(Alert.AlertType.ERROR, "Erro ao inserir o registro.", ButtonType.OK).showAndWait();
            }
        }
    }

    private boolean validarCampoCadastrar() {
        if (txtTipo.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo tipo é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (txtNome.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo nome é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (txtMarca.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo marca é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (txtMotor.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo motor é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (cboxCombustivel.getSelectionModel().isSelected(5)) {
            new Alert(Alert.AlertType.ERROR, "Campo combustível precisa ser selecionado.", ButtonType.OK).showAndWait();
            return false;
        }

        try {
            Integer.parseInt(txtQtdportas.getText());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Campo quantidade de portas é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }

        return true;
    }

    private boolean validarCamposEditar() {
        if (txtEditTipo.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo tipo é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (txtEditNome.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo nome é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (txtEditMarca.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo marca é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (txtEditMotor.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Campo motor é obrigatório", ButtonType.OK).showAndWait();
            return false;
        } else if (cbEditCombustivel.getSelectionModel().isSelected(5)) {
            new Alert(Alert.AlertType.ERROR, "Campo combustível precisa ser selecionado.", ButtonType.OK).showAndWait();
            return false;
        }

        try {
            Integer.parseInt(txtEditQtdPortas.getText());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Campo quantidade de portas é obrigatório", ButtonType.OK).showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    private void btnLimpar_pressed(ActionEvent event) {
        txtTipo.setText("");
        txtNome.setText("");
        txtMarca.setText("");
        txtMotor.setText("");
        txtQtdportas.setText("");
        cboxCombustivel.getSelectionModel().select(5);
    }

    @FXML
    private void btnEditSalvar_pressed(ActionEvent event) {

        if (validarCamposEditar()) {
            try {
                daoModelo.alterar(new Modelo(Integer.valueOf(txtEditId.getText()), txtEditTipo.getText(), txtEditNome.getText(),
                        txtEditMarca.getText(), Integer.valueOf(txtEditQtdPortas.getText()), txtEditMotor.getText(), cbEditCombustivel.getSelectionModel().getSelectedIndex()));
                Alert mens = new Alert(Alert.AlertType.INFORMATION, "Registro editado com sucesso.", ButtonType.OK);
                mens.showAndWait();
            } catch (Exception ex) {
                exibirErro(ex);
                Alert mens = new Alert(Alert.AlertType.ERROR, "Não foi possível editar o registro.", ButtonType.OK);
                mens.showAndWait();
                System.out.println("Erro ao alterar o modelo: " + ex.getMessage());
            }
        }

    }

    @FXML
    private void btnExcluir_press(ActionEvent event) {
        Alert mens = new Alert(Alert.AlertType.CONFIRMATION, "Gostaria de excluir este registro?", ButtonType.YES, ButtonType.NO);
        mens.showAndWait();
        if (mens.getResult() == ButtonType.YES) {
            try {
                daoModelo.excluir(Integer.valueOf(txtEditId.getText()));
                Alert res = new Alert(Alert.AlertType.INFORMATION, "Excluído com sucesso.", ButtonType.OK);
                res.showAndWait();

            } catch (Exception ex) {
                exibirErro(ex); 
                Alert res = new Alert(Alert.AlertType.ERROR, "Erro ao excluir um modelo.", ButtonType.OK);
                 res.showAndWait();
                System.out.println("Erro ao excluir o modelo:" + ex.getMessage());
            }
            abas.getSelectionModel().select(0);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getTabs().get(3).setDisable(true);
            atualizarTabela();
        } else {
            Alert res = new Alert(Alert.AlertType.INFORMATION, "Registro mantido.", ButtonType.OK);
            res.showAndWait();
        }
    }

    @FXML
    private void btnEditCancelar_pressed(ActionEvent event) {
        abas.getSelectionModel().select(0);
        abas.getTabs().get(0).setDisable(false);
        abas.getTabs().get(1).setDisable(false);
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(3).setDisable(true);
        atualizarTabela();
    }

    @FXML
    private void btnViewVoltar_press(ActionEvent event) {
        abas.getSelectionModel().select(0);
        abas.getTabs().get(0).setDisable(false);
        abas.getTabs().get(1).setDisable(false);
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(3).setDisable(true);
        atualizarTabela();
    }
    
    
    @FXML
    private void btnViewModificar_press(ActionEvent event){
       abas.getSelectionModel().select(2);
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
        abas.getTabs().get(2).setDisable(false);
        abas.getTabs().get(3).setDisable(true); 
    }

}
