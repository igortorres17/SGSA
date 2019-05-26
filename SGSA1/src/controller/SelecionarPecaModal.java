package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Peca;
import model.dao.PecaDAO;

/**
 * @author Hércules M.
 */
public class SelecionarPecaModal extends ControleBase implements Initializable {

    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableView tabelaPecas;
    @FXML
    private Button btnSelecionar;
    @FXML
    private ProgressIndicator progresso;
    
    // Custom
    private final int LIMITE_REGISTROS = 5;
    private Peca pecaSelecionada = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTableView();
        buscar("");
    }   
    
    public Peca getPecaSelecionada(){
        return pecaSelecionada;
    }
    
    public void configurarTableView(){
        TableColumn cID = (TableColumn) tabelaPecas.getColumns().get(0);
        TableColumn cNome = (TableColumn) tabelaPecas.getColumns().get(1);
        TableColumn cPreco = (TableColumn) tabelaPecas.getColumns().get(2);
        
        cID.setCellValueFactory(new PropertyValueFactory("id"));
        cNome.setCellValueFactory(new PropertyValueFactory("nome"));
        cPreco.setCellValueFactory(new PropertyValueFactory("valor"));
    }
    
    public void preencherTableView(ArrayList<Peca> pecas){
        tabelaPecas.getItems().clear();
        for(int i = 0; i < pecas.size(); i++){
            tabelaPecas.getItems().add(pecas.get(i));
        }
        
        if(tabelaPecas.getItems().isEmpty()){
            btnSelecionar.setDisable(true);            
        }else{
            btnSelecionar.setDisable(false);
            tabelaPecas.getSelectionModel().selectFirst();
        }
    }
    
    public void buscar(String placa){
        progresso.setVisible(true);
        txtPesquisar.setDisable(true);
        Task taskBuscar = new Task(){
            public Void call(){
                PecaDAO pecaDAO = new PecaDAO();
                ArrayList<Peca> pecas;
                try {
                    pecas = pecaDAO.buscar(placa, LIMITE_REGISTROS);
                    preencherTableView(pecas);
                    progresso.setVisible(false);
                    txtPesquisar.setDisable(false);
                } catch (SQLException ex) {
                    System.out.println("Falha ao buscar serviços: " + ex.getMessage());
                }
                
                return null;
            }
        };
        
        new Thread(taskBuscar).start();
    }
    
    @FXML
    private void txtPesquisar_keyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            buscar(txtPesquisar.getText());
        }
    }

    @FXML
    private void btnSelecionar_pressed(ActionEvent event) {
        this.pecaSelecionada = (Peca) tabelaPecas.getSelectionModel().getSelectedItem();
        getStage().close();
    }
    
}
