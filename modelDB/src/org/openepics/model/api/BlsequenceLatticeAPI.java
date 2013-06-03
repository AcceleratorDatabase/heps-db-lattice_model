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
import org.openepics.model.entity.BlsequenceLattice;
import org.openepics.model.entity.Lattice;

/**
 *
 * @author lv
 * @author chu
 */
public class BlsequenceLatticeAPI {

    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static final EntityManager em = emf.createEntityManager();

    @PersistenceContext
    public void setBlsequenceLattice(BeamlineSequence beamline_sequence, Lattice lattice, int beamline_order) {
        BlsequenceLattice blsl = new BlsequenceLattice();
        blsl.setBeamlineSequenceId(beamline_sequence);
        blsl.setLatticeId(lattice);
        blsl.setBeamlineOrder(beamline_order);
        em.getTransaction().begin();
        em.persist(blsl);
        em.getTransaction().commit();
    }

    public void setBlsequenceLattice(BeamlineSequence beamline_sequence, Lattice lattice) {
        BlsequenceLattice blsl = new BlsequenceLattice();
        blsl.setBeamlineSequenceId(beamline_sequence);
        blsl.setLatticeId(lattice);

        em.getTransaction().begin();
        em.persist(blsl);
        em.getTransaction().commit();
    }

    public List<BeamlineSequence> getSequencesForLattice(String lattice_name) {
        Query q;
        q = em.createQuery("SELECT blsl.beamlineSequenceId FROM BlsequenceLattice blsl WHERE blsl.latticeId.latticeName=:latticeName").setParameter("latticeName", lattice_name);
        List<BeamlineSequence> blsList = q.getResultList();
        return blsList;
    }
}
