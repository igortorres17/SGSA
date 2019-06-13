package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.Sessao;

/**
 *
 * @author Hércules M.
 */
public class ControlePrincipal extends ControleBase implements Initializable{
    @FXML
    private Button btnDashboard;
    
    @FXML
    private Button btnOS;
    
    @FXML
    private Button btnVeiculos;
    
    @FXML
    private Button btnClientes;
    
    @FXML
    private Button btnServicos;
    
    @FXML
    private Button btnPecas;
    
    @FXML
    private AnchorPane contentPane;
    
    @FXML
    private Label lblUsuario;
    
    @FXML
    private Label lblLogadoDesde;
    
    @FXML
    private Button bbtnDeslogar;
    
    @FXML
    private StackPane stackOpcoes;    
    // Custom
    AnchorPane paneLoading;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        lblUsuario.setText(Sessao.getUsuario().getNome());
        
        abrirSubForm("/view/Dashboard.fxml");
        btnDashboard.getStyleClass().add("side-button-active");
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        String data = dataFormatada.format(Calendar.getInstance().getTime());
        lblLogadoDesde.setText(data);
        
    }
    
    private void removerBtnActiveTodos(){
        btnDashboard.getStyleClass().remove("side-button-active");
        btnOS.getStyleClass().remove("side-button-active");
        btnVeiculos.getStyleClass().remove("side-button-active");
        btnClientes.getStyleClass().remove("side-button-active");
        btnServicos.getStyleClass().remove("side-button-active");
        btnPecas.getStyleClass().remove("side-button-active");
    }
    
    private void selecionarBtn(Button btn){
        removerBtnActiveTodos();
        btn.getStyleClass().add("side-button-active");
    }
    
    private Parent carregarSubForm(String caminho_fxml){
        try { 
            Parent subPane = FXMLLoader.load(getClass().getResource(caminho_fxml));
            return subPane;
        } catch (IOException ex) {
            System.out.println("Erro ao carregar FXML: " + ex.getMessage());
            return null;
        }
    }
    
    private void abrirSubForm(String caminho_fxml){
        // Exibe tela de carregamento         
        this.contentPane.getChildren().setAll(carregarSubForm("/view/Carregando.fxml"));
        
        Task task = new Task<Void>(){
            public Void call(){
                try {
                    Thread.sleep(150);
                } catch (InterruptedException ex) {}
                
                Platform.runLater(
                    () -> {
                        contentPane.getChildren().setAll(carregarSubForm(caminho_fxml)); 
                    }
                );
                return null;
            }
        };
        
        new Thread(task).start();
        
    }

    @Override
    public void windowShow(WindowEvent event) {
        getStage().getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            String id = evt.getPickResult().getIntersectedNode().getId();
            String classe = evt.getPickResult().getIntersectedNode().getClass().getName();
            //System.out.println("--------------------------------------------------");
            //System.out.println("ID: " + id);
            //System.out.println("Classe: " + classe);
            if(id != null){
                if(id.equals("dropLblAlterarSenha") || id.equals("dropLblSair") || id.equals("lblUsuario") )
                    return;
            }else if(classe.equals("com.sun.javafx.scene.control.LabeledText")){
                Text txt = (Text) evt.getPickResult().getIntersectedNode();
                String texto = txt.getText();
                if(texto.equals("Sair") || texto.equals("Alterar Senha") || texto.equals(Sessao.getUsuario().getNome()))
                    return;
            }
            
            stackOpcoes.setVisible(false);
            
        });
    }
    
    @FXML
    protected void btnDashboard_pressed(ActionEvent event){
        abrirSubForm("/view/Dashboard.fxml");
        selecionarBtn(btnDashboard);
    }
    
    @FXML
    protected void btnOS_pressed(ActionEvent event){
        abrirSubForm("/view/OrdemServico.fxml");
        selecionarBtn(btnOS);
    }
    
    @FXML
    protected void btnVeiculos_pressed(ActionEvent event){
        abrirSubForm("/view/Veiculos.fxml");
        selecionarBtn(btnVeiculos);
    }
    
    @FXML
    protected void btnClientes_pressed(ActionEvent event){
        abrirSubForm("/view/Clientes.fxml");
        selecionarBtn(btnClientes);
    }
    
     @FXML
    protected void btnServicos_pressed(ActionEvent event){
        abrirSubForm("/view/Servicos.fxml");
        selecionarBtn(btnServicos);
    }
    
    @FXML
    protected void btnPecas_pressed(ActionEvent event){
        abrirSubForm("/view/Pecas.fxml");
        selecionarBtn(btnPecas);
    }
    
    @FXML
    private void btnDeslogar_pressed(ActionEvent event){
        Sessao.logoff();
        abrirJanela("/view/Login.fxml", StageStyle.TRANSPARENT);
        getStage().close();
    }
    
    @FXML
    private void btnClose_pressed(ActionEvent event){
        getStage().close();
    }
    
    @FXML
    private void btnMinimize_pressed(ActionEvent event){
        getStage().setIconified(true);
       // getStage().toBack();
    }
    
    @FXML
    private void dropLblAlterarSenha(MouseEvent event){
        new Alert(Alert.AlertType.INFORMATION, "Em breve...").showAndWait();
    }
    
    @FXML
    private void dropLblSair_pressed(MouseEvent event){
        Sessao.logoff();
        abrirJanela("/view/Login.fxml", StageStyle.TRANSPARENT);
        getStage().close();
    }
    
    
    @FXML
    void lblUsuario_pressed(MouseEvent event) {
        stackOpcoes.setVisible(true);
    }
}
