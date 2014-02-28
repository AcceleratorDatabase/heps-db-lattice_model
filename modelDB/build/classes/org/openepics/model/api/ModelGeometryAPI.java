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
import org.openepics.model.entity.ModelGeometry;

/**
 *
 * @author chu
 */
public class ModelGeometryAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Set a new model geometry in DB
     * @param model_geometry_name model geometry name
     * @param model_geometry_desc description for this model geometry
     */
    public void setModelGeometry(String model_geometry_name, String model_geometry_desc) {
        ModelGeometry mg = new ModelGeometry();
        mg.setModelGeometryName(model_geometry_name);
        mg.setModelGeometryDescription(model_geometry_desc);
        em.getTransaction().begin();
        em.persist(mg);
        em.getTransaction().commit();
    }  
}
