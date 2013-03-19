/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 */
public class ReadBeamSheet {
    
    /*public static ArrayList getRowLabels(Workbook wb, String sheetName, String rowLabel) {
        ArrayList rowLabels = new ArrayList();
        Sheet sheet = wb.getSheet(sheetName);
        int startColNum = getLabelColNum(wb, sheetName, "element", "Physical label");
        int totalColNum = getColNum(wb, sheetName);
        int rowNum = getLabelRowNum(wb, sheetName, rowLabel);
        for (int i = startColNum; i < totalColNum; i++) {
            Cell cell = sheet.getRow(rowNum).getCell(i);
            if (cell != null) {
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    String label = cell.getStringCellValue();
                    if (!label.isEmpty()) {
                        rowLabels.add(label.replaceAll(" +", "_"));

                    } else {
                        String label1 = sheet.getRow(rowNum - 1).getCell(i).getStringCellValue().toLowerCase();
                        rowLabels.add(label1.replaceAll(" +", "_"));

                    }
                }
            } else {
                rowLabels.add("");
            }

        }

        return rowLabels;
    }*/
}
