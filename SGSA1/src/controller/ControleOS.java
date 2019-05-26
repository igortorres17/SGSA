package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.OrdemServico;
import model.Peca;
import model.Servico;
import model.Veiculo;
import model.dao.OrdemServicoDAO;

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
    private ListView listvServicos;
    @FXML
    private Button btnAddServico;
    @FXML
    private ListView listvPecas;
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
    @FXML
    private Button btnRemoverServico;
    @FXML
    private Button btnRemoverPeca;
    
    // Custom
    private Veiculo veiculoSelecionado = null;
    private float valorTotal = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    private void limparCampos(){        
        listvPecas.getItems().clear();
        listvServicos.getItems().clear();
        veiculoSelecionado = null;
        txtVeiculo.setText("");
        txtObs.setText("");
    }
    
    @FXML
    private void btnEmitirOS_pressed(ActionEvent event) {
        if(veiculoSelecionado == null){
            new Alert(AlertType.ERROR, "Selecione um veículo!").showAndWait();
            return;
        }
        
        if(listvPecas.getItems().isEmpty() && listvServicos.getItems().isEmpty()){
            new Alert(AlertType.ERROR, "Você deve incluir pelo menos um serviço ou peça!").showAndWait();
            return;
        }
        
        ArrayList<Servico> servicos = new ArrayList(listvServicos.getItems());
        ArrayList<Peca> pecas = new ArrayList(listvPecas.getItems());
        int status = OrdemServico.ABERTA;
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        String data = formatador.format(Calendar.getInstance().getTime());
        OrdemServico os = new OrdemServico(veiculoSelecionado, valorTotal, servicos, pecas, txtObs.getText(), status, data);
        
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        try {
            osDAO.inserir(os);
            Alert alert = new Alert(AlertType.INFORMATION, "OS emitida com sucesso. Deseja visualizar e/ou imprimir OS?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            limparCampos();
            
        } catch (Exception ex) {
            new Alert(AlertType.ERROR, "Não foi possível registrar OS. Contate o suporte!", ButtonType.OK).showAndWait();
            System.out.println("Não foi possível inserir OS: " + ex.getMessage() );
            
        }
    }

    @FXML
    private void btnLimpar_pressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente limpar todos os campos?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() != ButtonType.YES)
            return;
        limparCampos();
    }

    @FXML
    private void btnAddServico_pressed(ActionEvent event) {
        SelecionarServicoModal controller = (SelecionarServicoModal) abrirModal("/view/SelecionarServicoModal.fxml");
        controller.getStage().setTitle("Incluir Serviço");
        controller.getStage().showAndWait();
        Servico servicoSelecionado = controller.getServicoSelecionado();
        if(servicoSelecionado == null)
            return;
        
        this.listvServicos.getItems().add(servicoSelecionado);
        this.listvServicos.getSelectionModel().selectFirst();
        
        valorTotal += servicoSelecionado.getValor();
        lblTotal.setText(valorTotal + "");
       
    }

    @FXML
    private void btnAddPeca_pressed(ActionEvent event) {
        SelecionarPecaModal controller = (SelecionarPecaModal) abrirModal("/view/SelecionarPecaModal.fxml");
        controller.getStage().setTitle("Incluir Serviço");
        controller.getStage().showAndWait();
        Peca pecaSelecionada = controller.getPecaSelecionada();
        
        if(pecaSelecionada == null)
            return;
        
        this.listvPecas.getItems().add(pecaSelecionada);
        this.listvPecas.getSelectionModel().selectFirst();
        
        valorTotal += pecaSelecionada.getValor();
        lblTotal.setText(valorTotal + "");
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
    
    @FXML
    private void btnRemoverServico_pressed(ActionEvent event){
        Servico servico = (Servico) listvServicos.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION, "Remover '" + servico.getNome() + "' da OS?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            listvServicos.getItems().remove(servico);
            valorTotal -= servico.getValor();
            lblTotal.setText(valorTotal + "");
        }
    }
    
    @FXML
    private void btnRemoverPeca_pressed(ActionEvent event){
        Peca peca = (Peca) listvPecas.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION, "Remover '" + peca.getNome() + "' da OS?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            listvPecas.getItems().remove(peca);
            valorTotal -= peca.getValor();
            lblTotal.setText(valorTotal + "");
        }
    }
    
}
