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
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamParameterProp;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ParticleType;

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
        Query q;
        q = em.createQuery("SELECT bpp FROM BeamParameterProp bpp WHERE bpp.beamParameterId.elementId.elementName=:elemName "
                + "AND  bpp.propertyName=:propName").setParameter("elemName", elem_name).setParameter("propName", prop_name);
        List<BeamParameterProp> bppList = q.getResultList();
        if (bppList.isEmpty()) {
            return null;
        } else {
            BeamParameterProp bpp = bppList.get(0);
            switch (bpp.getPropertyDatatype().toLowerCase()) {
                case "string":
                    return bpp.getBeamParameterString();
                case "int":
                    return bpp.getBeamParameterInt();
                case "double":
                    return bpp.getBeamParameterDouble();
            }
        }
        return null;
    }

    public void setBeamParameterPropFor(String elem_name, int model_id, String prop_name, Object prop_val) {
        // TODO
        // determine object type and insert accordingly
        
    }
    
    /**
     * Set beam parameter property for the specified element.
     *
     * @param elem_name Element name
     * @param prop_name Property name
     * @param prop Property value
     * @param datatype Property datatype
     */
    public void setBeamParameterPropFor(String elem_name, String model_name, String prop_name, Object prop, String datatype) {      
        BeamParameterProp bpp = new BeamParameterProp();
        BeamParameter bp = new BeamParameterAPI().getBeamParameterForElementAndModel(elem_name, model_name);
        if (bp == null) {
            Element elment = new ElementAPI().getElementByName(elem_name);
            Model model = new ModelAPI().getModelForName(model_name);
            bp = new BeamParameterAPI().setBeamParameter(elment, model, null);            
        }        
        bpp.setBeamParameterId(bp);
        bpp.setPropertyName(prop_name);
        bpp.setPropertyDatatype(datatype);
        switch (datatype.toLowerCase()) {
            case "string":
                bpp.setBeamParameterString(prop.toString());
                break;
            case "int":
                bpp.setBeamParameterInt(Integer.parseInt(prop.toString()));
                break;
            case "double":
                bpp.setBeamParameterDouble(Double.parseDouble(prop.toString()));
                break;
        }  
        em.getTransaction().begin();
        em.persist(bpp);
        em.getTransaction().commit();
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
                bpp.setBeamParameterInt((int) Double.parseDouble(prop.toString()));
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
