/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.sequence2DB;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author lv
 * @author chu
 */
public class ReadSeqExl {

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    FileInputStream inp;
    Workbook wb;
    Sheet sheet;

    public ReadSeqExl() {
    }

    public ArrayList getDataList() {
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");
            return null;
        } else {
            ArrayList dataList = new ArrayList();
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
                Row row = (Row) rit.next();
                ArrayList oneRow = new ArrayList();
                for (Iterator<Cell> cit = row.iterator(); cit.hasNext();) {
                    Cell cell = cit.next();
                    Object o = "";
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
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
                    oneRow.add(o);
                }
                dataList.add(oneRow);
            }
            return dataList;
        }
    }
}