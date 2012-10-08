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
import org.openepics.model.entity.GoldModel;
import org.openepics.model.entity.Model;

/**
 *
 * @author chu
 * @author lv
 */
public class GoldModelAPI {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * get all present gold models
     * 
     * @return all present gold models
     */
    public static List<Model> getAllPresentGoldModels() {
        // TODO fill in code
        Query q;
        q = em.createNamedQuery("GoldModel.findByGoldStatusInd")
                .setParameter("goldStatusInd", GoldModel.PRESENT);
        List<GoldModel> gmList = q.getResultList();
        List<Model> mList = new ArrayList<>();
        if (gmList.isEmpty()) {
            mList = null;
        } else {
            Iterator it = gmList.iterator();
            while (it.hasNext()) {
                GoldModel gm = (GoldModel) it.next();
                Model m = gm.getModelId();
                mList.add(m);
            }
        }
        return mList;
    }
    
    
    /**
     * get the Gold Model for the specified machine mode and model line
     * 
     * @param mode Machine Mode name
     * @param line Model Line name
     * @return Gold Model for the specified machine mode and model line 
     */
    public static Model getGoldModelForMachineModeAndModelLine(String mode, String line) {
        Query q;

        q = em.createQuery("SELECT g FROM GoldModel g WHERE"
                + " g.modelId.latticeId.machineModeId.machineModeName=:modeName"
                + " AND g.modelId.latticeId.modelLineId.modelLineName=:lineName"
                + " AND g.goldStatusInd=:gind").setParameter("modeName", mode)
                .setParameter("lineName", line).setParameter("gind", GoldModel.PRESENT);
        List<GoldModel> gmList = q.getResultList();
        if (gmList.isEmpty()) {
            return null;
        } else {
            if (gmList.size() > 1) {
                System.out.println("Warning: there are more than 1 Gold Model for the specified model line and machine mode combination!");
            }
            
            Model m = gmList.get(0).getModelId();
            return m;            
        }

    }

    /**
     * 
     * @param m 
     */
    public static void setGoldModel(Model m) {
        GoldModel gm = new GoldModel();
        Date date = new Date();
        List<GoldModel> gList = em.createQuery("SELECT g FROM GoldModel g WHERE"
                + " g.modelId.latticeId.machineModeId.machineModeName=:modeName"
                + " AND g.modelId.latticeId.modelLineId.modelLineName=:lineName"
                + " AND g.goldStatusInd=:gind").setParameter("modeName", gm.getModelId().getLatticeId().getMachineModeId().getMachineModeName())
                .setParameter("lineName", gm.getModelId().getLatticeId().getModelLineId().getModelLineName()).setParameter("gind", GoldModel.PRESENT).getResultList();
        GoldModel gm_old = gList.get(0);
        
        em.getTransaction().begin();
        if (gm_old!=null) {
            gm_old.setGoldStatusInd(GoldModel.PREVIOUS);
            gm_old.setUpdateDate(date);
            gm.setUpdatedBy(System.getProperty("user.name"));
            em.persist(gm_old);
        }
        gm.setCreateDate(date);
        gm.setCreatedBy(System.getProperty("user.name"));
        gm.setGoldStatusInd(GoldModel.PRESENT);
        gm.setModelId(m);
        em.persist(gm);
        em.getTransaction().commit();
    }
    
    /**
     * 
     * @param modelId 
     */
    public static void setGoldModel(int modelId) {
        Query q;
        q = em.createNamedQuery("Model.findByModelId").setParameter("modelId", modelId);
        Model m = (Model) q.getResultList().get(0);
        setGoldModel(m);
    }
}
