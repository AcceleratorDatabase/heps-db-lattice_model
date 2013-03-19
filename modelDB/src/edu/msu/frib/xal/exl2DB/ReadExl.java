/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB;

import edu.msu.frib.xal.exl2DB.tools.FileTools;
import edu.msu.frib.xal.exl2DB.tools.FileTools;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lv
 * @author chu
 */
public class ReadExl {
    
    
    public static Workbook getWorkbook(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");
            return null;
        } else {
            FileInputStream inp = FileTools.getFileInputStream(filePath);
            Workbook wb = FileTools.getWorkbook(inp);
            return wb;
        }
    }
}
