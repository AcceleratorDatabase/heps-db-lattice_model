/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

import java.io.File;
import java.util.ArrayList;
import org.openepics.model.extraEntity.Device;

/**
 *
 * @author chu
 */
public class ImpactParser {
    String AcceleratorName = "";
    String MODEL = "IMPACT";
    
    String refPtclFile = "fort.18";
    String xFile = "fort.24";
    String yFile = "fort.25";
    String zFile = "fort.26";
    
    ArrayList<Device> devices = new ArrayList<>(); 
    
    public void parseIMPACT(){
        
        parseRMS(new File(xFile));
        parseRMS(new File(yFile));
        parseRMS(new File(zFile));
    }
    
    /**
     * Parse RMS data file
     * @param file RMS data file
     */
    private final void parseRMS(File file) {
        
    }
}
