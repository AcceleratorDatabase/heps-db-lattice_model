/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.openepics.model.api.BeamParameterAPI;
import org.openepics.model.api.BeamParameterPropAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ParticleTypeAPI;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ParticleType;

/**
 *
 * @author lv
 * @author chu
 */
public class EncapData2DB {

    public static void instDB(Workbook wb, String sheetName) {
        ArrayList particleList=ReadBeamSheet.getParticleList(wb, sheetName);
       
        ArrayList eleList = ReadBeamSheet.getColList(wb, sheetName, "start element", "physical");
        ArrayList encapDataList = DataEncap.getEncapData(wb, sheetName);
        int num = encapDataList.size();
        for (int i = 0; i < num; i++) {
            System.out.println("*****"+i);
            String eleName = (String) eleList.get(i);
            Element element = new ElementAPI().getElementByName(eleName);
            if(element==null){
               System.out.println("The element "+eleName+" doesn't exist in the database!");
            }

            Map particle=(Map) particleList.get(i);                          
            ParticleType parType = new ParticleTypeAPI().getParticleType(particle.get("particle_name").toString() ,
                    Double.parseDouble(particle.get("particle_mass").toString()), 
                    (int)Double.parseDouble(particle.get("particle_charge").toString()));
            if(parType==null){
               System.out.println("The ParticleType "+particle.get("particle_name")+" "+particle.get("particle_mass")+" "+particle.get("particle_charge")+" doesn't exist in the database!");
            }

            BeamParameter bp = new BeamParameterAPI().setBeamParameter(element, null, parType);

            ArrayList rowClsList = (ArrayList) encapDataList.get(i);           
            Iterator it = rowClsList.iterator();
            while (it.hasNext()) {
                BeamCell cell = (BeamCell) it.next();                
                new BeamParameterPropAPI().setBeamParameterProp(bp, cell.getCategory(), cell.getName(), cell.getDatatype(), cell.getValue(), null);
            }
        }
    }
    
}
