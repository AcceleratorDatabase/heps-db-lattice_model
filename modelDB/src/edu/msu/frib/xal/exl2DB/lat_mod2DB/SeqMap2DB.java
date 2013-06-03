/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.lat_mod2DB;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.BlsequenceLatticeAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.LatticeAPI;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.Lattice;

/**
 *
 * @author lv
 * @author chu
 */
public class SeqMap2DB {

    @PersistenceUnit
    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext
    public void instDB(ArrayList mapData, String latticeName, String created_by, Date create_date) {
        if (latticeName == null || "".equals(latticeName)) {
            System.out.println("Please insert the Lattice name!");
        } else {
            LatticeAPI latticeAPI = new LatticeAPI();
            Lattice l = latticeAPI.getLatticeByName(latticeName);
            if (l != null) {
                System.out.println("The Lattice " + latticeName + " is already in the database! Please don't insert repeatedly!");
            } else {
                em.getTransaction().begin();

                int lattice_id = new LatticeAPI().setLattice(latticeName, created_by, create_date);
                Lattice lattice = em.find(Lattice.class, lattice_id);

                Iterator it = mapData.iterator();
                while (it.hasNext()) {
                    Map dataMap = (Map) it.next();
                    String sequence_name = (String) dataMap.get("sequence_name");
                    String first_ele_name = dataMap.get("first_element_name").toString();
                    String last_ele_name = dataMap.get("last_element_name").toString();
                    String pre_seq1 = dataMap.get("predecessor_sequence").toString();
                    int bl_seq_order = (int) Double.parseDouble(dataMap.get("beamline_sequence_order").toString());

                    String pre_seq = pre_seq1;
                    if ("".equals(pre_seq1) || pre_seq == null || "null".equals(pre_seq)) {
                        pre_seq = null;
                    }
                    Double seq_length = Double.parseDouble(dataMap.get("sequence_length").toString());
                    Double first_ele_s = Double.parseDouble(dataMap.get("start_global_position").toString());
                    Double last_ele_s = first_ele_s + seq_length;
                    String seq_des = dataMap.get("sequence_description").toString();
                    BeamlineSequenceAPI beamlineSequenceAPI = new BeamlineSequenceAPI();
                    BeamlineSequence bs = beamlineSequenceAPI.getSequenceByName(sequence_name);
                    int beamline_sequence_id;
                    if (bs == null) {
                        beamline_sequence_id = beamlineSequenceAPI.setBeamlineSequence(sequence_name, first_ele_name,
                                last_ele_name, pre_seq, seq_length, seq_des);
                        bs = em.find(BeamlineSequence.class, beamline_sequence_id);

                    } else {
                        System.out.println("The sequence " + sequence_name + " is already in the database. Your execution will overwrite this record!");

                        bs = em.find(BeamlineSequence.class, bs.getBeamlineSequenceId());
                        bs.setFirstElementName(first_ele_name);
                        bs.setLastElementName(last_ele_name);
                        bs.setPredecessorSequence(pre_seq);
                        bs.setSequenceLength(seq_length);
                        bs.setSequenceDescription(seq_des);
                    }

                    new BlsequenceLatticeAPI().setBlsequenceLattice(bs, lattice, bl_seq_order);
                    Element first_ele = new ElementAPI().getElementByName(first_ele_name);
                    Element last_ele = new ElementAPI().getElementByName(last_ele_name);
                    if (first_ele != null) {
                        System.out.println("Warning:The element " + first_ele_name + " is already in the database! Please don't insert repeatedly!");
                    } else {
                        new ElementAPI().setElement(first_ele_name, first_ele_s, 0, 0, 0, 0, 0, 0, 0, 0, created_by, create_date,sequence_name, "marker");
                    }
                    if (last_ele != null) {
                        System.out.println("Warning:The element " + last_ele_name + " is already in the database! Please don't insert repeatedly!");
                    } else {
                          new ElementAPI().setElement(last_ele_name, last_ele_s, 0, 0, 0, 0, 0, 0, 0, seq_length, created_by, create_date,sequence_name, "marker");
                    }                 
                }
                //  em.getTransaction().commit();
            }
        }
    }
}