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
import model.Peca;
import model.dao.PecaDAO;

/**
 * FXML Controller class
 *
 * @author Hércules M.
 */
public class ControlePecas extends ControleBase implements Initializable {

    @FXML
    private TabPane abas;
    @FXML
    private TableView tabelaPecas;
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
    private TextField txtCodigo;
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
    private TextField txtEditCodigo;
    @FXML
    private Button btnVisVoltar;
    @FXML
    private Label lblNome;
    @FXML
    private Label lblPreco;
    @FXML
    private Label lblSerial;

    // Custom
    private final int LIMITE_REGISTRO = 8;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTableView();
        buscar("");
    }    
    
    private void configurarTableView(){
        TableColumn cId = (TableColumn) tabelaPecas.getColumns().get(0);
        TableColumn cNome = (TableColumn) tabelaPecas.getColumns().get(1);
        TableColumn cSerial = (TableColumn) tabelaPecas.getColumns().get(2);
        TableColumn cPreco = (TableColumn) tabelaPecas.getColumns().get(3);
        cId.setResizable(false);
        cNome.setResizable(false);
        cSerial.setResizable(false);
        cPreco.setResizable(false);
        
        cId.setCellValueFactory(new PropertyValueFactory("id"));
        cNome.setCellValueFactory(new PropertyValueFactory("nome"));
        cSerial.setCellValueFactory(new PropertyValueFactory("serial"));
        cPreco.setCellValueFactory(new PropertyValueFactory("valor"));
        
    }
    
    private void  preencherTableView(ArrayList<Peca> pecas){
        tabelaPecas.getItems().clear();
        for(int i = 0; i < pecas.size(); i++){
            tabelaPecas.getItems().add(pecas.get(i));
        }
        
        if(!tabelaPecas.getItems().isEmpty()){
            tabelaPecas.getSelectionModel().selectFirst();
            btnVisualizar.setDisable(false);
            btnEditar.setDisable(false);
        }else{
           btnVisualizar.setDisable(true); 
           btnEditar.setDisable(true);
           tabelaPecas.getSelectionModel().selectFirst();
        }
    }
    
    private void buscar(String nome){
        PecaDAO pecaDAO = new PecaDAO();
        try {
            ArrayList<Peca> pecas = pecaDAO.buscar(nome, LIMITE_REGISTRO);
            preencherTableView(pecas);
        } catch (SQLException ex) {
            exibirErro(ex);
            System.out.println("Falha ao buscar peças: " + ex.getMessage());
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
            Float.parseFloat(txtPreco.getText());
        }catch(NumberFormatException ex){
            new Alert(AlertType.ERROR, "Campo preço tem que ser numérico", ButtonType.OK).showAndWait();
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
            Float.parseFloat(txtEditPreco.getText());
        }catch(NumberFormatException ex){
            new Alert(AlertType.ERROR, "Campo preço tem que ser numérico", ButtonType.OK).showAndWait();
            return false;
        }
        
        return true;
    }
    
    private void limparCamposCadastro(){
        txtNome.setText("");
        txtCodigo.setText("");
        txtPreco.setText("");
    }
    
    private void limparCamposEditar(){
        txtEditNome.setText("");
        txtEditCodigo.setText("");
        txtEditPreco.setText("");
    }
        
    /**
     * Tratamento de Eventos
     */
    @FXML
    private void txtPesquisar_keypressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            buscar(txtPesquisar.getText());
        }
    }

    @FXML
    private void btnEditar_pressed(ActionEvent event) {
        Peca peca = (Peca) tabelaPecas.getSelectionModel().getSelectedItem();
        txtEditNome.setText(peca.getNome());
        txtEditCodigo.setText(peca.getSerial());
        txtEditPreco.setText(peca.getValor() + "");
        abas.getSelectionModel().select(2);        
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
        abas.getTabs().get(2).setDisable(false);
    }

    @FXML
    private void btnVisualizar_pressed(ActionEvent event) {
        Peca peca = (Peca) tabelaPecas.getSelectionModel().getSelectedItem();
        lblNome.setText(peca.getNome());
        lblSerial.setText(peca.getSerial());
        lblPreco.setText(peca.getValor() + "");
        
        abas.getSelectionModel().selectLast();
        abas.getSelectionModel().getSelectedItem().setDisable(false);
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
    }
    
    @FXML
    private void btnCadastrar_pressed(ActionEvent event) {
        if(!validarCamposCadastro())
            return;
        Peca peca = new Peca(txtNome.getText(), txtCodigo.getText(), Float.parseFloat(txtPreco.getText().trim()));
        PecaDAO pecaDAO = new PecaDAO();
        try {
            pecaDAO.inserir(peca);
            Alert alert = new Alert(AlertType.INFORMATION, "A peça foi cadastrada com sucesso", ButtonType.OK);
            alert.setHeaderText("Cadastro de peça");
            alert.showAndWait();
            limparCamposCadastro();
            abas.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            exibirErro(ex);
            System.out.println("Erro ao cadastrar peca: " + ex.getMessage());
            ex.printStackTrace();
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
        
        Alert alert = new Alert(AlertType.CONFIRMATION, "Realmente deseja salvar todas as alterações?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Salvar dados da peça?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.NO)
            return;
        
        Peca peca = (Peca) tabelaPecas.getSelectionModel().getSelectedItem();
        peca.setNome(txtEditNome.getText());
        peca.setSerial(txtEditCodigo.getText());
        peca.setValor(Float.parseFloat(txtEditPreco.getText().trim()));
        PecaDAO pecaDAO = new PecaDAO();
        try {
            pecaDAO.alterar(peca);
            Alert alert_ = new Alert(AlertType.INFORMATION, "Os dados da peça foram alterados com sucesso!", ButtonType.OK);
            alert_.setHeaderText("Peça alterada");
            alert_.showAndWait();
            tabelaPecas.refresh();
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
    private void btnEditExcluir_pressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Realmente deseja deletar o registro desta peça permanentemente?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Excluir registro?");
        alert.showAndWait();
        if(alert.getResult() != ButtonType.YES)
            return;
        
        PecaDAO pecaDAO = new PecaDAO();
        try {
            Peca peca = (Peca)tabelaPecas.getSelectionModel().getSelectedItem();
            pecaDAO.excluir(peca);
            Alert alert_ = new Alert(AlertType.INFORMATION, "O registro da peça foi deletado com sucesso!", ButtonType.OK);
            alert_.setHeaderText("Deleção bem sucedida");
            alert_.showAndWait();
            abas.getTabs().get(0).setDisable(false);
            abas.getTabs().get(1).setDisable(false);
            abas.getTabs().get(2).setDisable(true);
            abas.getSelectionModel().selectFirst();
            tabelaPecas.getItems().remove(peca);
            tabelaPecas.refresh();
        } catch (SQLException ex) {
            exibirErro(ex);
            System.out.println("Falha ao deletar peça: " + ex.getMessage());
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
       
}
