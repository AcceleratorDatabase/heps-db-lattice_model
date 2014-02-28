/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.BlsequenceLattice;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.RfGap;

/**
 *
 * @author chu
 * @author lv
 */
@Stateless
public class BeamlineSequenceAPI_1 {

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
    public int setBeamlineSequence(final String seq_name, final String first_elem_name,
            final String last_elem_name, final String previous_seq, final double seq_length, final String seq_desc) {

        final BeamlineSequence bs = new BeamlineSequence();
        EntityManagerHelper.doTransaction(new Runnable() {
            public void run() {
                EntityManager em = EntityManagerHelper.getEntityManager();

                bs.setSequenceName(seq_name);
                bs.setFirstElementName(first_elem_name);
                bs.setLastElementName(last_elem_name);
                bs.setPredecessorSequence(previous_seq);
                bs.setSequenceLength(seq_length);
                bs.setSequenceDescription(seq_desc);
                em.persist(bs);
            }
        });
        return bs.getBeamlineSequenceId();
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
    public int setBeamlineSequence(final String seq_name, final String first_elem_name,
            final String last_elem_name, final String previous_seq, final double seq_length,
            final String seq_desc, final Collection<BlsequenceLattice> blsequenceLatticeCollection) {
        final BeamlineSequence bs = new BeamlineSequence();
        EntityManagerHelper.doTransaction(new Runnable() {
            @Override
            public void run() {
                EntityManager em = EntityManagerHelper.getEntityManager();

                bs.setSequenceName(seq_name);
                bs.setFirstElementName(first_elem_name);
                bs.setLastElementName(last_elem_name);
                bs.setPredecessorSequence(previous_seq);
                bs.setSequenceLength(seq_length);
                bs.setSequenceDescription(seq_desc);
                bs.setBlsequenceLatticeCollection(blsequenceLatticeCollection);
                em.persist(bs);
            }
        });
        return bs.getBeamlineSequenceId();
    }

    /**
     * Get all accelerator sequences.
     *
     * @return all sequences
     */
    public List<BeamlineSequence> getAllSequences() {
        Query q;
        q = EntityManagerHelper.createNamedQuery("BeamlineSequence.findAll");
        List<BeamlineSequence> seqList = q.getResultList();

        return seqList;
    }

    /**
     * get all elements within the specified sequence
     *
     * @param seq sequence name
     * @return elements within the specified sequence
     */
    public List<Element> getAllElementsForSequence(String seq) {
        Query q;
        q = EntityManagerHelper.createQuery("SELECT e FROM Element e JOIN e.beamlineSequenceId b "
                + "WHERE b.sequenceName = :sequenceName").setParameter("sequenceName", seq);
        List<Element> eList = q.getResultList();
        return eList;
    }

    /**
     * Get the Sequence with the specified sequence name
     *
     * @param seqName sequence name
     * @return the sequence with the specified sequence name
     */
    public BeamlineSequence getSequenceByName(String seqName) {
        Query q;
        q = EntityManagerHelper.createNamedQuery("BeamlineSequence.findBySequenceName").setParameter("sequenceName", seqName);
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
    public String getEntranceElementForSequence(String seq) {
        Query q;

        q = EntityManagerHelper.createNamedQuery("BeamlineSequence.findBySequenceName").setParameter("sequenceName", seq);
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
    public List<Element> getAllElementsOfTypeForSequence(String seq, String type) {
        Query q;
        q = EntityManagerHelper.createQuery("SELECT e FROM Element e WHERE "
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
    public int getElementCountForSequence(String seq) {
        Query q;
        q = EntityManagerHelper.createQuery("SELECT e FROM Element e WHERE e.sequenceId.sequenceName=:seqName")
                .setParameter("seqName", seq);
        List<Element> eList = q.getResultList();
        return eList.size();
    }

    /**
     * delete the BeamlineSequence by the given sequence name delete the
     * Elements belonging to the sequence delete the ElementProps belonging to
     * the Elements delete the RfGaps belonging to the Elements
     */
    public void deleteBySequence(final String seqName) {
        final BeamlineSequence bls = getSequenceByName(seqName);
        if (bls != null) {
            EntityManagerHelper.doTransaction(new Runnable() {
                @Override
                public void run() {
                    EntityManager em = EntityManagerHelper.getEntityManager();
                    List<Element> eList = getAllElementsForSequence(seqName);
                    Iterator it = eList.iterator();
                    while (it.hasNext()) {
                        Element e = (Element) it.next();

                        ElementAPI elementAPI = new ElementAPI();
                        List<ElementProp> epList = elementAPI.getAllPropertiesForElement(e.getElementName());
                        Iterator it1 = epList.iterator();
                        while (it1.hasNext()) {
                            ElementProp ep = (ElementProp) it1.next();
                            em.remove(em.merge(ep));
                        }

                        RfGapAPI rfGapAPI = new RfGapAPI();
                        List<RfGap> rfList = rfGapAPI.getAllRfgapsForCavity(e.getElementName());
                        Iterator it2 = rfList.iterator();
                        while (it2.hasNext()) {
                            RfGap rf = (RfGap) it2.next();
                            em.remove(em.merge(rf));
                        }
                        em.remove(em.merge(e));
                    }
                    List<BlsequenceLattice> blList = (List<BlsequenceLattice>) bls.getBlsequenceLatticeCollection();
                    Iterator it3 = blList.iterator();
                    while (it3.hasNext()) {
                        BlsequenceLattice bl = (BlsequenceLattice) it3.next();
                        em.remove(em.merge(bl));
                    }
                    em.remove(bls);
                }
            });
        } else {
            System.out.println("The BeamlineSequence " + seqName + " doesn't exist!");
        }
    }

    public void deleteAll() {
        EntityManagerHelper.doTransaction(new Runnable() {
            @Override
            public void run() {
                EntityManager em = EntityManagerHelper.getEntityManager();
                List sequences = getAllSequences();
                Iterator it = sequences.iterator();
                while (it.hasNext()) {
                    BeamlineSequence bls = (BeamlineSequence) it.next();
                    List<Element> eList = getAllElementsForSequence(bls.getSequenceName());
                    
                    Iterator it3 = eList.iterator();
                    while (it3.hasNext()) {
                        Element e = (Element) it3.next();

                        ElementAPI elementAPI = new ElementAPI();
                        List<ElementProp> epList = elementAPI.getAllPropertiesForElement(e.getElementName());
                        Iterator it1 = epList.iterator();
                        while (it1.hasNext()) {
                            ElementProp ep = (ElementProp) it1.next();
                            em.remove(em.merge(ep));
                        }

                        RfGapAPI rfGapAPI = new RfGapAPI();
                        List<RfGap> rfList = rfGapAPI.getAllRfgapsForCavity(e.getElementName());
                        Iterator it2 = rfList.iterator();
                        while (it2.hasNext()) {
                            RfGap rf = (RfGap) it2.next();
                            em.remove(em.merge(rf));
                        }
                        em.remove(em.merge(e));


                    }
                    
                    List<BlsequenceLattice> blList = (List<BlsequenceLattice>) bls.getBlsequenceLatticeCollection();
                    Iterator it4 = blList.iterator();
                    while (it4.hasNext()) {
                        BlsequenceLattice bl = (BlsequenceLattice) it4.next();
                        em.remove(bl);
                    }
                    em.remove(bls);

                }

            }
        });



    }

    public void updateBeamlineSequence(final String old_seq_name, final String new_seq_name,
            final String first_elem_name, final String last_elem_name, final String previous_seq, final double seq_length, final String seq_desc) {
        EntityManagerHelper.doTransaction(new Runnable() {
            @Override
            public void run() {
                EntityManager em = EntityManagerHelper.getEntityManager();
                BeamlineSequence bs = getSequenceByName(old_seq_name);
                bs.setSequenceName(new_seq_name);
                bs.setFirstElementName(first_elem_name);
                bs.setLastElementName(last_elem_name);
                bs.setPredecessorSequence(previous_seq);
                bs.setSequenceLength(seq_length);
                bs.setSequenceDescription(seq_desc);
                em.merge(bs);
            }
        });
    }
}
