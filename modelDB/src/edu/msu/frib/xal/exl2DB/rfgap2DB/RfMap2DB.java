/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.rfgap2DB;

import edu.msu.frib.xal.exl2DB.sequence2DB.Data2Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.RfGapAPI;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.RfGap;

/**
 *
 * @author lv
 * @author chu
 *
 */
public class RfMap2DB {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();
    @PersistenceContext
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public RfMap2DB() {
    }

    public void insertDB() {
        if (this.filePath == null || "".equals(this.filePath)) {
            System.out.println("Warning: Please assign the specific path of the spreadsheet!");
        } else {
            Data2Map data2Map = new Data2Map();
            data2Map.setFilePath(this.getFilePath());
            ArrayList mapList = data2Map.getMapData();
            Iterator it = mapList.iterator();
            while (it.hasNext()) {

                String cavity_name = null;
                String gap_name = null;
                double pos = 0;
                double TTF = 0;
                double ampFactor = 0;
                int endCell_ind = 0;
                double gapOffset = 0;
                double len = 0;
                double phaseFactor = 0;

                Element cavity;
                Map dataMap = (Map) it.next();
                Iterator it1 = dataMap.entrySet().iterator();
                while (it1.hasNext()) {
                    Map.Entry entry = (Map.Entry) it1.next();
                    if (entry.getKey().toString().toLowerCase().contains("cavity") && entry.getKey().toString().toLowerCase().contains("name")) {
                        cavity_name = entry.getValue().toString();
                    }
                    if (entry.getKey().toString().toLowerCase().contains("gap") && entry.getKey().toString().toLowerCase().contains("name")) {
                        gap_name = entry.getValue().toString();
                    }
                    if (entry.getKey().toString().toLowerCase().contains("pos")) {
                        pos = Double.parseDouble(entry.getValue().toString());
                    }

                    if (entry.getKey().toString().toLowerCase().contains("ttf")) {
                        TTF = Double.parseDouble(entry.getValue().toString());
                    }
                    if (entry.getKey().toString().toLowerCase().contains("ampfactor")) {
                        ampFactor = Double.parseDouble(entry.getValue().toString());
                    }
                    if (entry.getKey().toString().toLowerCase().contains("end") && entry.getKey().toString().toLowerCase().contains("cell")
                            && entry.getKey().toString().toLowerCase().contains("ind")) {
                        endCell_ind = (int) Double.parseDouble(entry.getValue().toString());
                    }
                    if (entry.getKey().toString().toLowerCase().contains("gap") && entry.getKey().toString().toLowerCase().contains("offset")) {
                        gapOffset = Double.parseDouble(entry.getValue().toString());
                    }
                    if (entry.getKey().toString().toLowerCase().contains("len")) {
                        len = Double.parseDouble(entry.getValue().toString());
                    }
                    if (entry.getKey().toString().toLowerCase().contains("phase") && entry.getKey().toString().toLowerCase().contains("factor")) {
                        phaseFactor = Double.parseDouble(entry.getValue().toString());
                    }



                }
                RfGap rfGap = RfGapAPI.getRfGapByName(gap_name);
                cavity = ElementAPI.getElementByName(cavity_name);
                if (rfGap != null) {
                    System.out.println("The RfGap " + gap_name + " is already in the database! Please don't insert repeatedly!");
                }
                if (cavity == null) {
                    System.out.println("The cavity " + cavity_name + " doesn't exist. Please insert it firse!");
                }
                if (rfGap == null && cavity != null) {
                    RfGapAPI.setRfGap(gap_name, pos, TTF, ampFactor, endCell_ind, gapOffset, len, phaseFactor, cavity);
                }
            }
            em.close();
            emf.close();
        }
    }
}
