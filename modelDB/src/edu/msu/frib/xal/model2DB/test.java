/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

/**
 *
 * @author lv
 */
public class test {
    public static void main(String[] args) {
       //ArrayList contents=ReadExcel.readExcel("E:\\study\\Apache POI\\lattice_model_template.xlsx");
     // HashMap map=(HashMap) contents.get(0);
       //System.out.println(contents.size());
       //System.out.println(map.get("element_name"));
      //  XLS2DB.write2DB("E:\\study\\Apache POI\\lattice_model_template.xlsx");
       Exl2DB.write2DB("E:\\study\\Apache POI\\lattice_model_template.xlsx");
       // Tool.strContain("xal_label",XLS2DB.WNDS);
      // System.out.println("channelsuite_transform_name".indexOf("_"));
      // System.out.println("channelsuite_transform_name".substring(13));
       // System.out.println(Double.parseDouble("0.26".toString()));
        
    }
}
