/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.element2DB;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author lv
 * @author chu
 */
public class Data2Class {

    public String filePath;

    public Data2Class() {
    }

    public Data2Class(String path) {
        filePath = path;
    }

    public ArrayList getClsData() {
        ReadEleExl read = new ReadEleExl(this.filePath);
        ArrayList XALLabels = read.getXALLabels();
        ArrayList DBLabels = read.getDBLabels();
        ArrayList dataList = read.getDataList();

        ArrayList dataClsList = new ArrayList();

        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            ArrayList<CellProperty> rowClsList = new ArrayList();

            ArrayList oneRow = (ArrayList) it.next();
            int i = 0;
            Iterator dbit = DBLabels.iterator();
            while (dbit.hasNext()) {
                Object object = dbit.next();
                if (!"".equals(object)) {
                    CellProperty cellProp = new CellProperty();
                    String dbCell = (String) object;
                    int dbSepLine = dbCell.indexOf("/");
                    String firstStr = dbCell.substring(0, dbSepLine);
                    String secStr = dbCell.substring(dbSepLine + 1);
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
                    } else {
                        cellProp.setTableName(firstStr);
                        cellProp.setName(secStr);
                        cellProp.setValue(oneRow.get(i));
                    }

                    rowClsList.add(cellProp);
                }
                i++;
            }
            dataClsList.add(rowClsList);
        }

        return dataClsList;
    }
}
