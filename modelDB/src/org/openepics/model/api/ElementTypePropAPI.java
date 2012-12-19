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
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    /**
     * Get the element_type_prop with the specified element type prop name
     *
     * @param eleTypePropName element type prop name
     * @return the element type prop with the specified element type prop name
     */
    public static ElementTypeProp getElementTypePropByName(String eleTypePropName) {
        Query q;
        q = em.createNamedQuery("ElementTypeProp.findByElementTypePropName").setParameter("elementTypePropName", eleTypePropName);
        List<ElementTypeProp> eletpList = q.getResultList();
        if (eletpList.isEmpty()) {
            return null;
        } else {
            return eletpList.get(0);
        }
    }

    public static void setElementTypeProp(ElementType et, String ele_type_prop_name, String ele_type_prop_desc, String ele_type_prop_defa, String ele_type_prop_unit, String ele_type_prop_data) {

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
}
