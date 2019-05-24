/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Servico;
import model.dao.ServicoDAO;

/**
 * FXML Controller class
 *
 * @author Hercules
 */
public class ControleServicos extends ControleBase implements Initializable {

    @FXML
    private TabPane abas;
    @FXML
    private TableView tabelaServicos;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnVisualizar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtPreco;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnLimpar;
    @FXML
    private TextField txtEditNome;
    @FXML
    private TextField txtEditPreco;
    @FXML
    private Button btnEditSalvar;
    @FXML
    private Button btnEditCancelar;
    @FXML
    private Button btnVisVoltar;
    @FXML
    private Button btnVisModificar;
    @FXML
    private Button btnVisImprimir;
    @FXML
    private Label lblNome;
    @FXML
    private Label lblPreco;
    
    // CUSTOM
    private final int LIMITE_REGISTRO = 10;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTableView();
        ServicoDAO servicoDAO = new ServicoDAO();
        buscar("");
    }    
    
    private void configurarTableView(){
        TableColumn tId = (TableColumn) tabelaServicos.getColumns().get(0);
        TableColumn tNome = (TableColumn) tabelaServicos.getColumns().get(1);
        TableColumn tPreco = (TableColumn) tabelaServicos.getColumns().get(2);
        
        tId.setCellValueFactory(new PropertyValueFactory("id"));
        tNome.setCellValueFactory(new PropertyValueFactory("nome"));
        tPreco.setCellValueFactory(new PropertyValueFactory("valor"));
        
    }
    
    private void preencherTableView(ArrayList<Servico> servicos){
        tabelaServicos.getItems().clear();
        for(int i = 0; i < servicos.size(); i++){
            tabelaServicos.getItems().add(servicos.get(i));
        }
        
        if(!tabelaServicos.getItems().isEmpty()){
            tabelaServicos.getSelectionModel().selectFirst();
            btnVisualizar.setDisable(false);
            btnEditar.setDisable(false);
        }else{
           btnVisualizar.setDisable(true); 
           btnEditar.setDisable(true);
        }
    }
    
    private void buscar(String nome){
        ServicoDAO servicoDAO = new ServicoDAO();
        try {
            preencherTableView(servicoDAO.buscar(nome, LIMITE_REGISTRO));
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar serviços: " + ex.getMessage());
        }
    }
    
    /*
    *   Tratamento de Eventos
    */
    @FXML
    private void txtPesquisar_keypressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            buscar(txtPesquisar.getText());
        }
    }

    @FXML
    private void btnEditar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnVisualizar_pressed(ActionEvent event) {
        Servico servico = (Servico)tabelaServicos.getSelectionModel().getSelectedItem();
        lblNome.setText(servico.getNome());
        lblPreco.setText(servico.getValor() + "");
        abas.getSelectionModel().selectLast();
        abas.getSelectionModel().getSelectedItem().setDisable(false);
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
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
    private void btnVisVoltar_pressed(ActionEvent event) {
        abas.getSelectionModel().getSelectedItem().setDisable(true);
        abas.getSelectionModel().selectFirst();
        abas.getTabs().get(0).setDisable(false);
        abas.getTabs().get(1).setDisable(false);
    }

    @FXML
    private void btnVisModificar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnVisImprimir_pressed(ActionEvent event) {
    }
    
}
