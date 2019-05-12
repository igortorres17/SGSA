package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Hércules M.
 */
public class ControlePrincipal extends ControleBase{
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
    protected void initialize(){
        
    }
    
    protected void abrirSubForm(String caminho_fxml){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(caminho_fxml));
            this.contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("Erro ao abrir sub-formulario: " + ex.getMessage());
        } 
    }
    
    @FXML
    protected void btnDashboard_pressed(ActionEvent event){
        abrirSubForm("/view/Dashboard.fxml");
    }
}
