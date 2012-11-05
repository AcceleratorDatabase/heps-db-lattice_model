/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;

/**
 *
 * @author chu
 */
public class ElementAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * get all properties for the specified element
     * @param elementName element name
     * @return all properties for the specified element
     */
    public static List<ElementProp> getAllPropertiesForElement(String elementName) {
        Query q;
        q = em.createQuery("SELECT ep FROM ElementProp ep JOIN ep.elementId e "
                + "WHERE e.elementName = :elementName").setParameter("elementName", elementName);
        List<ElementProp> epList = q.getResultList();
        return epList;
    }
    
     /**
     * get Element for the specified element name
     * @param name element name
     * @return element for the specified element name
     */
       public static Element getElementByName(String name) {
        Query q;
        q = em.createNamedQuery("Element.findByElementName").setParameter("elementName", name);
        List<Element> eList = q.getResultList();
        if(eList.isEmpty()) {
            return null;
        }
        else{
            return eList.get(0);
        }
    }

    
    /**
     * Insert a new element into database
     * @param name 
     * @param order
     * @param s
     * @param len
     * @param dx
     * @param dy
     * @param dz
     * @param pitch
     * @param yaw
     * @param roll
     * @param pos 
     * @param sequence_name 
     */
    public void setElement(String name, int order, double s, 
            double len, double dx, double dy, double dz, double pitch, double yaw, double roll, 
            double pos, String sequence_name) {
        // first, check if this element has already in the DB
        if (ElementAPI.getElementByName(name) == null) {
            Element e = new Element();
            Date date = new Date();
            e.setInsertDate(date);
            e.setCreatedBy(System.getProperty("user.name"));
            e.setDx(dx);
            e.setDy(dy);
            e.setDz(dz);
            e.setPitch(pitch);
            e.setYaw(yaw);
            e.setRoll(roll);
            e.setPos(pos);

            // find out the beamline sequence id
            BeamlineSequence bls = BeamlineSequenceAPI.getSequenceByName(sequence_name);
            e.setBeamlineSequenceId(bls);
        }
    }   
    
     /**
     * set element install error for the specified element
     * @param e element 
     * @param dx
     * @param dy
     * @param dz
     * @param pitch
     * @param yaw
     * @param roll
    */
    public static  void setEleInsErr(Element e, double dx, double dy, double dz, double pitch, double yaw, double roll) {
        // TODO save an individual element's model data
        
        Date date = new Date();
        e.setInsertDate(date);
        e.setCreatedBy(System.getProperty("user.name"));
        e.setDx(dx);
        e.setDy(dy);
        e.setDz(dz);
        e.setPitch(pitch);
        e.setYaw(yaw);
        e.setRoll(roll);
     }  
    
}
