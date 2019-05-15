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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Cliente;
import model.dao.ClienteDAO;

/**
 *
 * @author Hércules M.
 */
public class ControleClientes extends ControleBase implements Initializable{
    @FXML
    private TextField txtPesquisar;
    
    @FXML
    private Button btnPesquisar;
    
    @FXML
    private TableView tabelaClientes;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnVisualizar;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtCpf;
    
    @FXML
    private TextField txtNascimento;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtTelefone;
    
    @FXML
    private TextField txtLogradouro;
    
    @FXML
    private TextField txtNumero;
    
    @FXML
    private TextField txtComplemento;
    
    @FXML
    private TextField txtBairro;
    
    @FXML
    private TextField txtCidade;
    
    @FXML
    private ComboBox cbEstado;
    
    @FXML
    private RadioButton rbFisica;
    
    @FXML
    private RadioButton rbJuridica;
    
    @FXML
    private Button btnLimpar;
    
    @FXML
    private Button btnCadastrar;
    
    // Custom
    ClienteDAO clienteDao;
    private int limiteRegistros = 10;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {        
        configurarTableView();
        clienteDao = new ClienteDAO();
        
        try {
            ArrayList<Cliente> clientes = clienteDao.buscar("", limiteRegistros);
            preencherTableView(clientes);
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar clientes: " + ex.getMessage());
        }
        
        tabelaClientes.getSelectionModel().selectFirst();
        if(tabelaClientes.getItems().size() > 0)
            habilitarBotoesEditarVisualizar(true);
        
        cbEstado.getItems().addAll("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "Selecionar");
    }
    
    private void configurarTableView(){
        TableColumn colId = (TableColumn) tabelaClientes.getColumns().get(0);
        TableColumn colNome = (TableColumn) tabelaClientes.getColumns().get(1);
        TableColumn colCpf = (TableColumn) tabelaClientes.getColumns().get(2);
        TableColumn colEmail = (TableColumn) tabelaClientes.getColumns().get(3);
        TableColumn colTelefone = (TableColumn) tabelaClientes.getColumns().get(4);
        TableColumn colTipo = (TableColumn) tabelaClientes.getColumns().get(5);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }
    
    private void preencherTableView(ArrayList<Cliente> clientes){
        if(clientes == null)
            return;
        
        for(int i = 0; i < clientes.size(); i++){
                tabelaClientes.getItems().add(clientes.get(i));
            }
    }
    
    private void habilitarBotoesEditarVisualizar(Boolean habilitar){
        btnEditar.setDisable(!habilitar);
        btnVisualizar.setDisable(!habilitar);
    }
    
    private void buscar(String nome){
        tabelaClientes.getItems().clear();
        try {
            ArrayList<Cliente> clientes = clienteDao.buscar(nome, limiteRegistros);
            for(int i = 0; i < clientes.size(); i++){
                tabelaClientes.getItems().add(clientes.get(i));
            }
            if(tabelaClientes.getItems().size() == 0)
                habilitarBotoesEditarVisualizar(false);
            else
                habilitarBotoesEditarVisualizar(true);
            
            tabelaClientes.getSelectionModel().selectFirst();            
        } catch (SQLException ex) {
            System.out.println("Falha ao buscar: " + ex.getMessage());
        }
    }
    
    private void limparCampos(){
        txtNome.setText("");
        txtCpf.setText("");
        txtNascimento.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtLogradouro.setText("");
        txtNumero.setText("");
        txtComplemento.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        cbEstado.getSelectionModel().selectLast();
        rbFisica.selectedProperty().set(true);
    }    
    
    /*
    * Tratamento de eventos
    */
    @FXML
    protected void btnPesquisar_pressed(ActionEvent event){
        String termo_busca = txtPesquisar.getText();
        
        if(termo_busca.isEmpty())
            return;
        
        buscar(termo_busca);
    }
    
    @FXML
    protected void txtPesquisar_keyTyped(KeyEvent event){
        if(txtPesquisar.getText().isEmpty())
            buscar("");
    }
    
    @FXML
    protected void btnLimpar_pressed(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja limpar TODOS os campos?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            limparCampos();
        }
    }
    
}
