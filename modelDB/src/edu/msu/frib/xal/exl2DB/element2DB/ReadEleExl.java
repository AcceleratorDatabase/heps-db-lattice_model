/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.element2DB;

import edu.msu.frib.xal.exl2DB.tools.FileTools;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ReadEleExl {

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    private String filePath;

    public ReadEleExl() {
    }

    public int getColNum() {
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");

        } else {
            FileInputStream inp = FileTools.getFileInputStream(filePath);
            Workbook wb = FileTools.getWorkbook(inp);
            Sheet sheet = wb.getSheetAt(0);

            return sheet.getRow(0).getLastCellNum();
        }
        return -1;
    }

    public int getLabelRowNum(String label) {
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");

        } else {
            FileInputStream inp = FileTools.getFileInputStream(filePath);
            Workbook wb = FileTools.getWorkbook(inp);
            Sheet sheet = wb.getSheetAt(0);
            
            if(label.indexOf(" ")>0){
                label=label.substring(0, label.indexOf(" "));
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
        }
        return -1;
    }

    /**
     *
     * @param label the column label
     * @param labelType Physical label, XAL label, or DB label
     * @return
     */
    public int getLabelColNum(String label, String labelType) {
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");

        } else {
            FileInputStream inp = FileTools.getFileInputStream(filePath);
            Workbook wb = FileTools.getWorkbook(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row;
            int rowNum = 0;
            if(labelType.toLowerCase().contains("physical")){
                rowNum=this.getLabelRowNum("XAL label")-1;
            }else if(labelType.toLowerCase().contains("xal")){
                rowNum=this.getLabelRowNum("XAL label");
            }else if(labelType.toLowerCase().contains("db")){
                rowNum=this.getLabelRowNum("DB label");
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
        }
        return -1;
    }

    public ArrayList getRowLabels(String rowLabel) {
        ArrayList rowLabels = new ArrayList();
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");
        } else {
            FileInputStream inp = FileTools.getFileInputStream(filePath);
            Workbook wb = FileTools.getWorkbook(inp);
            Sheet sheet = wb.getSheetAt(0);

            int startColNum=this.getLabelColNum("element", "Physical label");
            int totalColNum = this.getColNum();
            int rowNum = this.getLabelRowNum(rowLabel);
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
        }
        return rowLabels;
    }

    /**
     *
     * @param label the column label
     * @param labelType Physical label, XAL label, or DB label
     * @return
     */
    public ArrayList getColList(String label,String labelType) {
        ArrayList colList = new ArrayList();
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");
        } else {

            FileInputStream inp = FileTools.getFileInputStream(filePath);
            Workbook wb = FileTools.getWorkbook(inp);
            Sheet sheet = wb.getSheetAt(0);

            int colNum=this.getLabelColNum(label, labelType);
            int startRowNum = this.getLabelRowNum("Unit") + 1;
            for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
                Row row = (Row) rit.next();
                if (row.getRowNum() >= startRowNum) {
                    Cell cell = row.getCell(colNum);
                    if(Cell.CELL_TYPE_STRING==cell.getCellType()){
                        colList.add(cell.getStringCellValue());
                    }
                }
            }
        }
        return colList;
    }

    public ArrayList getDataList() {
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");
        } else {
            ArrayList dataList = new ArrayList();
            FileInputStream inp = FileTools.getFileInputStream(filePath);
            Workbook wb = FileTools.getWorkbook(inp);
            Sheet sheet = wb.getSheetAt(0);

            int startColNum = this.getLabelColNum("element","Physical label");
            int totalColNum = this.getColNum();
            int startRowNum = this.getLabelRowNum("Unit") + 1;
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
        return null;
    }
}
