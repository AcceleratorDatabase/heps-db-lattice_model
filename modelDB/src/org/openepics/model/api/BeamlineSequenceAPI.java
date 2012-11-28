/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.RfGap;

/**
 *
 * @author chu
 * @author lv
 */
public class BeamlineSequenceAPI {

    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    /**
     * Get all accelerator sequences.
     *
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
        q = em.createQuery("SELECT e FROM Element e JOIN e.beamlineSequenceId b "
                + "WHERE b.sequenceName = :sequenceName").setParameter("sequenceName", seq);
        /*  q = em.createQuery("SELECT blsl FROM BeamlineSequence blsl WHERE blsl.beamlineSequenceId.sequenceName=:sequenceName").setParameter("sequenceName", seq);
         List<BeamlineSequence> blslList = q.getResultList();
         ArrayList<Element> eList = new ArrayList<>();
         Iterator it = blslList.iterator();
         while (it.hasNext()) {
         BeamlineSequence bls = (BeamlineSequence) it.next();
         Collection eCol = (Collection) bls.getElementCollection();
         //System.out.println(eCol.size());
         Iterator it2 = eCol.iterator();
         while (it2.hasNext()) {
         Element e = (Element) it2.next();
         //System.out.println(e.getElementId());
         eList.add(e);
         }

         }*/
        // return eList;

        List<Element> eList = q.getResultList();
        return eList;
    }

    /**
     * Get the Sequence with the specified sequence name
     *
     * @param seqName sequence name
     * @return the sequence with the specified sequence name
     */
    public static BeamlineSequence getSequenceByName(String seqName) {
        Query q;
        q = em.createNamedQuery("BeamlineSequence.findBySequenceName").setParameter("sequenceName", seqName);
        List<BeamlineSequence> seqList = q.getResultList();
        if (seqList.isEmpty()) {
            return null;
        } else {
            return seqList.get(0);
        }
    }

    /**
     * get the first element in the specified sequence
     *
     * @param seq sequence name
     * @return the first element name in the specified sequence
     */
    public static String getEntranceElementForSequence(String seq) {
        Query q;

        q = em.createNamedQuery("BeamlineSequence.findBySequenceName").setParameter("sequenceName", seq);
        List<BeamlineSequence> bList = q.getResultList();
        if (bList.isEmpty()) {
            return null;
        } else {
            if (bList.size() > 1) {
                System.out.println("Warning: there are more than 1 BeamlineSequence for the specified sequence name!");
                return null;
            } else {
                BeamlineSequence bls = bList.get(0);
                return bls.getFirstElementName();
            }
        }
    }

    /**
     * get all elements of the specified type and within the specified sequence
     *
     * @param seq sequence name
     * @param type element type
     * @return all elements of the specified type and within the specified
     * sequence
     */
    public static List<Element> getAllElementsOfTypeForSequence(String seq, String type) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e WHERE "
                + "e.sequenceId.sequenceName=:seqName "
                + "AND e.elementTypeId.elementType= :type")
                .setParameter("seqName", seq).setParameter("type", type);
        List<Element> eList = q.getResultList();
        return eList;
    }

    /**
     * get element count for the specified sequence
     *
     * @param seq sequence name
     * @return element count for the specified sequence
     */
    public static int getElementCountForSequence(String seq) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e WHERE e.sequenceId.sequenceName=:seqName")
                .setParameter("seqName", seq);
        List<Element> eList = q.getResultList();
        return eList.size();
    }

    /**
     * Set a new beamline sequence
     *
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

    /**
     * delete the BeamlineSequence by the given sequence name 
     * delete the Elements belonging to the sequence 
     * delete the ElementProps belonging to the Elements 
     * delete the RfGaps belonging to the Elements
     */
    public static void deleteBySequence(String seqName) {
        em.getTransaction().begin();
        BeamlineSequence bls = BeamlineSequenceAPI.getSequenceByName(seqName);
        if (bls != null) {
            List<Element> eList = BeamlineSequenceAPI.getAllElementsForSequence(seqName);
            Iterator it = eList.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();

                List<ElementProp> epList = ElementAPI.getAllPropertiesForElement(e.getElementName());
                Iterator it1 = epList.iterator();
                while (it1.hasNext()) {
                    ElementProp ep = (ElementProp) it1.next();
                    em.remove(em.merge(ep));
                }

                List<RfGap> rfList = RfGapAPI.getAllRfgapsForCavity(e.getElementName());
                Iterator it2 = rfList.iterator();
                while (it2.hasNext()) {
                    RfGap rf = (RfGap) it2.next();
                    em.remove(em.merge(rf));
                }
                em.remove(em.merge(e));
            }
            em.remove(em.merge(bls));
            em.getTransaction().commit();
        } else {
            System.out.println("The BeamlineSequence " + seqName + " doesn't exist!");
        }
       
    }

    public static void deleteAll() {
        List sequences = BeamlineSequenceAPI.getAllSequences();

        Iterator it = sequences.iterator();
        em.getTransaction().begin();
        while (it.hasNext()) {

            BeamlineSequence bls = (BeamlineSequence) it.next();
            List<Element> eList = BeamlineSequenceAPI.getAllElementsForSequence(bls.getSequenceName());
            Iterator it3 = eList.iterator();
            while (it3.hasNext()) {
                Element e = (Element) it3.next();

                List<ElementProp> epList = ElementAPI.getAllPropertiesForElement(e.getElementName());
                Iterator it1 = epList.iterator();
                while (it1.hasNext()) {
                    ElementProp ep = (ElementProp) it1.next();
                    em.remove(em.merge(ep));
                }

                List<RfGap> rfList = RfGapAPI.getAllRfgapsForCavity(e.getElementName());
                Iterator it2 = rfList.iterator();
                while (it2.hasNext()) {
                    RfGap rf = (RfGap) it2.next();
                    em.remove(em.merge(rf));
                }
                em.remove(em.merge(e));
            }
            em.remove(em.merge(bls));

        }
        em.getTransaction().commit();
     
    }
    
    public static void updateBeamlineSequence(String old_seq_name, String new_seq_name,
            String first_elem_name, String last_elem_name, String previous_seq, double seq_length, String seq_desc){
        em.getTransaction().begin();
        BeamlineSequence bs=BeamlineSequenceAPI.getSequenceByName(old_seq_name);
        bs.setSequenceName(new_seq_name);
        bs.setFirstElementName(first_elem_name);
        bs.setLastElementName(last_elem_name);
        bs.setPredecessorSequence(previous_seq);
        bs.setSequenceLength(seq_length);
        bs.setSequenceDescription(seq_desc);
        em.merge(bs);
        em.getTransaction().commit();
        
    } 
}
