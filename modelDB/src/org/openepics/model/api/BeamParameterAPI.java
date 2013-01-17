/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamParameter;

/**
 *
 * @author chu
 * @author lv
 */
public class BeamParameterAPI {
    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * 
     * @param elm 
     */
    public void setBeamParametersForElement(String elm) {
        // TODO fill in code
    }

    /**
     * get all beam parameters for the specified element
     * @param elm element name
     * @return all beam parameters for the specified element
     */
    public Map<String, Object> getAllBeamParametersForElement(String elm) 
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException{
        Query q;
        q = em.createQuery("SELECT  bp FROM BeamParameter bp WHERE bp.elementId.elementName=:elName")
                .setParameter("elName", elm);
        List<BeamParameter> bps = q.getResultList();
        if (bps.isEmpty()) {
            return null;
        } else {
            //to remove the first field "serialVersionUID" 
            if (bps.size() > 1) {
                System.out.println("warning:there are more than 1 bean_parameter for the specified element!");
            } else {
                BeamParameter bp = bps.get(0);
                Map<String, Object> map = new HashMap<>();
                Field[] fields = bp.getClass().getDeclaredFields();
                int l = fields.length;
                //System.out.println(l);
                for (int i = 1; i < l; i++) {
                    String fieldName = fields[i].getName();
                    String getter = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = bp.getClass().getMethod(getter, new Class[]{});
                    Object value = null;
                    try {
                        value = method.invoke(bp, new Object[]{});
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(BeamParameterAPI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    map.put(fieldName, value);
                }
                return map;
            }
        }
        return null;
    }
    
}