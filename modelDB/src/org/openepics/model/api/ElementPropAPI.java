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
import org.openepics.model.entity.Element;
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
     * get the physics name for the element
     * 
     * @param elm element name
     * @return physics name for the element
     */
    public String getPidForElement(String elm) {
        String pid = "";
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.elementPropName = \"pid\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            pid = ep.getElementPropString();
        }
        
        return pid;
    }
    
    /**
     * get magnet attributes for the specified element
     * 
     * @param elm element name
     * @return magnet attributes for the specified element
     */
    public Map getMagnetAttributesForElement(String elm) {
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
    public Map getApertureAttributesForElement(String elm) {
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
    public Map getAlignmentAttributesForElement(String elm) {
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
    public Map getBpmAttributesForElement(String elm) {
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
    public Map getRfcavityAttributesForElement(String elm) {
        HashMap<String, Object> atts = new HashMap<>();
        
        Query q;
        q = em.createQuery("SELECT ep from ElementProp ep "
                + "WHERE ep.elementId.elementName = :elementname "
                + "AND ep.propCategory = \"rfcavity\"")
                .setParameter("elementname", elm);
        List<ElementProp> attrList = q.getResultList();
        
        Iterator<ElementProp> it = attrList.iterator();
        while (it.hasNext()) {
            ElementProp ep = it.next();
            if (ep.getElementPropDouble() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropDouble());
            } else if (ep.getElementPropString() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropString());
            } else if (ep.getElementPropInt() != null) {
                atts.put(ep.getElementPropName(), ep.getElementPropInt());
            }
        }
        
        return atts;
    } 

    /**
     * set ElementProp for the specified Element
     * 
     * @param e Element 
     * @param propCategory prop category
     * @param propName elment prop name
     * @param prop elment prop value 
     * @return initialized ElementProp
     */
    public ElementProp setElementProperty(Element e, String propCategory, String propName, Object prop) {
        ElementProp ep = new ElementProp();
        ep.setElementId(e);
        ep.setPropCategory(propCategory);
        ep.setElementPropName(propName);
        try{
           double dval=Double.parseDouble(prop.toString());
           int ival=(int)dval;
           if(Math.abs(dval-ival)<Math.pow(10, -10)) ep.setElementPropInt(ival);
           else ep.setElementPropDouble(dval);
        }catch(Exception e1){
           ep.setElementPropString(prop.toString());
        }
        return ep;
       
        
    }
    
    public int getMaxId(){
        Query q;
        q = em.createQuery("SELECT MAX(ep.elementPropId) FROM ElementProp ep");
        List<Integer> idList = q.getResultList();
        if(idList.get(0)==null) {
            return 0;
        }
        else {
            return idList.get(0);
        }
        
    }
    
}
