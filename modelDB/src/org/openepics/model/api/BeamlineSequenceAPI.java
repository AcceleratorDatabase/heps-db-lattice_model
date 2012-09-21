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
import org.openepics.model.entity.BeamlineSequence;

/**
 *
 * @author chu
 */
public class BeamlineSequenceAPI {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Get all accelerator sequences.
     * @return all sequences
     */
    public static List<BeamlineSequence> getAllSequences() {
        Query q;
        q = em.createNamedQuery("BeamlineSequence.findAll");
        List<BeamlineSequence> seqList = q.getResultList();

        return seqList;        
    }
    
    
}
