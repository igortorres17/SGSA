package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

/**
 *
 * @author Hércules M.
 */
public class ControleLogin extends ControleBase{
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
        super.abrirJanela("/view/Principal.fxml");
    }
}
