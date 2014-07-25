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
public class MadxParser {
    
    static String sequenceName = "";
    static String speciesName = "";
    static double mass = 0.;
    static double charge = 0.;
    static double energy = 0.;
    static String modelName = "";

    ArrayList<Device> devices = new ArrayList<>();        

    public MadxParser() {
        
    }
    
    public MadxParser(File file) {
        this.parse(file);
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
                            modelName = st.nextToken();
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
                
                // define a device object
                Device dev = new Device();
                dev.setElementName(elemName);
                dev.setPos(pos);
                dev.setLen(len);
                dev.setElementType(type);
                dev.setBeamlineSequenceName(sequenceName);
                // beam parameters
        	ArrayList<BeamParameterProp> beamParameterPropCollection = new ArrayList<>();
        	BeamParams beamParams = new BeamParams();
        	// device settings
        	ArrayList<ElementProp> elementPropCollection = new ArrayList<>();
                
                // fill in parameters
                BeamParameterProp x = new BeamParameterProp();
        	x.setPropertyName("x");
        	x.setBeamParameterDouble(Double.parseDouble(propMap.get("X")));
                beamParameterPropCollection.add(x);
                
        	BeamParameterProp y = new BeamParameterProp();
        	y.setPropertyName("y");
        	y.setBeamParameterDouble(Double.parseDouble(propMap.get("Y")));        	
                beamParameterPropCollection.add(y);
                
        	BeamParameterProp beta_x = new BeamParameterProp();
        	beta_x.setPropertyName("x_beta");
        	beta_x.setBeamParameterDouble(Double.parseDouble(propMap.get("BETX")));
        	beamParameterPropCollection.add(beta_x);
                
        	BeamParameterProp alpha_x = new BeamParameterProp();
        	alpha_x.setPropertyName("x_alpha");
        	alpha_x.setBeamParameterDouble(Double.parseDouble(propMap.get("ALFX")));
        	beamParameterPropCollection.add(alpha_x);

                BeamParameterProp beta_y = new BeamParameterProp();
        	beta_y.setPropertyName("y_beta");
        	beta_y.setBeamParameterDouble(Double.parseDouble(propMap.get("BETY")));
        	beamParameterPropCollection.add(beta_y);

        	BeamParameterProp alpha_y = new BeamParameterProp();
        	alpha_y.setPropertyName("y_alpha");
        	alpha_y.setBeamParameterDouble(Double.parseDouble(propMap.get("ALFY")));
        	beamParameterPropCollection.add(alpha_y);

                BeamParameterProp d_x = new BeamParameterProp();
        	d_x.setPropertyName("x_d");
        	d_x.setBeamParameterDouble(Double.parseDouble(propMap.get("DX")));
        	beamParameterPropCollection.add(d_x);

                BeamParameterProp d_y = new BeamParameterProp();
        	d_y.setPropertyName("y_d");
        	d_y.setBeamParameterDouble(Double.parseDouble(propMap.get("DY")));
        	beamParameterPropCollection.add(d_y);                
                
                beamParams.setBeamParameterPropCollection(beamParameterPropCollection);
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
     * Return all devices (modeling elements)
     * @return all devices 
     */
    public ArrayList<Device> getDevices() {
        return devices;
    }
    
    public void saveModel2DB() {
        ModelAPI theModel = new ModelAPI();

        System.out.println("sequenceName = " + sequenceName);
        // saving model data
        theModel.setModel("XAL Model", sequenceName, devices);        
    }
}
