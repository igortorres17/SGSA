package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        abrirSubForm("/view/Dashboard.fxml");
        btnDashboard.getStyleClass().add("side-button-active");
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
    
    private void abrirSubForm(String caminho_fxml){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(caminho_fxml));
            this.contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao abrir sub-formulario: " + ex.getMessage());
        } 
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
}
