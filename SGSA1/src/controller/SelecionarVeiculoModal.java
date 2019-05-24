package controller;

import controller.ControleBase;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Hércules M.
 */
public class SelecionarVeiculoModal extends ControleBase implements Initializable {

    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnSelecionar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void txtPesquisar_keyPressed(KeyEvent event) {
    }

    @FXML
    private void btnSelecionar_pressed(ActionEvent event) {
    }
    
}
