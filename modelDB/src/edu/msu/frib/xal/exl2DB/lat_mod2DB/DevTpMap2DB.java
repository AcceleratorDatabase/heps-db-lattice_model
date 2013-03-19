/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.lat_mod2DB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.openepics.model.api.ElementTypeAPI;
import org.openepics.model.entity.ElementType;

/**
 *
 * @author lv
 * @author chu
 */
public class DevTpMap2DB {

    public static void instDB(ArrayList mapData) {
        Iterator it = mapData.iterator();
        while (it.hasNext()) {
            Map dataMap = (Map) it.next();
            String type = dataMap.get("element_type").toString();
            String desc = dataMap.get("element_type_description").toString();
            ElementTypeAPI elementTypeAPI = new ElementTypeAPI();
            ElementType et = elementTypeAPI.getElementTypeByType(type);
            if (et != null) {
                System.out.println("The ElementType " + type + " is already in the database! Please don't insert repeatedly!");
            } else {
                elementTypeAPI.setElementType(type, desc);
            }
        }
    }
}
