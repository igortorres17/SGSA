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
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Cliente;
import model.dao.ClienteDAO;

/**
 * FXML Controller class
 *
 * @author root
 */
public class SelecionarClienteModal extends ControleBase implements Initializable {

    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableView tabelaCliente;
    @FXML
    private Button btnSelecionar;
    @FXML
    private ProgressIndicator progresso;
    

    /**
     * Initializes the controller class.
     */
    private Cliente clienteSelecionado;
    private boolean controle = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabelaCliente.getItems().clear();
        configurarTabela();
        atualizarTabela();
    }    

    private void configurarTabela(){
    TableColumn colId = (TableColumn) tabelaCliente.getColumns().get(0);
        TableColumn colNome = (TableColumn) tabelaCliente.getColumns().get(1);
        TableColumn colCpf = (TableColumn) tabelaCliente.getColumns().get(2);
        colId.setResizable(false);
        colNome.setResizable(false);
        colCpf.setResizable(false);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    }
    private void atualizarTabela(){
    tabelaCliente.getItems().clear();
    ClienteDAO daocli = new ClienteDAO();
    ArrayList<Cliente> clientes = new ArrayList<>();
    
    try{
        clientes = daocli.buscar("", 10);
    }
    catch(Exception ex){
        System.out.println("Erro ao buscar clientes"+ex.getMessage());
    }
    if(!clientes.isEmpty()){
        for(int i=0; i<clientes.size(); i++){
        tabelaCliente.getItems().add(clientes.get(i));
        }
    }
    
    }
    
    
    @FXML
    private void txtPesquisar_keyPressed(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")){
            if(!txtPesquisar.getText().isEmpty()){
                ArrayList<Cliente> clientes = new ArrayList<>();
                ClienteDAO daocli = new ClienteDAO();
                tabelaCliente.getItems().clear();
                try{
                    clientes=daocli.buscar(txtPesquisar.getText().toUpperCase(), 10);
                }
                catch(Exception ex){
                    System.out.println("Erro ao buscar clientes em SelecionarClientes: "+ex.getMessage());
                }
                if(!clientes.isEmpty()){
                    for(int i=0; i<clientes.size(); i++){
                        tabelaCliente.getItems().add(clientes.get(i));
                    }
                    controle = true;
                }
                else{
                    System.out.println("Cliente não encontrado.");
                }
            }
        } else if(event.getCode().toString().equals("BACK_SPACE")){
           if(txtPesquisar.getText().isEmpty() && controle){
               tabelaCliente.getItems().clear();
               atualizarTabela();
               controle = false;
           } 
        }
    }

    @FXML
    private void btnSelecionar_pressed(ActionEvent event) {
        this.clienteSelecionado = (Cliente) tabelaCliente.getSelectionModel().getSelectedItem();
        getStage().close();
    }
    
    public Cliente buscaCliente(){
        return clienteSelecionado;
    }
}
