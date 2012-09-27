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
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.MachineMode;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ModelCode;
import org.openepics.model.entity.ModelLine;

/**
 *
 * @author chu
 */
public class ModelAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * get all models
     * 
     * @return all models
     */
    public List<Model> getAllModels() {
         final Query q = em.createNamedQuery("Model.findAll");
         List<Model> mc = q.getResultList();
        
        return mc;
       
    }
    
    /**
     * Get all models for the specified machine mode
     * 
     * @param mode machine mode name
     * @return models for the specified machine mode
     */
    public static List<Model> getAllModelsForMachineMode(String mode) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "JOIN l.machineModeId mm WHERE mm.machineModeName = :modeName").setParameter("modeName", mode);
        List<Model> mList = q.getResultList();
        
        return mList;               
    }
    
    /**
     * Get all models for the specified model line
     * 
     * @param line model line name
     * @return models for the specified model line
     */
    public static List<Model> getAllModelsForModelLine(String line) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "JOIN l.modelLineId ml WHERE ml.modelLineName = :lineName").setParameter("lineName", line);
        List<Model> mList = q.getResultList();
        
        return mList;        
    }
    
    /**
     * get all models with specified model line and machine mode
     * 
     * @param line model line name
     * @param mode machine mode
     * @return modes with specified model line and machine mode
     */
    public static List<Model> getAllModelsForModelLineAndMachineMode(String line, String mode) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "WHERE "
                + "l.modelLineId.modelLineName = :lineName "
                + "AND l.machineModeId.machineModeName = :modeName").setParameter("lineName", line).setParameter("modeName", mode);
        
        List<Model> mList = q.getResultList();
        return mList;
    }
    
    /**
     * Set a new model information
     * 
     * @param model_line model line name
     * @param model_code code used 
     * @param machine_mode machine mode for this model
     * @param model_name a name for this model
     * @param model_desc description for this model
     */
    public static void setModelHeader(String model_line, String model_code, String machine_mode, 
            String model_name, String model_desc) {

        Model m = new Model();

        Query q;
        
        // check if such lattice exists
        q = em.createNativeQuery("SELECT l.Lattice_Id FROM Lattice l, Model_Line ml, Machine_Mode mm "
                + "WHERE l.model_Line_Id=ml.model_Line_Id AND l.machine_Mode_Id=mm.machine_Mode_Id "
                + "AND ml.model_Line_Name=\"" + model_line + "\" AND mm.machine_Mode_Name=\"" 
                + machine_mode + "\"");
        q.setParameter("modelLineName", model_line);
        q.setParameter("machineModeName", machine_mode);
        List<Integer> lattices = q.getResultList();
        // check if the specified lattice exists
        if (lattices.isEmpty()) {
            Lattice l = new Lattice();
            // check if the specified model_line exists
            q = em.createNamedQuery("ModelLine.findByModelLineName").setParameter("modelLineName", model_line);
            // if the model_line exists, use it; otherwise, create a new model_line
            List<ModelLine> mlList = q.getResultList();
            if (mlList.isEmpty()) {
                ModelLine ml = new ModelLine();
                ml.setModelLineName(model_line);
                em.persist(ml);
              
                l.setModelLineId(ml);
            } else {
                l.setModelLineId(mlList.get(0));
            }
            
            
            // check if the machine_mode exists
            q = em.createNamedQuery("MachineMode.findByMachineModeName").setParameter("machineModeName", machine_mode);
            // if the model_line exists, use it; otherwise, create a new model_line
            List<MachineMode> mmList = q.getResultList();
            if (mmList.isEmpty()) {
                MachineMode mm = new MachineMode();
                mm.setMachineModeName(machine_mode);
                em.persist(mm);
                l.setMachineModeId(mm);
            } else {
                l.setMachineModeId(mmList.get(0));
            } 
            em.persist(l);
            m.setLatticeId(l);
        } else {
            q = em.createNamedQuery("Lattice.findByLatticeId").setParameter("latticeId", lattices.get(0));
            m.setLatticeId((Lattice) q.getResultList().get(0));
        }
               
        // check if the specified model_code exists
        q = em.createNamedQuery("ModelCode.findByCodeName").setParameter("codeName", model_code);
        List<ModelCode> mcList = q.getResultList();
        if (mcList.size() > 0) {
            ModelCode mc = (ModelCode) q.getResultList().get(0);
            m.setModelCodeId(mc);
        }
        else {
            // create new model_code
            ModelCode mc = new ModelCode();
            mc.setCodeName(model_code);
            em.persist(mc);
            m.setModelCodeId(mc);
        }
        
        m.setModelName(model_name);
        m.setModelDesc(model_desc);
        m.setCreatedBy(System.getProperty("user.name"));
        m.setCreateDate(new Date());
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }
    
}
