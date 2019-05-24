package controller;

import com.mysql.cj.result.ValueFactory;
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
import model.Peca;
import model.dao.PecaDAO;

/**
 * FXML Controller class
 *
 * @author Hércules M.
 */
public class ControlePecas implements Initializable {

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
        }
    }
    
    private void buscar(String nome){
        PecaDAO pecaDAO = new PecaDAO();
        try {
            ArrayList<Peca> pecas = pecaDAO.buscar(nome, LIMITE_REGISTRO);
            preencherTableView(pecas);
        } catch (SQLException ex) {
            System.out.println("Falha ao buscar peças: " + ex.getMessage());
        }
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
    private void btnEditExcluir_pressed(ActionEvent event) {
    }

    @FXML
    private void btnVisVoltar_pressed(ActionEvent event) {
    }
    
}
