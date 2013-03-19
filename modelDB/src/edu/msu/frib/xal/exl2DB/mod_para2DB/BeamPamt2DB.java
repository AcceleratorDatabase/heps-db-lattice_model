/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author lv
 */
public class BeamPamt2DB {

    public static void instDB(ArrayList mapData) {
        String speciesName;
        double speciesMass;
        double speciesCharge;
        
        String propertyName;
        Object propertyValue;
        String dataType;
        Iterator it = mapData.iterator();
        while (it.hasNext()) {
            //Map dataMap=(Map) it.next();
  
            /*Map.Entry entry = (Map.Entry) it.next();
            if (entry.getKey().toString().equals("particle_type")) {
                speciesName = entry.getValue().toString();
            } else if (entry.getKey().toString().equals("particle_mass")) {
                speciesMass = Double.parseDouble(entry.getValue().toString());
            } else if (entry.getKey().toString().equals("particle_charge")) {
                speciesCharge = Double.parseDouble(entry.getValue().toString());
            }*/



        }
    }
}
