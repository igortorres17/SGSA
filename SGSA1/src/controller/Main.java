package controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application{

    public static void main(String[] args) {
           launch(args);     
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Splash.fxml"));        
        Parent root = loader.load();    
        
        ControleBase controller = (ControleBase) loader.getController();
        controller.setStage(stage);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SGSA - Carregando...");
        
        stage.addEventHandler(
                WindowEvent.WINDOW_SHOWN,
                new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent window){
                        controller.windowShow(window);
                    }
                }
        );
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    
}
