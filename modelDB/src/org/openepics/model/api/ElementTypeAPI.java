/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.ElementType;

/**
 *
 * @author chu
 */
public class ElementTypeAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Get all element types.
     * @return all element types
     */
    public static List<ElementType> getAllElementTypes() {
        Query q;
        q = em.createNamedQuery("ElementType.findAll");
        List<ElementType> typeList = q.getResultList();

        return typeList;        
    }
    
    /**
     * set element type definition in DB
     * 
     * @param type element type
     * @param desc element type description
     */
    public static void setElementType(String type, String desc) {
        ElementType et = new ElementType();
        et.setElementType(type);
        et.setElementTypeDescription(desc);
        em.getTransaction().begin();
        em.persist(et);
        em.getTransaction().commit();
    }
    
}
