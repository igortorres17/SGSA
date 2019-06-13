package controller;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.StageStyle;
import model.Cliente;
import model.PessoaFisica;

import model.Sessao;
import model.dao.ClienteDAO;

/**
 *
 * @author Hércules M.
 */
public class ControleLogin extends ControleBase{
    @FXML
    private TextField txtUsuario;
    
    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private Button btnLogin;
    
    // Custom
    Alert alertVazio;
    Alert alertIncorreto;
    Alert alertIncorreto2;
    
    private Cliente login(String usuario, String senha){
        ClienteDAO clienteDao = new ClienteDAO();
        try {
            return clienteDao.login(usuario, senha);
        } catch (SQLException ex) {
            System.out.println("Falha ao logar-se: " + ex.getMessage());
            return null;
        }
    }
    
    private boolean vazio(String texto){
        if(texto.isEmpty())
            return true;
        
        return false;
    }
    
    @FXML
    protected void btnLogin_pressed(ActionEvent event){
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText(); 
        
        alertVazio = new Alert(AlertType.WARNING, "Todos os campos são obrigatórios!", ButtonType.OK);
        alertIncorreto = new Alert(AlertType.ERROR, "Login ou senha incorretos!", ButtonType.OK);
        alertIncorreto2 = new Alert(AlertType.ERROR, "Obs: ocorreu um problema. Contate o suporte", ButtonType.OK);
        
        if(vazio(usuario) || vazio(senha)){
            alertVazio.showAndWait();
            return;
        }
        
        Cliente usr = login(usuario, senha);
        if(usr == null){
            alertIncorreto.showAndWait();
            return;
        }
        
        try {
            Sessao.setUsuario((PessoaFisica) usr);
        } catch (Exception ex) {
            alertIncorreto2.showAndWait();
            return;
        }
            
        
        super.abrirJanela("/view/Principal.fxml");
         getStage().close();
    }
    
    @FXML
    private void btnClose_pressed(ActionEvent event) {
        getStage().close();
        Runtime.getRuntime().exit(0);
    }
}
