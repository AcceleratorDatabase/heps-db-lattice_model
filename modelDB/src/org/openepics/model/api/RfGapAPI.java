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
import org.openepics.model.entity.RfGap;

/**
 *
 * @author chu
 */
public class RfGapAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    public static List<RfGap> getAllRfgapsForCavity(String cav) {
        
        Query q;
        q = em.createQuery("SELECT rg from RfGap rg WHERE rg.cavityId.elementName = :elementname ")
                .setParameter("elementname", cav);
        List<RfGap> gaps = q.getResultList();
                
        return gaps;
    } 
    
}
