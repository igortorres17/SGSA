package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Hercules M.
 */
public abstract class ControleBase {
    private Stage stage;
    
    private Stage instanciarJanela(String caminho_fxml){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho_fxml));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            ControleBase controller = (ControleBase) loader.getController();
            controller.setStage(stage);
            
            stage.setScene(scene);
            return stage;
        } catch (IOException ex) {
            System.out.println("Falha ao abrir janela: " + ex.getMessage());
        }     
        
        return null;
    }
    
    protected Stage abrirJanela(String caminho_fxml){
        Stage stage = instanciarJanela(caminho_fxml);
        stage.setResizable(false);
        stage.show();
        return stage;
    }
    
    protected Stage abrirJanela(String caminho_fxml, StageStyle style){
        Stage stage = instanciarJanela(caminho_fxml);
        stage.setResizable(false);
        stage.initStyle(style);
        stage.show();
        return stage;
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
