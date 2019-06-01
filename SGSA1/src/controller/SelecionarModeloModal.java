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
import javafx.scene.control.ComboBox;
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
public class SelecionarModeloModal implements Initializable {

    @FXML
    private TabPane abas;
    @FXML
    private TableView<?> tabelaModelo;
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
    private ComboBox<?> cboxCombustivel;
    @FXML
    private TextField txtEditPlaca;
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
    private ComboBox<?> cbEditCombustivel;
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
    private void btnSelecionar_press(ActionEvent event) {
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
    private void btnViewVoltar_press(ActionEvent event) {
    }
    
}
