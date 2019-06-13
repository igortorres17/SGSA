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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private Button btnEditExcluir;
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
    private final int LIMITE_REGISTRO = 8;

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
        tId.setResizable(false);
        tNome.setResizable(false);
        tPreco.setResizable(false);
        
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
            exibirErro(ex);
            System.out.println("Erro ao buscar serviços: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private boolean validarCamposCadastro(){
        if(txtNome.getText().isEmpty()){
            new Alert(AlertType.ERROR, "Campo nome é obrigatório!", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtPreco.getText().isEmpty()){
            new Alert(AlertType.ERROR, "Campo preço é obrigatório!", ButtonType.OK).showAndWait();
            return false;
        }
        
        try{
            float preco = Float.parseFloat(txtPreco.getText());
        }catch(NumberFormatException ex){
            new Alert(AlertType.ERROR, "Campo preço é numérico!", ButtonType.OK).showAndWait();
            return false;
        }
        return true;
    }
    
    private boolean validarCamposEditar(){
        if(txtEditNome.getText().isEmpty()){
            new Alert(AlertType.ERROR, "Campo nome é obrigatório!", ButtonType.OK).showAndWait();
            return false;
        }
        
        if(txtEditPreco.getText().isEmpty()){
            new Alert(AlertType.ERROR, "Campo preço é obrigatório!", ButtonType.OK).showAndWait();
            return false;
        }
        
        try{
            float preco = Float.parseFloat(txtEditPreco.getText());
        }catch(NumberFormatException ex){
            new Alert(AlertType.ERROR, "Campo preço é numérico!", ButtonType.OK).showAndWait();
            return false;
        }
        return true;
    }
        
    private void limparCamposCadastro(){
        txtNome.setText("");
        txtPreco.setText("");
    }
    
    private void limparCamposEditar(){
        txtEditNome.setText("");
        txtEditPreco.setText("");
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
        Servico servico = (Servico) tabelaServicos.getSelectionModel().getSelectedItem();
        txtEditNome.setText(servico.getNome());
        txtEditPreco.setText(servico.getValor() + "");
        abas.getSelectionModel().select(2);        
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
        abas.getTabs().get(2).setDisable(false);
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
        if(validarCamposCadastro()){
            Servico servico = new Servico(txtNome.getText(), Float.parseFloat(txtPreco.getText()));
            ServicoDAO servicoDAO = new ServicoDAO();
            try {
                servicoDAO.inserir(servico);
                Alert alert = new Alert(AlertType.INFORMATION, "Serviço cadastrado com sucesso!", ButtonType.OK);
                alert.setHeaderText("Cadastro bem sucedido");
                alert.showAndWait();
                limparCamposCadastro();
                abas.getSelectionModel().selectFirst();
            } catch (SQLException ex) {
                new Alert(AlertType.ERROR, "Falha ao inserir. Contate o suporte!", ButtonType.OK).showAndWait();
                System.out.println("Falha ao inserir: " + ex.getMessage());
            }
        }
    }

    @FXML
    private void btnLimpar_pressed(ActionEvent event) {
        limparCamposCadastro();
    }

    @FXML
    private void btnEditSalvar_pressed(ActionEvent event) {
        if(!validarCamposEditar())
            return;
        
        Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja salvar todas as alterações?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Alterar dados do serviço?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.NO)
            return;
        
        Servico servico = (Servico) tabelaServicos.getSelectionModel().getSelectedItem();
        servico.setNome(txtEditNome.getText());
        servico.setValor(Float.parseFloat(txtEditPreco.getText()));
        ServicoDAO servicoDAO = new ServicoDAO();
        try {
            servicoDAO.alterar(servico);
            alert = new Alert(AlertType.INFORMATION, "Todos os dados alterados foram salvos com sucesso!", ButtonType.OK);
            alert.setHeaderText("Serviço alterado");
            alert.showAndWait();
            tabelaServicos.refresh();
            abas.getSelectionModel().selectFirst();
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
        } catch (SQLException ex) {
            exibirErro(ex);
            System.out.println("Falha ao alterar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnEditCancelar_pressed(ActionEvent event) {
        limparCamposEditar();
        abas.getTabs().get(0).setDisable(false);
        abas.getTabs().get(1).setDisable(false);
        abas.getTabs().get(2).setDisable(true);
        abas.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void btnEditExcluir_pressed(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Realmente deseja deletar permanentemente este registro?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Excluir serviço?");
        alert.showAndWait();
        if(alert.getResult() != ButtonType.YES)
            return;
        
        ServicoDAO servicoDAO = new ServicoDAO();
        try {
            Servico servico = (Servico)tabelaServicos.getSelectionModel().getSelectedItem();
            servicoDAO.excluir(servico);
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getSelectionModel().selectFirst();
            tabelaServicos.getItems().remove(servico);
            tabelaServicos.refresh();
            alert = new Alert(AlertType.INFORMATION, "O serviço foi deletado com sucesso!", ButtonType.OK);
            alert.setHeaderText("Serviço deletado");
            alert.showAndWait();
        } catch (SQLException ex) {
            exibirErro(ex);
            System.out.println("Falha ao excluir serviço: " + ex.getMessage());
            ex.printStackTrace();
        }
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
    
}
