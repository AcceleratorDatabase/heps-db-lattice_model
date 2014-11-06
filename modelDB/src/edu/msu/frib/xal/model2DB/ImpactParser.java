/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.extraEntity.BeamParams;
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
        
        parseRMS(new File(xFile), 1);
        parseRMS(new File(yFile), 2);
        parseRMS(new File(zFile), 3);
    }
    
    /**
     * Parse RMS data file
     * @param file RMS data file
     * @param ind plane indicator, 1 = x, 2 = y, 3 = z
     */
    private final void parseRMS(File file, int ind) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line = "";
            ArrayList<String> elemLines = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                String[] data = line.split("\\s");
                double pos = Double.parseDouble(data[0]);
                double x = Double.parseDouble(data[1]);
                double sigma = Double.parseDouble(data[2]);
                double xp = Double.parseDouble(data[3]);
                double rms_xp = Double.parseDouble(data[4]);
                double alpha = Double.parseDouble(data[5]);
                double emit_n = Double.parseDouble(data[6]);
                
                //TODO Element name, may need to modify IMPACT to accommodate this
                String elemName = "";
                // define a device object
                Device dev = new Device();
                dev.setElementName(elemName);
                dev.setPos(pos);
                
        	BeamParams beamParams = new BeamParams();
                
                // beam parameters
        	ArrayList<BeamParameterProp> beamParameterPropCollection = new ArrayList<>();
                
        	// device settings
        	ArrayList<ElementProp> elementPropCollection = new ArrayList<>();
                
                // fill in parameters
                BeamParameterProp x_prop = new BeamParameterProp();
        	BeamParameterProp alpha_prop = new BeamParameterProp();
        	BeamParameterProp sigma_prop = new BeamParameterProp();
                BeamParameterProp xp_prop = new BeamParameterProp();
                BeamParameterProp emit_prop = new BeamParameterProp();

                switch (ind) {
                    case 1:
                        x_prop.setPropertyName("x");
                        alpha_prop.setPropertyName("x_alpha");
                        sigma_prop.setPropertyName("x_sigma");
                        xp_prop.setPropertyName("xp");
                        emit_prop.setPropertyName("x_emittance_n");
                        break;
                    case 2:
                        x_prop.setPropertyName("y");
                        alpha_prop.setPropertyName("y_alpha");
                        sigma_prop.setPropertyName("y_sigma");
                        xp_prop.setPropertyName("yp");
                        emit_prop.setPropertyName("y_emittance_n");
                        break;
                    case 3:
                        x_prop.setPropertyName("z");
                        alpha_prop.setPropertyName("z_alpha");
                        sigma_prop.setPropertyName("z_sigma");
                        xp_prop.setPropertyName("yp");
                        emit_prop.setPropertyName("z_emittance_n");
                        break;
                    default:
                        x_prop.setPropertyName("x");
                        alpha_prop.setPropertyName("x_alpha");
                        sigma_prop.setPropertyName("x_sigma");
                        xp_prop.setPropertyName("xp");
                        emit_prop.setPropertyName("x_emittance_n");
                        break;                        
                }
                
        	x_prop.setBeamParameterDouble(x);
                beamParameterPropCollection.add(x_prop);
                alpha_prop.setBeamParameterDouble(alpha);
                beamParameterPropCollection.add(alpha_prop);
                sigma_prop.setBeamParameterDouble(sigma);
                beamParameterPropCollection.add(sigma_prop);
                xp_prop.setBeamParameterDouble(xp);
                beamParameterPropCollection.add(xp_prop);
                emit_prop.setBeamParameterDouble(emit_n);
                beamParameterPropCollection.add(emit_prop);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: " + file.getPath());
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println("File reading error: " + file.getPath());
            System.out.println(e.toString());
        }
    }
}
