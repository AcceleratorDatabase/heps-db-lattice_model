/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    /**
     * get beam parameter property for specified model ID, element name and property name
     * @param model_id model ID
     * @param elem_name element name
     * @param prop_name property name
     * @return 
     */
    public Object getBeamParameterPropFor(int model_id, String elem_name, String prop_name) {
        Query q;
        q = em.createQuery("SELECT bpp FROM BeamParameterProp bpp WHERE bpp.beamParameterId.elementId.elementName=:elemName "
                + "AND  bpp.propertyName=:propName "
                + "AND bpp.beamParameterId.modelId.modelId=:modelId ").setParameter("elemName", elem_name)
                .setParameter("propName", prop_name).setParameter("modelId", model_id);
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
        bpp.setDescription(description);
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

    public void deleteBeamParameterProp(BeamParameterProp bpp) {
        if (bpp != null) {
            em.getTransaction().begin();
            if (em.contains(bpp)) {
                em.remove(bpp);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bpp);
                em.remove(em.find(BeamParameterProp.class, id));
            }
            em.getTransaction().commit();
        }
    }

    public void deleteBeamParameterPropCollection(Collection<BeamParameterProp> bppList) {
        Iterator it = bppList.iterator();
        em.getTransaction().begin();
        while (it.hasNext()) {
            BeamParameterProp bpp = (BeamParameterProp) it.next();
            if (em.contains(bpp)) {
                em.remove(bpp);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bpp);
                em.remove(em.find(BeamParameterProp.class, id));
            }
        }
        em.getTransaction().commit();
    }

    public List<String> getAllPropertyNames() {
        List<String> names = new ArrayList();
        Query q;
        String sql = "SELECT DISTINCT property_name FROM beam_parameter_prop";
        q = em.createNativeQuery(sql);
        List l = q.getResultList();
        return l;
    }

    public Object getPropValueByNameForBeamParameter(String bp_prop_name, BeamParameter bp) {
        Query q;
        String sql = "SELECT beam_parameter_prop_id FROM (SELECT * FROM beam_parameter_prop WHERE beam_parameter_id="
                + bp.getTwissId() + ")" + " AS bpp" + " WHERE property_name='" + bp_prop_name + "'";
        // System.out.println(sql);
        q = em.createNativeQuery(sql);
        List idList = q.getResultList();
        // System.out.println(idList);
        if (idList.isEmpty()) {
            return null;
        } else {
            BeamParameterProp bpp = em.find(BeamParameterProp.class, idList.get(0));
            if (bpp.getBeamParameterDouble() != null) {
                return bpp.getBeamParameterDouble();
            } else if (bpp.getBeamParameterInt() != null) {
                return bpp.getBeamParameterInt();
            } else if (bpp.getBeamParameterString() != null) {
                return bpp.getBeamParameterString();
            }
        }
        return null;
    }
}
