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
import org.openepics.model.entity.ParticleType;

/**
 *
 * @author paul
 * @author lv
 */
public class ParticleTypeAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Set a particle type with name, mass and charge
     * 
     * @param particle_name particle name
     * @param particle_mass particle mass in Mev/c^2
     * @param particle_charge particle charge in e.u.
     */
    public void setParticleType(String particle_name,double particle_mass,int paritcle_charge){
        ParticleType pt=new ParticleType();
        pt.setParticleName(particle_name);
        pt.setParticleMass(particle_mass);
        pt.setParticleCharge(paritcle_charge);
        
        em.getTransaction().begin();
        em.persist(pt);
        em.getTransaction().commit();   
    }
    
    /**
     * Get the specified particle type
     * @param particle_name particle name
     * 
     * @return the specified particle type
     */
    public ParticleType getParticleType(String particle_name){
        Query q;
        q = em.createQuery("SELECT pt FROM ParticleType pt WHERE pt.particleName=:particleName");             
              
        List<ParticleType> pList = q.getResultList();
        if (pList.isEmpty()) {
            return null;
        } else {
            return pList.get(0);
        }
    }
    
    /**
     * Get all particle types
     * @return all particle types
     */
    public List<ParticleType> getAllParticleTypes() {
        List<ParticleType> pList = null;
        Query q;
        q = em.createNamedQuery("ParticleType.findAll");
        pList = q.getResultList();
        
        return pList;
    }
}
