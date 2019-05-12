package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hercules M.
 */
public abstract class ControleBase {
    
    protected Stage abrirJanela(String caminho_fxml){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(caminho_fxml));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            return stage;
        } catch (IOException ex) {
            System.out.println("Falha ao abrir janela: " + ex.getMessage());
        }     
        
        return null;
    }
}
