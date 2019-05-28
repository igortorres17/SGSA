/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author root
 */
public class ControleVeiculo implements Initializable {

    @FXML
    private TabPane abas;
    @FXML
    private TableView<?> tabelaVeiculo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnVisualizar;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtChassi;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtQuilometragem;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TextField txtModelo;
    @FXML
    private Button btnInserirModelelo;
    @FXML
    private TextField txtNomeCliente;
    @FXML
    private Button btnSelecionarCliente;
    @FXML
    private TextField txtEditPlaca;
    @FXML
    private TextField txtEditChassi;
    @FXML
    private TextField txtEditQuilo;
    @FXML
    private TextField txtEditAno;
    @FXML
    private TextField txtEditModelo;
    @FXML
    private TextField txtEditProprietario;
    @FXML
    private Button btnEditSalvar;
    @FXML
    private Button btnEditCancelar;
    @FXML
    private Button btnEditModelo;
    @FXML
    private Button btnEditProprietario;
    @FXML
    private Button btnViewVoltar;
    @FXML
    private Button btnViewModificar;
    @FXML
    private Button btnViewImprimir;
    @FXML
    private Label viewPlaca;
    @FXML
    private Label viewChassi;
    @FXML
    private Label viewAno;
    @FXML
    private Label viewModelo;
    @FXML
    private Label viewProprietario;
    @FXML
    private Label viewAno1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void txtPesquisar_keypressed(KeyEvent event) {
    }

    @FXML
    private void btnEditar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnVisualizar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnCadastrar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnLimpar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnEditSalvar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnEditCancelar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnEditModelo_pres(ActionEvent event) {
    }

    @FXML
    private void btnEditProprietario_press(ActionEvent event) {
    }

    @FXML
    private void btnViewVoltar_press(ActionEvent event) {
    }
    
}
