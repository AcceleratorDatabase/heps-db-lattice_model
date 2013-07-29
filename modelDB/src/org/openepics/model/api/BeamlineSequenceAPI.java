/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.tools.persistence.PersistenceTools;

/**
 *
 * @author chu
 * @author lv
 */
@Stateless
public class BeamlineSequenceAPI {

    @PersistenceUnit

    /*static Map properties=PersistenceTools.setPersistenceParameters("mysql", "localhost:3306", "root", "826529");
     static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU",properties);
     static final EntityManager em = emf.createEntityManager();*/
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext
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
    public int setBeamlineSequence(String seq_name, String first_elem_name,
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
    public int setBeamlineSequence(String seq_name, String first_elem_name,
            String last_elem_name, String previous_seq, double seq_length,
            String seq_desc, Collection<BlsequenceLattice> blsequenceLatticeCollection) {
        BeamlineSequence bs = new BeamlineSequence();
        bs.setSequenceName(seq_name);
        bs.setFirstElementName(first_elem_name);
        bs.setLastElementName(last_elem_name);
        bs.setPredecessorSequence(previous_seq);
        bs.setSequenceLength(seq_length);
        bs.setSequenceDescription(seq_desc);
        bs.setBlsequenceLatticeCollection(blsequenceLatticeCollection);
        em.getTransaction().begin();
        em.persist(bs);
        em.getTransaction().commit();

        return bs.getBeamlineSequenceId();
    }

    public BeamlineSequence getBeamlineSequenceById(int bls_id) {
        BeamlineSequence bls = em.find(BeamlineSequence.class, bls_id);
        return bls;
    }

    /**
     * Get all accelerator sequences.
     *
     * @return all sequences
     */
    public List<BeamlineSequence> getAllSequences() {
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
    public List<Element> getAllElementsForSequence(String seq) {
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
    public BeamlineSequence getSequenceByName(String seqName) {
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
    public String getEntranceElementForSequence(String seq) {
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
    public List<Element> getAllElementsOfTypeForSequence(String seq, String type) {
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
    public int getElementCountForSequence(String seq) {
        Query q;
        q = em.createQuery("SELECT e FROM Element e WHERE e.sequenceId.sequenceName=:seqName")
                .setParameter("seqName", seq);
        List<Element> eList = q.getResultList();
        return eList.size();
    }

    /**
     * delete the BeamlineSequence by the given sequence name delete the
     * BlseqneceLattice belonging to the sequence delete the Elements belonging
     * to the sequence
     */
    public void deleteBySequence(String seqName) {
        em.getTransaction().begin();
        BeamlineSequence bls = getSequenceByName(seqName);
        if (bls != null) {
            //BlsequenceLattice
            Collection<BlsequenceLattice> blslList = bls.getBlsequenceLatticeCollection();
            new BlsequenceLatticeAPI().deleteBlsequenceLatticeCollection(blslList);
            //Element
            List<Element> eList = (List<Element>) bls.getElementCollection();
            Iterator it = eList.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                new ElementAPI().deleteElementByName(e.getElementName());
            }

            if (em.contains(bls)) {
                em.remove(bls);
            } else {
                int id = (int) emf.getPersistenceUnitUtil().getIdentifier(bls);
                em.remove(em.find(BeamlineSequence.class, id));
            }
            em.getTransaction().commit();
        } else {
            System.out.println("The BeamlineSequence " + seqName + " doesn't exist!");
        }
    }

    public void deleteAll() {
        List sequences = getAllSequences();
        Iterator it = sequences.iterator();
        while (it.hasNext()) {
            BeamlineSequence bls = (BeamlineSequence) it.next();
            this.deleteBySequence(bls.getSequenceName());
        }
    }

    public void updateBeamlineSequence(String old_seq_name, String new_seq_name,
            String first_elem_name, String last_elem_name, String previous_seq, double seq_length, String seq_desc) {
        em.getTransaction().begin();
        BeamlineSequence bs = getSequenceByName(old_seq_name);
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
