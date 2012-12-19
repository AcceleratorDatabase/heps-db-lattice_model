/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.ele2DB;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 * @author chu
 */
public class ReadEleSheet {

    public static int getColNum(Workbook wb, String sheetName) {
        Sheet sheet = wb.getSheet(sheetName);
        return sheet.getRow(0).getLastCellNum();
    }

    public static int getLabelRowNum(Workbook wb, String sheetName, String label) {
        Sheet sheet = wb.getSheet(sheetName);
        if (label.indexOf(" ") > 0) {
            label = label.substring(0, label.indexOf(" "));
        }
        for (int i = 0; i < 10; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (Cell.CELL_TYPE_BLANK != cell.getCellType()) {
                String value = cell.getStringCellValue();
                if (value.toLowerCase().contains(label.toLowerCase())) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     *
     * @param label the column label
     * @param labelType Physical label, XAL label, or DB label
     * @return
     */
    public static int getLabelColNum(Workbook wb, String sheetName, String label, String labelType) {
        Sheet sheet = wb.getSheet(sheetName);
        Row row;
        int rowNum = 0;
        if (labelType.toLowerCase().contains("physical")) {
            rowNum = getLabelRowNum(wb, sheetName, "XAL label") - 1;
        } else if (labelType.toLowerCase().contains("xal")) {
            rowNum = getLabelRowNum(wb, sheetName, "XAL label");
        } else if (labelType.toLowerCase().contains("db")) {
            rowNum = getLabelRowNum(wb, sheetName, "DB label");
        }
        row = sheet.getRow(rowNum);
        int i = 0;
        Iterator it = row.iterator();
        while (it.hasNext()) {
            Cell cell = (Cell) it.next();
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                String s = cell.getStringCellValue();
                if (label.toLowerCase().equals(s.toLowerCase())) {
                    return i;
                }
            }
            i++;
        }

        return -1;
    }

    public static ArrayList getRowLabels(Workbook wb, String sheetName, String rowLabel) {
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
    }

    /**
     *
     * @param label the column label
     * @param labelType Physical label, XAL label, or DB label
     * @return
     */
    public static ArrayList getColList(Workbook wb, String sheetName, String label, String labelType) {
        ArrayList colList = new ArrayList();
        Sheet sheet = wb.getSheet(sheetName);
        int colNum = getLabelColNum(wb, sheetName, label, labelType);
        int startRowNum = getLabelRowNum(wb, sheetName, "Unit") + 1;
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
            Row row = (Row) rit.next();
            if (row.getRowNum() >= startRowNum) {
                Cell cell = row.getCell(colNum);
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    colList.add(cell.getStringCellValue());
                }
            }
        }

        return colList;
    }

    public static ArrayList getDataList(Workbook wb, String sheetName) {
        ArrayList dataList = new ArrayList();
        Sheet sheet = wb.getSheet(sheetName);
        int startColNum = getLabelColNum(wb, sheetName, "element", "Physical label");
        int totalColNum = getColNum(wb, sheetName);
        int startRowNum = getLabelRowNum(wb, sheetName, "Unit") + 1;
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
            Row row = (Row) rit.next();

            if (row.getRowNum() >= startRowNum) {
                ArrayList oneRow = new ArrayList();
                for (int i = startColNum; i < totalColNum; i++) {
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
