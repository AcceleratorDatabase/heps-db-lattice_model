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
import org.openepics.model.entity.Element;

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
     * get all elements within the specified sequence
     * 
     * @param seq sequence name
     * @return elements within the specified sequence
     */
    public static List<Element> getAllElementsForSequence(String seq) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e JOIN e.sequenceId s "
                + "WHERE s.sequenceName = :sequenceName").setParameter("sequenceName", seq);
        List<Element> eList = q.getResultList();
        return eList;
    }
    
    /**
     * get the first element in the specified sequence
     * 
     * @param seq sequence name
     * @return the first element name in the specified sequence
     */
    public static String getEntranceElementForSequence(String seq) {
       // TODO fill in code
       return null; 
    }
    
    /**
     * get all elements of the specified type and within the specified sequence
     * @param seq sequence name
     * @param type element type
     * @return all elements of the specified type and within the specified sequence
     */
    public static List<Element> getAllElementsOfTypeForSequence(String seq, String type) {
        // TODO fill in code
        return null;
    }
    
    /**
     * get element count for the specified sequence
     * @param seq sequence name
     * @return 
     */
    public static int getElementCountForSequence(String seq) {
        // TODO fill in code
        return 0;
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
