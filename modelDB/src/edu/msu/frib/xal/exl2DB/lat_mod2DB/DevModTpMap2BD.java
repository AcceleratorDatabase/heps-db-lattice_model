/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.lat_mod2DB;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.openepics.model.api.ElementTypeAPI;
import org.openepics.model.api.ElementTypePropAPI;
import org.openepics.model.entity.ElementType;

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
            //int elementTypeId = (int) Double.parseDouble(dataMap.get("element_type_id").toString());
            String element_type=dataMap.get("element_type").toString();           
            ElementType et=new ElementTypeAPI().getElementTypeByType(element_type);
            
            String elementTypePropName = dataMap.get("element_type_prop_name").toString();
            String elementTypePropDesc = dataMap.get("element_type_prop_description").toString();
            String elementTypePropDefault = dataMap.get("element_type_prop_default").toString();
            String elementTypePropUnit = dataMap.get("element_type_prop_unit").toString();
            String elementTypePropDatatype = dataMap.get("element_type_prop_datatype").toString();
            if(et==null) {
                System.out.println("The element_type "+element_type+" does not exist. Please insert it first!");
            }
            ElementTypePropAPI elementTypePropAPI = new ElementTypePropAPI();
            elementTypePropAPI.setElementTypeProp(et, elementTypePropName, elementTypePropDesc, elementTypePropDefault, elementTypePropUnit, elementTypePropDatatype);
        }
    }
    
}
