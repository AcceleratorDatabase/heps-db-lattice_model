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
    
    /**
     * Set a new beamline sequence
     * @param seq_name sequence name
     * @param first_elem_name first element name
     * @param last_elem_name last element name
     * @param previous_seq previous sequence name
     * @param seq_length sequence length
     * @param seq_desc description for this sequence
     */
    public static void setBeamlineSequence(String seq_name, String first_elem_name, 
            String last_elem_name, String previous_seq, double seq_length, String seq_desc) {
        BeamlineSequence bs = new BeamlineSequence();
        bs.setSequenceName(seq_name);
        bs.setFirstElementName(first_elem_name);
        bs.setLastElementName(last_elem_name);
        bs.setPredecessorSequence(previous_seq);
        bs.setSequenceLength(seq_length);
        bs.setSequenceDescription(seq_desc);
        em.getTransaction().begin();
        em.persist(bs);
        em.getTransaction().commit();
    }
        
}
