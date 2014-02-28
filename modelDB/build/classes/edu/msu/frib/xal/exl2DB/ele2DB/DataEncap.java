/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.ele2DB;

import edu.msu.frib.xal.exl2DB.lat_mod2DB.ReadComSheet;
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

        ArrayList XALLabels = ReadComSheet.getRowLabels(wb, sheetName, "XAL label","element", "Physical label");
        ArrayList DBLabels = ReadComSheet.getRowLabels(wb, sheetName, "DB label","element", "Physical label");
        ArrayList dataList = ReadComSheet.getDataList(wb, sheetName,"element", "Physical label");
        //System.out.println("*********"+DBLabels.size());

        ArrayList encapDataList = new ArrayList();

        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            ArrayList<ElementCell> rowClsList = new ArrayList();
            ArrayList oneRow = (ArrayList) it.next();

            int i = 0;
            Iterator dbit = DBLabels.iterator();
            while (dbit.hasNext()) {
                Object object = dbit.next();

                if (!"".equals(object)) {
                    //System.out.println(object);
                    ElementCell cellProp = new ElementCell();
                    String dbCell = (String) object;
                    //System.out.println(dbCell);
                    int dbSepLine = dbCell.indexOf("/");
                    String firstStr = dbCell.substring(0, dbSepLine);
                    String secStr = dbCell.substring(dbSepLine + 1);
                    if (!("".equals(oneRow.get(i)) || " ".equals(oneRow.get(i)))) {
                        if ("element_prop".equals(firstStr)) {
                            String xalLabel = (String) XALLabels.get(i);
                            int xalSepLine = xalLabel.indexOf("_");
                            if (xalSepLine > -1) {
                                cellProp.setCategory(xalLabel.substring(0, xalSepLine));
                                cellProp.setName(xalLabel.substring(xalSepLine + 1));

                            } else {
                                cellProp.setName(xalLabel);
                            }
                            cellProp.setTableName("element_prop");
                            cellProp.setType(secStr);
                            cellProp.setValue(oneRow.get(i));
                        }
                        if (!"element_prop".equals(firstStr)) {
                            cellProp.setTableName(firstStr);
                            cellProp.setName(secStr);
                            cellProp.setValue(oneRow.get(i));
                        }

                        rowClsList.add(cellProp);
                    }
                }

                i++;
            }
            encapDataList.add(rowClsList);
        }

        return encapDataList;
    }
}
