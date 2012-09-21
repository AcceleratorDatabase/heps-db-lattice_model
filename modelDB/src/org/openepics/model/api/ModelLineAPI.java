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
import org.openepics.model.entity.ModelLine;

/**
 *
 * @author chu
 */
public class ModelLineAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Get all model lines
     * @return all model lines
     */
    public static List<ModelLine> getAllModelLines() {
       Query q;
        q = em.createNamedQuery("ModelLine.findAll");
        List<ModelLine> lineList = q.getResultList();
        
        return lineList;
        
    }
    
    
    /**
     * Set a new model line in DB
     * @param model_line_name model line's name
     * @param model_line_desc description for this model line
     * @param start_pos starting position for this model line
     * @param end_pos ending position for this model line
     * @param start_marker starting marker  name
     * @param end_marker ending marker name
     */
    public static void setModelLine(String model_line_name, String model_line_desc, 
            double start_pos, double end_pos, String start_marker, String end_marker) {
        ModelLine ml = new ModelLine();
        ml.setModelLineName(model_line_name);
        ml.setModelLineDescription(model_line_desc);
        ml.setStartPosition(start_pos);
        ml.setEndPosition(end_pos);
        ml.setStartMarker(start_marker);
        ml.setEndMarker(end_marker);
        em.getTransaction().begin();
        em.persist(ml);
        em.getTransaction().commit();
    }
    
    
}
