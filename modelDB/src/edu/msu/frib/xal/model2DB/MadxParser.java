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
    
    public void parse(File file) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        
            String line = "";
            ArrayList<String> elemLines = new ArrayList<>();
            StringTokenizer st = null;
            String[] paramLabels;

            // first read in all lines, find out general info and element lines
            while ((line=in.readLine()) != null) {
                if (line.startsWith("@")) {
                    st = new StringTokenizer(line);
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
                        if (i>2) {
                            paramLabels[i-3] = st.nextToken();
                        }
                        i++;
                    } 
                }
                // if it is a line of element data
                else if (line.startsWith("\"")) elemLines.add(line);
            }
            
            for (String elemLine : elemLines) {
                st = new StringTokenizer(elemLine);
                int i = 0;
                while (st.hasMoreTokens()) {
                    String elemName = "";
                    String type = "";
                    if (i==0) elemName = st.nextToken();
                    if (i==1) type = st.nextToken();
                    if (i>3) {
                        
                    }
                    i++;
                }
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
