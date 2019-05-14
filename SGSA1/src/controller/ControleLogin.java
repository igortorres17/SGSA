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
    private TextField txtUsuario;
    
    @FXML
    private TextField txtSenha;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    protected void btnLogin_pressed(ActionEvent event){
        System.out.println("Login: " + txtUsuario.getText());
        System.out.println("Senha: " + txtSenha.getText());
        super.abrirJanela("/view/Principal.fxml");
    }
}
