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
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementType;

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
     * get all elements within the specified sequence
     * 
     * @param seq sequence name
     * @return elements within the specified sequence
     */
    public static List<Element> getAllElementForSequence(String seq) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e JOIN e.sequenceId s "
                + "WHERE s.sequenceName = :sequenceName").setParameter("sequenceName", seq);
        List<Element> eList = q.getResultList();
        return eList;
    }
    
    public void setElement() {
        // TODO save an individual element's model data
        Element e = new Element();
        Date date = new Date();
        e.setInsertDate(date);
//        e.setBeamParameterCollection(null);
        
    }
    
    /**
     * Set a new element type
     * @param elem_type element type
     * @param elem_type_desc description for this element type
     */
    public static void setElementType(String elem_type, String elem_type_desc) {
        ElementType et = new ElementType();
        et.setElementType(elem_type);
        et.setElementTypeDescription(elem_type_desc);
        em.getTransaction().begin();
        em.persist(et);
        em.getTransaction().commit();
    }
    
    
}
