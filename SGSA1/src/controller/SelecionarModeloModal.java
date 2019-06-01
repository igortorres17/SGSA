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
    

    private ModeloDAO daoModelo = new ModeloDAO();
    private Modelo modeloSelecionado;
    private boolean controle = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void configurarTabela(){
      TableColumn colId = (TableColumn) tabelaModelo.getColumns().get(0);
        TableColumn colTipo = (TableColumn) tabelaModelo.getColumns().get(1);
        TableColumn colNome = (TableColumn) tabelaModelo.getColumns().get(2);
        TableColumn colMarca = (TableColumn) tabelaModelo.getColumns().get(3);
        TableColumn colQtdPortas = (TableColumn) tabelaModelo.getColumns().get(4);
        TableColumn colMotor = (TableColumn) tabelaModelo.getColumns().get(5);
        TableColumn colCombustivel = (TableColumn) tabelaModelo.getColumns().get(6);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colQtdPortas.setCellValueFactory(new PropertyValueFactory<>("quantidade_portas"));
        colMotor.setCellValueFactory(new PropertyValueFactory<>("motor"));
        colCombustivel.setCellValueFactory(new PropertyValueFactory<>("nomeCombustivel"));   
    }
    
    private void atualizarTabela(){
        ArrayList<Modelo> modelos = new ArrayList<>();
        try{
            modelos = daoModelo.relatorio();
        }
        catch(Exception ex){
            System.out.println("Erro ao buscar o relatório de Modelo: "+ex.getMessage());
        }
        
        for(int i=0; i<modelos.size(); i++){
            tabelaModelo.getItems().add(modelos.get(i));
        }
    }

    @FXML
    private void txtPesquisar_keypressed(KeyEvent event) {
        ArrayList<Modelo> modelos = new ArrayList<>();
        if(event.getCode().toString() == "ENTER"){
            tabelaModelo.getItems().clear();
            try{
            modelos = daoModelo.buscar(txtPesquisar.getText(), 8);    
            } 
            catch(Exception ex){
                System.out.println("Erro ao buscar o Modelo: "+ex.getMessage());
            }
            controle = true;
            for(int i= 0; i<modelos.size(); i++){
            tabelaModelo.getItems().add(modelos.get(i));
            }
        }
        else if(event.getCode().toString() == "BACK_SPACE"){
            if(txtPesquisar.getText().isEmpty()&&controle){
            tabelaModelo.getItems().clear();
            atualizarTabela();
            controle=false;
            }
        }
    }

    @FXML
    private void btnEditar_pressed(ActionEvent event) {
        Modelo mod = (Modelo) tabelaModelo.getSelectionModel().getSelectedItem();
            if(mod!=null){
            abas.getSelectionModel().select(2);
            abas.getTabs().get(0).setDisable(true);
            abas.getTabs().get(1).setDisable(true);
            abas.getTabs().get(2).setDisable(false);
            preencherEditar(mod);
            }
            else{
            Alert mens = new Alert(Alert.AlertType.INFORMATION, "Selecione um Modelo na Tabela.");
            mens.show();
            }
        
    }
    private void configurarComboBox(){
        cboxCombustivel.getItems().addAll("GASOLINA", "ETANOL","FLEX", "DIESEL", "GNV","--");
        cboxCombustivel.getSelectionModel().select(5);
        cbEditCombustivel.getItems().addAll("GASOLINA", "ETANOL","FLEX", "DIESEL", "GNV","--");
        cbEditCombustivel.getSelectionModel().select(5);
    }
    
    private void preencherEditar(Modelo mod){
        txtEditTipo.setText(mod.getTipo());
        txtEditNome.setText(mod.getNome());
        txtEditMarca.setText(mod.getMarca());
        txtEditQtdPortas.setText(String.valueOf(mod.getQuantidade_portas()));
        txtEditMotor.setText(mod.getMotor());
        cbEditCombustivel.setVisibleRowCount(mod.getCombustivel());
    }

    @FXML
    private void btnVisualizar_pressed(ActionEvent event) {
        Modelo mod = (Modelo) tabelaModelo.getSelectionModel().getSelectedItem();
        if(mod != null){
        abas.getSelectionModel().select(3);
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(3).setDisable(false);
        preencherVisualizar(mod);
        }
        else{
            Alert mens = new Alert(Alert.AlertType.INFORMATION, "Selecione um Modelo na Tabela.");
            mens.show();
        }
    }
    
    private void preencherVisualizar(Modelo mod){
        viewTipo.setText(mod.getTipo());
        viewNome.setText(mod.getNome());
        viewMotor.setText(mod.getMotor());
        viewMarca.setText(mod.getMarca());
        viewPortas.setText(String.valueOf(mod.getQuantidade_portas()));
        viewCombustível.setText(mod.nomeCombustivel());
    }

    @FXML
    private void btnSelecionar_press(ActionEvent event) {
        modeloSelecionado = (Modelo) tabelaModelo.getSelectionModel().getSelectedItem();
        getStage().close();
    }
    
    public Modelo buscarModelo(){
        return modeloSelecionado;
    }

    @FXML
    private void btnCadastrar_pressed(ActionEvent event) {
        if(!txtTipo.getText().isEmpty()){
            if(!txtNome.getText().isEmpty()){
                if(!txtMotor.getText().isEmpty()){
                    if(!txtMarca.getText().isEmpty()){
                        if(!txtQtdportas.getText().isEmpty()){
                            if(!cboxCombustivel.getSelectionModel().isSelected(5)){
                                try{
                                 daoModelo.inserir(new Modelo(txtTipo.getText(), txtNome.getText(),
                                        txtMarca.getText(),Integer.valueOf(txtQtdportas.getText()),txtMotor.getText(), cboxCombustivel.getSelectionModel().getSelectedIndex()));   
                                }
                                catch(Exception ex){
                                    System.out.println("Erro ao inserir o modelo: "+ex.getMessage());
                                }
                            } else{
                                
                            }
                        }
                    }
                }
            }
        }
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
