package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hércules M.
 */
public class ControleLogin {
    @FXML
    private TextField fieldLogin;
    
    @FXML
    private TextField fieldSenha;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    protected void btnLogin_pressed(ActionEvent event){
        System.out.println("Login: " + fieldLogin.getText());
        System.out.println("Senha: " + fieldSenha.getText());
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Principal.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControleLogin.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
