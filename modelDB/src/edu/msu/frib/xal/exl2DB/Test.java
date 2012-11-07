/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB;

import edu.msu.frib.xal.exl2DB.element2DB.Class2DB;
import edu.msu.frib.xal.exl2DB.element2DB.ReadEleExl;
import edu.msu.frib.xal.exl2DB.rfgap2DB.RfMap2DB;
import edu.msu.frib.xal.exl2DB.sequence2DB.Data2Map;
import edu.msu.frib.xal.exl2DB.sequence2DB.SeqMap2DB;
import edu.msu.frib.xal.exl2DB.sequence2DB.ReadSeqExl;
import edu.msu.frib.xal.exl2DB.tools.DBTools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.RfGapAPI;

/**
 *
 * @author lv
 */
public class Test {

    public static void main(String[] args) {

      //  Class2DB set = new Class2DB("E:\\xal\\source_code\\code\\modelDB\\data\\lattice_model_template.xlsx");
       // set.insertDB();

        //SeqMap2DB map2DB = new SeqMap2DB("E:\\xal\\source_code\\code\\modelDB\\data\\beamline_sequences.xlsx");
        //map2DB.insertDB();

       RfMap2DB map2DB = new RfMap2DB("E:\\xal\\source_code\\code\\modelDB\\data\\rf_gaps.xlsx");
       map2DB.insertDB();
       
    }
}
