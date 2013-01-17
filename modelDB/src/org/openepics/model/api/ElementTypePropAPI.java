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
import org.openepics.model.entity.ElementTypeProp;

/**
 *
 * @author chu
 */
public class ElementTypePropAPI {

    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext

    /**
     * 
     * @param et element type
     * @param ele_type_prop_name element property name
     * @param ele_type_prop_desc element property description
     * @param ele_type_prop_defa element property default value
     * @param ele_type_prop_unit element property unit
     * @param ele_type_prop_data element property data type
     * 
     */
    public void setElementTypeProp(ElementType et, String ele_type_prop_name, String ele_type_prop_desc, String ele_type_prop_defa, String ele_type_prop_unit, String ele_type_prop_data) {

        ElementTypeProp etp = new ElementTypeProp();
        etp.setElementTypeId(et);
        etp.setElementTypePropName(ele_type_prop_name);
        etp.setElementTypePropDescription(ele_type_prop_desc);
        etp.setElementTypePropDefault(ele_type_prop_defa);
        etp.setElementTypePropUnit(ele_type_prop_unit);
        etp.setElementTypePropDatatype(ele_type_prop_data);

        em.getTransaction().begin();
        em.persist(etp);
        em.getTransaction().commit();
    }    
    
    /**
     * Get the element_type_prop with the specified element type prop name
     *
     * @param eleTypePropName element type prop name
     * @return the element type prop with the specified element type prop name
     */
    public ElementTypeProp getElementTypePropByName(String eleTypePropName) {
        Query q;
        q = em.createNamedQuery("ElementTypeProp.findByElementTypePropName").setParameter("elementTypePropName", eleTypePropName);
        List<ElementTypeProp> eletpList = q.getResultList();
        if (eletpList.isEmpty()) {
            return null;
        } else {
            return eletpList.get(0);
        }
    }

}
