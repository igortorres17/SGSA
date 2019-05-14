package controller;

import java.sql.SQLException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import model.dao.Conexao;

/**
 *
 * @author Hércules M.
 */
public class ControleSplash extends ControleBase{
    @FXML
    private AnchorPane root;
    
    @Override
    public void windowShow(WindowEvent event){
        Platform.runLater(
                () -> {
                    conectar();
                }
        );
    }
    
    private void conectar(){
        try {
            Conexao.get();
            abrirJanela("/view/Login.fxml");
            getStage().close();
        } catch (SQLException ex) {
            System.out.println("Falha ao conetar-se: " + ex.getMessage());
        }
    }
    
}
