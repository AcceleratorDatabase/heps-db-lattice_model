/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB;

import edu.msu.frib.xal.exl2DB.element2DB.Class2DB;
import edu.msu.frib.xal.exl2DB.sequence2DB.Data2Map;
import edu.msu.frib.xal.exl2DB.sequence2DB.Map2DB;
import edu.msu.frib.xal.exl2DB.sequence2DB.ReadSeqExl;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author lv
 */
public class Test {

    public static void main(String[] args) {
   
        Class2DB set=new Class2DB("E:\\xal\\source_code\\code\\modelDB\\data\\lattice_model_template.xlsx");
        set.insertDB();
       // Map2DB map2DB=new Map2DB("E:\\xal\\source_code\\code\\modelDB\\data\\beamline_sequences.xlsx");
       // map2DB.insertDB();
        
    }
}