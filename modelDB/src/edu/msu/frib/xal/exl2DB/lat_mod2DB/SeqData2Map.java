/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.lat_mod2DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author lv
 */
public class SeqData2Map {
     public static ArrayList getMapData(ArrayList dataList) {
        ArrayList mapList = new ArrayList();
        ArrayList labelRow = (ArrayList) dataList.get(0);
        int colNum = labelRow.size()-1;
        int i = 0;
        Iterator it = dataList.iterator();
        while (it.hasNext()) {
            ArrayList dataRow = (ArrayList) it.next(); 
           // System.out.println(dataRow);
            if (i > 1) {              
                Map dataMap = new HashMap();
                for (int j = 0; j < colNum; j++) {                    
                    dataMap.put(labelRow.get(j), dataRow.get(j));                   
                }
                mapList.add(dataMap);
            }
            i++;
        }
        return mapList;
    }
}
