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
import org.openepics.model.entity.MachineMode;

/**
 *
 * @author chu
 */
public class MachineModeAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Get all machine modes
     * @return all machine modes
     */
    public List<MachineMode> getAllMachineModes() {
        return null;
    }
    
    /**
     * Get the machine mode with the specified name
     * @param machine_mode_name machine mode name 
     * @return the machine mode with the specified name
     */
    public MachineMode getMachineModeWithName(String machine_mode_name) {
        return null;
    }
    
    
    /**
     * Set a new machine mode in DB
     * @param machine_mode_name machine mode name
     * @param machine_mode_desc description for this machine mode
     */
    public void setMachineMode(String machine_mode_name, String machine_mode_desc) {
        MachineMode mm = new MachineMode();
        mm.setMachineModeName(machine_mode_name);
        mm.setMachineModeDescription(machine_mode_desc);
        em.getTransaction().begin();
        em.persist(mm);
        em.getTransaction().commit();
    }    
}
