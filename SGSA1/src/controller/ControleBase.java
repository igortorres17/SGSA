package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Hercules M.
 */
public abstract class ControleBase {
    private Stage stage;
    
    protected Stage abrirJanela(String caminho_fxml){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho_fxml));
            Parent root = loader.load();
            
            ControleBase controller = loader.getController();
            controller.setStage(stage);
            
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
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public void windowShow(WindowEvent event){
        
    }
    
}
