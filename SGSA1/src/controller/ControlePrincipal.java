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
public class ControlePrincipal {
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
    
    @FXML
    protected void btnDashboard_pressed(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            this.contentPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            System.out.println("Erro ao abrir dashboard: " + ex.getMessage());
        }
    }
}
