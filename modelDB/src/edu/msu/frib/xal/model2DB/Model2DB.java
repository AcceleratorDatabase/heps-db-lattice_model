/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.xal.model2DB;

import static edu.msu.frib.xal.model2DB.MadxParser.sequenceName;
import java.util.ArrayList;
import org.openepics.model.api.ModelAPI;
import org.openepics.model.extraEntity.Device;

/**
 *
 * @author chu
 */
public abstract class Model2DB {
    ArrayList<Device> devices = new ArrayList<>();        
    String modelName;
    String accName;
    
    public void setAcceleratorName(String name) {
        accName = name;
    }
    
    public void setModelName(String name) {
        modelName = name;
    };
    
    public String getAcceleratorName() {
        return accName;
    }
    
    public String getModelName() {
        return modelName;
    }
    
    public void saveModel2DB() {
        ModelAPI theModel = new ModelAPI();

        // saving model data
        theModel.setModel(accName, "Model: " + modelName, sequenceName, devices);        
    }
}
