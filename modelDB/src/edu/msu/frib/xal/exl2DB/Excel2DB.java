/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB;

import edu.msu.frib.xal.exl2DB.beam2DB.BeamEncapData2DB;
import edu.msu.frib.xal.exl2DB.ele2DB.EncapData2DB;
import edu.msu.frib.xal.exl2DB.lat_mod2DB.Data2Map;
import edu.msu.frib.xal.exl2DB.lat_mod2DB.DevModTpMap2BD;
import edu.msu.frib.xal.exl2DB.lat_mod2DB.DevTpMap2DB;
import edu.msu.frib.xal.exl2DB.lat_mod2DB.ReadSheet;
import edu.msu.frib.xal.exl2DB.lat_mod2DB.RfMap2DB;
import edu.msu.frib.xal.exl2DB.lat_mod2DB.SeqData2Map;
import edu.msu.frib.xal.exl2DB.lat_mod2DB.SeqMap2DB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 * @author chu
 */
public class Excel2DB {

    public static void exl2DB(String filePath,String latticeName) {
        Workbook wb = ReadExl.getWorkbook(filePath);
        
        DevTpMap2DB.instDB(Data2Map.getMapData(ReadSheet.getDataList(wb, "device types")));
        DevModTpMap2BD.instDB(Data2Map.getMapData(ReadSheet.getDataList(wb, "device-model types")));
        SeqMap2DB seqMap2DB = new SeqMap2DB();
        seqMap2DB.instDB(SeqData2Map.getMapData(ReadSheet.getDataList(wb, "beamline sequences")),latticeName);                                 
        EncapData2DB.instDB(wb, "elements", latticeName);     
        RfMap2DB.instDB(Data2Map.getMapData(ReadSheet.getDataList(wb, "RF Gaps")));
        BeamEncapData2DB.instDB(wb, "init conditions",latticeName);
    }
}
