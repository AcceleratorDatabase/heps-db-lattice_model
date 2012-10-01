/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.GoldLattice;
import org.openepics.model.entity.Lattice;

/**
 *
 * @author chu
 * @author lv
 */
public class GoldLatticeAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * get all present gold lattices
     * 
     * @return all present gold lattices
     */
    public static List<Lattice> getAllPresentGoldModels() {
        // TODO fill in code
        
        return null;
    }
    
    /**
     * get the gold model for the specified machine mode and model line
     * @param mode machine mode
     * @param line model line
     * @return the gold model for the specified machine mode and model line
     */
    public static GoldLattice getGoldLatticeForMachineModeAndModelLine(String mode, String line) {
        Query q;
        q = em.createQuery("SELECT g FROM Gold g WHERE "
                + "g.latticeId.modelLineId.modelLineName = :lineName "
                + "AND g.latticeId.machineModeId.machineModeName = :modeName "
                + "AND g.goldStatusInd = :gind").setParameter("lineName", line)
                .setParameter("modeName", mode).setParameter("gind", GoldLattice.PRESENT); 
        List<GoldLattice> gmList = q.getResultList();
        if (gmList.isEmpty()) {
            return null;
        }
        else { 
            if (gmList.size() > 1) {
                System.out.println("Warning: there are more than 1 Gold Lattice for the specified model line and machine mode combination!");
            }
            return gmList.get(0);   
        }
    }
        
    /**
     * Tag a Lattice as Gold
     * 
     * @param l the Lattice to be tagged as Gold
     */
    public static void setGoldLattice(Lattice l) {
        // move present Gold to previous Gold
        GoldLattice gl = new GoldLattice();
        Date date = new Date();
        em.getTransaction().begin();
        GoldLattice g_old = getGoldLatticeForMachineModeAndModelLine(
                    gl.getLatticeId().getMachineModeId().getMachineModeName(), 
                gl.getLatticeId().getModelLineId().getModelLineName());
        if (g_old != null) {
            g_old.setGoldStatusInd(GoldLattice.PREVIOUS);
            g_old.setUpdateDate(date);
            g_old.setUpdatedBy(System.getProperty("user.name"));
            em.persist(g_old);
        }
        gl.setCreateDate(date);
        gl.setCreatedBy(System.getProperty("user.name"));
        gl.setGoldStatusInd(GoldLattice.PRESENT);
        gl.setLatticeId(l);
        em.persist(gl);
        
        em.getTransaction().commit();
    }   
    
    /**
     * Tag a Gold lattice by lattice ID
     * @param latticeId 
     */
    public static void setGoldLattice(int latticeId) {
        Query q;
        q = em.createNamedQuery("Lattice.findByLatticeId").setParameter("latticeId", latticeId);
        Lattice lat = (Lattice) q.getResultList().get(0);
        setGoldLattice(lat);
    }
}
