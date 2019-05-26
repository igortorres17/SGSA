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
import model.Servico;
import model.dao.ServicoDAO;

/**
 *
 * @author Hércules M.
 */
public class SelecionarServicoModal extends ControleBase implements Initializable {

    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableView tabelaServicos;
    @FXML
    private Button btnSelecionar;
    @FXML
    private ProgressIndicator progresso;

    // Custom
    private final int LIMITE_REGISTROS = 5;
    private Servico servicoSelecionado = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTableView();
        buscar("");
    }    
    
    public Servico getServicoSelecionado(){
        return servicoSelecionado;
    }
    
    public void configurarTableView(){
        TableColumn cID = (TableColumn) tabelaServicos.getColumns().get(0);
        TableColumn cNome = (TableColumn) tabelaServicos.getColumns().get(1);
        TableColumn cPreco = (TableColumn) tabelaServicos.getColumns().get(2);
        
        cID.setCellValueFactory(new PropertyValueFactory("id"));
        cNome.setCellValueFactory(new PropertyValueFactory("nome"));
        cPreco.setCellValueFactory(new PropertyValueFactory("valor"));
    }
    
    public void preencherTableView(ArrayList<Servico> servicos){
        tabelaServicos.getItems().clear();
        for(int i = 0; i < servicos.size(); i++){
            tabelaServicos.getItems().add(servicos.get(i));
        }
        
        if(tabelaServicos.getItems().isEmpty()){
            btnSelecionar.setDisable(true);            
        }else{
            btnSelecionar.setDisable(false);
            tabelaServicos.getSelectionModel().selectFirst();
        }
    }
    
    public void buscar(String placa){
        progresso.setVisible(true);
        txtPesquisar.setDisable(true);
        Task taskBuscar = new Task(){
            public Void call(){
                ServicoDAO servicoDAO = new ServicoDAO();
                ArrayList<Servico> servicos;
                try {
                    servicos = servicoDAO.buscar(placa, LIMITE_REGISTROS);
                    preencherTableView(servicos);
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
        this.servicoSelecionado = (Servico) tabelaServicos.getSelectionModel().getSelectedItem();
        getStage().close();
    }
    
}
