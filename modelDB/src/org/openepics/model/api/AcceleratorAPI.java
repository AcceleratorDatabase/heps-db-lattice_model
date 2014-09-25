/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import org.openepics.model.entity.Accelerator;

/**
 *
 * @author chu
 */
public class AcceleratorAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    public void setAccelerator(String accelerator_name, String accelerator_description, Date create_date) {
        Accelerator acc = new Accelerator();
//        Date date = new Date();
        acc.setAcceleratorName(accelerator_name);
        acc.setCreateDate(create_date);
        acc.setAcceleratorDescription(accelerator_description);
 
        em.getTransaction().begin();
        em.persist(acc);
        em.getTransaction().commit();

    } 
    
    /**
     *
     * @param acceleratorName the accelerator name
     * @return the accelerator object with the specified name
     */
    public Accelerator getAcceleratorByName(String acceleratorName) {
        Query q;
        q = em.createNamedQuery("Accelerator.findByAcceleratorName").setParameter("acceleratorName", acceleratorName);
        List<Accelerator> accList = q.getResultList();
        if (accList.isEmpty()) {
            return null;
        } else {
            return accList.get(0);
        }
    }

    
}
