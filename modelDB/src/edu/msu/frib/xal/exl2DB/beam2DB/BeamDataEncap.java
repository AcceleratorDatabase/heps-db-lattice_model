/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.beam2DB;

import edu.msu.frib.xal.exl2DB.lat_mod2DB.ReadComSheet;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 * @author chu
 */
public class BeamDataEncap {

    public static ArrayList getEncapData(Workbook wb, String sheetName) {

        ArrayList xalLabels = ReadComSheet.getRowLabels(wb, sheetName, "xal", "model_line", "phsical");
        ArrayList DBLabels = ReadComSheet.getRowLabels(wb, sheetName, "DB", "model_line", "phsical");
        ArrayList dataList = ReadComSheet.getDataList(wb, sheetName, "model_line", "phsical");

        ArrayList encapDataList = new ArrayList();

        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            ArrayList<BeamCell> rowClsList = new ArrayList();
            ArrayList oneRow = (ArrayList) it.next();

            int i = 0;

            Iterator dbit = DBLabels.iterator();
            while (dbit.hasNext()) {
                String xalLabel = (String) xalLabels.get(i);
                Object object = dbit.next();
                if (!"".equals(object)) {
                    BeamCell cellProp = new BeamCell();
                    String dbCell = (String) object;
                    int dbSepLine = dbCell.indexOf("/");
                    String firstStr = dbCell.substring(0, dbSepLine);
                    String secStr = dbCell.substring(dbSepLine + 1);

                    if (!("".equals(oneRow.get(i)) || " ".equals(oneRow.get(i)))) {
                        if ("beam_parameter_prop".equals(firstStr)) {
                            int xalSepLine = xalLabel.indexOf("_");
                            if (xalSepLine > -1) {
                                cellProp.setCategory(xalLabel.substring(0, xalSepLine));
                                cellProp.setName(xalLabel.substring(xalSepLine + 1));
                            }
                            String datatype = secStr.substring(secStr.lastIndexOf("_") + 1);
                            cellProp.setTableName("beam_parameter_prop");
                            cellProp.setDatatype(datatype);
                            cellProp.setValue(oneRow.get(i));
                        } else if ("model".equals(firstStr)) {
                            cellProp.setTableName("model");
                            cellProp.setValue(oneRow.get(i));
                        } else if ("particle_type".equals(firstStr)) {
                            cellProp.setTableName("particle_type");
                            cellProp.setName(secStr);
                            cellProp.setValue(oneRow.get(i));
                        }
                    }
                    rowClsList.add(cellProp);
                }
                i++;
            }

            encapDataList.add(rowClsList);
        }


        return encapDataList;
    }
}
