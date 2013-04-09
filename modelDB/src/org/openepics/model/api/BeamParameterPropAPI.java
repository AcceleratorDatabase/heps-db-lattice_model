/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamParameterProp;

/**
 *
 * @author chu
 */
public class BeamParameterPropAPI {
    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * 
     * @param elem_name
     * @param prop_name
     * @return 
     */
    public Object getBeamParameterPropFor(String elem_name, String prop_name) {
        //TODO
        return null;
    }
    
    
    /**
     * Set beam parameter property for the specified element.
     * @param elem_name Element name
     * @param prop_name Property name
     * @param prop Property value
     */
    public void setBeamParameterPropFor(String elem_name, String prop_name, Object prop) {
        //TODO
    }
    
    public void setBeamParameterProp(BeamParameter bp, String category, String name, String datatype, Object prop, String description) {
        BeamParameterProp bpp = new BeamParameterProp();
        bpp.setBeamParameterId(bp);
        bpp.setPropCategory(category);
        bpp.setPropertyName(name);
        bpp.setPropertyDatatype(datatype);
        switch (datatype.toLowerCase()) {
            case "string":
                bpp.setBeamParameterString(prop.toString());
                break;
            case "int":
                bpp.setBeamParameterInt((int)Double.parseDouble(prop.toString()));
                break;
            case "double":
                bpp.setBeamParameterDouble(Double.parseDouble(prop.toString()));
                break;
        }
        em.getTransaction().begin();
        em.persist(bpp);
        em.getTransaction().commit();
    }
    
}
