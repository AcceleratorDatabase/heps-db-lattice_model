/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelmanager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author chu
 */
public class ModelDisplayController implements Initializable {

    @FXML
    Button runModel;
    @FXML
    MenuItem quit;
    @FXML
    private LineChart<Double, Double> modelDataPlot;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        assert runModel != null : "fx:id=\"runModel\" was not injected: check your FXML file 'modelDisplay.fxml'.";        
        if (runModel != null) {
            runModel.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Hello World");
                }
            });
        }

        assert quit != null : "fx:id=\"quit\" was not injected: check your FXML file 'modelDisplay.fxml'.";        
        if (quit != null) {
            quit.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
        }

        dataPlot();
    }
    
    private void dataPlot() {
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Test");
        // test data
        series.getData().add(new XYChart.Data<>(0.0, 1.6));
        series.getData().add(new XYChart.Data<>(0.8, 0.4));
        series.getData().add(new XYChart.Data<>(1.4, 2.9));
        series.getData().add(new XYChart.Data<>(2.1, 1.3));
        series.getData().add(new XYChart.Data<>(2.6, 0.9));
       
        modelDataPlot.getData().add(series);
        
        modelDataPlot.createSymbolsProperty();
        modelDataPlot.getXAxis().setAutoRanging(true);
    }
}
