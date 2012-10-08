/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
        Query q;
        q = em.createNamedQuery("GoldLattice.findByGoldStatusInd")
                .setParameter("goldStatusInd", GoldLattice.PRESENT);
        List<Lattice> glList = q.getResultList();
        List<Lattice> lList = new ArrayList<>();
        if (glList.isEmpty()) {
            lList = null;
        } else {
            Iterator it = glList.iterator();
            while (it.hasNext()) {
                GoldLattice gl = (GoldLattice) it.next();
                Lattice l = gl.getLatticeId();
                lList.add(l);
            }
        }
        return lList;
     
    }
    
    /**
     * get the gold model for the specified machine mode and model line
     * @param mode machine mode
     * @param line model line
     * @return the gold model for the specified machine mode and model line
     */
    public static Lattice getGoldLatticeForMachineModeAndModelLine(String mode, String line) {
        Query q;
        q=em.createQuery("SELECT g FROM GoldLattice g WHERE"
                + " g.latticeId.machineModeId.machineModeName = :modeName "
                + "AND g.latticeId.modelLineId.modelLineName = :lineName "
                + "AND g.goldStatusInd = :gind").setParameter("modeName",mode)
                .setParameter("lineName", line).setParameter("gind", GoldLattice.PRESENT);
        List<GoldLattice> gmList = q.getResultList();
        if (gmList.isEmpty()) {
            return null;
        }
        else { 
            if (gmList.size() > 1) {
                System.out.println("Warning: there are more than 1 Gold Lattice for the specified model line and machine mode combination!");
            }
            // find the lattice corresponding to the Gold tag
            Lattice lat = gmList.get(0).getLatticeId();
            return lat;   
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
        List<GoldLattice> gList = em.createQuery("SELECT g FROM GoldLattice g WHERE"
                + " g.latticeId.machineModeId.machineModeName = :modeName "
                + "AND g.latticeId.modelLineId.modelLineName = :lineName "
                + "AND g.goldStatusInd = :gind").setParameter("modeName",gl.getLatticeId().getMachineModeId().getMachineModeName())
                .setParameter("lineName", gl.getLatticeId().getModelLineId().getModelLineName()).setParameter("gind", GoldLattice.PRESENT).getResultList();
        GoldLattice g_old = gList.get(0);
        
        em.getTransaction().begin();
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
