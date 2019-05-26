package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Veiculo;

/**
 * FXML Controller class
 *
 * @author Hércules M.
 */
public class ControleOS extends ControleBase implements Initializable {

    @FXML
    private TabPane abas;
    @FXML
    private Button btnEmitirOS;
    @FXML
    private Button btnLimpar;
    @FXML
    private ListView<?> listvServicos;
    @FXML
    private Button btnAddServico;
    @FXML
    private ListView<?> listvPecas;
    @FXML
    private Button btnAddPeca;
    @FXML
    private TextArea txtObs;
    @FXML
    private TextField txtVeiculo;
    @FXML
    private Button btnSelecionarVeiculo;
    @FXML
    private Label lblTotal;
    @FXML
    private TableView<?> tabelaOS;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnDarBaixaOS;
    @FXML
    private Button btnVisualizarOS;
    @FXML
    private Button btnCancelarOS;
    @FXML
    private Button btnVisVoltar;
    @FXML
    private Button btnVisImprimir;
    
    // Custom
    Veiculo veiculoSelecionado = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void btnEmitirOS_pressed(ActionEvent event) {
    }

    @FXML
    private void btnLimpar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnAddServico_pressed(ActionEvent event) {
    }

    @FXML
    private void btnAddPeca_pressed(ActionEvent event) {
    }

    @FXML
    private void btnSelecionarVeiculo_pressed(ActionEvent event) {
        SelecionarVeiculoModal controller = (SelecionarVeiculoModal) abrirModal("/view/SelecionarVeiculoModal.fxml");
        controller.getStage().setTitle("Selecionar Veículo");
        controller.getStage().showAndWait();
        veiculoSelecionado = controller.getVeiculoSelecionado();
        if(veiculoSelecionado == null)
            return;
        String txt = veiculoSelecionado.getModelo().getMarca() + " " + veiculoSelecionado.getNomeModelo() + " (" + veiculoSelecionado.getPlaca() +")" + " - " + veiculoSelecionado.getNomeProprietario();
        txtVeiculo.setText(txt);
    }

    @FXML
    private void txtPesquisar_keypressed(KeyEvent event) {
    }

    @FXML
    private void btnDarBaixaOS_pressed(ActionEvent event) {
    }

    @FXML
    private void btnVisualizarOS_pressed(ActionEvent event) {
    }

    @FXML
    private void btnCancelarOS_pressed(ActionEvent event) {
    }

    @FXML
    private void btnVisVoltar_pressed(ActionEvent event) {
    }

    @FXML
    private void btnVisImprimir_pressed(ActionEvent event) {
    }
    
}
