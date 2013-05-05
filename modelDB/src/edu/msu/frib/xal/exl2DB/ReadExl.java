/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB;

import edu.msu.frib.xal.exl2DB.tools.FileTools;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;

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

    public static void getProperty(String filePath) {
        try {
            final String filename = filePath;
            POIFSReader r = new POIFSReader();
            r.registerListener(new MyPOIFSReaderListener(),
                    "\005SummaryInformation");
            r.read(new FileInputStream(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadExl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadExl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static class MyPOIFSReaderListener implements POIFSReaderListener {

        public void processPOIFSReaderEvent(POIFSReaderEvent event) {
            SummaryInformation si = null;
            try {
                si = (SummaryInformation) PropertySetFactory.create(event.getStream());
            } catch (Exception ex) {
                throw new RuntimeException("Property set stream \""
                        + event.getPath() + event.getName() + "\": " + ex);
            }
            final String title = si.getTitle();
            if (title != null) {
                System.out.println("Title: \"" + title + "\"");
            } else {
                System.out.println("Document has no title.");
            }
            System.out.println(si.getAuthor());
        }
    }
}
