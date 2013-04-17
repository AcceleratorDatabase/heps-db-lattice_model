/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

import edu.msu.frib.xal.exl2DB.ReadExl;
import java.util.ArrayList;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.openepics.model.api.BeamParameterAPI;
import org.openepics.model.api.BeamParameterPropAPI;
import org.openepics.model.api.ParticleTypeAPI;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.ParticleType;

/**
 *
 * @author lv
 */
public class Test {

    public static void main(String args[]) {
      
        Workbook wb = ReadExl.getWorkbook("E:\\xal\\source_code\\code\\modelDB\\data\\model_parameters_template.xlsx");
        EncapData2DB.instDB(wb,"beam_parameters");
        //new BeamParameterPropAPI().setBeamParameterPropFor("LS1_CA01:GV_D1124", null,"e","f","String"); 
    }
}
