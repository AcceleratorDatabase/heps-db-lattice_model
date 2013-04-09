/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 * @author chu
 */
public class DataEncap {

    public static ArrayList getEncapData(Workbook wb, String sheetName) {


        ArrayList xalLabels = ReadBeamSheet.getRowLabels(wb, sheetName, "xal");
        ArrayList datatypeLabels = ReadBeamSheet.getRowLabels(wb, sheetName, "data type");
        ArrayList dataList = ReadBeamSheet.getDataList(wb, sheetName);

        ArrayList encapDataList = new ArrayList();

        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            ArrayList<BeamCell> rowClsList = new ArrayList();
            ArrayList oneRow = (ArrayList) it.next();

            int i = 0;
            Iterator xit = xalLabels.iterator();
            while (xit.hasNext()) {
                BeamCell cell = new BeamCell();
                String xal = (String) xit.next();
                if (!"".equals(xal)) {
                    if (!xal.toLowerCase().contains("species")) {
                        cell.setTableName("beam_parameter_prop");
                        int sepLine = xal.indexOf("_");
                        cell.setCategory(xal.substring(0, sepLine));
                        cell.setName(xal.substring(sepLine + 1));
                        cell.setDatatype(datatypeLabels.get(i).toString());
                        cell.setValue(oneRow.get(i));
                        rowClsList.add(cell);
                    }
                }
                i++;
            }
            encapDataList.add(rowClsList);
        }
        return encapDataList;
    }
    
}
