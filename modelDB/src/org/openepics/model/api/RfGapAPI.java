/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
    @PersistenceContext(unitName = "modelAPIPU", type=PersistenceContextType.TRANSACTION)
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    public List<RfGap> getAllRfgapsForCavity(String cav) {       
        Query q;
        q = em.createQuery("SELECT rg from RfGap rg WHERE rg.cavityId.elementName = :elementname ")
                .setParameter("elementname", cav);
        List<RfGap> gaps = q.getResultList();
                
        return gaps;
    } 
    
     public RfGap getRfGapByName(String gapName) {
        Query q;
        q = em.createNamedQuery("RfGap.findByGapName").setParameter("gapName", gapName);
        List<RfGap> gapList = q.getResultList();
        if (gapList.isEmpty()) {
            return null;
        } else {
            return gapList.get(0);
        }
    }
    
    public void setRfGap(String gap_name,double pos, double TTF, double ampFactor, int endCell_ind, 
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
    
    public void deleteRfGap(RfGap rf){
        if (rf != null) {
            em.getTransaction().begin();
            if (em.contains(rf)) {
                em.remove(rf);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(rf);
                em.remove(em.find(RfGap.class, id));
            }
            em.getTransaction().commit();
        }
    }
    
     public void deleteRfGapCollection(Collection<RfGap> rfList){
       Iterator it=rfList.iterator();
       em.getTransaction().begin();
       while(it.hasNext()){
          RfGap rf=(RfGap) it.next();
          if (em.contains(rf)) {
                em.remove(rf);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(rf);
                em.remove(em.find(RfGap.class, id));
            }
       }
       em.getTransaction().commit();
    }
}
