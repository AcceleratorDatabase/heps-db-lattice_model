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
import org.openepics.model.entity.Element;
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
    
     public static RfGap getRfGapByName(String gapName) {
        Query q;
        q = em.createNamedQuery("RfGap.findByGapName").setParameter("gapName", gapName);
        List<RfGap> gapList = q.getResultList();
        if (gapList.isEmpty()) {
            return null;
        } else {
            return gapList.get(0);
        }
    }
    
    public static void setRfGap(String gap_name,double pos, double TTF, double ampFactor, int endCell_ind, 
            double gapOffset, double len, double phaseFactor, Element cavity) {
        RfGap rf=new RfGap();
        rf.setPos(pos);
        rf.setTtf(TTF);
        rf.setAmpFactor(ampFactor);
        rf.setEndCellind(endCell_ind);
        rf.setGapOffset(gapOffset);
        rf.setLen(len);
        rf.setPhaseFactor(phaseFactor);
        rf.setGapName(gap_name);
        rf.setCavityId(cavity);
      
        em.getTransaction().begin();
        em.persist(rf);
        em.getTransaction().commit();
    }
    
}
