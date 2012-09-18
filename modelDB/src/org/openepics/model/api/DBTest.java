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
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ModelCode;

/**
 *
 * @author paul
 */
public class DBTest {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    public List<ModelCode> getAllModelCodesWithName(String name) {
        final Query q = em.createNamedQuery("ModelCode.findByCodeName");
        q.setParameter("codeName", name);
        List<ModelCode> mc = q.getResultList();
        
        return mc;
    }
    
    public List<Model> getAllModels() {
         final Query q = em.createNamedQuery("Model.findAll");
         List<Model> mc = q.getResultList();
        
        return mc;
       
    }
    
    public static void main(String[] args) {
        DBTest x = new DBTest();
//        List seqs = x.getAllSequences();
//        System.out.println("There are " + seqs.size() + " sequences.");
        
//        x.setModelCode("XAL", "EnvelopeTracker");
        
//        List<ModelCode> mcList = x.getAllModelCodes();
//        System.out.println("There are " + mcList.size() + " mode codes.");
        
        System.out.println("Algorithm = " + x.getAllModelCodesWithName("XAL").get(0).getAlgorithm());
        
    }

}
