/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.element2DB;

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
    
    public String filePath;
    FileInputStream inp;
    Workbook wb;
    Sheet sheet;
    
    public ReadEleExl() {
    }
    
    public ReadEleExl(String file) {
        filePath = file;
    }
    
    public int getColNum() {
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sheet.getRow(0).getLastCellNum();
    }
    
    public int getXALLabelNum() {
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 0; i < 10; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (Cell.CELL_TYPE_BLANK != cell.getCellType()) {
                String value = cell.getStringCellValue();
                if (value.toLowerCase().contains("xal") && ("XAL label".toLowerCase().contains("label"))) {
                    return i;
                }
            }
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int getDBLabelNum() {
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 0; i < 10; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (Cell.CELL_TYPE_BLANK != cell.getCellType()) {
                String value = cell.getStringCellValue();
                if (value.toLowerCase().contains("db") && ("XAL label".toLowerCase().contains("label"))) {
                    return i;
                }
            }
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int getUnitLabelNum() {
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 0; i < 10; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            if (Cell.CELL_TYPE_BLANK != cell.getCellType()) {
                String value = cell.getStringCellValue();
                if (value.toLowerCase().contains("unit")) {
                    return i;
                }
            }
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int getEleColNum() {
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        Row row = sheet.getRow(this.getXALLabelNum() - 1);
        for (int i = 0; i < 10; i++) {
            Cell cell = row.getCell(i);
            if (Cell.CELL_TYPE_BLANK != cell.getCellType()) {
                String value = cell.getStringCellValue();
                if ("element".equals(value.toLowerCase())) {
                    return i;
                }
                
            }
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int getEleNameCol(){
        ArrayList DBLabels=this.getDBLabels();
        for(int i=0;i<DBLabels.size();i++){
            if("element/element_name".equals(DBLabels.get(i).toString())){
               return i;
            }
        }
        return -1;
    }
    
    public ArrayList getXALLabels() {
        ArrayList<String> xalLabels = new ArrayList();
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int startColNum = this.getEleColNum();
        int totalColNum = this.getColNum();
        int rowNum = this.getXALLabelNum();
        
        for (int i = startColNum; i < totalColNum; i++) {
            Cell cell = sheet.getRow(rowNum).getCell(i);
            if (cell != null) {
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    String label = cell.getStringCellValue();
                    if (!label.isEmpty()) {
                        xalLabels.add(label.replaceAll(" +", "_"));
                           
                    } else {
                        String label1 = sheet.getRow(rowNum - 1).getCell(i).getStringCellValue().toLowerCase();
                        xalLabels.add(label1.replaceAll(" +", "_"));
                       
                    }
                }
            } else {
                xalLabels.add("");
            }
            
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xalLabels;
    }
    
    public ArrayList getDBLabels() {
        ArrayList<String> DBLabels = new ArrayList();
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int startColNum = this.getEleColNum();
        int totalColNum = this.getColNum();
        int rowNum = this.getDBLabelNum();
        
        for (int i = startColNum; i < totalColNum; i++) {
           
            Cell cell = sheet.getRow(rowNum).getCell(i);
            if (cell != null) {
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    String label = cell.getStringCellValue();
                    DBLabels.add(label);
                }
            } else {
                DBLabels.add("");
            }
            
        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DBLabels;
    }
    
    public ArrayList getDataList() {
        ArrayList dataList = new ArrayList();
        if (inp == null) {
            try {
                inp = new FileInputStream(this.filePath);
                wb = WorkbookFactory.create(inp);
                sheet = wb.getSheetAt(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int startColNum = this.getEleColNum();
        int totalColNum = this.getColNum();
        int startRowNum = this.getUnitLabelNum() + 1;
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
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadEleExl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataList;
    }
}
