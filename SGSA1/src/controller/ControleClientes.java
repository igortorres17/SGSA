package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Cliente;
import model.PessoaFisica;
import model.PessoaJuridica;
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
    
    @FXML
    private Label lblNome;
    
    @FXML
    private Label lblCpf;
    
    @FXML
    private Label lblNasc;
    
    @FXML
    private Label lblRua;
    
    @FXML
    private Label lblBairro;
    
    @FXML
    private Label lblCidade;
    
    @FXML
    private Label lblEmail;
    
    @FXML
    private Label lblTel;
    
    @FXML
    private TabPane abas;
    
    // Custom
    ClienteDAO clienteDao;
    private final int LIMITE_CLIENTES = 10;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {        
        configurarTableView();
        clienteDao = new ClienteDAO();
        
        try {
            ArrayList<Cliente> clientes = clienteDao.buscar("", LIMITE_CLIENTES);
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
    
    private void buscarCliente(String nome){
        Task task = new Task(){
            public Void call() {
                ArrayList<Cliente> clientes;
                try {
                    clientes = clienteDao.buscar(nome, LIMITE_CLIENTES);
                    Platform.runLater(
                        () -> {
                            tabelaClientes.getItems().clear();
                            preencherTableView(clientes);
                            if(tabelaClientes.getItems().isEmpty())
                                habilitarBotoesEditarVisualizar(false);
                            else
                                habilitarBotoesEditarVisualizar(true);

                            tabelaClientes.getSelectionModel().selectFirst();
                        }
                    );
                } catch (SQLException ex) {
                    System.out.println("Falha ao buscar clientes: " + ex.getMessage());
                }

                return null;
            }
        };
        new Thread(task).start();
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
    
    private void visualizarInfo(Cliente cliente){
        lblNasc.setText(cliente.getData_nascimento());
        
        lblRua.setText(cliente.getLogradouro() + ", " + cliente.getNumero());
        lblBairro.setText(cliente.getBairro());
        lblCidade.setText(cliente.getMunicipio() + " / " + cliente.getEstado());
        
        lblEmail.setText(cliente.getEmail());
        lblTel.setText(cliente.getTelefone());
        
        if(cliente instanceof PessoaFisica){
            PessoaFisica pf = (PessoaFisica)cliente;
            lblNome.setText(pf.getNome());
            lblCpf.setText(pf.getCpf());
        }else{
            PessoaJuridica pj = (PessoaJuridica)cliente;
            lblNome.setText(pj.getRazaoSocial());
            lblCpf.setText(pj.getCnpj());
        }
    }
    /*
    * Tratamento de eventos
    */
    @FXML
    protected void btnPesquisar_pressed(ActionEvent event){
        String termo_busca = txtPesquisar.getText();
        
        if(termo_busca.isEmpty())
            return;
        
        buscarCliente(termo_busca);
    }
    
    @FXML
    protected void txtPesquisar_keypressed(KeyEvent event){
        String termo_busca = txtPesquisar.getText();
        KeyCode tecla = event.getCode();
        
        if(tecla == KeyCode.ENTER){
            buscarCliente(txtPesquisar.getText());
        }else if(tecla == KeyCode.BACK_SPACE && termo_busca.isEmpty()){
            buscarCliente("");
        }
    }
    
    @FXML
    protected void btnLimpar_pressed(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja limpar TODOS os campos?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            limparCampos();
        }
    }
    
    @FXML
    protected void btnVisualizar_pressed(ActionEvent event){
        Cliente cliente = (Cliente)tabelaClientes.getSelectionModel().getSelectedItem();         
        visualizarInfo(cliente);
        abas.getTabs().get(0).setDisable(true);
        abas.getTabs().get(1).setDisable(true);
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(3).setDisable(false);
        abas.getSelectionModel().select(3);
        
    }
    
    @FXML
    protected void vis_btnVoltar_pressed(ActionEvent event){        
        abas.getTabs().get(0).setDisable(false);
        abas.getTabs().get(1).setDisable(false);
        abas.getTabs().get(2).setDisable(true);
        abas.getTabs().get(3).setDisable(true);
        abas.getSelectionModel().selectFirst();
    }
    
}
