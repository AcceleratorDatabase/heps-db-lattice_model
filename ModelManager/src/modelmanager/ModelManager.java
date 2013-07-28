/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelmanager;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author paul
 */
public class ModelManager extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane page = (AnchorPane) FXMLLoader.load(ModelManager.class.getResource("modelDisplay.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setTitle("Open XAL Online Model");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(ModelManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
