/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openepics.model.api;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.ElementType;
import org.openepics.model.entity.ElementTypeProp;
import org.openepics.model.entity.GoldLattice;
import org.openepics.model.entity.Lattice;
import org.openepics.model.entity.MachineMode;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ModelCode;
import org.openepics.model.entity.ModelGeometry;
import org.openepics.model.entity.ModelLine;

/**
 *
 * @author chu
 * @version
 */
public class ModelDB {
    @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("modelAPIPU");
    EntityManager em = emf.createEntityManager();

    @PersistenceContext
    
    /**
     * Set a new beamline sequence
     * @param seq_name sequence name
     * @param first_elem_name first element name
     * @param last_elem_name last element name
     * @param previous_seq previous sequence name
     * @param seq_length sequence length
     * @param seq_desc description for this sequence
     */
    public void setBeamlineSequence(String seq_name, String first_elem_name, 
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
     * 
     * @param mode
     * @param line
     * @return 
     */
    public GoldLattice getGoldLatticeForMachineModeAndModelLine(String mode, String line) {
        Query q;
        q = em.createQuery("SELECT g FROM Gold g WHERE "
                + "g.modelLineId.modelLineName = :lineName "
                + "AND g.machineModeId.machineModeName = :modeName "
                + "AND g.goldStatusInd = :gind").setParameter("lineName", line)
                .setParameter("modeName", mode).setParameter("gind", GoldLattice.PRESENT); 
        List<GoldLattice> gmList = q.getResultList();
        if (gmList.isEmpty()) {
            return null;
        }
        else { 
            return gmList.get(0);   
        }
    }
        
    /**
     * Get all models for the specified model line
     * 
     * @param line model line name
     * @return models for the specified model line
     */
    public List<Model> getAllModelsForModelLine(String line) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "JOIN l.modelLineId ml WHERE ml.modelLineName = :lineName").setParameter("lineName", line);
        List<Model> mList = q.getResultList();
        
        return mList;        
    }
    
    /**
     * get all models with specified model line and machine mode
     * 
     * @param line model line name
     * @param mode machine mode
     * @return modes with specified model line and machine mode
     */
    public List<Model> getAllModelsForModelLineAndMachineMode(String line, String mode) {
        Query q;
        q = em.createQuery("SELECT m FROM Model m JOIN m.latticeId l "
                + "WHERE "
                + "l.modelLineId.modelLineName = :lineName "
                + "AND l.machineModeId.machineModeName = :modeName").setParameter("lineName", line).setParameter("modeName", mode);
        
        List<Model> mList = q.getResultList();
        return mList;
    }
    
    /**
     * get all element type to modeling class mappings
     * 
     * @return element type to model class mappings
     */
    public List<ElementTypeProp> getAllElementClassMappings() {
        Query q;
        q = em.createNamedQuery("ElementTypeProp.findByElementTypePropName").setParameter("elementTypePropName", "XAL_class_mapping");
        List<ElementTypeProp> maps = q.getResultList();
        return maps;
    }
    
    /**
     * Set a new element type
     * @param elem_type element type
     * @param elem_type_desc description for this element type
     */
    public void setElementType(String elem_type, String elem_type_desc) {
        ElementType et = new ElementType();
        et.setElementType(elem_type);
        et.setElementTypeDescription(elem_type_desc);
        em.getTransaction().begin();
        em.persist(et);
        em.getTransaction().commit();
    }
    
    
    /**
     * Tag a Lattice as Gold
     * 
     * @param l the Lattice to be tagged as Gold
     */
    public void setGoldLattice(Lattice l) {
        GoldLattice gl = new GoldLattice();
        Date date = new Date();
        gl.setCreateDate(date);
        gl.setCreatedBy(System.getProperty("user.name"));
        gl.setGoldStatusInd(GoldLattice.PRESENT);
        gl.setLatticeId(l);
        em.getTransaction().begin();
        em.persist(gl);
        
        // move present Gold to previous Gold
        try {
            GoldLattice g_old = getGoldLatticeForMachineModeAndModelLine(
                    gl.getLatticeId().getMachineModeId().getMachineModeName(), 
                gl.getLatticeId().getModelLineId().getModelLineName());
        
            g_old.setGoldStatusInd(GoldLattice.PREVIOUS);
            g_old.setUpdateDate(date);
            g_old.setUpdatedBy(System.getProperty("user.name"));
            em.persist(g_old);
        } 
        // if there is no Gold for this line/mode, skip it
        catch (NullPointerException e) {
            // do nothing
        }
        
        em.getTransaction().commit();
    }
    
    
    public void saveModelData() {
        // TODO save model output data
    }
    
    public void saveModelInputData() {
        // TODO save model input data
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ModelDB x = new ModelDB();
        List seqs = BeamlineSequenceAPI.getAllSequences();
        System.out.println("There are " + seqs.size() + " sequences.");
        
//        x.setModelCode("XAL", "EnvelopeTracker");
        
//        List<ModelCode> mcList = x.getAllModelCodes();
//        System.out.println("There are " + mcList.size() + " mode codes.");

//        x.setElementType("SH", "horizontal sextupole");
          
//        MachineModeAPI.setMachineMode("USER", "user defined model");
                  
//        List<ModelCode> mcList = x.getAllModelCodes();
//        System.out.println("There are " + mcList.size() + " mode codes.");
        
//        System.out.println("1st XAL algorithm: " + x.getAlgorithmsForModelCode("XAL").get(0).getAlgorithm());
        
//        ModelAPI.setModelHeader("LINAC", "XAL", "DESIGN", "Test", "A database upload test");
        
        List<Model> ml = x.getAllModelsForModelLineAndMachineMode("LINAC", "DESIGN");
//        List<Model> ml = x.getAllModelsForModelLine("LINAC");
        if (!ml.isEmpty()) {
            Iterator<Model> it = ml.iterator();
            int i = 1;
            while (it.hasNext()) {
                Model m = it.next();
                System.out.println("Model " + i + ": " + m.getModelName() + " with mode = " + m.getLatticeId().getMachineModeId().getMachineModeName());
                i++;
            }
        }

    }
    
    
}
