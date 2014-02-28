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
import org.openepics.model.entity.ElementType;

/**
 *
 * @author chu
 */
public class ElementTypeAPI {
    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * set element type definition in DB
     * 
     * @param type element type
     * @param desc element type description
     */
    public void setElementType(String type) {
        ElementType et = new ElementType();
        et.setElementType(type);
        em.getTransaction().begin();
        em.persist(et);
        em.getTransaction().commit();
    }
    
    /**
     * set element type definition in DB
     * 
     * @param type element type
     * @param desc element type description
     */
    public void setElementType(String type, String desc) {
        ElementType et = new ElementType();
        et.setElementType(type);
        et.setElementTypeDescription(desc);
        em.getTransaction().begin();
        em.persist(et);
        em.getTransaction().commit();
    }
    
    /**
     * Get all element types.
     * @return all element types
     */
    public List<ElementType> getAllElementTypes() {
        Query q;
        q = em.createNamedQuery("ElementType.findAll");
        List<ElementType> typeList = q.getResultList();

        return typeList;        
    }
    
    /**
     * Get the ElementType for specified type
     * @param type element type
     * @return the element type for specified type
     */
    public ElementType getElementTypeByType(String type) {
        Query q;
        q = em.createNamedQuery("ElementType.findByElementType").setParameter("elementType",type);
        List<ElementType> eltList = q.getResultList();
        if(eltList.isEmpty()) return null;
        else{
            return eltList.get(0);
        }
    }
    
     public List<ElementType> getElementTypesByText(String searchItem) {
        Query q;
        q = em.createQuery("SELECT et FROM ElementType et WHERE et.elementType LIKE :ele_type OR et.elementTypeDescription LIKE :ele_type_des")
                .setParameter("ele_type","%"+searchItem+"%").setParameter("ele_type_des","%"+searchItem+"%");
        List<ElementType> l = q.getResultList();      
        return l;
    }   
}
