/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.beam2DB;

import edu.msu.frib.xal.exl2DB.lat_mod2DB.ReadComSheet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 * @author chu
 */
public class ReadBeamSheet {

   
    public static ArrayList getParticleList(Workbook wb, String sheetName) {
        ArrayList nameList = ReadComSheet.getColList(wb, sheetName, "particle_type", "physical");
        ArrayList massList = ReadComSheet.getColList(wb, sheetName, "particle_mass", "physical");
        ArrayList chargeList = ReadComSheet.getColList(wb, sheetName, "particle_charge", "physical");

        int size = nameList.size();
        ArrayList particleList = new ArrayList();
        for (int i = 0; i < size; i++) {
            Map particle = new HashMap();
            
            particle.put("particle_name", nameList.get(i));
            particle.put("particle_mass", massList.get(i));
            particle.put("particle_charge", chargeList.get(i));

            particleList.add(particle);
        }
        return particleList;
    }
   
}
