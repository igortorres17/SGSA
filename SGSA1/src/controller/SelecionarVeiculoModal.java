package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import model.Veiculo;
import model.dao.VeiculoDAO;

/**
 *
 * @author Hércules M.
 */
public class SelecionarVeiculoModal extends ControleBase implements Initializable {

    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnSelecionar;
    @FXML
    private TableView tabelaVeiculos;
    @FXML
    private ProgressIndicator progresso;
    
    // CUSTOM
    private final int LIMITE_REGISTROS = 5;
    private Veiculo veiculoSelecionado = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTableView();
        buscar("");
    }  
    
    public Veiculo getVeiculoSelecionado(){
        return veiculoSelecionado;
    }
    
    public void configurarTableView(){
        TableColumn cProprietario = (TableColumn) tabelaVeiculos.getColumns().get(0);
        TableColumn cModelo = (TableColumn) tabelaVeiculos.getColumns().get(1);
        TableColumn cPlaca = (TableColumn) tabelaVeiculos.getColumns().get(2);
        cProprietario.setResizable(false);
        cModelo.setResizable(false);
        cPlaca.setResizable(false);
        
        
        cProprietario.setCellValueFactory(new PropertyValueFactory("nomeProprietario"));
        cModelo.setCellValueFactory(new PropertyValueFactory("nomeModelo"));
        cPlaca.setCellValueFactory(new PropertyValueFactory("placa"));
    }
    
    public void preencherTableView(ArrayList<Veiculo> veiculos){
        tabelaVeiculos.getItems().clear();
        for(int i = 0; i < veiculos.size(); i++){
            tabelaVeiculos.getItems().add(veiculos.get(i));
        }
        
        if(tabelaVeiculos.getItems().isEmpty()){
            btnSelecionar.setDisable(true);            
        }else{
            btnSelecionar.setDisable(false);
            tabelaVeiculos.getSelectionModel().selectFirst();
        }
    }
    
    public void buscar(String placa){
        progresso.setVisible(true);
        txtPesquisar.setDisable(true);
        Task taskBuscar = new Task(){
            public Void call(){
                VeiculoDAO veiculoDAO = new VeiculoDAO();
                ArrayList<Veiculo> veiculos;
                try {
                    veiculos = veiculoDAO.buscar(placa, LIMITE_REGISTROS);
                    preencherTableView(veiculos);
                    progresso.setVisible(false);
                    txtPesquisar.setDisable(false);
                } catch (SQLException ex) {
                    System.out.println("Falha ao buscar veículos: " + ex.getMessage());
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
        this.veiculoSelecionado = (Veiculo) tabelaVeiculos.getSelectionModel().getSelectedItem();
        getStage().close();
    }
    
}
