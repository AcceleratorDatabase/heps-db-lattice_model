/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.sequence2DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author lv
 * @author chu
 */
public class Data2Map {
    public String filePath;
   
    public  Data2Map() { 
    }

    public Data2Map(String file) {
        filePath = file;
    }
    
    public ArrayList getMapData(){
        ArrayList mapList=new ArrayList();
        ArrayList dataList=new ReadSeqExl(this.filePath).getDataList();
        ArrayList labelRow=(ArrayList) dataList.get(0);
        int colNum=labelRow.size();
        int i=0;
        Iterator it=dataList.iterator();
        while(it.hasNext()){
            ArrayList dataRow=(ArrayList) it.next();
            if(i>0){
                Map dataMap=new HashMap();
                for(int j=0;j<colNum;j++){
                   dataMap.put(labelRow.get(j), dataRow.get(j));
                }
                mapList.add(dataMap);
            }
            i++;
        }
        return mapList;
    }
}
