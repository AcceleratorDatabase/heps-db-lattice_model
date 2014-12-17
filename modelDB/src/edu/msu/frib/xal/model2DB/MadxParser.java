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
import java.util.HashMap;
import java.util.StringTokenizer;
import org.openepics.model.api.ModelAPI;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.extraEntity.BeamParams;
import org.openepics.model.extraEntity.Device;

/**
 *
 * @author chu
 */
public class MadxParser extends Model2DB {
    
    static String sequenceName = "";
    static String speciesName = "";
    static double mass = 0.;
    static double charge = 0.;
    static double energy = 0.;
    String AcceleratorName = "";
    String MODEL = "MAD-X";

//    ArrayList<Device> devices = new ArrayList<>();        

    public MadxParser() {
        setModelName(MODEL);
    }
    
    public MadxParser(File file) {
        this.parse(file);
        setModelName(MODEL);
    }
    
    /**
     * Parse MAD-X output file
     * @param file MAD-X file path
     */
    public final void parse(File file) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        
            String line = "";
            ArrayList<String> elemLines = new ArrayList<>();
            StringTokenizer st = null;
            String[] paramLabels = null;
            
            HashMap<String, Device> deviceNameMap = new HashMap<>();

            // first read in all lines, find out general info and element lines
            while ((line=in.readLine()) != null) {
                if (line.startsWith("@")) {
                    st = new StringTokenizer(line);
                    // skip the "@"
                    st.nextToken();  
                    String label = st.nextToken();
                    switch (label) {
                        case "SEQUENCE":
                            st.nextToken();
                            sequenceName = st.nextToken().replaceAll("\"", "");
                            break;
                        case "PARTICLE":
                            st.nextToken();
                            speciesName = st.nextToken().replaceAll("\"", "");
                            break;
                        case "MASS":
                            st.nextToken();
                            mass = Double.parseDouble(st.nextToken());
                            break;
                        case "CHARGE":
                            st.nextToken();
                            charge = Double.parseDouble(st.nextToken());
                            break;
                        case "ENERGY":
                            st.nextToken();
                            energy = Double.parseDouble(st.nextToken());
                            break;
                        case "TITLE":
                            st.nextToken();
                            AcceleratorName = st.nextToken().replace("\"", "");
                            setAcceleratorName(AcceleratorName);
                            break;
                        default:
                            break;
                    }
                } 
                // get all parameter labels
                else if (line.startsWith("*")) {
                    st = new StringTokenizer(line);
                    int count = st.countTokens();
                    paramLabels = new String[count-3];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        String s = st.nextToken();
                        if (i>2) {
                            paramLabels[i-3] = s;
                        }
                        i++;
                    } 
                }
                // if it is a line of element data
                else if (line.startsWith(" \"")) elemLines.add(line);
            }
            
            for (String elemLine : elemLines) {
                st = new StringTokenizer(elemLine);
                int i = 1;
                String elemName = "";
                double pos = 0.;
                double len = 0.;
                String type = "";
                String k1Val = null;
                String k2Val = null;
                
                HashMap<String, String> propMap = new HashMap<>();
                while (st.hasMoreTokens()) {
                    String s = st.nextToken();
                    if (i==1) elemName = s.replace("\"", "");
                    
                    if (i==2) type = s.replace("\"", "");
                    if (i>2) {
                        switch (paramLabels[i-3]) {
                            case "S":
                                pos = Double.parseDouble(s);
                                break;
                            case "L":
                                len = Double.parseDouble(s);
                                break;
                            case "K1":
                                k1Val = s;
                                break;
                            case "K2":
                                k2Val = s;
                                break;
                            default:
                                propMap.put(paramLabels[i-3], s);
                                break;
                        }                                                                       
                    }
                    i++;
                }
                
                Device dev;
                BeamParams beamParams;
                if (elemName.contains("_A") && deviceNameMap.containsKey(elemName)) {
                    dev = deviceNameMap.get(elemName);
                    beamParams = dev.getBeamParams();
                } else {
                    // define a device object
                    dev = new Device();
                    dev.setElementName(elemName);
                    dev.setPos(pos);
                    dev.setLen(len);
                    dev.setElementType(type);
                    dev.setBeamlineSequenceName(sequenceName);
                    beamParams = new BeamParams(); 
                }
                
              
                // beam parameters
        	ArrayList<BeamParameterProp> beamParameterPropCollection = new ArrayList<>();
                
        	// device settings
        	ArrayList<ElementProp> elementPropCollection = new ArrayList<>();
                
                // fill in parameters
                BeamParameterProp model_s = new BeamParameterProp();
        	model_s.setPropertyName("s");
        	model_s.setBeamParameterDouble(pos);
                model_s.setPropertyDatatype("double");
                model_s.setPropCategory("phaseMean");
                beamParameterPropCollection.add(model_s);

                BeamParameterProp x = new BeamParameterProp();
        	x.setPropertyName("x");
        	x.setBeamParameterDouble(Double.parseDouble(propMap.get("X")));
                x.setPropertyDatatype("double");
                x.setPropCategory("phaseMean");
                beamParameterPropCollection.add(x);
                
        	BeamParameterProp y = new BeamParameterProp();
        	y.setPropertyName("y");
        	y.setBeamParameterDouble(Double.parseDouble(propMap.get("Y")));        	
                y.setPropertyDatatype("double");
                y.setPropCategory("phaseMean");
                beamParameterPropCollection.add(y);
                
        	BeamParameterProp beta_x = new BeamParameterProp();
        	beta_x.setPropertyName("x_beta");
        	beta_x.setBeamParameterDouble(Double.parseDouble(propMap.get("BETX")));
                beta_x.setPropertyDatatype("double");
                beta_x.setPropCategory("twiss");
        	beamParameterPropCollection.add(beta_x);
                
        	BeamParameterProp alpha_x = new BeamParameterProp();
        	alpha_x.setPropertyName("x_alpha");
        	alpha_x.setBeamParameterDouble(Double.parseDouble(propMap.get("ALFX")));
                alpha_x.setPropertyDatatype("double");
                alpha_x.setPropCategory("twiss");
        	beamParameterPropCollection.add(alpha_x);

                BeamParameterProp beta_y = new BeamParameterProp();
        	beta_y.setPropertyName("y_beta");
        	beta_y.setBeamParameterDouble(Double.parseDouble(propMap.get("BETY")));
                beta_y.setPropertyDatatype("double");
                beta_y.setPropCategory("twiss");
        	beamParameterPropCollection.add(beta_y);

        	BeamParameterProp alpha_y = new BeamParameterProp();
        	alpha_y.setPropertyName("y_alpha");
        	alpha_y.setBeamParameterDouble(Double.parseDouble(propMap.get("ALFY")));
                alpha_y.setPropertyDatatype("double");
                alpha_y.setPropCategory("twiss");
        	beamParameterPropCollection.add(alpha_y);

                BeamParameterProp d_x = new BeamParameterProp();
        	d_x.setPropertyName("x_d");
        	d_x.setBeamParameterDouble(Double.parseDouble(propMap.get("DX")));
                d_x.setPropertyDatatype("double");
                d_x.setPropCategory("twiss");
        	beamParameterPropCollection.add(d_x);

                BeamParameterProp d_y = new BeamParameterProp();
        	d_y.setPropertyName("y_d");
        	d_y.setBeamParameterDouble(Double.parseDouble(propMap.get("DY")));
                d_y.setPropertyDatatype("double");
                d_y.setPropCategory("twiss");
        	beamParameterPropCollection.add(d_y);  
                
                BeamParameterProp energy = new BeamParameterProp();
        	energy.setPropertyName("W");
        	energy.setBeamParameterDouble(Double.parseDouble(propMap.get("ENERGY"))*1.e9 - mass*1.e9);
                energy.setPropertyDatatype("double");
                energy.setPropCategory("location");
        	beamParameterPropCollection.add(energy);                  
                
                if (elemName.contains("_A")) {
                    beamParams.setBeamParameterPropCollection(beamParameterPropCollection, 1);
                } else {
                    beamParams.setBeamParameterPropCollection(beamParameterPropCollection, 0);
                }
                // set beam parameters to the corresponding element
                dev.setBeamParams(beamParams);
                
                // device settings
                // add K1
                if (k1Val != null) {
                    ElementProp k1 = new ElementProp();
                    k1.setElementPropName("K1");
                    k1.setElementPropDouble(Double.parseDouble(k1Val));
                    // add to device parameter collection
                    elementPropCollection.add(k1);
                } 
                // add K2
                if (k2Val != null) {
                    ElementProp k2 = new ElementProp();
                    k2.setElementPropName("K2");
                    k2.setElementPropDouble(Double.parseDouble(k2Val));
                    // add to device parameter collection
                    elementPropCollection.add(k2);
                }
                
                dev.setElementPropCollection(elementPropCollection);
                
                devices.add(dev);
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file: " + file.getPath());
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println("File reading error: " + file.getPath());
            System.out.println(e.toString());
        }
    }
    
    /**
     * Return the model name
     * @return model name
     */
    public String getModelName() {
        return AcceleratorName;
    }
    
//    public void saveModel2DB() {
//        ModelAPI theModel = new ModelAPI();

//        // saving model data
//        theModel.setModel("MAD-X Model", sequenceName, devices);        
//    }
}
