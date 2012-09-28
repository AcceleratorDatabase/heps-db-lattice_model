/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.ElementProp;

/**
 *
 * @author chu
 */
public class ElementPropAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * get magnet attributes for the specified element
     * 
     * @param elm element name
     * @return magnet attributes for the specified element
     */
    public static Map getMagnetAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"magnet\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
            if (ep.getElementPropInt() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropInt());
            }
            if (ep.getElementPropString() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropString());
            }
        }
        
        return atts;
    }
    
    /**
     * get aperture attributes for the specified element
     * 
     * @param elm element name
     * @return aperture attributes for the specified element
     */
    public static Map getApertureAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"aperture\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    }
    
    /**
     * get alignment attributes for the specified element
     * 
     * @param elm element name
     * @return alignment attributes for the specified element
     */
    public static Map getAlignmentAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"align\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    }
    
    /**
     * get BPM attributes for the specified element
     * 
     * @param elm element name
     * @return bpm attributes for the specified element 
     */
    public static Map getBpmAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"bpm\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    } 
    
    /**
     * get rfgap attributes for the specified element
     * 
     * @param elm element name
     * @return rfgap attributes for the specified element
     */
    public static Map getRfgapAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"rfgap\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            }
        }
        
        return atts;
    } 

    public void setElementProperty(String propCategory, String propName, Object prop) {
        // TODO fill in code
        
    }
    
}
