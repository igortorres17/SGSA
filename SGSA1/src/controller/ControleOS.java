package controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import model.Mecanico;
import model.OrdemServico;
import model.Peca;
import model.Servico;
import model.Sessao;
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
    private TableView tabelaOS;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnDarBaixaOS;
    @FXML
    private Button btnVisualizarOS;
    @FXML
    private Button btnCancelarOS;
    @FXML
    private Button btnRemoverServico;
    @FXML
    private Button btnRemoverPeca;
    @FXML
    private ProgressIndicator progresso;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblVisualizar;
    
    // Custom
    private final int LIMITE_REGISTROS = 8;
    private Veiculo veiculoSelecionado = null;
    private float valorTotal = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTableView();
        buscar("");
        
        tabelaOS.setRowFactory(
                tv -> {
                TableRow row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if( event.getClickCount() == 1 && (! row.isEmpty()) ){
                        OrdemServico rowData = (OrdemServico)row.getItem();
                        if(rowData.getStatus() == OrdemServico.CONCLUIDA)
                            btnDarBaixaOS.setDisable(true);
                        else
                            btnDarBaixaOS.setDisable(false);
                        
                        if(rowData.getStatus() == OrdemServico.CANCELADA){
                            btnCancelarOS.setDisable(true);
                            btnDarBaixaOS.setDisable(true);
                        }else{
                            btnCancelarOS.setDisable(false);
                            btnDarBaixaOS.setDisable(false);
                        }
                    }
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        OrdemServico rowData = (OrdemServico)row.getItem();
                        btnVisualizarOS_pressed(null);
                    }
                    

                });
                return row ;
            }
        );
    }    
    
    private void configurarTableView(){
        TableColumn cId = (TableColumn) tabelaOS.getColumns().get(0);
        TableColumn cVeiculo = (TableColumn) tabelaOS.getColumns().get(1);
        TableColumn cObs = (TableColumn) tabelaOS.getColumns().get(2);
        TableColumn cValor = (TableColumn) tabelaOS.getColumns().get(3);
        TableColumn cStatus = (TableColumn) tabelaOS.getColumns().get(4);
        cId.setResizable(false);
        cVeiculo.setResizable(false);
        cObs.setResizable(false);
        cValor.setResizable(false);
        cStatus.setResizable(false);
        
        cId.setCellValueFactory(new PropertyValueFactory("id"));
        cVeiculo.setCellValueFactory(new PropertyValueFactory("veiculoStr"));
        cObs.setCellValueFactory(new PropertyValueFactory("Observacao"));
        cValor.setCellValueFactory(new PropertyValueFactory("valor"));
        cStatus.setCellValueFactory(new PropertyValueFactory("StatusName"));
    }
        
    private void preencherTableView(ArrayList<OrdemServico> ordens){
        tabelaOS.getItems().clear();
        for(int i = 0; i < ordens.size(); i++){
            if(ordens.get(i).getStatus() == OrdemServico.CANCELADA)
                continue;
            tabelaOS.getItems().add(ordens.get(i));
        }
        
        if(tabelaOS.getItems().size() > 0){
            btnCancelarOS.setDisable(false);
            btnDarBaixaOS.setDisable(false);
            btnVisualizarOS.setDisable(false);
            tabelaOS.getSelectionModel().selectFirst();
            return;
        }
        
            btnCancelarOS.setDisable(true);
            btnDarBaixaOS.setDisable(true);
            btnVisualizarOS.setDisable(true);
    }
    
    private void buscar(String placa){
        progresso.setVisible(true);
        txtPesquisar.setDisable(true);
        txtPesquisar.getStyleClass().remove("pesquisar-cinza-icon");
        tabelaOS.setDisable(true);
        btnCancelarOS.setDisable(true);
        btnDarBaixaOS.setDisable(true);
        btnVisualizarOS.setDisable(true);
        Task task = new Task(){
            public Void call(){
                OrdemServicoDAO osDAO = new OrdemServicoDAO();
                try {
                    ArrayList<OrdemServico> os;
                    os = osDAO.bucar_em_high_speed(placa, LIMITE_REGISTROS);
                    preencherTableView(os);
                    progresso.setVisible(false);
                    txtPesquisar.setDisable(false);
                    txtPesquisar.getStyleClass().add("pesquisar-cinza-icon");
                    tabelaOS.setDisable(false);
                } catch (SQLException ex) {
                    exibirErro(ex);
                    System.out.println("Falha ao buscar OS's: " + ex.getMessage());
                    ex.printStackTrace();
                }
               return null; 
            }
        };
        
        new Thread(task).start();
    }
    
    private void limparCampos(){        
        listvPecas.getItems().clear();
        listvServicos.getItems().clear();
        veiculoSelecionado = null;
        valorTotal = 0;
        txtVeiculo.setText("");
        txtObs.setText("");
        lblTotal.setText("0.0");
    }
    
    private  void preencherListViewServico(ArrayList<Servico> servicos){
        listvServicos.getItems().clear();
        for(int i = 0; i < servicos.size(); i++){
            listvServicos.getItems().add(servicos.get(i));
        }
    }
    
    private void preencherListViewPeca(ArrayList<Peca> pecas){
        listvPecas.getItems().clear();
        for(int i = 0; i < pecas.size(); i++){
            listvPecas.getItems().add(pecas.get(i));
        }
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
        
        Alert alert = new Alert(AlertType.CONFIRMATION, "Realmente deseja EMITIR esta Ordem de Serviço?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Emitir OS?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.NO)
            return;
        
        ArrayList<Servico> servicos = new ArrayList(listvServicos.getItems());
        ArrayList<Peca> pecas = new ArrayList(listvPecas.getItems());
        int status = OrdemServico.ABERTA;
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        String data = formatador.format(Calendar.getInstance().getTime());
        OrdemServico os = new OrdemServico(veiculoSelecionado, valorTotal, servicos, pecas, txtObs.getText(), status, data);
        
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        try {
            osDAO.inserir(os);
            alert = new Alert(AlertType.INFORMATION, "Ordem de Serviço emitida com sucesso", ButtonType.OK);
            alert.setHeaderText("Emissão bem sucedida");
            alert.showAndWait();
            
            abas.getSelectionModel().selectLast();
            limparCampos();
            
        } catch (Exception ex) {
            exibirErro(ex);
            System.out.println("Não foi possível inserir OS: " + ex.getMessage() );
            ex.printStackTrace();
            
        }
    }

    @FXML
    private void btnLimpar_pressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente limpar todos os campos?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Limpar campos?");
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
        if(event.getCode() == KeyCode.ENTER){
            buscar(txtPesquisar.getText());
        }
    }

    @FXML
    private void btnDarBaixaOS_pressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente dar baixa / marcar como concluida esta Ordem de Serviço?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Dar baixa na OS?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.NO)
            return;
        
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        try {
            OrdemServico os = (OrdemServico) tabelaOS.getSelectionModel().getSelectedItem();
            os.setStatus(OrdemServico.CONCLUIDA);
            osDAO.concluir(os);
            tabelaOS.refresh();
            alert = new Alert(AlertType.INFORMATION, "A Ordem de Serviço foi baixada / concluída com sucesso", ButtonType.OK);
            alert.setHeaderText("Baixa de OS bem sucedida");
            alert.showAndWait();
        } catch (SQLException ex) {
            exibirErro(ex);
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnVisualizarOS_pressed(ActionEvent event) {
        
        // Controles
        btnEmitirOS.setVisible(false);
        btnLimpar.setVisible(false);
        btnAddPeca.setVisible(false);
        btnAddServico.setVisible(false);
        btnRemoverPeca.setVisible(false);
        btnRemoverServico.setVisible(false);
        btnSelecionarVeiculo.setVisible(false);
        btnVoltar.setVisible(true);
        lblVisualizar.setText("Visualizar Ordem de Serviço");
        
        listvPecas.setDisable(true);
        listvServicos.setDisable(true);
        txtObs.setDisable(true);
        
        OrdemServico os = (OrdemServico) tabelaOS.getSelectionModel().getSelectedItem();
        preencherListViewServico(os.getServicos());
        preencherListViewPeca(os.getPecas());
        txtVeiculo.setText(os.getVeiculoStr());
        txtObs.setText(os.getObservacao());
        lblTotal.setText(os.getValor() + "");
        abas.getTabs().get(0).setText("Visualizar");
        abas.getTabs().get(1).setDisable(true);
        abas.getSelectionModel().selectFirst();
    }

    @FXML
    private void btnCancelarOS_pressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Esta operação só poderá ser desfeita pela equipe de T.I. Realmente deseja cancelar esta Ordem de Serviço? ", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Cancelar OS?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.NO)
            return;
        
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Autenticação");
        inputDialog.setHeaderText("Insira a senha do usuário " + Sessao.getUsuario().getNome() + " para continuar.");
        inputDialog.setContentText("Senha: ");
        Optional<String> result = inputDialog.showAndWait();
        Mecanico usuario = (Mecanico)Sessao.getUsuario();
        
        if(result == null)
            return;
        
        if(!result.isPresent())
            return;
        
        int eIgual = usuario.getSenha().compareTo(result.get());
        if( eIgual != 0 ){
            Alert al = new Alert(AlertType.ERROR, "Senha informada não confere com a conta do usuário corrente", ButtonType.OK);
            al.setHeaderText("Senha incorreta!");
            al.showAndWait();
            return;
        }
        
        OrdemServicoDAO osDAO = new OrdemServicoDAO();
        try {
            OrdemServico os = (OrdemServico) tabelaOS.getSelectionModel().getSelectedItem();
            os.setStatus(OrdemServico.CANCELADA);
            osDAO.cancelar(os);
            tabelaOS.refresh();
            alert = new Alert(AlertType.INFORMATION, "A Ordem de Serviço foi cancelada com sucesso", ButtonType.OK);
            alert.setHeaderText("Cancelamento bem sucedido");
            alert.showAndWait();
        } catch (SQLException ex) {
            exibirErro(ex);
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void btnRemoverServico_pressed(ActionEvent event){
        Servico servico = (Servico) listvServicos.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION, "Realmente deseja remover o serviço '" + servico.getNome() + "' da lista?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Remover serviço?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            listvServicos.getItems().remove(servico);
            valorTotal -= servico.getValor();
            lblTotal.setText(valorTotal + "");
            alert = new Alert(AlertType.CONFIRMATION, "Serviço removido com sucesso da lista", ButtonType.OK);
            alert.setHeaderText("Remoção bem sucedida");
        }
    }
    
    @FXML
    private void btnRemoverPeca_pressed(ActionEvent event){
        Peca peca = (Peca) listvPecas.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION, "Realmente deseja remover a peça '" + peca.getNome() + "' da lista?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Remover peça?");
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            listvPecas.getItems().remove(peca);
            valorTotal -= peca.getValor();
            lblTotal.setText(valorTotal + "");
            alert = new Alert(AlertType.CONFIRMATION, "Peça removido com sucesso da lista", ButtonType.OK);
            alert.setHeaderText("Remoção bem sucedida");
        }
    }
    
    
    @FXML
    void btnVoltar_pressed(ActionEvent event) {
        limparCampos();
        abas.getSelectionModel().selectLast();
        abas.getSelectionModel().getSelectedItem().setDisable(false);
        abas.getTabs().get(0).setText("Emitir OS");
        btnVoltar.setVisible(false);
        
        // Controles
        btnEmitirOS.setVisible(true);
        btnLimpar.setVisible(true);
        btnAddPeca.setVisible(true);
        btnAddServico.setVisible(true);
        btnRemoverPeca.setVisible(true);
        btnRemoverServico.setVisible(true);
        btnSelecionarVeiculo.setVisible(true);
        btnVoltar.setVisible(false);
        lblVisualizar.setText("Emitir Ordem de Serviço");
        
        listvPecas.setDisable(false);
        listvServicos.setDisable(false);
        txtObs.setDisable(false);
    }
    
}
