/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

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
