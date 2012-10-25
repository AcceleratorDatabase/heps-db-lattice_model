/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.model2DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author lv
 */
public class ReadExl {

    /**
     * read the spreadsheet, and return the arrayList which is made up of
     * hashMap.Each hashMap contains the contents of an entire row , in which,
     * the key is xal_name, and the value is the corresponding value.
     *
     * @param file the path of the specified spreadsheet, for
     * example,"E:\\xal\\lattice_model_template.xlsx"
     * @return the spreadsheet contents in the format of arrayList,which is made
     * up of hashMap. Each hashMap contains the contents of one row.
     */
    public static ArrayList readExcel(String file) {
        ArrayList<HashMap> contents = new ArrayList<>();
        ArrayList rowListSet = new ArrayList();   //存储多个arraylist，其中每个arrayList是一行的内容
        int rowNum = 0;
        int colNum = 0;
        FileInputStream inp = null;
        Workbook wb;
        Sheet sheet = null;
        try {
            inp = new FileInputStream(file);
            wb = WorkbookFactory.create(inp);
            sheet = wb.getSheetAt(0);
            rowNum = sheet.getLastRowNum();
            colNum = sheet.getRow(0).getLastCellNum();
        } catch (InvalidFormatException | IOException ex) {
            Logger.getLogger(ReadExl.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
            // 迭代行   
            ArrayList rowList = new ArrayList();   //存储一行的内容
            Row row = (Row) rit.next();
            for (int c = 0; c < colNum; c++) {
                Object o = "";
                try {
                    Cell cell = row.getCell(c);
                    //  如果该行没有值，就赋给它空值""
                    if (!"".equals(cell) && cell != null) {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                o = cell.getStringCellValue().replaceAll(" +", "_");  //将空格转换成下划线
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
                rowList.add(o);
            }
            rowListSet.add(rowList);

        }
        try {
            inp.close();
        } catch (IOException ex) {
            Logger.getLogger(ReadExl.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList xalLabels = new ArrayList();
        ArrayList xalList = (ArrayList) rowListSet.get(1); //第二行是xal_label
        ArrayList phyList = (ArrayList) rowListSet.get(0);//第一行是物理人员需要的label
        //如果第二行没有，就使用第一行的label，并转换成小写
        for (int t = 0; t < colNum; t++) {
            if ("".equals(xalList.get(t))) {
                xalLabels.add(phyList.get(t).toString().toLowerCase());
            } else {
                xalLabels.add(xalList.get(t));
            }
        }
       //第三行是单位，所以真正的值是从第四行开始
        for (int i = 3; i < rowNum; i++) {
            ArrayList rowList2 = (ArrayList) rowListSet.get(i);
            HashMap map = new HashMap();
            for (int j = 0; j < colNum; j++) {
                //map的key是xal_label，value是对应的取值
                map.put(xalLabels.get(j), rowList2.get(j));
            }
            contents.add(map);
        }
        return contents;

    }
}