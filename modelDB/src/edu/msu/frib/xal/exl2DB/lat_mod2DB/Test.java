/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.lat_mod2DB;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import edu.msu.frib.xal.exl2DB.ele2DB.DataEncap;
import edu.msu.frib.xal.exl2DB.ele2DB.ReadEleSheet;
import edu.msu.frib.xal.exl2DB.tools.DBTools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openepics.model.api.BeamParameterAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ModelAPI;
import org.openepics.model.api.ModelCodeAPI;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.Model;

/**
 *
 * @author lv
 */
public class Test {

    public static void main(String[] args) {
       // DataEncap.getEncapData(ReadExl.getWorkbook("E:\\xal\\CSNS_RDBT_RTBT_model_template.xlsx"), "elements");
       // DataEncap.getEncapData(ReadExl.getWorkbook("E:\\xal\\lattice_model_template.xlsx"), "elements");
       //Excel2DB.exl2DB("E:\\xal\\lattice_model_template.xlsx","frib");
        Excel2DB.exl2DB("E:\\xal\\source_code\\code\\modelDB\\data\\lattice_model_template.xlsx","frib");
       // Excel2DB.exl2DB("E:\\xal\\CSNS_RDBT_RTBT_model_template1.xlsx","CSNS");
        /*int i=ReadEleSheet.getLabelColNum(ReadExl.getWorkbook("E:\\xal\\CSNS_RDBT_RTBT_model_template.xlsx"), "elements","element","physical label");
        System.out.println(i);
        int j=ReadEleSheet.getLabelColNum(ReadExl.getWorkbook("E:\\xal\\CSNS_RDBT_RTBT_model_template.xlsx"), "elements","pid","XAL label");
        System.out.println(j);*/
        /* int i=ReadEleSheet.getLabelColNum(ReadExl.getWorkbook("E:\\xal\\lattice_model_template.xlsx"), "elements","element","physical label");
        System.out.println(i);
        int j=ReadEleSheet.getLabelColNum(ReadExl.getWorkbook("E:\\xal\\lattice_model_template.xlsx"), "elements","pid","XAL label");
        System.out.println(j);*/
        //ArrayList a=ReadEleSheet.getRowLabels(ReadExl.getWorkbook("E:\\xal\\CSNS_RDBT_RTBT_model_template.xlsx"), "elements", "physical");
      // Excel2DB.exl2DB("E:\\xal\\CSNS_RDBT_RTBT_model_template.xlsx","CSNS");
      // DataEncap.getEncapData(ReadExl.getWorkbook("E:\\xal\\CSNS_RDBT_RTBT_model_template.xlsx"), "elements");
     //  DataEncap.getEncapData(ReadExl.getWorkbook("E:\\xal\\lattice_model_template.xlsx"), "elements");
       //  DataEncap.getEncapData(ReadExl.getWorkbook("E:\\xal\\lattice_model_template.xlsx"), "elements");
       //int i= ReadEleSheet.getLabelRowNum(ReadExl.getWorkbook("E:\\xal\\CSNS_RDBT_RTBT_model_template.xlsx"), "elements", "XAL");
       // System.out.println(i);
       /* for(int t=11;t<101;t++){
         Model m=ModelAPI.getModelById(t);
         for(int k=1;k<1001;k++){
         Element e=ElementAPI.getElementById(k);
         BeamParameterAPI.setBeamParametersForElement(e, m, "uranium", t+k, 33);
         }
          
         }*/




        /*String a[] = {"beam_current", "beam_intensity", "alpha_x", "beta_x", "emit_rms_x_n", "alpha_y", "beta_y", "emit_rms_y_n", "alpha_z", "beta_z", "emit_rms_z_n", "beam_energy"};

        Connection conn = (Connection) DBTools.getConnection();
        PreparedStatement state = null;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        ////////////////BeamParameter
        /*
        for (int t = 801; t < 1001; t++) {
            for (int k = 1; k < 1001; k++) {
                String sql="INSERT INTO beam_parameter(element_id,model_id,species_name,species_mass,species_charge) VALUES(?,?,?,?,?)";
                try {
                    state=(PreparedStatement) conn.prepareStatement(sql);
                    state.setInt(1, k);
                    state.setInt(2,t);
                    state.setString(3, "uranium");
                    state.setDouble(4, k+t);
                    state.setDouble(5, 33);
                    state.executeUpdate();
                    state.addBatch();
                    //  BeamParameterAPI.setBeamParametersForElement(e, m, "uranium", t+k, 33);
                } catch (SQLException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }*/


        //////////////model
       /* for(int i=102;i<1001;i++){
         String sql="INSERT INTO model(model_name) VALUES(?)";
         try {
         state=(PreparedStatement) conn.prepareStatement(sql);
         state.setString(1, "a"+i);
         state.executeUpdate();
         state.addBatch();
         } catch (SQLException ex) {
         Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/

       /* for (int j = 980001; j < 1000001; j++) {
            System.out.println(j);
            int b_id=j+597618;
         for (int i = 1; i < 13; i++) {
         String sql = "INSERT INTO beam_parameter_prop(beam_parameter_id, property_name, beam_parameter_double) VALUES(?,?,?)";
         try {
         state = (PreparedStatement) conn.prepareStatement(sql);
         state.setInt(1, b_id);
         state.setString(2, a[i - 1]);
         state.setDouble(3, i + j);
         state.executeUpdate();
         state.addBatch();

         } catch (SQLException ex) {
         Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         }
        try {
            state.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }*/

       






    }
}
