package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
            
            stage.addEventHandler(
                WindowEvent.WINDOW_SHOWN,
                new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent window){
                        controller.windowShow(window);
                    }
                }
            );
            
            stage.setScene(scene);
            
            return stage;
        } catch (IOException ex) {
            System.out.println("Falha ao abrir janela: " + ex.getMessage());
            ex.printStackTrace();
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
        
    protected ControleBase abrirModal(String caminho_fxml){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho_fxml));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner(getStage());
            stage.initModality(Modality.APPLICATION_MODAL);
            ControleBase controller = (ControleBase) loader.getController();
            controller.setStage(stage);
            return controller;
        } catch (IOException ex) {
            System.out.println("Falha ao abrir modal: " + ex.getMessage());
            return null;
        }  
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public void windowShow(WindowEvent event){
        
    }
    
    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String s = sw.toString();
        return s;
    }
        
    protected void exibirErro(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Alerta de erro");
        alert.setHeaderText(e.getMessage());
 
        VBox dialogPaneContent = new VBox();
 
        Label label = new Label("Rastreamento da pilha:");
 
        String stackTrace = this.getStackTrace(e);
        TextArea textArea = new TextArea();
        textArea.setText(stackTrace);
 
        dialogPaneContent.getChildren().addAll(label, textArea);
 
        // Set content for Dialog Pane
        alert.getDialogPane().setContent(dialogPaneContent);
 
        alert.showAndWait();
    }
           
}
