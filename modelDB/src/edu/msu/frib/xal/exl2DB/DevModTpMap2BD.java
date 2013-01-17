/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.openepics.model.api.ElementTypePropAPI;

/**
 *
 * @author lv
 * @author chu
 */
public class DevModTpMap2BD {

    public static void instDB(ArrayList mapData) {
        Iterator it = mapData.iterator();
        while (it.hasNext()) {
            Map dataMap = (Map) it.next();
            int elementTypeId = (int) Double.parseDouble(dataMap.get("element_type_id").toString());
            String elementTypePropName = dataMap.get("element_type_prop_name").toString();
            String elementTypePropDesc = dataMap.get("element_type_prop_description").toString();
            String elementTypePropDefault = dataMap.get("element_type_prop_default").toString();
            String elementTypePropUnit = dataMap.get("element_type_prop_unit").toString();
            String elementTypePropDatatype = dataMap.get("element_type_prop_datatype").toString();
            ElementTypePropAPI elementTypePropAPI = new ElementTypePropAPI();
            elementTypePropAPI.setElementTypeProp(null, elementTypePropName, elementTypePropDesc, elementTypePropDefault, elementTypePropUnit, elementTypePropDatatype);
        }
    }
}
