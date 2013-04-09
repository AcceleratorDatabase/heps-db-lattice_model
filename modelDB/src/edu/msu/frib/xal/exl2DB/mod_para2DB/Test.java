/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

import edu.msu.frib.xal.exl2DB.ReadExl;
import java.util.ArrayList;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.openepics.model.api.ParticleTypeAPI;
import org.openepics.model.entity.ParticleType;

/**
 *
 * @author lv
 */
public class Test {

    public static void main(String args[]) {
        Workbook wb = ReadExl.getWorkbook("E:\\xal\\source_code\\code\\modelDB\\data\\model_parameters_template.xlsx");
        EncapData2DB.instDB(wb,"beam_parameters");
        //   ReadBeamSheet.getColNum(wb, "beam_parameters");
       /*ArrayList a= ReadBeamSheet.getRowLabels(wb, "beam_parameters", "data type");
         for(int i=0;i<a.size();i++){
         System.out.println(i+"****"+a.get(i));
         }*/
        /*  ArrayList b=ReadBeamSheet.getDataList(wb, "beam_parameters");
         ArrayList b1=(ArrayList) b.get(0);
         System.out.println(b1.size());
         for(int i=0;i<b1.size();i++){
         System.out.println(b1.get(i));
         }*/
        /*ArrayList a = DataEncap.getEncapData(wb, "beam_parameters");

        ArrayList b = (ArrayList) a.get(0);
        for (int j = 0; j < b.size(); j++) {
            BeamCell cell = (BeamCell) b.get(j);
            System.out.println(cell.getValue()+"*******"+cell.getTableName());
        }*/
        /*  ArrayList eleList=ReadBeamSheet.getColList(wb, "beam_parameters", "start element","physical");
       
        for(int i=0;i<eleList.size();i++)
            System.out.println(eleList.get(i));*/
        //ParticleType pt= new ParticleTypeAPI().getParticleType("uranium", 221695648160.0, 33);
        //System.out.println(pt);
        //ArrayList parList=ReadBeamSheet.getParticleList(wb, "beam_parameters");
       // Map m=(Map) parList.get(0);
      //  System.out.println(m.get("particle_mass"));
      //  ArrayList massList = ReadBeamSheet.getColList(wb, "beam_parameters", "species_charge", "xal");
      //  System.out.println(massList);
      // System.out.println(ReadBeamSheet.getLabelColNum(wb, "beam_parameters", "species_name", "xal"));
       
      //  int i=ReadBeamSheet.getLabelColNum(wb, "beam_parameters", "start element", "physical");
        //System.out.println(i);
        // ArrayList eleList = ReadBeamSheet.getColList(wb, "beam_parameters", "start element", "physical");
       //  System.out.println(eleList);
    }
}
