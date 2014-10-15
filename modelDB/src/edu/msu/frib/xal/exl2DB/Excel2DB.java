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
import java.util.Date;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.ss.usermodel.Workbook;
import org.openepics.model.api.AcceleratorAPI;
import org.openepics.model.api.LatticeAPI;
import org.openepics.model.entity.Accelerator;
import org.openepics.model.entity.Lattice;

/**
 *
 * @author lv
 * @author chu
 */
public class Excel2DB {

    public static void exl2DB(String filePath) {
        ReadExl r = new ReadExl();
        Workbook wb = ReadExl.getWorkbook(filePath);

        String file_type = filePath.substring(filePath.lastIndexOf(".") + 1);
        if (!"xls".equals(file_type)) {
            System.out.println("Warning:Please insert the spreadsheet of .xls format");
        } else {
            String latticeName = r.getFileName(filePath);
            Lattice lattice = new LatticeAPI().getLatticeByName(latticeName);
            if (lattice != null) {
                System.out.println("The Lattice "+latticeName+" is already in the database! Please don't insert repeatedly! ");
            } 
            else {
                SummaryInformation si = r.getSummaryInformation(filePath);
                String created_by = si.getAuthor();
                Date create_date = si.getLastSaveDateTime();
                String accName = si.getTitle();
                String desc = si.getComments();
                Accelerator acc;
                acc = new AcceleratorAPI().getAcceleratorByName(accName);
                if (acc != null) {
                    System.out.println("The Accelerator "+accName+" exists in the database! Please don't insert repeatedly! ");
                } else {
                    // write to Accelerator Table
                    AcceleratorAPI accAPI = new AcceleratorAPI();
                    accAPI.setAccelerator(accName, desc, create_date);
                    acc = accAPI.getAcceleratorByName(accName);
                    //acc = new Accelerator();
                    //acc.setAcceleratorName(accName);
                    //acc.setAcceleratorDescription(desc);
                    //acc.setCreateDate(create_date);
                    
                    DevTpMap2DB.instDB(Data2Map.getMapData(ReadSheet.getDataList(wb, "device types")));
                    DevModTpMap2BD.instDB(Data2Map.getMapData(ReadSheet.getDataList(wb, "device-model types")));
                    SeqMap2DB seqMap2DB = new SeqMap2DB();
                    seqMap2DB.instDB(SeqData2Map.getMapData(ReadSheet.getDataList(wb, "beamline sequences")), acc, latticeName, created_by, create_date);
                    EncapData2DB.instDB(wb, "elements", latticeName, created_by, create_date);
                    RfMap2DB.instDB(Data2Map.getMapData(ReadSheet.getDataList(wb, "RF Gaps")));
                    BeamEncapData2DB.instDB(wb, "init conditions", latticeName, created_by, create_date);
                }
            }
        }
    }
}
