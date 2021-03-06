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
import org.openepics.model.entity.ModelCode;

/**
 *
 * @author chu
 */
public class ModelCodeAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Set a new model code in DB
     * @param codeName model code name, e.g. XAL
     * @param algorithm algorithm used, e.g. EnvelopeTracker
     */
    public void setModelCode(String codeName) {
        ModelCode mc = new ModelCode();
        mc.setCodeName(codeName);
        em.getTransaction().begin();
        em.persist(mc);
        em.getTransaction().commit();
    }    
    
    /**
     * Set a new model code in DB
     * @param codeName model code name, e.g. XAL
     * @param algorithm algorithm used, e.g. EnvelopeTracker
     */
    public void setModelCode(String codeName, String algorithm) {
        ModelCode mc = new ModelCode();
        mc.setCodeName(codeName);
        mc.setAlgorithm(algorithm);
        em.getTransaction().begin();
        em.persist(mc);
        em.getTransaction().commit();
    }
    
    /**
     * Get all modeling codes
     * @return all modeling codes
     */
    public List<ModelCode> getAllModelCodes() {
        Query q;
        q = em.createNamedQuery("ModelCode.findAll");
        List<ModelCode> codeList = q.getResultList();
        
        return codeList;
    }
    
    /**
     * get all algorithms for the specified model code
     * @param code_name model code
     * @return all algorithms for the specified model code
     */
    public List<ModelCode> getAlgorithmsForModelCode(String code_name) {
        Query q;
        q = em.createNamedQuery("ModelCode.findByCodeName").setParameter("codeName", code_name);
        return q.getResultList();
    }
    
    /**
     * get all models run with the specific model code
     * @param name model code name
     * @return all models run with this model code
     */
    public ModelCode getModelCodeWithName(String name) {
        final Query q = em.createNamedQuery("ModelCode.findByCodeName");
        q.setParameter("codeName", name);
        ModelCode mc = (ModelCode) q.getResultList().get(0);
        
        return mc;
    }
       
}
