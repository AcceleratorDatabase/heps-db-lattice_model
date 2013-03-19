/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.mod_para2DB;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 */
public class ReadBeamSheet {

    public static int getColNum(Workbook wb, String sheetName) {
        Sheet sheet = wb.getSheet(sheetName);
        return sheet.getRow(0).getLastCellNum();
    }

    /*
     *  @param rowLabel:physical, xal, unit, data type
     */
    public static ArrayList getRowLabels(Workbook wb, String sheetName, String rowLabel) {
        ArrayList rowLabels = new ArrayList();
        Sheet sheet = wb.getSheet(sheetName);
        int rowNum = 0;
        if (rowLabel.toLowerCase().contains("physical")) {
            rowNum = 0;
        }
        if (rowLabel.toLowerCase().contains("xal")) {
            rowNum = 1;
        }
        if (rowLabel.toLowerCase().contains("unit")) {
            rowNum = 2;
        } else if (rowLabel.toLowerCase().contains("data") && rowLabel.toLowerCase().contains("type")) {
            rowNum = 3;
        }
        for (int i = 1; i < getColNum(wb, sheetName); i++) {
            Cell cell = sheet.getRow(rowNum).getCell(i);
            if (cell != null) {
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    String label = cell.getStringCellValue();
                    if (!label.isEmpty()) {
                        rowLabels.add(label.replaceAll(" +", "_"));
                    }
                }
            } else {
                rowLabels.add("");
            }
        }
        return rowLabels;
    }
   
    public static ArrayList getDataList(Workbook wb, String sheetName) {
        ArrayList dataList = new ArrayList();
        Sheet sheet = wb.getSheet(sheetName);
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
            Row row = (Row) rit.next();
            if (row.getRowNum() > 3) {
                ArrayList oneRow = new ArrayList();
                for (int i = 1; i < getColNum(wb, sheetName); i++) {
                    Object o = "";
                    try {
                        Cell cell = row.getCell(i);

                        if (!"".equals(cell) && cell != null) {
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_STRING:
                                    //o = cell.getStringCellValue().replaceAll(" +", "_");
                                    o = cell.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    o = cell.getNumericCellValue();
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    o = cell.getBooleanCellValue();
                                    break;
                                case Cell.CELL_TYPE_FORMULA:
                                    o = cell.getCellFormula();
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    o = "";
                                    break;
                                case Cell.CELL_TYPE_ERROR:
                                    System.out.println("Error");
                                    break;
                            }
                        } else {
                            o = "";
                        }
                    } catch (NullPointerException e) {
                        o = "";
                    }
                    oneRow.add(o);
                }
                dataList.add(oneRow);
            }
        }

        return dataList;
    }
}
