package controller;

import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.PasswordField;
import model.Mecanico;
import model.Sessao;
import model.dao.ClienteDAO;

/**
 * FXML Controller class
 *
 * @author Hércules M.
 */
public class ControleAlterarSenhaModal extends ControleBase implements Initializable {

    @FXML
    private PasswordField txtSenhaAtual;
    @FXML
    private PasswordField txtNovaSenha;
    @FXML
    private PasswordField txtRepitaNovaSenha;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSalvar_pressed(ActionEvent event) {
        Mecanico usuario = (Mecanico) Sessao.getUsuario();
        
        if(txtSenhaAtual.getText().isBlank() || txtSenhaAtual.getText().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR, "Campo 'Senha Atual' não pode ser vazio ou conter somente espaços em branco", ButtonType.OK);
            alert.setHeaderText("Campo 'Senha Atual' está vazio");
            alert.showAndWait();
            return;
        }
        
        if( !txtSenhaAtual.getText().equals( usuario.getSenha() ) ){
            Alert alert = new Alert(AlertType.ERROR, "Campo 'Senha Atual' não confere com os dados do usuário logado", ButtonType.OK);
            alert.setHeaderText("Senha atual incorreta");
            alert.showAndWait();
            return;
        }
        
        if(txtNovaSenha.getText().isBlank() || txtNovaSenha.getText().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR, "Campo 'Nova Senha' não pode ser vazio ou conter somente espaços em branco", ButtonType.OK);
            alert.setHeaderText("Campo 'Nova Senha' está vazio");
            alert.showAndWait();
            return;
        }
        
        if( !txtNovaSenha.getText().equals(txtRepitaNovaSenha.getText()) ){
            Alert alert = new Alert(AlertType.ERROR, "Campo 'Repita Nova Senha' não confere com o campo 'Nova Senha'", ButtonType.OK);
            alert.setHeaderText("'Repita Nova Senha' diferente de 'Nova Senha'");
            alert.showAndWait();
            return;
        }        
        
        
        ClienteDAO clienteDAO = new ClienteDAO();
        usuario.setSenha(txtNovaSenha.getText());
        try {
            clienteDAO.alterar(usuario);
            Alert alert = new Alert(AlertType.INFORMATION, "Nova senha salva com sucesso.", ButtonType.OK);
            alert.setHeaderText("Sucesso");
            alert.showAndWait();
            getStage().close();
        } catch (SQLException ex) {
            exibirErro(ex);
            Logger.getLogger(ControleAlterarSenhaModal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnCancelar_pressed(ActionEvent event) {
    }
    
}
