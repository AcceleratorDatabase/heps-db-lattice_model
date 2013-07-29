/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB;

import edu.msu.frib.xal.exl2DB.tools.DBTools;
import java.util.Collection;
import java.util.List;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.ss.usermodel.Workbook;
import org.openepics.model.api.BeamParameterAPI;
import org.openepics.model.api.BeamParameterPropAPI;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ElementPropAPI;
import org.openepics.model.api.LatticeAPI;
import org.openepics.model.api.ParticleTypeAPI;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.ParticleType;

/**
 *
 * @author lv
 */
public class Test {

    public static void main(String[] args) {   
        //delete a lattice
       // new LatticeAPI().deleteLatticeByName("lattice_model_template");    
        
       Excel2DB.exl2DB("E:\\NetBeansProjects\\discs_model\\code\\code\\modelDB\\data\\lattice_model_template.xls"); 
              
      /*  ReadExl r=new ReadExl();
         SummaryInformation si=r.getSummaryInformation("E:\\xal\\source_code\\code\\modelDB\\data\\lattice_model_template.xls");
         System.out.println(si.getProperties());
         // Workbook book=ReadExl.getWorkbook("E:\\xal\\source_code\\code\\modelDB\\data\\lattice_model_template.xls");
         System.out.println(si.getAuthor());
         System.out.println(si.getLastAuthor());
         System.out.println(si.getCreateDateTime());
         System.out.println(si.getLastSaveDateTime());*/



        /*String s=r.getFileName("E:\\xal\\source_code\\code\\modelDB\\data\\lattice_model_template.xlsx");
         System.out.println(s);*/


    }
}
